package pharmaceuticals.nl.peptrix.export;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class Storereport {

	int updatesample;

	Controller cc;

	byte[] datareport;

	double filegrootte_kbytes;

	BigDecimal bigdecimal_filegrootte_kbytes;

	ResultService resultService;

	public Storereport(Controller cc) {
		this.cc = cc;
		resultService = new ResultServiceImpl(cc);
	}

	public int storereport(String report, String strexperimentid, String expyear, String reportname,
			String strquantilethreshold) {
		datareport = report.getBytes();
		filegrootte_kbytes = datareport.length / 1024.000;
		boolean exporttodisksucceeded = cc.exportfiletodisk.exportfile_and_directory(cc.userhome, reportname,
				datareport);
		boolean filetransported = false;
		if (cc.ftp != null) {
		} else {
			cc.ftp = new FTPClient();
		}
		try {
			cc.ftp.setRemoteHost(cc.ftpremotehost);
			cc.ftp.connect();
			cc.ftp.login(cc.ftpuser, cc.ftppassword);
			cc.ftp.setConnectMode(FTPConnectMode.PASV);
			cc.ftp.setType(FTPTransferType.BINARY);
			cc.ftp.chdir(File.separator + expyear + File.separator + strexperimentid.trim());
			cc.ftp.put(datareport, reportname);
			filetransported = true;
			cc.ftp.quit();
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (filetransported) {
			bigdecimal_filegrootte_kbytes = new BigDecimal(filegrootte_kbytes);
			cc.actualtime.resettime();

			updatesample = resultService.delete_report(strquantilethreshold, reportname, strexperimentid);
			updatesample = resultService.insertreport(strquantilethreshold, reportname, strexperimentid,
					bigdecimal_filegrootte_kbytes);
		}
		return updatesample;
	}

}

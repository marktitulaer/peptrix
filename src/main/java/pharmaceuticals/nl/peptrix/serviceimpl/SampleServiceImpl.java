package pharmaceuticals.nl.peptrix.serviceimpl;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.SampleService;

public class SampleServiceImpl implements SampleService {

	String strquery;

	Object[][] odatasample;

	Controller cc;

	Object[][] totalnumberofsamples;

	Object[][] numberofsamplesinmatrix;

	Object[][] toolownumberofcalibratedsamples;

	public SampleServiceImpl(Controller cc) {
		this.cc = cc;
	}

	public Object[][] getsampledata(String sampleid) {
		strquery = "select sa.sample_code, sa.name  " + "from sample sa where sa.sampleid = " + sampleid;
		try {
			odatasample = cc.jdbcconnection.returnData(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odatasample;
	}

	public Object[][] determine_number_of_samples(String strexperimentid) {
		strquery = "select count(distinct(result.sampleid)) Total_samples, "
				+ " group_code, group_.name, group_.group_id from result inner join group_ on "
				+ " result.group_id = group_.group_id inner join sample on "
				+ " sample.sampleid = result.sampleid where result.experimentid = " + strexperimentid + " and "
				+ " result.type = 'fid' group by result.group_id ";
		try {
			totalnumberofsamples = cc.jdbcconnection.returnData(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return totalnumberofsamples;
	}

	public Object[][] getnumberofsamplesinmatrix(String strexperimentid, String strquantilethreshold) {
		strquery = "select count(distinct(result.sampleid)) Samples_in_matrix, "
				+ "group_code, group_.name, group_.group_id from " + "result "
				+ "inner join group_ on result.group_id = group_.group_id "
				+ "inner join sample on sample.sampleid = result.sampleid " + "where result.experimentid = "
				+ strexperimentid + " and quantilethreshold = '" + strquantilethreshold.trim()
				+ "' and result.type = 'reduced' " + "group by result.group_id";
		try {
			numberofsamplesinmatrix = cc.jdbcconnection.returnData(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return numberofsamplesinmatrix;
	}

	public Object[][] getsampleswithtoolownumberofcalibrantmatches(String strexperimentid,
			String strquantilethreshold) {
		strquery = "select count(distinct(result.sampleid)) Samples_tolowcalibrated, "
				+ "group_code, group_.name, group_.group_id " + "from result inner join group_ on "
				+ "result.group_id = group_.group_id inner join sample on "
				+ "sample.sampleid = result.sampleid where result.experimentid = " + strexperimentid + " and "
				+ "result.quantilethreshold = '" + strquantilethreshold.trim() + "' and result.type like '%n<%' group "
				+ "by result.group_id ";
		try {
			toolownumberofcalibratedsamples = cc.jdbcconnection.returnData(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return toolownumberofcalibratedsamples;
	}

	public int insertsample(String strSample_code, String newsamplename) {
		int updatesample = -1;
		strquery = "insert into sample (sample_code, name) " + "values ('" + strSample_code.trim() + "','"
				+ newsamplename + "');";
		try {
			updatesample = cc.jdbcconnection.update_table(strquery);
		} catch (SQLException e) {
			if (cc.errornotshownbefore) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				cc.errornotshownbefore = false;
			}
			if (e.getMessage().trim().indexOf("Duplicate entry") > -1) {
				updatesample = 1;
			}
		}
		return updatesample;
	};

	public Object[][] getmaxsampleidfromsamplecode(String strSample_code) {
		Object[][] odata = null;
		strquery = " select max(sa.sampleid) " + "from sample sa where sa.sample_code = '" + strSample_code.trim()
				+ "' ";
		try {
			odata = cc.jdbcconnection.returnData(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odata;
	}

	public void insertsample(String filename) {
		strquery = "insert into sample (sample_code, name) " + "values ('" + filename + "','" + "sample " + filename
				+ "');";
		try {
			cc.jdbcconnection.add_update_batch(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}

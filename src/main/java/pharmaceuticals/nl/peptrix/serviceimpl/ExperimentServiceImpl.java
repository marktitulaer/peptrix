package pharmaceuticals.nl.peptrix.serviceimpl;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ExperimentService;

public class ExperimentServiceImpl implements ExperimentService {

	Controller cc;

	private String strquery;

	public ExperimentServiceImpl(Controller cc) {
		this.cc = cc;
	}

	// @Override
	public String[] getexperimentdata(String strexperimentid) {
		String[] dataexperiment = new String[6];
		Object[][] odata;
		try {
			dataexperiment[0] = "";
			dataexperiment[1] = "";
			dataexperiment[2] = "";
			dataexperiment[3] = "";
			dataexperiment[4] = "";
			dataexperiment[5] = "";
			if (!strexperimentid.trim().equalsIgnoreCase("")) {
				strquery = "select exp.name, equip.name, exp.date, equip.equipmentid, exp.year, equip.code "
						+ " from Experiment exp left outer join equipment equip "
						+ "on exp.equipmentid = equip.equipmentid " + "where experimentid = " + strexperimentid.trim();
				odata = cc.jdbcconnection.returnData(strquery);
				if (odata.length > 0) {
					dataexperiment[0] = (String) odata[0][0];
					dataexperiment[1] = (String) odata[0][1];
					dataexperiment[2] = (String) odata[0][2];
					dataexperiment[3] = (String) odata[0][3];
					dataexperiment[4] = (String) odata[0][4];
					dataexperiment[5] = (String) odata[0][5];
				}
			}
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return dataexperiment;
	}

	// @Override
	public String getnewexperimentid() {
		String newexperimentid = null;
		try {
			strquery = "select max(experimentid) + 1 from Experiment";
			Object[][] odataexperiment = cc.jdbcconnection.returnData(strquery);
			if (odataexperiment.length > 0) {
				newexperimentid = (String) odataexperiment[0][0];

			} else {
				newexperimentid = "1";
			}
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return newexperimentid;
	}

	public int getequipmentid(String experimentnumber) {
		Object[][] odataequipmentid = null;
		strquery = "select equipmentid from experiment where experimentid = " + experimentnumber;
		try {
			odataequipmentid = cc.jdbcconnection.returnData(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		int equipmentid = Integer.parseInt(odataequipmentid[0][0].toString());
		return equipmentid;
	}

	public int create_new_experiment(String equipmentid, String newexperimentname) {
		int records_affected = -1;
		try {
			strquery = "INSERT INTO Experiment(Name, Date, Equipmentid,Year) VALUES('" + newexperimentname + "','"
					+ cc.actualtime.getdatestring() + "', " + equipmentid + ",'" + cc.actualtime.getyear() + "')";
			records_affected = cc.jdbcconnection.update_table(strquery);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return records_affected;
	}

	public String getlatestexperimentid() {
		String latestexperimentid = "1";
		Object[][] odataexperiment = null;
		try {
			strquery = "select max(experimentid) from Experiment";
			odataexperiment = cc.jdbcconnection.returnData(strquery);
			if (odataexperiment.length > 0) {
				latestexperimentid = (String) odataexperiment[0][0];
			} else {
				latestexperimentid = "1";
			}
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return latestexperimentid;
	}

	public Object[][] getexperimentdata2(String experimentid) {
		Object[][] odata = null;
		try {
			strquery = "select exp.name, equip.name, exp.date, equip.equipmentid "
					+ " from Experiment exp left outer join equipment equip "
					+ "on exp.equipmentid = equip.equipmentid " + "where experimentid = " + experimentid.trim();
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

}

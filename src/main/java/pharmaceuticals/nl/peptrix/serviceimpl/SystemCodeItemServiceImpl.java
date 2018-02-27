package pharmaceuticals.nl.peptrix.serviceimpl;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.SystemCodeItemService;

public class SystemCodeItemServiceImpl implements SystemCodeItemService {

	Controller cc;

	private String query;

	public SystemCodeItemServiceImpl(Controller cc) {
		this.cc = cc;
	}

	public Object[][] getsystemcodeitem() {
		Object[][] odatamodifications = null;
		String query = "select itemvalue from systemcode, systemcodeitem, itemvalue "
				+ " where itemvalue.systemcodeitemid  = systemcodeitem.systemcodeitemid "
				+ " and systemcode.systemcodeid = systemcodeitem.systemcodeid "
				+ " and systemcode.code = 'modifications' and systemcodeitem.itemcode = 'ms2_mod'";
		try {
			odatamodifications = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return odatamodifications;
	}

	public Object[][] selectenzymes() {
		Object[][] odataenzymes = null;
		query = "select itemvalue from systemcode, systemcodeitem, itemvalue "
				+ " where itemvalue.systemcodeitemid  = systemcodeitem.systemcodeitemid "
				+ " and systemcode.systemcodeid = systemcodeitem.systemcodeid "
				+ " and systemcode.code = 'enzyme' and systemcodeitem.itemcode = 'enzyme'";
		try {
			odataenzymes = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return odataenzymes;
	}

	public Object[][] getdirectories() {
		Object[][] odata = null;
		query = "select count(*) from systemcode where systemcode.code = 'directories'";
		try {
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odata;
	}

	public Object[][] getdirectories2() {
		Object[][] odata = null;
		query = "select * from systemcode where systemcode.code = 'directories'";
		try {
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odata;
	}

	public Object[][] filecount(String file_to_search) {
		Object[][] odata = null;
		query = "select count(*) from systemcode, systemcodeitem where systemcode.systemcodeid = systemcodeitem.systemcodeid and systemcode.code = 'directories' and systemcodeitem.itemcode = '"
				+ file_to_search + "'";
		try {
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odata;
	}

	public void insertsystemdirectories() {
		query = "INSERT INTO systemcode(code,description) values ('Directories','System Directories')";
		int updatenumber;
		try {
			updatenumber = cc.jdbcconnection.update_table(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public Object[][] searchfile(String file_to_search) {
		Object[][] odata = null;
		query = "select itemvalue from systemcode, systemcodeitem, itemvalue where itemvalue.systemcodeitemid  = systemcodeitem.systemcodeitemid and systemcode.systemcodeid = systemcodeitem.systemcodeid and systemcode.code = 'directories' and systemcodeitem.itemcode = '"
				+ file_to_search + "'";
		try {
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odata;
	}

	public void insertfile(String systemcode_id, String file_to_search) {
		query = "INSERT INTO systemcodeitem(systemcodeid,itemcode,description) values ('" + systemcode_id + "','"
				+ file_to_search + "','" + file_to_search + " directory')";
		try {
			int updatenumber = cc.jdbcconnection.update_table(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public Object[][] searchdirectories(String file_to_search) {
		Object[][] odata = null;
		query = "select systemcodeitem.systemcodeitemid from systemcodeitem, systemcode where systemcodeitem.itemcode = '"
				+ file_to_search
				+ "' and systemcodeitem.systemcodeid = systemcode.systemcodeid and systemcode.code = 'directories'";

		try {
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odata;
	}

	public void insertitemvalue(String systemcodeitemid, String itemvalue) {
		query = "INSERT INTO itemvalue(systemcodeitemid,itemvalue) values ('" + systemcodeitemid.trim() + "','"
				+ itemvalue.trim() + "')";
		try {
			int updatenumber = cc.jdbcconnection.update_table(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public Object[][] getclibrationstandards() {
		Object[][] odata = null;
		query = "select  syscoditem.systemcodeitemid, syscoditem.description "
				+ "from systemcodeitem syscoditem,  systemcode syscod "
				+ "where syscoditem.systemcodeid = syscod.systemcodeid " + "and syscod.code = 'CALMASSES'";
		try {
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return odata;
	}

	public Object[][] getclibrationmasses(String systemcodeitemid) {
		Object[][] odata = null;
		query = "select itemvalue.itemvalue, itemcode from itemvalue "
				+ " inner join systemcodeitem on systemcodeitem.systemcodeitemid = itemvalue.systemcodeitemid "
				+ " inner join Unit on itemvalue.Unitid = Unit.Unitid " + " where systemcodeitem.systemcodeitemid = "
				+ systemcodeitemid + " and Unit.Type = 'mass'";
		try {
			odata = cc.jdbcconnection.returnData(query);
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

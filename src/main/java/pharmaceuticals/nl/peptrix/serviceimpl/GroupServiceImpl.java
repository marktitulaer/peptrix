package pharmaceuticals.nl.peptrix.serviceimpl;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.GroupService;

public class GroupServiceImpl implements GroupService {
    private String strquery;
    Controller cc;

    public GroupServiceImpl(Controller cc) {
        this.cc = cc;
    }

    public Object[][] getgroupdata(String groupid) {
        Object[][] odatagroup = null;
        strquery = "select gr.Group_id, gr.group_code, gr.name " + "from group_  gr " + "where gr.Group_id = "
                + groupid;
        try {
            odatagroup = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatagroup;
    }

    public Object[][] selectgroup(String strGroup_code, String strexperimentid) {
        cc.strquery = " select rs.group_id " + "from result rs, group_ gr " + "where rs.group_id = gr.group_id "
                + "and gr.group_code = '" + strGroup_code.trim() + "' and rs.experimentid = " + strexperimentid.trim();
        Object[][] odatagroup = null;
        try {
            odatagroup = cc.jdbcconnection.returnData(cc.strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatagroup;
    }

    public int insertgroup(String strGroup_code, String strGroup_name) {
        cc.strquery = "insert into group_ (group_code, name) " + "values ('" + strGroup_code.trim() + "','"
                + strGroup_name.trim() + "');";
        int updategroup = -1;
        try {
            updategroup = cc.jdbcconnection.update_table(cc.strquery);
        } catch (SQLException e) {
            if (cc.errornotshownbefore) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                cc.errornotshownbefore = false;
            }
        }
        return updategroup;
    }

    public String getmaxgroupid(String strGroup_code) {
        Object[][] odatagroup = null;
        String newgroupid2 = "";
        cc.strquery = " select max(gr.group_id) " + "from group_ gr where gr.group_code = '" + strGroup_code.trim()
                + "' ";
        try {
            odatagroup = cc.jdbcconnection.returnData(cc.strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (odatagroup.length > 0) {
            newgroupid2 = odatagroup[0][0].toString().trim();
        }
        return newgroupid2;
    }
}

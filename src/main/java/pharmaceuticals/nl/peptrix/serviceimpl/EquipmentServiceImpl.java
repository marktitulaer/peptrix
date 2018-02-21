package pharmaceuticals.nl.peptrix.serviceimpl;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.EquipmentService;

public class EquipmentServiceImpl implements EquipmentService {
    Controller cc;
    private String strquery;

    public EquipmentServiceImpl(Controller cc) {
        this.cc = cc;
    }

    public void show_equipment() {
        try {
            strquery = "select equipmentid, name from Equipment order by name";
            cc.odataequipment = cc.jdbcconnection.returnData(strquery);
            cc.equipmentnames = new String[cc.odataequipment.length];
            for (int i = 0; i <= (cc.odataequipment.length - 1); i++) {
                cc.equipmentnames[i] = (String) cc.odataequipment[i][1];
            }
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

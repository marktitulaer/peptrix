package pharmaceuticals.nl.peptrix;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import pharmaceuticals.nl.peptrix.database.HibernateUtil;
import pharmaceuticals.nl.peptrix.gui.CreateMenu;
import pharmaceuticals.nl.peptrix.gui.application.FramePanel;
import pharmaceuticals.nl.peptrix.model.*;

import org.hibernate.*;

public class Loginscreen implements WindowListener {

	static String screentitle = "PEPTRIX v3.37";

	Controller cc;

	public static void main(String[] args) throws SQLException {
		Loginscreen lg = new Loginscreen(screentitle);
		lg.dummy();
	}

	Loginscreen(String title) throws SQLException {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			Equipment equipment = new Equipment();
			Sample sample = new Sample(); 
			Group group = new Group();
			Systemcode systemcode = new Systemcode();
			Systemcodeitem systemcodeitem = new Systemcodeitem();
			Unit unit = new Unit();
			
			//equipment.setCode("MALDITOF");
			//equipment.setName("Bruker Daltonics Ultraflex MALDI-TOF");
			//session.save(equipment);

			//equipment = new Equipment();
			//equipment.setCode("FTICRMS");
			//equipment.setName("FTMS");
			//session.save(equipment);

			//equipment = new Equipment();
			//equipment.setCode("MALDILCMS");
			//equipment.setName("MALDI-TOF LC MS");
			//session.save(equipment);

			//equipment = new Equipment();
			//equipment.setCode("ORBITRAP");
			//equipment.setName("LTQ Orbitrap LC MS");
			//session.save(equipment);
			
			//equipment = new Equipment();
			//equipment.setCode("IONTRAP");
			//equipment.setName("Bruker Ion Trap");
			//session.save(equipment);
			
			//group.setName("Group 1");
			//group.setGroupcode("10");
			//session.save(group);
			
			//sample.setSamplecode("sample 1");
			//sample.setName("sample name 1");
			//session.save(sample);
			
			//systemcode.setCode("CALMASSES");
			//systemcode.setDescription("Calibration Masses");
			//session.save(systemcode);
			
			//systemcode = new Systemcode(); 
			//systemcode.setCode("MODIFICATIONS");
			//systemcode.setDescription("Modifications");
			//session.save(systemcode);
			
			//systemcode = new Systemcode(); 
			//systemcode.setCode("ENZYME");
			//systemcode.setDescription("Enzyme");
			//session.save(systemcode);
			
			//systemcodeitem.setItemcode("ALBMASSES");
			//systemcodeitem.setDescription("Albumine Masses");
			//session.save(systemcodeitem);
			
			//systemcodeitem = new Systemcodeitem();
			//systemcodeitem.setItemcode("MS2_MOD");
			//systemcodeitem.setDescription("MS2 Modifications");
			//session.save(systemcodeitem);
	
			//systemcodeitem = new Systemcodeitem();
			//systemcodeitem.setItemcode("ENZYME");
			//systemcodeitem.setDescription("Enzyme");
			//session.save(systemcodeitem);
			
			unit.setType("mass");
			unit.setUnitvalue("m/z");
			session.save(unit);
			
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		HibernateUtil.shutdown();

		JFrame frame = new JFrame(title);
		cc = new Controller(frame);
		MenuBar menubar = new CreateMenu(cc);
		frame.setMenuBar(menubar);
		Dimension d = frame.getToolkit().getScreenSize();
		frame.setSize(d.width, d.height);
		frame.setLocation(0, 0);
		frame.addWindowListener(this);
		FramePanel framepanel = new FramePanel(cc);
		JPanel jpanel = framepanel.getPanel();
		frame.getContentPane().add(jpanel);
		frame.setVisible(true);
	}

	private void dummy() {
	}

	public void windowClosing(WindowEvent e) {
		try {
			if (cc.jdbcconnection.con != null) {
				cc.jdbcconnection.con.close();
			}
		} catch (SQLException sqlexception) {
		}
		System.exit(0);
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}
}

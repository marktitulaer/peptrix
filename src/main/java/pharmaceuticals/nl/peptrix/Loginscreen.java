package pharmaceuticals.nl.peptrix;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import pharmaceuticals.nl.peptrix.gui.CreateMenu;
import pharmaceuticals.nl.peptrix.gui.application.FramePanel;
import pharmaceuticals.nl.peptrix.hibernate.HibernateUtil;
import pharmaceuticals.nl.peptrix.model.*;

import org.hibernate.*;

public class Loginscreen implements WindowListener {

	static String screentitle = "PEPTRIX v3.36";

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
			Person person = new Person();
			Group group = new Group();
			
			equipment.setCode("MALDITOF");
			equipment.setName("Bruker Daltonics Ultraflex MALDI-TOF");
			session.save(equipment);

			equipment = new Equipment();
			equipment.setCode("FTICRMS");
			equipment.setName("FTMS");
			session.save(equipment);

			equipment = new Equipment();
			equipment.setCode("MALDILCMS");
			equipment.setName("MALDI-TOF LC MS");
			session.save(equipment);

			equipment = new Equipment();
			equipment.setCode("ORBITRAP");
			equipment.setName("LTQ Orbitrap LC MS");
			session.save(equipment);

			equipment = new Equipment();
			equipment.setCode("IONTRAP");
			equipment.setName("Bruker Ion Trap");
			session.save(equipment);
			
			person.setName("Person 1");
			session.save(person);
			
			group.setName("Group 1");
			group.setGroupcode("1");
			session.save(group);
			

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

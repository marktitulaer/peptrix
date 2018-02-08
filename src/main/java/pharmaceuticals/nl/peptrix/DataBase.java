package pharmaceuticals.nl.peptrix;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import pharmaceuticals.nl.peptrix.database.HibernateUtil;
import pharmaceuticals.nl.peptrix.model.*;

public class DataBase {

	public DataBase() {
	}

	public void FillInitialData() {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			Sample sample = new Sample();
			Group group = new Group();
			Systemcode systemcode = new Systemcode();
			Systemcodeitem systemcodeitem = new Systemcodeitem();
			Unit unit = new Unit();
			ItemValue itemvalue = new ItemValue();
			Experiment experiment = new Experiment();

			Equipment equipment = new Equipment();
			Query<Equipment> query = session.createQuery("from Equipment where Code = :code  ");
			query.setParameter("code", "MALDITOF");
			List<Equipment> equipments = query.list();
			if (equipments.size() == 0) {
				equipment.setCode("MALDITOF");
				equipment.setName("Bruker Daltonics Ultraflex MALDI-TOF");
				session.save(equipment);
			}

			equipment = new Equipment();
			query.setParameter("code", "FTICRMS");
			equipments = query.list();
			if (equipments.size() == 0) {
				equipment.setCode("FTICRMS");
				equipment.setName("FTMS");
				session.save(equipment);
			}

			equipment = new Equipment();
			query.setParameter("code", "MALDILCMS");
			equipments = query.list();
			if (equipments.size() == 0) {
				equipment.setCode("MALDILCMS");
				equipment.setName("MALDI-TOF LC MS");
				session.save(equipment);
			}

			equipment = new Equipment();
			query.setParameter("code", "ORBITRAP");
			equipments = query.list();
			if (equipments.size() == 0) {
				equipment.setCode("ORBITRAP");
				equipment.setName("LTQ Orbitrap LC MS");
				session.save(equipment);
			}

			equipment = new Equipment();
			query.setParameter("code", "IONTRAP");
			equipments = query.list();
			if (equipments.size() == 0) {
				equipment.setCode("IONTRAP");
				equipment.setName("Bruker Ion Trap");
				session.save(equipment);
			}

			// group.setName("Group 1");
			// group.setGroupcode("10");
			// session.save(group);

			// sample.setSamplecode("sample 1");
			// sample.setName("sample name 1");
			// session.save(sample);

			// systemcode.setCode("CALMASSES");
			// systemcode.setDescription("Calibration Masses");
			// session.save(systemcode);

			// systemcode = new Systemcode();
			// systemcode.setCode("MODIFICATIONS");
			// systemcode.setDescription("Modifications");
			// session.save(systemcode);

			// systemcode = new Systemcode();
			// systemcode.setCode("ENZYME");
			// systemcode.setDescription("Enzyme");
			// session.save(systemcode);

			// systemcodeitem.setItemcode("ALBMASSES");
			// systemcodeitem.setDescription("Albumine Masses");
			// session.save(systemcodeitem);

			// systemcodeitem = new Systemcodeitem();
			// systemcodeitem.setItemcode("MS2_MOD");
			// systemcodeitem.setDescription("MS2 Modifications");
			// session.save(systemcodeitem);

			// systemcodeitem = new Systemcodeitem();
			// systemcodeitem.setItemcode("ENZYME");
			// systemcodeitem.setDescription("Enzyme");
			// session.save(systemcodeitem);

			// unit.setType("mass");
			// unit.setUnitvalue("m/z");
			// session.save(unit);

			// itemvalue.setItemvalue("960.5631");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("1000.6043");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("1149.6156");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("1511.8433");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("2045.0959");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("Carbamidomethyl (C)");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("Oxidation (M)");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("Phosphorylation (S)");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("Phosphorylation (T)");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("Phosphorylation (Y)");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("Trypsin");
			// session.save(itemvalue);

			// itemvalue = new ItemValue();
			// itemvalue.setItemvalue("Chymotrypsin");
			// session.save(itemvalue);

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
	}

}

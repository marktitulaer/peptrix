package pharmaceuticals.nl.peptrix.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pharmaceuticals.nl.peptrix.model.*;

public class DataBase {

    String jdbc_user;
    String jdbc_password;
    String databaseName;

    public DataBase(String jdbc_user, String jdbc_password, String databaseName) {
        this.jdbc_user = jdbc_user;
        this.jdbc_password = jdbc_password;
        this.databaseName = databaseName;
    }

    public void FillInitialData() {
        Session session = null;
        Transaction transaction = null;

        try {
            HibernateUtil.setDatabaseVariables(jdbc_user, jdbc_password, databaseName);
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Equipment equipment = new Equipment();
            Query<Equipment> queryequipment = session.createQuery("from Equipment where Code = :code  ");
            queryequipment.setParameter("code", "MALDITOF");
            List<Equipment> equipments = queryequipment.list();
            if (equipments.size() == 0) {
                equipment.setCode("MALDITOF");
                equipment.setName("Bruker Daltonics Ultraflex MALDI-TOF");
                session.save(equipment);
            }
            equipment = new Equipment();
            queryequipment.setParameter("code", "FTICRMS");
            equipments = queryequipment.list();
            if (equipments.size() == 0) {
                equipment.setCode("FTICRMS");
                equipment.setName("FTMS");
                session.save(equipment);
            }
            equipment = new Equipment();
            queryequipment.setParameter("code", "MALDILCMS");
            equipments = queryequipment.list();
            if (equipments.size() == 0) {
                equipment.setCode("MALDILCMS");
                equipment.setName("MALDI-TOF LC MS");
                session.save(equipment);
            }
            equipment = new Equipment();
            queryequipment.setParameter("code", "ORBITRAP");
            equipments = queryequipment.list();
            if (equipments.size() == 0) {
                equipment.setCode("ORBITRAP");
                equipment.setName("LTQ Orbitrap LC MS");
                session.save(equipment);
            }
            equipment = new Equipment();
            queryequipment.setParameter("code", "IONTRAP");
            equipments = queryequipment.list();
            if (equipments.size() == 0) {
                equipment.setCode("IONTRAP");
                equipment.setName("Bruker Ion Trap");
                session.save(equipment);
            }
            Group group = new Group();
            Query<Group> querygroup = session.createQuery("from Group where Group_code = :code  ");
            querygroup.setParameter("code", "1");
            List<Group> groups = querygroup.list();
            if (groups.size() == 0) {
                group.setGroupcode("1");
                group.setName("Group 1");
                session.save(group);
            }
            Sample sample = new Sample();
            Query<Sample> querysample = session.createQuery("from Sample where Sample_code = :code  ");
            querysample.setParameter("code", "1");
            List<Sample> samples = querysample.list();
            if (samples.size() == 0) {
                sample.setSamplecode("1");
                sample.setName("sample1");
                session.save(sample);
            }
            Systemcode systemcode = new Systemcode();
            Query<Systemcode> querysystemcode = session.createQuery("from Systemcode where Code = :code  ");
            querysystemcode.setParameter("code", "CALMASSES");
            List<Systemcode> systemcodes = querysystemcode.list();
            if (systemcodes.size() == 0) {
                systemcode.setCode("CALMASSES");
                systemcode.setDescription("Calibration Masses");
                session.save(systemcode);
            }
            systemcode = new Systemcode();
            querysystemcode.setParameter("code", "MODIFICATIONS");
            systemcodes = querysystemcode.list();
            if (systemcodes.size() == 0) {
                systemcode.setCode("MODIFICATIONS");
                systemcode.setDescription("Modifications");
                session.save(systemcode);
            }
            systemcode = new Systemcode();
            querysystemcode.setParameter("code", "ENZYME");
            systemcodes = querysystemcode.list();
            if (systemcodes.size() == 0) {
                systemcode.setCode("ENZYME");
                systemcode.setDescription("Enzyme");
                session.save(systemcode);
            }
            Systemcodeitem systemcodeitem = new Systemcodeitem();
            Query<Systemcodeitem> querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
            querysystemcodeitem.setParameter("code", "ALBMASSES");
            List<Systemcodeitem> systemcodeitems = querysystemcodeitem.list();
            if (systemcodeitems.size() == 0) {
                querysystemcode = session.createQuery("from Systemcode where Code = :code  ");
                querysystemcode.setParameter("code", "CALMASSES");
                systemcodes = querysystemcode.list();
                systemcode = systemcodes.get(0);
                systemcodeitem.setSystemcode(systemcode);
                systemcodeitem.setItemcode("ALBMASSES");
                systemcodeitem.setDescription("Albumine Masses");
                session.save(systemcodeitem);
            }
            systemcodeitem = new Systemcodeitem();
            querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
            querysystemcodeitem.setParameter("code", "MS2_MOD");
            systemcodeitems = querysystemcodeitem.list();
            if (systemcodeitems.size() == 0) {
                querysystemcode = session.createQuery("from Systemcode where Code = :code  ");
                querysystemcode.setParameter("code", "MODIFICATIONS");
                systemcodes = querysystemcode.list();
                systemcode = systemcodes.get(0);
                systemcodeitem.setSystemcode(systemcode);
                systemcodeitem.setItemcode("MS2_MOD");
                systemcodeitem.setDescription("MS2 Modifications");
                session.save(systemcodeitem);
            }
            systemcodeitem = new Systemcodeitem();
            querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
            querysystemcodeitem.setParameter("code", "ENZYME");
            systemcodeitems = querysystemcodeitem.list();
            if (systemcodeitems.size() == 0) {
                querysystemcode = session.createQuery("from Systemcode where Code = :code  ");
                querysystemcode.setParameter("code", "ENZYME");
                systemcodes = querysystemcode.list();
                systemcode = systemcodes.get(0);
                systemcodeitem.setSystemcode(systemcode);
                systemcodeitem.setItemcode("ENZYME");
                systemcodeitem.setDescription("Enzyme");
                session.save(systemcodeitem);
            }
            Unit unit = new Unit();
            Query<Unit> queryunit = session.createQuery("from Unit where Type = :type and UnitValue = :unitvalue ");
            queryunit.setParameter("type", "mass");
            queryunit.setParameter("unitvalue", "m/z");
            List<Unit> units = queryunit.list();
            if (units.size() == 0) {
                unit.setType("mass");
                unit.setUnitvalue("m/z");
                session.save(unit);
            }
            ItemValue itemvalue = new ItemValue();
            Query<ItemValue> queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "960.5631");
            List<ItemValue> itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                queryunit = session.createQuery("from Unit where Type = :type and UnitValue = :unitvalue ");
                queryunit.setParameter("type", "mass");
                queryunit.setParameter("unitvalue", "m/z");
                units = queryunit.list();
                unit = units.get(0);
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "ALBMASSES");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setUnit(unit);
                itemvalue.setItemvalue("960.5631");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "1000.6043");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                queryunit = session.createQuery("from Unit where Type = :type and UnitValue = :unitvalue ");
                queryunit.setParameter("type", "mass");
                queryunit.setParameter("unitvalue", "m/z");
                units = queryunit.list();
                unit = units.get(0);
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "ALBMASSES");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setUnit(unit);
                itemvalue.setItemvalue("1000.6043");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "1149.6156");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                queryunit = session.createQuery("from Unit where Type = :type and UnitValue = :unitvalue ");
                queryunit.setParameter("type", "mass");
                queryunit.setParameter("unitvalue", "m/z");
                units = queryunit.list();
                unit = units.get(0);
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "ALBMASSES");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setUnit(unit);
                itemvalue.setItemvalue("1149.6156");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "1511.8433");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                queryunit = session.createQuery("from Unit where Type = :type and UnitValue = :unitvalue ");
                queryunit.setParameter("type", "mass");
                queryunit.setParameter("unitvalue", "m/z");
                units = queryunit.list();
                unit = units.get(0);
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "ALBMASSES");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setUnit(unit);
                itemvalue.setItemvalue("1511.8433");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "2045.0959");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                queryunit = session.createQuery("from Unit where Type = :type and UnitValue = :unitvalue ");
                queryunit.setParameter("type", "mass");
                queryunit.setParameter("unitvalue", "m/z");
                units = queryunit.list();
                unit = units.get(0);
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "ALBMASSES");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setUnit(unit);
                itemvalue.setItemvalue("2045.0959");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "Carbamidomethyl (C)");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "MS2_MOD");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setItemvalue("Carbamidomethyl (C)");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "Oxidation (M)");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "MS2_MOD");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setItemvalue("Oxidation (M)");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "Phosphorylation (S)");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "MS2_MOD");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setItemvalue("Phosphorylation (S)");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "Phosphorylation (T)");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "MS2_MOD");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setItemvalue("Phosphorylation (T)");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "Phosphorylation (Y)");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "MS2_MOD");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setItemvalue("Phosphorylation (Y)");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "Trypsin");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "ENZYME");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setItemvalue("Trypsin");
                session.save(itemvalue);
            }
            itemvalue = new ItemValue();
            queryitemValue = session.createQuery("from ItemValue where Itemvalue = :itemvalue  ");
            queryitemValue.setParameter("itemvalue", "Chymotrypsin");
            itemValues = queryitemValue.list();
            if (itemValues.size() == 0) {
                querysystemcodeitem = session.createQuery("from Systemcodeitem where ItemCode = :code  ");
                querysystemcodeitem.setParameter("code", "ENZYME");
                systemcodeitems = querysystemcodeitem.list();
                systemcodeitem = systemcodeitems.get(0);
                itemvalue.setSystemcodeitem(systemcodeitem);
                itemvalue.setItemvalue("Chymotrypsin");
                session.save(itemvalue);
            }
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

package pharmaceuticals.nl.peptrix.allocation;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

public class InsertGroups {

    Controller cc;

    InsertGroup insertgroup;

    String stroldsampleid;

    String strnewampleid;

    String stroldgroupid;

    String strnewgroupid;

    ResultService resultService;

    public InsertGroups(Controller cc) {
        this.cc = cc;
        insertgroup = new InsertGroup(cc);
        resultService = new ResultServiceImpl(cc);
    }

    public void insertgroups(int datalength, String filtertype, String[] arraygroupids, String[] arraygroupcodes,
                             String[] arraygroupnames, String strexperimentid, String[] newgroupid, String[] oldgroupid,
                             String[] resultid, String[] oldgroupcode) {
        for (int i = 0; i <= (datalength - 1); i++) {
            newgroupid[i] = "";
            if (arraygroupids[i].trim().equals("")) {
                if (!(arraygroupcodes[i].trim().equals(""))) {
                    newgroupid[i] = insertgroup.insertgroup(arraygroupcodes[i], arraygroupnames[i], strexperimentid);
                } else {
                    if ((oldgroupid[i] != null) && (!(oldgroupid[i].trim().equals("")))) {
                        cc.updategroup = resultService.resetgroupid(resultid[i]);
                    }
                }
            } else {
                newgroupid[i] = arraygroupids[i].trim();
                if ((oldgroupid[i] != null) && (!(oldgroupid[i].trim().equalsIgnoreCase(newgroupid[i].trim())))) {
                    newgroupid[i] = arraygroupids[i].trim();
                } else {
                    if (oldgroupcode[i] != null) {
                        if (!(oldgroupcode[i].trim().equals(arraygroupcodes[i].trim()))) {
                            newgroupid[i] = insertgroup.insertgroup(arraygroupcodes[i], arraygroupnames[i],
                                    strexperimentid);
                        }
                    }
                }
            }
            if (!(newgroupid[i].trim().equals(""))) {
                cc.updategroup = resultService.updategroupid(newgroupid[i], resultid[i]);
            }
        }
        if (!strexperimentid.trim().equalsIgnoreCase("")) {
            stroldsampleid = "";
            strnewampleid = "";
            stroldgroupid = "";
            strnewgroupid = "";
            cc.odatafiles = resultService.getresultrecords(strexperimentid, filtertype);
            for (int i = 0; i <= (cc.odatafiles.length - 1); i++) {
                if (cc.odatafiles[i][1] == null) {
                    strnewampleid = "";
                } else {
                    strnewampleid = cc.odatafiles[i][1].toString().trim();
                }
                if (cc.odatafiles[i][2] == null) {
                    strnewgroupid = "";
                } else {
                    strnewgroupid = cc.odatafiles[i][2].toString().trim();
                }
                if ((stroldsampleid.trim().equalsIgnoreCase(strnewampleid))
                        && (!(stroldgroupid.trim().equalsIgnoreCase("")))) {
                    if (!stroldgroupid.trim().equalsIgnoreCase(strnewgroupid)) {
                        cc.updategroup = resultService.update_group_id(stroldgroupid, cc.odatafiles[i][0].toString());
                    }
                } else {
                    stroldsampleid = strnewampleid;
                    stroldgroupid = strnewgroupid;
                }
            }
        }
    }
}

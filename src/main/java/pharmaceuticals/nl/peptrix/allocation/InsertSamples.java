package pharmaceuticals.nl.peptrix.allocation;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

public class InsertSamples {

	Controller cc;

	Insertsample insertsample;

	ResultService resultService;

	int datalength;

	public InsertSamples(Controller cc) {
		this.cc = cc;
		insertsample = new Insertsample(cc);
		resultService = new ResultServiceImpl(cc);
	}

	public void insertsamples(String[] arraySampleids, String[] arraySample_codes, String[] arraySample_names,
			String strexperimentid, String[] newsampleid, String[] oldsampleid, String[] resultid,
			String[] oldsamplecode) {
		datalength = arraySampleids.length;
		for (int i = 0; i <= (datalength - 1); i++) {
			newsampleid[i] = "";
			if (arraySampleids[i].trim().equals("")) {
				if (!(arraySample_codes[i].trim().equals(""))) {
					newsampleid[i] = insertsample.insertsample(arraySample_codes[i], arraySample_names[i],
							strexperimentid);
				} else {
					if ((oldsampleid[i] != null) && (!(oldsampleid[i].trim().equals("")))) {
						cc.updategroup = resultService.resetsampleid(resultid[i]);
					}
				}
			} else {
				newsampleid[i] = arraySampleids[i];
				if ((oldsampleid[i] != null) && (!(oldsampleid[i].trim().equalsIgnoreCase(newsampleid[i].trim())))) {
					newsampleid[i] = arraySampleids[i].trim();
				} else {
					if (oldsamplecode[i] != null) {
						if (!(oldsamplecode[i].trim().equals(arraySample_codes[i].trim()))) {
							newsampleid[i] = insertsample.insertsample(arraySample_codes[i], arraySample_names[i],
									strexperimentid);
						}
					}
				}
			}
			if (!(newsampleid[i].trim().equals(""))) {
				cc.updategroup = resultService.updatesampleid(newsampleid[i], resultid[i]);
			}
		}
	}
}

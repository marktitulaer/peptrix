package pharmaceuticals.nl.peptrix.export;

public class CreateexportstringWilcoxon {

	String exportstringwilcoxon;

	StringBuffer exportbuffer;

	String linefeed = "\n";

	byte[] data_wilcoxon;

	public CreateexportstringWilcoxon() {
	}

	public byte[] create_exportstring(double[][] cleanedPalues) {
		exportstringwilcoxon = "";
		exportbuffer = new StringBuffer(exportstringwilcoxon);
		for (int i = 0; i < cleanedPalues[0].length; i++) {
			exportbuffer.append(String.valueOf(cleanedPalues[0][i]));
			exportbuffer.append(",");
			exportbuffer.append(String.valueOf(cleanedPalues[1][i]));
			exportbuffer.append(linefeed);
		}
		exportstringwilcoxon = exportbuffer.toString();
		data_wilcoxon = exportstringwilcoxon.getBytes();
		return data_wilcoxon;
	}
}

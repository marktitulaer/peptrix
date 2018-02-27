package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.io.*;
import javax.swing.*;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.gui.Progress;
import com.enterprisedt.net.ftp.*;

public class Wilcoxon {
    ExportFileToDisk exportfiletodisk;
    ExportFileToDisk rterminputfile;
    Controller cc;
    boolean filepresent;
    boolean isWindowsFlag;
    boolean first = true;
    int numberofrows;
    int rowlength;
    int int_randomizationgroups;
    int count;
    int index1;
    int index2;
    char backslash = (char) 92;
    String filename;
    String experimentnumber;
    String groupcode1;
    String groupid1;
    String groupcode2;
    String groupid2;
    String stringnumberrandomizations;
    String line;
    String linefeed;
    String Rscript;
    String color1;
    String color2;
    String osName;
    String inputpath;
    String outputpath;
    String Rscriptname;
    String searchstring;
    String teststring = "";
    String strinputplotheight;
    String strinputplotwidth;
    String pvalue;
    StringBuffer linebuffer;
    StringBuffer inputpathbuffer;
    StringBuffer outputpathbuffer;
    double[][] doublePvalues;
    String[] temp;
    String[] listdir;
    String experimentyear;
    FTPClient ftp;
    FileInputStream fstream;
    DataInputStream in;
    String strequipmentid;

    public Wilcoxon(Controller cc) {
        exportfiletodisk = new ExportFileToDisk(cc);
        rterminputfile = new ExportFileToDisk(cc);
        this.cc = cc;
    }

    public double[][] performtest(String experimentyear, String filename, String experimentnumber, String groupcode1,
                                  String groupid1, String groupcode2, String groupid2, String stringnumberrandomizations,
                                  int int_randomizationgroups, String color1, String color2, String strinputplotheight,
                                  String strinputplotwidth, String strequipmentid, Progress progress) {
        this.strequipmentid = strequipmentid;
        this.experimentyear = experimentyear;
        this.color1 = color1;
        this.color2 = color2;
        this.filename = filename;
        this.experimentnumber = experimentnumber;
        this.groupcode1 = groupcode1;
        this.groupid1 = groupid1;
        this.groupcode2 = groupcode2;
        this.groupid2 = groupid2;
        this.stringnumberrandomizations = stringnumberrandomizations;
        this.int_randomizationgroups = int_randomizationgroups;
        this.strinputplotheight = strinputplotheight;
        this.strinputplotwidth = strinputplotwidth;
        doublePvalues = null;
        Rscriptname = "Wilcoxon";
        progress.init("Perform Wilcoxon-Mann-Whittney test ...... generate input file R", 70);
        progress.setmaximum(100);
        generateinputfiles();
        progress.setnumberandtext(25, "Generate input files");
        if (filepresent == true) {
            progress.setnumberandtext(40, "Perform Wilcoxon-Mann-Whitney test ...... generate R script");
            generateRscript();
            cc.rterm.createRbatchfile(Rscriptname);
            progress.setnumberandtext(60, "Perform Wilcoxon-Mann-Whitney test ...... execute R script");
            int procesexitvalue = cc.rterm.execRscript();
            if (procesexitvalue == 0) {
                progress.setnumberandtext(75, "Perform Wilcoxon-Mann-Whitney test ...... read output files");
                readoutputfiles();
            }
            if (procesexitvalue == 1) {
                doublePvalues = null;
            }
            progress.setnumberandtext(100, "Done");
        }
        return doublePvalues;
    }

    private void readoutputfiles() {
        try {
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output0.txt");
            in = new DataInputStream(fstream);
            count = 0;
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(in));
            while ((pvalue = bufferedreader.readLine()) != null) {
                if (count >= 2) {
                    doublePvalues[1][count - 2] = Double.parseDouble(pvalue);
                }
                count++;
            }
            bufferedreader.close();
            in.close();
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void generateinputfiles() {
        cc.ms2_proteins = null;
        temp = null;
        first = true;
        boolean second = false;
        cc.data = null;
        filepresent = false;
        if (ftp != null) {
        } else {
            try {
                ftp = new FTPClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            ftp.setRemoteHost(cc.ftpremotehost);
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + experimentyear + File.separator + experimentnumber);
            numberofrows = 0;
            filepresent = false;
            listdir = ftp.dir();
            for (int i = 0; i < listdir.length; i++) {
                if (listdir[i].trim().equalsIgnoreCase(filename.trim())) {
                    filepresent = true;
                }
            }
            if (filepresent) {
                cc.data = ftp.get(filename);
                line = "";
                linebuffer = new StringBuffer(line);
                for (int i = 0; i < cc.data.length; i++) {
                    if ((cc.data[i] != 10) && (cc.data[i] != 13)) {
                        linebuffer.append((char) cc.data[i]);
                    }
                    if (cc.data[i] == 10) {
                        index1 = linebuffer.indexOf(",");
                        if (!linebuffer.toString().substring(0, index1).trim().equalsIgnoreCase("")) {
                            numberofrows++;
                        }
                        linebuffer = new StringBuffer(line);
                    }
                }
                linefeed = "\n";
                if (numberofrows > 0) {
                    try {
                        rterminputfile.open_complete_file(cc.rterm.RinputBasePath + "input0.txt");
                    } catch (IOException e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    numberofrows = 0;
                    line = "";
                    linebuffer = new StringBuffer(line);
                    for (int i = 0; i < cc.data.length; i++) {
                        if ((cc.data[i] != 10) && (cc.data[i] != 13)) {
                            linebuffer.append((char) cc.data[i]);
                        }
                        if (cc.data[i] == 10) {
                            index1 = linebuffer.indexOf(",");
                            if (!linebuffer.toString().substring(0, index1).trim().equalsIgnoreCase("")) {
                                if (index1 > -1) {
                                    index2 = linebuffer.indexOf(",", index1 + 1);
                                    if (index2 > -1) {
                                        linebuffer.replace(index1, index2, "");
                                    }
                                }
                                index1 = linebuffer.indexOf(",");
                                if (index1 > -1) {
                                    index1 = linebuffer.indexOf(",", index1 + 1);
                                    if (index1 > -1) {
                                        index2 = linebuffer.indexOf(",", index1 + 1);
                                        if (index2 > -1) {
                                            linebuffer.replace(index1, index2, "");
                                        }
                                    }
                                }
                                if (numberofrows == 0) {
                                    searchstring = "sampleid";
                                    index1 = linebuffer.indexOf(searchstring);
                                    if (index1 > -1) {
                                        linebuffer.replace(index1, index1 + searchstring.length(), "sample");
                                    }
                                    searchstring = "groupid";
                                    index1 = linebuffer.indexOf(searchstring);
                                    if (index1 > -1) {
                                        linebuffer.replace(index1, index1 + searchstring.length(), "group");
                                    }
                                }
                                line = linebuffer.toString();
                                if (first == true) {
                                    temp = line.split(",");
                                    rowlength = temp.length;
                                    doublePvalues = new double[4][rowlength - 2];
                                    count = 0;
                                    for (int j = 0; j < temp.length; j++) {
                                        if (j > 1) {
                                            doublePvalues[0][count] = Double.parseDouble(temp[j].trim());
                                            doublePvalues[3][count] = j;
                                            count++;
                                        }
                                    }
                                    first = false;
                                    second = true;
                                }
                                try {
                                    rterminputfile.append_data_to_file(line.getBytes());
                                    rterminputfile.append_data_to_file(linefeed.getBytes());
                                } catch (IOException e) {
                                    if (cc.debugmode) {
                                        e.printStackTrace();
                                    } else {
                                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                numberofrows++;
                            } else {
                                line = linebuffer.toString();
                                if (line.toLowerCase().indexOf("proteins") > -1) {
                                    cc.ms2_proteins = line.split(",");
                                }
                                if ((second == true) && (strequipmentid.trim().equalsIgnoreCase("3")
                                        || strequipmentid.trim().equalsIgnoreCase("4")
                                        || strequipmentid.trim().equalsIgnoreCase("5"))) {
                                    temp = line.split(",");
                                    count = 0;
                                    for (int j = 0; j < temp.length; j++) {
                                        if (j > 3) {
                                            doublePvalues[2][count] = Double.parseDouble("0")
                                                    + Double.parseDouble(temp[j].trim());
                                            doublePvalues[3][count] = j;
                                            count++;
                                        }
                                    }
                                    second = false;
                                }
                            }
                            line = "";
                            linebuffer = new StringBuffer(line);
                        }
                    }
                    try {
                        rterminputfile.close_file();
                    } catch (IOException e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            ftp.quit();
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateRscript() {
        Rscript = "zz <- read.table(\"input0\",sep=\",\",header=TRUE)" + linefeed + "randomzz <- zz" + linefeed
                + "n <- length(zz[1,])" + linefeed + "cgroup1 <- c(" + groupid1 + ")" + linefeed + "cgroup2 <- c("
                + groupid2 + ")" + linefeed + "numberofrandomizations <- c(" + stringnumberrandomizations + ")"
                + linefeed + "#----------------------------------------------------------------" + linefeed
                + "g1 <- zz[zz$group==cgroup1,]" + linefeed + "g2 <- zz[zz$group==cgroup2,]" + linefeed
                + "n <- length(zz[1,])" + linefeed + "wil <- NULL" + linefeed + "wil1 <-NULL" + linefeed
                + "for (i in 3:n) {" + linefeed + "  w <- wilcox.test(g1[,i],g2[,i],exact=FALSE)" + linefeed
                + "  wil[i] <- w$p.value" + linefeed + "}" + linefeed + "for (i in 3:n) {" + linefeed
                + "  w <- wilcox.test(g1[,i],g2[,i],exact=FALSE)" + linefeed + "  wil1[i] <- w$p.value" + linefeed + "}"
                + linefeed + "elkelhist <- hist(wil1, br=100, xlim=c(0,1), plot = FALSE)" + linefeed
                + "filepvalues <- \"output0\"" + linefeed
                + "write(wil1, file = filepvalues,ncolumns = 1,append = FALSE)" + linefeed
                + "#----------------------------------------------------------------" + linefeed
                + "peakg1 <- apply(g1[,1:n],2,sum)" + linefeed + "peakg2 <- apply(g2[,1:n],2,sum)" + linefeed
                + "inittabel <- rep(0,(3 * n))" + linefeed + "numberg1g2 = matrix(inittabel, nrow=3)" + linefeed
                + "for (i in 1:n) {" + linefeed + "   numberg1g2[1,i] <- wil[i]" + linefeed
                + "   numberg1g2[2,i] <- peakg1[i]" + linefeed + "   numberg1g2[3,i] <- peakg2[i]" + linefeed + "}"
                + linefeed + "ondergrens <- c(0)" + linefeed + "teller <- c(0)" + linefeed + "tellerg1 <- c(0)"
                + linefeed + "tellerg2 <- c(0)" + linefeed + "stap <- c(0.01)" + linefeed + "aantalstappen <- c(1/stap)"
                + linefeed + "verdeling <- rep(0,(2 * aantalstappen))" + linefeed
                + "tabverdeling = matrix(verdeling, nrow=2)" + linefeed + "for (i in 1:aantalstappen){" + linefeed
                + "  bovengrens <- ondergrens + stap" + linefeed + "  teller <- c(0)" + linefeed + "  tellerg1 <- c(0)"
                + linefeed + "  tellerg2 <- c(0)" + linefeed + "  for (j in 3:n){" + linefeed
                + "    if (!is.nan(numberg1g2[1,j])) {" + linefeed
                + "      if ((numberg1g2[1,j] > ondergrens) & ( bovengrens >= numberg1g2[1,j])){" + linefeed
                + "        teller = teller + 1" + linefeed + "        tellerg1 = tellerg1 + numberg1g2[2,j]" + linefeed
                + "        tellerg2 = tellerg2 + numberg1g2[3,j]" + linefeed + "      }" + linefeed + "    }" + linefeed
                + "  }" + linefeed + "  tabverdeling[1,i] = (tellerg1 / (tellerg1 + tellerg2)) * teller" + linefeed
                + "  tabverdeling[2,i] = teller" + linefeed + "  ondergrens <- ondergrens + stap" + linefeed + "}"
                + linefeed + "#----------------------------------------------------------------" + linefeed
                + "aantal_vlakken = (n-1) * numberofrandomizations  #aantal_vlakken = (n-2) * numberofrandomizations"
                + linefeed + "initialisatie = rep(0,aantal_vlakken)" + linefeed
                + "totaal = matrix(initialisatie, nrow=numberofrandomizations)" + linefeed
                + "for (j in 1:numberofrandomizations) {" + linefeed + "  wil <- NULL" + linefeed
                + "  randomreeks <- sample(zz[,2])" + linefeed + "  for (k in 1:length(randomreeks)) {" + linefeed
                + "    randomzz[k,2] <- randomreeks[k]" + linefeed + "  }" + linefeed
                + "  g1 <- randomzz[randomzz$group==cgroup1,]" + linefeed + "  g2 <- randomzz[randomzz$group==cgroup2,]"
                + linefeed + "  for (i in 3:n) {" + linefeed + "    w <- wilcox.test(g1[,i],g2[,i],exact=FALSE)"
                + linefeed + "    wil[i] <- w$p.value" + linefeed + "    totaal[j,(i-2)] <- wil[i]" + linefeed + "  }"
                + linefeed + "}" + linefeed + "histsignal <- hist(totaal, br=100, xlim=c(0,1),plot = FALSE)" + linefeed
                + "bincount <- length(histsignal$count)" + linefeed + "x <- seq(1,length(histsignal$count))" + linefeed
                + "#----------------------------------------------------------------" + linefeed;
        osName = System.getProperty("os.name");
        isWindowsFlag = osName.startsWith("Windows");
        if (isWindowsFlag) {
            Rscript = Rscript + "bmp(filename = \"bitmap0\", width = " + strinputplotwidth + ", height = "
                    + strinputplotheight + ", pointsize = 12, bg = \"white\", res = NA)";
        } else {
            Rscript = Rscript + "jpeg(filename = \"jpeg0\", width = " + strinputplotwidth + ", height = "
                    + strinputplotheight + ", pointsize = 12, quality = 75, bg = \"white\", res = NA)";
        }
        Rscript = Rscript + linefeed + "titel <- \"Wilcoxon\n" + groupcode1 + " (" + color1.toLowerCase().trim() + ")\n"
                + groupcode2 + " (" + color2.toLowerCase().trim() + ") \"" + linefeed
                + "plot(elkelhist,main=titel,xlab=\"Probability P\")" + linefeed
                + "gegevens <- paste(\"randomization = \",numberofrandomizations)" + linefeed
                + "mtext(side=4,gegevens, col=\"red\")" + linefeed
                + "lines((x/100),(histsignal$count[x]/numberofrandomizations),col=\"red\")" + linefeed
                + "ondergrens <- (stap /2)" + linefeed + "for (i in 1:aantalstappen){" + linefeed
                + "  arrows(ondergrens,0,ondergrens,tabverdeling[1,i],code=0,col=\"" + color1.toLowerCase().trim()
                + "\")" + linefeed + "  arrows(ondergrens,tabverdeling[1,i],ondergrens,tabverdeling[2,i],code=0,col=\""
                + color2.toLowerCase().trim() + "\")" + linefeed + "  ondergrens <- ondergrens + stap" + linefeed + "}"
                + linefeed + "dev.off()";
        teststring = String.valueOf(cc.rterm.RinputBasePath);
        inputpathbuffer = new StringBuffer();
        while (teststring.indexOf(backslash) > -1) {
            inputpathbuffer.append(teststring.substring(0, teststring.indexOf(backslash)));
            inputpathbuffer.append(backslash);
            inputpathbuffer.append(backslash);
            inputpathbuffer.append(backslash);
            inputpathbuffer.append(backslash);
            teststring = teststring.substring(teststring.indexOf(backslash) + 1);
        }
        inputpath = inputpathbuffer.toString();
        teststring = String.valueOf(cc.rterm.ReportsBasePath);
        outputpathbuffer = new StringBuffer();
        while (teststring.indexOf(backslash) > -1) {
            outputpathbuffer.append(teststring.substring(0, teststring.indexOf(backslash)));
            outputpathbuffer.append(backslash);
            outputpathbuffer.append(backslash);
            outputpathbuffer.append(backslash);
            outputpathbuffer.append(backslash);
            teststring = teststring.substring(teststring.indexOf(backslash) + 1);
        }
        outputpath = outputpathbuffer.toString();
        for (int i = 0; i <= 20; i++) {
            searchstring = "input" + String.valueOf(i);
            if (Rscript.indexOf(searchstring) >= 0) {
                Rscript = Rscript.replaceAll(searchstring, inputpath + searchstring + ".txt");
            }
            searchstring = "output" + String.valueOf(i);
            if (Rscript.indexOf(searchstring) >= 0) {
                Rscript = Rscript.replaceAll(searchstring, outputpath + searchstring + ".txt");
            }
            searchstring = "bitmap" + String.valueOf(i);
            if (Rscript.indexOf(searchstring) >= 0) {
                Rscript = Rscript.replaceAll(searchstring, outputpath + searchstring + ".bmp");
                cc.P_plot_file = cc.rterm.ReportsBasePath + searchstring + ".bmp";
            }
            searchstring = "jpeg" + String.valueOf(i);
            if (Rscript.indexOf(searchstring) >= 0) {
                Rscript = Rscript.replaceAll(searchstring, outputpath + searchstring + ".jpeg");
                cc.P_plot_file = cc.rterm.ReportsBasePath + searchstring + ".bmp";
            }
        }
        boolean exporttodisksucceeded = exportfiletodisk
                .exportcompletefilename((cc.rterm.RsourceBasePath + Rscriptname + ".R"), Rscript.getBytes());
    }
}

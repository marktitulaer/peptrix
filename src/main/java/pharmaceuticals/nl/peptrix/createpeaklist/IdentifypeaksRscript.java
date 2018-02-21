package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class IdentifypeaksRscript {
    ExportFileToDisk exportfiletodisk;
    Controller cc;
    StringBuffer outputpathbuffer;
    StringBuffer inputpathbuffer;
    String searchstring;
    String outputpath;
    String inputpath;
    String teststring;
    String Rscript;
    char backslash = (char) 92;
    double deltamz;
    double quantilethreshold;
    double deltaxcentroiding;
    double limitshiftcentroiding;
    int ndiv;
    int clusteringtechnique;
    int centroidingmethod;

    public IdentifypeaksRscript(Controller cc) {
        this.cc = cc;
        exportfiletodisk = new ExportFileToDisk(cc);
    }

    public boolean generateRscript(Experiment experiment, String Rscriptname) {
        limitshiftcentroiding = experiment.getmaximal_shift_by_centroiding_ppm();
        quantilethreshold = Double.parseDouble(experiment.getquantilethreshold());
        ndiv = experiment.getdivisions_in_determination_noise();
        clusteringtechnique = experiment.getclusteringtechnique();
        deltamz = Double.parseDouble(experiment.getdelta_mz_searchmaximum());
        deltaxcentroiding = experiment.getwindow_centroiding_ppm();
        centroidingmethod = experiment.getmethod_peak_centroiding();
        Rscript = " ################################################################################################"
                + cc.linefeed + " # Function to determine weighted mean" + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " weightedmean <- function(y,x) {" + cc.linefeed
                + "   weightedmean <- sum(y * x)/ sum(y)" + cc.linefeed + "   c(weightedmean)" + cc.linefeed + " }"
                + cc.linefeed + "" + cc.linefeed + "" + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " # Plots a part of the spectrum " + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " plotSpectrum <- function(x, y, base, noisecutoff, maxes, xlow=1, xhigh=length(y)) {"
                + cc.linefeed + "   yselected = y[xlow:xhigh]" + cc.linefeed + "   ylims <- c(0, max(yselected) * 1.2) "
                + cc.linefeed
                + "   plot(x[xlow:xhigh], y[xlow:xhigh], type=\"l\",col=\"blue\",ylim=ylims,xlab=\"Mass over charge (m/z)\",ylab=\"Normalized intensity\"); "
                + cc.linefeed + "   lines(x[xlow:xhigh],base[xlow:xhigh],pch=2,col=\"green\")" + cc.linefeed
                + "   lines(x[xlow:xhigh],noisecutoff[xlow:xhigh],pch=2,col=\"red\")" + cc.linefeed
                + "   points(maxes$x, maxes$y, pch=2, col=\"black\")" + cc.linefeed
                + "   scrollinstructions <- \"enter keys at [ ]: + = zoom in , - = zoom out, f = forward, b = backward, q = quit\""
                + cc.linefeed + "   mtext(side=3,scrollinstructions, col=\"red\")" + cc.linefeed
                + "   if ( x[xhigh] -  x[xlow] < 2){" + cc.linefeed
                + "     points(maxes$centroided, maxes$y, pch=2, col=\"red\")" + cc.linefeed
                + "     text(maxes$x, maxes$y,maxes$x,pos=4,srt=90)" + cc.linefeed
                + "     centroidedtext <- paste((round(1E8 * (maxes$centroided - maxes$x)/maxes$x) / 100), \" ppm\")"
                + cc.linefeed + "     text(maxes$centroided, maxes$y, centroidedtext  ,pos=2,srt=90, col=\"red\")"
                + cc.linefeed + "     arrows(maxes$min,0,maxes$min,maxes$y,code=0,col=\"red\")" + cc.linefeed
                + "     arrows(maxes$max,0,maxes$max,maxes$y,code=0,col=\"red\")" + cc.linefeed + "   }else{"
                + cc.linefeed + "     limitslabel <- min(yselected) + 0.5 * (max(yselected) - min(yselected))"
                + cc.linefeed + "     selectlabels <- findlabelsabovelimit(maxes,limitslabel)" + cc.linefeed
                + "     text(selectlabels$x, selectlabels$y,selectlabels$x,pos=4)" + cc.linefeed + "   }" + cc.linefeed
                + " }" + cc.linefeed + "" + cc.linefeed + " findlabelsabovelimit <- function(maxes,limitslabel) {"
                + cc.linefeed + "   maxesselectedx <- 1:0" + cc.linefeed + "   maxesselectedy <- 1:0" + cc.linefeed
                + "   nfound <- 0" + cc.linefeed + "   for (i in 1:length(maxes$y)) {" + cc.linefeed
                + "     if (maxes$y[i] < limitslabel) next" + cc.linefeed + "     nfound <- nfound + 1" + cc.linefeed
                + "     maxesselectedx[nfound] <- maxes$x[i]" + cc.linefeed
                + "     maxesselectedy[nfound] <- maxes$y[i] " + cc.linefeed + "   }" + cc.linefeed
                + "   list(x=maxesselectedx,y=maxesselectedy)" + cc.linefeed + " } " + cc.linefeed + "" + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " # Allows plotting and zooming in on a spectrum" + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " studySpectrum <- function(x,y, base, noisecutoff, maxes) {" + cc.linefeed
                + "   i <- 0" + cc.linefeed + "   xlo <- 1" + cc.linefeed + "   xhi <- length(y)" + cc.linefeed
                + "   repeat {" + cc.linefeed + "     if (xlo < 1) xlo <- 1" + cc.linefeed
                + "     if (xhi > length(y)) xhi <- length(y)" + cc.linefeed + "     window <- xhi - xlo" + cc.linefeed
                + "     halfwindow <- round(window/2)" + cc.linefeed
                + "     plotSpectrum(x,y,base,noisecutoff,maxes,xlo,xhi)" + cc.linefeed + "     i <- i + 1"
                + cc.linefeed + "     p <- paste(\"[\",i,\"]\",sep=\"\")" + cc.linefeed
                + "     answer <- readline(prompt=p)" + cc.linefeed
                + "     if ((answer == \"q\") || (answer == \"quit\")) break" + cc.linefeed
                + "     if (answer == \"+\") {" + cc.linefeed + "       delta <- round(window / 4)" + cc.linefeed
                + "       xlo <- xlo + delta" + cc.linefeed + "       xhi <- xhi - delta" + cc.linefeed + "     }"
                + cc.linefeed + "     if (answer == \"-\") {" + cc.linefeed + "       xlo <- xlo - halfwindow"
                + cc.linefeed + "       xhi <- xhi + halfwindow" + cc.linefeed + "     }" + cc.linefeed
                + "     if (answer == \"f\") {" + cc.linefeed + "       xlo <- xlo + halfwindow" + cc.linefeed
                + "       xhi <- xhi + halfwindow" + cc.linefeed + "     }" + cc.linefeed
                + "     if (answer == \"b\") {" + cc.linefeed + "       xhi <- xhi - halfwindow" + cc.linefeed
                + "       xlo <- xlo - halfwindow" + cc.linefeed + "     }" + cc.linefeed + "   }" + cc.linefeed + " }"
                + cc.linefeed + " " + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " # Finds and returns the local maxima in a list." + cc.linefeed
                + " # Checks if all surrounding points are lower than the local maximum " + cc.linefeed
                + " # and if the local maximum is higher than a noise cutoff." + cc.linefeed
                + " # If deltaxIsRelative == 1 then it is assumed that deltax is given in ppm." + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed
                + " findLocalMaxima <- function(x, y, deltax, base, noise, cutoff, deltaxIsRelative,z,deltaxcentroiding,centroidingmethod,limitshiftcentroiding) {"
                + cc.linefeed + "   deltaxcentroidinglocal <- deltaxcentroiding" + cc.linefeed
                + "   limitshiftcentroidinglocal <- limitshiftcentroiding" + cc.linefeed + "   localMaxes <- 1:0"
                + cc.linefeed + "   xmaxes <- 1:0" + cc.linefeed + "   ymaxes <- 1:0" + cc.linefeed
                + "   noisemasses <- 1:0" + cc.linefeed + "   noiseintensities <- 1:0" + cc.linefeed
                + "   snmaxes <- 1:0" + cc.linefeed + "   indexmaxes <- 1:0" + cc.linefeed + "   nfound <- 0"
                + cc.linefeed + "   nfoundnoises <- 0" + cc.linefeed + "   centroidedmaxes <- 1:0" + cc.linefeed
                + "   centroidmin <- 1:0" + cc.linefeed + "   centroidmax <- 1:0" + cc.linefeed + "   lookleft <- 1"
                + cc.linefeed + "   for (i in 1:length(y)) {" + cc.linefeed + "     if (y[i] < base[i]) next"
                + cc.linefeed + "     threshold <- base[i] + cutoff*noise[i]" + cc.linefeed + "     delta <- deltax"
                + cc.linefeed + "     if (deltaxIsRelative==1) {" + cc.linefeed + "       delta <- x[i] * deltax * 1E-6"
                + cc.linefeed + "     }  " + cc.linefeed + "  if (nfound > 1){ " + cc.linefeed
                + "  if (x[i] - xmaxes[nfound] < delta) next " + cc.linefeed + "  } " + cc.linefeed + "     valid <- 1"
                + cc.linefeed + "     if (lookleft == 1){" + cc.linefeed + "       j <- 0" + cc.linefeed
                + "       repeat {" + cc.linefeed + "         j <- j + 1" + cc.linefeed + "         left  <- i - j"
                + cc.linefeed + "         if (left <= 0) break" + cc.linefeed
                + "         if (x[i] - x[left] > delta) break" + cc.linefeed + "         if (y[left] > y[i]) {"
                + cc.linefeed + "          valid <- 0; # there is a higher value on the left " + cc.linefeed
                + "            if (j > 1) { " + cc.linefeed + "    left2 <- left - 1 " + cc.linefeed
                + "    if (left2 > 0){ " + cc.linefeed + "     if (y[left2] > y[left]) { " + cc.linefeed
                + "       valid <- 1 " + cc.linefeed + "     } " + cc.linefeed + "     } " + cc.linefeed + "    } "
                + cc.linefeed + "    if (valid == 0){ " + cc.linefeed + "     break; " + cc.linefeed + "      } "
                + cc.linefeed + "         }" + cc.linefeed + "       }" + cc.linefeed + "     }" + cc.linefeed
                + "     if (lookleft == 0){" + cc.linefeed + "	 lookleft <- 1" + cc.linefeed + "     }" + cc.linefeed
                + "     if (valid == 1) {" + cc.linefeed + "       j <- 0" + cc.linefeed + "       repeat {"
                + cc.linefeed + "         j <- j + 1" + cc.linefeed + "         right <- i + j" + cc.linefeed
                + "         if (right > length(y)) break" + cc.linefeed + "         if (x[right] - x[i] > delta) break"
                + cc.linefeed + "         if (y[right] > y[i]) {" + cc.linefeed
                + "            valid <- 0; # there is a higher value on the right" + cc.linefeed
                + "            if (j > 1) { " + cc.linefeed + "            right2 <- right + 1" + cc.linefeed
                + "            if (right2 < length(y)){" + cc.linefeed + "              if (y[right2] > y[right]) {"
                + cc.linefeed + "               valid <- 1" + cc.linefeed + "             }" + cc.linefeed
                + "            }" + cc.linefeed + "            }" + cc.linefeed + "            if (valid == 0){"
                + cc.linefeed + "             i <- right - 1" + cc.linefeed + "             lookleft <- 0" + cc.linefeed
                + "             break;" + cc.linefeed + "            }" + cc.linefeed + "          }" + cc.linefeed
                + "       }" + cc.linefeed + "     }" + cc.linefeed + "     if (valid == 1) {" + cc.linefeed
                + "       new_i <- right - 1" + cc.linefeed + "       if (y[i] < threshold) {" + cc.linefeed
                + "         nfoundnoises = nfoundnoises + 1" + cc.linefeed
                + "         noisemasses[nfoundnoises] <- x[i]" + cc.linefeed
                + "         noiseintensities[nfoundnoises] <- y[i] " + cc.linefeed + "	   i <- right - 1" + cc.linefeed
                + "         next" + cc.linefeed + "       }" + cc.linefeed + "       nfound <- nfound + 1" + cc.linefeed
                + "       xmaxes[nfound] <- x[i]" + cc.linefeed + "       centroidedmaxes[nfound] <- x[i]" + cc.linefeed
                + "       ymaxes[nfound] <- y[i] " + cc.linefeed + "       indexmaxes[nfound] <- z[i] " + cc.linefeed
                + "       snmaxes[nfound] <- (y[i]-base[i])/noise[i]" + cc.linefeed + "       centroidpoints_x <- 1:0"
                + cc.linefeed + "       centroidpoints_y <- 1:0" + cc.linefeed + "       if (centroidingmethod > 0){"
                + cc.linefeed + "         if (deltaxIsRelative==1) {" + cc.linefeed
                + "           deltaxcentroidinglocal <- xmaxes[nfound] * deltaxcentroiding * 1E-6" + cc.linefeed
                + "           limitshiftcentroidinglocal <- xmaxes[nfound] * limitshiftcentroiding * 1E-6" + cc.linefeed
                + "         }  " + cc.linefeed + "         centroidpoints_counts <- 1" + cc.linefeed
                + "         if ((y[i]-base[i]) > 0){" + cc.linefeed
                + "           centroidpoints_x[centroidpoints_counts] <- x[i]" + cc.linefeed
                + "           centroidpoints_y[centroidpoints_counts] <- (y[i]-base[i])" + cc.linefeed
                + "         } else {" + cc.linefeed + "           centroidpoints_x[centroidpoints_counts] <- x[i]"
                + cc.linefeed + "           centroidpoints_y[centroidpoints_counts] <- 0" + cc.linefeed + "         }"
                + cc.linefeed + "         centroidmin[nfound] <- x[i]" + cc.linefeed
                + "         centroidmax[nfound] <- x[i]" + cc.linefeed + "         j <- 0" + cc.linefeed
                + "         repeat {" + cc.linefeed + "           j <- j + 1" + cc.linefeed
                + "           left  <- i - j" + cc.linefeed + "           if (left <= 0) { " + cc.linefeed
                + "             centroidmin[nfound] <- x[1]" + cc.linefeed + "             break " + cc.linefeed
                + "           }           " + cc.linefeed + "           if (x[i] - x[left] > deltaxcentroidinglocal) {"
                + cc.linefeed + "             centroidmin[nfound] <- x[left]" + cc.linefeed + "             break"
                + cc.linefeed + "           }" + cc.linefeed
                + "           centroidpoints_counts <- centroidpoints_counts + 1" + cc.linefeed
                + "           if ((y[left]-base[left]) > 0){" + cc.linefeed
                + "             centroidpoints_x[centroidpoints_counts] <- x[left]" + cc.linefeed
                + "             centroidpoints_y[centroidpoints_counts] <- (y[left] - base[left])" + cc.linefeed
                + "           } else {" + cc.linefeed
                + "             centroidpoints_x[centroidpoints_counts] <- x[left]" + cc.linefeed
                + "             centroidpoints_y[centroidpoints_counts] <- 0" + cc.linefeed + "           }"
                + cc.linefeed + "         }" + cc.linefeed + "         j <- 0" + cc.linefeed + "         repeat {"
                + cc.linefeed + "           j <- j + 1" + cc.linefeed + "           right <- i + j" + cc.linefeed
                + "           if (right > length(y)) { " + cc.linefeed
                + "             centroidmax[nfound] <- x[length(y)]" + cc.linefeed + "             break " + cc.linefeed
                + "           }" + cc.linefeed + "           if (x[right] - x[i] > deltaxcentroidinglocal) {"
                + cc.linefeed + "             centroidmax[nfound] <- x[right]" + cc.linefeed + "             break"
                + cc.linefeed + "           }" + cc.linefeed
                + "           centroidpoints_counts <- centroidpoints_counts + 1" + cc.linefeed
                + "           if ((y[right]-base[right]) > 0){" + cc.linefeed
                + "             centroidpoints_x[centroidpoints_counts] <- x[right]" + cc.linefeed
                + "             centroidpoints_y[centroidpoints_counts] <- (y[right] - base[right])" + cc.linefeed
                + "           } else {" + cc.linefeed
                + "             centroidpoints_x[centroidpoints_counts] <- x[right]" + cc.linefeed
                + "             centroidpoints_y[centroidpoints_counts] <- 0" + cc.linefeed + "           }"
                + cc.linefeed + "         }" + cc.linefeed
                + "         centroidedmaxes[nfound] =  weightedmean (centroidpoints_y,centroidpoints_x)" + cc.linefeed
                + "         if (abs(centroidedmaxes[nfound] - xmaxes[nfound]) > limitshiftcentroidinglocal) {"
                + cc.linefeed + "           centroidedmaxes[nfound] <- xmaxes[nfound]" + cc.linefeed + "         }"
                + cc.linefeed + "       }" + cc.linefeed + "       i <- new_i" + cc.linefeed + "     }" + cc.linefeed
                + "   }" + cc.linefeed
                + "   list(x=xmaxes, y=ymaxes, sn=snmaxes, index=indexmaxes, centroided=centroidedmaxes,min=centroidmin,max=centroidmax,xnoise=noisemasses,ynoise=noiseintensities)"
                + cc.linefeed + " } " + cc.linefeed + " " + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " # Fit function for the background noise, currently Gaussian" + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " fitFunction <- function(z, ampl, mu, sigma) {" + cc.linefeed
                + "   zz <- exp( -1 * (z - mu)*(z - mu) / (2*sigma*sigma))" + cc.linefeed + "   zz <- ampl * zz / sigma"
                + cc.linefeed + "   zz " + cc.linefeed + " }" + cc.linefeed + " " + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " # Function to determine the noise level" + cc.linefeed
                + " ################################################################################################"
                + cc.linefeed + " noiselevel <- function(y) {" + cc.linefeed
                + "   limits <- quantile(y,probs=c(0.02,0.96))" + cc.linefeed + "   ysel <- c()" + cc.linefeed
                + "   for (i in 1:length(y)) {" + cc.linefeed
                + "     if (y[i] > 0 && y[i] > limits[1] && y[i] < limits[2]) {      " + cc.linefeed
                + "       ysel <- c(ysel,y[i])" + cc.linefeed + "     }" + cc.linefeed + "   }" + cc.linefeed
                + "   h <- hist(ysel, br=50, plot=FALSE) # Use plot=FALSE to turn off plotting" + cc.linefeed
                + "   maxIndex <- which.max(h$counts)" + cc.linefeed + "   m <- h$mids[maxIndex]" + cc.linefeed
                + "   stdev <- sd(ysel)" + cc.linefeed + "   amplStart <- stdev * max(h$counts)" + cc.linefeed
                + "   xpts <- h$mids" + cc.linefeed + "   ypts <- h$counts" + cc.linefeed + "   tryCatch({"
                + cc.linefeed
                + "     fitresult <- nls(ypts ~ fitFunction(xpts, ampl, mu, sigma), start = list(ampl=amplStart, mu=m, sigma=stdev), trace=FALSE)"
                + cc.linefeed + "     params <- fitresult$m$getPars()" + cc.linefeed + "     ampl <- params[1]"
                + cc.linefeed + "     m <- params[2]" + cc.linefeed + "     stdev <- params[3]" + cc.linefeed
                + "   }, error = function(ex) {" + cc.linefeed + "     #cat(\"An error was detected.\")" + cc.linefeed
                + "     #print(ex)" + cc.linefeed + "   })" + cc.linefeed + "   c(m , stdev)" + cc.linefeed + " }"
                + cc.linefeed + " " + cc.linefeed
                + " ########################################################################" + cc.linefeed
                + " # Smooth the baseline and noise levels between the different intervals" + cc.linefeed
                + " ########################################################################" + cc.linefeed
                + " smootheNoiselevel <- function(intervalCenters, bases, noises) {" + cc.linefeed
                + "   basesSmoothed <- smooth(bases)" + cc.linefeed + "   noisesSmoothed <- smooth(noises)"
                + cc.linefeed + "   baseFun <- splinefun(intervalCenters, basesSmoothed)" + cc.linefeed
                + "   noiseFun <- splinefun(intervalCenters, noisesSmoothed)" + cc.linefeed
                + "   list(baseFun=baseFun, noiseFun=noiseFun)" + cc.linefeed + " }" + cc.linefeed + " " + cc.linefeed
                + " ########################################################################" + cc.linefeed
                + " # Divide the spectrum into a number of intervals for which the baseline" + cc.linefeed
                + " # and noise level are determined" + cc.linefeed
                + " # ndiv  : number of intervals into which the spectrum is divided" + cc.linefeed
                + " # cutoff: noise level cutoff in sigma's  " + cc.linefeed + " # deltax: definition of local maximum"
                + cc.linefeed + " ########################################################################"
                + cc.linefeed
                + " identifyPeaks <- function(x, y, ndiv, cutoff, deltax, deltaxIsRelative,z,deltaxcentroiding,centroidingmethod,displayspectrum,limitshiftcentroiding) {"
                + cc.linefeed + "   # divide the spectrum in intervals and determine the baseline and " + cc.linefeed
                + "   # noise level for each interval" + cc.linefeed + "   noisecutoff <- rep(0,length(y))"
                + cc.linefeed + "   width <- round(length(y)/ndiv)" + cc.linefeed + "   nlPrevious <- c(NA,NA)"
                + cc.linefeed + "   bases <- rep(0,ndiv)" + cc.linefeed + "   noises <- rep(0,ndiv)" + cc.linefeed
                + "   intervalCenters <- rep(0,ndiv)" + cc.linefeed + "   index2 <- 1" + cc.linefeed
                + "   for (i in 1:ndiv) {" + cc.linefeed + "     index1 <- index2" + cc.linefeed
                + "     index2 <- index1 + width" + cc.linefeed + "     index2 <- min(index2, length(y))" + cc.linefeed
                + "     nl <- noiselevel(y[index1:index2])" + cc.linefeed + "     bases[i] <- nl[1]  " + cc.linefeed
                + "     noises[i] <- nl[2]" + cc.linefeed + "     intervalCenters[i] <- (x[index1] + x[index2])/2"
                + cc.linefeed + "   }" + cc.linefeed + " " + cc.linefeed
                + "   # Smooth the baseline and noise levels between the different intervals" + cc.linefeed
                + "   smoothed <- smootheNoiselevel(intervalCenters, bases, noises)" + cc.linefeed
                + "   base <- smoothed$baseFun(x)" + cc.linefeed + "   noise <- smoothed$noiseFun(x)" + cc.linefeed
                + " " + cc.linefeed + "   # Identify all local maxima above a noise cutoff" + cc.linefeed
                + "   maxes <- findLocalMaxima(x, y, deltax, base, noise, cutoff, deltaxIsRelative, z,deltaxcentroiding,centroidingmethod,limitshiftcentroiding)"
                + cc.linefeed + "   noisecutoff <- base + cutoff * noise" + cc.linefeed + " " + cc.linefeed
                + "   if (displayspectrum == \"yes\"){" + cc.linefeed
                + "     studySpectrum(x, y, base, noisecutoff, maxes) # plots the spectrum and results" + cc.linefeed
                + "   }" + cc.linefeed + "   maxes" + cc.linefeed + " }" + cc.linefeed + " " + cc.linefeed
                + " ########################################################################" + cc.linefeed
                + " # Example of how identifyPeaks can be used for a FTMS spectrum" + cc.linefeed
                + " ########################################################################" + cc.linefeed
                + " input <- \"input0\"" + cc.linefeed
                + " spectrum <- scan(input, what=list(x=0.0,y=0.0,z=0.0), sep=\" \") # x are the masses, y are the intensities"
                + cc.linefeed + " ndiv <- " + String.valueOf(ndiv) + cc.linefeed;
        if (quantilethreshold > 0) {
            Rscript = Rscript + " cutoff <- " + String.valueOf(quantilethreshold) + "  " + cc.linefeed;
        } else {
            Rscript = Rscript + " cutoff <- 4" + cc.linefeed;
        }
        if (clusteringtechnique == 1) {
            if (deltamz > 0) {
                Rscript = Rscript + " deltax <- " + String.valueOf(deltamz) + "  # ppm" + cc.linefeed;
            } else {
                Rscript = Rscript + " deltax <- 250 # ppm" + cc.linefeed;
            }
            Rscript = Rscript + " deltaxIsRelative <- 1" + cc.linefeed;
        } else {
            if (deltamz > 0) {
                Rscript = Rscript + " deltax <- " + String.valueOf(deltamz) + "  # Da" + cc.linefeed;
            } else {
                Rscript = Rscript + " deltax <- 0.5 # Da" + cc.linefeed;
            }
            Rscript = Rscript + " deltaxIsRelative <- 2" + cc.linefeed;
        }
        Rscript = Rscript + " deltaxcentroiding <- " + String.valueOf(deltaxcentroiding) + "  # ppm" + cc.linefeed
                + " limitshiftcentroiding <- " + String.valueOf(limitshiftcentroiding) + " # ppm" + cc.linefeed
                + " centroidingmethod <- " + String.valueOf(centroidingmethod).trim() + "  # 0 = no centroiding"
                + cc.linefeed + " displayspectrum <- \"no\" # \"yes\"" + cc.linefeed + " " + cc.linefeed + " "
                + cc.linefeed + " ########################################################################"
                + cc.linefeed + " # Do the work, identify the peaks" + cc.linefeed
                + " ########################################################################" + cc.linefeed
                + " maxy <- max(spectrum$y)" + cc.linefeed + " spectrum$y <- spectrum$y / maxy" + cc.linefeed
                + " peaks <- identifyPeaks(spectrum$x, spectrum$y, ndiv, cutoff, deltax, deltaxIsRelative,spectrum$z,deltaxcentroiding,centroidingmethod, displayspectrum, limitshiftcentroiding)"
                + cc.linefeed + " peaks$y <- peaks$y * maxy" + cc.linefeed + " peaks$ynoise <- peaks$ynoise * maxy"
                + cc.linefeed + " " + cc.linefeed + " filepeakmasses <- \"output0\"" + cc.linefeed
                + " filepeakintensities <- \"output1\"" + cc.linefeed + " filepeakindexes <- \"output2\"" + cc.linefeed
                + " filepeaksbelownoiselevel <- \"output3\"" + cc.linefeed + " noiseintensities <- \"output4\""
                + cc.linefeed + " " + cc.linefeed + "options(digits=11) " + cc.linefeed
                + " if (centroidingmethod == 0){" + cc.linefeed
                + "   write(peaks$x, file = filepeakmasses,ncolumns = 1,append = FALSE)" + cc.linefeed + " }"
                + cc.linefeed + " if (centroidingmethod == 1){" + cc.linefeed
                + "   write(peaks$centroided, file = filepeakmasses,ncolumns = 1,append = FALSE)" + cc.linefeed + " }"
                + cc.linefeed + " write(peaks$y, file = filepeakintensities,ncolumns = 1,append = FALSE)" + cc.linefeed
                + " write(peaks$index, file = filepeakindexes,ncolumns = 1,append = FALSE)" + cc.linefeed
                + " write(peaks$xnoise, file = filepeaksbelownoiselevel,ncolumns = 1,append = FALSE)" + cc.linefeed
                + " write(peaks$ynoise, file = noiseintensities,ncolumns = 1,append = FALSE)";
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
        }
        boolean exporttodisksucceeded = exportfiletodisk
                .exportcompletefilename((cc.rterm.RsourceBasePath + Rscriptname), Rscript.getBytes());
        return exporttodisksucceeded;
    }
}

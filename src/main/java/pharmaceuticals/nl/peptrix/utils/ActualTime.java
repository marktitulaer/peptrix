package pharmaceuticals.nl.peptrix.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ActualTime {

    private GregorianCalendar gc;

    private String day;

    private String month;

    private String year;

    private String hour;

    private String minutes;

    private String seconds;

    private String datestring;

    private String timestring;

    private int intyear;

    public void resettime() {
        gc = new GregorianCalendar();
        fillvariableswithCalendarvalues();
    }

    public void setTimeInMillis(long lastmod) {
        gc = new GregorianCalendar();
        gc.setTimeInMillis(lastmod);
        fillvariableswithCalendarvalues();
    }

    private void fillvariableswithCalendarvalues() {
        day = String.valueOf(gc.get(Calendar.DAY_OF_MONTH)).trim();
        month = String.valueOf((gc.get(Calendar.MONTH) + 1)).trim();
        intyear = gc.get(Calendar.YEAR);
        if (intyear < 1000) {
            intyear = (intyear + 1900);
        }
        year = String.valueOf(intyear).trim();
        hour = String.valueOf(gc.get(Calendar.HOUR_OF_DAY)).trim();
        minutes = String.valueOf(gc.get(Calendar.MINUTE)).trim();
        seconds = String.valueOf(gc.get(Calendar.SECOND)).trim();
    }

    public String getdatestring() {
        datestring = year + "-" + month + "-" + day;
        return datestring;
    }

    public String gettimestring() {
        timestring = hour + ":" + minutes + ":" + seconds;
        return timestring;
    }

    public String getyear() {
        return year;
    }

    public String gethour() {
        return hour;
    }

    public String getminutes() {
        return minutes;
    }

    public String getseconds() {
        return seconds;
    }

}

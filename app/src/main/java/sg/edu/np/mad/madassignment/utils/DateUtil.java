package sg.edu.np.mad.madassignment.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat newDateFormat = new SimpleDateFormat("MM/dd/yy");

    //get current time stamp .............................................................
    public static String getCurrentTimeStamp(){
        try {
            String currentDateTime = newDateFormat.format(new Date()); // Find todays date
            Log.d("current time :",""+currentDateTime);
            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //get current date .................................................................
    public static String getCurrentDate(){
        try {
            String currentDateTime = currentDateFormat.format(new Date()); // Find todays date
            Log.d("current time :",""+currentDateTime);
            return currentDateTime + " 17:30:00";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //get current date .................................................................
    public static String getOneDayAfterDate(){
        try {
            String currentDateTime = currentDateFormat.format(new Date()); // Find todays date
            Calendar c = Calendar.getInstance();
            Date date1 = currentDateFormat.parse(currentDateTime);
            c.setTime(date1);
            c.add(Calendar.DATE, 1);
            String oneDayAfterDate = currentDateFormat.format(c.getTime());
            Log.d("one day after date :",""+oneDayAfterDate);
            return oneDayAfterDate + " 17:30:00";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //difference between current and after date..........................................
    public static String getDiffBetTwoDate(String timestamp,String afterDate){
        try {
//            Calendar c = Calendar.getInstance();
//            Date date1 = dateFormat.parse(timestamp);
//            c.setTime(date1);
//            c.add(Calendar.DATE, 30);
//            String thirtyDayDate = dateFormat.format(c.getTime());
            Date date2 = newDateFormat.parse(timestamp);
            Date date3 = newDateFormat.parse(afterDate);
            long difference = Math.abs(date2.getTime() - date3.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            String dayDifference = Long.toString(differenceDates);
            return dayDifference;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //elapsed time or not.......................................................................
    public static boolean checkTimeElapseOrNot(String expireTimeStamp,String timeStamp){
        try {
            Date date2 = newDateFormat.parse(timeStamp);
            Date date3 = newDateFormat.parse(expireTimeStamp);
            return date2.after(date3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

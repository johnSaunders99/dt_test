package cn.gzsunrun.downtime.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttDateUtil {

    // 获得本周第一天0点时间
    public static Date getWeekMorning(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);

        //设置周日为第一天
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        //获取第一天
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        return cal.getTime();
    }
    // 获得本周第一天0点时间
    public static Date getWeekNight(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);

        //设置周日为第一天
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        return cal.getTime();
    }

    // 获得本周第一天0点时间
    public static Date getLastWeekMorning(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekMorning(dateStr));
        cal.add(Calendar.WEEK_OF_MONTH,-1);
        return cal.getTime();
    }
    // 获得本周第一天0点时间
    public static Date getLastWeekNight(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekMorning(dateStr));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }


    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }

    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }
    // 获得上月第一天0点时间
    public static Date getLastTimesMonthmorning(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.add(Calendar.MONTH,-1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    // 获得上月最后一天24点时间
    public static Date getLastTimesMonthnight(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.add(Calendar.MONTH,-1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    public static Date getCurrentQuarterStartTime(Date dateStr) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateStr);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime(dateStr));
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }

    //上个季度
    public static Date getLastCurrentQuarterStartTime(Date dateStr) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateStr);
        //往前一个季度
        c.add(Calendar.MONTH,-3);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static Date getLastCurrentQuarterEndTime(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime(dateStr));
        return cal.getTime();
    }


    public static Date getCurrentYearStartTime(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.YEAR));
        return cal.getTime();
    }
    public static Date getCurrentYearEndTime(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime(dateStr));
        cal.add(Calendar.YEAR, 1);
        return cal.getTime();
    }

    public static Date getLastCurrentYearStartTime(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.YEAR));
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }
    public static Date getLastCurrentYearEndTime(Date dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentYearStartTime(dateStr));
        return cal.getTime();
    }
}

package seed.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 获取两个时间差,单位秒
     */
    public static long getDuration(Date d1,Date d2){
        long seconds=0L;
        try
        {
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            seconds = diff / 1000;
        }catch (Exception e)
        {
        }finally {
            return seconds;
        }

    }
}

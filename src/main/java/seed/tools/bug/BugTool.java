package seed.tools.bug;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BugTool {
    /**
     * 严重程度
     * @param severity
     * @return
     */
    public static String toSeverity(String severity){
        if("功能改进".equals(severity)){
            return 1+"";
        }

        if("普通问题".equals(severity)){
            return 2+"";
        }

        if("功能缺失".equals(severity)){
            return 3+"";
        }

        if("严重奔溃".equals(severity)){
            return 4+"";
        }

        if("阻碍进度".equals(severity)){
            return 5+"";
        }

        return 0+"";
    }

    /**
     * 严重程度
     * @param priority
     * @return
     */
    public static String toPriority(String priority){
        if("低".equals(priority)){
            return 1+"";
        }

        if("一般".equals(priority)){
            return 2+"";
        }

        if("高".equals(priority)){
            return 3+"";
        }

        if("紧急".equals(priority)){
            return 4+"";
        }

        return 0+"";
    }

    /**
     * 开始时间
     * @param time
     * @return
     */
    public static Date toTime(String time) {
        try {
            String[] times = time.split(" ");
            String[] timess = times[0].split("/");
            String m = Integer.parseInt(timess[1])<10?0+timess[1]:timess[1];
            String d = Integer.parseInt(timess[0])<10?0+timess[0]:timess[0];
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            Date date = sdf.parse(timess[2]+"-"+m+"-"+d+" "+times[1]);
            return date;
        }catch (Exception e){
            return null;
        }

    }
}

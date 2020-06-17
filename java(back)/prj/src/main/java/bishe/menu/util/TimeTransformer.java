package bishe.menu.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeTransformer {
    public Long getNowTimeStamp(){
        long time = System.currentTimeMillis();
        time/=1000;
        return time;
    }

    public String getFormatTime(String TimeStamp){
        String format = "yyyy-MM-dd HH:mm:ss";
        Long time = Long.parseLong(TimeStamp)*1000;
        String date = new SimpleDateFormat(format, Locale.CHINA).format(new Date(time));
        return date;
    }
}

package core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author estuche
 */
public class DateTime {
    
    public static String now() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }
    
}

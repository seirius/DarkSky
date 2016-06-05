package darksky.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MyUtil {

	//Comprueba si es nulo, vacio o un espacio
	public static boolean isEmpty(String string) {
		if (string == null || string.length() == 0 || string.equals(" ")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Date getCurrentDate() {
		Date today = Calendar.getInstance().getTime();        
		return today;
	}
	
	/**
	 * Formatea una fecha dado un formato (Ejemplo: 'dd-MM-yyyy HH:mm:ss')
	 * 
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static String getFechaFormateada(Date fecha, String formato) {
		String oldString = fecha.toString();
		LocalDateTime dateTime = LocalDateTime.parse(oldString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
		String newString = dateTime.format(DateTimeFormatter.ofPattern(formato));
		return newString;
	}
}

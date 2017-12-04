package net.eoutech.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat sf;
	
	public static Date getCurrentTime(){
		return new Date();
	}
	
	public static Date getCurrentDate () {
		sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(sf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getCurrentDateString () {
		sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(new Date());
	}
	
	public static String getCurrentTimeString () {
		sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}
	
	public static String getStringDate (long time) {
		sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date(time));
	}
	
	public static String timesUpdate ( int minutes ) {
		SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( new Date() );
		calendar.add( Calendar.MINUTE, minutes );
		return sf.format( calendar );
	}
	
	public static boolean isRealTime (Timestamp time,long addTime) {
		long timel = time.getTime() + addTime;
		long nowl = new Date().getTime();
		if(nowl > timel){
			return true;
		}
		return false;
	}
	
	public static boolean isRealcheckTime (Timestamp checkTime) {
		long checkTimel = checkTime.getTime();
		long nowl = new Date().getTime();
		if (nowl <= checkTimel) {
			return true;
		}
		return false;
	}
	
	public static boolean checkSubscribeTime (Date subscribeTime) {
		
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String beginTime = dateStr + " 00:00:00";
		String endTime = dateStr + " 23:59:59";
		
		Timestamp from = Timestamp.valueOf( beginTime );
		Timestamp to = Timestamp.valueOf( endTime );
		
		long froml = from.getTime();
		long tol = to.getTime();
		
		if(subscribeTime != null){
			long subl = subscribeTime.getTime();
			if (froml <= subl && subl <= tol) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static boolean checkSubscribeTime (Timestamp subscribeTime) {
		
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String beginTime = dateStr + " 00:00:00";
		String endTime = dateStr + " 23:59:59";
		
		Timestamp from = Timestamp.valueOf( beginTime );
		Timestamp to = Timestamp.valueOf( endTime );
		
		long froml = from.getTime();
		long tol = to.getTime();
		
		if(subscribeTime != null){
			long subl = subscribeTime.getTime();
			if (froml <= subl && subl <= tol) {
				return true;
			}
		}
		
		return false;
		
	}
}

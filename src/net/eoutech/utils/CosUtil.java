package net.eoutech.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class CosUtil {

	public static String myExceptionString(Exception e) {
		String myexception = "Exception:" + e.toString();
		StackTraceElement[] stack = e.getStackTrace();
		for (int i = 0; i < 6 && i < stack.length; i++) {
			String firstStack = stack[i].toString();
			int pos = firstStack.indexOf('(');
			myexception += (pos > 0) ? firstStack.substring(pos)
					: (" " + firstStack);
		}

		return myexception;
	}

	public static String time2date(long unixTimestamp) {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		return dateformat.format(unixTimestamp * 1000);
	}

	public static String nowdate() {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		return dateformat.format(new Date());
	}

	public static String nowdatetime() {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat.format(new Date());
	}

	public static String afterNowTime(int secs) {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		long time = date.getTime();
		time = time + secs * 1000;
		date.setTime(time);
		return dateformat.format(date);
	}

	public static String nowdatetimestr() {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		return dateformat.format(new Date());
	}
	
	
	public static String buildRandomStr(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String buildRandomNum(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// �ж��ַ��Ƿ�Ϊ��
	public static boolean validateEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		}
		return false;
	}

	// Md5�����㷨
	public static String getEncryptByMD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			String byteMd5 = Integer.toHexString(byteArray[i] & 0xFF);
			if (byteMd5.length() == 1) {
				stringBuffer.append("0").append(byteMd5);
			} else {
				stringBuffer.append(byteMd5);
			}
		}
		return stringBuffer.toString();
	}

	// ��ʽ��ʱ��
	public static String dateFormat(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	// ���ַ�תΪʱ��
	public static Date parseDate(String string, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date = sdf.parse(string);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// �ַ����ַ��г��ִ���
	public static int getNumberOf(String str, char c) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			char bit = str.charAt(i);
			if (bit == c) {
				count++;
			}
		}
		return count;
	}

	public static int exsit(String[] values, String value) {
		if (value == null || values == null) {
			return -1;
		}
		for (int i = 0; i < values.length; i++) {
			String string = values[i];
			if (value.equals(string.trim())) {
				return i;
			}
		}
		return -1;
	}

	public static int indexOfCaseInsensitive(String string, String key, int from) {
		if (validateEmpty(string) || validateEmpty(key)) {
			return -1;
		}
		if (from < 0 || from > string.length() - 1) {
			throw new RuntimeException("out of bound[" + from + "]");
		}

		for (int i = from; i < string.length(); i++) {
			boolean b = true;
			for (int j = 0; j < key.length(); j++) {
				char c = string.charAt(i + j);
				if (Character.toLowerCase(c) != Character.toLowerCase(key
						.charAt(j))) {
					b = false;
					break;
				}
			}
			if (b) {
				return i;
			}
		}
		return -1;
	}
	
	public static String ifnull(String source, String dest){
		if (source != null) {
			return source;
		}else {
			return dest;
		}
	}
	
	public static boolean moveFile(File file, File destDir){
		if (!destDir.isDirectory()) {
			return false;
		}
		if (!destDir.exists()) {
			boolean b = destDir.mkdirs();
			if (!b) {
				throw new RuntimeException("server create directory[" + destDir.getAbsolutePath() + "] failed. please create it");
			}
		}
		
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(file);
			output = new FileOutputStream(new File(destDir, file.getName()));
			IOUtils.copy(input, output);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
			file.delete();
		}
		return false;
	}
	
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static String getValue(String value, String paramName){            //values=class:OriginateAction|setActionID:(k1,v1,k2,v2,k3,v3)
		int start = value.indexOf(paramName+":");
		if (start == -1) {
			return null;
		}
		int end = value.indexOf("|", start) == -1 ? value.length() : value.indexOf("|", start);
		return value.substring(start + 6, end);
	}
	
	public static Object getValue(String value, int pos){
		int count = 0;
		for (int i = 1; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == ',') {
				continue;
			}
			
			if (value.charAt(i) == '{') {
				int end = value.indexOf("}", i);
				if (count == pos) {
					String tmp = value.substring(i+1, end);
					String[] arr = tmp.split(",");
					Map map = new HashMap();
					for (int j = 0; j < arr.length; j+=2) {
						Object kv = null;
						if (j+1 < arr.length) {
							kv = arr[j+1];
						}
						map.put(arr[j], kv);
					}
					return map;
				}
				
				i = end+1;
			}else if(value.charAt(i) == '['){
				int end = value.indexOf("]", i);
				if (count == pos) {
					String tmp = value.substring(i+1, end);
					String[] arr = tmp.split(",");
					return Arrays.asList(arr);
				}
				i = end+1;
			}else {
				int end = value.indexOf(",", i);
				end = end == -1 ? value.length()-1 : end;
				if (count == pos) {
					String tmp = value.substring(i, end);
					return tmp;
				}
				i = end;
			}
			count ++;
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getValue("(k1,v1,[k2,v2],k3,v3)", 0));
	}
}

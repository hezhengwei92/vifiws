package net.eoutech.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class EuFileUtil {
	
	private static EuFileUtil fileUtil = null;
	private Properties properties = new Properties();
	
	private EuFileUtil () {
		try {
			InputStream in = EuFileUtil.class.getClassLoader().getResourceAsStream("application.properties");
			properties.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static EuFileUtil getInstance () {
		if (fileUtil == null) {
			fileUtil = new EuFileUtil();
		}
		return fileUtil;
	}
	
	public String getDispositionValue(String key){
		if(properties.containsKey(key)){
			return properties.getProperty(key);
		}
		return null;
	}
	
//	public static File validateDirExist(String filePath){
//		File file = new File(filePath);
//		boolean b = true;
//		if (!file.exists() || file.isFile()) {
//			b = file.mkdirs();
//		}
//		if (!b) {
//			throw new RuntimeException("create dir error path=[" + filePath + "]");
//		}
//		return file;
//	}
//	
//	
//	public static String readFile(File file){
//		InputStream inputStream = null;
//        String result = null;
//        try {
//			inputStream =  new FileInputStream(file);
//			long contentLength = inputStream.available();
//            byte[] ba = new byte[(int)contentLength];
//            inputStream.read(ba);
//            result = new String(ba);
//            return result;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally {
//            IOUtils.closeQuietly(inputStream);
//        }
//		return null;
//	}
//
//	//��֤�ļ������Ч��?
//	public static boolean validateFileName(String fileName, String validateFileName){
//		File file = new File(fileName);
//		if (!file.exists()) {
//			return false;
//		}
//		String fileShortName = file.getName();
//		String[] regs = validateFileName.split(";");
//		for (String string : regs) {
//			if(Pattern.matches(string, fileShortName)){
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	/**
//	 * �����ļ���ָ��Ŀ¼
//	 * @param file  Դ�ļ�
//	 * @param destPath   Ŀ���ļ���
//	 * @param delSrc    �Ƿ�ɾ��Դ�ļ�
//	 */
//	public static void copyFile(File file, String destPath, boolean delSrc){
//		File dest = validateDirExist(destPath);
//		copyFile(file, dest, delSrc);
//	}
//	
//	/**
//	 * �����ļ���ָ��Ŀ¼
//	 * @param file  Դ�ļ�
//	 * @param destPath   Ŀ���ļ���
//	 * @param delSrc    �Ƿ�ɾ��Դ�ļ�
//	 */
//	public static void copyFile(File file, File destPath, boolean delSrc){
//		File destFileName = new File(destPath + File.separator + file.getName());   
//		FileInputStream fis = null;
//		FileOutputStream fos = null;
//		try {
//			fis = new FileInputStream(file);
//			fos = new FileOutputStream(destFileName);
////			IOUtils.copy(new FileInputStream(file), new FileOutputStream(destFileName));
//			byte[] buffer = new byte[1024];
//			int count = 0;
//			while ((count = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, count);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//		} finally{
//			IOUtils.closeQuietly(fis);
//			IOUtils.closeQuietly(fos);
//			if (delSrc) {
//				file.delete();
//			}
//		}
//	}
//	
////	public static String getDispositionValue(String key){
////		Properties properties = new Properties();
////		try {
////			String pwdFile = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "application.properties";
////			properties.load(new FileInputStream(pwdFile));
////			if(properties.containsKey(key)){
////				return properties.getProperty(key);
////			}
////			return null;
////		} catch (FileNotFoundException e) {
////			e.printStackTrace();
////			return null;
////		} catch (IOException e) {
////			e.printStackTrace();
////			return null;
////		}
////		
////	}
//	
//	public static List<Object> read(String filename,int lineNum,long filePointer) {
//		List<Object> list=new ArrayList<Object>();
//	    RandomAccessFile rf = null;
//	    try {
//	      rf = new RandomAccessFile(filename, "r");
//	      // long len = rf.length();
//	      long len = filePointer;
//	      long start = rf.getFilePointer();
//	      long nextend = start + len - 1;
//	      String line;
//	      rf.seek(nextend);
//	      int c = -1;
//	      int i=0;
//	      while (nextend > start) {
//	        c = rf.read();
//	        if (c == '\n' || c == '\r') {
//	          line = rf.readLine();
//	          if (line != null) {
//	        	  list.add(line);
//	          }
//	          nextend--;
//	          i++;
//	          if(i >= lineNum){
//	        	  break;
//	          }
//	        }
//	        nextend--;
//	        rf.seek(nextend);
//	        if (nextend == 0) {// ���ļ�ָ�������ļ���ʼ���������һ��?
//	          list.add(rf.readLine());
//	        }
//	        
//	      }
//	      return list;
//	    }catch (Exception e) {
//	      
//	      e.printStackTrace();
//	      return null;
//	    } finally {
//	      try {
//	        if (rf != null)
//	          rf.close();
//	      } catch (IOException e) {
//	        e.printStackTrace();
//	      }
//	    }
//	  }
//	public static int getTotalLines(String path){ 
//		 int cnt = 0;
//	        LineNumberReader reader = null;
//	        try {
//	            reader = new LineNumberReader(new FileReader(path));
//	            @SuppressWarnings("unused")
//	            String lineRead = "";
//	            while ((lineRead = reader.readLine()) != null) {
//	            }
//	            cnt = reader.getLineNumber();
//	        } catch (Exception ex) {
//	            cnt = -1;
//	            ex.printStackTrace();
//	        } finally {
//	            try {
//	                reader.close();
//	            } catch (Exception ex) {
//	                ex.printStackTrace();
//	            }
//	        }
//	        return cnt;
//	}
//	
//	public static List<Object> readLog(String strPath , int lineNum,long filePointer){
//		List<Object> list=new ArrayList<Object>();
//		RandomAccessFile myFile = null;
//		 try{
//			myFile = new RandomAccessFile(strPath, "r");
//			myFile.seek(filePointer);
//			for (int i = 0 ;i< lineNum ; ++i){
//				String str = myFile.readLine();
//				if(str== null){
//					break;
//				}
//				list.add(str);
//			}
//		} catch(Exception e){
//		
//		}
//		return list;
//	}
//	
//	public static List<String> loadRows(String path,int startIndex,int endIndex){
//		List<String> list=new ArrayList<String>();
//		try {
//			//for(int i=startIndex;i<endIndex;i++){
//				list=FileUtils.readLines(new File(path)).subList(startIndex, endIndex);
//				//list.add(line);
//			//}
//			return list;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	public static String executeCMD(String cmd){
//		try {
//			Process process = Runtime.getRuntime().exec(cmd);
//			InputStream is = process.getInputStream();
//			String result = IOUtils.toString(is);
//			return result;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}

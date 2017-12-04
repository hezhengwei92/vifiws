package net.eoutech.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author SUU
 * description ZIPUtil 类主要包含使用zip压缩（解压）文件、字符串    以及  使用gzip压缩（解压）字符串
 */
public class ZIPUtil {

	private static ZIPUtil util = null;

	private ZIPUtil() {
	}

	public static ZIPUtil getInstance() {
		if (util == null) {
			util = new ZIPUtil();
		}
		return util;
	}

	/**
	 * zip文件压缩
	 * @param filePath
	 * @param file
	 * @throws Exception
	 */
	public void compressingFile(String filePath, File file) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath), Charset.forName("UTF-8"));
		BufferedOutputStream bm = new BufferedOutputStream(out);
		compressingFile(out, file, file.getName(), bm);
		bm.close();
		out.close(); // 输出流关闭
	}
	// 方法重载  递归
	private void compressingFile(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws Exception { 
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
			}
			for (int i = 0; i < fl.length; i++) {
				compressingFile(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
			}
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bm = new BufferedInputStream(in);
			int b;
			while ((b = bm.read()) != -1) {
				bo.write(b); // 将字节流写入当前zip目录
			}
			bm.close();
			in.close(); // 输入流关闭
		}
	}

	/**
	 * zip方式解压
	 * @param zipFilePath
	 * @param compressPath
	 * @throws Exception
	 */
	public void deCompressingFile(String zipFilePath, String compressPath) throws Exception {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFilePath), Charset.forName("UTF-8"));

		BufferedInputStream bm = new BufferedInputStream(in);
		File file = null;
		ZipEntry entry;
		while ((entry = in.getNextEntry()) != null && !entry.isDirectory()) {
			file = new File(compressPath, entry.getName());
			if (!file.exists()) {
				(new File(file.getParent())).mkdirs();
			}
			FileOutputStream out = new FileOutputStream(file);
			BufferedOutputStream bom = new BufferedOutputStream(out);
			int b;
			while ((b = bm.read()) != -1) {
				bom.write(b);
			}
			bom.close();
			out.close();
		}
		bm.close();
		in.close();
	}

	/**
	 * ZIP/GZIP 压缩（解压缩）字符串
	 * @param primStr
	 * @return
	 */
	public String gCompressStr(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return new BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 * Description:使用gzip进行解压缩
	 * @param compressedStr
	 * @return
	 */
	public String gDeCompressStr(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new BASE64Decoder().decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

	/**
	 * 使用zip进行压缩
	 * 
	 * @param str压缩前的文本
	 * @return 返回压缩后的文本
	 */
	public String compressStr(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new BASE64Encoder().encodeBuffer(compressed);
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}

	/**
	 * 使用zip进行解压缩
	 * 
	 * @param compressed 压缩后的文本
	 * @return 解压后的字符串
	 */
	public String deCompressStr(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			byte[] compressed = new BASE64Decoder().decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}

}

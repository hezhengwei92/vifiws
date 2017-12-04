package net.eoutech.utils;

import org.apache.commons.net.ftp.*;

import java.io.*;
import java.net.SocketException;

/**
 * Created by WangY on 2017/8/26 0026.
 */
public class FtpClientUtil {
    public static final String FTP_URL = "172.28.250.129";
    public static final int FTP_PORT = 21;
    public static final String FTP_USERNAME = "ammsclient";
    public static final String FTP_PASSWORD = "123456";
    public static final String SPLIT = "/";

    public static String encoding = System.getProperty("file.encoding");

    private static FtpClientUtil singleFtp;
    private static FTPClient ftpClient;

    FtpClientUtil() {
        ftpClient = new FTPClient();
    }

    //单例模式：懒汉模式
    public static FtpClientUtil getInstance() {
        if(singleFtp == null){
            singleFtp = new FtpClientUtil();
        }
        return singleFtp;
    }

    //连接FTP服务器
    @SuppressWarnings("finally")
    public boolean connectFtpServer() {
        boolean bool = false;

        try {
            //如果存在连接，先断开，再重新连接
            if(ftpClient.isConnected()) {
                ftpClient.disconnect();
                //return true;
            }


            // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            conf.setServerLanguageCode("zh");
            //连接FTP服务器
            ftpClient.connect("172.28.250.129", 21);
            ftpClient.login("ammsclient", "123456");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setDataTimeout(1000*60*30);   //设置传输超时（30分钟）

            //判断连接是否成功
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("FTP 连接成功");
                bool = true;
            }else {
                System.out.println("FTP 连接失败");
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }




    //连接FTP服务器
    public FTPClient getFTPClient() {
        try {
            //如果存在连接，先断开，再重新连接
            if(ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftpClient.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            conf.setServerLanguageCode("zh");
            //连接FTP服务器
            ftpClient.connect(FTP_URL, FTP_PORT);
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setDataTimeout(1000*60*30);   //设置传输超时（30分钟）

            //判断连接是否成功
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
//              System.out.println("FTP 连接成功");
            }else {
//              System.out.println("FTP 连接失败");
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return ftpClient;
        }
    }


    /**
     * 断开ftp连接
     *
     * @throws Exception
     */
    public void disconnect() throws Exception {
        try {
            FTPClient ftpClient = getFTPClient();
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
                ftpClient = null;
            }
        } catch (IOException e) {
            throw new Exception("Could not disconnect from server.", e);
        }
    }

    //创建目录并改变到目录
    @SuppressWarnings("finally")
    public boolean createAndChangeDirectory(String dir) {
        boolean bool = false;

        //参数是null，直接返回false
        if (dir==null) {
            return false;
        }

        try {
            //连接FTP服务器
            if(connectFtpServer()) {
                //设置编码，处理乱码问题
                dir = new String(dir.getBytes(encoding), FTP.DEFAULT_CONTROL_ENCODING);
                //判断目录是否存在
                if(ftpClient.changeWorkingDirectory(dir)) {
                    //目录已存在，无需创建
                    bool = true;
                }else {
                    //目录不存在，创建目录
                    if(createMultiDirectory(dir)) {
                        if(ftpClient.changeWorkingDirectory(dir)) {
                            bool = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }

    //远程FTP服务器创建多级目录，创建目录失败或发生异常则返回false
    @SuppressWarnings("finally")
    private boolean createMultiDirectory(String multiDirectory) {
        boolean bool = false;
        try {
            String[] dirs = multiDirectory.split("/");
            ftpClient.changeWorkingDirectory("/");

            //按顺序检查目录是否存在，不存在则创建目录
            for(int i=1; dirs!=null&&i<dirs.length; i++) {
                if(!ftpClient.changeWorkingDirectory(dirs[i])) {
                    if(ftpClient.makeDirectory(dirs[i])) {
                        if(!ftpClient.changeWorkingDirectory(dirs[i])) {
                            return false;
                        }
                    }else {
                        return false;
                    }
                }
            }

            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }

    //验证程序版本
    @SuppressWarnings("finally")
    public boolean checkVersion(String remotDir, String properties, String currentVersion) {

        boolean bool = false;

        //连接FTP服务器
        try {
            if (connectFtpServer() && ftpClient.changeWorkingDirectory(remotDir)) {
                String version = getVersion(remotDir+"/"+properties);
                if(version.equals(currentVersion)) {
                    bool = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }

    //读取文件第一行内容
    private String getVersion(String pathFile) {
        InputStream in = null;
        BufferedReader br = null;
        String version = "";
        try {
            in = ftpClient.retrieveFileStream(pathFile);
            if (in != null) {
                br = new BufferedReader(new InputStreamReader(in));
                version = br.readLine();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                br.close();
                in.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return version;
    }

    //上传文件
    @SuppressWarnings("finally")
    public boolean uploadFile(File file, String dir) {
        boolean bool = false;
        FileInputStream in;
        String fileName;

        //参数是null，直接返回false
        if (file==null || dir==null || !file.canRead()) {
            return false;
        }

        try {
            //连接FTP服务器，创建目录
            if(connectFtpServer() && createAndChangeDirectory(dir)) {
                //设置编码，处理乱码问题
                fileName = new String(file.getName().getBytes(encoding), FTP.DEFAULT_CONTROL_ENCODING);
                FTPFile[] files = ftpClient.listFiles(fileName);
                in = new FileInputStream(file);
                if (files.length == 1) {
                    //文件大小
                    long remoteFileSize = files[0].getSize();
                    long localFileSize = file.length();
                    // 本地文件大于等于远程文件，断点续传
                    if (localFileSize>=remoteFileSize && in.skip(remoteFileSize)==remoteFileSize) {
                        ftpClient.setRestartOffset(remoteFileSize);
                        if (ftpClient.storeFile(fileName, in)) {
                            bool = true;
                        }
                    }
                }else {
                    //文件不存在，直接上传
                    if (ftpClient.storeFile(fileName, in)) {
                        bool = true;
                    }
                }
                in.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }


    /**
     * 上传单个文件，并重命名
     *
     * @param file--本地文件
     * @return true 上传成功，false 上传失败
     * @throws Exception
     */
    public boolean uploadFile(File file) throws Exception {
        boolean flag = true;
        InputStream input = null;
        try {
            ftpClient.makeDirectory(FtpClientUtil.SPLIT + file.getName());
            input = new FileInputStream(file);
            flag = ftpClient.storeFile(file.getName(), input);
            // ftpClient.changeWorkingDirectory(FileUtil.SPLIT);
        } catch (Exception e) {
            if (input != null)
                input.close();
            System.out.println("本地文件上传失败！");
            throw e;
        }
        return flag;
    }

    /**
     * 下载单个文件
     * @param remoteFile
     *            ：需要下载的文件，格式为ftp://xx.xx.xx.xx/remoteFile，如：ftp://10.10.10.10/dir1
     *            /dir2/file.txt，则remoteFile为dir1/dir2/file.txt
     * @param localFile：下载的文件保存到本地的文件，为完整绝对路径。
     * @return
     * @throws IOException
     */
    public boolean ftpDownload(String remoteFile, String localFile) throws IOException {
        FileOutputStream fos = null;
        InputStream is = null;
        boolean flag = true;
        try {

            //连接FTP服务器，创建目录
            if(connectFtpServer()) {
                System.out.println("--------FTP下载文件-----------");
                // 第一步：设置基本属性
//              ftpClient.setBufferSize(1024);
//              ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//              ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // 第二步：获取远程文件的输入流
                is = ftpClient.retrieveFileStream(remoteFile);

                if (is == null) {
                    System.out.println("------------");
                    // 如果输入流为空，则表示要下载的远程文件不存在
                    try {
                        is = ftpClient.retrieveFileStream(remoteFile.replace("imageFile", "imageFile2"));
                        if (is != null) {
                            // 如果输入流不为空，则将远程文件的输入流写到本地
                            fos = new FileOutputStream(localFile);
                            byte[] buffer = new byte[1024];
                            int i = -1;
                            while ((i = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, i);
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        flag = false;
                    }
                } else {
                    // 如果输入流不为空，则将远程文件的输入流写到本地
                    fos = new FileOutputStream(localFile);
                    byte[] buffer = new byte[1024];
                    int i = -1;
                    while ((i = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, i);
                    }
                }

            }
        }catch (Exception e) {
            try {
                is = ftpClient.retrieveFileStream(remoteFile.replace("imageFile", "imageFile2"));
                if (is != null) {
                    // 如果输入流不为空，则将远程文件的输入流写到本地
                    fos = new FileOutputStream(localFile);
                    byte[] buffer = new byte[1024];
                    int i = -1;
                    while ((i = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, i);
                    }
                }
            } catch (FileNotFoundException ex) {
                flag = false;
            }
            e.printStackTrace();
        } finally {
            if(is != null){
                is.close();
            }
            if(fos != null){
                fos.close();
            }
        }
        return flag;
    }



    /**
     * 下载单个文件
     * @param remoteFile
     *            ：需要下载的文件，格式为ftp://xx.xx.xx.xx/remoteFile，如：ftp://10.10.10.10/dir1
     *            /dir2/file.txt，则remoteFile为dir1/dir2/file.txt
     * @param localFile：下载的文件保存到本地的文件，为完整绝对路径。
     * @return
     * @throws IOException
     */
    public boolean ftpDownload(FTPClient ftpClient, String remoteFile, String localFile, String qgFileName, String fileName ) throws IOException {
        FileOutputStream fos = null;
        InputStream is = null;
        boolean flag = true;
        try {
//          System.out.println("--------FTP下载文件-----------");
            // 第一步：设置基本属性
//          ftpClient.setBufferSize(1024);
//          ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

//          is = ftpClient.retrieveFileStream("/home/mis/import/imageFile/ZZ_杉德电商间联_20140325_003/特批审核_201403251604318.jpg");


            // 第二步：获取远程文件的输入流
            if (ftpClient.changeWorkingDirectory(remoteFile)) {//判断是不是目录
                is = ftpClient.retrieveFileStream(remoteFile+"/"+qgFileName);
//              System.out.println(remoteFile+"/"+fileName);
//              System.out.println(localFile+"/"+fileName);
                if (is == null) {
                    // 如果输入流为空，则表示要下载的远程文件不存在
                    try {
                        if (ftpClient.changeWorkingDirectory(remoteFile.replace("imageFile", "imageFile2"))) {//判断是不是目录
                            is = ftpClient.retrieveFileStream(remoteFile.replace("imageFile", "imageFile2")+"/"+qgFileName);
                            if (is != null) {
                                // 如果输入流不为空，则将远程文件的输入流写到本地
                                fos = new FileOutputStream(localFile+"/"+fileName);
                                byte[] buffer = new byte[1024];
                                int i = -1;
                                while ((i = is.read(buffer)) != -1) {
                                    fos.write(buffer, 0, i);
                                }
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        flag = false;
                    }
                } else {
                    // 如果输入流不为空，则将远程文件的输入流写到本地
                    fos = new FileOutputStream(localFile+"/"+fileName);
                    byte[] buffer = new byte[1024];
                    int i = -1;
                    while ((i = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, i);
                    }
                }
            }
        }catch (Exception e) {
            try {
                if (ftpClient.changeWorkingDirectory(remoteFile.replace("imageFile", "imageFile2"))) {//判断是不是目录
                    is = ftpClient.retrieveFileStream(remoteFile.replace("imageFile", "imageFile2")+"/"+qgFileName);
                    if (is != null) {
                        // 如果输入流不为空，则将远程文件的输入流写到本地
                        fos = new FileOutputStream(localFile+"/"+fileName);
                        byte[] buffer = new byte[1024];
                        int i = -1;
                        while ((i = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, i);
                        }
                    }
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                flag = false;
            }
            e.printStackTrace();
        } finally {
            if(is != null){
                is.close();
            }
            if(fos != null){
                fos.close();
            }
        }
        return flag;
    }


    /**
     * 移动文件或文件夹
     * @Title: mv
     * @param remoteFile
     * @param localFile
     * @return void
     */
    public boolean mv(String remoteFile, String localFile){
        boolean flg = true;
        try {
            if (ftpClient.changeWorkingDirectory(remoteFile) && createMultiDirectory(localFile)) {//判断是不是目录
                ftpClient.rename(remoteFile, localFile);
            }else {
                flg = false;
            }
        } catch (IOException e) {
            flg = false;
            e.printStackTrace();
        }
        System.out.println(flg);
        return flg;
    }
    /**
     * 上传多个文件
     *
     * @param file--本地文件
     * @param toDir--上传的ftp服务器目录
     * @return true 上传成功，false 上传失败
     * @throws Exception
     */
    public String uploadManyFiles(File file, String toDir) throws Exception {
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
        ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
        createMultiDirectory(toDir);
        boolean flag = true;
        StringBuffer strBuf = new StringBuffer();
        int n = 0;
        try {
            File fises[] = file.listFiles();
            for (int i = 0; i < fises.length; i++) {
                if (fises[i].isDirectory()) {// 文件夹中还有文件夹
                    uploadManyFiles(fises[i], toDir);
                } else {
                    flag = uploadFile(fises[i]);
                }
                if (!flag) {
                    n++;
                    strBuf.append(fises[i].getName() + "\r\n");
                }
            }
            if (n > 0) {
                strBuf.insert(0, "共有" + n + "上传失败，分别为\r\n");
                System.out.println(strBuf.toString());
            }
        } catch (Exception e) {
            System.out.println("本地文件上传失败！");
            throw e;
        }
        return strBuf.toString();
    }
    /**
     * 上传多个文件
     *
     * @param file--本地文件
     * @param toDir--上传的ftp服务器目录
     * @return true 上传成功，false 上传失败
     * @throws Exception
     */
    public String uploadManyFiles2(File file, String toDir) throws Exception {
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件
        ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
        String filePath = file.getAbsolutePath();
        //如果路径不存在，创建路径
        createMultiDirectory(toDir);
        boolean flag = true;
        //StringBuffer strBuf = new StringBuffer();
        int n = 0;
        try {
            File fises[] = file.listFiles();
            for (int i = 0; i < fises.length; i++) {
                if (fises[i].isDirectory()) {// 文件夹中还有文件夹
                    String folderPath = toDir+fises[i].getName()+"/";
                    createMultiDirectory(folderPath);

                    File layerFiles[] = fises[i].listFiles();
                    for(int j=0;j<layerFiles.length;j++){
                        uploadManyFiles2(fises[i], folderPath);
                    }
                } else {
                    flag = uploadFile(fises[i],toDir);
                }
                if (!flag) {
                    n++;
                    //strBuf.append(fises[i].getName() + "\r\n");
                }
            }
            if (n > 0) {
                //strBuf.insert(0, "共有" + n + "上传失败，分别为\r\n");
                //System.out.println(strBuf.toString());
            }
        } catch (Exception e) {
            System.out.println("本地文件上传失败！");
            throw e;
        }
        //return strBuf.toString();
        return "1";
    }


    public static void main(String[] args) {
        FtpClientUtil ftp = new FtpClientUtil();
        long time = System.currentTimeMillis();
        String filePath = "/home/mis/import/imageFile/ZZ_杉德电商间联_20140325_001//";
        String fileName = "店面 (1)_201403251506196.jpg";
        try {
            //System.out.println(ftp.uploadFile(new File("D:/report_data2.txt"), "/home/ammsclient/ammsftpFiles/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //createMultiDirectory

        ftp.connectFtpServer();
        File file = new File("E:/ftpTestPackage/");
        //ftp.createMultiDirectory("/home/ammsclient/payFiles/1/");
        //String folderTree[] = file.getPath().split("\\");
        //System.out.println(file.getName());


        try {
            ftp.uploadManyFiles2(file, "/home/ammsclient/payFiles/5/");
            ftp.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*ftp.getFTPClient();
        ftp.mv("/home/mis/import/imageFile/ZZ_杉德电商间联_20140326_003/","/home/mis/import/DsImageFile/ZZ_杉德电商间联_20140326_003/");
        System.out.println(System.currentTimeMillis() - time);*/
    }
}

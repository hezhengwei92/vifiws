package net.eoutech.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by WangY on 2017/7/28 0028.
 */
public class ObjectUtil {
    /**对象转byte[]
     * @param obj
     * @return
     * @throws Exception
     */
    public static byte[] objectToBytes(Object obj) throws Exception{
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        byte[] bytes = bo.toByteArray();
        bo.close();
        oo.close();
        return bytes;
    }
    /**byte[]转对象
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }

//    public static JGPush JGPush(String wsType,String appId,String nickname,String title,String msgContent,String type,String content,String newTitle){
//        JGPush jGPush = new JGPush();
//        jGPush.setWsType(wsType);
//        jGPush.setUserId(appId);
//        jGPush.setNickname(nickname);
//        jGPush.setTitle(title);
//        jGPush.setMsgContent(msgContent);
//        jGPush.setType(type);
//        jGPush.setContent(content);
//        jGPush.setNewTitle(newTitle);
//        return jGPush;
//    }
}

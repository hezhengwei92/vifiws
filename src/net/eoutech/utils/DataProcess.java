package net.eoutech.utils;

import java.util.*;

/**
 * Created by WangY on 2017/3/13 0013.
 */
public class DataProcess {
    /**
     * 将10进制幻化为16进制
     *
     * @param n
     * @return
     */
    public static String IntToHex(int n) {
        char[] ch = new char[20];
        int nIndex = 0;
        StringBuffer sb = new StringBuffer();
        while (true) {
            int m = n / 16;
            int k = n % 16;
            if (k == 15) {
                ch[nIndex] = 'F';
            } else if (k == 14) {
                ch[nIndex] = 'E';
            } else if (k == 13) {
                ch[nIndex] = 'D';
            } else if (k == 12) {
                ch[nIndex] = 'C';
            } else if (k == 11) {
                ch[nIndex] = 'B';
            } else if (k == 10) {
                ch[nIndex] = 'A';
            } else {
                ch[nIndex] = (char) ('0' + k);
            }
            nIndex++;
            if (m == 0) {
                break;
            }
            n = m;
        }
        sb.append(ch, 0, nIndex);
        sb.reverse();
        String strHex = sb.toString();
        if (strHex.length() == 1) {
            strHex = "0" + strHex;
        }
        return strHex;
    }

    /**
     * 将16进制转化为字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "iso_8859_1");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 将byte转化为int
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        int temp;
        temp = (b & 0b11111111);
        return temp;
    }

    /**
     * 校验ccitt标准下XModem的计算准则的CRC的值
     *
     * @param bytes  二进制数组
     * @param offset 数组起始位置
     * @param len    需要验证的数组长度
     * @return
     */
    public static int CRC_XModem(byte[] bytes, int offset, int len) {
        int crc = 0x0000;          // initial value
        int polynomial = 0x1021;
        for (int index = 0; index < len; index++) {
            byte b = bytes[index + offset];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return crc;
    }

    /**
     * 将short型数字转化为小端在前，大端在后的byte数组
     *
     * @param n
     * @return
     */
    public static byte[] shortToLowHigh(short n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xFF);
        b[1] = (byte) ((n & 0xFF) >> 8);
        return b;
    }

    /**
     * 将short型数字转化为byte数组
     *
     * @param n
     * @return
     */
    public static byte[] shortToBytes(short n) {
        int temp = n;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xFF).byteValue();//将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * 求两个字符串数组的并集，利用set的元素唯一性
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] union(String[] arr1, String[] arr2) {
        Set<String> set = new HashSet<String>();
        for (String str : arr1) {
            set.add(str);
        }
        for (String str : arr2) {
            set.add(str);
        }
        String[] result = {};
        return set.toArray(result);
    }

    /**
     * 求两个数组的交集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] intersect(String[] arr1, String[] arr2) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        LinkedList<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if (!map.containsKey(str)) {
                map.put(str, Boolean.FALSE);
            }
        }
        for (String str : arr2) {
            if (map.containsKey(str)) {
                map.put(str, Boolean.TRUE);
            }
        }

        for (Map.Entry<String, Boolean> e : map.entrySet()) {
            if (e.getValue().equals(Boolean.TRUE)) {
                list.add(e.getKey());
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    /**
     * 求两个数组的差集
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] minus(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        LinkedList<String> history = new LinkedList<String>();
        String[] longerArr = arr1;
        String[] shorterArr = arr2;
        //找出较长的数组来减较短的数组
        if (arr1.length > arr2.length) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (String str : longerArr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : shorterArr) {
            if (list.contains(str)) {
                history.add(str);
                list.remove(str);
            } else {
                if (!history.contains(str)) {
                    list.add(str);
                }
            }
        }

        String[] result = {};
        return list.toArray(result);
    }
}

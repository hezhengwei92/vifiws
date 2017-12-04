package net.eoutech.base.tcpserver.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EouLogger {
	private static int LOGN = 0;

    public EouLogger() {
    }

    public static void dbg(Object... args) {
        System.out.print(String.format("%05d", new Object[]{Integer.valueOf(++LOGN)}));
        System.out.print((new SimpleDateFormat("|yyyy-MM-dd HH:mm:ss.SSS")).format(new Date()));
        Object[] var4 = args;
        int var3 = args.length;

        for(int var2 = 0; var2 < var3; ++var2) {
            Object arg = var4[var2];
            System.out.print("|" + arg);
        }

        System.out.println("");
    }
}

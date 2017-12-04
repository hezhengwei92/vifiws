package net.eoutech.utils;

import net.eoutech.annotation.MyAnno;
import net.eoutech.base.tcpserver.entity.EouMsg;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EReflectUtils {

	/**
	 * 将socket数据转化成msgReq对象
	 * @param req
	 * @param c
	 * @return
	 */
	public static <T> T makeReqMsg(EouMsg req, Class<T> c) {

		if (req == null) {
			return null;
		}

		try {
			T t = c.newInstance();

			Field[] fields = c.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);

				MyAnno anno = field.getAnnotation(MyAnno.class);
				if (anno != null) {

					String type = field.getType().toString();
					if (type.endsWith("String")) {
//						field.set(t, req.getTLVStr(anno.tag()));
					} else if (type.endsWith("Integer") || type.endsWith("int")) {
//						field.set(t, req.getTLVInt(anno.tag()));
					}

				}

			}

			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 将msgResp对象中的值设置返回到socket resp中
	 * @param resp
	 * @param obj
	 * @throws Exception
	 */
	public static void setRespTlvDatas ( EouMsg resp, Object obj ) throws Exception {
		Class c = obj.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible( true );
			MyAnno anno = f.getAnnotation( MyAnno.class );
			if ( anno != null ) {
				// 取值
				String methodName = getMethodName( f.getName() );
				Method method = c.getMethod( methodName, null );
				Object value = method.invoke( obj, new Object[]{} );
				
				String type = f.getType().toString();
				if ( type.endsWith("String") ) {
//					resp.putTLVStr( anno.tag(), value == null ? "" : String.valueOf( value ) );
				} else if ( type.endsWith( "Integer" ) || type.endsWith( "int" ) ) {
//					resp.putTLVInt( anno.tag(), value == null ? -1 : (Integer)value );
				}
				
			}
			
		}
	}
	
	private static String getMethodName ( String fieldName ) {
		return "get"+fieldName.substring( 0, 1 ).toUpperCase()+fieldName.substring( 1 );
	}

}

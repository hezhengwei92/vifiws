package net.eoutech.utils;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * Created by SUU on 2016/9/9.
 */
public class EntityUtil {

    public static <T> T formatEntity ( Map< String, String[] > reqParams, Class< T > reqClass ) {

        if ( reqParams == null || reqParams.isEmpty() || reqClass == null ) {
            return null;
        }

        try {
            T t = reqClass.newInstance();

            Field[] fields = reqClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible( true );

                String type = field.getType().toString();
                if ( type.endsWith( "String" ) ) {
                	
                	if ( reqParams.containsKey( field.getName() ) ) {
                		String value =  reqParams.get( field.getName() )[0];
                		field.set( t, value == null ? null : URLDecoder.decode( value, "UTF-8" ) );
                	}
                    
                } else if ( type.endsWith( "Integer" ) ) {
                	
                	if ( reqParams.containsKey( field.getName() ) ) {
                		Integer value =  StringUtils.isEmpty( reqParams.get( field.getName() )[0] ) ? 0 : Integer.parseInt( reqParams.get( field.getName() )[0] ) ;
                		field.set( t, value );
                	}
                	
                }
            }

            return t;
        } catch ( Exception e ) {
            e.printStackTrace();
            LogUtils.error( "format entity throw exception " + e.toString() );
        }

        return null;
    }

}

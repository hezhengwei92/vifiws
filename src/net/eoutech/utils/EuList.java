package net.eoutech.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EuList {
    private Map< String, Object > maplist = new ConcurrentHashMap< String, Object >();

    private static EuList euList = null;
    private EuList () {
    	
    }
    
    public static EuList getInstance () {
    	if ( euList == null ) {
    		euList = new EuList();
    	}
    	return euList;
    }
    
    
    public Map< String, Object > getMaplist() {
        return maplist;
    }

    public < T > T get( String keyId ) {
        return ( maplist.containsKey( keyId ) ? ( T ) maplist.get( keyId ) : null );
    }


    public void put( String keyId, Object obj ) {
        maplist.put( keyId, obj );
    }


    public int add( String keyId, Object obj ) {
        if ( !maplist.containsKey( keyId ) ) {
            maplist.put( keyId, obj );
            return 0;
        }

        return -1;
    }

    public int update( String keyId, Object obj ) {
        if ( maplist.containsKey( keyId ) ) {
            maplist.put( keyId, obj );
            return 0;
        }
        return -1;
    }

    public void remove( String keyId ) {
        maplist.remove( keyId );
    }

    public int getSize() {
        return maplist.size();
    }


    public void clear() {
        maplist.clear();
    }


}

package net.eoutech.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.io.*;
import java.util.List;

/**
 * Created by WangY on 2017/7/29 0029.
 */
public class RedisClusterClient {
    // 由于在Spring ApplicationContext.xml 配置文件中导入了 redis的配置文件，也就间接的将 <bean id="clusterRedisTemplate"                                                                  class="org.springframework.data.redis.core.RedisTemplate">  这个Bean托管给了Spring bean容器来管理所以 只要我使用注解就可以把这个模板类对象引用过来。
    private static ApplicationContext ac;
    private static RedisTemplate<String, String> clusterRedisTemplate;
    public static String JGPushKey = "JGMsgKey";
    public static String NearbyDevice = "NearbyDevice";
    public static RedisConnectionFactory factory;

    static {
        ac = new FileSystemXmlApplicationContext("classpath*:applicationContext.xml");
        if (RedisClusterClient.clusterRedisTemplate == null) {
            RedisClusterClient.clusterRedisTemplate = ac.getBean(RedisTemplate.class);
        }
    }

    public static synchronized RedisClusterConnection getConnection() {
        if (factory == null) {
            factory = clusterRedisTemplate.getConnectionFactory();
        }
        return factory.getClusterConnection();
    }

    /*//添加数据
    public static void put(Object key, Object value, final long liveTime) {
        if (null == value) {
            return;
        }
        if (value instanceof String) {
            if (StringUtils.isEmpty(value.toString())) {
                return;
            }
        }
        final String keyf = key + "";
        final Object valuef = value;
        clusterRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keyb, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                connection.close();
                LogUtils.info("Redis关闭：" + connection.isClosed());
                return 1L;
            }
        });
    }

    //添加数据
    public static void lpush(Object key, Object value) {
        LogUtils.info("准备把消息放入redis队列");
        if (null == value) {
            return;
        }
        if (value instanceof String) {
            if (StringUtils.isEmpty(value.toString())) {
                return;
            }
        }
        final String keyf = key + "";
        final Object valuef = value;
        clusterRedisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.lPush(keyb, valueb);
                connection.close();
                LogUtils.info("Redis关闭：" + connection.isClosed());
                return 1L;
            }
        });
        LogUtils.info("把消息放入redis队列成功");
    }

    public static Object geoRedius(Object key, final Circle circle) {
        LogUtils.info("执行geoRedius操作");
        final String keyf = key + "";
        Object geoResults = clusterRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                GeoResults<RedisGeoCommands.GeoLocation<byte[]>> geoResult = connection.geoRadius(keyb, circle);
                connection.close();
                LogUtils.info("Redis关闭：" + connection.isClosed());
                return geoResult;
            }
        });
        LogUtils.info("geoRedius操作完成");
        return geoResults;
    }

    //添加设备
    public static void geoAdd(Object key, final RedisGeoCommands.GeoLocation<byte[]> location) {
        LogUtils.info("进行geoAdd操作");
        final String keyf = key + "";
        try {
            clusterRedisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) {
                    byte[] keyb = keyf.getBytes();
                    connection.geoAdd(keyb, location);
                    connection.expire(keyb, 15 * 60);//失效时间15分钟
                    connection.close();
                    LogUtils.info("Redis关闭：" + connection.isClosed());
                    return 1L;
                }
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
            LogUtils.info("异常：" + e.getMessage());
        }
        LogUtils.info("geoAdd操作完成");
    }

    // 获取数据
    public static Object get(Object key) {
        final String keyf = (String) key;
        Object object;
        object = clusterRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {

                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                connection.close();
                LogUtils.info("Redis关闭：" + connection.isClosed());
                return toObject(value);

            }
        });

        return object;
    }

    public static void remove(String key) {
        LogUtils.info("删除key:" + key);
//        clusterRedisTemplate.delete(key);
        final byte[] rmKey = key.getBytes();
        clusterRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long result = connection.del(rmKey);
                connection.close();
                LogUtils.info("Redis关闭：" + connection.isClosed());
                return result;
            }
        });
    }

    // 获取数据
    public static Object rpop(Object key) {
        final String keyf = (String) key;
        Object object;
        object = clusterRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = connection.rPop(key);
                if (value == null) {
                    return null;
                }
                connection.close();
                LogUtils.info("Redis关闭：" + connection.isClosed());
                return toObject(value);
            }
        });

        return object;
    }

    public static Object rpopLpush(Object getKey, Object putKey) {
        final String gkey = (String) getKey;
        final String pkey = (String) getKey;
        Object object;
        object = clusterRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] gKey = gkey.getBytes();
                byte[] pKey = gkey.getBytes();
                byte[] value = connection.rPopLPush(gKey, pKey);
                if (value == null) {
                    return null;
                }
                connection.close();
                LogUtils.info("Redis关闭：" + connection.isClosed());
                return toObject(value);
            }
        });

        return object;
    }

    public static Long lLen(Object getKey) {
        final String gkey = getKey + "";
        Long len = (Long) clusterRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] gKey = gkey.getBytes();
                Long aLong = redisConnection.lLen(gKey);
                redisConnection.close();
                LogUtils.info("Redis关闭：" + redisConnection.isClosed());
                return aLong;
            }
        });
        return len;
//        final String gkey = getKey + "";
//        return clusterRedisTemplate.opsForList().size(gkey);
//        object = clusterRedisTemplate.execute(new RedisCallback<Object>() {
//            public Object doInRedis(RedisConnection connection)
//                    throws DataAccessException {
//                byte[] gKey = gkey.getBytes();
//                Long value = connection.lLen(gKey);
//                if (value == null) {
//                    return null;
//                }
//                return value;
//            }
//        });

//        return object;
    }*/

    /**
     * 描述 : <byte[]转Object>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param bytes
     * @return
     */
    private static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }


    /**
     * 存放定时的键值对
     *
     * @param key   键
     * @param value 值
     * @param time  存放时间，秒
     */
    public static void PutValue(String key, Object value, Long time) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        if (value == null) {
            LogUtils.info("PutValue空值");
            return;
        }
        byte[] bValue = toByteArray(value);
        connection.set(bKey, bValue);
        connection.expire(bKey, time);
        connection.close();
        LogUtils.info("PutValue操作关闭连接：" + connection.isClosed());
    }

    /**
     * 获取key对应的value
     *
     * @param key 键
     * @return 返回一个对象
     */
    public static Object GetValue(String key) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        byte[] bValue = connection.get(bKey);
        connection.close();
        LogUtils.info("GetValue操作关闭连接：" + connection.isClosed());
        if (bValue == null){
            return null;
        }
        return toObject(bValue);
    }

    /**
     * 存放一个值到列表中
     *
     * @param key   键
     * @param value 值
     * @return 返回一个长度
     */
    public static Long LeftPush(String key, Object value) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        if (value == null) {
            LogUtils.info("PutValue空值");
            return -1L;
        }
        byte[] bValue = toByteArray(value);
        Long result = connection.lPush(bKey, bValue);
        connection.close();
        LogUtils.info("LeftPush操作关闭连接：" + connection.isClosed()+"---返回结果："+result);
        return result;
    }

    /**
     * 取出列表中最先插入的值
     *
     * @param key 键
     * @return 返回一个对象
     */
    public static Object RightPop(String key) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        byte[] bValue = connection.rPop(bKey);
        connection.close();
        if (bValue == null){
            return null;
        }
        LogUtils.info("RightPop操作关闭连接：" + connection.isClosed());
        return toObject(bValue);
    }

    /**
     * 删除key对应的value或list
     *
     * @param key 键
     * @return 返回一个长度
     */
    public static Long RemoveKey(String key) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        Long result = connection.del(bKey);
        connection.close();
        LogUtils.info("RemoveKey操作关闭连接：" + connection.isClosed()+"---返回结果："+result);
        return result;
    }

    /**
     * 存放位置信息
     *
     * @param key    键
     * @param bValue 值
     * @param time   存放时间，秒
     * @return 返回一个长度
     */
    public static Long GeoAdd(String key, RedisGeoCommands.GeoLocation<byte[]> bValue, Long time) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        Long result = connection.geoAdd(bKey, bValue);
        connection.expire(bKey, time);
        connection.close();
        LogUtils.info("RemoveKey操作关闭连接：" + connection.isClosed()+"---返回结果："+result);
        return result;
    }

    /**
     * 获取位置结果
     *
     * @param key    键
     * @param circle 值
     * @return 返回符合条件的结果
     */
    public static GeoResults<RedisGeoCommands.GeoLocation<byte[]>> GeoRadius(String key, Circle circle) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        GeoResults<RedisGeoCommands.GeoLocation<byte[]>> results = connection.geoRadius(bKey, circle);
        connection.close();
        LogUtils.info("GeoRadius操作关闭连接：" + connection.isClosed());
        return results;
    }

    /**
     * 获取列表长度
     *
     * @param key 键
     * @return 返回列表长度
     */
    public static Long ListLength(String key) {
        RedisClusterConnection connection = getConnection();
        byte[] bKey = key.getBytes();
        Long result = connection.lLen(bKey);
        connection.close();
        LogUtils.info("ListLength操作关闭连接：" + connection.isClosed()+"---返回结果："+result);
        return result;
    }

}

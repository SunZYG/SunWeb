package com.commonality;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @Description: Redis工具类
 * 
 * @ClassName: RedisUtilbak
 * @Copyright: Copyright (c) 2017年3月9日
 * 
 * @author em.D
 * @date 2017年3月9日 上午10:30:38
 * @version
 *
 */
public class RedisUtilbak {

	public static ShardedJedisPool pool;
	public static int defaultSeconds = Integer.MAX_VALUE;

	/**
	 * 初始化Redis
	 */
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(1000 * 60);
		config.setTestOnBorrow(true);
		String host = "localhost";
		int port = 6379;
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(2);
		JedisShardInfo info = new JedisShardInfo(host, port);
		jdsInfoList.add(info);
		pool = new ShardedJedisPool(config, jdsInfoList);
	}

	/**
	 * 往Redis里插入一条数据
	 * @param key 键
	 * @param value 值
	 * @param seconds 生命周期（秒），如果设置成-1，则表示生命周期无限
	 * @return 插入是否成功
	 */
	@SuppressWarnings("deprecation")
	public static boolean put(Object key,Object value,int seconds){
		ShardedJedis jds = pool.getResource();
		try {
			if("OK".equals(jds.setex(toByteArray(key), seconds == -1 ? defaultSeconds : seconds,toByteArray(value)))){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 往Redis里插入一条有序列的数据
	 * @param key 键
	 * @param value 值
	 * @param index 下标
	 * @return 插入是否成功
	 */
	@SuppressWarnings("deprecation")
	public static boolean zadd(Object key,Object value,long index){
		ShardedJedis jds = pool.getResource();
		try {
			if(1L <= jds.zadd(toByteArray(key), index,toByteArray(value))){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 取出有序集合中的一部分数据
	 * @param key 键
	 * @param start 起始下标
	 * @param end 终止下标
	 * @return 按照下标范围查处的有序结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static Set<byte[]> getRange(String key,long start,long end){
		ShardedJedis jds = pool.getResource();
		try {
			Set temp = jds.zrange(toByteArray(key),start,end);
			if(temp == null){
				return new HashSet();
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 根据key查找值
	 * @param key 键
	 * @return 查询结果对象
	 */
	@SuppressWarnings("deprecation")
	public static Object get(Object key){
		ShardedJedis jds = pool.getResource();
		try {
			byte[] temp = jds.get(toByteArray(key));
			if(temp == null){
				return null;
			}
			Object ret = toObject(temp);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 根据key删除一条记录
	 * @param key 键
	 * @return 删除是否成功
	 */
	@SuppressWarnings("deprecation")
	public static boolean delete(Object key){
		ShardedJedis jds = pool.getResource();
		try {
			if(jds.del(toByteArray(key)) == 1L){
				return true;
			}
			else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 根据通配符匹配，批量删除数据
	 * @param pattern 通配符（注：这里不是正则表达式）
	 * @return 批量删除是否成功
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static boolean batchDelete(String pattern){
		pattern = "[^\\d]*" + pattern;
		ShardedJedis jds = pool.getResource();
		try {
			Collection j = jds.getAllShards();
			if(j.isEmpty())
				return false;
			Set<byte[]> s = ((Jedis)j.iterator().next()).keys(pattern.getBytes());
			Iterator it = s.iterator();
			while (it.hasNext()) {
				jds.del((byte[])it.next());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 根据通配符匹配，查询数据
	 * @param pattern 通配符（注：这里不是正则表达式）
	 * @return 查询结果数据键值队
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static Map getPattern(String pattern){
		pattern = "[^\\d]*" + pattern;
		ShardedJedis jds = pool.getResource();
		Map<Object,Object> ret = new HashMap<Object,Object>();
		try {
			Collection j = jds.getAllShards();
			if(j.isEmpty())
				return ret;
			Set<byte[]> s = ((Jedis)j.iterator().next()).keys(pattern.getBytes());
			Iterator it = s.iterator();
			while (it.hasNext()) {
				byte[] key = (byte[])it.next();
				ret.put(toObject(key), toObject(jds.get(key)));
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return ret;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 清空Redis库
	 * @return 清空是否成功
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static boolean flush(){
		ShardedJedis jds = pool.getResource();
		try {
			Collection j = jds.getAllShards();
			if(j.isEmpty())
				return false;
			if("OK".equals(((Jedis)j.iterator().next()).flushDB()))
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 批量插入数据
	 * @param keyvalues 键值队String数组["键1","值1","键2","值2"...]
	 * @return 批量插入是否成功
	 */
	@SuppressWarnings("deprecation")
	public static boolean mset(String... keyvalues){
		@SuppressWarnings({ "unused", "rawtypes" })
		List<?> ret = new ArrayList();
		ShardedJedis jds = pool.getResource();
		try {
			@SuppressWarnings("rawtypes")
			Collection j = jds.getAllShards();
			if(j.isEmpty())
				return false ;
			if("OK".equals(((Jedis)j.iterator().next()).mset(keyvalues)))
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			pool.returnResource(jds);
		}
	}
	
	/**
	 * 批量查询数据
	 * @param keys 键值队byte二纬数组["键1","键2","键3"...]
	 * @return 查询结果数据（List里放的是byte数组）
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static List mget(byte[]... keys){
		List ret = new ArrayList();
		ShardedJedis jds = pool.getResource();
		try {
			Collection j = jds.getAllShards();
			if(j.isEmpty())
				return ret ;
			ret = ((Jedis)j.iterator().next()).mget(keys);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return ret;
		} finally {
			pool.returnResource(jds);
		}
	}

	/**
	 * 对象序列化
	 * @param obj 需要序列化的对象
	 * @return 对象序列化后的字节数组
	 */
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 对象反序列化
	 * @param bytes 需要反序列化的字节数组
	 * @return 反序列化生成的对象
	 */
	public static Object toObject(byte[] bytes) {
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
}
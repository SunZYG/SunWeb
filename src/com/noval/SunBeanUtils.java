package com.noval;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Bean 通用类
 * @author 85366
 *
 */
public class SunBeanUtils {
	 public  static Object getObjBeans(Map<String,String> maps,Object obj) {
		  for (Map.Entry<String, String> entry : maps.entrySet()) {
			  try {
				BeanUtils.setProperty(obj, entry.getKey(), entry.getValue());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}  //其中p代表的是要设置的对象
			  }
		return obj;
	 }
}

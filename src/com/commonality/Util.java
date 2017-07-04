package com.commonality;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



import com.wjm.util.PinYinUtil;

public class Util {

	private final static String DES = "DES";

	public static String getProperties(String bundleName, String propertyName) {
		ResourceBundle rb = ResourceBundle.getBundle(bundleName);
		try {
			return new String(rb.getString(
					new String(propertyName.getBytes("gbk"), "iso-8859-1"))
					.getBytes("iso-8859-1"), "gbk");
		} catch (Exception e) {
			return "";
		}
	}

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	public final synchronized static String decrypt(String data) {
		try {
			return new String(decrypt(hex2byte(data.getBytes()), "wjmmuyun".getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final synchronized static String encrypt(String password) {
		try {
			return byte2hex(encrypt(password.getBytes(), "wjmmuyun".getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("���Ȳ���ż��");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getPinYin(String source, boolean isFullPinyin) {
		String pinyinSource = null;
		if (isFullPinyin)
			pinyinSource = PinYinUtil.getPinYin(source, false);
		else
			pinyinSource = PinYinUtil.getPinYinHeadChar(source);
		String[] chars = pinyinSource.split("\\s+");
		List temp = new ArrayList();
		for (int i = 0; i < chars.length; i++) {
			temp.add(chars[i].split("\\|"));
		}
		String[] current = (String[]) temp.get(0);
		for (int i = 0; i < temp.size() - 1; i++) {
			String[] after = (String[]) temp.get(i + 1);
			String[] xin = new String[current.length * after.length];
			for (int j = 0, count = 0; j < current.length; j++) {
				for (int k = 0; k < after.length; k++) {
					xin[count++] = current[j] + after[k];
				}
			}
			current = xin;
		}
		Map map = new HashMap();
		for (int i = 0; i < current.length; i++) {
			map.put(current[i], "");
		}
		Set keys = map.keySet();
		Object[] cu = keys.toArray();
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < cu.length; i++) {
			ret.append("|" + cu[i]);
		}
		if (isFullPinyin)
			System.out
					.println(" "
							+ source
							+ " ---> "
							+ (ret.toString().length() > 0 ? ret.toString()
									+ "|" : ""));
		else
			System.out
					.println(" "
							+ source
							+ " ---> "
							+ (ret.toString().length() > 0 ? ret.toString()
									+ "|" : ""));
		return ret.toString().length() > 0 ? ret.toString() + "|" : "";
	}

	public static String jsonCharFilter(String sourceStr) {
		sourceStr = sourceStr.replace("\\", "\\\\");
		sourceStr = sourceStr.replace("\b", "\\\b");
		sourceStr = sourceStr.replace("\t", "\\\t");
		sourceStr = sourceStr.replace("\r", "\\\r");
		sourceStr = sourceStr.replace("\n", "\\\n");
		sourceStr = sourceStr.replace("\f", "\\\f");
		sourceStr = sourceStr.replace("\r", "\\\r");
		return sourceStr.replace("\"", "\\\"");
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 
	 * @Title: list2JsonArry
	 * @Description: TODO(用于转换json)
	 * @param @param list
	 * @param @param dateFormat
	 * @param @param response 设定文件
	 * @author zhaoh
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static String list2JsonArry(List list) {
		return null;
//		// 将list<Object>解析成JSON字符串
//		JSONArray jsonArray = JSONArray.fromObject(list);
//		// 设置响应编码格式
//		return jsonArray.toString();
	}

	public static String strtoJsonOBJ(String str) {
		return str;
//		// 将list<Object>解析成JSON字符串
//		JSONObject obj = JSONObject.fromObject(str);
//		// 设置响应编码格式
//		return obj.toString();
	}
	/**
	 * @throws UnsupportedEncodingException *
	 * 
	* @Title: main
	* @Description: TODO(发送短信)
	* @param @param args    设定文件
	* @author zhaoh
	* @return void    返回类型
	* @throws
	 */
	public static void senderMassage(String mobiles,String content) throws UnsupportedEncodingException {
//		WebServiceTest.Send(mobiles, content);
	}
	/***
	 * 
	* @Title: randomChars
	* @Description: TODO(随机手机验证码)
	* @param @param randomLength
	* @param @return    设定文件
	* @author zhaoh
	* @return String    返回类型
	* @throws
	 */
	public static String randomYZM() {  
        char[] randoms = {'0','1','2','3','4','5','6','7','8','9','9','8','7','6','5','4','3','2','1','0'};  
        Random random = new Random();  
        StringBuffer ret = new StringBuffer();  
        for (int i = 0; i < 5; i++) {  
            ret.append(randoms[random.nextInt(randoms.length)]);  
        }  
        random = null;  
     
        return ret.toString();
    }
	
	public static Date getBJTime(){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		URL url;
		try {
			url = new URL("http://www.bjtime.cn");
			URLConnection uc = url.openConnection();
			uc.connect();
			long ld = uc.getDate();
			return new Date(ld);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	public static boolean isNotNull(String source){
		if(null == source || "".equals(source.trim()) || "null".equals(source.trim()))
			return false;
		return true;
	}
	
	public static String whenNullThenEmptyString(String source){
		if(isNotNull(source))
			return source;
		return "";
	}
	/**
	 * 	科学计数法 转数字
	 */
	public static String KXToNums(String num){
		num=Math.round(Double.valueOf(num))+"";
		BigDecimal nums = new BigDecimal(num);
		return nums.toPlainString();
	}
	/** 
	 * 转义正则特殊字符 （$()*+.[]?\^{},|） 
	 *  
	 * @param keyword 
	 * @return 
	 */  
	public static String escapeExprSpecialWord(String keyword) {  
	    if (keyword!=null) {  
	        String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
	        for (String key : fbsArr) {  
	            if (keyword.contains(key)) {  
	                keyword = keyword.replace(key, "\\" + key);  
	            }  
	        }  
	    }  
	    return keyword;  
	}  
}
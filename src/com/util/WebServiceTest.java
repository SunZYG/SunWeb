package com.util;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * 
 */
public class WebServiceTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceTest.class);

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void Send(String sendphone,String SendMsg) throws UnsupportedEncodingException {
		WmgwLocator wmgwLocator = new WmgwLocator();
		String strArgs[] = new String[10];
		strArgs[0] = "JC2482";		
		strArgs[1] = "563013";		
//		strArgs[2] = "18317143214";
//		strArgs[3] = "永达汽车相爱难过吗测试";
		strArgs[4] = "1";	
		strArgs[5] = "*";
		
		//mongateCsSendSmsEx MongateCsSpSendSmsNew
		try {
			System.out.println("Test mongateCsSendSmsEx ...");
			System.out.println("back value is :"
					+ wmgwLocator.getwmgwSoap().
					mongateCsSendSmsEx(strArgs[0], strArgs[1], sendphone, 
							SendMsg, Integer.valueOf(strArgs[4]).intValue()));
			System.out.println("send mongateCsSendSmsEx end !");
			System.out.println();
		} catch (RemoteException e) {
			LOGGER.error(e.getMessage());
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
		}
		
	}
}

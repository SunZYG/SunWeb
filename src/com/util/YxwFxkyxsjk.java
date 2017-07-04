package com.util;


import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;



public class YxwFxkyxsjk {
    private String endpoint = "http://cecs-service.ceair.com/WSDataExchange.asmx";// 提供接口的地址
    private String soapaction = "http://tempuri.org/"; // 域名，这是在server定义的

    public String PutWeather(String date) throws Exception {
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(endpoint));
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(soapaction + "GetJSON");
        call.setOperationName(new QName(soapaction, "GetJSON"));
        call.addParameter(new QName(soapaction, "id"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(soapaction, "userName"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(soapaction, "userPass"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
        call.addParameter(new QName(soapaction, "paramsStr"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
        // 由于需要认证，故需要设置调用的用户名和密码。
        SOAPHeaderElement soapHeaderElement = new SOAPHeaderElement(soapaction, "ESBHeader");
        soapHeaderElement.setNamespaceURI(soapaction);
        call.addHeader(soapHeaderElement);
        String res = (String) call.invoke(new Object[] { "558", "yxw_jzpb", "yxw_jzpb", date });
        return res;
    }

    public void InsertFXHours() throws Exception {
        String dates = Constant.getSysNowDate();
        InsertFXHoursByDate(dates);
    }

    public void InsertFXHoursByDate(String date) throws Exception {
      
    }

    private static Map<String, String> putmaps() {
        Map<String, String> companies = new HashMap<String, String>();
        companies.put("HFE", "EA01_安徽");
        companies.put("LHW", "EA02_甘肃");
        companies.put("SJW", "EA03_河北");
        companies.put("NKG", "EA05_江苏");
        companies.put("KHN", "EA06_江西");
        companies.put("NGB", "EA07_浙江");
        companies.put("TAO", "EA08_山东");
        companies.put("TYN", "EA09_山西");
        companies.put("MU", "EA10_上海");
        companies.put("WUH", "EA11_武汉");
        companies.put("SIA", "EA12_西北");
        companies.put("KMG", "EA13_云南");
        companies.put("PEK", "EA15_北京");
        companies.put("CTU", "EA16_四川");
        companies.put("FM", "EA17_上航");
        companies.put("KN", "EA19_中联航");
        companies.put("TNA", "EA20_济南");
        companies.put("CK", "EA21_货运");
        return companies;
    }

}

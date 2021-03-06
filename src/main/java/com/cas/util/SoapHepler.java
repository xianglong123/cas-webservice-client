package com.cas.util;

import com.cas.controller.ClientController;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

/**
* 发送自定义soap报文（内嵌jsonString）获取返回报文工具类
*/
public class SoapHepler {
  //对接服务地址
  private String addressUrl="http://XXXXXXXX";


  public SoapHepler(String addressUrl) {
    if(addressUrl!=null&&!"".equals(addressUrl.trim())){
      this.addressUrl = addressUrl;
    }
  }
  /**
  * 添加所需信息
  * @return
  */
  public String sendSoapXml(ClientController.ResultDTO resultDTO) {
    String XSI_I = "http://www.chinamobile.com";
    try {
      //实例化一个soap连接对象工厂
      SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
      //实例化一个连接对象
      SOAPConnection connection = soapConnFactory.createConnection();
      //实例化一个消息对象
      MessageFactory messageFactory = MessageFactory.newInstance();
      //实例化一个消息
      SOAPMessage message = messageFactory.createMessage();
      //获取消息中soap消息部分的句柄
      SOAPPart soapPart = message.getSOAPPart();
      //获取soap消息部分中的信封句柄
      SOAPEnvelope envelope = soapPart.getEnvelope();
      envelope.setPrefix("soap");
      envelope.removeNamespaceDeclaration("SOAP-ENV");
      SOAPHeader header = envelope.getHeader();
      header.removeNamespaceDeclaration("SOAP-ENV");
      header.setPrefix("soap");
      //添加消息体以及json字符串
      SOAPBody body = envelope.getBody();
      body.setPrefix("soap");

      SOAPBodyElement bodyElement = body.addBodyElement(envelope.createName("OperationResultNotify", "simota", XSI_I));
      SOAPElement seqNumEl = bodyElement.addChildElement("SeqNum","simota");
      SOAPElement soapElement = bodyElement.addChildElement("SessionID","simota");
      SOAPElement sessionTypeEl = bodyElement.addChildElement("SessionType","simota");
      SOAPElement timeStampEl = bodyElement.addChildElement("TimeStamp","simota");
      SOAPElement msisdnEl = bodyElement.addChildElement("Msisdn","simota");
      SOAPElement sEIDEl = bodyElement.addChildElement("SEID","simota");
//      SOAPElement iMEIEl = bodyElement.addChildElement("IMEI","simota");
      SOAPElement appAIDEl = bodyElement.addChildElement("AppAID","simota");
      SOAPElement sessionId = bodyElement.addChildElement("SessionID","simota");
      SOAPElement resultCode = bodyElement.addChildElement("ResultCode","simota");
      seqNumEl.addTextNode(judgeNull("20210623113223106039"));
      soapElement.addTextNode(judgeNull("TSM2106100000000000000000029501"));
      sessionTypeEl.addTextNode(judgeNull(resultDTO.getSessionType()));
      timeStampEl.addTextNode(judgeNull("20210623113223"));
      msisdnEl.addTextNode(judgeNull(resultDTO.getMobile()));
      sEIDEl.addTextNode(judgeNull(resultDTO.getSeid()));
//      iMEIEl.addTextNode(judgeNull("5e7da3230cf51975"));
      appAIDEl.addTextNode(judgeNull("D15600010180000000000004B0015200"));
      sessionId.addTextNode(judgeNull("20210623113223094032"));
      resultCode.addTextNode(judgeNull(resultDTO.getResultCode()));
      message.saveChanges();
      System.out.println("输出报文，如下：");
      message.writeTo(System.out);

      //<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><simota:PreOperationsReq xmlns:simota="http://www.chinamobile.com"><simota:SeqNum>20210713012531166153</simota:SeqNum><simota:SessionID>20210713012531153107</simota:SessionID><simota:SessionType>10</simota:SessionType><simota:TimeStamp>20210713012531</simota:TimeStamp><simota:Msisdn>13522165497</simota:Msisdn><simota:SEID>21000009103151913488</simota:SEID><simota:IMEI>cbd83f91d9cc2690</simota:IMEI><simota:AppAID>D15600010180000000000004B0015200</simota:AppAID></simota:PreOperationsReq></soap:Body></soap:Envelope>
      //{"appAid":"D15600010180000000000004B0015200","commType":"1","imei":"5e7da3230cf51975","msisdn":"13476230893","seid":"20000002103003177181","seqNum":"20210623113223106039","sessionID":"20210623113223094032","sessionType":"2","timeStamp":"20210623113223"}
      //发送信息
       //发送信息
      SOAPMessage call = connection.call(message, addressUrl);
      TransformerFactory transformerFactory=TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      Source sourceContent = call.getSOAPPart().getContent();
      StreamResult result = new StreamResult(new ByteArrayOutputStream());
      transformer.transform(sourceContent,result);
      //获取返回报文
      String s = result.getOutputStream().toString();
      System.out.println("返回报文,如下：");
      System.out.print( s);
      connection.close();
      return s;
    } catch (Exception e) {
      //todo 需要改成自定义异常
      throw new RuntimeException("返回报文失败 ,错误信息："+e);
    }
  }
  /**添加上述节点文本是不能为null
  * 如果str为null 置为“”
  * @param str
  * @return
  */
  public static String judgeNull(String str){
    return (str==null)?"":str;
  }

}



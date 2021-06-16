package com.cas.controller;


import com.cas.util.SoapHepler;
import com.cas.util.XMLUtil;
import org.jdom2.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping("/testSoap")
    public String getSoapXml() {
        String hospitalRegistrationUrl = "http://localhost:8101/tsm/api/PreOperationsReq";
        //发送soap报文
        SoapHepler soapHepler = new SoapHepler(hospitalRegistrationUrl);
        String data = soapHepler.sendSoapXml("data");
        //解析，获取返回json
        Document document = XMLUtil.strXmlToDocument(data);
        String returnJson = XMLUtil.getValueByElementName(document, "return");
        System.out.print(returnJson);
        return returnJson;
    }

}

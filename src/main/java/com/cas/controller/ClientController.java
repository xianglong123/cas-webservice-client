package com.cas.controller;


import com.cas.util.SoapHepler;
import com.cas.util.XMLUtil;
import org.jdom2.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @PostMapping("/testSoap")
    public String getSoapXml(ResultDTO resultDTO) {
        String hospitalRegistrationUrl = "http://localhost:8202/tsm/api/PreOperationsReq";
        //发送soap报文
        SoapHepler soapHepler = new SoapHepler(hospitalRegistrationUrl);
        String data = soapHepler.sendSoapXml(resultDTO);
        //解析，获取返回json
        Document document = XMLUtil.strXmlToDocument(data);
        String returnJson = XMLUtil.getValueByElementName(document, "return");
        System.out.print(returnJson);
        return returnJson;
    }


    public class ResultDTO {

        private String mobile;
        private String seid;
        private String resultCode;
        private String sessionType;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSeid() {
            return seid;
        }

        public void setSeid(String seid) {
            this.seid = seid;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getSessionType() {
            return sessionType;
        }

        public void setSessionType(String sessionType) {
            this.sessionType = sessionType;
        }
    }

}

package com.qianxuefeng.base;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class JAXBTest {
  
    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(JAXBVO.class);
         
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBVO msg = new JAXBVO();
        marshaller.marshal(msg, System.out);
        System.out.println();  
         
        String xml = "<xml>\n" +
                "    <name>姓名</name>\n" +
                "    <nick><![CDATA[<B>昵称</B>]]></nick>\n" +
                "</xml>";
        JAXBVO jaxbVO = (JAXBVO) unmarshaller.unmarshal(new StringReader(xml));
        System.out.println(jaxbVO.getName());
    }  
} 
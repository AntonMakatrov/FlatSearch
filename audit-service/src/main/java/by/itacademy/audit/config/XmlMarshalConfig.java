package by.itacademy.audit.config;

import by.itacademy.audit.core.entity.xml.XmlAuditList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

@Configuration
public class XmlMarshalConfig {

    @Bean
    public Marshaller getMarshaller() throws Exception {
        JAXBContext context = JAXBContext.newInstance(XmlAuditList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }
}

package by.itacademy.flats_service.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class DocumentRequester {

    public static Document requestHtmlDocument(String baseUrl) {
        try {
            return Jsoup.connect(baseUrl).get();
        } catch (Exception exception) {
            log.error("Error scrapping HTML document from " + baseUrl);
            throw new RuntimeException(exception);
        }
    };
}

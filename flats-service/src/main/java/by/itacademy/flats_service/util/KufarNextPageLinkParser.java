package by.itacademy.flats_service.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;

@Slf4j
public class KufarNextPageLinkParser {

    public static String parseKufarNextPageLink(Element content) {
        return "https://re.kufar.by" +
                content.getElementsByAttributeValue(
                        "data-testid",
                        "realty-pagination-next-link"
                ).attr("href");
    }
}

package by.itacademy.flats_service.service.api;

import by.itacademy.flats_service.core.entity.FlatWebSite;
import org.jsoup.nodes.Element;

public interface ContentRequester {

    FlatWebSite getType();

    Element getHtmlDocument(String url);
}

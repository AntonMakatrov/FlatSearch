package by.itacademy.flats_service.service.api;

import by.itacademy.flats_service.core.entity.Flat;
import by.itacademy.flats_service.core.entity.FlatWebSite;
import org.jsoup.nodes.Element;

import java.util.List;

public interface FlatParser {

    FlatWebSite getType();

    List<Flat> parseFlats(Element content);

    String parseNextPageLink(Element content);
}

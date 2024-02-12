package by.itacademy.flats_service.service.factory;

import by.itacademy.flats_service.core.entity.FlatWebSite;
import by.itacademy.flats_service.service.api.SaleFlatParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class SaleFlatParserFactory {

    private final List<SaleFlatParser> saleFlatParsers;

    public SaleFlatParserFactory(List<SaleFlatParser> saleFlatParsers) {
        this.saleFlatParsers = saleFlatParsers;
    }

    public SaleFlatParser getByType(FlatWebSite type) {
        return saleFlatParsers.stream().filter(it -> it.getType() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("sale parser not found by type " + type));
    }
}

package by.itacademy.flats_service.service.factory;

import by.itacademy.flats_service.core.entity.FlatWebSite;
import by.itacademy.flats_service.service.api.RentFlatParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class RentFlatParserFactory {

    private final List<RentFlatParser> rentFlatParsers;

    public RentFlatParserFactory(List<RentFlatParser> rentFlatParsers) {
        this.rentFlatParsers = rentFlatParsers;
    }

    public RentFlatParser getByType(FlatWebSite type) {
        return rentFlatParsers.stream().filter(it -> it.getType() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("rent parser not found by type " + type));
    }
}

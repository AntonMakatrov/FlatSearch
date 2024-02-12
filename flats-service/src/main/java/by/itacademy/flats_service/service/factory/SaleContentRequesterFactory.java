package by.itacademy.flats_service.service.factory;

import jakarta.annotation.PostConstruct;
import by.itacademy.flats_service.core.entity.FlatWebSite;
import by.itacademy.flats_service.service.api.SaleContentRequester;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public final class SaleContentRequesterFactory {

    private final List<SaleContentRequester> saleContentRequesters;

    private Map<FlatWebSite, SaleContentRequester> saleContentRequestersByType;

    public SaleContentRequesterFactory(List<SaleContentRequester> saleContentRequesters) {
        this.saleContentRequesters = saleContentRequesters;
    }

    @PostConstruct
    private void prepareMap() {
        saleContentRequestersByType = saleContentRequesters
                .stream()
                .collect(
                        Collectors.toMap(SaleContentRequester::getType, Function.identity())
                );
    }

    public SaleContentRequester getByType(FlatWebSite type) {
        SaleContentRequester saleContentRequester = saleContentRequestersByType.get(type);
        if (saleContentRequester != null) {
            return saleContentRequester;
        } else {
            throw new RuntimeException("sale content requester not found by type " + type);
        }
    }
}

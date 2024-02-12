package by.itacademy.flats_service.controller;

import by.itacademy.flats_service.core.dto.FlatFilter;
import by.itacademy.flats_service.core.dto.FlatWriteDTO;
import by.itacademy.flats_service.core.dto.PageOfFlatDTO;
import by.itacademy.flats_service.service.api.FlatCrudService;
import by.itacademy.flats_service.transformer.api.IPageTransformer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/flats")
public class FlatController {

    private final FlatCrudService flatService;
    private final IPageTransformer pageTransformer;

    public FlatController(FlatCrudService flatService, IPageTransformer pageTransformer) {
        this.flatService = flatService;
        this.pageTransformer = pageTransformer;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveFlat(@RequestBody FlatWriteDTO flatWriteDto) {
        flatService.createFlat(flatWriteDto);
    }

    @GetMapping
    PageOfFlatDTO getAllFlatByFilter(@RequestParam(name = "price_from", required = false) Integer priceFrom,
                                     @RequestParam(name = "price_to", required = false) Integer priceTo,
                                     @RequestParam(name = "bedrooms_from", required = false) Integer bedroomsFrom,
                                     @RequestParam(name = "bedrooms_to", required = false) Integer bedroomsTo,
                                     @RequestParam(name = "area_from", required = false) Integer areaFrom,
                                     @RequestParam(name = "area_to", required = false) Integer areaTo,
                                     @RequestParam(name = "floors", required = false) Integer[] floors,
                                     @RequestParam(name = "photo", required = false) Boolean photo,
                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        FlatFilter flatFilter = new FlatFilter().setPriceFrom(priceFrom)
                .setPriceTo(priceTo)
                .setBedroomsFrom(bedroomsFrom)
                .setBedroomsTo(bedroomsTo)
                .setAreaFrom(areaFrom)
                .setAreaTo(areaTo)
                .setFloors(floors)
                .setPhoto(photo);
        return pageTransformer.transformPageOfFlatDtoFromPage(flatService.getAllFlats(flatFilter, pageable));
    }
}

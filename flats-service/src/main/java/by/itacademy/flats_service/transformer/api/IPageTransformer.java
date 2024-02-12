package by.itacademy.flats_service.transformer.api;

import by.itacademy.flats_service.core.dto.FlatDTO;
import by.itacademy.flats_service.core.dto.PageOfFlatDTO;
import org.springframework.data.domain.Page;

public interface IPageTransformer {

    PageOfFlatDTO transformPageOfFlatDtoFromPage(Page<FlatDTO> page);
}

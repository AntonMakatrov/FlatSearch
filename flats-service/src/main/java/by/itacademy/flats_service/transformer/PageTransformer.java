package by.itacademy.flats_service.transformer;

import by.itacademy.flats_service.core.dto.FlatDTO;
import by.itacademy.flats_service.core.dto.PageOfFlatDTO;
import by.itacademy.flats_service.transformer.api.IPageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageTransformer implements IPageTransformer {

    @Override
    public PageOfFlatDTO transformPageOfFlatDtoFromPage(Page<FlatDTO> page) {
        return (PageOfFlatDTO) new PageOfFlatDTO()
                .setContent(page.getContent())
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast());
    }
}

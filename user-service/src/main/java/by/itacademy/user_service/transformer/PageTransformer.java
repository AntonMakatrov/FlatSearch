package by.itacademy.user_service.transformer;

import by.itacademy.user_service.core.dto.PageDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.transformer.api.IPageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageTransformer implements IPageTransformer {

    @Override
    public PageDTO<UserDTO> transformPageDtoFromPage(Page<UserDTO> page) {
        return new PageDTO<UserDTO>().setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(page.getContent());
    }

}

package by.itacademy.user_service.transformer.impl;

import by.itacademy.user_service.core.dto.PageDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import by.itacademy.user_service.transformer.PageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageTransformerImpl implements PageTransformer {

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

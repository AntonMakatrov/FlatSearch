package by.itacademy.user_service.transformer.api;

import by.itacademy.user_service.core.dto.PageDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface IPageTransformer {

    PageDTO<UserDTO> transformPageDtoFromPage(Page<UserDTO> page);
}

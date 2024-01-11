package by.itacademy.user_service.transformer;

import by.itacademy.user_service.core.dto.PageDTO;
import by.itacademy.user_service.core.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface PageTransformer {

    PageDTO<UserDTO> transformPageDtoFromPage(Page<UserDTO> page);
}

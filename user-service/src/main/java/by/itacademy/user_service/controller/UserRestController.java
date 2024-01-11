package by.itacademy.user_service.controller;

import by.itacademy.user_service.core.dto.*;
import by.itacademy.user_service.core.entity.UserEntity;
import by.itacademy.user_service.service.api.IAuthorizationService;
import by.itacademy.user_service.service.api.IUserService;
import by.itacademy.user_service.service.api.IVerificationService;
import by.itacademy.user_service.transformer.PageTransformer;
import by.itacademy.user_service.transformer.UserTransformer;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final IUserService userService;
    private final IAuthorizationService authorizationService;
    private final IVerificationService verificationService;
    private final ModelMapper modelMapper;
    private final PageTransformer pageTransformer;
    private final UserTransformer userTransformer;

    public UserRestController(IUserService userService, IAuthorizationService authorizationService, IVerificationService verificationService, ModelMapper modelMapper, PageTransformer pageTransformer, UserTransformer userTransformer) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.verificationService = verificationService;
        this.modelMapper = modelMapper;
        this.pageTransformer = pageTransformer;
        this.userTransformer = userTransformer;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> createUser(@Validated @RequestBody UserCreateDTO user) {
        UserEntity userEntity = this.userTransformer.transformEntityFromCreateDto(user);
        this.userService.save(userEntity);
        return new ResponseEntity<>("Пользователь добавлен", HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseBody
    public PageDTO<UserDTO> getAllUsers(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return pageTransformer.transformPageDtoFromPage(userService.getAllUsers(pageable));
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    public UserDTO getUserById(@PathVariable("uuid") UUID id) {
        return convertToDto(this.userService.findById(id));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<String> update(
            @Validated @NotNull @PathVariable("uuid") UUID uuid,
            @PathVariable("dt_update") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dtUpdate,
            @Validated @RequestBody UserCreateDTO userCreateDTO
    ) {
        UserEntity userEntity = this.userTransformer.transformEntityFromCreateDto(userCreateDTO);
        this.userService.update(userEntity, uuid, dtUpdate);
        return ResponseEntity.ok("Пользователь обновлен");
    }


    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<String> registration(@RequestBody UserRegistrationDTO userRegDTO) {
        UserEntity userEntity = convertToEntity(userRegDTO);
        this.userService.save(userEntity);
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    @ResponseBody
    public ResponseEntity<String> verification(@RequestParam("code") String code,
                                               @RequestParam("mail") String mail) {
        VerificationDTO dto = new VerificationDTO(code, mail);
        verificationService.verify(dto);
        userService.activate(mail);
        return new ResponseEntity<>("Пользователь верифицирован", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authorizationService.login(userLoginDTO);
        return new ResponseEntity<>("Токен для авторизации " + token, HttpStatus.OK);
    }

    @GetMapping("/me")
    @ResponseBody
    public UserDTO me(){
        return userService.findInfoAbout();
    }

    private UserDTO convertToDto(Optional<UserEntity> entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    private UserEntity convertToEntity(UserCreateDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
    private UserDTO convertToDto(UserEntity entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    private UserEntity convertToEntity(UserRegistrationDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }


}

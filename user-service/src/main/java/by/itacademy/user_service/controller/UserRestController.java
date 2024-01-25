package by.itacademy.user_service.controller;

import by.itacademy.user_service.core.dto.*;
import by.itacademy.user_service.service.api.IAuthenticationService;
import by.itacademy.user_service.service.api.IVerificationService;
import by.itacademy.user_service.transformer.api.IPageTransformer;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import by.itacademy.user_service.service.api.IUserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final IUserService userService;
    private final IAuthenticationService authenticationService;
    private final IVerificationService verificationService;
    private final IPageTransformer pageTransformer;

    public UserRestController(IUserService userService, IAuthenticationService authenticationService, IVerificationService verificationService, ModelMapper modelMapper, IPageTransformer pageTransformer) {
        this.userService = userService;
        this.verificationService = verificationService;
        this.pageTransformer = pageTransformer;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> createUser(@Validated @RequestBody UserCreateDTO user) {
        this.userService.createUser(user);
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
        return this.userService.findById(id);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<String> update(
            @Validated @NotNull @PathVariable("uuid") UUID uuid,
            @PathVariable("dt_update") Long dtUpdate,
            @Validated @RequestBody UserCreateDTO userCreateDTO
    ) {
        LocalDateTime dtUpdated = LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdate), ZoneId.systemDefault());
        this.userService.update(userCreateDTO, uuid, dtUpdated);
        return ResponseEntity.ok("Пользователь обновлен");
    }


    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userRegDTO) {
        authenticationService.registerUser(userRegDTO);
        return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    @ResponseBody
    public ResponseEntity<String> verify(@RequestParam("code") String code,
                                               @RequestParam("mail") String mail) {
        VerificationDTO dto = new VerificationDTO(code, mail);
        verificationService.verify(dto);
        userService.activate(mail);
        return new ResponseEntity<>("Пользователь верифицирован", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authenticationService.login(userLoginDTO);
        return new ResponseEntity<>("Токен для авторизации " + token, HttpStatus.OK);
    }

    @GetMapping("/me")
    @ResponseBody
    public UserDTO getInfoAboutMe(){
        return userService.findInfoAbout();
    }

}

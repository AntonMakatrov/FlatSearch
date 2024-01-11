package by.itacademy.user_service.service.api;

public interface IUserPasswordEncoder {

    String encodePassword(String plainPassword);

    Boolean passwordMatches(String plainPassword, String encodedPassword);
}

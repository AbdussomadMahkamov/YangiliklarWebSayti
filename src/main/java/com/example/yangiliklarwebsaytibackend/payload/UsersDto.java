package com.example.yangiliklarwebsaytibackend.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsersDto {
    @NotNull(message = "Ismni to'ldiring")
    private String ism;
    @NotNull(message = "Familyani to'ldiring")
    private String familya;
    @NotNull(message = "Usernameni to'ldiring")
    private String username;
    @NotNull(message = "Parolni to'ldiring")
    private String password;
    @NotNull(message = "Telefon raqamni to'ldiring")
    private String telRaqam;
    @NotNull(message = "Parolni qayta to'ldiring")
    private String repassword;
}

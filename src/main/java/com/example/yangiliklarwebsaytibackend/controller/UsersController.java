package com.example.yangiliklarwebsaytibackend.controller;

import com.example.yangiliklarwebsaytibackend.payload.*;
import com.example.yangiliklarwebsaytibackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/Users")
public class UsersController {
    @Autowired
    UsersService usersService;
//    @PreAuthorize(value = "hasAuthority('ADDUSER')")
    @PostMapping("/registr")
    public HttpEntity<?> Registr(@Valid @RequestBody UsersDto usersDto){
        ApiResponse apiResponse=usersService.usersRegistr(usersDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:400).body(apiResponse.getXabar());
    }
    @GetMapping("/tasdiqlash")
    public HttpEntity<?> Tasdiqlash(@RequestParam String email, @RequestParam String emailkod){
        ApiResponse apiResponse=usersService.Faollashtirish(email, emailkod);
        return ResponseEntity.status(apiResponse.isHolat()? 200: 409).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITUSER')")
    @PutMapping("/tahrirlash/{id}")
    public HttpEntity<?> Tahrirlash(@PathVariable Integer id, @Valid @RequestBody UsersDto usersDto){
        ApiResponse apiResponse=usersService.Tahrirlash(id, usersDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:409).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITMYINFO')")
    @PutMapping("/tahrirlashProfil/{id}")
    public HttpEntity<?> TahrirlashProfil(@PathVariable Integer id, @Valid @RequestBody UsersDto usersDto){
        ApiResponse apiResponse=usersService.TahrirlashProfil(id, usersDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:409).body(apiResponse.getXabar());
    }
    @PostMapping("/userslogin")
    public HttpEntity<?> UsersLogin(@Valid @RequestBody LoginDto loginDto){
        ApiResponse apiResponse=usersService.usersLogin(loginDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('LAVOZIMTAYINLASH')")
    @PutMapping("/lavozimTayinlash/{username}")
    public HttpEntity<?> lavozimTayinlash(@PathVariable String username, @RequestBody UsersEditRoleDto usersEditRoleDto){
        ApiResponse apiResponse=usersService.lavozimAdd(username, usersEditRoleDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
}

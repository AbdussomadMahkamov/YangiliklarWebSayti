package com.example.yangiliklarwebsaytibackend.controller;

import com.example.yangiliklarwebsaytibackend.payload.ApiResponse;
import com.example.yangiliklarwebsaytibackend.payload.LavozimDto;
import com.example.yangiliklarwebsaytibackend.service.LavozimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lavozim")
public class LavozimController {
    @Autowired
    LavozimService lavozimService;
    @PreAuthorize(value = "hasAuthority('ADDROLL')")
    @PostMapping("/lavozimAdd")
    public HttpEntity<?> LavozimAdd(@RequestBody LavozimDto lavozimDto){
        ApiResponse apiResponse=lavozimService.lavozimAdd(lavozimDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITROOL')")
    @PutMapping("/lavozimTahrirlash/{id}")
    public HttpEntity<?> LavozimEdit(@PathVariable Integer id, @RequestBody LavozimDto lavozimDto){
        ApiResponse apiResponse=lavozimService.lavozimEdit(id, lavozimDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READROOL')")
    @GetMapping("/lavozimOqish")
    public HttpEntity<?> LavozimOqish(){
        ApiResponse apiResponse=lavozimService.lavozimOqish();
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READROOL')")
    @GetMapping("/lavozimOqishIdlab/{id}")
    public HttpEntity<?> LavozimOqishId(@PathVariable Integer id){
        ApiResponse apiResponse=lavozimService.lavozimOqishId(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('DELETEROOL')")
    @DeleteMapping("/lavozimOchirish/{id}")
    public HttpEntity<?> LavozimOchirish(@PathVariable Integer id){
        ApiResponse apiResponse=lavozimService.lavozimOchirish(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
}

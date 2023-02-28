package com.example.yangiliklarwebsaytibackend.controller;

import com.example.yangiliklarwebsaytibackend.payload.ApiResponse;
import com.example.yangiliklarwebsaytibackend.payload.PostDto;
import com.example.yangiliklarwebsaytibackend.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    PostsService postsService;
    @PreAuthorize(value = "hasAuthority('ADDPOST')")
    @PostMapping("/postJoylash")
    public HttpEntity<?> PostJoylash(@RequestBody PostDto postDto){
        ApiResponse apiResponse=postsService.postJoylash(postDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READPOST')")
    @GetMapping("/postOqish")
    public HttpEntity<?> PostOqish(){
        ApiResponse apiResponse=postsService.postOqish();
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITPOST')")
    @PutMapping("/postTahrirlash/{id}")
    public HttpEntity<?> PostTahrirlash(@PathVariable Integer id, @RequestBody PostDto postDto){
        ApiResponse apiResponse=postsService.postTahrirlash(id, postDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITMYPOST')")
    @PutMapping("/mypostTahrirlash/{id}")
    public HttpEntity<?> MyPostTahrirlash(@PathVariable Integer id, @RequestBody PostDto postDto){
        ApiResponse apiResponse=postsService.myPostTahrirlash(id, postDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('DELETEPOST')")
    @DeleteMapping("/postOchirish/{id}")
    public HttpEntity<?> PostOchirish(@PathVariable Integer id){
        ApiResponse apiResponse=postsService.postOchirish(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('DELETEMYPOST')")
    @DeleteMapping("/mypostOchirish/{id}")
    public HttpEntity<?> MyPostOchirish(@PathVariable Integer id){
        ApiResponse apiResponse=postsService.myPostOchirish(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
}

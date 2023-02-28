package com.example.yangiliklarwebsaytibackend.controller;

import com.example.yangiliklarwebsaytibackend.payload.ApiResponse;
import com.example.yangiliklarwebsaytibackend.payload.CommentsDto;
import com.example.yangiliklarwebsaytibackend.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    CommentsService commentsService;
    @PreAuthorize(value = "hasAuthority('ADDCOMMENT')")
    @PostMapping("/addComment/{idpost}")
    public HttpEntity<?> AddComment(@RequestBody CommentsDto commentsDto, @PathVariable Integer idpost){
        ApiResponse apiResponse=commentsService.addComment(commentsDto, idpost);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READCOMMENT')")
    @GetMapping("/readComments")
    public HttpEntity<?> ReadComment(){
        ApiResponse apiResponse=commentsService.readComment();
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('READCOMMENT')")
    @GetMapping("/readIdComment/{id}")
    public HttpEntity<?> ReadIdComment(@PathVariable Integer id){
        ApiResponse apiResponse=commentsService.readIdComment(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITCOMMENT')")
    @PutMapping("/editComment/{id}")
    public HttpEntity<?> EditComment(@PathVariable Integer id, @RequestBody CommentsDto commentsDto){
        ApiResponse apiResponse=commentsService.editComment(id, commentsDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('EDITMYCOMMENT')")
    @PutMapping("/editMyComment/{id}")
    public HttpEntity<?> EditMyComment(@PathVariable Integer id, @RequestBody CommentsDto commentsDto){
        ApiResponse apiResponse=commentsService.editMyComment(id, commentsDto);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('DELETECOMMENT')")
    @DeleteMapping("/deleteComment/{id}")
    public HttpEntity<?> DeleteComment(@PathVariable Integer id){
        ApiResponse apiResponse=commentsService.deleteComment(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
    @PreAuthorize(value = "hasAuthority('DELETEMYCOMMENT')")
    @DeleteMapping("/deleteMyComment/{id}")
    public HttpEntity<?> DeleteMyComment(@PathVariable Integer id){
        ApiResponse apiResponse=commentsService.deleteMyComment(id);
        return ResponseEntity.status(apiResponse.isHolat()?200:208).body(apiResponse.getXabar());
    }
}

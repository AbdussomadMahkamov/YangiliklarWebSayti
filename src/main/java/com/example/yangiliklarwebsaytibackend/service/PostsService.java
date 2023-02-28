package com.example.yangiliklarwebsaytibackend.service;

import com.example.yangiliklarwebsaytibackend.entity.Comments;
import com.example.yangiliklarwebsaytibackend.entity.Posts;
import com.example.yangiliklarwebsaytibackend.entity.Users;
import com.example.yangiliklarwebsaytibackend.payload.ApiResponse;
import com.example.yangiliklarwebsaytibackend.payload.PostDto;
import com.example.yangiliklarwebsaytibackend.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostsService {
    @Autowired
    PostsRepository postsRepository;
    public ApiResponse postJoylash(PostDto postDto) {
        Posts posts=new Posts(
                postDto.getSarlavha(),
                postDto.getPostmatn(),
                postDto.getUrl()
        );
        postsRepository.save(posts);
        return new ApiResponse("Post muvoffaqiyatli qo'shildi", true);
    }

    public ApiResponse postOqish() {
        String matn="";
        for (Posts posts : postsRepository.findAll()) {
            matn+=posts+"\n";
        }
        return new ApiResponse(matn, true);
    }

    public ApiResponse postTahrirlash(Integer id, PostDto postDto ) {
        Optional<Posts> byId = postsRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Kechirasiz bunday id mavjud emas!", false);
        }
        Posts posts = byId.get();
        posts.setSarlavha(postDto.getSarlavha());
        posts.setPostmatn(postDto.getPostmatn());
        posts.setUrl(postDto.getUrl());
        postsRepository.save(posts);
        return new ApiResponse("Ma'lumotlar muvoffaqiyatli tahrirlandi", true);
    }

    public ApiResponse postOchirish(Integer id) {
        Optional<Posts> byId = postsRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Kechirasiz bunday idda ma'lumot topilmadi", false);
        }
        postsRepository.deleteById(id);
        return new ApiResponse("Post muvoffaqiyatli o'chirildi", true);
    }

    public ApiResponse myPostTahrirlash(Integer id, PostDto postDto) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Posts> byIdAndYaratuvchiId = postsRepository.findByIdAndYaratuvchiId(id, users.getId());
        if (!byIdAndYaratuvchiId.isPresent()){
            return new ApiResponse("Kechirasiz siz bu idli postni tahrirlay olmaysiz olmaysiz", false);
        }
        Posts posts = byIdAndYaratuvchiId.get();
        posts.setSarlavha(postDto.getSarlavha());
        posts.setPostmatn(postDto.getPostmatn());
        posts.setUrl(postDto.getUrl());
        postsRepository.save(posts);
        return new ApiResponse("Ma'lumotlar muvoffaqiyatli tahrirlandi", true);
    }

    public ApiResponse myPostOchirish(Integer id) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Posts> byIdAndYaratuvchiId = postsRepository.findByIdAndYaratuvchiId(id, users.getId());
        if (!byIdAndYaratuvchiId.isPresent()){
            return new ApiResponse("Kechirasiz siz bu idli postni o'chira olmaysiz olmaysiz", false);
        }
        postsRepository.deleteById(id);
        return new ApiResponse("Siz postingizni muvoffaqiyatli o'chirdingiz!", true);
    }
}

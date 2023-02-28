package com.example.yangiliklarwebsaytibackend.service;

import com.example.yangiliklarwebsaytibackend.entity.Comments;
import com.example.yangiliklarwebsaytibackend.entity.Posts;
import com.example.yangiliklarwebsaytibackend.entity.Users;
import com.example.yangiliklarwebsaytibackend.payload.ApiResponse;
import com.example.yangiliklarwebsaytibackend.payload.CommentsDto;
import com.example.yangiliklarwebsaytibackend.repository.CommentsRepository;
import com.example.yangiliklarwebsaytibackend.repository.PostsRepository;
import com.example.yangiliklarwebsaytibackend.repository.UsersRepository;
import com.example.yangiliklarwebsaytibackend.settings.KimYozganiniQaytarish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentsService {
    @Autowired
    CommentsRepository commentsRepository;
    @Autowired
    PostsRepository postsRepository;
    @Autowired
    KimYozganiniQaytarish kimYozganiniQaytarish;
    @Autowired
    UsersRepository usersRepository;

    public ApiResponse addComment(CommentsDto commentsDto, Integer id) {
        Optional<Posts> byId = postsRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Kechirasiz bunday idli post mavjud emas. Siz commentariya qoldira olmaysiz!", false);
        }
        Comments comments=new Comments(
                commentsDto.getComment(),
                byId.get()
        );
        commentsRepository.save(comments);
        return new ApiResponse("Commentariya muvoffaqiyatli saqlandi", true);
    }

    public ApiResponse readComment() {
        String matn="";
        for (Comments comments : commentsRepository.findAll()) {
            matn+=comments+"\n";
        }
        return new ApiResponse(matn, true);
    }

    public ApiResponse readIdComment(Integer id) {
        Optional<Comments> byId = commentsRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Kechirasiz bunday idli commentariya mavjud emas!", false);
        }
        return new ApiResponse(byId.get().toString(), true);
    }

    public ApiResponse editComment(Integer id, CommentsDto commentsDto) {
        Optional<Comments> byId = commentsRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Kechirasiz bunday idli commentariya mavjud emas!", false);
        }
        Comments comments=byId.get();
        comments.setComment(commentsDto.getComment());
        commentsRepository.save(comments);
        return new ApiResponse("Commentariya muvoffaqiyatli tahrirlandi!", true);
    }

    public ApiResponse editMyComment(Integer id, CommentsDto commentsDto) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comments> byIdAndYaratuvchiId = commentsRepository.findByIdAndYaratuvchiId(id, users.getId());
        if (!byIdAndYaratuvchiId.isPresent()){
            return new ApiResponse("Kechirasiz siz bu idli commentariyani tahrirlay olmaysiz", false);
        }
        Comments comments = byIdAndYaratuvchiId.get();
        comments.setComment(commentsDto.getComment());
        commentsRepository.save(comments);
        return new ApiResponse("Commentariya muvoffaqiyatli tahrirlandi", true);
    }

    public ApiResponse deleteComment(Integer id) {
        boolean b = commentsRepository.existsById(id);
        if (!b){
            return new ApiResponse("Kechirasiz bunday idli commentariya mavjud emas!", false);
        }
        commentsRepository.deleteById(id);
        return new ApiResponse("Commentariya muvoffaqiyatli o'chirildi.", true);
    }

    public ApiResponse deleteMyComment(Integer id) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Comments> byIdAndYaratuvchiId = commentsRepository.findByIdAndYaratuvchiId(id, users.getId());
        if (!byIdAndYaratuvchiId.isPresent()){
            return new ApiResponse("Kechirasiz siz bu idli commentariyani o'chira olmaysiz", false);
        }
        commentsRepository.deleteById(id);
        return new ApiResponse("Siz commentariyani muvoffaqiyatli o'chirdingiz", true);
    }
}

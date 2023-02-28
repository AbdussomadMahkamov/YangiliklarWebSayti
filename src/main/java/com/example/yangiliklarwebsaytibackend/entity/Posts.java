package com.example.yangiliklarwebsaytibackend.entity;

import com.example.yangiliklarwebsaytibackend.entity.templated.AbstaktEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Posts extends AbstaktEntity {
    @Column(nullable = false)
    private String sarlavha;
    @Column(nullable = false, columnDefinition = "text")
    private String postmatn;
    @Column(nullable = false)
    private String url;

}

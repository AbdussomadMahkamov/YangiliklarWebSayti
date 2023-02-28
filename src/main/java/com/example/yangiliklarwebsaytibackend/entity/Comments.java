package com.example.yangiliklarwebsaytibackend.entity;

import com.example.yangiliklarwebsaytibackend.entity.templated.AbstaktEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Comments extends AbstaktEntity {
    @Column(nullable = false)
    private String comment;
    @ManyToOne
    private Posts posts;
}

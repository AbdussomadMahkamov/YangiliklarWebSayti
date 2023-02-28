package com.example.yangiliklarwebsaytibackend.entity;

import com.example.yangiliklarwebsaytibackend.entity.enums.Huquqlar;
import com.example.yangiliklarwebsaytibackend.entity.templated.AbstaktEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Lavozim extends AbstaktEntity {
    private String nomi;
    private String izohi;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Huquqlar> huquqlarList;


}
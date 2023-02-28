package com.example.yangiliklarwebsaytibackend.payload;

import com.example.yangiliklarwebsaytibackend.entity.enums.Huquqlar;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;
@Data
public class LavozimDto {
    private String nomi;
    private String izohi;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Huquqlar> huquqlarList;
}

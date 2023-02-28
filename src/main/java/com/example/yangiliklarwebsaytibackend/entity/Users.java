package com.example.yangiliklarwebsaytibackend.entity;

import com.example.yangiliklarwebsaytibackend.entity.enums.Huquqlar;
import com.example.yangiliklarwebsaytibackend.entity.templated.AbstaktEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users extends AbstaktEntity implements UserDetails {
    @Column(nullable = false)
    private String ism;
    @Column(nullable = false)
    private String familya;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String telRaqam;
    private String emailKod;
    @ManyToOne
    private Lavozim lavozim;
    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=false;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Huquqlar> huquqlarList = this.lavozim.getHuquqlarList();
        List<GrantedAuthority> grantedAuthorityList=new ArrayList<>();
        for (Huquqlar huquqlar : huquqlarList) {
            grantedAuthorityList.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return huquqlar.name();
                }
            });
        }
        return grantedAuthorityList;
    }

    public Users(String ism, String familya, String username, String password, String telRaqam, Lavozim lavozim, boolean enabled) {
        this.ism = ism;
        this.familya = familya;
        this.username = username;
        this.password = password;
        this.telRaqam = telRaqam;
        this.lavozim = lavozim;
        this.enabled= enabled;
    }



}

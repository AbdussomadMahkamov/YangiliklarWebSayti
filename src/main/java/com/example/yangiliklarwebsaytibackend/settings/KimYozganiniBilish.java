package com.example.yangiliklarwebsaytibackend.settings;

import com.example.yangiliklarwebsaytibackend.entity.Users;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class KimYozganiniBilish implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){
            Users principal = (Users) authentication.getPrincipal();
            return  Optional.of(principal.getId());
        }
        return Optional.empty();
    }
}

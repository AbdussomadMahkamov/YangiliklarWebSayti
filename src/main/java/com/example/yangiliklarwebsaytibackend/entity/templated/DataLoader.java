package com.example.yangiliklarwebsaytibackend.entity.templated;

import com.example.yangiliklarwebsaytibackend.entity.Lavozim;
import com.example.yangiliklarwebsaytibackend.entity.Users;
import com.example.yangiliklarwebsaytibackend.entity.enums.Huquqlar;
import com.example.yangiliklarwebsaytibackend.repository.LavozimRepository;
import com.example.yangiliklarwebsaytibackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.yangiliklarwebsaytibackend.entity.enums.Huquqlar.*;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    LavozimRepository lavozimRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UsersRepository usersRepository;
    @Value(value = "${spring.sql.init.mode}")
    private  String boshlangichBoshqaruv;
    @Override
    public void run(String... args) throws Exception {
        if (boshlangichBoshqaruv.equals("always")){
            Huquqlar[] huquqlar = Huquqlar.values();
            Lavozim admin = lavozimRepository.save(new Lavozim(LavozimConstanta.ADMIN,"Platforma egasi.", Arrays.asList(huquqlar)));
            Lavozim user = lavozimRepository.save(new Lavozim(LavozimConstanta.USER, "Oddiy foydalanuvchi.", Arrays.asList(ADDCOMMENT, EDITMYCOMMENT, READCOMMENT,  DELETEMYCOMMENT, READPOST, EDITMYINFO)));
            usersRepository.save(new Users("Abdussomad", "Mahkamov", "mahkamovabdussomad@gmail.com", passwordEncoder.encode("23022000"), "998943562131", admin, true));
            usersRepository.save(new Users("Ali", "Valiyev", "valiyevali@gmail.com", passwordEncoder.encode("12345678"),"998901234567", user, true));
        }
    }
}

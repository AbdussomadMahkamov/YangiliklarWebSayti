package com.example.yangiliklarwebsaytibackend.service;

import com.example.yangiliklarwebsaytibackend.entity.Lavozim;
import com.example.yangiliklarwebsaytibackend.entity.Users;
import com.example.yangiliklarwebsaytibackend.entity.templated.LavozimConstanta;
import com.example.yangiliklarwebsaytibackend.payload.*;
import com.example.yangiliklarwebsaytibackend.repository.LavozimRepository;
import com.example.yangiliklarwebsaytibackend.repository.UsersRepository;
import com.example.yangiliklarwebsaytibackend.token.TokenGenerate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LavozimRepository lavozimRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenGenerate tokenGenerate;
    public ApiResponse usersRegistr(UsersDto usersDto) {
        if (usersRepository.existsByUsername(usersDto.getUsername())){
            return new ApiResponse("Bunday username ro'yxatdan o'tkazilgan!", false);
        }
        if (usersDto.getPassword().equals(usersDto.getRepassword())){
            if (usersRepository.existsByTelRaqam(usersDto.getTelRaqam())){
                return new ApiResponse("Bunday telefon raqam oldin ro'yxatdan o'tkazilgan", false);
            }
            Users users=new Users();
            users.setIsm(usersDto.getIsm());
            users.setFamilya(usersDto.getFamilya());
            users.setUsername(usersDto.getUsername());
            users.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            users.setTelRaqam(usersDto.getTelRaqam());
            users.setLavozim(lavozimRepository.findByNomi(LavozimConstanta.USER).get());
            String code= UUID.randomUUID().toString().substring(0,6);
            users.setEmailKod(code);
            boolean b=XabarYuborish(usersDto.getUsername(),code);
            if (b){
                usersRepository.save(users);
                return new ApiResponse("Ro'yxatdan o'tdingiz, Hisobni faollashtirish uchun emailga xabar yuborildi", true);
            }
            return new ApiResponse("Email adresingizda xatolik mavjud. Qayta tekshirib urinib ko'ring!", false);
        }
        return new ApiResponse("Parollar mos emas", false);
    }
    public boolean XabarYuborish(String email, String emailkod){
        try {
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setFrom("mahkamovabdussomad@gmail.com");
            simpleMailMessage.setSubject("Tasdiqlash kodi: ");
            simpleMailMessage.setText("<a href='http://localhost:8080/Users/tasdiqlash?email="+email+"&emailkod="+emailkod+"'>Emailni tasdiqlash</a>");
            javaMailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }

    public ApiResponse Faollashtirish(String email, String emailkod) {
        Optional<Users> byUsernameAndEmailKod = usersRepository.findByUsernameAndEmailKod(email, emailkod);
        if (byUsernameAndEmailKod.isPresent()){
            Users users=byUsernameAndEmailKod.get();
            users.setEmailKod(null);
            users.setEnabled(true);
            usersRepository.save(users);
            return new ApiResponse("Hisobingiz muvoffaqiyatli faollashtirildi", true);
        }
        return new ApiResponse("Siz hisobni oldin faollashtirgansiz", false);
    }

    public ApiResponse Tahrirlash(Integer id, UsersDto usersDto) {
        Optional<Users> byId = usersRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Bunday idli foydalanuvchi topilmadi!", false);
        }
        Users users=byId.get();
        users.setIsm(usersDto.getIsm());
        users.setFamilya(usersDto.getFamilya());
        if (!usersDto.getPassword().equals(usersDto.getRepassword())){
            return new ApiResponse("Parollarni qayta tekshiring", false);
        }
        users.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        usersRepository.save(users);
        return new ApiResponse("Foydalanuvchi muvoffaqiyatli tahrirlandi", true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> byUsername = usersRepository.findByUsername(username);
        if (byUsername.isPresent()){
            return byUsername.get();
        }
        throw new UsernameNotFoundException("Bunday foydalanuvchi mavjud emas");
    }

    public ApiResponse usersLogin(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if (authenticate.isAuthenticated()){
            Optional<Users> byUsernameAndEmailKod = usersRepository.findByUsernameAndEmailKod(loginDto.getUsername(), null);
            if (byUsernameAndEmailKod.isPresent()){
                Users principal = (Users) authenticate.getPrincipal();
                return new ApiResponse("Profilga xush kelibsiz "+byUsernameAndEmailKod.get().getIsm()+"!\n"+tokenGenerate.TokenOlish(principal.getUsername(), principal.getLavozim()),true);
            }
            return new ApiResponse("Accountingiz faollashtirilmagan", false);
        }
        return new ApiResponse("Login yoki parol xato", false);
    }

    public ApiResponse lavozimAdd(String username, UsersEditRoleDto usersEditRoleDto) {
        Optional<Users> byUsername = usersRepository.findByUsername(username);
        if (!byUsername.isPresent()){
            return new ApiResponse("Bunday foydalanuvchi mavjud emas", false);
        }
        Optional<Lavozim> byNomi = lavozimRepository.findByNomi(usersEditRoleDto.getLavozimNomi());
        if (!byNomi.isPresent()){
            return new ApiResponse("Bunday lavozim mavjud emas", false);
        }
        Lavozim lavozim=byNomi.get();
        Users users=byUsername.get();
        users.setLavozim(lavozim);
        usersRepository.save(users);
        return new ApiResponse("Foydalanuvchiga lavozim muvoffaqiyatli tahrirlandi", true);
    }

    public ApiResponse TahrirlashProfil(Integer id, UsersDto usersDto) {
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!users.getId().equals(id)){
            return new ApiResponse("Siz tahrirlashni amalga oshira olmaysiz!", false);
        }
        Optional<Users> byId = usersRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Bunday idli foydalanuvchi topilmadi!", false);
        }
        Users users1=byId.get();
        users1.setIsm(usersDto.getIsm());
        users1.setFamilya(usersDto.getFamilya());
        if (!usersDto.getPassword().equals(usersDto.getRepassword())){
            return new ApiResponse("Parollarni qayta tekshiring", false);
        }
        users1.setPassword(passwordEncoder.encode(usersDto.getPassword()));
        usersRepository.save(users1);
        return new ApiResponse("Foydalanuvchi muvoffaqiyatli tahrirlandi", true);
    }
}

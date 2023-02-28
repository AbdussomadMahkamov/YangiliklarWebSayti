package com.example.yangiliklarwebsaytibackend.service;

import com.example.yangiliklarwebsaytibackend.entity.Lavozim;
import com.example.yangiliklarwebsaytibackend.payload.ApiResponse;
import com.example.yangiliklarwebsaytibackend.payload.LavozimDto;
import com.example.yangiliklarwebsaytibackend.repository.LavozimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LavozimService {
    @Autowired
    LavozimRepository lavozimRepository;

    public ApiResponse lavozimAdd(LavozimDto lavozimDto) {
//        boolean b = lavozimRepository.existsByNomi(lavozimDto.getNomi());
        if (lavozimRepository.existsByNomi(lavozimDto.getNomi())){
            return new ApiResponse("Bunday lavozim oldin yaratilgan.", false);
        }
//        Lavozim lavozim=new Lavozim();
//        lavozim.setNomi(lavozimDto.getNomi());
//        lavozim.setIzohi(lavozimDto.getIzohi());
//        lavozim.setHuquqlarList(lavozimDto.getHuquqlarList());
//        lavozimRepository.save(lavozim);
        Lavozim lavozim=new Lavozim(
                lavozimDto.getNomi(),
                lavozimDto.getIzohi(),
                lavozimDto.getHuquqlarList()
        );
        lavozimRepository.save(lavozim);
        return new ApiResponse("Lavozim muvoffaqiyatli saqlandi", true);
    }

    public ApiResponse lavozimEdit(Integer id, LavozimDto lavozimDto) {
        if (id==1){
            return new ApiResponse("Siz tizim admin lavozimini tahrir qilolmaysiz.", false);
        }
        Optional<Lavozim> byId = lavozimRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Bunday idli lavozim mavjud emas",false);
        }
        Lavozim lavozim = byId.get();
        lavozim.setNomi(lavozimDto.getNomi());
        lavozim.setIzohi(lavozimDto.getIzohi());
        lavozim.setHuquqlarList(lavozimDto.getHuquqlarList());
        lavozimRepository.save(lavozim);
        return new ApiResponse("Ma'lumotlar tahrirlandi", true);
    }

    public ApiResponse lavozimOqish() {
        String lavozimlar="";
        for (Lavozim lavozim : lavozimRepository.findAll()) {
            lavozimlar+=lavozim.toString()+"\n***************************************************\n";
        }
        return new ApiResponse(lavozimlar, true);
    }

    public ApiResponse lavozimOqishId(Integer id) {
        Optional<Lavozim> byId = lavozimRepository.findById(id);
        if (byId.isPresent()){
            return new ApiResponse(byId.toString(), true);
        }
        return new ApiResponse("Bunday idli lavozim topilmadi", false);
    }

    public ApiResponse lavozimOchirish(Integer id) {
        if (id==1){
            return new ApiResponse("Siz tizmi adminini o'chira olmaysiz!!!", false);
        }
        Optional<Lavozim> byId = lavozimRepository.findById(id);
        if (!byId.isPresent()){
            return new ApiResponse("Bunday idli lavozim topilmadi", false);
        }
        lavozimRepository.deleteById(id);
        return new ApiResponse("Muvoffaqiyatli o'chirildi", true);
    }
}

package com.invocation.server.service;

import com.invocation.server.dto.*;
import com.invocation.server.entity.Professional;
import com.invocation.server.entity.SupportCenter;
import com.invocation.server.entity.Users;
import com.invocation.server.repository.ProfessionalRepo;
import com.invocation.server.repository.SupportCenterRepo;
import com.invocation.server.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GlobalsService {
    @Autowired
    private ProfessionalRepo profRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private SupportCenterRepo supportCenterRepo;

    public ResponceErrorServerDto getAllProfessionals(ResponceProfessionalsDto responceProfessionalsDto) {
        try {
            List<ProfessionalsDto> professionalDto = new ArrayList<>();
            List<Professional> professionals = profRepo.findAll();
            for (Professional professional : professionals) {
                Users user = usersRepo.findById(professional.getUser().getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));

                ProfessionalsDto professionalsDto = new ProfessionalsDto();
                professionalsDto.setEmail(user.getEmail());
                professionalsDto.setFull_name(user.getFullName());
                professionalsDto.setContact_number(user.getContactNumber());
                professionalsDto.setInfo(professional.getInfo());
                professionalsDto.setFk_center(professional.getCenter().getName());
                professionalsDto.setProfessionals_rating(professional.getAverageRating());
                professionalsDto.setCenter_rating(professional.getCenter().getAverageRating());
                professionalDto.add(professionalsDto);
            }
            responceProfessionalsDto.setProfessionals(professionalDto);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto getRandomProfessionals(ResponceProfessionalsDto responceProfessionalsDto) {
        try {
            List<ProfessionalsDto> professionalDto = new ArrayList<>();
            List<Professional> allProfessionals = profRepo.findAll();

            List<Professional> highRatingPros = new ArrayList<>();
            List<Professional> lowRatingPros = new ArrayList<>();

            for (Professional professional : allProfessionals) {
                if (professional.getAverageRating() > 2.5) {
                    highRatingPros.add(professional);
                }
                else {
                    lowRatingPros.add(professional);
                }
            }

            Collections.shuffle(highRatingPros);
            Collections.shuffle(lowRatingPros);

            int needed = 10;
            int addedFromHigh = Math.min(needed, highRatingPros.size());

            for (int i = 0; i < addedFromHigh; i++) {
                Professional professional = highRatingPros.get(i);
                Users user = usersRepo.findById(professional.getUser().getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));

                ProfessionalsDto professionalsDto = new ProfessionalsDto();
                professionalsDto.setEmail(user.getEmail());
                professionalsDto.setFull_name(user.getFullName());
                professionalsDto.setContact_number(user.getContactNumber());
                professionalsDto.setInfo(professional.getInfo());
                professionalsDto.setFk_center(professional.getCenter().getName());
                professionalsDto.setProfessionals_rating(professional.getAverageRating());
                professionalsDto.setCenter_rating(professional.getCenter().getAverageRating());
                professionalDto.add(professionalsDto);
            }

            if (addedFromHigh < needed) {
                int remaining = needed - addedFromHigh;
                int addedFromLow = Math.min(remaining, lowRatingPros.size());

                for (int i = 0; i < addedFromLow; i++) {
                    Professional professional = lowRatingPros.get(i);
                    Users user = usersRepo.findById(professional.getUser().getId())
                            .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));

                    ProfessionalsDto professionalsDto = new ProfessionalsDto();
                    professionalsDto.setEmail(user.getEmail());
                    professionalsDto.setFull_name(user.getFullName());
                    professionalsDto.setContact_number(user.getContactNumber());
                    professionalsDto.setInfo(professional.getInfo());
                    professionalsDto.setFk_center(professional.getCenter().getName());
                    professionalsDto.setProfessionals_rating(professional.getAverageRating());
                    professionalsDto.setCenter_rating(professional.getCenter().getAverageRating());
                    professionalDto.add(professionalsDto);
                }
            }

            responceProfessionalsDto.setProfessionals(professionalDto);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto getCenters(ResponceSupportCenterDto responceSupportCenterDto){
        try {
            List<SupportCenterDto> supportCenterDtos = new ArrayList<>();
            List<SupportCenter> supportCenters = supportCenterRepo.findAll();

            for(SupportCenter supportCenter : supportCenters){
                SupportCenterDto supportCenterDto = new SupportCenterDto();
                supportCenterDto.setName(supportCenter.getName());
                supportCenterDto.setEmail(supportCenter.getEmail());
                supportCenterDto.setAddress(supportCenter.getAddress());
                supportCenterDto.setContact_number(supportCenter.getContactNumber());
                supportCenterDto.setRating(supportCenter.getAverageRating());
                supportCenterDto.setAvatar(supportCenter.getAvatar());
                supportCenterDtos.add(supportCenterDto);
            }
            responceSupportCenterDto.setSupportCenters(supportCenterDtos);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }
}

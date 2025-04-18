package com.invocation.server.service;

import com.invocation.server.dto.*;
import com.invocation.server.entity.*;
import com.invocation.server.repository.*;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private ProblemProcessingRepo problemProcessingRepo;
    @Autowired
    private ProblemRepo problemRepo;

    public ResponceErrorServerDto getAllProfessionals(ResponceProfessionalsDto responceProfessionalsDto) {
        try {
            List<ProfessionalsDto> professionalDto = new ArrayList<>();
            List<Professional> professionals = profRepo.findAll();
            for (Professional professional : professionals) {
                Users user = usersRepo.findById(professional.getUser().getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));

                ProfessionalsDto professionalsDto = new ProfessionalsDto();
                professionalsDto.setId(user.getId());
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
                supportCenterDto.setId(supportCenter.getId());
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

    public ResponceErrorServerDto acceptProblem(int id){
        try {
            Users user = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            ProblemProcessing problemProcessing = problemProcessingRepo.findById(2)
                    .orElseThrow(() -> new UsernameNotFoundException("Процесс не найден"));
            Problem problem = problemRepo.findById(user.getProblem().getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Проблема не найдена"));
            problem.setProblemProcessing(problemProcessing);
            problemRepo.save(problem);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    @Transactional
    public ResponceErrorServerDto refuseProblem(int id) {
        try {
            Users user = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

            problemRepo.deleteById(user.getProblem().getId());
            user.setProblem(null);
            usersRepo.save(user);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(500);
        }
    }

    public ResponceErrorServerDto getUsersCitizen(ResponceUsersCitizenDto responceUsersCitizenDto){
        try {
            List<Users> users = usersRepo.findByRoleId(1);
            List<UsersCitizenDto> usersCitizenDtos = new ArrayList<>();
            for(Users user : users){
                UsersCitizenDto usersCitizenDto = new UsersCitizenDto();
                usersCitizenDto.setId(user.getId());
                usersCitizenDto.setLogin(user.getLogin());
                usersCitizenDto.setEmail(user.getEmail());
                usersCitizenDto.setFullName(user.getFullName());
                usersCitizenDto.setAddress(user.getAddress());
                usersCitizenDto.setRole(user.getRole().getRoleName());
                usersCitizenDto.setBaned(user.getIsBanned());
                usersCitizenDtos.add(usersCitizenDto);
            }
            responceUsersCitizenDto.setUsers(usersCitizenDtos);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(500);
        }
    }
}

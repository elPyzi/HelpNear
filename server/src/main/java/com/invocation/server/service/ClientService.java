package com.invocation.server.service;

import com.invocation.server.dto.ConclusionUserDto;
import com.invocation.server.dto.RequestMakeApplication;
import com.invocation.server.dto.ResponceConclusionUserDto;
import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.entity.*;
import com.invocation.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ProblemProcessingRepo problemProcessingRepo;
    @Autowired
    private ProblemStatusRepo problemStatusRepo;
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private SupportCenterRepo supportCenterRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    public ResponceErrorServerDto makeApplication(RequestMakeApplication request, String login) {
        try {
            Users user = usersRepo.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Логин не найден"));
            if (user.getProblem() != null) {
                throw new RuntimeException();
            }
            ProblemStatus problemStatus = problemStatusRepo.findById(5)
                    .orElseThrow(() -> new UsernameNotFoundException("Статус не найден"));
            ProblemProcessing problemProcessing = problemProcessingRepo.findById(1)
                    .orElseThrow(() -> new UsernameNotFoundException("Процесс не найден"));
            SupportCenter supportCenter = supportCenterRepo.findById(request.getIdCenter())
                    .orElseThrow(() -> new UsernameNotFoundException("Центр не найден"));

            Problem problem = new Problem();
            problem.setTitle(request.getTitle());
            problem.setDescription(request.getDescription());
            problem.setReceivedDateFromString(request.getReceivedDate());
            problem.setSupportCenter(supportCenter);
            problem.setProblemStatus(problemStatus);
            problem.setProblemProcessing(problemProcessing);
            problem = problemRepo.save(problem);
            user.setProblem(problem);
            usersRepo.save(user);
            return new ResponceErrorServerDto(200);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto getConclusion(ResponceConclusionUserDto responceConclusionUserDto, String login){
        try {
            Users user = usersRepo.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            List<Appointment> appointments = appointmentRepo.findByUserId(user.getId());
            List<ConclusionUserDto> conclusionUserDtoList = new ArrayList<>();
            for (Appointment appointment : appointments) {
                Users userPro = usersRepo.findById(appointment.getProfessional().getUser().getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));
                ConclusionUserDto conclusionUserDto = new ConclusionUserDto();
                conclusionUserDto.setTextInfo(appointment.getTextInfo());
                conclusionUserDto.setTitle(appointment.getTitle());
                conclusionUserDto.setFullNamePro(userPro.getFullName());
                conclusionUserDto.setContactNumberPro(userPro.getContactNumber());
                conclusionUserDtoList.add(conclusionUserDto);
            }
            responceConclusionUserDto.setConclusionUsers(conclusionUserDtoList);
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

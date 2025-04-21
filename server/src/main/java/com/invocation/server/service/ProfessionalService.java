package com.invocation.server.service;

import com.invocation.server.dto.*;
import com.invocation.server.entity.*;
import com.invocation.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionalService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private ProfessionalRepo profRepo;
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;

    public ResponceErrorServerDto getUsersProfessionalProblem(String login, ResponceUsersProblemProfessionalDto responceUsersProblemProfessionalDto){
        try {
            Users userPro = usersRepo.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            Professional professional = profRepo.findByUserId(userPro.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));
            List<Client> clients = clientRepo.findByProfessionalId(professional.getId());
            List<UsersProblemProfessionalDto> usersProblemProfessionalDto = new ArrayList<>();
            for (Client client : clients) {
                Users user = usersRepo.findById(client.getUser().getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Клиент не найден"));
                UsersProblemProfessionalDto usersProblemProfessionalDtoUser = new UsersProblemProfessionalDto();
                usersProblemProfessionalDtoUser.setId(user.getId());
                usersProblemProfessionalDtoUser.setFullName(user.getFullName());
                usersProblemProfessionalDtoUser.setEmail(user.getEmail());
                usersProblemProfessionalDtoUser.setBirthDate(user.getBirthDateAsString());
                usersProblemProfessionalDtoUser.setContactNumber(user.getContactNumber());
                usersProblemProfessionalDtoUser.setAddress(user.getAddress());
                usersProblemProfessionalDto.add(usersProblemProfessionalDtoUser);
            }
            responceUsersProblemProfessionalDto.setUsersProblemProfessionals(usersProblemProfessionalDto);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto getUserProblemId(int id, ResponceUserProblemId responceUserProblemId){
        try {
            Users user = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            if (user.getProblem() == null) {
                new RuntimeException();
            }
            Problem problem = problemRepo.findById(user.getProblem().getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Проблема не найден"));
            responceUserProblemId.setId(user.getId());
            responceUserProblemId.setFullName(user.getFullName());
            responceUserProblemId.setTitle(problem.getTitle());
            responceUserProblemId.setDescription(problem.getDescription());
            responceUserProblemId.setReceivedDate(problem.getReceivedDateAsString());
            responceUserProblemId.setStatus(problem.getProblemStatus().getStatus());
            responceUserProblemId.setProcess(problem.getProblemProcessing().getProcess());
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto makeJudgment(RequestMakeJudgment requestMakeJudgment, String professional){
        try{
            Users userPro = usersRepo.findByLogin(professional)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь профессионал не найден"));
            Users user = usersRepo.findById(requestMakeJudgment.getUserId())
                    .orElseThrow(() -> new UsernameNotFoundException("Обычный пользователь не найден"));
            Professional pro = profRepo.findByUserId(userPro.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));
            Problem problem = problemRepo.findById(user.getProblem().getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Проблема пользователя не найдена"));
            Client client = clientRepo.findByUserId(user.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Запись  пользователем не найдена"));

            Appointment appointment = new Appointment();
            appointment.setTextInfo(requestMakeJudgment.getTextInfo());
            appointment.setUser(user);
            appointment.setProfessional(pro);
            appointment.setTitle(problem.getTitle());
            appointment.setDescription(problem.getDescription());
            appointmentRepo.save(appointment);

            user.setProblem(null);
            usersRepo.save(user);
            problemRepo.delete(problem);
            clientRepo.delete(client);
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

package com.invocation.server.service;

import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceUserProblemId;
import com.invocation.server.dto.ResponceUsersProblemProfessionalDto;
import com.invocation.server.dto.UsersProblemProfessionalDto;
import com.invocation.server.entity.Client;
import com.invocation.server.entity.Problem;
import com.invocation.server.entity.Professional;
import com.invocation.server.entity.Users;
import com.invocation.server.repository.ClientRepo;
import com.invocation.server.repository.ProblemRepo;
import com.invocation.server.repository.ProfessionalRepo;
import com.invocation.server.repository.UsersRepo;
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
}

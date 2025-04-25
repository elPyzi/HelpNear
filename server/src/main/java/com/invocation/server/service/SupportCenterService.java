package com.invocation.server.service;

import com.invocation.server.dto.*;
import com.invocation.server.entity.*;
import com.invocation.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupportCenterService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private ProfessionalRepo profRepo;
    @Autowired
    private CenterEmployeeRepo centerEmployeeRepo;
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private RolesRepo rolesRepo;

    public ResponceErrorServerDto getProfessionals(ResponceProfessionalsDto responceProfessionalsDto, String login){
        try {
            Users support = usersRepo.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Работник центра не найден"));
            CenterEmployee centerEmployee = centerEmployeeRepo.findByUserId(support.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Запись не найдена"));
            List<ProfessionalsDto> professionalDto = new ArrayList<>();
            List<Professional> professionals = profRepo.findByCenterId(centerEmployee.getCenter().getId());
            for (Professional professional : professionals) {
                Users user = usersRepo.findById(professional.getUser().getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));

                ProfessionalsDto professionalsDto = new ProfessionalsDto();
                professionalsDto.setId(user.getId());
                professionalsDto.setFull_name(user.getFullName());
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

    public ResponceErrorServerDto getUsersProblemCenter(ResponceUsersProblemCenterDto responceUsersProblemCenterDto, String login){
        try {
            Users support = usersRepo.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Работник центра не найден"));
            CenterEmployee centerEmployee = centerEmployeeRepo.findByUserId(support.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Запись не найдена"));
            List<Problem> problemUserCenter = problemRepo.findBySupportCenterId(centerEmployee.getCenter().getId());
            List<UsersProblemCenterDto> usersProblemCenterDto = new ArrayList<>();
            List<Client> clients = clientRepo.findAll();
            for (Problem problem : problemUserCenter) {
                int check = 0;
                Users user = usersRepo.findByProblemId(problem.getId())
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
                for (Client client : clients) {
                    if(client.getUser().getId() == user.getId()){
                        check++;
                        break;
                    }
                }
                if(check == 0){
                    UsersProblemCenterDto usersProblemCenterDto1 = new UsersProblemCenterDto();
                    usersProblemCenterDto1.setUserId(user.getId());
                    usersProblemCenterDto1.setFullName(user.getFullName());
                    usersProblemCenterDto1.setTitle(problem.getTitle());
                    usersProblemCenterDto1.setDescription(problem.getDescription());
                    usersProblemCenterDto.add(usersProblemCenterDto1);
                }
            }
            responceUsersProblemCenterDto.setUsersProblemCenters(usersProblemCenterDto);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto setProfessionalUser(RequestSetProfessionalDto req){
        try {
            Professional professional = profRepo.findByUserId(req.getProfessionalId())
                    .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));
            System.out.println("1");
            Users users = usersRepo.findById(req.getUserId())
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

            Client client = new Client();
            client.setProfessional(professional);
            client.setUser(users);
            clientRepo.save(client);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto addProfessional(int id, String login){
        try{
            Users support = usersRepo.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("Работник центра не найден"));
            CenterEmployee centerEmployee = centerEmployeeRepo.findByUserId(support.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Центр не найден"));
            Users users = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            Roles professionalRole = rolesRepo.findByRoleName("PROFESSIONAL")
                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));
            users.setRole(professionalRole);
            usersRepo.save(users);
            System.out.println("2");

            Professional professional = new Professional();
            professional.setInfo("Опытный специалист");
            professional.setAverageRating(4);
            professional.setCenter(centerEmployee.getCenter());
            professional.setUser(users);
            profRepo.save(professional);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto removeProfessional(int id){
        try{
            Users users = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            if(users.getRole().getRoleName().equals("CITIZEN")){
                new RuntimeException();
            }
            Roles professionalRole = rolesRepo.findByRoleName("CITIZEN")
                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));
            users.setRole(professionalRole);
            usersRepo.save(users);

            Professional professional = profRepo.findByUserId(users.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Профессионал не найден"));
            profRepo.delete(professional);
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

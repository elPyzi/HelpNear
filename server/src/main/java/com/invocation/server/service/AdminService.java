package com.invocation.server.service;

import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceSupportDto;
import com.invocation.server.dto.SupportDto;
import com.invocation.server.entity.*;
import com.invocation.server.repository.CenterEmployeeRepo;
import com.invocation.server.repository.RolesRepo;
import com.invocation.server.repository.SupportCenterRepo;
import com.invocation.server.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private CenterEmployeeRepo centerEmployeeRepo;
    @Autowired
    private SupportCenterRepo supportCenterRepo;
    @Autowired
    private RolesRepo rolesRepo;

    public ResponceErrorServerDto addSupport(int id, int idCenter){
        try{
            Users user = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            SupportCenter supportCenter = supportCenterRepo.findById(idCenter)
                    .orElseThrow(() -> new UsernameNotFoundException("Центр не найден"));
            Roles professionalRole = rolesRepo.findByRoleName("CENTER")
                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));
            user.setRole(professionalRole);
            usersRepo.save(user);

            CenterEmployee centerEmployee = new CenterEmployee();
            centerEmployee.setUser(user);
            centerEmployee.setCenter(supportCenter);
            centerEmployeeRepo.save(centerEmployee);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto removeCenter(int id){
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

            CenterEmployee centerEmployee = centerEmployeeRepo.findByUserId(users.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Не найдено"));
            centerEmployeeRepo.delete(centerEmployee);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto getAllSupports(ResponceSupportDto responceSupportDto){
        try{
            List<CenterEmployee> centerEmployees = centerEmployeeRepo.findAll();
            List<SupportDto> supportDtoList = new ArrayList<>();
            for (CenterEmployee centerEmployee : centerEmployees) {
                SupportDto supportDto = new SupportDto();
                supportDto.setId(centerEmployee.getUser().getId());
                supportDto.setLogin(centerEmployee.getUser().getLogin());
                supportDto.setFullName(centerEmployee.getUser().getFullName());
                supportDtoList.add(supportDto);
            }
            responceSupportDto.setSupports(supportDtoList);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto ban(int id){
        try {
            Users user = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            user.setIsBanned(true);
            usersRepo.save(user);
            return new ResponceErrorServerDto(200);
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401);
        }
        catch (Exception e) {
            return new ResponceErrorServerDto(401);
        }
    }

    public ResponceErrorServerDto unban(int id){
        try {
            Users user = usersRepo.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            user.setIsBanned(false);
            usersRepo.save(user);
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

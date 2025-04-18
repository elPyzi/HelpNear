package com.invocation.server.service;

import com.invocation.server.dto.ProfessionalsDto;
import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceProfessionalsDto;
import com.invocation.server.entity.CenterEmployee;
import com.invocation.server.entity.Professional;
import com.invocation.server.entity.Users;
import com.invocation.server.repository.CenterEmployeeRepo;
import com.invocation.server.repository.ProfessionalRepo;
import com.invocation.server.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupportCenterService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private ProfessionalRepo profRepo;
    @Autowired
    private CenterEmployeeRepo centerEmployeeRepo;

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
}

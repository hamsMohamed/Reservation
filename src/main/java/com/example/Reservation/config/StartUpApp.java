package com.example.Reservation.config;

import com.example.Reservation.entity.RoleModel;
import com.example.Reservation.entity.User;
import com.example.Reservation.service.RoleService;
import com.example.Reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartUpApp implements CommandLineRunner {
    private final UserService userService;

    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {


        if (roleService.findAll().isEmpty()) {
            roleService.save(new RoleModel(null, "admin"));
            roleService.save(new RoleModel(null, "user"));
        }


        if (userService.findAll().isEmpty()) {

//            RoleModel roleModel=new RoleModel(null, "admin",null);
//            RoleModel roleModel1=new RoleModel(null, "user",null);
//            --------------------------------------------------------------------------

            List<RoleModel> adminRoles = new ArrayList<>();
//            RoleModel roleModel=roleService.findByName("admin");
//            System.out.println(roleModel.toString());

            adminRoles.add(roleService.findByName("admin"));
//            adminRoles.add(roleModel);

            List<RoleModel> userRoles = new ArrayList<>();
            userRoles.add(roleService.findByName("user"));
//            userRoles.add(roleModel1);

            userService.save(new User(null, "eslam_daghash","daghash.eslam@gmail.com", "E130890Dcoach", "01012158963", "Eslam Daghash", adminRoles,true,true,true,true));

//            userService.save(new User(null, "nour nour","Norhan2001@", "nour@gmail.com", "Norhan2001",adminRoles,null,true,true,true,true));

//            userService.save(new User(null, "Ali","AliMohamed12@", "ali@gmail.com", "Norhan2001",adminRoles,null,true,true,true,true));
//
//            userService.save(new User(null, "Shrook","Zein8080@", "srouk7007@gmail.com", "Norhan2001",userRoles,null,true,true,true,true));
//            userService.save(new User(null, "Maha","Gaber5050@", "Maha20@gmail.com", "Norhan2001",userRoles,null,true,true,true,true));
//            userService.save(new User(null, "Noha","Zeinnn8070@", "Noha@gmail.com", "Norhan2001",userRoles,null,true,true,true,true));
//            userService.save(new User(null, "Mohamed","Maher774@", "mohamed20@gmail.com", "Norhan2001",userRoles,null,true,true,true,true));
        }

    }
}

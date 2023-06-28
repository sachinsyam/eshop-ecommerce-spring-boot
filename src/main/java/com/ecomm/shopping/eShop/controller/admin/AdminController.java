package com.ecomm.shopping.eShop.controller.admin;

import com.ecomm.shopping.eShop.entity.user.AuditLog;
import com.ecomm.shopping.eShop.entity.user.Role;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.RoleRepository;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.ecomm.shopping.eShop.service.UserRegistrationService;
import com.ecomm.shopping.eShop.worker.UsernameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class AdminController {
    @Autowired
    UserRegistrationService userRegistrationService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsernameProvider usernameProvider;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/audit")
    public String audit(@RequestParam(required = false,defaultValue = "") String filter,
                        @RequestParam(required = false, defaultValue = "") String keyword,
                        @RequestParam(required = false, defaultValue = "DESC") String sort,
                        @RequestParam(required = false, defaultValue = "loginTimestamp") String field,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "100") int size,
                        Model model){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));
        Page<AuditLog> audit = userInfoService.getAudit(pageable);
        model.addAttribute("audit", audit);

        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", audit.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, audit.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalRecords", audit.getTotalElements());

        model.addAttribute("empty", audit.getTotalElements() == 0);



        return "auditView";
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')") //method level role auth
    //@PreAuthorize("hasRole(‘ROLE_ADMIN')")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(Model model, String keyword){

        if(keyword == null){
            List<UserInfo> users = userInfoService.loadAllUsers();

            model.addAttribute("users",users);
        }
        else{
            Optional<UserInfo>  users = userInfoService.searchUsers(keyword);
            model.addAttribute("users",users);
        }

        return "adminDashboard";
    }
    //To view user.
    @GetMapping("/admin/{uuid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String viewUser(@PathVariable UUID uuid, Model model){

        UserInfo user = userInfoService.getUser(uuid);

        model.addAttribute("uuid",uuid);
        model.addAttribute("firstName",user.getFirstName());
        model.addAttribute("lastName",user.getLastName());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("role",user.getRole());
        model.addAttribute("enabled",user.isEnabled());

        return "viewUser";

    }
    //view create user ui
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/createUser")
    public String createUser(Model model) {
        UserInfo userInfo = new UserInfo();
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("bindingResult", new BeanPropertyBindingResult(userInfo, "userInfo"));
        return "dashboard/html/user/CreateUser";
    }


    //Create user from admin dashboard
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/createUser")
    public String createNewUserFromAdminDashboard(@ModelAttribute @Valid UserInfo userInfo,
                                                  BindingResult bindingResult,
                                                  Model model){
        if(bindingResult.hasErrors()){
             return "dashboard/html/user/CreateUser";
        }

        //finding the uuid of the role selected
        List<Role> roleUuids = roleRepository.findAll();
        for(Role role : roleUuids){
            if(role.getRoleName().equals(userInfo.getRole().getRoleName())){
                userInfo.getRole().setUuid(role.getUuid());
            }
        }



        if(!Objects.equals(userRegistrationService.addUser(userInfo), "signupSuccess")){

            switch (userRegistrationService.addUser(userInfo)) {
                case "username" -> {
                    bindingResult.rejectValue("username", "username", "username already taken");
                }
                case "email" -> {
                    bindingResult.rejectValue("email", "email", "email address already taken");
                }
                case "phone" -> {
                    bindingResult.rejectValue("phone", "phone", "phone number already taken");
                }
                default -> {
                    System.out.println("Unusual error found");
                }
            }

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("bindingResult", bindingResult);
            return "dashboard/html/user/CreateUser";
        }
        else{
            return "redirect:/dashboard/users/"+userInfo.getUuid();
        }

    }
    //delete a user from dashboard
    @GetMapping("/admin/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUserById(@PathVariable("id") UUID uuid){

        UserInfo user = userInfoService.getUser(uuid);
        user.setDeleted(true);
        user.setEnabled(false);
        user.setDeletedAt(new Date());
        user.setDeletedBy(usernameProvider.get());
        System.out.println("Soft deleting user"+user.getUsername());
        userInfoService.save(user);
     //   userInfoService.delete(uuid);
        return "redirect:/dashboard/users";
    }
    //delete
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute UserInfo userInfo){
        UserInfo user = userInfoService.getUser(userInfo.getUuid());
        user.setDeleted(true);
        user.setEnabled(false);
        user.setDeletedAt(new Date());
        user.setDeletedBy(usernameProvider.get());

        System.out.println("Soft deleting user"+user.getUsername());
        userInfoService.save(user);

//        System.out.println("Deleting"+userInfo.getUuid());
//        userInfoService.delete(userInfo.getUuid());
        return "redirect:/dashboard/users";
    }

    //Update User
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute UserInfo updatedUser){
        UserInfo oldUser = userInfoService.getUser((updatedUser.getUuid()));
        //check if there is a change in role
        if (updatedUser.getRole().getRoleName().equals(oldUser.getRole().getRoleName())){
            updatedUser.getRole().setUuid(oldUser.getRole().getUuid());
        }
        else{
            List<Role> allRoles = roleRepository.findAll();
            for(Role role : allRoles){
                if(updatedUser.getRole().getRoleName().equals(role.getRoleName())){
                    updatedUser.getRole().setUuid(role.getUuid());
                }
            }
        }
        if(updatedUser.getPassword().isEmpty()){
            updatedUser.setPassword(oldUser.getPassword());
        }
        else{
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userInfoService.updateUser(updatedUser);

        return "redirect:/dashboard/users/"+updatedUser.getUuid();

    }
}

package com.ecomm.shopping.eShop.controller.user;

import com.ecomm.shopping.eShop.entity.user.Role;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.RoleRepository;
import com.ecomm.shopping.eShop.repository.UserAddressRepository;
import com.ecomm.shopping.eShop.repository.UserInfoRepository;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.ecomm.shopping.eShop.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/app")
public class SignUpController {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRegistrationService userRegistrationService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserAddressRepository userAddressRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    private String currentUser;

    //getting current username        return "redirect:/category/"+category.getUuid();

    public ResponseEntity<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(username);
    }

    //landing page
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/signup")
    public String signup(Model model){

        model.addAttribute("signupError", "false");

        return "signup";
    }
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute UserInfo userInfo, Model model){
        Optional<Role> userRole = roleRepository.findRoleByName("ROLE_USER");
        Role role = userRole.get();
        userInfo.setRole(role);



        String res = userRegistrationService.addUser(userInfo);

        if(res.equals("signupSuccess")){
            //redirect to login page after registration
            model.addAttribute("signedUp", true);

            return "login-page";
        }

        switch (res) {
            case "phone" -> model.addAttribute("signupError", "phone");
            case "email" -> model.addAttribute("signupError", "email");
            case "username" -> model.addAttribute("signupError", "username");
            default -> model.addAttribute("signupError", "");
        }

        return "signup";

    }


}

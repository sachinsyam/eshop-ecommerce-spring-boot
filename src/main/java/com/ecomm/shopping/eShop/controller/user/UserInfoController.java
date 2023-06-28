package com.ecomm.shopping.eShop.controller.user;

import com.ecomm.shopping.eShop.dto.UserDto;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.service.UserAddressService;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.ecomm.shopping.eShop.worker.ReportGenerator;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserAddressService userAddressService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ReportGenerator reportGenerator;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public String save(@ModelAttribute UserDto userDto,
                       BindingResult bindingResult){
            UserInfo user = userInfoService.findByUsername(getCurrentUsername());
            if(userDto.getUuid().equals(user.getUuid())){
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setPhone(userDto.getPhone());
                user.setEmail(userDto.getEmail());

                //save new and updated addresses


               //check if there is password change and change password if required

                if(passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
                    System.out.println("matched");
                    if(!userDto.getNewPassword().equals("") &&  userDto.getNewPassword().equals(userDto.getNewPasswordRe())){
                        user.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
                    }

                }else{
                    System.out.println("not matched");
                }

                userInfoService.updateUser(user);
            }else{
                return "404";
            }

        return "redirect:/profile";
    }

    @GetMapping("/generateUserReport")
    public  ResponseEntity<ByteArrayResource>  generateUserReport() throws IOException {

        String fileName = reportGenerator.generateUserReportPdf(userInfoService.loadAllUsers());

        File requestedFile = new File(fileName);

        ByteArrayResource resource = new ByteArrayResource(FileUtils.readFileToByteArray(requestedFile));

        // Set the content type as application/pdf
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Set the file name for download
        headers.setContentDispositionFormData("attachment", fileName);

        // Return the resource as a response with OK status
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);

    }



    //get current username
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}

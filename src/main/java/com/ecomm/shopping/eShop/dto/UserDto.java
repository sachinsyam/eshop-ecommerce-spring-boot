package com.ecomm.shopping.eShop.dto;

import com.ecomm.shopping.eShop.entity.user.UserAddress;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class UserDto {

    UUID uuid;
    @NotNull(message = "First Name is empty")
    private String firstName;
    @NotNull(message = "Last Name is empty")
    private String lastName;
    @NotNull(message = "password is empty")
    private String password;
    @NotNull(message = "password is empty")
    private String newPassword;
    private String newPasswordRe;

    @Email(message = "not a valid email")
    private String email;

    private String phone;

    private String flat;
    private String area;
    private String town;
    private String city;
    private String state;
    private String pin;
    private String landmark;

    private List<UserAddress> savedAddress = new ArrayList<>();

}

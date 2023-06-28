package com.ecomm.shopping.eShop.entity.user;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;
    private Date otpRequestTime;
    private String otp;
    @OneToOne
    @JoinColumn(name="user_id")
    UserInfo userInfo;

    //methods
    //5 minutes for timeout
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
    public boolean isOTPRequired() {
        if (this.getOtp() == null) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpRequestTime.getTime();

        // OTP expired
        return otpRequestedTimeInMillis + OTP_VALID_DURATION >= currentTimeInMillis;
    }

    public OTP(String otp) {
        this.otp = otp;
        this.otpRequestTime = new Date(); // Set current time as OTP request time
    }

}
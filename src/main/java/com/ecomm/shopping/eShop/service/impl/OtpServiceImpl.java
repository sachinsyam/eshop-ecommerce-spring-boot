package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.user.OTP;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.repository.OtpRepository;
import com.ecomm.shopping.eShop.service.OtpService;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    UserInfoService userInfoService;





    @Override
    public void save(OTP otp) {
        otpRepository.save(otp);
    }

    @Override
    public OTP findByUser(UserInfo user) {
        return otpRepository.findByUserInfo(user);
    }

    @Override
    public void delete(OTP oldOtp) {
        otpRepository.delete(oldOtp);
    }

    @Override
    public boolean verifyPhoneOtp(String otp, String phone) {
        UserInfo user = userInfoService.findByPhone(phone);
        if(user.isVerified()){
            return true;
        }
        OTP savedOtp = this.findByUser(user);
        if(savedOtp.isOTPRequired()){
            if(otp.equals(savedOtp.getOtp())){
                user.setVerified(true);
                userInfoService.save(user);
                this.delete(savedOtp);
                return true;
            }
        }

        return false;
    }


    public void sendPhoneOtp(String phoneNumber){

        UserInfo user = userInfoService.findByPhone(phoneNumber);
        OTP savedOtp = this.findByUser(user);

        if(savedOtp != null){
            if (savedOtp.isOTPRequired()) {
                System.out.println("valid otp already exists");
                return;
            }else{
                System.out.println("Deleting expired otp");
                otpRepository.delete(savedOtp);
            }
        }

            String otp = generateOTP();
            //save otp tp db
            OTP generatedOtp = new OTP(otp);
            generatedOtp.setUserInfo(user);
            generatedOtp = otpRepository.save(generatedOtp);
            System.out.println("new otp generated");
            /*
            // Create the request body
            String jsonBody = String.format("{\"route\": \"otp\", \"variables_values\": \"%s\", \"numbers\": \"%s\"}", otp, phoneNumber);

            // Set the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HTTP entity with the request body and headers
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            // Create the RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Send the POST request to the Fast2SMS API
            restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);


             */
         final String ACCOUNT_SID = "sid"; //replace with your credentials form twilio
         final String AUTH_TOKEN = "token";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        PhoneNumber from = new PhoneNumber("+11111"); //replace with your phone number from twilio

        PhoneNumber to = new PhoneNumber(phoneNumber);


        String content = "This is your account verification OTP. Valid for 5 minutes. OTP: " + generatedOtp.getOtp();
        MessageCreator messageCreator = new MessageCreator(to, from,  content);
        Message res = messageCreator.create();
        System.out.println(res.getSid());



    }


    //Generate Random OTP
    private String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otpNumber = 100_000 + random.nextInt(900_000);
        return String.valueOf(otpNumber);
    }


}

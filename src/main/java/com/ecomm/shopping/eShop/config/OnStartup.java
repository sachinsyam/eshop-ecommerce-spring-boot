/*
 * Created by Sachin
 */
package com.ecomm.shopping.eShop.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class OnStartup {
    @PostConstruct
    private void showAscii(){
        System.out.println("\n" +
                "                                                              \n" +
                "                       88                                     \n" +
                "                       88                                     \n" +
                "                       88                                     \n" +
                " ,adPPYba,  ,adPPYba,  88,dPPYba,    ,adPPYba,   8b,dPPYba,   \n" +
                "a8P_____88  I8[    \"\"  88P'    \"8a  a8\"     \"8a  88P'    \"8a  \n" +
                "8PP\"\"\"\"\"\"\"   `\"Y8ba,   88       88  8b       d8  88       d8  \n" +
                "\"8b,   ,aa  aa    ]8I  88       88  \"8a,   ,a8\"  88b,   ,a8\"  \n" +
                " `\"Ybbd8\"'  `\"YbbdP\"'  88       88   `\"YbbdP\"'   88`YbbdP\"'   \n" +
                "                                                 88           \n" +
                "                                                 88           \n");
    }
}

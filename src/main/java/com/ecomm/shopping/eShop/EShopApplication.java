/*
 * Created by Sachin
 */
package com.ecomm.shopping.eShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableJpaAuditing
public class EShopApplication {

	public static void main(String[] args) {

		SpringApplication.run(EShopApplication.class, args);
		System.out.println("""

				                                  __           __         __
				  ___ ___ _____  _____ ____  ___ / /____ _____/ /____ ___/ /
				 (_-</ -_) __/ |/ / -_) __/ (_-</ __/ _ `/ __/ __/ -_) _  /\s
				/___/\\__/_/  |___/\\__/_/   /___/\\__/\\_,_/_/  \\__/\\__/\\_,_/ \s
				                                                           \s
				""");
	}

}

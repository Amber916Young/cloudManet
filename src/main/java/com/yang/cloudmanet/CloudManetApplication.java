package com.yang.cloudmanet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.yang.cloudmanet.mapper")
//@EnableDiscoveryClient
//@EnableScheduling
@SpringBootApplication(scanBasePackages={"com.yang.cloudmanet"})
public class CloudManetApplication {

    public static void main(String[] args) {
        System.out.println("---start---");
        SpringApplication.run(CloudManetApplication.class, args);
        System.out.println("---end---");
    }

}

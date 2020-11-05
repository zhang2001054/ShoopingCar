package com.zlj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/4 19:45
 * description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@RibbonClients
public class CarApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarApiApplication.class,args);
    }
}

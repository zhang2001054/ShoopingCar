package com.zlj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * projectName: ShoopingCar
 * author: 张留佳
 * time: 2020/11/4 19:41
 * description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling //启用定时任务12
public class CarProiverApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarProiverApplication.class,args);
    }
}

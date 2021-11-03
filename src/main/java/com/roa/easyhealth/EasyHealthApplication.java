package com.roa.easyhealth;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(value = "generator")
@ComponentScan(value = "com.roa.easyhealth")
@SpringBootApplication
@EnableEncryptableProperties
@MapperScan({"com.roa.easyhelth.mapper", "generator.mapper"})
public class EasyHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyHealthApplication.class, args);
    }

}

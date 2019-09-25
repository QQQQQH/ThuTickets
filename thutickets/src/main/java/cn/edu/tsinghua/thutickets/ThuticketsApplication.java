package cn.edu.tsinghua.thutickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.edu.tsinghua.thutickets.dao")
public class ThuticketsApplication {

    public static void main(String[] args) {

        SpringApplication.run(ThuticketsApplication.class, args);
    }

}

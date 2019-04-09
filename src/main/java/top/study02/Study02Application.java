package top.study02;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.study02.mapper")
@SpringBootApplication
public class Study02Application {

    public static void main(String[] args) {
        SpringApplication.run(Study02Application.class, args);
    }

}

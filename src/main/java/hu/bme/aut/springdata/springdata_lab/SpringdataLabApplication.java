package hu.bme.aut.springdata.springdata_lab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringdataLabApplication implements CommandLineRunner {

    @Override
    public void run(String... args) {
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringdataLabApplication.class, args);
    }

}

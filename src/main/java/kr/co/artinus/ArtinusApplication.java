package kr.co.artinus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ArtinusApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtinusApplication.class, args);
    }
}

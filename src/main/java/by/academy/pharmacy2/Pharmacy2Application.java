package by.academy.pharmacy2;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Pharmacy2Application {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE).setSkipNullEnabled(true);
        return modelMapper;
    }

    public static void main(final String[] args) {
        SpringApplication.run(Pharmacy2Application.class, args);
    }
}

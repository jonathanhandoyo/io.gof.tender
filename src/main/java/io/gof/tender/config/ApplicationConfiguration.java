package io.gof.tender.config;

import com.google.code.geocoder.Geocoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"io.gof.tender"})
@Import({MongoConfiguration.class, SecurityConfiguration.class})
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Geocoder geocoder() throws Exception {
        return new Geocoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("login");
    }
}

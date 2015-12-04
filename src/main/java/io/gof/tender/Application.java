package io.gof.tender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.net.InetAddress;

@SpringBootApplication
@Controller
public class Application extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder app = new Application().configure(new SpringApplicationBuilder(Application.class));
        Environment env = app.run(args).getEnvironment();
        log.info("\n" +
                "Access URLs:\n" +
                "----------------------------------------------------------\n" +
                "\tExternal: \thttp://{}:{}\n" +
                "\tInternal: \thttp://localhost:{}\n" +
                "----------------------------------------------------------",
                new String[] {InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getProperty("server.port")}
        );
    }
}

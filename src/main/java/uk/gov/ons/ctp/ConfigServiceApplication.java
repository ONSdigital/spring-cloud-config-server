package uk.gov.ons.ctp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@Slf4j
@EnableConfigServer
@SpringBootApplication
public class ConfigServiceApplication {

  public static void main(String[] args) {
    log.debug("About to start the ConfigServiceApplication...");
    SpringApplication.run(ConfigServiceApplication.class, args);
  }

}

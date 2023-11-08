package com.airepublic.wego.exercise.carparkinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main entry point to start the application.
 */
@SpringBootApplication
@EnableJpaRepositories("com.airepublic.wego.exercise")
@EntityScan("com.airepublic.wego.exercise.*")
public class CarparkFinderApplication {
    // @Autowired
    // private CarparkAvailabilityImport availabilityImport;
    //
    // @PostConstruct
    // public void init() throws Exception {
    // availabilityImport.importCarparkAvailibility();
    // }
    //

    /**
     * Main method - no args.
     *
     * @param args no arguments need to be specified
     */
    public static void main(final String[] args) {
        SpringApplication.run(CarparkFinderApplication.class, args);
    }

}

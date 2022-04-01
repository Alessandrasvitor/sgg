package br.com.ajenterprice.sgg_api.controller;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.CleanResult;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/migrate")
public class MigrateController {

    @Autowired
    private Flyway flyway;

    @GetMapping("/clean")
    public CleanResult cleanMigrate() {
        return flyway.clean();
    }

    @GetMapping("/check")
    public MigrateResult checkMigrate() {
        return flyway.migrate();
    }

}

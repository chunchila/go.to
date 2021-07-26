package com.example.application.views.helloworld;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Locale;

@RestController
public class Api {

    @Autowired
    SitesRepo sitesRepo;

    @GetMapping("/hash/{hash}")
    public String hash(@PathVariable(required = false) String hash) throws NoSuchAlgorithmException {

        String s = new Site().hashMe(hash);
        return s;

    }

    @GetMapping("/get")
    public String read() {


        StringBuilder stringBuilder = new StringBuilder();
        for (Site site : sitesRepo.findAll()) {

            stringBuilder.append(site.getUrl() + ":" + site.getHash().toLowerCase(Locale.ROOT).toString() + Strings.LINE_SEPARATOR).append(System.getProperty("line.separator"));


        }
        return stringBuilder.toString();

    }

    @GetMapping("/clear")
    public String clear() {

        sitesRepo.deleteAll();
        return "cleared";
    }
}

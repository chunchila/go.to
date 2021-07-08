package com.example.application.views.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
public class Api {

    @Autowired
    SitesRepo sitesRepo;

    @GetMapping("/hash/{hash}")
    public String hash(@PathVariable(required = false) String hash) throws NoSuchAlgorithmException {


        return "hash";
    }

    @GetMapping("/read")
    public String read() {

        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        Set<String> strings = new HashSet<>();
        for (Site site : sitesRepo.findAll()) {
            counter++;
            stringBuilder.append(site.getHash());
            strings.add(site.getHash());
        }
        return "counter " + String.valueOf(counter) + " duplicates " + String.valueOf(counter - strings.size());

    }

    @GetMapping("/get")
    public String get() throws NoSuchAlgorithmException {

        for (int i = 0; i < 1E3; i++) {
            int rand = new Random().nextInt(100);
            String page = String.valueOf(rand);
            sitesRepo.save(new Site("www." + page + ".com", page + "-name"));
        }

        return "saved";
    }
}

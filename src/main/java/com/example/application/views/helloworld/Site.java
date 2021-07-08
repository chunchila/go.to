package com.example.application.views.helloworld;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
public class Site {

//    @GeneratedValue(generator = "UUID")
//    UUID uid;

    @Id
    String hash;
    String person;
    String url;

    public Site() {
    }



    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Site(String url, String person) throws NoSuchAlgorithmException {

        this.url = url;
        this.person = person;
        this.hash = hashMe(url);
    }

    public String hashMe(String url) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashed = digest.digest(
                url.getBytes(StandardCharsets.UTF_8));

        return DatatypeConverter.printHexBinary(hashed);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }


}
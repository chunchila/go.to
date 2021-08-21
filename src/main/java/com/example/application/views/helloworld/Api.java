package com.example.application.views.helloworld;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
public class Api {

    @Autowired
    SitesRepo sitesRepo;

    public static void generateQRCode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
        //the BitMatrix class represents the 2D matrix of bits
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }


    @GetMapping("/qr/{txt}")
    public void getQr(@PathVariable(required = true) String txt) throws IOException, WriterException {

        String str = "THE HABIT OF PERSISTENCE IS THE HABIT OF VICTORY.";
        //data that we want to store in the QR code

        //path where we want to get QR Code
        String path = "Quote.png";
        //Encoding charset to be used
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        //generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //invoking the user-defined method that creates the QR code
        generateQRCode(str, path, charset, hashMap, 800, 800);//increase or decrease height and width accodingly
        //prints if the QR code is generated
        System.out.println("QR Code created successfully.");
    }

    @GetMapping("/hash/{hash}")
    public String getHash(@PathVariable(required = false) String hash) throws NoSuchAlgorithmException {

        return new Site().hashMe(hash);


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

package com.sajjadamin.randomface;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class People {
    public boolean getFace(String fileLocation){
        boolean success = false;
        String fileName = fileLocation+ File.separator+Double.toString(Math.random()).substring(2)+".jpg";
        try{
            HttpURLConnection connection = (HttpURLConnection) new URL("https://thispersondoesnotexist.com/image").openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (HTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] data = new byte[1024];
            int count;
            while ((count = bis.read(data, 0, 1024)) != -1) {
                fos.write(data, 0, count);
            }
            success = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }
}

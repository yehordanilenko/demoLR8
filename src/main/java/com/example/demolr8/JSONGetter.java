package com.example.demolr8;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JSONGetter extends Thread{

    public String jsonIn="";
    public static String url;

    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public void ConnectAndGetData() {
        jsonIn = "";
        InputStream is;
        try {
            is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                try {
                    jsonIn = readAll(rd);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            jsonIn = "Couldn't find API";
        }

    }

    @Override
    public void run() {
        ConnectAndGetData();
        super.run();
    }
}

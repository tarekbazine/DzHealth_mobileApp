package com.example.seadev.dzhealth_mobileapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class Alerte extends AsyncTask<String, Void, Void> {
    Context c;

    Alerte(Context c) {
        this.c = c;
    }

    @Override
    protected Void doInBackground(String... params) {
        String url_service = "http://192.168.43.96/service_DzHealth/alerte_service.php";
        String line="",id = params[0], type = params[1], desc = params[2];


        try {
            URL url = new URL(url_service);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data =
                    URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                            + URLEncoder.encode("typeAlerte", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") +
                            "&" + URLEncoder.encode("desc", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            ///////////////////////
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
                /*while ((line=bufferedReader.readLine())!=null){
                    result += line;
                }*/

            line = bufferedReader.readLine();
            //Toast.makeText(LoginActivity0.this, line, Toast.LENGTH_SHORT).show();
            Log.i("loginoo1", line + "1");
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            Toast.makeText(c, "prblm connection", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.i("loginoo666665", line + "dd");

        return null;
    }
}

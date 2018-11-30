package com.englishapps.com.checkyourenglishvocabulary;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Murat on 21.2.2017.
 */

public class indir extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {
//http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/gettask1/Iva90tedYfcYTloipQGqKnTxB8M=

        HttpURLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(params[0]);
            urlConn = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }


            if (bufferedReader != null) {
                bufferedReader.close();
            }
            return new String(stringBuffer.toString());
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String response) {
        if (response != null) {

            Log.e("App", "Success: " + response);

        }
    }
}


package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {
    private LinearLayout registeraltl;
    private LinearLayout registerortal;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registeraltl = (LinearLayout) findViewById(R.id.registeraltlinear);
        registerortal = (LinearLayout) findViewById(R.id.registerortalinear);
        LinearLayout lw1 = (LinearLayout) findViewById(R.id.Registerlinear);
        lw1.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.registerbackground))));
        try {
            registeraltl.addView(altview());
            registerortal.addView(ortaview());


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        //buradaki sorunları gider
      //  client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public String cpuId() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }

    String register(String username, String password, String mail, String cozulensoruindexleri, String cpuId, int gold, String task1, String task2, String task3, int toplamskor) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {
        SendData yenidata = new SendData();
        yenidata.cpuid = cpuId;
        yenidata.soruindex = cozulensoruindexleri;
        yenidata.kullaniciadi = username;
        yenidata.sifre = password;
        yenidata.mail = mail;
        yenidata.gold = gold;
        Gson gson = new Gson();
        String data = gson.toJson(yenidata);

        String sonuc = null;
        try {
            sonuc = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/register", data).get().replace("\"", "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (sonuc == "Your registration has been added successfully, You are redirected to the MainPage.") {
            yenidata.task1 = task1;
            yenidata.task2 = task2;
            yenidata.task3 = task3;
            yenidata.skor = toplamskor;
            gson = new Gson();
            data = gson.toJson(yenidata);
            try {
                new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/setskor", data).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (sonuc != "") {
            return sonuc;
        } else {
            sonuc = "bos";
            return sonuc;
        }
    }


    public View ortaview() throws IllegalAccessException {
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setId(R.id.lay1);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(0, marg, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(3);
        int k = 1;
        String[] value = null;
        for (int i = 0; i < 4; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            for (int j = 0; j < 1; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels * 0.5));
                int sol = (int) ((getResources().getDisplayMetrics().widthPixels * 0.25));
                double oran = (float) 65 / (float) 200;

                int yukseklik = (int) (oran * genislik);

                //81x52 level buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins(sol, bosluk, sol, bosluk);
                if (i == 3) {
                    int yukarı = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                    genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 3.5));
                    sol = (int) ((getResources().getDisplayMetrics().widthPixels
                            / 2.8));
                    oran = (float) 57 / (float) 155;
                    yukseklik = (int) (oran * genislik);
                    tb1 = new TableRow.LayoutParams(genislik, yukseklik);

                    tb1.setMargins(sol, yukarı, sol, 0);

                }

                final EditText edt = new EditText(this);
                Button btn = new Button(this);


                switch (k) {
                    case 1:
                        edt.setId(R.id.usernameinputtxt);
                        edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        edt.setHint(getString(R.string.username));

                        break;
                    case 2:
                        edt.setId(R.id.passwordinputtxt);
                        edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edt.setHint("Password");

                        break;
                    case 3:
                        edt.setId(R.id.mailinputtxt);
                        edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        edt.setHint(R.string.email);
                        break;
                    case 4:
                        btn.setId(R.id.registerbtn);
                        btn.setBackgroundResource(resimara(getString(R.string.registerbutton)));
                        break;
                }

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //Register
                        if (MainActivity.logout == false) {

                            String username = ((EditText) findViewById(R.id.usernameinputtxt)).getText().toString();
                            String password = ((EditText) findViewById(R.id.passwordinputtxt)).getText().toString();
                            String mail = ((EditText) findViewById(R.id.mailinputtxt)).getText().toString();
                            Gson gson = new Gson();
                            String task1 = gson.toJson(MainActivity.task1);
                            String task2 = gson.toJson(MainActivity.task2);
                            String task3 = gson.toJson(MainActivity.task3);
                            int toplamskor = 0;
                            if (username != "" && password != "" && mail != "") {
                                if (!mail.contains("@") || !mail.contains(".com")) {
                                    Toast.makeText(getApplicationContext(), R.string.reg4, Toast.LENGTH_LONG).show();
                                } else if (mail.contains("@") && mail.contains(".com")) {
                                    //register yap
                                    MainActivity.temizle();
                                    String cozulensoruindexleri = gson.toJson(Soruekrani.cozulensoruindexleri);
                                    try {
                                        String sonuc = register(username, password, mail, cozulensoruindexleri, cpuId(), 30, task1, task2, task3, toplamskor);
                                        Toast.makeText(getApplicationContext(), sonuc, Toast.LENGTH_LONG).show();
                                        if (sonuc == getString(R.string.reg5)) {
                                            Intent i = new Intent();
                                            i.setClass(Register.this, MainActivity.class);
                                            startActivity(i);
                                        }
                                    } catch (ClassNotFoundException e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    } catch (InstantiationException e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    } catch (SQLException e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.reg6, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.reg7, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                if (k == 4) {
                    tableRow.addView(btn, tb1);
                } else {
                    tableRow.addView(edt, tb1);
                }
                k = k + 1;

            }

            tableLayout.addView(tableRow);
        }


        linearLayout.addView(tableLayout);

        return linearLayout;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);

    }

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }

    public View altview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("BacktoMenu");
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(0, 0, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(1);
        int k = 1;
        String[] value = null;
        for (int i = 0; i < 1; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 1; j++) {
                int yukarı = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 3.5));
                int sol = (int) ((getResources().getDisplayMetrics().widthPixels / 2.8));
                double oran = (float) 57 / (float) 155;
                int yukseklik = (int) (oran * genislik);

                //155x57 backto menu button
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins(sol, yukarı, 0, 0);
                Button button = new Button(this);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent i = new Intent();
                        i.setClass(Register.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.yourstatisticbacktomenu)));
                }

                tableRow.addView(button, tb1);
                k = k + 1;

            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      /* client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Register Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.englishapps.com.checkyourenglishvocabulary/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      /*  Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Register Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.englishapps.com.checkyourenglishvocabulary/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }
}

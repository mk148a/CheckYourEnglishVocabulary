package com.englishapps.com.checkyourenglishvocabulary;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    public static boolean giriscalisti = false;
    public static boolean logout = false;
    public static int gold = 0;
    public static String kullaniciadi = "";
    public static Task task1 = new Task();
    public static Task task2 = new Task();
    public static Task task3 = new Task();
    public static InterstitialAd interstitial;
    public static boolean turkcedil = false;
    public static boolean ingilizcedil = true;
    public static boolean questiondurumu = true;
    public static boolean sesdurumu = true;
    public Button freecoinbutton;
    private LinearLayout ul;
    private LinearLayout orta;
    public static void temizle() {

        Soruekrani.cozulensoruindexleri.clear();
        Soruekrani.sorusayisi = 1;
        task1.bitis = false;
        task2.bitis = false;
        task3.bitis = false;
        task1.level1.bitis = false;
        task1.level2.bitis = false;
        task1.level3.bitis = false;
        task2.level1.bitis = false;
        task2.level2.bitis = false;
        task2.level3.bitis = false;
        task3.level1.bitis = false;
        task3.level2.bitis = false;
        task3.level3.bitis = false;
        task1.level1.dogru = 0;
        task1.level2.dogru = 0;
        task1.level3.dogru = 0;
        task2.level1.dogru = 0;
        task2.level2.dogru = 0;
        task2.level3.dogru = 0;
        task3.level1.dogru = 0;
        task3.level2.dogru = 0;
        task3.level3.dogru = 0;
        task1.level1.yanlis = 0;
        task1.level2.yanlis = 0;
        task1.level3.yanlis = 0;
        task2.level1.yanlis = 0;
        task2.level2.yanlis = 0;
        task2.level3.yanlis = 0;
        task3.level1.yanlis = 0;
        task3.level2.yanlis = 0;
        task3.level3.yanlis = 0;
        task1.level1.gecilen = 0;
        task1.level2.gecilen = 0;
        task1.level3.gecilen = 0;
        task2.level1.gecilen = 0;
        task2.level2.gecilen = 0;
        task2.level3.gecilen = 0;
        task3.level1.gecilen = 0;
        task3.level2.gecilen = 0;
        task3.level3.gecilen = 0;
        task1.level1.skor = 0;
        task1.level2.skor = 0;
        task1.level3.skor = 0;
        task2.level1.skor = 0;
        task2.level2.skor = 0;
        task2.level3.skor = 0;
        task3.level1.skor = 0;
        task3.level2.skor = 0;
        task3.level3.skor = 0;
        task1.level1.reset = false;
        task1.level2.reset = false;
        task1.level3.reset = false;
        task2.level1.reset = false;
        task2.level2.reset = false;
        task2.level3.reset = false;
        task3.level1.reset = false;
        task3.level2.reset = false;
        task3.level3.reset = false;
        task1.level1.pasif = true;
        task1.level2.pasif = true;
        task1.level3.pasif = true;
        task2.level1.pasif = true;
        task2.level2.pasif = true;
        task2.level3.pasif = true;
        task3.level1.pasif = true;
        task3.level2.pasif = true;
        task3.level3.pasif = true;
    }

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }

    public String cpuId() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }



    void veritabaninayaz() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (MainActivity.logout == false) {
            if (MainActivity.giriscalisti == true) {
                SendData yenidata = new SendData();
                yenidata.cpuid = cpuId();
                yenidata.gold = MainActivity.gold;
                Gson gson = new Gson();
                String data = gson.toJson(yenidata);
                try {
                    //basarili yada hata+exception yaziyo
                    String s = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/setskor", data).get();
                    // Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    void giriskontrol() {


        if (logout == false) {

            try {
                // Establish the connection.


                EditText usertxt = (EditText) findViewById(R.id.usernametxt);
                usertxt.setEnabled(true);
                usertxt.setVisibility(View.VISIBLE);
                usertxt.setText(kullaniciadi);

                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                String gelen = new indir().execute(("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/gettask1/" + cpuId())).get();
                //beyin amcıkladığı yer
                List<String> task = gson.fromJson(gelen, type);

                if (task.get(0) == "hata") {
                    giriscalisti = false;

                } else {

                    if (task.get(0) != "") {
                        task1 = gson.fromJson(task.get(0), Task.class);
                    }
                    if (task.get(1) != "") {
                        task2 = gson.fromJson(task.get(1), Task.class);
                    }
                    if (task.get(2) != "") {
                        task3 = gson.fromJson(task.get(2), Task.class);
                    }
                    if (task.get(3) != "") {
                        Soruekrani.cozulensoruindexleri = gson.fromJson(task.get(3), type);
                    }
                    if (task.get(4) != "") {
                        gold = Integer.parseInt(task.get(4));
                    }
                    if (task.get(5) != "") {
                        kullaniciadi = (task.get(5));
                    }
                    giriscalisti = true;
                }


            } catch (Exception e) {
                giriscalisti = false;
                e.printStackTrace();
                // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            // giriscalisti = false;
            temizle();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppRater.app_launched(MainActivity.this);
        MobileAds.initialize(this,"ca-app-pub-9551427770579242~1967488613");

        ayarlariyukle();
        if (ingilizcedil == true) {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        } else if (turkcedil == true) {
            Locale locale = new Locale("tr");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        } else {
            Configuration config = new Configuration();
            Locale locale = Locale.getDefault(); // Sayfayı yüklemeden önce default locale alıyoruz ve sayfayı ona göre yüklüyoruz.
            Locale.setDefault(locale);
            config.locale = locale;

        }


        giriskontrol();

        TextView goldtxt = (TextView) findViewById(R.id.goldtxt);
        EditText usertxt = (EditText) findViewById(R.id.usernametxt);
        ul = (LinearLayout) findViewById(R.id.ustlinear);
        orta = (LinearLayout) findViewById(R.id.main_orta);
        LinearLayout altlinear = (LinearLayout) findViewById(R.id.main_altlinear);

        try {


            LinearLayout anasayfa = (LinearLayout) findViewById(R.id.anasayfa);
            orta.addView(ortaview());
            altlinear.addView(altview());

            if (giriscalisti == true) {
                ul.addView(ustviewuye());

                // yl.addView(yarimaayarview());
                goldtxt.setEnabled(true);
                goldtxt.setVisibility(View.VISIBLE);
                goldtxt.setText(Integer.toString(gold));
                usertxt.setEnabled(true);
                usertxt.setVisibility(View.VISIBLE);
                usertxt.setText(kullaniciadi);
                anasayfa.setBackgroundDrawable(getResources().getDrawable(resimara("mainpagebackgrounduye")));

            } else {
                ul.addView(ustview());
                goldtxt.setEnabled(false);
                goldtxt.setVisibility(View.INVISIBLE);
                usertxt.setEnabled(false);
                usertxt.setVisibility(View.INVISIBLE);
                anasayfa.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.mainpageuyesiz))));
            }


        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
    public void ayarlariyukle() {

        SharedPreferences settings = getSharedPreferences("status", 0);
        MainActivity.turkcedil = settings.getBoolean("setturkish", false);
        MainActivity.ingilizcedil = settings.getBoolean("setenglsih", false);
        MainActivity.questiondurumu = settings.getBoolean("questions_on", true);
        MainActivity.sesdurumu = settings.getBoolean("sound_on", true);
    }

    int dphesapla(int dp) {
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return marg;
    }

    public View ustview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("Login");
        adad.add("Register");

        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        int marg2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(marg2, (int) (marg * 0.50), 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(2);
        int k = 1;
        String[] value = null;
        for (int i = 0; i < 1; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 2; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 3.5));
                double oran = (float) 43 / (float) 110;
                int yukseklik = (int) (oran * genislik);
                //110x43 level buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins((int) (bosluk * 3.5), bosluk, bosluk, bosluk);
                Button button = new Button(this);
                //butonadı
                button.setTag(adad.get(j));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //kanal adı ve linkini tagden alıyor
                        String gelentag = v.getTag().toString();

                        Intent i = new Intent();


                        switch ((v.getTag().toString())) {
                            case "Login":
                                i.setClass(MainActivity.this, Login.class);
                                break;
                            case "Register":
                                i.setClass(MainActivity.this, Register.class);
                                break;

                        }


                        startActivity(i);
                    }
                });

                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.loginbutton)));
                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.registerbutton)));
                }

                tableRow.addView(button, tb1);
                k = k + 1;
            }

            tableLayout.addView(tableRow);
        }


        linearLayout.addView(tableLayout);

        return linearLayout;
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    public View ustviewuye() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("BuyCoin");
        adad.add("FreeCoin");
        adad.add("Logout");
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        int marg2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(marg2, (int) (marg * 0.50), 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(3);
        int k = 0;
        String[] value = null;
        for (int i = 0; i < 1; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.CENTER_VERTICAL);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 3; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 3.5));
                double oran = (float) 43 / (float) 110;
                int yukseklik = (int) (oran * genislik);
                //110x43 level buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins((int) (bosluk * 1.1), bosluk, bosluk, bosluk);

                Button button = new Button(this);
                //butonadı
                button.setTag(adad.get(k));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //kanal adı ve linkini tagden alıyor
                        String gelentag = v.getTag().toString();

                        Intent i = new Intent();


                        switch ((v.getTag().toString())) {
                            case "BuyCoin":
                                i.setClass(MainActivity.this, BuyCoin.class);
                                startActivity(i);
                                break;
                            case "FreeCoin":
                                // final AdConfig overrideConfig = new AdConfig();
                                //  overrideConfig.setIncentivized(true);
                                // overrideConfig.setSoundEnabled(true);

                                // vunglePub.playAd(overrideConfig);

                                //Reklam olayına çözüm bul
                             /*   if (UnityAds.isReady()) {
                                    UnityAds.show(MainActivity.this);
                                }*/
                                break;
                            case "Logout":

                                giriscalisti = false;
                                logout = true;
                                i.setClass(MainActivity.this, MainActivity.class);
                                startActivity(i);
                                break;

                        }


                    }
                });

                if (k == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.buycoinbutton1)));
                } else if (k == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.freecoinbutton1)));
                    button.setId(R.id.freecoinbutton);
                } else if (k == 2) {
                    button.setBackgroundResource(resimara(getString(R.string.logoutbutton)));
                }


                tableRow.addView(button, tb1);
                k = k + 1;
            }

            tableLayout.addView(tableRow);
        }


        linearLayout.addView(tableLayout);

        return linearLayout;
    }
    public View ortaview() throws IllegalAccessException {
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(dphesapla(5), marg, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(3);
        int k = 0;

        String[] value = null;
        for (int i = 0; i < 5; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));


            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 1; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 2.5));
                int soldan = (getResources().getDisplayMetrics().widthPixels - genislik) / 2;
                double oran = (float) 50 / (float) 180;
                int yukseklik = (int) (oran * genislik);

                //180x50 mainpage buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);

                tb1.setMargins(soldan, bosluk, 0, 0);
                final Button button = new Button(this);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent();
                        String gelentag = v.getTag().toString();
                        switch (gelentag) {
                            case "0":
                                a.setClass(MainActivity.this, singleplayer.class);
                                startActivity(a);

                                break;
                            case "1":
                                if (MainActivity.giriscalisti == true) {
                                    // a.setClass(MainActivity.this, Multiplayer.class);
                                    //startActivity(a);
                                    Toast.makeText(getApplicationContext(), R.string.Coming_Soon, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.mainyazi1, Toast.LENGTH_LONG).show();
                                }

                                break;
                            case "2":

                                if (MainActivity.giriscalisti == true) {
                                    //a.setClass(MainActivity.this, PlayWithFriend.class);
                                    // startActivity(a);
                                    Toast.makeText(getApplicationContext(), R.string.Coming_Soon, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.mainyazi1, Toast.LENGTH_LONG).show();
                                }
                                break;
                            case "3":

                                if (MainActivity.giriscalisti == true) {
                                    Toast.makeText(getApplicationContext(), R.string.Coming_Soon, Toast.LENGTH_LONG).show();
                                   // a.setClass(MainActivity.this, compitation.class);
                                  //  startActivity(a);
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.mainyazi1, Toast.LENGTH_LONG).show();
                                }

                                break;
                            case "4":
                                a.setClass(MainActivity.this, settingss.class);
                                startActivity(a);
                                break;
                        }

                    }
                });


                switch (k) {
                    case 0:
                        button.setBackgroundResource(resimara(getString(R.string.singleplayerbutton)));
                        button.setTag(Integer.toString(k));
                        break;
                    case 1:
                        button.setBackgroundResource(resimara(getString(R.string.multiplayerbutton)));
                        button.setTag(Integer.toString(k));
                        break;
                    case 2:
                        button.setBackgroundResource(resimara(getString(R.string.playwithfriendbutton)));
                        button.setTag(Integer.toString(k));
                        break;
                    case 3:
                        button.setBackgroundResource(resimara(getString(R.string.competitionbutton)));
                        button.setTag(Integer.toString(k));
                        break;
                    case 4:
                        button.setBackgroundResource(resimara(getString(R.string.settingsbutton)));
                        button.setTag(Integer.toString(k));
                        break;
                }


                tableRow.addView(button, tb1);
                k = k + 1;
            }

            tableLayout.addView(tableRow);
        }


        linearLayout.addView(tableLayout);

        return linearLayout;
    }

    public View altview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("Rate");
        adad.add("Contact");
        adad.add("About");
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        int marg2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(marg2, 0, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(2);
        int k = 1;
        String[] value = null;
        for (int i = 0; i < 1; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 3; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 3.5));
                double oran = (float) 43 / (float) 110;
                int yukseklik = (int) (oran * genislik);
                //110x43 level buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins((int) (bosluk * 1.1), bosluk, bosluk, bosluk);
                Button button = new Button(this);
                //butonadı
                button.setTag(adad.get(j));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //kanal adı ve linkini tagden alıyor
                        String gelentag = v.getTag().toString();
                        Intent i = new Intent();
                        switch ((v.getTag().toString())) {
                            case "Rate":
                                // i.setClass(MainActivity.this, YourStatistics.class);
                                // startActivity(i);
                                try {
                                    Intent rateIntent = rateIntentForUrl("market://details");
                                    startActivity(rateIntent);
                                } catch (ActivityNotFoundException e) {
                                    Intent rateIntent = rateIntentForUrl("http://play.google.com/store/apps/details");
                                    startActivity(rateIntent);
                                }

                                break;
                            case "Contact":
                                //
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/geniusprogramming/"));
                                startActivity(browserIntent);
                                break;
                            case "About":
                                //
                                Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/geniusprogramming/"));
                                startActivity(browserIntent1);
                                break;
                        }

                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.rateusbutton)));
                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.contactusbutton)));
                } else if (j == 2) {
                    button.setBackgroundResource(resimara(getString(R.string.aboutbutton)));
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
    public void onBackPressed() {

        new AlertDialog.Builder(this)

                .setMessage(R.string.mainyazi2)
                .setPositiveButton(getString(R.string.yestext), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

                })
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }


}

package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.R.attr.duration;
import static com.englishapps.com.checkyourenglishvocabulary.compitation_soruekrani.compitationsoruekrani_cozulensoruindexleri;

public class compitation extends AppCompatActivity {

    public static Match match = new Match();
    public static List<Match> matchs = new ArrayList<>();
    public static boolean join=false;
    public static boolean start=false;
    public static boolean bitis;
    public static Integer katilimcisayisi=0;
    public static Integer odul1 = 0;
    public static Integer odul2 = 0;
    public static Integer odul3 = 0;
    public static String kazanan1 = "";
    public static String kazanan2 = "";
    public static String kazanan3 = "";
    public TextView goldtxt;
    public TextView winner1txt;
    public TextView winner2txt;
    public TextView winner3txt;
    public TextView award1txt;
    public TextView award2txt;
    public TextView award3txt;
    private boolean goldbuttontiklama = false;
    private String msjsonuc = "";

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public String cpuId()
    {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }



    void veritabaninayaz() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (MainActivity.logout == false)
        {
            if (MainActivity.giriscalisti == true)
            {
                SendData yenidata = new SendData();
                yenidata.cpuid = cpuId();
                yenidata.gold = MainActivity.gold;
                try {
                    Gson gson = new Gson();
                    String data = gson.toJson(yenidata);

                    String s = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/setskor", data).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public Date tarihal()
    {
        Date now=new Date();
        try {
            String json = readUrl("http://api.geonames.org/timezoneJSON?formatted=true&lat=33&lng=44&username=mk148a");
            Gson gson = new Gson();
            Time time = gson.fromJson(json, Time.class);
            //2016-11-05 04:04
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = format.parse(time.time);
            now=date;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return now;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compitation);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

        LinearLayout sol=(LinearLayout)findViewById(R.id.competition_sollinear);
        LinearLayout alt=(LinearLayout)findViewById(R.id.compitation_altlinear);
        TextView katlm=(TextView)findViewById(R.id.participantstxt);
        goldtxt=(TextView)findViewById(R.id.compitation_goldtxt);
        winner1txt = (TextView) findViewById(R.id.winners1txt);
        winner2txt = (TextView) findViewById(R.id.winners2txt);
        winner3txt = (TextView) findViewById(R.id.winners3txt);
        award1txt = (TextView) findViewById(R.id.award1txt);
        award2txt = (TextView) findViewById(R.id.award2txt);
        award3txt = (TextView) findViewById(R.id.award3txt);

        try {
            joinkontrol();
            join = joinkontrol();


            start = startkontrol();

            if (match.bitis==true)
            {
                join=true;
                start=false;
            } else
            {
                if (sdf.format(tarihal()).equals("Sunday")) {
                    join = false;
                    start = false;

                } else if (sdf.format(tarihal()).equals("Monday")) {
                    Date suan = tarihal();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    Date zaman = format.parse("00:05");
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar1.setTime(suan);
                    calendar2.setTime(zaman);
                    //Toast.makeText(getBaseContext(), Integer.toString(calendar1.compareTo(calendar2)), Toast.LENGTH_LONG).show();
                    if (calendar1.compareTo(calendar2) == -1) {
                        join = true;
                        start = false;
                    }

                }

            }


            sol.addView(solview());
            alt.addView(altview());
            katlm.setText(Integer.toString(katilimcisayisi));
            goldtxt.setText(Integer.toString(MainActivity.gold));
            award1txt.setText(Integer.toString(odul1));
            award2txt.setText(Integer.toString(odul2));
            award3txt.setText(Integer.toString(odul3));
            winner1txt.setText(kazanan1);
            winner2txt.setText(kazanan2);
            winner3txt.setText(kazanan3);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    void  joingold()
    {
        if (MainActivity.gold >= 5)
        {
            //join kodları

            Boolean sonuc = join();

            MainActivity.gold += -5;
            goldtxt.setText(Integer.toString(MainActivity.gold));
            goldbuttontiklama = true;


            goldtxt.setText(Integer.toString( MainActivity.gold));

            try {

                veritabaninayaz();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        } else if (MainActivity.gold < 5)
        {

            msjsonuc=getString(R.string.yourstats4);
            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(compitation.this);
            alertDialogBuilder1.setTitle(R.string.yourstats3);
            alertDialogBuilder1
                    .setMessage(msjsonuc)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.okbutton),new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });




            goldbuttontiklama = false;
            AlertDialog a1 = alertDialogBuilder1.create();

            // show it
            a1.show();
            goldbuttontiklama = false;
        }
        Intent i=getIntent();
        finish();
        startActivity(i);
    }

    void Goldkullan()
    {

        String mesaj="";

        mesaj = getString(R.string.joindialog);
        if (MainActivity.questiondurumu==false)
        {
            joingold();
        }
        else
        {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.yourstats3));
            alertDialogBuilder
                    .setMessage(mesaj)
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            joingold();
                        }
                    })
                    .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            goldbuttontiklama = false;
                            msjsonuc = "";
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }


    }

    int dphesapla(int dp) {
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return marg;
    }

    public int resimara(String isim)
    {
        Context context=this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }

    public View solview() throws IllegalAccessException {
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        pr.setMargins(0, marg, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(4);
        int k = 0;

        String[] value = null;
        for (int i = 0; i < 4; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());

            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 1; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels /3.5));
                // int soldan=(int)((getResources().getDisplayMetrics().widthPixels-genislik)/2);
                double oran = (float) 50 / (float) 133;
                int yukseklik = (int) (oran * genislik);

                //180x50 mainpage buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);

                tb1.setMargins(0, bosluk, 0, 0);
                final Button button = new Button(this);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a =new Intent();
                        String gelentag = v.getTag().toString();
                        switch (gelentag)
                        {
                            case "0":
                                a.setClass(compitation.this, compitation_soruekrani.class);
                                startActivity(a);
                                break;
                            case "1":
                                //join komutları
                                Goldkullan();
                                break;
                            case "2":
                                //
                                break;
                            case "3":
                                a.setClass(compitation.this, CompetitionRules.class);
                                startActivity(a);
                                break;
                        }
                    }
                });



                switch (k) {
                    case 0:
                        button.setBackgroundResource(resimara(getString(R.string.startbutton)));
                        button.setId(R.id.compitation_startbutton);
                        button.setTag(Integer.toString(k));
                        if (start==false)
                        {

                            final AlphaAnimation animation = new AlphaAnimation(.5f, .5f);
                            animation.setDuration(duration);
                            animation.setFillAfter(true);
                            button.startAnimation(animation);
                            button.setEnabled(false);
                            button.setTag(Integer.toString(k));
                        }
                        else
                        {
                            final AlphaAnimation animation = new AlphaAnimation(.5f, .5f);
                            animation.cancel();
                            button.startAnimation(animation);
                            button.setEnabled(true);

                        }

                        break;
                    case 1:
                        button.setBackgroundResource(resimara(getString(R.string.joinbutton)));
                        button.setTag(Integer.toString(k));
                        button.setId(R.id.joinbutton);
                        if (join==true)
                        {
                            final AlphaAnimation animation = new AlphaAnimation(.5f, .5f);
                            animation.setDuration(duration);
                            animation.setFillAfter(true);

                            button.startAnimation(animation);
                            button.setEnabled(false);
                        }
                        else
                        {
                            final AlphaAnimation animation = new AlphaAnimation(.5f, .5f);
                            animation.cancel();
                            button.startAnimation(animation);
                            button.setEnabled(true);
                        }
                        break;
                    case 2:
                        button.setBackgroundResource(resimara(getString(R.string.winnersbutton)));
                        button.setTag(Integer.toString(k));
                        break;
                    case 3:
                        button.setBackgroundResource(resimara(getString(R.string.rulesbutton)));
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

    public View altview() throws IllegalAccessException
    {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("rank");
        adad.add("stats");
        adad.add("back");
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
                double oran = (float) 57 / (float) 155;
                int yukseklik = (int) (oran * genislik);
                //155x57 level buttonları
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
                            case "rank":
                                //start komutları
                                Toast.makeText(getApplicationContext(), R.string.Coming_Soon, Toast.LENGTH_LONG).show();

                                break;
                            case "stats":
                                Toast.makeText(getApplicationContext(), R.string.Coming_Soon, Toast.LENGTH_LONG).show();
                                break;
                            case "back":
                                i.setClass(compitation.this, MainActivity.class);
                                startActivity(i);
                                break;
                        }

                    }
                });
                if (j == 0)
                {
                    button.setBackgroundResource(resimara(getString(R.string.ranklistbutton)));

                }
                else if (j == 1)
                {
                    button.setBackgroundResource(resimara(getString(R.string.yourstatisticbutton)));
                }
                else if (j == 2)
                {
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
    public void onBackPressed()
    {

    }

    public Boolean join() {
        Boolean gelen = false;
        String sonuc = "false";
        Gson gson = new Gson();
        String matchdata = gson.toJson(match);
        String cozulenindexstring = gson.toJson(compitationsoruekrani_cozulensoruindexleri);
        SendData yenidata = new SendData();

        yenidata.soruindex = cozulenindexstring;
        yenidata.kullaniciadi = MainActivity.kullaniciadi;
        yenidata.task1 = matchdata;
        yenidata.skor = match.skor;
        String data = gson.toJson(yenidata);
        try {
            sonuc = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/join", data).get().replace("\"", "");
            if (sonuc == "true") {
                gelen = true;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        return gelen;

    }

    public Boolean joinkontrol() {
        Boolean gelen = false;
        String gelendata = "";
        try {
            gelendata = new indir().execute(("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/joinkontrol/" + MainActivity.kullaniciadi)).get();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Gson gson = new Gson();

        if (!gelendata.contains("hata")) {
            Toast.makeText(this, gelendata, Toast.LENGTH_LONG).show();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> temp = gson.fromJson(gelendata, type);
            compitation_soruekrani.compitationsoruekrani_cozulensoruindexleri = gson.fromJson(temp.get(0), type);
            match = gson.fromJson(temp.get(1), Match.class);
            gelen = true;
        } else {

            gelen = false;
        }
        return gelen;
    }

    public Boolean startkontrol() {
        {

            Boolean bool = false;
            String gelen = "";
            try {
                gelen = new indir().execute(("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/startkontrol")).get().replace("\"", "");

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                return false;
            }
            katilimcisayisi = 0;
            if (gelen != "0") {
                katilimcisayisi = Integer.parseInt(gelen.split(",")[0]);
            }
            if (katilimcisayisi > 10) {
                bool = join == true;
                odul1 = 25 + 2 * (katilimcisayisi - 10);
                odul2 = 15 + 1 * (katilimcisayisi - 10);
                odul3 = 10 + (int) 0.5 * (katilimcisayisi - 10);
            } else {
                odul1 = (int) 2.5 * katilimcisayisi;
                odul2 = (int) 1.5 * katilimcisayisi;
                odul3 = katilimcisayisi;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            if (sdf.format(tarihal()).equals("Sunday")) {
                kazanan1 = gelen.split(",")[1];

                kazanan2 = gelen.split(",")[2];

                kazanan3 = gelen.split(",")[3];
            }
            return bool;
        }
    }
}

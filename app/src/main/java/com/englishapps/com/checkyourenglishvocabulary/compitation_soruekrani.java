package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class compitation_soruekrani extends AppCompatActivity {
    public static List<Soru> compitationsoruekrani_tumsorular = new ArrayList<Soru>();
    public static List<String> compitationsoruekrani_cozulensoruindexleri = new ArrayList<String>();
    public static int compitationsoruekrani_eklenensorusayisi;
    public static String url;
    public static int sorusayisi = 1;
    public Soru cikansoru;
    public CountDownTimer timer;
    public TextView soru;
    public RadioButton cvp1;
    public RadioButton cvp2;
    public RadioButton cvp3;
    public RadioButton cvp4;
    public RadioButton cvp5;
    public TextView questiontxt;
    public TextView goldtxt;
    public Button jokerbtn;
    public Button btnskip;
    public Button nextbtn;
    public Button backbtn;
    public Button reportbtn;
    TextView dogrutxt;
    TextView yanlistxt;
    TextView atlanantxt;
    TextView skortxt;
    boolean isRunning = false;
    LinearLayout nextbuttnln;
    Random rastgele = new Random();
    Random sikele = new Random();
    private boolean backbtnbool = false;
    private LinearLayout al;
    private boolean goldbuttontiklama = false;
    private String msjsonuc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compitation_soruekrani);

        final TextView sure = (TextView) findViewById(R.id.compitationsoru_suretxt);
        soru = (TextView) findViewById(R.id.compitationsoru_sorutext);
        questiontxt = (TextView) findViewById(R.id.compitationsoru_sorutxt);
        cvp1 = (RadioButton) findViewById(R.id.compitationsoru_cevap1txt);
        cvp2 = (RadioButton) findViewById(R.id.compitationsoru_cevap2txt);
        cvp3 = (RadioButton) findViewById(R.id.compitationsoru_cevap3txt);
        cvp4 = (RadioButton) findViewById(R.id.compitationsoru_cevap4txt);
        cvp5 = (RadioButton) findViewById(R.id.compitationsoru_cevap5txt);
        dogrutxt = (TextView) findViewById(R.id.compitationsoru_dogrutxt);
        yanlistxt = (TextView) findViewById(R.id.compitationsoru_yanlistxt);
        atlanantxt = (TextView) findViewById(R.id.compitationsoru_atlanantxt);
        skortxt = (TextView) findViewById(R.id.compitationsoru_skortxt);
        goldtxt = (TextView) findViewById(R.id.compitationsoru_goldtxt);
        dogrutxt.setText(Integer.toString(compitation.match.dogru));
        yanlistxt.setText(Integer.toString(compitation.match.yanlis));
        atlanantxt.setText(Integer.toString(compitation.match.gecilen));
        skortxt.setText(Integer.toString(compitation.match.skor));
        sorusayisi = compitation.match.dogru + compitation.match.yanlis + compitation.match.gecilen;
        questiontxt.setText(getString(R.string.question) + Integer.toString(sorusayisi + 1));
        nextbuttnln = (LinearLayout) findViewById(R.id.compitationsoru_nextbuttonlinear);
        EditText usernametxt = (EditText) findViewById(R.id.compitationsoru_usernametxt);
        goldtxt.setText(Integer.toString(MainActivity.gold));
        usernametxt.setText(MainActivity.kullaniciadi);
        cvp1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Siksec(cikansoru.cevap1, "cevap1");

            }
        });
        cvp2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Siksec(cikansoru.cevap2, "cevap2");
            }
        });
        cvp3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Siksec(cikansoru.cevap3, "cevap3");
            }
        });
        cvp4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Siksec(cikansoru.cevap4, "cevap4");
            }
        });
        cvp5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Siksec(cikansoru.cevap5, "cevap5");
            }
        });


        al = (LinearLayout) findViewById(R.id.compitationsoru_ortabutonlinear);

        try {
            al.addView(ortabutonview());
            nextbuttnln.addView(nexttime());


            compitationsoruekrani_tumsorular = getsoru();


            compitationsoruekrani_eklenensorusayisi = compitationsoruekrani_tumsorular.size();
            timer = new CountDownTimer(25000, 1000) {

                public void onTick(long millisUntilFinished) {
                    sure.setText(Integer.toString((int) (millisUntilFinished / 1000)));
                    isRunning = true;
                    //here you can have your logic to set text to edittext
                }

                public void onFinish() {
                    sure.setText("0");
                    isRunning = false;
                    Toast.makeText(getBaseContext(), R.string.soruekran1, Toast.LENGTH_SHORT).show();


                    if (MainActivity.sesdurumu == true) {
                        MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.timeranout);
                        ses.start();
                    }

                    dogrucevapisaretle();
                    kapat();
                    compitationsoruekrani_cozulensoruindexleri.add(cikansoru.id.replace(" ", ""));
                    yanlistxt.setText(Integer.toString(Integer.parseInt((String) yanlistxt.getText()) + 1));
                    int skor = compitation.match.skor - 50;
                    skortxt.setText(Integer.toString(skor));

                    btnskip.setEnabled(false);
                    jokerbtn.setEnabled(false);
                    nextbtn.setEnabled(true);
                    nextbtn.setVisibility(View.VISIBLE);
                    reportbtn.setEnabled(true);
                    backbtnbool = true;
                    Skoryaz();
                    sorusayisi = compitation.match.dogru + compitation.match.yanlis + compitation.match.gecilen;
                    if (sorusayisi == compitationsoruekrani_eklenensorusayisi) {
                        compitation.match.bitis = true;
                        sorusayisi = 1;
                        backbtnbool = true;
                        //compitationsoruekrani_cozulensoruindexleri.clear();
                        Toast.makeText(getBaseContext(), R.string.congratulations, Toast.LENGTH_SHORT).show();


                        try {
                            aylikistatistikekle();

                        } catch (Exception e) {

                        }
                        if (MainActivity.sesdurumu == true) {
                            MediaPlayer ses1 = MediaPlayer.create(compitation_soruekrani.this, R.raw.levelcomplated);
                            ses1.start();
                        }
                        nextbtn.setVisibility(View.INVISIBLE);
                    }

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
                }

            }.start();
            Rastgelesorucagir();
            jokerbtn = (Button) findViewById(R.id.compitationsoruekrani_jokerbutton);
            btnskip = (Button) findViewById(R.id.compitationsoruekrani_skipbutton);
            nextbtn = (Button) findViewById(R.id.compitationsoruekrani_nextbutton);
            backbtn = (Button) findViewById(R.id.compitationsoruekrani_backbutton);
            reportbtn = (Button) findViewById(R.id.compitationsoruekrani_reportbutton);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    Integer puanhesapla(String durum) {
        TextView sure = (TextView) findViewById(R.id.compitationsoru_suretxt);
        Integer kalan = Integer.parseInt(String.valueOf(sure.getText()));
        Integer Puan = 0;

        if (durum.equals("dogru")) {
            Puan = compitation.match.skor + 5 * kalan;
        } else if (durum.equals("yanlis")) {
            Puan = compitation.match.skor - 2 * kalan;
        } else {
            Puan = compitation.match.skor;
        }

        return Puan;
    }

    public void Rastgelesorucagir() {

        if (compitationsoruekrani_tumsorular.size() > 0) {
            int cikandeger = rastgele.nextInt(compitationsoruekrani_tumsorular.size());
            if (compitationsoruekrani_cozulensoruindexleri.contains(compitationsoruekrani_tumsorular.get(cikandeger).id)) {
                compitationsoruekrani_tumsorular.remove(cikandeger);
                Rastgelesorucagir();
            } else {
                soru.setText(compitationsoruekrani_tumsorular.get(cikandeger).soru);
                cikansoru = compitationsoruekrani_tumsorular.get(cikandeger);
                cvp1.setText(compitationsoruekrani_tumsorular.get(cikandeger).cevap1);
                cvp2.setText(compitationsoruekrani_tumsorular.get(cikandeger).cevap2);
                cvp3.setText(compitationsoruekrani_tumsorular.get(cikandeger).cevap3);
                cvp4.setText(compitationsoruekrani_tumsorular.get(cikandeger).cevap4);
                cvp5.setText(compitationsoruekrani_tumsorular.get(cikandeger).cevap5);
                questiontxt.setText(getString(R.string.question) + Integer.toString(sorusayisi + 1));
            }
        }


    }

    void jokerkullan() {
        if (MainActivity.gold >= 2) {
            if (isRunning == true) {
                MainActivity.gold += -2;
                goldtxt.setText(Integer.toString(MainActivity.gold));
                goldbuttontiklama = true;

                //timer.cancel();
                int dogru = cikansoru.dogrucevap;
                int e1 = randomsik();
                compitationsoruekrani_cozulensoruindexleri.add(cikansoru.id.replace(" ", ""));

                while (e1 == dogru) {
                    e1 = randomsik();
                }

                int e2 = randomsik();
                while (e2 == e1 || e2 == dogru) {
                    e2 = randomsik();
                }
                //dogru cevaptan ve birbirlerinden farklı 2 sayı ürettik

                if (e1 == 1) {
                    cvp1.setTextColor(Color.GRAY);
                    cvp1.setEnabled(false);
                } else if (e1 == 2) {

                    cvp2.setTextColor(Color.GRAY);
                    cvp2.setEnabled(false);
                } else if (e1 == 3) {
                    cvp3.setTextColor(Color.GRAY);
                    cvp3.setEnabled(false);
                } else if (e1 == 4) {
                    cvp4.setTextColor(Color.GRAY);
                    cvp4.setEnabled(false);
                } else if (e1 == 5) {
                    cvp5.setTextColor(Color.GRAY);
                    cvp5.setEnabled(false);
                }

                if (e2 == 1) {
                    cvp1.setTextColor(Color.GRAY);
                    cvp1.setEnabled(false);
                } else if (e2 == 2) {
                    cvp2.setTextColor(Color.GRAY);
                    cvp2.setEnabled(false);
                } else if (e2 == 3) {
                    cvp3.setTextColor(Color.GRAY);
                    cvp3.setEnabled(false);
                } else if (e2 == 4) {
                    cvp4.setTextColor(Color.GRAY);
                    cvp4.setEnabled(false);
                } else if (e2 == 5) {
                    cvp5.setTextColor(Color.GRAY);
                    cvp5.setEnabled(false);
                }
                jokerbtn.setEnabled(false);
                backbtnbool = false;
            } else {
                goldtxt.setText(Integer.toString(MainActivity.gold));
            }
            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.jokerbutton);
                ses.start();
            }

        } else if (MainActivity.gold < 2) {
            msjsonuc = getString(R.string.yourstats4);

            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(compitation_soruekrani.this);
            alertDialogBuilder1.setTitle(R.string.yourstats3);
            alertDialogBuilder1
                    .setMessage(msjsonuc)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.okbutton), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


            goldbuttontiklama = false;
            AlertDialog a1 = alertDialogBuilder1.create();

            // show it
            a1.show();
            goldbuttontiklama = false;
        }
    }

    void skipyap() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (MainActivity.gold >= 1) {
            MainActivity.gold += -1;
            goldtxt.setText(Integer.toString(MainActivity.gold));
            goldbuttontiklama = true;


            sorusayisi++;
            timer.cancel();
            jokerbtn.setEnabled(false);
            btnskip.setEnabled(false);
            backbtnbool = true;
            dogrucevapisaretle();
            nextbtn.setVisibility(View.VISIBLE);
            reportbtn.setEnabled(true);
            nextbtn.setEnabled(true);
            cvp5.setEnabled(false);

            if (sorusayisi == compitationsoruekrani_eklenensorusayisi) {
                timer.cancel();
                sorusayisi = 1;
                backbtnbool = true;
                reportbtn.setEnabled(true);
                jokerbtn.setEnabled(false);
                nextbtn.setVisibility(View.INVISIBLE);
                btnskip.setEnabled(false);
                //compitationsoruekrani_cozulensoruindexleri.clear();
                atlanantxt.setText(Integer.toString((Integer.parseInt((String) atlanantxt.getText()) + 1)));


                cvp1.setEnabled(false);
                cvp2.setEnabled(false);
                cvp3.setEnabled(false);
                cvp4.setEnabled(false);

                Toast.makeText(getBaseContext(), getString(R.string.congratulations), Toast.LENGTH_LONG).show();
                compitation.match.bitis = true;
                Skoryaz();

                try {
                    aylikistatistikekle();

                } catch (Exception e) {

                }
                if (MainActivity.sesdurumu == true) {
                    MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.levelcomplated);
                    ses.start();
                }
            } else {
                goldtxt.setText(Integer.toString(MainActivity.gold));
                atlanantxt.setText(Integer.toString(Integer.parseInt((String) atlanantxt.getText()) + 1));


                compitationsoruekrani_cozulensoruindexleri.add(cikansoru.id.replace(" ", ""));


                sorusayisi = compitation.match.dogru + compitation.match.yanlis + compitation.match.gecilen;
                if (MainActivity.sesdurumu == true) {
                    MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.skipthequestion);
                    ses.start();
                }
            }
            Skoryaz();
            veritabaninayaz();


        } else if (MainActivity.gold < 1) {
            msjsonuc = getString(R.string.yourstats4);

            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(compitation_soruekrani.this);
            alertDialogBuilder1.setTitle(getString(R.string.yourstats3));
            alertDialogBuilder1
                    .setMessage(msjsonuc)
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.okbutton), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


            goldbuttontiklama = false;
            AlertDialog a1 = alertDialogBuilder1.create();

            // show it
            a1.show();
            goldbuttontiklama = false;

        }

    }

    void Temizle() {
        sorusayisi = sorusayisi + 1;
        Intent i = getIntent();
        finish();
        startActivity(i);
    }

    void Skoryaz() {

        compitation.match.skor = Integer.parseInt(skortxt.getText().toString());
        compitation.match.dogru = Integer.parseInt(dogrutxt.getText().toString());
        compitation.match.yanlis = Integer.parseInt(yanlistxt.getText().toString());
        compitation.match.gecilen = Integer.parseInt(atlanantxt.getText().toString());


    }

    void Goldkullan(String tür) {
        final String tur = tür;
        String mesaj = "";
        if (tür == "skip") {
            mesaj = getString(R.string.skipsorusu);
            if (MainActivity.questiondurumu == false) {
                try {
                    skipyap();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.yourstats3));
                alertDialogBuilder
                        .setMessage(mesaj)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yestext, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (tur.equals("skip")) {
                                    try {
                                        skipyap();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                } else if (tur.equals("joker")) {
                                    jokerkullan();
                                }

                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
        } else if (tür == "joker") {
            mesaj = getString(R.string.jokersorusu);
            if (MainActivity.questiondurumu == false) {
                jokerkullan();
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.yourstats3));
                alertDialogBuilder
                        .setMessage(mesaj)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yestext, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (tur.equals("skip")) {
                                    try {
                                        skipyap();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                } else if (tur.equals("joker")) {
                                    jokerkullan();
                                }

                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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


    }



    void veritabaninayaz() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (MainActivity.logout == false) {
            if (MainActivity.giriscalisti == true) {
                Gson gson = new Gson();
                String compitationsoruekrani_cozulensoruindexleri = gson.toJson(compitation_soruekrani.compitationsoruekrani_cozulensoruindexleri);
                String matchdata = gson.toJson(compitation.match);


                SendData yenidata = new SendData();
                yenidata.soruindex = compitationsoruekrani_cozulensoruindexleri;
                yenidata.task1 = matchdata;
                yenidata.skor = compitation.match.skor;
                yenidata.gold = MainActivity.gold;
                yenidata.kullaniciadi = MainActivity.kullaniciadi;
                gson = new Gson();
                String data = gson.toJson(yenidata);
                try {
                    String sonuc = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/haftaliksetskor", data).get();
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

    void kapat() {
        cvp1.setEnabled(false);
        cvp2.setEnabled(false);
        cvp3.setEnabled(false);
        cvp4.setEnabled(false);
        cvp5.setEnabled(false);

    }

    void dogrucevapisaretle() {
        int dogrucevapindex = cikansoru.dogrucevap;
        if (dogrucevapindex == 1) {
            cvp1.setTextColor(Color.parseColor("#006400"));
            kapat();
        }
        if (dogrucevapindex == 2) {
            cvp2.setTextColor(Color.parseColor("#006400"));
            kapat();
        }
        if (dogrucevapindex == 3) {
            cvp3.setTextColor(Color.parseColor("#006400"));
            kapat();
        }
        if (dogrucevapindex == 4) {
            cvp4.setTextColor(Color.parseColor("#006400"));
            kapat();
        }
        if (dogrucevapindex == 5) {
            cvp5.setTextColor(Color.parseColor("#006400"));
            kapat();
        }
    }

    public int randomsik() {
        int deger = sikele.nextInt(5) + 1;
        return deger;
    }

    public View ortabutonview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("Skip");
        adad.add("joker");
        adad.add("report");
        adad.add("back");

        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(marg, 0, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(4);
        int k = 1;
        String[] value = null;
        for (int i = 0; i < 1; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 4; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 4.4));
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


                        Intent i = new Intent();
                        switch ((v.getTag().toString())) {
                            case "Skip":
                                //skip kodları
                                if (MainActivity.giriscalisti == false || MainActivity.logout == true) {
                                    Toast.makeText(getBaseContext(), getString(R.string.mainyazi1), Toast.LENGTH_LONG).show();
                                } else {
                                    //skip çalşmıyor

                                    Goldkullan("skip");


                                }
                                break;
                            case "joker":
                                if (MainActivity.giriscalisti == false || MainActivity.logout == true) {
                                    Toast.makeText(getBaseContext(), getString(R.string.mainyazi1), Toast.LENGTH_LONG).show();
                                } else {
                                    Goldkullan("joker");


                                }
                                break;
                            case "report":
                                //report koşulunu yaz
                                if (MainActivity.giriscalisti == true) {

                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"doguhanyangoz@gmail.com"});
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "CheckYourVocabularyCompitationReportQuestionId=" + cikansoru.id);
                                    startActivity(Intent.createChooser(intent, "Send Email"));
                                } else {
                                    Toast.makeText(getBaseContext(), getString(R.string.mainyazi1), Toast.LENGTH_SHORT);
                                }
                                break;
                            case "back":
                                if (backbtnbool == true) {
                                    // sorusayisi = 1;
                                    // skoru uygulamaya ve veritabınana yazıyor
                                    timer.cancel();
                                    Skoryaz();
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
                                    if (MainActivity.logout == true) {
                                        MainActivity.giriscalisti = false;
                                    }


                                    sorusayisi = compitation.match.dogru + compitation.match.yanlis + compitation.match.gecilen;
                                    timer.cancel();
                                    MainActivity.interstitial = new InterstitialAd(getBaseContext());
                                    MainActivity.interstitial.setAdUnitId("ca-app-pub-9551427770579242/3444221817");

                                    AdRequest adRequest1 = new AdRequest.Builder().build();

                                    MainActivity.interstitial.loadAd(adRequest1);

                                    MainActivity.interstitial.setAdListener(new AdListener() {
                                        @Override
                                        public void onAdLoaded() {
                                            if (MainActivity.interstitial.isLoaded()) {
                                                MainActivity.interstitial.show();
                                            }
                                        }
                                    });

                                    i.setClass(compitation_soruekrani.this, compitation.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getBaseContext(), R.string.soruekranibackdialog, Toast.LENGTH_LONG).show();
                                }

                                break;
                        }

                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.sorubutonyazi)));
                    button.setId(R.id.compitationsoruekrani_skipbutton);
                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.sorubutonyazi2)));
                    button.setId(R.id.compitationsoruekrani_jokerbutton);
                } else if (j == 2) {
                    button.setBackgroundResource(resimara(getString(R.string.reportthequestionbutton)));
                    button.setEnabled(false);
                    button.setId(R.id.compitationsoruekrani_reportbutton);
                } else if (j == 3) {
                    button.setBackgroundResource(resimara(getString(R.string.backtomenutosavebutton)));
                    button.setId(R.id.compitationsoruekrani_backbutton);
                }
                tableRow.addView(button, tb1);
                k = k + 1;
            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }

    private void Siksec(String cevap, String name) {
        timer.cancel();
        reportbtn.setEnabled(true);
        String secilen = cevap;

        int dogrucevapindex = cikansoru.dogrucevap;
        compitationsoruekrani_cozulensoruindexleri.add(cikansoru.id.replace(" ", ""));

        String dogrucevap = "";
        if (dogrucevapindex == 1) {
            dogrucevap = cikansoru.cevap1;
        } else if (dogrucevapindex == 2) {
            dogrucevap = cikansoru.cevap2;
        } else if (dogrucevapindex == 3) {
            dogrucevap = cikansoru.cevap3;
        } else if (dogrucevapindex == 4) {
            dogrucevap = cikansoru.cevap4;
        } else if (dogrucevapindex == 5) {
            dogrucevap = cikansoru.cevap5;
        }
        if (secilen.equals(dogrucevap)) {
            dogrucevapisaretle();
            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.trueanswer);
                ses.start();
            }
            dogrutxt.setText(Integer.toString(Integer.parseInt((String) dogrutxt.getText()) + 1));

            skortxt.setText(Integer.toString(puanhesapla("dogru")));
        } else {
            dogrucevapisaretle();
            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.wronganswer);
                ses.start();
            }
            if (name.equals("cevap1")) {
                cvp1.setTextColor(Color.RED);
            } else if (name.equals("cevap2")) {
                cvp2.setTextColor(Color.RED);
            } else if (name.equals("cevap3")) {
                cvp3.setTextColor(Color.RED);
            } else if (name.equals("cevap4")) {
                cvp4.setTextColor(Color.RED);
            } else if (name.equals("cevap5")) {
                cvp5.setTextColor(Color.RED);
            }


            yanlistxt.setText(Integer.toString(Integer.parseInt((String) yanlistxt.getText()) + 1));


            skortxt.setText(Integer.toString(puanhesapla("yanlis")));
        }

        btnskip.setEnabled(false);
        jokerbtn.setEnabled(false);
        nextbtn.setEnabled(true);
        nextbtn.setVisibility(View.VISIBLE);
        reportbtn.setEnabled(true);
        backbtnbool = true;
        Skoryaz();
        sorusayisi = compitation.match.dogru + compitation.match.yanlis + compitation.match.gecilen;

        if (sorusayisi == compitationsoruekrani_eklenensorusayisi) {


            sorusayisi = 1;
            backbtnbool = true;
            //compitationsoruekrani_cozulensoruindexleri.clear();


            Toast.makeText(getBaseContext(), getString(R.string.congratulations), Toast.LENGTH_SHORT).show();
            Skoryaz();
            compitation.match.bitis = true;


            try {
                aylikistatistikekle();

            } catch (Exception e) {

            }
            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.levelcomplated);
                ses.start();
            }

            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(compitation_soruekrani.this, R.raw.levelcomplated);
                ses.start();
            }

            nextbtn.setVisibility(View.INVISIBLE);
        }

        Skoryaz();
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

    }

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }

    public View nexttime() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("Next");
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int marg2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(marg2, marg, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(4);
        int k = 1;
        String[] value = null;
        for (int i = 0; i < 1; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 1; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 4.5));
                double oran = (float) 43 / (float) 99;
                int yukseklik = (int) (oran * genislik);
                //99x43 level buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins((int) (bosluk * 1.1), bosluk, bosluk, bosluk);
                Button button = new Button(this);
                button.setVisibility(View.INVISIBLE);
                //butonadı
                button.setTag(adad.get(j));
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //kanal adı ve linkini tagden alıyor

                        Temizle();


                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.nextquestionbutton)));
                    button.setId(R.id.compitationsoruekrani_nextbutton);
                }

                tableRow.addView(button, tb1);
                k = k + 1;
            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }

    public void aylikistatistikekle() {
        String sonuc = "basarili";

        try {
            SendData yenidata = new SendData();
            Gson gson = new Gson();
            yenidata.kullaniciadi = MainActivity.kullaniciadi;
            yenidata.task1 = gson.toJson(compitation.match);

            String data = gson.toJson(yenidata);
            sonuc = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/aylikistatistikekle", data).get().replace("\"", "");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage() + "," + sonuc, Toast.LENGTH_LONG).show();
        }
    }

    public List<Soru> getsoru() {

        List<Soru> gelen = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Soru>>() {
        }.getType();
        try {
            String data = new indir().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/getcompatetionSoru/").get();
            gelen = gson.fromJson(data, type);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return gelen;

    }
}

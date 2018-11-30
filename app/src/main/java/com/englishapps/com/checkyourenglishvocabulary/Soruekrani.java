package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Soruekrani extends AppCompatActivity {
    public static List<Soru> tumsorular = new ArrayList<Soru>();
    public static List<String> cozulensoruindexleri = new ArrayList<String>();

    public static int eklenensorusayisi;
    public static Level cikanlevel;
    public static String url;
    public static int level = 0;
    public static int task = 0;
    public static int sorusayisi = 1;
    public int soruindexi = 0;
    public Soru cikansoru;
    public String Urll;
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
        setContentView(R.layout.activity_soruekrani);
        final TextView sure = (TextView) findViewById(R.id.suretxt);
        soru = (TextView) findViewById(R.id.sorutext);
        questiontxt = (TextView) findViewById(R.id.sorutxt);
        cvp1 = (RadioButton) findViewById(R.id.cevap1txt);
        cvp2 = (RadioButton) findViewById(R.id.cevap2txt);
        cvp3 = (RadioButton) findViewById(R.id.cevap3txt);
        cvp4 = (RadioButton) findViewById(R.id.cevap4txt);
        cvp5 = (RadioButton) findViewById(R.id.cevap5txt);
        dogrutxt = (TextView) findViewById(R.id.dogrutxt);
        yanlistxt = (TextView) findViewById(R.id.yanlistxt);
        atlanantxt = (TextView) findViewById(R.id.atlanantxt);
        skortxt = (TextView) findViewById(R.id.skortxt);
        goldtxt = (TextView) findViewById(R.id.soruekrangoldtxt);
        dogrutxt.setText(Integer.toString(cikanlevel.dogru));
        yanlistxt.setText(Integer.toString(cikanlevel.yanlis));
        atlanantxt.setText(Integer.toString(cikanlevel.gecilen));
        skortxt.setText(Integer.toString(cikanlevel.skor));
        sorusayisi = cikanlevel.dogru + cikanlevel.yanlis + cikanlevel.gecilen;
        questiontxt.setText(getString(R.string.question) + Integer.toString(sorusayisi + 1));
        nextbuttnln = (LinearLayout) findViewById(R.id.nextbuttonlinear);
        EditText usernametxt = (EditText) findViewById(R.id.soruekraniusernametxt);
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


        al = (LinearLayout) findViewById(R.id.ortabutonlinear);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Urll = null;
            } else {
                Urll = extras.getString("data");
            }
        } else {
            Urll = (String) savedInstanceState.getSerializable("data");
        }
        try {
            al.addView(ortabutonview());
            nextbuttnln.addView(nexttime());


            tumsorular = getsoru();

            eklenensorusayisi = tumsorular.size();
            timer = new CountDownTimer(20000, 1000) {

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
                        MediaPlayer ses = MediaPlayer.create(Soruekrani.this, R.raw.timeranout);
                        ses.start();
                    }

                    dogrucevapisaretle();
                    kapat();
                    cozulensoruindexleri.add(cikansoru.id);


                    yanlistxt.setText(Integer.toString(Integer.parseInt((String) yanlistxt.getText()) + 1));

                    int skor = Integer.parseInt((String) dogrutxt.getText()) * 5 - Integer.parseInt((String) yanlistxt.getText()) * 2;
                    skortxt.setText(Integer.toString(skor));

                    Skoryaz();
                    try {
                        veritabaninayaz();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    btnskip.setEnabled(false);
                    jokerbtn.setEnabled(false);
                    nextbtn.setEnabled(true);
                    nextbtn.setVisibility(View.VISIBLE);
                    reportbtn.setEnabled(true);
                    backbtnbool = true;
                    sorusayisi = cikanlevel.dogru + cikanlevel.yanlis + cikanlevel.gecilen;
                    if (sorusayisi == eklenensorusayisi) {

                        Toast.makeText(getBaseContext(), getString(R.string.task) + task + getString(R.string.level) + level + getString(R.string.hasbencomplated), Toast.LENGTH_LONG).show();
                        sorusayisi = 1;
                        backbtnbool = true;
                        cozulensoruindexleri.clear();


                        if (task == 1) {
                            if (level == 1) {
                                MainActivity.task1.level1.bitis = true;
                                MainActivity.task1.level1.reset = false;
                                if (MainActivity.giriscalisti == false) {
                                    Toast.makeText(getBaseContext(), R.string.soruekran2, Toast.LENGTH_LONG).show();
                                }
                            } else if (level == 2) {
                                MainActivity.task1.level2.bitis = true;
                                MainActivity.task1.level2.reset = false;
                            } else if (level == 3) {
                                MainActivity.task1.level3.bitis = true;
                                MainActivity.task1.level3.reset = false;
                                MainActivity.task1.bitis = true;

                            }
                        } else if (task == 2) {
                            if (level == 1) {
                                MainActivity.task2.level1.bitis = true;
                                MainActivity.task2.level1.reset = false;
                            } else if (level == 2) {
                                MainActivity.task2.level2.bitis = true;
                                MainActivity.task2.level2.reset = false;
                            } else if (level == 3) {
                                MainActivity.task2.level3.bitis = true;
                                MainActivity.task2.level3.reset = false;
                                MainActivity.task2.bitis = true;
                            }
                        } else if (task == 3) {
                            if (level == 1) {
                                MainActivity.task3.level1.bitis = true;
                                MainActivity.task3.level1.reset = false;
                            } else if (level == 2) {
                                MainActivity.task3.level2.bitis = true;
                                MainActivity.task3.level2.reset = false;
                            } else if (level == 3) {
                                MainActivity.task3.level3.bitis = true;
                                MainActivity.task3.level3.reset = false;
                                MainActivity.task3.bitis = true;
                                Toast.makeText(getBaseContext(), R.string.congratulations, Toast.LENGTH_SHORT).show();
                                if (MainActivity.sesdurumu == true) {
                                    MediaPlayer ses1 = MediaPlayer.create(Soruekrani.this, R.raw.levelcomplated);
                                    ses1.start();
                                }
                            }
                        }
                        nextbtn.setVisibility(View.INVISIBLE);

                    }
                }

            }.start();
            Rastgelesorucagir();
            jokerbtn = (Button) findViewById(R.id.jokerbutton);
            btnskip = (Button) findViewById(R.id.skipbutton);
            nextbtn = (Button) findViewById(R.id.nextbutton);
            backbtn = (Button) findViewById(R.id.backbutton);
            reportbtn = (Button) findViewById(R.id.reportbutton);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void Rastgelesorucagir() {
        int cikandeger = rastgele.nextInt(tumsorular.size());
        if (tumsorular.size() > 0) {
            if (cozulensoruindexleri.contains(tumsorular.get(cikandeger).id)) {
                tumsorular.remove(cikandeger);
                Rastgelesorucagir();
            } else {
                soru.setText(tumsorular.get(cikandeger).soru);
                cikansoru = tumsorular.get(cikandeger);
                cvp1.setText(tumsorular.get(cikandeger).cevap1);
                cvp2.setText(tumsorular.get(cikandeger).cevap2);
                cvp3.setText(tumsorular.get(cikandeger).cevap3);
                cvp4.setText(tumsorular.get(cikandeger).cevap4);
                cvp5.setText(tumsorular.get(cikandeger).cevap5);
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

                timer.cancel();
                int dogru = cikansoru.dogrucevap;
                int e1 = randomsik();
                cozulensoruindexleri.add(cikansoru.id);

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
                MediaPlayer ses = MediaPlayer.create(Soruekrani.this, R.raw.jokerbutton);
                ses.start();
            }

        } else if (MainActivity.gold < 2) {
            msjsonuc = getString(R.string.yourstats4);

            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(Soruekrani.this);
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

    void skipyap() {
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

            if (sorusayisi == eklenensorusayisi) {
                timer.cancel();

                Toast.makeText(getBaseContext(), getString(R.string.task) + task + getString(R.string.level) + level + getString(R.string.hasbencomplated), Toast.LENGTH_LONG).show();
                sorusayisi = 1;
                backbtnbool = true;
                reportbtn.setEnabled(true);
                jokerbtn.setEnabled(false);
                nextbtn.setVisibility(View.INVISIBLE);
                btnskip.setEnabled(false);
                cozulensoruindexleri.clear();
                atlanantxt.setText(Integer.toString((Integer.parseInt((String) atlanantxt.getText()) + 1)));
                int skor = Integer.parseInt((String) dogrutxt.getText()) * 5 - Integer.parseInt((String) yanlistxt.getText()) * 2;
                skortxt.setText(Integer.toString(skor));
                cvp1.setEnabled(false);
                cvp2.setEnabled(false);
                cvp3.setEnabled(false);
                cvp4.setEnabled(false);

                if (task == 1) {
                    if (level == 1) {
                        MainActivity.task1.level1.bitis = true;
                        MainActivity.task1.level1.reset = false;
                    } else if (level == 2) {
                        MainActivity.task1.level2.bitis = true;
                        MainActivity.task1.level2.reset = false;
                    } else if (level == 3) {
                        MainActivity.task1.level3.bitis = true;
                        MainActivity.task1.level3.bitis = false;
                        MainActivity.task1.bitis = true;
                    }
                } else if (task == 2) {
                    if (level == 1) {
                        MainActivity.task2.level1.bitis = true;
                        MainActivity.task2.level1.reset = false;
                    } else if (level == 2) {
                        MainActivity.task2.level2.bitis = true;
                        MainActivity.task2.level2.reset = false;
                    } else if (level == 3) {
                        MainActivity.task2.level3.bitis = true;
                        MainActivity.task2.level3.reset = false;
                        MainActivity.task2.bitis = true;
                    }
                } else if (task == 3) {
                    if (level == 1) {
                        MainActivity.task3.level1.bitis = true;
                        MainActivity.task3.level1.reset = false;
                    } else if (level == 2) {
                        MainActivity.task3.level2.bitis = true;
                        MainActivity.task3.level2.reset = false;
                    } else if (level == 3) {
                        MainActivity.task3.level3.bitis = true;
                        MainActivity.task3.level3.reset = false;
                        MainActivity.task3.bitis = true;
                        Toast.makeText(getBaseContext(), getString(R.string.congratulations), Toast.LENGTH_LONG).show();
                        if (MainActivity.sesdurumu == true) {
                            MediaPlayer ses = MediaPlayer.create(Soruekrani.this, R.raw.levelcomplated);
                            ses.start();
                        }
                    }
                }
            } else {
                goldtxt.setText(Integer.toString(MainActivity.gold));
                atlanantxt.setText(Integer.toString(Integer.parseInt((String) atlanantxt.getText()) + 1));
                int skor = Integer.parseInt((String) dogrutxt.getText()) * 5 - Integer.parseInt((String) yanlistxt.getText()) * 2;
                skortxt.setText(Integer.toString(skor));
                cozulensoruindexleri.add(cikansoru.id);

                Skoryaz();
                sorusayisi = cikanlevel.dogru + cikanlevel.yanlis + cikanlevel.gecilen;
                if (MainActivity.sesdurumu == true) {
                    MediaPlayer ses = MediaPlayer.create(Soruekrani.this, R.raw.skipthequestion);
                    ses.start();
                }
            }


        } else if (MainActivity.gold < 1) {
            msjsonuc = getString(R.string.yourstats4);

            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(Soruekrani.this);
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
        if (level == 3) {
            if (task == 1) {

                MainActivity.task1.level3.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task1.level3.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task1.level3.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task1.level3.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            } else if (task == 2) {

                MainActivity.task2.level3.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task2.level3.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task2.level3.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task2.level3.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            } else if (task == 3) {

                MainActivity.task3.level3.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task3.level3.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task3.level3.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task3.level3.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            }
        } else if (level == 2) {

            if (task == 1) {
                MainActivity.task1.level2.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task1.level2.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task1.level2.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task1.level2.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            } else if (task == 2) {
                MainActivity.task2.level2.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task2.level2.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task2.level2.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task2.level2.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            } else if (task == 3) {
                MainActivity.task3.level2.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task3.level2.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task3.level2.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task3.level2.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            }

        } else if (level == 1) {

            if (task == 1) {
                MainActivity.task1.level1.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task1.level1.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task1.level1.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task1.level1.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            } else if (task == 2) {
                MainActivity.task2.level1.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task2.level1.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task2.level1.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task2.level1.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            } else if (task == 3) {
                MainActivity.task3.level1.skor = Integer.parseInt(skortxt.getText().toString());
                MainActivity.task3.level1.dogru = Integer.parseInt(dogrutxt.getText().toString());
                MainActivity.task3.level1.yanlis = Integer.parseInt(yanlistxt.getText().toString());
                MainActivity.task3.level1.gecilen = Integer.parseInt(atlanantxt.getText().toString());
            }
        }

    }

    void Goldkullan(String tür) {
        final String tur = tür;
        String mesaj = "";
        if (tür == "skip") {
            mesaj = getString(R.string.skipsorusu);
            if (MainActivity.questiondurumu == false) {
                skipyap();
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.yourstats3));
                alertDialogBuilder
                        .setMessage(mesaj)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yestext, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (tur.equals("skip")) {
                                    skipyap();
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
                                    skipyap();
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

    public String cpuId() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }


    void veritabaninayaz() throws ExecutionException, InterruptedException {
        if (MainActivity.logout == false) {
            if (MainActivity.giriscalisti == true) {
                Gson gson = new Gson();
                String task1 = gson.toJson(MainActivity.task1);
                String task2 = gson.toJson(MainActivity.task2);
                String task3 = gson.toJson(MainActivity.task3);
                String cozulensoruindexleri = gson.toJson(Soruekrani.cozulensoruindexleri);
                int toplamskor = MainActivity.task1.level1.skor + MainActivity.task1.level2.skor + MainActivity.task1.level3.skor + MainActivity.task2.level1.skor + MainActivity.task2.level2.skor + MainActivity.task2.level3.skor + MainActivity.task3.level1.skor + MainActivity.task3.level2.skor + MainActivity.task3.level3.skor;
                SendData yenidata = new SendData();
                yenidata.cpuid = cpuId();
                yenidata.soruindex = cozulensoruindexleri;
                yenidata.task1 = task1;
                yenidata.task2 = task2;
                yenidata.task3 = task3;
                yenidata.skor = toplamskor;
                yenidata.gold = MainActivity.gold;
                gson = new Gson();
                String data = gson.toJson(yenidata);
                String sonuc = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/setskor", data).get();
                // veritaninayz(cpuId(), cozulensoruindexleri, task1, task2, task3, toplamskor, MainActivity.gold);
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
                        //kanal adı ve linkini tagden alıyor
                        String gelentag = v.getTag().toString();
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
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "CheckYourVocabularyReportQuestionId=" + cikansoru.id);
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
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                    if (MainActivity.logout == true) {
                                        MainActivity.giriscalisti = false;
                                    }
                                    i.setClass(Soruekrani.this, singleplayer.class);
                                    startActivity(i);
                                    sorusayisi = cikanlevel.dogru + cikanlevel.yanlis + cikanlevel.gecilen;
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
                                } else {
                                    Toast.makeText(getBaseContext(), R.string.soruekranibackdialog, Toast.LENGTH_LONG).show();
                                }

                                break;
                        }

                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.sorubutonyazi)));
                    button.setId(R.id.skipbutton);
                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.sorubutonyazi2)));
                    button.setId(R.id.jokerbutton);
                } else if (j == 2) {
                    button.setBackgroundResource(resimara(getString(R.string.reportthequestionbutton)));
                    button.setEnabled(false);
                    button.setId(R.id.reportbutton);
                } else if (j == 3) {
                    button.setBackgroundResource(resimara(getString(R.string.backtomenutosavebutton)));
                    button.setId(R.id.backbutton);
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
        cozulensoruindexleri.add(cikansoru.id);

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
                MediaPlayer ses = MediaPlayer.create(Soruekrani.this, R.raw.trueanswer);
                ses.start();
            }
            dogrutxt.setText(Integer.toString(Integer.parseInt((String) dogrutxt.getText()) + 1));
            int skor = Integer.parseInt((String) dogrutxt.getText()) * 5 - Integer.parseInt((String) yanlistxt.getText()) * 2;
            skortxt.setText(Integer.toString(skor));
        } else {
            dogrucevapisaretle();
            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(Soruekrani.this, R.raw.wronganswer);
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
            int skor = Integer.parseInt((String) dogrutxt.getText()) * 5 - Integer.parseInt((String) yanlistxt.getText()) * 2;
            skortxt.setText(Integer.toString(skor));
        }
        Skoryaz();
        btnskip.setEnabled(false);
        jokerbtn.setEnabled(false);
        nextbtn.setEnabled(true);
        nextbtn.setVisibility(View.VISIBLE);
        reportbtn.setEnabled(true);
        backbtnbool = true;
        sorusayisi = cikanlevel.dogru + cikanlevel.yanlis + cikanlevel.gecilen;

        if (sorusayisi == eklenensorusayisi) {

            Toast.makeText(getBaseContext(), getString(R.string.task) + task + getString(R.string.level) + level + getString(R.string.hasbencomplated), Toast.LENGTH_SHORT).show();
            sorusayisi = 1;
            backbtnbool = true;
            cozulensoruindexleri.clear();


            if (task == 1) {
                if (level == 1) {
                    MainActivity.task1.level1.bitis = true;
                    MainActivity.task1.level1.reset = false;
                    if (MainActivity.giriscalisti == false) {
                        Toast.makeText(getBaseContext(), getString(R.string.soruekran2), Toast.LENGTH_SHORT).show();
                    }
                } else if (level == 2) {
                    MainActivity.task1.level2.bitis = true;
                    MainActivity.task1.level2.reset = false;
                } else if (level == 3) {
                    MainActivity.task1.level3.bitis = true;
                    MainActivity.task1.level3.reset = false;
                    MainActivity.task1.bitis = true;

                }
            } else if (task == 2) {
                if (level == 1) {
                    MainActivity.task2.level1.bitis = true;
                    MainActivity.task2.level1.reset = false;
                } else if (level == 2) {
                    MainActivity.task2.level2.bitis = true;
                    MainActivity.task2.level2.reset = false;
                } else if (level == 3) {
                    MainActivity.task2.level3.bitis = true;
                    MainActivity.task2.level3.reset = false;
                    MainActivity.task2.bitis = true;
                }
            } else if (task == 3) {
                if (level == 1) {
                    MainActivity.task3.level1.bitis = true;
                    MainActivity.task3.level1.reset = false;
                } else if (level == 2) {
                    MainActivity.task3.level2.bitis = true;
                    MainActivity.task3.level2.reset = false;
                } else if (level == 3) {
                    MainActivity.task3.level3.bitis = true;
                    MainActivity.task3.level3.reset = false;
                    MainActivity.task3.bitis = true;
                    Toast.makeText(getBaseContext(), getString(R.string.congratulations), Toast.LENGTH_SHORT).show();
                    if (MainActivity.sesdurumu == true) {
                        MediaPlayer ses = MediaPlayer.create(Soruekrani.this, R.raw.levelcomplated);
                        ses.start();
                    }
                }
            }
            nextbtn.setVisibility(View.INVISIBLE);

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
                    button.setId(R.id.nextbutton);
                }

                tableRow.addView(button, tb1);
                k = k + 1;
            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }

    public List<Soru> getsoru() {
        List<Soru> gelen = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Soru>>() {
        }.getType();
        try {
            String data = new indir().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/getsoru/" + Urll).get();

            gelen = gson.fromJson(data, type);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return gelen;
    }

}

package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class YourStatistics extends AppCompatActivity {
    public TextView totalscoretxt;
    public TextView totalknowtxt;
    public TextView totaluknowntxt;
    public TextView totalskipedtxt;
    public TextView goldtxt;
    public Integer index;
    private LinearLayout yourstatisticaltl;
    private String[] arraySpinner;
    private boolean goldbuttontiklama = false;
    private String msjsonuc = "";

    private void sil(String sil) {
        Iterator<String> it = Soruekrani.cozulensoruindexleri.iterator();
        while (it.hasNext()) {
            if (it.next().matches("(?i)(" + sil + ").*")) {
                it.remove();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_statistics);
        LinearLayout anasayfa = (LinearLayout) findViewById(R.id.yourstatisticlinear);
        goldtxt = (TextView) findViewById(R.id.sakirinkuzeni);
        EditText usertxt = (EditText) findViewById(R.id.sakirinamcasi);
        yourstatisticaltl = (LinearLayout) findViewById(R.id.yourstatisticaltlinear);
        AdView adView = (AdView) this.findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        try {
            yourstatisticaltl.addView(altview());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (MainActivity.giriscalisti == true) {


            goldtxt.setEnabled(true);
            goldtxt.setVisibility(View.VISIBLE);
            goldtxt.setText(Integer.toString(MainActivity.gold));
            usertxt.setEnabled(true);
            usertxt.setVisibility(View.VISIBLE);
            usertxt.setText(MainActivity.kullaniciadi);
            anasayfa.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.yourstatisticbackground))));

        } else {


            goldtxt.setEnabled(false);
            goldtxt.setVisibility(View.INVISIBLE);
            usertxt.setEnabled(false);
            usertxt.setVisibility(View.INVISIBLE);
            anasayfa.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.yourstatisticbackgrounduye))));
        }
        final Spinner mulayim = (Spinner) findViewById(R.id.sakir);

        this.arraySpinner = new String[]{
                getString(R.string.t1l1score), getString(R.string.t1l2score), getString(R.string.t1l3score), getString(R.string.t2l1score), getString(R.string.t2l2score), getString(R.string.t2l3score), getString(R.string.t3l1score), getString(R.string.t3l2score), getString(R.string.t3l3score), getString(R.string.generalscore)
        };

        totalscoretxt = (TextView) findViewById(R.id.statistotaltxt);
        totalknowtxt = (TextView) findViewById(R.id.statictsdogrutxt);
        totaluknowntxt = (TextView) findViewById(R.id.statisticyanlistxt);
        totalskipedtxt = (TextView) findViewById(R.id.statisticskip);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);
        mulayim.setAdapter(adapter);
        mulayim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                index = mulayim.getSelectedItemPosition();
                Button button1 = (Button) findViewById(R.id.ResetChosenbtn);
                Button resetbtn = (Button) findViewById(R.id.ResetAllbtn);

                if (MainActivity.giriscalisti == true) {
                    if (MainActivity.logout == false) {
                        button1.setVisibility(View.VISIBLE);
                        resetbtn.setVisibility(View.INVISIBLE);
                    }
                } else {
                    resetbtn.setVisibility(View.INVISIBLE);
                    button1.setVisibility(View.INVISIBLE);
                }

                if (index == 0) {
                    //task1 level1

                    if (MainActivity.task1.level1.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.logout == false) {
                            if (MainActivity.giriscalisti == true) {
                                button1.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task1.level1.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task1.level1.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task1.level1.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task1.level1.gecilen));


                } else if (index == 1) {
                    //task1 level1
                    if (MainActivity.task1.level2.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task1.level2.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task1.level2.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task1.level2.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task1.level2.gecilen));

                } else if (index == 2) {
                    //task1 level1
                    if (MainActivity.task1.level3.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task1.level3.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task1.level3.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task1.level3.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task1.level3.gecilen));

                } else if (index == 3) {
                    //task1 level1
                    if (MainActivity.task2.level1.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task2.level1.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task2.level1.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task2.level1.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task2.level1.gecilen));

                } else if (index == 4) {
                    //task1 level1
                    if (MainActivity.task2.level2.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task2.level2.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task2.level2.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task2.level2.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task2.level2.gecilen));

                } else if (index == 5) {
                    //task1 level1
                    if (MainActivity.task2.level3.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task2.level3.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task2.level3.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task2.level3.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task2.level3.gecilen));

                } else if (index == 6) {
                    //task1 level1
                    if (MainActivity.task3.level1.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task3.level1.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task3.level1.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task3.level1.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task3.level1.gecilen));

                } else if (index == 7) {
                    //task1 level1
                    if (MainActivity.task3.level2.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task3.level2.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task3.level2.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task3.level2.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task3.level2.gecilen));

                } else if (index == 8) {
                    //task1 level1
                    if (MainActivity.task3.level3.pasif == true) {
                        button1.setVisibility(View.INVISIBLE);
                    } else {
                        if (MainActivity.giriscalisti == true) {
                            button1.setVisibility(View.VISIBLE);
                        }
                    }
                    totalscoretxt.setText(Integer.toString(MainActivity.task3.level3.skor));
                    totalknowtxt.setText(Integer.toString(MainActivity.task3.level3.dogru));
                    totaluknowntxt.setText(Integer.toString(MainActivity.task3.level3.yanlis));
                    totalskipedtxt.setText(Integer.toString(MainActivity.task3.level3.gecilen));

                } else if (index == 9) {
                    //task1 level1
                    totalscoretxt.setText(Integer.toString((MainActivity.task1.level1.skor + MainActivity.task1.level2.skor + MainActivity.task1.level3.skor + MainActivity.task2.level1.skor + MainActivity.task2.level2.skor + MainActivity.task2.level3.skor + MainActivity.task3.level1.skor + MainActivity.task3.level2.skor + MainActivity.task3.level3.skor)));
                    totalknowtxt.setText(Integer.toString((MainActivity.task3.level1.dogru + MainActivity.task3.level2.dogru + MainActivity.task3.level3.dogru + MainActivity.task2.level1.dogru + MainActivity.task2.level2.dogru + MainActivity.task2.level3.dogru + MainActivity.task1.level1.dogru + MainActivity.task1.level2.dogru + +MainActivity.task1.level3.dogru)));
                    totaluknowntxt.setText(Integer.toString((MainActivity.task3.level1.yanlis + MainActivity.task3.level2.yanlis + MainActivity.task3.level3.yanlis + MainActivity.task2.level1.yanlis + MainActivity.task2.level2.yanlis + MainActivity.task2.level3.yanlis + MainActivity.task1.level1.yanlis + MainActivity.task1.level2.yanlis + +MainActivity.task1.level3.yanlis)));
                    totalskipedtxt.setText(Integer.toString((MainActivity.task3.level1.gecilen + MainActivity.task3.level2.gecilen + MainActivity.task3.level3.gecilen + MainActivity.task2.level1.gecilen + MainActivity.task2.level2.gecilen + MainActivity.task2.level3.gecilen + MainActivity.task1.level1.gecilen + MainActivity.task1.level2.gecilen + +MainActivity.task1.level3.gecilen)));

                    if (MainActivity.giriscalisti == true) {
                        resetbtn.setVisibility(View.VISIBLE);
                        button1.setVisibility(View.INVISIBLE);
                    }
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    public String cpuId() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }



    void veritabaninayaz() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
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
                try {
                    String sonuc = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/setskor", data).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
            }
        }


    void resetall() {
        if (MainActivity.gold >= 20) {
            MainActivity.gold += -20;
            goldtxt.setText(Integer.toString(MainActivity.gold));
            goldbuttontiklama = true;
            Soruekrani.sorusayisi = 1;

            Soruekrani.cozulensoruindexleri.clear();
            MainActivity.task1.bitis = false;
            MainActivity.task2.bitis = false;
            MainActivity.task3.bitis = false;
            MainActivity.task1.level1.bitis = false;
            MainActivity.task1.level2.bitis = false;
            MainActivity.task1.level3.bitis = false;
            MainActivity.task2.level1.bitis = false;
            MainActivity.task2.level2.bitis = false;
            MainActivity.task2.level3.bitis = false;
            MainActivity.task3.level1.bitis = false;
            MainActivity.task3.level2.bitis = false;
            MainActivity.task3.level3.bitis = false;
            MainActivity.task1.level1.dogru = 0;
            MainActivity.task1.level2.dogru = 0;
            MainActivity.task1.level3.dogru = 0;
            MainActivity.task2.level1.dogru = 0;
            MainActivity.task2.level2.dogru = 0;
            MainActivity.task2.level3.dogru = 0;
            MainActivity.task3.level1.dogru = 0;
            MainActivity.task3.level2.dogru = 0;
            MainActivity.task3.level3.dogru = 0;
            MainActivity.task1.level1.yanlis = 0;
            MainActivity.task1.level2.yanlis = 0;
            MainActivity.task1.level3.yanlis = 0;
            MainActivity.task2.level1.yanlis = 0;
            MainActivity.task2.level2.yanlis = 0;
            MainActivity.task2.level3.yanlis = 0;
            MainActivity.task3.level1.yanlis = 0;
            MainActivity.task3.level2.yanlis = 0;
            MainActivity.task3.level3.yanlis = 0;
            MainActivity.task1.level1.gecilen = 0;
            MainActivity.task1.level2.gecilen = 0;
            MainActivity.task1.level3.gecilen = 0;
            MainActivity.task2.level1.gecilen = 0;
            MainActivity.task2.level2.gecilen = 0;
            MainActivity.task2.level3.gecilen = 0;
            MainActivity.task3.level1.gecilen = 0;
            MainActivity.task3.level2.gecilen = 0;
            MainActivity.task3.level3.gecilen = 0;
            MainActivity.task1.level1.skor = 0;
            MainActivity.task1.level2.skor = 0;
            MainActivity.task1.level3.skor = 0;
            MainActivity.task2.level1.skor = 0;
            MainActivity.task2.level2.skor = 0;
            MainActivity.task2.level3.skor = 0;
            MainActivity.task3.level1.skor = 0;
            MainActivity.task3.level2.skor = 0;
            MainActivity.task3.level3.skor = 0;
            MainActivity.task1.level1.reset = false;
            MainActivity.task1.level2.reset = false;
            MainActivity.task1.level3.reset = false;
            MainActivity.task2.level1.reset = false;
            MainActivity.task2.level2.reset = false;
            MainActivity.task2.level3.reset = false;
            MainActivity.task3.level1.reset = false;
            MainActivity.task3.level2.reset = false;
            MainActivity.task3.level3.reset = false;
            MainActivity.task1.level1.pasif = true;
            MainActivity.task1.level2.pasif = true;
            MainActivity.task1.level3.pasif = true;
            MainActivity.task2.level1.pasif = true;
            MainActivity.task2.level2.pasif = true;
            MainActivity.task2.level3.pasif = true;
            MainActivity.task3.level1.pasif = true;
            MainActivity.task3.level2.pasif = true;
            MainActivity.task3.level3.pasif = true;
            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(YourStatistics.this, R.raw.resetstatistic);
                ses.start();
            }
            goldtxt.setText(Integer.toString(MainActivity.gold));
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
            totalscoretxt.setText(Integer.toString(0));
            totalknowtxt.setText(Integer.toString(0));
            totaluknowntxt.setText(Integer.toString(0));
            totalskipedtxt.setText(Integer.toString(0));

        } else if (MainActivity.gold < 20) {
            msjsonuc = getString(R.string.yourstats4);
            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(YourStatistics.this);
            alertDialogBuilder1.setTitle(R.string.yourstats3);
            alertDialogBuilder1
                    .setMessage(msjsonuc)
                    .setCancelable(true)
                    .setPositiveButton(R.string.okbutton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


            goldbuttontiklama = false;
            AlertDialog a1 = alertDialogBuilder1.create();

            // show it
            a1.show();
        }

    }

    void resetchosen() {
        if (MainActivity.gold >= 5) {
            MainActivity.gold += -5;
            goldtxt.setText(Integer.toString(MainActivity.gold));
            goldbuttontiklama = true;

            int x = index;
            goldtxt.setText(Integer.toString(MainActivity.gold));
            Soruekrani.sorusayisi = 1;
            if (x == 0) {

                MainActivity.task1.level1.dogru = 0;
                MainActivity.task1.level1.gecilen = 0;
                MainActivity.task1.level1.yanlis = 0;
                MainActivity.task1.level1.skor = 0;
                if (MainActivity.task1.level1.bitis == true) {
                    MainActivity.task1.level1.bitis = true;
                }
                sil("t1l1");
                MainActivity.task1.level1.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task1.level1.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task1.level1.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task1.level1.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task1.level1.gecilen));


            } else if (x == 1) {
                MainActivity.task1.level2.dogru = 0;
                MainActivity.task1.level2.gecilen = 0;
                MainActivity.task1.level2.yanlis = 0;
                MainActivity.task1.level2.skor = 0;
                if (MainActivity.task1.level2.bitis == true) {
                    MainActivity.task1.level2.bitis = true;
                }

                sil("t1l2");
                MainActivity.task1.level2.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task1.level2.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task1.level2.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task1.level2.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task1.level2.gecilen));

            } else if (x == 2) {
                MainActivity.task1.level3.dogru = 0;
                MainActivity.task1.level3.gecilen = 0;
                MainActivity.task1.level3.yanlis = 0;
                MainActivity.task1.level3.skor = 0;
                if (MainActivity.task1.level3.bitis == true) {
                    MainActivity.task1.level3.bitis = true;
                    MainActivity.task1.bitis = true;
                }

                sil("t1l3");
                MainActivity.task1.level3.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task1.level3.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task1.level3.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task1.level3.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task1.level3.gecilen));

            } else if (x == 3) {
                MainActivity.task2.level1.dogru = 0;
                MainActivity.task2.level1.gecilen = 0;
                MainActivity.task2.level1.yanlis = 0;
                MainActivity.task2.level1.skor = 0;
                if (MainActivity.task2.level1.bitis == true) {
                    MainActivity.task2.level1.bitis = true;
                }

                sil("t2l1");
                MainActivity.task2.level1.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task2.level1.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task2.level1.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task2.level1.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task2.level1.gecilen));
            } else if (x == 4) {
                MainActivity.task2.level2.dogru = 0;
                MainActivity.task2.level2.gecilen = 0;
                MainActivity.task2.level2.yanlis = 0;
                MainActivity.task2.level2.skor = 0;
                if (MainActivity.task2.level2.bitis == true) {
                    MainActivity.task2.level2.bitis = true;
                }

                sil("t2l2");
                MainActivity.task2.level2.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task2.level2.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task2.level2.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task2.level2.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task2.level2.gecilen));

            } else if (x == 5) {
                MainActivity.task2.level3.dogru = 0;
                MainActivity.task2.level3.gecilen = 0;
                MainActivity.task2.level3.yanlis = 0;
                MainActivity.task2.level3.skor = 0;
                if (MainActivity.task2.level3.bitis == true) {
                    MainActivity.task2.level3.bitis = true;
                    MainActivity.task2.bitis = false;
                }

                sil("t2l3");
                MainActivity.task2.level3.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task2.level3.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task2.level3.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task2.level3.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task2.level3.gecilen));
            } else if (x == 6) {
                MainActivity.task3.level1.dogru = 0;
                MainActivity.task3.level1.gecilen = 0;
                MainActivity.task3.level1.yanlis = 0;
                MainActivity.task3.level1.skor = 0;
                if (MainActivity.task3.level1.bitis == true) {
                    MainActivity.task3.level1.bitis = true;
                }
                sil("t3l1");
                MainActivity.task3.level1.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task3.level1.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task3.level1.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task3.level1.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task3.level1.gecilen));

            } else if (x == 7) {
                MainActivity.task3.level2.dogru = 0;
                MainActivity.task3.level2.gecilen = 0;
                MainActivity.task3.level2.yanlis = 0;
                MainActivity.task3.level2.skor = 0;
                if (MainActivity.task3.level2.bitis == true) {
                    MainActivity.task3.level2.bitis = true;
                }

                sil("t3l2");
                MainActivity.task3.level2.reset = true;
                totalscoretxt.setText(Integer.toString(MainActivity.task3.level2.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task3.level2.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task3.level2.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task3.level2.gecilen));
            } else if (x == 8) {
                MainActivity.task3.level3.dogru = 0;
                MainActivity.task3.level3.gecilen = 0;
                MainActivity.task3.level3.yanlis = 0;
                MainActivity.task3.level3.skor = 0;
                if (MainActivity.task3.level3.bitis == true) {
                    MainActivity.task3.level3.bitis = true;
                    MainActivity.task3.bitis = false;
                }

                MainActivity.task3.level3.reset = true;
                sil("t3l3");
                totalscoretxt.setText(Integer.toString(MainActivity.task3.level3.skor));
                totalknowtxt.setText(Integer.toString(MainActivity.task3.level3.dogru));
                totaluknowntxt.setText(Integer.toString(MainActivity.task3.level3.yanlis));
                totalskipedtxt.setText(Integer.toString(MainActivity.task3.level3.gecilen));

            }
            if (MainActivity.sesdurumu == true) {
                MediaPlayer ses = MediaPlayer.create(YourStatistics.this, R.raw.resetstatistic);
                ses.start();
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


        } else if (MainActivity.gold < 5) {

            msjsonuc = getString(R.string.yourstats4);
            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(YourStatistics.this);
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

    void Goldkullan(String tür) {
        final String tur = tür;
        String mesaj = "";
        if (tür == "resetchosen") {
            mesaj = getString(R.string.yourstats1);
            if (MainActivity.questiondurumu == false) {
                resetchosen();
            } else {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.yourstats3));
                alertDialogBuilder
                        .setMessage(mesaj)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (tur.equals("resetchosen")) {
                                    resetchosen();
                                } else if (tur.equals("resetall")) {
                                    resetall();
                                }

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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
        } else if (tür == "resetall") {
            mesaj = getString(R.string.yourstats2);
            if (MainActivity.questiondurumu == false) {
                resetall();
            } else {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.yourstats3));
                alertDialogBuilder
                        .setMessage(mesaj)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (tur.equals("resetchosen")) {
                                    resetchosen();
                                } else if (tur.equals("resetall")) {
                                    resetall();
                                }

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }

    public View altview() throws IllegalAccessException, InstantiationException, SQLException, ClassNotFoundException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("ResetChosen");
        adad.add("ResetAll");
        adad.add("BacktoMenu");


        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(marg, 0, 0, 0);
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

            for (int j = 0; j < 3; j++) {
                int yukarı = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels / 3.5));
                int sol = (int) ((getResources().getDisplayMetrics().widthPixels / 2.8));
                double oran = (float) 57 / (float) 155;
                int yukseklik = (int) (oran * genislik);
                //155x57 backto menu button
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins((int) (bosluk * 1.1), yukarı, 0, 0);
                Button button = new Button(this);
                switch (k) {
                    case 1:
                        button.setId(R.id.ResetChosenbtn);
                        button.setTag("ResetChosen");
                        button.setBackgroundResource(resimara(getString(R.string.resetchosenbutton)));
                        button.setVisibility(View.INVISIBLE);

                        break;
                    case 2:
                        button.setId(R.id.ResetAllbtn);
                        button.setTag("ResetAll");
                        button.setBackgroundResource(resimara(getString(R.string.resetyourstatisticsbutton)));
                        button.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        button.setTag("BacktoMenu");
                        button.setBackgroundResource(resimara(getString(R.string.gobackbutton)));
                        break;
                }

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        switch ((v.getTag().toString())) {
                            case "ResetChosen":
                                //seçileni resetle kodları
                                Goldkullan("resetchosen");

                                break;
                            case "ResetAll":
                                //hepsini resetle kodları

                                Goldkullan("resetall");

                                break;
                            case "BacktoMenu":
                                Intent i = new Intent();
                                i.setClass(YourStatistics.this, singleplayer.class);
                                startActivity(i);
                                break;
                        }
                    }
                });

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
        Intent i = new Intent();
        i.setClass(this, singleplayer.class);
        startActivity(i);
    }
}


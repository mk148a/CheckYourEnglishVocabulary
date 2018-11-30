package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class singleplayer extends AppCompatActivity {
    private LinearLayout tl;
    private LinearLayout al;
    private LinearLayout ll;
    private LinearLayout ust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);
        AdView adView = (AdView) this.findViewById(R.id.singleplayer_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        tl = (LinearLayout) findViewById(R.id.tasklinear);
        al = (LinearLayout) findViewById(R.id.altlinear);
        ll = (LinearLayout) findViewById(R.id.ortalinear);
        ust = (LinearLayout) findViewById(R.id.singleplayer_ustlinear);
        TextView goldtxt = (TextView) findViewById(R.id.singleplayer_goldtxt);
        EditText usertxt = (EditText) findViewById(R.id.singleplayer_usernametxt);
        try {

            ll.addView(levelview());
            tl.addView(taskview());
            al.addView(altview());
            ust.addView(ustview());
            LinearLayout anasayfa = (LinearLayout) findViewById(R.id.singleplayer);

            if (MainActivity.giriscalisti == true) {

                // yl.addView(yarimaayarview());
                goldtxt.setEnabled(true);
                goldtxt.setVisibility(View.VISIBLE);
                goldtxt.setText(Integer.toString(MainActivity.gold));
                usertxt.setEnabled(true);
                usertxt.setVisibility(View.VISIBLE);
                usertxt.setText(MainActivity.kullaniciadi);
                anasayfa.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.singleplayeruyebackground))));

            } else {

                goldtxt.setEnabled(false);
                goldtxt.setVisibility(View.INVISIBLE);
                usertxt.setEnabled(false);
                usertxt.setVisibility(View.INVISIBLE);
                anasayfa.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.singleplayerbackground))));
            }


        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        yenile();
    }

    public View levelview() throws IllegalAccessException {
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
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            for (int j = 0; j < 3; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels * 0.70 - 3 * bosluk) / 3);
                double oran = (float) 52 / (float) 81;

                //Toast.makeText(getApplicationContext(),Double.toString(oran)+","+Integer.toString(genislik), Toast.LENGTH_SHORT).show();
                int yukseklik = (int) (oran * genislik);


                // Toast.makeText(getApplicationContext(),Integer.toString(bosluk), Toast.LENGTH_SHORT).show();
                //getResources().getDisplayMetrics().scaledDensity*


                //81x52 level buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins(0, bosluk, bosluk, bosluk * 6);
                Button button = new Button(this);

                button.setTag("level" + k);
                switch (k) {
                    case 1:
                        button.setId(R.id.l1btn);
                        break;
                    case 2:
                        button.setId(R.id.l2btn);
                        break;
                    case 3:
                        button.setId(R.id.l3btn);
                        break;
                    case 4:
                        button.setId(R.id.l4btn);
                        break;
                    case 5:
                        button.setId(R.id.l5btn);
                        break;
                    case 6:
                        button.setId(R.id.l6btn);
                        break;
                    case 7:
                        button.setId(R.id.l7btn);
                        break;
                    case 8:
                        button.setId(R.id.l8btn);
                        break;
                    case 9:
                        button.setId(R.id.l9btn);
                        break;
                }
                if (j == 0) {
                    if (i == 0) {
                        button.setBackgroundResource(resimara(getString(R.string.level1active)));

                    } else if (i == 1) {
                        button.setBackgroundResource(resimara(getString(R.string.level1passive)));
                    } else if (i == 2) {
                        button.setBackgroundResource(resimara(getString(R.string.level1passive)));
                    }
                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.level2passive)));
                } else if (j == 2) {
                    button.setBackgroundResource(resimara(getString(R.string.level3passive)));
                }

                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //kanal adı ve linkini tagden alıyor
                        String gelentag = v.getTag().toString();

                        Intent i = new Intent();

                        i.setClass(singleplayer.this, Soruekrani.class);

                        switch ((v.getTag().toString())) {
                            case "level1":
                                i.putExtra("data", "t1l1");
                                Soruekrani.level = 1;
                                Soruekrani.task = 1;
                                Soruekrani.url = MainActivity.task1.level1.id;
                                Soruekrani.cikanlevel = MainActivity.task1.level1;
                                break;
                            case "level2":
                                i.putExtra("data", "t1l2");
                                Soruekrani.level = 2;
                                Soruekrani.task = 1;
                                Soruekrani.url = MainActivity.task1.level2.id;
                                Soruekrani.cikanlevel = MainActivity.task1.level2;
                                break;
                            case "level3":
                                i.putExtra("data", "t1l3");
                                Soruekrani.level = 3;
                                Soruekrani.task = 1;
                                Soruekrani.url = MainActivity.task1.level3.id;
                                Soruekrani.cikanlevel = MainActivity.task1.level3;
                                break;

                            case "level4":
                                i.putExtra("data", "t2l1");
                                Soruekrani.level = 1;
                                Soruekrani.task = 2;
                                Soruekrani.url = MainActivity.task2.level1.id;
                                Soruekrani.cikanlevel = MainActivity.task2.level1;
                                break;
                            case "level5":
                                i.putExtra("data", "t2l2");
                                Soruekrani.level = 2;
                                Soruekrani.task = 2;
                                Soruekrani.url = MainActivity.task2.level2.id;
                                Soruekrani.cikanlevel = MainActivity.task2.level2;
                                break;
                            case "level6":
                                i.putExtra("data", "t2l3");
                                Soruekrani.level = 3;
                                Soruekrani.task = 2;
                                Soruekrani.url = MainActivity.task2.level3.id;
                                Soruekrani.cikanlevel = MainActivity.task2.level3;
                                break;
                            case "level7":
                                i.putExtra("data", "t3l1");
                                Soruekrani.level = 1;
                                Soruekrani.task = 3;
                                Soruekrani.url = MainActivity.task3.level1.id;
                                Soruekrani.cikanlevel = MainActivity.task3.level1;
                                break;
                            case "level8":
                                i.putExtra("data", "t3l2");
                                Soruekrani.level = 2;
                                Soruekrani.task = 3;
                                Soruekrani.url = MainActivity.task3.level2.id;
                                Soruekrani.cikanlevel = MainActivity.task3.level2;
                                break;
                            case "level9":
                                i.putExtra("data", "t3l3");
                                Soruekrani.level = 3;
                                Soruekrani.task = 3;
                                Soruekrani.url = MainActivity.task3.level3.id;
                                Soruekrani.cikanlevel = MainActivity.task3.level3;
                                break;

                        }


                        startActivity(i);


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

    public View taskview() throws IllegalAccessException {
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pr.setMargins(dphesapla(15), marg, 0, 0);
        linearLayout.setLayoutParams(pr);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(false);
        TableLayout.LayoutParams tb = (new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        tableLayout.setLayoutParams(tb);
        tableLayout.setWeightSum(3);
        int k = 1;

        String[] value = null;
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.NO_GRAVITY);
            int marg1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
            TableLayout.LayoutParams prr = new TableLayout.LayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            tableRow.setLayoutParams(prr);

            for (int j = 0; j < 1; j++) {
                int bosluk = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                int genislik = (int) ((getResources().getDisplayMetrics().widthPixels * 0.20));
                double oran = (float) 36 / (float) 69;
                int yukseklik = (int) (oran * genislik);

                //69x36 level buttonları
                TableRow.LayoutParams tb1 = new TableRow.LayoutParams(genislik, yukseklik);
                tb1.setMargins(0, bosluk, bosluk, bosluk * 8);
                Button button = new Button(this);

                button.setTag("Task" + k);
                switch (k) {
                    case 1:
                        button.setId(R.id.t1btn);
                        break;
                    case 2:
                        button.setId(R.id.t2btn);
                        break;
                    case 3:
                        button.setId(R.id.t3btn);
                        break;
                }
                if (i == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.task1active)));
                } else if (i == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.task2passive)));
                } else if (i == 2) {
                    button.setBackgroundResource(resimara(getString(R.string.task3passive)));
                }

                tableRow.addView(button, tb1);
                k = k + 1;
            }

            tableLayout.addView(tableRow);
        }


        linearLayout.addView(tableLayout);

        return linearLayout;
    }

    public View ustview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("YourStatistics");
        adad.add("RankList");
        adad.add("Rules");
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
                            case "YourStatistics":
                                i.setClass(singleplayer.this, YourStatistics.class);
                                startActivity(i);
                                break;
                            case "RankList":
                                if (MainActivity.giriscalisti == true) {
                                    i.setClass(singleplayer.this, RankList.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.mainyazi1, Toast.LENGTH_LONG).show();
                                }
                                break;
                            case "Rules":
                                i.setClass(singleplayer.this, Rules.class);
                                startActivity(i);
                                break;
                        }

                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.yourstatisticbutton)));
                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(R.string.ranklistbutton)));
                } else if (j == 2) {
                    button.setBackgroundResource(resimara(getString(R.string.rulesbutton)));
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
                        i.setClass(singleplayer.this, MainActivity.class);
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
    public void onBackPressed() {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }

    int dphesapla(int dp) {
        int marg = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return marg;
    }

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }

    void yenile() {
        Button lvl1btn = (Button) findViewById(R.id.l1btn);
        Button lvl2btn = (Button) findViewById(R.id.l2btn);
        Button lvl3btn = (Button) findViewById(R.id.l3btn);
        Button lvl4btn = (Button) findViewById(R.id.l4btn);
        Button lvl5btn = (Button) findViewById(R.id.l5btn);
        Button lvl6btn = (Button) findViewById(R.id.l6btn);
        Button lvl7btn = (Button) findViewById(R.id.l7btn);
        Button lvl8btn = (Button) findViewById(R.id.l8btn);
        Button lvl9btn = (Button) findViewById(R.id.l9btn);
        Button task1btn = (Button) findViewById(R.id.t1btn);
        Button task2btn = (Button) findViewById(R.id.t2btn);
        Button task3btn = (Button) findViewById(R.id.t3btn);
        lvl1btn.setEnabled(false);
        lvl2btn.setEnabled(false);
        lvl3btn.setEnabled(false);
        lvl4btn.setEnabled(false);
        lvl5btn.setEnabled(false);
        lvl6btn.setEnabled(false);
        lvl7btn.setEnabled(false);
        lvl8btn.setEnabled(false);
        lvl9btn.setEnabled(false);

        if (MainActivity.task1.bitis == false) {
            task1btn.setEnabled(true);
            task1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task1active))));
            if (MainActivity.task1.level1.bitis == false) {
                lvl1btn.setEnabled(true);
                lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1active))));
                MainActivity.task1.level1.pasif = false;
            }
            if (MainActivity.giriscalisti == true) {
                if (MainActivity.task1.level1.bitis == true && MainActivity.task1.level2.bitis == false) {
                    lvl2btn.setEnabled(true);
                    lvl1btn.setEnabled(false);
                    lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2active))));
                    lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                    MainActivity.task1.level1.pasif = false;
                    MainActivity.task1.level2.pasif = false;
                } else if (MainActivity.task1.level2.bitis == true && MainActivity.task1.level3.bitis == false) {
                    lvl3btn.setEnabled(true);
                    lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3active))));
                    lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                    lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                    MainActivity.task1.level1.pasif = false;
                    MainActivity.task1.level2.pasif = false;
                    MainActivity.task1.level3.pasif = false;
                }
            } else {
                if (MainActivity.task1.level1.bitis == true && MainActivity.task1.level2.bitis == false) {
                    lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                    MainActivity.task1.level1.pasif = false;
                }
            }
        } else if (MainActivity.task1.bitis == true && MainActivity.task2.bitis == false) {
            task1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task1complated))));
            task1btn.setEnabled(false);
            task2btn.setEnabled(true);
            task2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task2active))));
            if (MainActivity.task2.level1.bitis == false) {
                lvl4btn.setEnabled(true);
                lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1active))));
                lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                MainActivity.task1.level1.pasif = false;
                MainActivity.task1.level2.pasif = false;
                MainActivity.task1.level3.pasif = false;
                MainActivity.task2.level1.pasif = false;
            } else if (MainActivity.task2.level1.bitis == true && MainActivity.task2.level2.bitis == false) {
                lvl5btn.setEnabled(true);
                lvl5btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2active))));
                lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                MainActivity.task1.level1.pasif = false;
                MainActivity.task1.level2.pasif = false;
                MainActivity.task1.level3.pasif = false;
                MainActivity.task2.level1.pasif = false;
                MainActivity.task2.level2.pasif = false;
            } else if (MainActivity.task2.level2.bitis == true && MainActivity.task2.level3.bitis == false) {
                lvl6btn.setEnabled(true);
                lvl6btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3active))));
                lvl5btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                MainActivity.task1.level1.pasif = false;
                MainActivity.task1.level2.pasif = false;
                MainActivity.task1.level3.pasif = false;
                MainActivity.task2.level1.pasif = false;
                MainActivity.task2.level2.pasif = false;
                MainActivity.task2.level3.pasif = false;
            }

        } else if (MainActivity.task2.bitis == true && MainActivity.task3.bitis == false) {
            task1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task1complated))));
            task2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task2complated))));
            task3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task3active))));
            task3btn.setEnabled(true);
            if (MainActivity.task3.level1.bitis == false) {
                lvl7btn.setEnabled(true);
                lvl7btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1active))));
                lvl6btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl5btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                MainActivity.task1.level1.pasif = false;
                MainActivity.task1.level2.pasif = false;
                MainActivity.task1.level3.pasif = false;
                MainActivity.task2.level1.pasif = false;
                MainActivity.task2.level2.pasif = false;
                MainActivity.task2.level3.pasif = false;
                MainActivity.task3.level1.pasif = false;

            } else if (MainActivity.task3.level1.bitis == true && MainActivity.task3.level2.bitis == false) {
                lvl8btn.setEnabled(true);
                lvl8btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2active))));
                lvl7btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                lvl6btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl5btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                MainActivity.task1.level1.pasif = false;
                MainActivity.task1.level2.pasif = false;
                MainActivity.task1.level3.pasif = false;
                MainActivity.task2.level1.pasif = false;
                MainActivity.task2.level2.pasif = false;
                MainActivity.task2.level3.pasif = false;
                MainActivity.task3.level1.pasif = false;
                MainActivity.task3.level2.pasif = false;
            } else if (MainActivity.task3.level2.bitis == true && MainActivity.task3.level3.bitis == false) {
                lvl9btn.setEnabled(true);
                lvl9btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3active))));
                lvl8btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl7btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                lvl6btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl5btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
                lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
                lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
                MainActivity.task1.level1.pasif = false;
                MainActivity.task1.level2.pasif = false;
                MainActivity.task1.level3.pasif = false;
                MainActivity.task2.level1.pasif = false;
                MainActivity.task2.level2.pasif = false;
                MainActivity.task2.level3.pasif = false;
                MainActivity.task3.level1.pasif = false;
                MainActivity.task3.level2.pasif = false;
                MainActivity.task3.level3.pasif = false;
            }

        } else if (MainActivity.task3.level3.bitis == true) {

            lvl9btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
            lvl8btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
            lvl7btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
            lvl6btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
            lvl5btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
            lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));
            lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3complated))));
            lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2complated))));
            lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1complated))));

            task1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task1complated))));
            task2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task2complated))));
            task3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.task3complated))));
            task3btn.setEnabled(false);
        }

        //#endregion


        if (MainActivity.task1.level1.reset == true) {
            lvl1btn.setEnabled(true);
            lvl1btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1active))));

        }
        if (MainActivity.task1.level2.reset == true) {
            lvl2btn.setEnabled(true);

            lvl2btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2active))));

        }
        if (MainActivity.task1.level3.reset == true) {
            lvl3btn.setEnabled(true);
            lvl3btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3active))));

        }


        if (MainActivity.task2.level1.reset == true) {
            lvl4btn.setEnabled(true);
            lvl4btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1active))));

        } else if (MainActivity.task2.level2.reset == true) {
            lvl5btn.setEnabled(true);
            lvl5btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2active))));

        } else if (MainActivity.task2.level3.reset == true) {
            lvl6btn.setEnabled(true);
            lvl6btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3active))));

        }

        if (MainActivity.task3.level1.reset == true) {
            lvl7btn.setEnabled(true);
            lvl7btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level1active))));

        } else if (MainActivity.task3.level2.reset == true) {
            lvl8btn.setEnabled(true);
            lvl8btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level2active))));

        } else if (MainActivity.task3.level3.reset == true) {
            lvl9btn.setEnabled(true);
            lvl9btn.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.level3active))));

        }
    }
}

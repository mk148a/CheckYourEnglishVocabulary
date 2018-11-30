package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class Multiplayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.englishapps.com.checkyourenglishvocabulary.R.layout.activity_multiplayer);
        LinearLayout orta = (LinearLayout) findViewById(com.englishapps.com.checkyourenglishvocabulary.R.id.multiplayer_orta);
        LinearLayout alt = (LinearLayout) findViewById(com.englishapps.com.checkyourenglishvocabulary.R.id.multiplayer_altlinear);
        try {
            orta.addView(multiplayer_ortaview());
            alt.addView(multiplayer_altview());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public View multiplayer_ortaview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("start");
        adad.add("search");
        adad.add("stats");
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
                double oran = (float) 50 / (float) 133;
                int yukseklik = (int) (oran * genislik);
                //133x50 level buttonları
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
                            case "start":
                                //start komutları

                                break;
                            case "search":
                                //search
                                break;
                            case "stats":
                                // i.setClass(Multiplayer.this, Rules.class);
                                //startActivity(i);
                                break;
                        }

                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(com.englishapps.com.checkyourenglishvocabulary.R.string.startbutton)));
                    button.setId(com.englishapps.com.checkyourenglishvocabulary.R.id.startbutton);
                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(com.englishapps.com.checkyourenglishvocabulary.R.string.searchbutton)));
                } else if (j == 2) {
                    button.setBackgroundResource(resimara(getString(com.englishapps.com.checkyourenglishvocabulary.R.string.yourstatisticbutton)));
                }
                tableRow.addView(button, tb1);
                k = k + 1;

            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }

    public View multiplayer_altview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("rank");
        adad.add("rules");
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

                                break;
                            case "rules":
                                //search
                                break;
                            case "back":

                                i.setClass(Multiplayer.this, MainActivity.class);
                                startActivity(i);
                                break;
                        }

                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(com.englishapps.com.checkyourenglishvocabulary.R.string.ranklistbutton)));

                } else if (j == 1) {
                    button.setBackgroundResource(resimara(getString(com.englishapps.com.checkyourenglishvocabulary.R.string.rulesbutton)));
                } else if (j == 2) {
                    button.setBackgroundResource(resimara(getString(com.englishapps.com.checkyourenglishvocabulary.R.string.yourstatisticbacktomenu)));
                }
                tableRow.addView(button, tb1);
                k = k + 1;

            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }
}

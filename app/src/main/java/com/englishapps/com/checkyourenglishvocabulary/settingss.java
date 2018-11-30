package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;

public class settingss extends AppCompatActivity {
    public RadioButton setenglsih;
    public RadioButton setturkish;
    public RadioButton questions_on;
    public RadioButton questions_off;
    public RadioButton sound_on;
    public RadioButton sound_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingss2);
        LinearLayout lsave = (LinearLayout) findViewById(R.id.settings_save_linear);
        LinearLayout lback = (LinearLayout) findViewById(R.id.settings_back_linear);
        LinearLayout anasayfa = (LinearLayout) findViewById(R.id.activity_settings);
        anasayfa.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.settingsbackground))));


        setenglsih = (RadioButton) findViewById(R.id.settings_english);
        setturkish = (RadioButton) findViewById(R.id.settings_turkish);
        questions_on = (RadioButton) findViewById(R.id.questions_on);
        questions_off = (RadioButton) findViewById(R.id.questions_off);
        sound_on = (RadioButton) findViewById(R.id.Sound_on);
        sound_off = (RadioButton) findViewById(R.id.Sound_off);
        try {
            lsave.addView(saveview());
            lback.addView(altview());
            setenglsih.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setturkish.setChecked(false);
                }
            });
            setturkish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setenglsih.setChecked(false);

                }
            });

            questions_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questions_off.setChecked(false);
                }
            });
            questions_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questions_on.setChecked(false);
                }
            });
            sound_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sound_off.setChecked(false);

                }
            });
            sound_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sound_on.setChecked(false);
                }
            });
            loadSettings();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {


        SharedPreferences settings = getSharedPreferences("status", 0);
        MainActivity.turkcedil = settings.getBoolean("setturkish", false);
        MainActivity.ingilizcedil = settings.getBoolean("setenglsih", false);
        MainActivity.questiondurumu = settings.getBoolean("questions_on", false);
        MainActivity.sesdurumu = settings.getBoolean("sound_on", false);


        setturkish.setChecked(settings.getBoolean("setturkish", false));
        setenglsih.setChecked(settings.getBoolean("setenglsih", false));
        questions_on.setChecked(settings.getBoolean("questions_on", false));
        questions_off.setChecked(settings.getBoolean("questions_off", false));
        sound_on.setChecked(settings.getBoolean("sound_on", false));
        sound_off.setChecked(settings.getBoolean("sound_off", false));

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
                        i.setClass(settingss.this, MainActivity.class);
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

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }

    public void refresh(View view) {          //refresh is onClick name given to the button
        onRestart();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(settingss.this, settingss.class);  //your class
        startActivity(i);
        finish();

    }

    public View saveview() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("Save");


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
                        //settings kodları buraya
                        SharedPreferences settings = getSharedPreferences("status", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("setturkish", setturkish.isChecked());
                        editor.putBoolean("setenglsih", setenglsih.isChecked());
                        editor.putBoolean("questions_on", questions_on.isChecked());
                        editor.putBoolean("questions_off", questions_off.isChecked());
                        editor.putBoolean("sound_on", sound_on.isChecked());
                        editor.putBoolean("sound_off", sound_off.isChecked());
                        editor.commit();
                        Intent i = new Intent(settingss.this, MainActivity.class);
                        startActivity(i);
                        Toast.makeText(getBaseContext(), R.string.the_change_Save, Toast.LENGTH_LONG).show();
                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.savebutton)));
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

    }
}

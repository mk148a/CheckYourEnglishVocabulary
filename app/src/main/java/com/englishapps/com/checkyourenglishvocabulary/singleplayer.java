package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

        TextView goldtxt = (TextView) findViewById(R.id.singleplayer_goldtxt);
        EditText usertxt = (EditText) findViewById(R.id.singleplayer_usernametxt);


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

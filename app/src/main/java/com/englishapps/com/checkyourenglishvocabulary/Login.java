package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Login extends AppCompatActivity {
    private LinearLayout loginaltl;
    private LinearLayout loginortal;
    private LinearLayout loginalt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout lw1 = (LinearLayout) findViewById(R.id.Loginlinear);
        lw1.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.loginbackground))));

        loginaltl = (LinearLayout) findViewById(R.id.loginaltlinear);
        loginortal = (LinearLayout) findViewById(R.id.loginortalinear);
        loginalt = (LinearLayout) findViewById(R.id.loginaltlinear);


        try {
            loginaltl.addView(altview());
            loginortal.addView(ortaview());
            loginalt.addView(altview());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String cpuId() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }

    String login(String username, String password) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String sonuc = "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String connectionUrl = "jdbc:jtds:sqlserver://213.239.192.147:1433/testyourcovabulary;user=sa;password=148150";

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(connectionUrl);

        String SQL = "select * from kullanici where kullaniciAd='" + username + "'";
        stmt = con.createStatement();
        rs = stmt.executeQuery(SQL);
        if (rs.next()) {

            if (rs.getString("sifre").equals(password)) {
                if (rs.getString("cpuid").equals(cpuId())) {
                    sonuc = getString(R.string.log1);
                } else {
                    sonuc = getString(R.string.log2);
                }
            } else {
                sonuc = getString(R.string.log3);
            }
            con.close();
        } else {
            sonuc = (getString(R.string.log4));
        }

        return sonuc;
    }

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
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
        for (int i = 0; i < 3; i++) {
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
                if (i == 2) {
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
                        edt.setId(R.id.loginusernameinputtxt);
                        edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        edt.setHint(R.string.username);


                        break;
                    case 2:
                        edt.setId(R.id.loginpasswordinputtxt);
                        edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edt.setHint(R.string.password);

                        break;
                    case 3:
                        btn.setId(R.id.loginbtn);
                        btn.setBackgroundResource(resimara(getString(R.string.loginbutton)));
                        break;
                }

                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        String username = ((EditText) findViewById(R.id.loginusernameinputtxt)).getText().toString();
                        String password = ((EditText) findViewById(R.id.loginpasswordinputtxt)).getText().toString();


                        if (username != "" && password != "") {
                            //register yap
                            MainActivity.temizle();
                            try {
                                String sonuc = login(username, password);
                                Toast.makeText(getApplicationContext(), sonuc, Toast.LENGTH_LONG).show();

                                if (sonuc == getString(R.string.log1)) {
                                    MainActivity.logout = false;
                                    MainActivity.giriscalisti = false;
                                    Intent i = new Intent();
                                    i.setClass(Login.this, MainActivity.class);
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
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), R.string.log5, Toast.LENGTH_LONG).show();
                        }

                    }

                });

                if (k == 3) {
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
                        i.setClass(Login.this, MainActivity.class);
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
}

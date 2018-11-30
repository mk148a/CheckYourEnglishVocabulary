package com.englishapps.com.checkyourenglishvocabulary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.englishapps.com.checkyourenglishvocabulary.util.IabHelper;
import com.englishapps.com.checkyourenglishvocabulary.util.IabResult;
import com.englishapps.com.checkyourenglishvocabulary.util.Inventory;
import com.englishapps.com.checkyourenglishvocabulary.util.Purchase;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class BuyCoin extends AppCompatActivity {
    final String ITEM_SKU = "";
    public RadioButton ellicoin;
    public RadioButton yuzcoin;
    public RadioButton ikiyuzcoin;
    public RadioButton ikiyuzellicoin;
    public RadioButton besyuzcoin;
    public RadioButton bincoin;
    public Integer secilen;
    public EditText usernametxt;
    public TextView goldtxt;
    public String apiKey = "";
    // Provides purchase notification while this app is running
    IabHelper mHelper;
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {

                        // Satın alma başarılı ise burada satın alma sonrası işlemleri yapabiliriz.
                        Toast.makeText(getBaseContext(), "başarılı", Toast.LENGTH_LONG).show();
                        MainActivity.gold = MainActivity.gold + secilen;
                        TextView x = (TextView) findViewById(R.id.buycoingoldtxt);
                        x.setText(Integer.toString(MainActivity.gold));
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Purchase failed", Toast.LENGTH_LONG).show();
                    }


                }
            };
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                            mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                Toast.makeText(getBaseContext(), "Purchase failed", Toast.LENGTH_LONG).show();
                Log.d("", "Error purchasing: " + result);
                return;
            } else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
                MainActivity.gold = MainActivity.gold + secilen;
                TextView x = (TextView) findViewById(R.id.buycoingoldtxt);
                x.setText(Integer.toString(MainActivity.gold));
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_coin);
        apiKey=getString(R.string.apikey);
        LinearLayout lw=(LinearLayout)findViewById(R.id.buycoinaltlinear);
        LinearLayout lw1=(LinearLayout)findViewById(R.id.buycoinlinear);
        lw1.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.buycoinbackground))));
        ellicoin=(RadioButton)findViewById(R.id.ellicointxt);
        yuzcoin=(RadioButton)findViewById(R.id.yuzcointxt);
        ikiyuzcoin=(RadioButton)findViewById(R.id.ikiyuzcointxt);
        ikiyuzellicoin=(RadioButton)findViewById(R.id.ikiyuzellicointxt);
        besyuzcoin=(RadioButton)findViewById(R.id.besyuzcointxt);
        bincoin=(RadioButton)findViewById(R.id.bincointxt);


        usernametxt=(EditText)findViewById(R.id.buycoinusernametxt);
        usernametxt.setText(MainActivity.kullaniciadi);
        goldtxt=(TextView) findViewById(R.id.buycoingoldtxt);
        goldtxt.setText(Integer.toString(MainActivity.gold));
        ellicoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isaretle(1);
            }
        });
        yuzcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isaretle(2);
            }
        });
        ikiyuzcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isaretle(3);
            }
        });
        ikiyuzellicoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isaretle(4);
            }
        });
        besyuzcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isaretle(5);
            }
        });
        bincoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isaretle(6);
            }
        });
        try {
            lw.addView(altview());
            LinearLayout bn= (LinearLayout)findViewById(R.id.buynowlayout);
            bn.addView(buynow());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        mHelper = new IabHelper(this, apiKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d("", "In-app Billing yüklenirken hata");

                } else {
                    Log.d("", "In-app Billing yüklendi.");

                    mHelper.enableDebugLogging(true, "");
                }
            }
        });

    }

    private void isaretle(Integer sik)
    {

        switch (sik)
        {
            case 1:
                yuzcoin.setChecked(false);
                ikiyuzcoin.setChecked(false);
                ikiyuzellicoin.setChecked(false);
                besyuzcoin.setChecked(false);
                bincoin.setChecked(false);
                bincoin.setChecked(false);
                secilen=50;
                break;
            case 2:
                ellicoin.setChecked(false);
                secilen=100;
                ikiyuzcoin.setChecked(false);
                ikiyuzellicoin.setChecked(false);
                besyuzcoin.setChecked(false);
                bincoin.setChecked(false);
                break;
            case 3:
                secilen=200;
                ellicoin.setChecked(false);
                yuzcoin.setChecked(false);

                ikiyuzellicoin.setChecked(false);
                besyuzcoin.setChecked(false);
                bincoin.setChecked(false);
                break;
            case 4:
                secilen=250;
                ellicoin.setChecked(false);
                yuzcoin.setChecked(false);
                ikiyuzcoin.setChecked(false);

                besyuzcoin.setChecked(false);
                bincoin.setChecked(false);
                break;
            case 5:
                secilen=500;
                ellicoin.setChecked(false);
                yuzcoin.setChecked(false);
                ikiyuzcoin.setChecked(false);
                ikiyuzellicoin.setChecked(false);

                bincoin.setChecked(false);
                break;
            case 6:
                secilen=1000;
                ellicoin.setChecked(false);
                yuzcoin.setChecked(false);
                ikiyuzcoin.setChecked(false);
                ikiyuzellicoin.setChecked(false);
                besyuzcoin.setChecked(false);
                break;

        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("", "Destroying helper.");
        if (mHelper != null) {
            try {
                mHelper.dispose();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
        mHelper = null;
    }

    public void consumeItem() {
        try {
            mHelper.queryInventoryAsync(mReceivedInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Toast.makeText(getBaseContext(),"You have bought the " + sku + ". Excellent choice,",Toast.LENGTH_LONG).show();
                    MainActivity.gold=MainActivity.gold+secilen;
                    TextView x=(TextView)findViewById(R.id.buycoingoldtxt);
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }
    }
    public int resimara(String isim)
    {
        Context context=this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
    }
    @Override
    public void onBackPressed()
    {

    }


    public String cpuId() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }

    void veritabaninayaz() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        SendData yenidata = new SendData();
        yenidata.cpuid = cpuId();
        yenidata.gold = MainActivity.gold;
        Gson gson = new Gson();
        String data = gson.toJson(yenidata);
        try {
            String sonuc = new gonder().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/setskor", data).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


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
                        i.setClass(BuyCoin.this, MainActivity.class);
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

    public View buynow() throws IllegalAccessException {
        ArrayList<String> adad = new ArrayList<String>();
        adad.add("Buynow");
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

                        Toast.makeText(getBaseContext(),"We have been working on it",Toast.LENGTH_LONG).show();
/*
                        String secilencoin=Integer.toString(secilen)+"coin";
                        try {//secilencoin
                            mHelper.launchPurchaseFlow(BuyCoin.this, "android.test.purchased", 10001,
                                    mPurchaseFinishedListener, "");
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            e.printStackTrace();
                        }*/



                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.buynowbutton)));
                }

                tableRow.addView(button, tb1);
                k = k + 1;

            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }
}

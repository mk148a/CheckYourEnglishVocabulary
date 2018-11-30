package com.englishapps.com.checkyourenglishvocabulary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;


public class RankList extends AppCompatActivity {
    public ArrayList<Level> levellar = new ArrayList<Level>();
    private UserDeailtListadapter selectedAdapter;
    private LinearLayout ranklistaltl;
    private Context mContext = RankList.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        LinearLayout lw1 = (LinearLayout) findViewById(R.id.Ranklistlinear);
        lw1.setBackgroundDrawable(getResources().getDrawable(resimara(getString(R.string.top1000background))));
        selectedAdapter = new UserDeailtListadapter(mContext, levellar);
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.maindialog);
        dialog.setTitle(R.string.rank2);
        ListView userdtaillist = (ListView) dialog.findViewById(R.id.uuserDeailtList);
        Button cancel = (Button) dialog.findViewById(R.id.Button01);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        userdtaillist.setAdapter(selectedAdapter);
        ranklistaltl = (LinearLayout) findViewById(R.id.ranklistaltlinear);
        try {
            ranklistaltl.addView(altview());
            ListView ranklst = (ListView) findViewById(R.id.Ranklistesi);
            ArrayList<Users> data = ranklarigetir();
            if ( data.size()>99   ) {
                data = (ArrayList<Users>) data.subList(0, 100);
            }
            TextView yourranktxt = (TextView) findViewById(R.id.YourRanktxt);

            Integer gets = getscorerank();


            yourranktxt.setText(getString(R.string.rank1) + Integer.toString(gets));
            final ArrayList<Users> datason = new ArrayList<>();
            for (Users item:data
                    ) {
                String color = "#8B937373";
                if (item.Sıra == gets) {
                    color = "#590095ff";
                }
                Users yenikisi = new Users();
                yenikisi.Name = item.Name;
                yenikisi.Sıra = item.Sıra;
                yenikisi.Score = item.Score;
                yenikisi.Color = color;
                datason.add(yenikisi);


            }

            ranklistadapter adapter = new ranklistadapter(this, datason);


            ranklst.setAdapter(adapter);


            ranklst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Users user = datason.get(position);
                    String kullaniciadi = user.Name;
                    //get userrank
                    ArrayList<Task> tasklar = getuserrank(kullaniciadi);
                    Task task1 = tasklar.get(0);
                    Task task2 = tasklar.get(1);
                    Task task3 = tasklar.get(2);

                    levellar.clear();
                    levellar.add(task1.level1);
                    levellar.add(task1.level2);
                    levellar.add(task1.level3);
                    levellar.add(task2.level1);
                    levellar.add(task2.level2);
                    levellar.add(task2.level3);
                    levellar.add(task3.level1);
                    levellar.add(task3.level2);
                    levellar.add(task3.level3);
                    TextView usrname = (TextView) dialog.findViewById(R.id.detail_usernametxt);
                    usrname.setText(kullaniciadi);

                    dialog.show();


                }
            });

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int resimara(String isim) {
        Context context = this;
        int id = context.getResources().getIdentifier(isim, "drawable", context.getPackageName());
        return id;
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
                        i.setClass(RankList.this, singleplayer.class);
                        startActivity(i);
                    }
                });
                if (j == 0) {
                    button.setBackgroundResource(resimara(getString(R.string.gobackbutton)));
                }

                tableRow.addView(button, tb1);
                k = k + 1;

            }
            tableLayout.addView(tableRow);


        }
        linearLayout.addView(tableLayout);
        return linearLayout;

    }

    public ArrayList<Users> ranklarigetir() {

        ArrayList<Users> gelen = new ArrayList<Users>();
        try {
            String data = new indir().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/getrank").get();

            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<String> daa = gson.fromJson(data, type);

            for (String s : daa) {
                Users yeni = new Users();
                yeni.Name = s.split(",")[0];
                yeni.Sıra = Integer.parseInt(s.split(",")[1]);
                yeni.Score = Integer.parseInt(s.split(",")[2]);
                gelen.add(yeni);


            }
        } catch (Exception e) {
          //  Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }


        return gelen;
    }

    public Integer getscorerank() {

        String kullaniciAd = MainActivity.kullaniciadi;
        Integer data = 0;
        try {
            data = Integer.parseInt(new indir().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/getscorerank/" + kullaniciAd).get().replace("\"", ""));
        } catch (Exception e) {
         //   Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        return data;


    }

    public ArrayList<Task> getuserrank(String kullaniciAd) {
        ArrayList<Task> gelen = new ArrayList<>();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        Gson gson = new Gson();
        String data = null;
        try {
            ArrayList<String> gelenlist = new ArrayList<>();
            data = new indir().execute("http://hdtvapp.tk/checkyourenglishvocabulary/v1/kullaniciService1.svc/getuserrank/" + kullaniciAd).get();

            gelenlist = gson.fromJson(data, type);
            gson = new Gson();
            type = new TypeToken<Task>() {
            }.getType();
            gelen.add((Task) gson.fromJson(gelenlist.get(0), type));
            gelen.add((Task) gson.fromJson(gelenlist.get(1), type));
            gelen.add((Task) gson.fromJson(gelenlist.get(2), type));

        } catch (Exception e) {
          //     Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        return gelen;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.setClass(this, singleplayer.class);
        startActivity(i);
    }

    class ranklistadapter extends BaseAdapter {

        Context context;
        ArrayList<Users> data;
        private LayoutInflater inflater = null;

        public ranklistadapter(Context context, ArrayList<Users> data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.ranklist_row, null);
            TextView text = (TextView) vi.findViewById(R.id.kullaniciadtxt);
            text.setText(data.get(position).Name);
            TextView text1 = (TextView) vi.findViewById(R.id.skortxt);
            text1.setText(Integer.toString(data.get(position).Score));
            TextView text2 = (TextView) vi.findViewById(R.id.siratxt);
            text2.setText(Integer.toString(data.get(position).Sıra));
            LinearLayout lcolor = (LinearLayout) vi.findViewById(R.id.ranklistrow);
            lcolor.setBackgroundColor(Color.parseColor(data.get(position).Color));
            return vi;
        }
    }

    class UserDeailtListadapter extends BaseAdapter {

        Context context;
        ArrayList<Level> data;
        private LayoutInflater inflater = null;

        public UserDeailtListadapter(Context context, ArrayList<Level> data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.userdetail_row, null);
            TextView dogru = (TextView) vi.findViewById(R.id.userdetail_knowtxt);
            dogru.setText(Integer.toString(data.get(position).dogru));
            TextView yanlist = (TextView) vi.findViewById(R.id.userdetail_uknowntxt);
            yanlist.setText(Integer.toString(data.get(position).yanlis));
            TextView atlanan = (TextView) vi.findViewById(R.id.userdetail_skipedtxt);
            atlanan.setText(Integer.toString(data.get(position).gecilen));
            TextView skor = (TextView) vi.findViewById(R.id.userdetail_scoretxt);
            skor.setText(Integer.toString(data.get(position).skor));
            final TextView tasklevel = (TextView) vi.findViewById(R.id.textView9);
            switch (position) {
                case 0:
                    tasklevel.setText(R.string.t1l1);
                    break;
                case 1:
                    tasklevel.setText(R.string.t1l2);
                    break;
                case 2:
                    tasklevel.setText(R.string.t1l3);
                    break;
                case 3:
                    tasklevel.setText(R.string.t2l1);
                    break;
                case 4:
                    tasklevel.setText(R.string.t2l2);
                    break;
                case 5:
                    tasklevel.setText(R.string.t2l3);
                    break;
                case 6:
                    tasklevel.setText(R.string.t3l1);
                    break;
                case 7:
                    tasklevel.setText(R.string.t3l2);
                    break;
                case 8:
                    tasklevel.setText(R.string.t3l3);
                    break;
            }


            return vi;

        }
    }








}


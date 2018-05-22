package com.example.cuong.blackjack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuong.blackjack.Activity.II_Sign;
import com.example.cuong.blackjack.Activity.IV_PlayListSong;
import com.example.cuong.blackjack.Activity.V_Song;
import com.example.cuong.blackjack.DataModel.Constants;
import com.example.cuong.blackjack.DataModel.PlayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.cuong.blackjack.Activity.IV_PlayListSong.playlistbool;
import static com.example.cuong.blackjack.DataModel.Network.ip;

public class MainActivity extends AppCompatActivity {

    private EditText edFind01;
    private ImageView imageFind01, imageIntro01;
    private ListView lv01;
    public static String keyword;
    private Button btnAdd;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    Intent intent01=new Intent(MainActivity.this, II_Sign.class);
                    startActivity(intent01);
                    return true;
              /*  case R.id.navigation_home:
                    Intent intent011=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent011);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


        edFind01=findViewById(R.id.edFind01);
        imageFind01=findViewById(R.id.imageFind01);
        imageIntro01=findViewById(R.id.imageIntro01);
        btnAdd=findViewById(R.id.btnAll01);

        new getArtist().execute(ip+"?listartist=a");

        imageFind01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edFind01.length()>0){
                   keyword=edFind01.getText().toString().replace(" ","-");
                   playlistbool=false;
                   Intent intent011=new Intent(MainActivity.this, IV_PlayListSong.class);
                   startActivity(intent011);
                }
            }
        });

        imageIntro01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword="2NE1";
                playlistbool=false;
                Intent intent012=new Intent(MainActivity.this, IV_PlayListSong.class);
                startActivity(intent012);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword="";
                playlistbool=false;
                Intent intent013=new Intent(MainActivity.this, IV_PlayListSong.class);
                startActivity(intent013);
            }
        });

    }

    private class loadImage extends AsyncTask<String, Void, Bitmap>{
        Bitmap bitmap;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                InputStream inputStream=url.openConnection().getInputStream();

                bitmap= BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageIntro01.setImageBitmap(bitmap);
        }
    }


    private class getArtist extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder=new StringBuilder();
            try {
                URL url=new URL(strings[0]);

                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();


                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                InputStream inputStream=httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                String line="";

                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line + "\n");
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array= new JSONArray(s);
                JSONObject object1=array.getJSONObject(1);
                String url01=object1.getString("urlimage");
                new loadImage().execute(url01);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}

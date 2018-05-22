package com.example.cuong.blackjack.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cuong.blackjack.Adapter.AdapterSong;
import com.example.cuong.blackjack.DataModel.PlayList;
import com.example.cuong.blackjack.DataModel.PlayListAdapter;
import com.example.cuong.blackjack.DataModel.Song;
import com.example.cuong.blackjack.MainActivity;
import com.example.cuong.blackjack.R;

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
import java.util.ArrayList;
import java.util.List;

import static com.example.cuong.blackjack.Activity.III_PlayList.idplaylist;
import static com.example.cuong.blackjack.DataModel.Network.ip;
import static com.example.cuong.blackjack.MainActivity.keyword;

public class IV_PlayListSong extends AppCompatActivity {
    private ListView lv04;
    public static List<Song> songs;
    private ArrayAdapter<Song> songAdapter;
    public static String songname="";
    public static String singername="";
    public static boolean playlistbool=false;
    public static int id;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener04
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    Intent intent04=new Intent(IV_PlayListSong.this, II_Sign.class);
                    startActivity(intent04);
                    return true;
                case R.id.navigation_home:
                    Intent intent041=new Intent(IV_PlayListSong.this, MainActivity.class);
                    startActivity(intent041);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iv__play_list_song);

        BottomNavigationView navigation04 = (BottomNavigationView) findViewById(R.id.navigation04);
        navigation04.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener04);
        lv04=findViewById(R.id.lv04);
        songs=new ArrayList<>();

        if(playlistbool==true){
            String url04=ip+"?idplaylist="+idplaylist;
            new getPlaylistSong().execute(url04);
        }

        if(playlistbool==false){
            String url041=ip+"?keyword="+keyword;
            new getPlaylistSong().execute(url041);
        }

        lv04.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id=i;
                songname=songs.get(i).getName().replace(" ","-");
                singername=songs.get(i).getSinger().replace(" ","-");
                Intent intent042=new Intent(IV_PlayListSong.this,V_Song.class);
                startActivity(intent042);
            }
        });
    }


    private class getPlaylistSong extends AsyncTask<String, Void, String> {
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
            if(s.length()>5) {
                try {
                    JSONArray array=new JSONArray(s) ;
                    for(int i=0;i<=array.length();i++){
                        JSONObject activity=array.getJSONObject(i);
                        String songname=activity.getString("songname");
                        String singername=activity.getString("singername");
                        songs.add(new Song(songname,singername));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            songAdapter=new AdapterSong(IV_PlayListSong.this,R.layout.adapter_song,songs);
            lv04.setAdapter(songAdapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}

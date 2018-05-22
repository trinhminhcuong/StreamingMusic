package com.example.cuong.blackjack.Activity;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuong.blackjack.DataModel.Constants;
import com.example.cuong.blackjack.DataModel.PlayList;
import com.example.cuong.blackjack.DataModel.PlayListAdapter;
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

import static com.example.cuong.blackjack.Activity.IV_PlayListSong.playlistbool;
import static com.example.cuong.blackjack.DataModel.Account.username;
import static com.example.cuong.blackjack.DataModel.Network.ip;

public class III_PlayList extends AppCompatActivity {


    private ListView lv03;
    private List<PlayList> playLists;
    private ArrayAdapter<PlayList> playListAdapter;
    private TextView tvNewPlayList03;
    public static String idplaylist;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener03
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    Intent intent03=new Intent(III_PlayList.this, II_Sign.class);
                    startActivity(intent03);
                    return true;
                case R.id.navigation_home:
                    Intent intent031=new Intent(III_PlayList.this, MainActivity.class);
                    startActivity(intent031);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iii__play_list);


        lv03=findViewById(R.id.lv03);
        tvNewPlayList03=findViewById(R.id.tvNewPlayList03);
        BottomNavigationView navigation03 = (BottomNavigationView) findViewById(R.id.navigation03);
        navigation03.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener03);

        playLists=new ArrayList<>();
        String url03=ip+"?owner="+username;
        new getPlaylist().execute(url03);

        tvNewPlayList03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog03=new Dialog(III_PlayList.this);
                dialog03.setContentView(R.layout.dialog_add_playlist);
                final EditText edDPlayListName=dialog03.findViewById(R.id.edDPlayListName);
                Button btnAdd=dialog03.findViewById(R.id.btnAdd);

                dialog03.show();

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edDPlayListName.length()==0){
                            Toast.makeText(III_PlayList.this,"Please enter Name",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String url031=ip+"?playlistname="+edDPlayListName.getText().toString().replace(" ","-")+"&owner="+username;
                            new AsyncAddPlayList().execute(url031);
                        }
                    }
                });

            }
        });

        lv03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idplaylist=playLists.get(i).getId();
                playlistbool=true;
                Intent intent032=new Intent(III_PlayList.this,IV_PlayListSong.class);
                startActivity(intent032);
            }
        });

    }

    private class getPlaylist extends AsyncTask<String, Void, String> {
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
                        String id=activity.getString("idnew");
                        String playlistname=activity.getString("namenew");
                        playLists.add(new PlayList(id,playlistname));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            playListAdapter=new PlayListAdapter(III_PlayList.this,R.layout.adapter_playlist,playLists);
            lv03.setAdapter(playListAdapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    private class AsyncAddPlayList extends AsyncTask<String, Void, String> {
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


                httpURLConnection.setRequestMethod("POST");
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
            String a=s.charAt(0)+"";

            if(a.equals("t")==true)
            {
                Toast.makeText(III_PlayList.this,"Successful", Toast.LENGTH_SHORT).show();
                Intent intent03=new Intent(III_PlayList.this,III_PlayList.class);
                startActivity(intent03);
            }
            else {
                Toast.makeText(III_PlayList.this,"Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}

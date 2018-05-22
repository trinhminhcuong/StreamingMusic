package com.example.cuong.blackjack.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cuong.blackjack.Activity.III_PlayList;
import com.example.cuong.blackjack.DataModel.PlayList;
import com.example.cuong.blackjack.DataModel.PlayListAdapter;
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

import static com.example.cuong.blackjack.DataModel.Account.username;
import static com.example.cuong.blackjack.DataModel.Network.ip;

/**
 * Created by Cuong on 5/17/2018.
 */

public class VI_DList extends Dialog {
    private Context context;
    private List<PlayList> list;
    private ListView lvD06;
    private ArrayAdapter<PlayList> playListArrayAdapter;
    private String a;
    private boolean run=false;


    public VI_DList(@NonNull Context context, final String songname, final String singer) {
        super(context);
        this.context=context;

        setContentView(R.layout.dialog_list);

        lvD06=this.findViewById(R.id.lvD06);
        list=new ArrayList<>();
        String urlD06=ip+"?owner="+username;
        new getDPlaylist().execute(urlD06);

        lvD06.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String id=list.get(i).getId();
                    String urlD062=ip+"?idplaylist="+id+"&songnamepl="+
                            songname
                            +"&singerpl="+singer;
                    new addSong().execute(urlD062);
            }
        });


    }

    private class getDPlaylist extends AsyncTask<String, Void, String> {
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
                        list.add(new PlayList(id,playlistname));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            playListArrayAdapter=new PlayListAdapter(context,R.layout.adapter_playlist,list);
            lvD06.setAdapter(playListArrayAdapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    private class addSong extends AsyncTask<String, Void, String> {
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
            a=s.charAt(0)+"";
            if(a.equals("t")==true){
                Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"This song's in playlist",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}

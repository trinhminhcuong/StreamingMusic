package com.example.cuong.blackjack.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuong.blackjack.DataModel.PlayList;
import com.example.cuong.blackjack.DataModel.PlayListAdapter;
import com.example.cuong.blackjack.DataModel.Song;
import com.example.cuong.blackjack.Dialog.IV_DInfoSong;
import com.example.cuong.blackjack.Dialog.VI_DList;
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

import static com.example.cuong.blackjack.Activity.IV_PlayListSong.id;
import static com.example.cuong.blackjack.Activity.IV_PlayListSong.singername;
import static com.example.cuong.blackjack.Activity.IV_PlayListSong.songname;
import static com.example.cuong.blackjack.Activity.IV_PlayListSong.songs;
import static com.example.cuong.blackjack.DataModel.Network.ip;

public class V_Song extends AppCompatActivity {

    private ImageView imageSingerLogo06;
    private TextView tvSongName06;
    private TextView tvLyrics06;
    private TextView tvStart06;
    private TextView tvEnd06;
    private SeekBar sb06;
    private ImageView imageRewind06;
    private ImageView imageNext06;
    private ImageView imagePlay06;
    private Song song;
    private ImageView image051,image052,image053,image054,image055;
    private Dialog dialog05;
    private boolean mediaPlaying;
    private MediaPlayer mediaPlayer;
    private TextView tvAdd06;
    private Dialog dialog06;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v__song);

        imageSingerLogo06=findViewById(R.id.imageSingerLogo05);
        tvSongName06=findViewById(R.id.tvSongName06);
        tvLyrics06=findViewById(R.id.tvLyrics06);
        tvStart06=findViewById(R.id.tvStart06);
        tvEnd06=findViewById(R.id.tvEnd06);
        sb06=findViewById(R.id.sb06);
        imageNext06=findViewById(R.id.imageNext06);
        imagePlay06=findViewById(R.id.imagePlay06);
        imageRewind06=findViewById(R.id.imageRewind06);
        image051=findViewById(R.id.image051);
        image052=findViewById(R.id.image052);
        image053=findViewById(R.id.image053);
        image054=findViewById(R.id.image054);
        image055=findViewById(R.id.image055);
        tvAdd06=findViewById(R.id.tvAdd06);

       creatMedia();

       tvAdd06.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dialog06=new VI_DList(V_Song.this,songname,singername);
               dialog06.show();
           }
       });

        imageSingerLogo06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog05=new IV_DInfoSong(V_Song.this,song);
                dialog05.show();
            }
        });

        imageNext06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();

                if(songs.size()==1){
                    creatMedia();
                }
                else if(id==songs.size()-1){
                    id=0;
                   creatMedia();
                }
                else {
                    id=id+1;
                    creatMedia();
                }

            }
        });

        imageRewind06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(songs.size()==1){
                   creatMedia();
                }
                else   if(id==0){
                    id=songs.size()-1;
                    creatMedia();
                }
                else {
                    id=id-1;
                    creatMedia();
                }
            }
        });

        imagePlay06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlaying==true){
                    mediaPlayer.pause();
                    mediaPlaying=false;
                    imagePlay06.setImageResource(R.drawable.playicon);
                }
                else {
                   mediaRunning();
                }
            }
        });

        sb06.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sb06.getProgress());
            }
        });

    }


    public void mediaRunning(){
        mediaPlayer.start();
        imagePlay06.setImageResource(R.drawable.pauseicon);
        mediaPlaying=true;
        updateTimeSong();
    }
    public void updateTimeSong() {
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvStart06.setText(exchange(mediaPlayer.getCurrentPosition()));
                //update progress
                sb06.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,1000);
            }
        },100);
    }
    public String exchange(int militime){
        int time=militime/1000;
        int second=time%60;
        int minute=time/60;
        String result=minute+":"+second;
        return result;
    }
    public void release(){
        mediaPlayer.release();
    }
    public void playMedia(String url){
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            sb06.setMax(mediaPlayer.getDuration());
            tvEnd06.setText(exchange(mediaPlayer.getDuration()));
            mediaRunning();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void setImage(String url){
        new loadImage().execute(url);
    }

    public void creatMedia(){
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        songname=songs.get(id).getName().replace(" ","-");
        singername=songs.get(id).getSinger().replace(" ","-");

        String url05=ip+"?urlsong="+songname+"&urlname="+singername;
        String url052=ip+"?rankname="+songname+"&ranksinger="+singername;
        String url053=ip+"?artistname="+singername;
        String url054=ip+"?nameview="+songname+"&singview="+singername;

        new getSong().execute(url05);
        new getRate().execute(url052);
        new getArtist().execute(url053);
        new insertView().execute(url054);

    }

    private class getSong extends AsyncTask<String, Void, String> {
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
                    for(int i=0;i<1;i++){
                        JSONObject activity=array.getJSONObject(i);
                        String songname=activity.getString("songname");
                        String songsinger=activity.getString("songsinger");
                        String songtype=activity.getString("songtype");
                        String songlistens=activity.getString("songlistens");
                        String songurl=activity.getString("songurl");
                        String lyrics=activity.getString("songlyrics");
                        song=new Song(songname,songsinger,songsinger,songtype,songlistens,songurl,lyrics);
                        tvSongName06.setText(songname);
                        tvLyrics06.setText(lyrics);
                        playMedia(songurl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    private class getRate extends AsyncTask<String, Void, String> {
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
                int total=0;

                try {
                    JSONArray array=new JSONArray(s) ;
                    for(int i=0;i<array.length();i++){
                        JSONObject activity=array.getJSONObject(i);
                        String newrate=activity.getString("newrate");
                        int rate=Integer.parseInt(newrate);
                        total=total+rate;
                    }
                    int avarage=total/array.length();
                    if(avarage==1){
                        image051.setImageResource(R.drawable.starpng);
                    }
                    if (avarage==2){
                        image052.setImageResource(R.drawable.starpng);
                        image051.setImageResource(R.drawable.starpng);
                    }
                    if(avarage==3){
                        image052.setImageResource(R.drawable.starpng);
                        image051.setImageResource(R.drawable.starpng);
                        image053.setImageResource(R.drawable.starpng);
                    }
                    if(avarage==4){
                        image054.setImageResource(R.drawable.starpng);
                        image052.setImageResource(R.drawable.starpng);
                        image051.setImageResource(R.drawable.starpng);
                        image053.setImageResource(R.drawable.starpng);
                    }
                    if(avarage==5){
                        image054.setImageResource(R.drawable.starpng);
                        image052.setImageResource(R.drawable.starpng);
                        image051.setImageResource(R.drawable.starpng);
                        image053.setImageResource(R.drawable.starpng);
                        image055.setImageResource(R.drawable.starpng);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
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
            imageSingerLogo06.setImageBitmap(bitmap);
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
            if(s.length()>5) {

                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(s);
                    String url=jsonObject.getString("urlimage");
                    setImage(url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private class insertView extends AsyncTask<String, Void, String> {
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


                httpURLConnection.setRequestMethod("PUT");
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

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}

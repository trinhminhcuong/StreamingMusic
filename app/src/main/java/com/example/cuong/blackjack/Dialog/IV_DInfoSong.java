package com.example.cuong.blackjack.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuong.blackjack.Activity.III_PlayList;
import com.example.cuong.blackjack.DataModel.Song;
import com.example.cuong.blackjack.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.cuong.blackjack.Activity.IV_PlayListSong.singername;
import static com.example.cuong.blackjack.Activity.IV_PlayListSong.songname;
import static com.example.cuong.blackjack.DataModel.Network.ip;

/**
 * Created by Cuong on 5/17/2018.
 */

public class IV_DInfoSong extends Dialog {
    private Context context;
    private TextView tvInfoD04;
    private TextView tvViewsD04;
    private ImageView imageD041,imageD042,imageD043,imageD044,imageD045;
    private Button btnRateD04;
    private Song song;
    private int rate=0;

    public IV_DInfoSong(Context context, Song song){
        super(context);
        this.context=context;
        this.song=song;

        setContentView(R.layout.dialog_info_song);

        tvInfoD04=this.findViewById(R.id.tvInfoD04);
        tvViewsD04=this.findViewById(R.id.tvViewsD04);
        imageD041=this.findViewById(R.id.imageD041);
        imageD042=this.findViewById(R.id.imageD042);
        imageD043=this.findViewById(R.id.imageD043);
        imageD044=this.findViewById(R.id.imageD044);
        imageD045=this.findViewById(R.id.imageD045);
        btnRateD04=this.findViewById(R.id.btnRateD04);

        final String namesong=song.getName().replace(" ","-");
        final String singersong=song.getSinger().replace(" ","-");

        tvInfoD04.setText(song.getSinger()+"-"+song.getName());
        tvViewsD04.setText(song.getListens()+" views");

        imageD041.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageD041.setImageResource(R.drawable.starpng);
                imageD042.setImageResource(R.drawable.freystar);
                imageD043.setImageResource(R.drawable.freystar);
                imageD044.setImageResource(R.drawable.freystar);
                imageD045.setImageResource(R.drawable.freystar);
                rate=1;
            }
        });

        imageD042.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageD041.setImageResource(R.drawable.starpng);
                imageD042.setImageResource(R.drawable.starpng);

                imageD043.setImageResource(R.drawable.freystar);
                imageD044.setImageResource(R.drawable.freystar);
                imageD045.setImageResource(R.drawable.freystar);
                rate=2;
            }
        });

        imageD043.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageD041.setImageResource(R.drawable.starpng);
                imageD042.setImageResource(R.drawable.starpng);
                imageD043.setImageResource(R.drawable.starpng);
                imageD044.setImageResource(R.drawable.freystar);
                imageD045.setImageResource(R.drawable.freystar);
                rate=3;
            }
        });

        imageD044.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageD041.setImageResource(R.drawable.starpng);
                imageD042.setImageResource(R.drawable.starpng);
                imageD043.setImageResource(R.drawable.starpng);
                imageD044.setImageResource(R.drawable.starpng);
                imageD045.setImageResource(R.drawable.freystar);
                rate=4;
            }
        });

        imageD045.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageD041.setImageResource(R.drawable.starpng);
                imageD042.setImageResource(R.drawable.starpng);
                imageD043.setImageResource(R.drawable.starpng);
                imageD044.setImageResource(R.drawable.starpng);
                imageD045.setImageResource(R.drawable.starpng);
                rate=5;
            }
        });

        btnRateD04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String urlD04=ip+"?namerank="
                        +songname +"&singerrank=" + singername + "&rate=" + rate;
                new AsyncRate().execute(urlD04);
            }
        });

       /* if(averageRate==1){
            imageD041.setImageResource(R.drawable.starpng);
        }
        if(averageRate==2){
            imageD041.setImageResource(R.drawable.starpng);
            imageD042.setImageResource(R.drawable.starpng);
        }
        if(averageRate==3){
            imageD041.setImageResource(R.drawable.starpng);
            imageD042.setImageResource(R.drawable.starpng);
            imageD043.setImageResource(R.drawable.starpng);
        }
        if(averageRate==4){
            imageD041.setImageResource(R.drawable.starpng);
            imageD042.setImageResource(R.drawable.starpng);
            imageD043.setImageResource(R.drawable.starpng);
            imageD044.setImageResource(R.drawable.starpng);
        }
        if(averageRate==5){
            imageD041.setImageResource(R.drawable.starpng);
            imageD042.setImageResource(R.drawable.starpng);
            imageD043.setImageResource(R.drawable.starpng);
            imageD044.setImageResource(R.drawable.starpng);
            imageD045.setImageResource(R.drawable.starpng);
        }*/

    }

    private class AsyncRate extends AsyncTask<String, Void, String> {
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
            if (a.equals("t")==true){
                Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }



}

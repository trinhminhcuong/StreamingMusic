package com.example.cuong.blackjack.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuong.blackjack.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.cuong.blackjack.DataModel.Account.password;
import static com.example.cuong.blackjack.DataModel.Account.username;
import static com.example.cuong.blackjack.DataModel.Network.ip;

/**
 * Created by Cuong on 5/17/2018.
 */

public class V_DChangePassword extends Dialog{

    private Context context;
    private EditText edOldPasswordD05,edNewPassWordD05,edNewPassWordD051;
    private Button btnOkD05;

    public V_DChangePassword(@NonNull final Context context) {
        super(context);
        this.context=context;
        setContentView(R.layout.dialog_change_password);

        edOldPasswordD05=this.findViewById(R.id.edOldassworđD05);
        edNewPassWordD05=this.findViewById(R.id.edNewPassworđD05);
        edNewPassWordD051=this.findViewById(R.id.edNewPassworđD051);
        btnOkD05=this.findViewById(R.id.btnOkD05);

        btnOkD05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edOldPasswordD05.length()==0||edNewPassWordD05.length()==0||edNewPassWordD051.length()==0){
                    Toast.makeText(context,"Please Enter All",Toast.LENGTH_SHORT).show();
                }
                else if(edOldPasswordD05.getText().toString().equals(password)==false){
                    Toast.makeText(context,"Your Old Password is not correct",Toast.LENGTH_SHORT).show();
                }
                else if(edNewPassWordD05.getText().toString().equals(edNewPassWordD051.getText().toString())==false){
                    Toast.makeText(context,"Please confirm new password again",Toast.LENGTH_SHORT).show();
                }
                else {
                    String urlD05=ip+"?username="+username+"&newpassword="+edNewPassWordD05.getText().toString().trim();
                    new AsyncChangePassword().execute(urlD05);
                }
            }
        });

    }

    private class AsyncChangePassword extends AsyncTask<String, Void, String> {
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
            String a=s.charAt(0)+"";

            if(a.equals("t")==true)
            {
                password=edNewPassWordD05.getText().toString();
                edOldPasswordD05.setText("");
                edNewPassWordD05.setText("");
                edNewPassWordD051.setText("");
                Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}

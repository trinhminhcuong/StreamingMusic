package com.example.cuong.blackjack.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuong.blackjack.Activity.II_Sign;
import com.example.cuong.blackjack.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.cuong.blackjack.DataModel.Network.ip;
import static com.example.cuong.blackjack.DataModel.Account.password;
import static com.example.cuong.blackjack.DataModel.Account.username;
import static com.example.cuong.blackjack.Activity.II_Sign.tvUserName02;


/**
 * Created by Cuong on 5/15/2018.
 */

public class II_DSignIn extends Dialog {
    private Context context;
    private EditText edUserNameD02;
    private EditText edPasswordD02;
    private Button btnSignInD02;
    private String result="";
    private boolean running=false;



    public II_DSignIn(@NonNull final Context context) {
        super(context);
        this.context=context;
        setContentView(R.layout.dialog_sign_in);

        edUserNameD02=this.findViewById(R.id.edUserNameD02);
        edPasswordD02=this.findViewById(R.id.edPasswordD02);
        btnSignInD02=this.findViewById(R.id.btnSignInD02);

        btnSignInD02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edUserNameD02.length() == 0 || edPasswordD02.length() == 0) {
                    Toast.makeText(context, "Xin mời nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    String url = ip + "?username=" + edUserNameD02.getText().toString().trim()+"&password="
                            +edPasswordD02.getText().toString().trim();
                    new AsyncSignIn().execute(url);
                    while (running==true){
                    };
                    cancel();
                }
            }
        });
    }

    private class AsyncSignIn extends AsyncTask<String, Void, String> {
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
            result=s.charAt(0)+"";
            if(result.equals("t")==true)
            {
                username=edUserNameD02.getText().toString();
                password=edPasswordD02.getText().toString();
                tvUserName02.setText(username);
                running=false;
            }
            else {
                Toast.makeText(context,"Tài khoản không đúng", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    public void Intent(){
        Intent intent2=new Intent(context,II_Sign.class);

    }

}

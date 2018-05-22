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

import static com.example.cuong.blackjack.DataModel.Network.ip;

/**
 * Created by Cuong on 5/15/2018.
 */

public class III_DSignUp extends Dialog {
    private Context context;
    private EditText edUsernameD03;
    private EditText edPasswordD03;
    private EditText edPasswordD031;
    private Button btnSignUpD03;
    private String a="";
    private boolean running=false;

    private final String usernameD03="?username=";
    private final String passwordD03="&password=";

    public III_DSignUp(@NonNull final Context context) {
        super(context);
        this.context=context;
        setContentView(R.layout.dialog_sign_up);

        edUsernameD03=this.findViewById(R.id.edUsernameD03);
        edPasswordD03=this.findViewById(R.id.edPasswordD03);
        edPasswordD031=this.findViewById(R.id.edPasswordD031);
        btnSignUpD03=this.findViewById(R.id.btnSignUpD03);

        btnSignUpD03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edUsernameD03.length()==0||edPasswordD03.length()==0||edPasswordD031.length()==0){
                    Toast.makeText(context,"Xin mời nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                else if(edPasswordD03.getText().toString().equals(edPasswordD031.getText().toString())==false){
                    Toast.makeText(context,"Mật khẩu chưa khớp",Toast.LENGTH_SHORT).show();
                }
                else {
                    String url=ip+"?username="+edUsernameD03.getText().toString().trim()
                            +"&password="+edPasswordD03.getText().toString().trim();
                    new AsyncSignUp().execute(url);
                return;
                }
            }
        });
    }

    private class AsyncSignUp extends AsyncTask<String, Void, String> {
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

            if(a.equals("t")==true)
            {
                edUsernameD03.setText("");
                edPasswordD03.setText("");
                edPasswordD031.setText("");
                running=true;
                Toast.makeText(context,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else {
                running=true;
                Toast.makeText(context,"Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}

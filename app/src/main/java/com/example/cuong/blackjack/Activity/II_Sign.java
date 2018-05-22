package com.example.cuong.blackjack.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuong.blackjack.DataModel.Constants;
import com.example.cuong.blackjack.Dialog.III_DSignUp;
import com.example.cuong.blackjack.Dialog.II_DSignIn;
import com.example.cuong.blackjack.Dialog.I_Sign;
import com.example.cuong.blackjack.Dialog.V_DChangePassword;
import com.example.cuong.blackjack.MainActivity;
import com.example.cuong.blackjack.R;

import java.util.MissingFormatArgumentException;

import static com.example.cuong.blackjack.DataModel.Account.password;
import static com.example.cuong.blackjack.DataModel.Account.username;

public class II_Sign extends AppCompatActivity {

    private Dialog dialogSign;
    private Dialog dialogChange;
    public static TextView tvUserName02;
    private TextView tvPlayList02;
    private TextView tvModPassword02;
    private TextView tvLogOut02;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener02
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    Intent intent02=new Intent(Constants.CURRENT_CONTEXT, II_Sign.class);
                    startActivity(intent02);
                    return true;
                case R.id.navigation_home:
                    Intent intent021=new Intent(Constants.CURRENT_CONTEXT, MainActivity.class);
                    startActivity(intent021);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ii__sign);
        tvUserName02=findViewById(R.id.tvUserName02);
        tvLogOut02=findViewById(R.id.tvLogOut02);
        tvModPassword02=findViewById(R.id.tvModPassword02);
        tvPlayList02=findViewById(R.id.tvPlayList02);
        dialogChange=new V_DChangePassword(this);

        Constants.CURRENT_CONTEXT=this;

        BottomNavigationView navigation02 = (BottomNavigationView) findViewById(R.id.navigation02);

        navigation02.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener02);



        if (username.equals("")==true){
            tvLogOut02.setText("     Sign In");
            dialogSign= new I_Sign(this);
            dialogSign.show();
        }
        else {
            tvUserName02.setText(username);
        }
        tvLogOut02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvLogOut02.getText().toString().equals("    Log out")==true){
                    dialogSign=new I_Sign(II_Sign.this);
                    dialogSign.show();
                }
                else {
                    username="";
                    password="";
                    tvUserName02.setText("User name");
                    Intent intent022=new Intent(II_Sign.this,II_Sign.class);
                    startActivity(intent022);
                }
            }
        });

        tvPlayList02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.equals("")==true){
                    Toast.makeText(II_Sign.this,"Please Sign In!",Toast.LENGTH_SHORT).show();
                }
                else {
                   Intent intent023=new Intent(II_Sign.this,III_PlayList.class);
                   startActivity(intent023);
                }
            }
        });

        tvModPassword02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.equals("")==false){
                    dialogChange.show();
                }
            }
        });

    }
}

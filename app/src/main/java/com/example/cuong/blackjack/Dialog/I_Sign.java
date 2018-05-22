package com.example.cuong.blackjack.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.cuong.blackjack.R;

/**
 * Created by Cuong on 5/15/2018.
 */

public class I_Sign extends Dialog{

    private Button btnSignInD01;
    private Button btnSignUpD01;
    private Context context;


    public I_Sign(@NonNull final Context context) {
        super(context);
        this.context=context;
        setContentView(R.layout.dialog_sign);

        btnSignInD01=this.findViewById(R.id.btnSignInD01);
        btnSignUpD01=this.findViewById(R.id.btnSignUpD01);

        btnSignInD01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                Dialog dialogSignIn=new II_DSignIn(context);
                dialogSignIn.show();
            }
        });

        btnSignUpD01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                Dialog dialogSignUp=new III_DSignUp(context);
                dialogSignUp.show();
            }
        });
    }
}

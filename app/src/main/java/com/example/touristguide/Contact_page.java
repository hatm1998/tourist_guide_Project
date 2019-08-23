package com.example.touristguide;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.touristguide.authentication.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Contact_page extends AppCompatActivity {

    private Dialog dialog;
    private FirebaseAuth mAuth;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);
        context=this;

    }


    // Action -> Sign Up Button
    public void btn_signUp(View view) {
        //finish();

        Intent intent = new Intent(Contact_page.this, signup.class);
        startActivity(intent);
        Animatoo.animateFade(context);
    }

    // Action -> Login Button
    public void btn_login(View view) {
        dialog = new Dialog(this, R.style.PauseDialog);
        dialog.setContentView(R.layout.activity_login);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView txt_email = dialog.findViewById(R.id.txt_email);
        final TextView txt_pass = dialog.findViewById(R.id.txt_pass);
        Button btn_login = dialog.findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check__For_Login(txt_email, txt_pass);
            }
        });
        ImageView close = dialog.findViewById(R.id.btn_close_loginpage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void check__For_Login(TextView txt_email, TextView txt_pass) {
        String Email = txt_email.getText().toString().trim();
        String password = txt_pass.getText().toString().trim();

        if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(password)) {
            txt_email.setError(getResources().getString(R.string.Field_empty));
            txt_pass.setError(getResources().getString(R.string.Field_empty));
        } else if (TextUtils.isEmpty(Email)) {
            txt_email.setError(getResources().getString(R.string.Field_empty));

        } else if (TextUtils.isEmpty(password)) {
            txt_pass.setError(getResources().getString(R.string.Field_empty));
        } else {

            // Success Login
            mAuth.signInWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                Intent intent = new Intent(getApplicationContext(), Navigation_Drawer.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

        }

    }

}

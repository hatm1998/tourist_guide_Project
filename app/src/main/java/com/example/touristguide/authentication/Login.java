package com.example.touristguide.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.touristguide.Navigation_Drawer;

import com.example.touristguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

  private   Button btn_login ;
  private   FirebaseAuth mAuth;
   private TextInputEditText txt_email, txt_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.btn_login);

        txt_email = findViewById(R.id.txt_email);
        txt_pass = findViewById(R.id.txt_pass);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check__For_Login();
            }
        });
    }
    private void check__For_Login()
    {
        String Email=txt_email.getText().toString().trim();
        String password=txt_pass.getText().toString().trim();

        if(TextUtils.isEmpty(Email) && TextUtils.isEmpty(password)) {
            txt_email.setError(getResources().getString(R.string.Field_empty));
            txt_pass.setError(getResources().getString(R.string.Field_empty));
        }
        else if(TextUtils.isEmpty(Email))
        {
            txt_email.setError(getResources().getString(R.string.Field_empty));

        }else if(TextUtils.isEmpty(password))
        {
            txt_pass.setError(getResources().getString(R.string.Field_empty));
        }else {

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

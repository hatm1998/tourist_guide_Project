package com.example.touristguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.touristguide.authentication.Login;
import com.example.touristguide.authentication.SignUp;

public class Contact_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);


    }


    // Action -> Sign Up Button
    public void btn_signUp(View view){
        finish();
        startActivity(  new Intent(this , SignUp.class));
    overridePendingTransition(R.anim.up_signup,R.anim.down_signup);
    }

    // Action -> Login Button
    public void btn_login(View view){
        startActivity(  new Intent(this , Login.class));
        overridePendingTransition(R.anim.up_signup,R.anim.down_signup);
    }

}

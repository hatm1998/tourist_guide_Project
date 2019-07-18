package com.example.touristguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.touristguide.authentication.SignUp;

public class Contact_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);
    }


    public void btn_signUp(View view){
        startActivity(  new Intent(this , SignUp.class));}
}

package com.example.touristguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.touristguide.Places_Api.Display_Places_Option;

public class test_places extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_places);
    }

    public  void open(View view){
        Intent intent =new Intent(this, Display_Places_Option.class);
        intent.putExtra("search",((Button)view).getText().toString());
        Toast.makeText(this,((Button)view).getText().toString(),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}

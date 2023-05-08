package com.example.harmoni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

     Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAG", "onCreate");
        button=findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it=new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(it);
                }
            });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG", "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG", "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG", "onStop");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TAG", "onRestart");
    }







    }
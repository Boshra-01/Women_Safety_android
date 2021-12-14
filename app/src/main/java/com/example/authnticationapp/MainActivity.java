package com.example.authnticationapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.android.gms.location.LocationListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  {


    private Button law_btn;
    private Button alarm;
    private Button send_location;
    private Button tips_tricks_btn;
    private Button call_police;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       law_btn = findViewById(R.id.button_law);
       law_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this,Laws.class);
               startActivity(intent);
           }
       });
        alarm = findViewById(R.id.button_alarm);
        final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.policesiren);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        send_location = findViewById(R.id.button_sendLocation);
        send_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Location_get_and_send.class);
                startActivity(intent);
            }
        });


        call_police = (Button) findViewById(R.id.button_callPolice);
        call_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01729819000"));
                startActivity(intent);
            }
        });

            tips_tricks_btn = (Button) findViewById(R.id.button_tipsTricks);
            tips_tricks_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Tips_Tricks.class);
                    startActivity(intent);
                }
            });

        findViewById(R.id.button_recordVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                startActivityForResult(intent,1);
            }
        });


    }



    public void logout (View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode ==1){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        VideoView videoView = new VideoView(this);
        videoView.setVideoURI(data.getData());
        videoView.start();
        alertDialogBuilder.setView(videoView).show();


    }
    }


}

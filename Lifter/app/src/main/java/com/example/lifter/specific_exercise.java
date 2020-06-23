package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Scanner;

public class specific_exercise extends AppCompatActivity {

    TextView exDescriptions,exTips;
    String Exdescrip ="";
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_exercise);

        //setting the text to specific tasks
        exDescriptions =  findViewById(R.id.Exercisedesctxt);
        exDescriptions.setText("The pulldown exercise works the back muscles,"+
                "It is performed at a workstation with adjustable resistance," +
                "usually plates. While sitting with your upper thighs restrained under a thigh pad," +
                " you pull a hanging bar down toward you, to reach chin level, " +
                "and then release it back up with control for one repetition. " +
                "This exercise can be done as part of an upper body strength workout.");
        exTips = findViewById(R.id.Exercisetiptxt);
        exTips.setText(" 1. Keep some tone through your abdominals as you pull the bar into your body to ensure you don’t arch excessively through the spine.\n" +
                "2.Don’t allow momentum to dictate the movement, control the dumbbells throughout the entirety of each rep.\n" +
                "3.If you feel your biceps being overused and your back remaining under active, consider utilizing a false grip (i.e. don’t wrap the thumb around the dumbbell).\n" +
                "4.Don’t allow the head to jut forward as you pull.\n" +
                "5.Similarly, ensure the shoulder blade moves on the rib cage. Don’t lock the shoulder blade down and just move through the glenohumeral joint.\n" +
                "6.Allow the shoulder to internally rotate and shrug slightly at the top of the movement. You will obviously reverse the movement and depress the shoulder blade before you pull with the arm");

        // for the video
        final VideoView videoview = (VideoView) findViewById(R.id.videoView);
        String path = "/home/bruce/Documents/Uni2020/Sdp/git/lifter_inc/Lifter/app/src/main/res/raw/exercise_video.mp4";
        String videoPath ="android.resource://com.android.AndroidVideoPlayer/"+ R.raw.exercise_video;
        Uri uri = Uri.parse(videoPath) ;

        videoview.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoview.setMediaController(mediaController);
        videoview.setVideoPath(videoPath);
        mediaController.setAnchorView(videoview);

    }


    public String setExerciseDescriptions() {
        if (counter == 1){
            Exdescrip = "The pulldown exercise works the back muscles,"+
                    "It is performed at a workstation with adjustable resistance," +
                    "usually plates. While sitting with your upper thighs restrained under a thigh pad," +
                    " you pull a hanging bar down toward you, to reach chin level, " +
                    "and then release it back up with control for one repetition. " +
                    "This exercise can be done as part of an upper body strength workout. ";
        }

        return Exdescrip;
    }
}

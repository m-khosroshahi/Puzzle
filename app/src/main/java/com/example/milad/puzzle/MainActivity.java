package com.example.milad.puzzle;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imgPerson;
    TextView txtFalse ,txtDash ;



    ArrayList<Button> invisibleButton = new ArrayList<>();
    String [] name = {"بهرام","اکبر","نیکی","انا","پرویز","بابک","شادمهر"};
    int [] image = {R.drawable.radan,R.drawable.akbar_abdi,R.drawable.niki_karimi,R.drawable.nemati,
                    R.drawable.parstoye,R.drawable.babak_jahanbakhsh,R.drawable.shadmehr};


    String currentName;
    int counter = 0;
    int vinConter = 0;

    int nCorect=0;
    int nFalse=0;


    MediaPlayer mediaPlayer;


    char [] current_name ;
    char [] current_name_length;
    int currentLength = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDash= (TextView) findViewById(R.id.txtDash);
        txtFalse= (TextView) findViewById(R.id.txtFalse);
        imgPerson= (ImageView) findViewById(R.id.imgPerson);

        newGame(counter);

    }

    public void newGame (int n){

        currentLength =0;

        if (n <  name.length){

            String dash = "";
            String space = "";

            currentName = name[n];
            imgPerson.setImageResource(image[n]);
            current_name_length = currentName.toCharArray();
            for (int i = 0; i <currentName.length();i++){

                current_name_length [i]= '0';
            }

            for (int i = 0; i<= currentName.length()-1; i++){

                dash= dash +"-";
                space = space + " ";
            }
            txtFalse.setText(space);
            txtDash.setText(dash);

            for (Button perssBtn:invisibleButton){
                perssBtn.setVisibility(View.VISIBLE);
            }
            invisibleButton.clear();
            counter++;
            nCorect=0;
            nFalse=0;


        }else if (n >= name.length){
            counter=0;
            vinConter=0;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("دوس داری به شاهکاریت ! ادامه بدی؟")
                   .setCancelable(false)
                   .setPositiveButton("آره", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           newGame(counter);
                       }
                   })
                    .setNegativeButton("نخیر", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();

        }

    }

    public void playAgain (int n){

        currentLength =0;
        String dash = "";
        String space = "";

        currentName = name[n];
        imgPerson.setImageResource(image[n]);

        for (int i = 0; i<= currentName.length()-1; i++){

            dash= dash +"-";
            space = space + " ";
        }

        txtFalse.setText(space);
        txtDash.setText(dash);

        for (Button perssBtn:invisibleButton){
            perssBtn.setVisibility(View.VISIBLE);
        }
        invisibleButton.clear();

        nCorect=0;
        nFalse=0;



    }

    public void showbtn(View v) {

        Button presBtn = (Button) v;
        presBtn.setVisibility(View.INVISIBLE);
        invisibleButton.add(presBtn);
        char letter = presBtn.getText().toString().toCharArray()[0];
        char[] dashTxt = txtDash.getText().toString().toCharArray();
        char[] falseTxt = txtFalse.getText().toString().toCharArray();
        char[] currentChar = currentName.toCharArray();


        if (currentName.contains("" + letter)) {
            for (int i = 0; i < currentChar.length; i++) {

                if (currentChar[i] == letter) {

                    dashTxt[i] = letter;


                    current_name = currentName.toCharArray();
                    current_name_length[i]=letter;
                    currentLength++;



                    nCorect++;


                }
            }
            txtDash.setText(new String(dashTxt));

            if (currentChar.length == nCorect) {

                vinConter++;
                newGame(counter);
                nCorect = 0;


            }

        }
        else {

            if (nFalse >= currentChar.length-1){

              playAgain(vinConter);
                loserMedia();
                return;

            }
            falseTxt[nFalse]=letter;
            txtFalse.setText(new String(falseTxt));
            nFalse++;


        }

    }

    public void loserMedia(){

        mediaPlayer = MediaPlayer.create(this,R.raw.lose);
        mediaPlayer.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("حدس").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (currentLength == 0){
                    current_name = currentName.toCharArray();
                    String dashe = "";
                    for (int i = 0; i < currentName.length(); i++) {

                        dashe = dashe + "-";
                    }
                    txtDash.setText(dashe);
                }

                for(int i =current_name_length.length-1; i<0;i++) {

                    if (current_name_length[i] == '0') {

                        currentLength = i;

                    }
                }
                char character = current_name[currentLength];
                char[] dashTxt = txtDash.getText().toString().toCharArray();
                char[] currentChar = currentName.toCharArray();

                for (int j = currentLength; j<currentChar.length;j++){

                    if (currentChar[j]==character){
                        dashTxt [j]= character;
                        current_name_length[j]=character;
                        nCorect++;
                    }
                }
                txtDash.setText(new String(dashTxt));
                currentLength++;

                if (currentChar.length == nCorect) {

                    vinConter++;
                    newGame(counter);
                    nCorect = 0;
                    currentLength =0;

                }

                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
}



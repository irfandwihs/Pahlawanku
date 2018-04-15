package com.example.papermotion.pahlawanku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.papermotion.pahlawanku.Adapter.GridViewAnswerAdapter;
import com.example.papermotion.pahlawanku.Adapter.GridViewSuggestAdapter;
import com.example.papermotion.pahlawanku.Common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();

    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;
    private TextView mScoreView;
    private int mScore = 0;
    //private Button btnPlay;
    public Button btnSubmit;

    public GridView gridViewAnswer,gridViewSuggest;

    public ImageView imgViewQuestion;

    int[] image_list={

            R.drawable.bung_tomo,
            R.drawable.cut_nyak_dien,
            R.drawable.cut_nyak_meutia,
            R.drawable.dewi_sartika,
            R.drawable.drs_moh_hatta,
            R.drawable.hr_rasuna_said,
            R.drawable.ir_soekarno,
            R.drawable.jendral_sudirman,
            R.drawable.kapitan_pattimura,
            R.drawable.ki_hajar_dewantoro,
            R.drawable.martha_christina_tiahahu,
            R.drawable.nyi_ageng_serang,
            R.drawable.pangeran_diponegoro,
            R.drawable.ra_kartini,
            R.drawable.w_r_supratman,

    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init View
        initView();

    }

    private void initView() {
        mScoreView = (TextView) findViewById(R.id.score);
        gridViewAnswer = (GridView) findViewById(R.id.gridViewAnswer);
        gridViewSuggest = (GridView) findViewById(R.id.gridViewSuggest);

        imgViewQuestion = (ImageView) findViewById(R.id.imgLogo);

        //Add SetupList Here
        setupList();

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="";
                for(int i = 0; i< Common.user_submit_answer.length; i++)
                    result+=String.valueOf(Common.user_submit_answer[i]);
                if(result.equals(correct_answer))
                {
                    Toast.makeText(getApplicationContext(),"Benar,jawabannya "+result,Toast.LENGTH_SHORT).show();

                    //Reset
                    mScore = mScore + 10;
                    updateScore(mScore);
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    //Set Adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource,getApplicationContext(),MainActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Permainan selesai", Toast.LENGTH_SHORT).show();
                    gameover();
                }
            }
        });
    }

    private void updateScore(int mScore) {

        mScoreView.setText("Score : " + mScore);
    }

    private void gameover() {
        AlertDialog.Builder alertDiaologBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDiaologBuilder
                .setMessage("Permainan selesai, scoremu " + mScore + " poin.")
                .setCancelable(false)
                .setPositiveButton("COBA LAGI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        })

                .setNegativeButton("KELUAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDiaologBuilder.create();
        alertDialog.show();
    }

    private void setupList() {
        //Random logo
        Random random = new Random();
        int imageSelected = image_list[random.nextInt(image_list.length)];
        imgViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

        answer = correct_answer.toCharArray();

        Common.user_submit_answer = new char[answer.length];

        //Add Answer character to List
        suggestSource.clear();
        for(char item:answer)
        {
            //Add logo name to list
            suggestSource.add(String.valueOf(item));
        }

        //Random add some character to list
        for(int i = answer.length;i<answer.length*2;i++)
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

        //Sort random
        Collections.shuffle(suggestSource);

        //Set for GridView
        answerAdapter = new GridViewAnswerAdapter(setupNullList(),this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource,this,this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);


    }

    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for(int i=0;i<answer.length;i++)
            result[i]=' ';
        return result;
    }

    public void mScoreView(TextView mScoreView) {

        this.mScoreView = mScoreView;
    }

}

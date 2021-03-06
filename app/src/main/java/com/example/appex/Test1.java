package com.example.appex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Test1 extends AppCompatActivity {
    ImageView imgv;
    TextView question;
    Button btn_test_next, btn_test_before;
    ProgressBar progressBar;
    RadioGroup rg;
    RadioButton rb;
    public int count=0, sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        //툴바 설정
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼
        actionBar.setHomeAsUpIndicator(R.drawable.back_img);

        //홈 버튼 설정
        imgv = (ImageView) findViewById(R.id.imgv);
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        //변경 질문, 프로그래스 바, 레디오그룹 값
        question = (TextView) findViewById(R.id.tx_test_question);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rg = (RadioGroup) findViewById(R.id.radioGroup);

        btn_test_next = (Button)findViewById(R.id.btn_test_next);
        btn_test_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                int id = rg.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(id);
                if(rb.getText().equals("매우 그렇다"))
                    sum += 7;
                if (rb.getText().equals("그렇다"))
                    sum += 5;
                if(rb.getText().equals("아니다"))
                    sum += 3;
                if(rb.getText().equals("전혀 아니다"))
                    sum += 0;

                if (count == 1){
                    progressBar.setProgress(25);
                    question.setText("최근 심리적인 이유로 체중에 큰 변화가 있었나요?");
                }else if(count == 2){
                    progressBar.setProgress(50);
                    question.setText("자신이 실패자라고 생각 되시나요?");
                }else if(count == 3){
                    progressBar.setProgress(75);
                    question.setText("일상에 불만족하시나요?");
                }else if(count == 4){
                    progressBar.setProgress(100);
                    question.setText("죄책감을 느낄 때가 있나요?");
                }
                if(count == 5){
                    Intent intent = new Intent(getApplicationContext(), TestResult.class);
                    intent.putExtra("value", sum);
                    startActivity(intent);
                }
                //체크상태 해제 rg.clearCheck();
                //동작 확인 Toast.makeText(getApplicationContext(), sum +" 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_test_before = (Button)findViewById(R.id.btn_test_before);
        btn_test_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 0)
                    count=0;
                else
                    count--;

                int id = rg.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(id);
                if(rb.getText().equals("매우 그렇다"))
                    sum -= 7;
                if (rb.getText().equals("그렇다"))
                    sum -= 5;
                if(rb.getText().equals("아니다"))
                    sum -= 3;
                if(rb.getText().equals("전혀 아니다"))
                    sum -= 0;

                if (count == 0){
                    progressBar.setProgress(0);
                    question.setText("요즘 슬픈 기분인가요?");
                }else if (count == 1){
                    progressBar.setProgress(25);
                    question.setText("최근 심리적인 이유로 체중에 큰 변화가 있었나요?");
                }else if(count == 2){
                    progressBar.setProgress(50);
                    question.setText("자신이 실패자라고 생각 되시나요?");
                }else if(count == 3){
                    progressBar.setProgress(75);
                    question.setText("일상에 불만족하시나요?");
                }else if(count == 4){
                    progressBar.setProgress(100);
                    question.setText("죄책감을 느낄 때가 있나요?");
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

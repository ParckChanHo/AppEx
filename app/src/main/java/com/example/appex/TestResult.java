package com.example.appex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TestResult extends AppCompatActivity {
    ImageView imgv;
    Button btn_result;
    TextView tx_sum, tx_t1, tx_t2;
    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

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
                startActivity(intent);
            }
        });

        //점수 받아오기
        tx_sum = (TextView) findViewById(R.id.tx_sum);
        Intent intent = getIntent();
        a = intent.getIntExtra("value",0);
        tx_sum.setText(a+" 점");

        tx_t1=(TextView)findViewById(R.id.tx_t1);
        tx_t2=(TextView)findViewById(R.id.tx_t2);
        if (9<a && a<=15){
            tx_t1.setText("10~15점 : 가벼운 우울 상태");
            tx_t2.setText("나도 모르게 요즘 좀 우울...");
        }else if(16<=a && a<=23){
            tx_t1.setText("16~23점 : 중간 우울 상태");
            tx_t2.setText("괜찮은 듯 그러나 괜찮지 않은...");
        }else if(24<=a){
            tx_t1.setText("24점 이상 : 심한 우울 상태");
            tx_t2.setText("누군가의 전문적인 도움이 필요해요!");
        }

        //검진결과 그래프 화면으로 이동
        btn_result = (Button) findViewById(R.id.btn_result);
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TestGraph.class);
                intent.putExtra("value", a);
                startActivity(intent);
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

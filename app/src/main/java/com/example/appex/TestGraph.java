package com.example.appex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class TestGraph extends AppCompatActivity {
    ImageView imgv;
    int a=0;

    ArrayList<Integer> jsonList = new ArrayList<>(); // ArrayList 선언
    ArrayList<String> labelList = new ArrayList<>(); // ArrayList 선언
    ArrayList<Integer> graph = new ArrayList<>(); //db에 저장된 값을 읽어오고 현재 점수 추가용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_graph);

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
        Intent intent = getIntent();
        a = intent.getIntExtra("value",0);

        graph.add(10);
        graph.add(20);
        graph.add(a);
        graph.add(0);
        graph.add(0);
        graph.add(0);
        graph.add(0);

        String day[] = {"월", "화", "수", "목", "금", "토", "일"};

        for (int i=0; i<graph.size(); i++){
            jsonList.add(graph.get(i));
            labelList.add(""+day[i]+"");
        }

        BarChartGraph(labelList, jsonList);

    }

    private void BarChartGraph(ArrayList<String> labelList, ArrayList<Integer> valList) {
        // BarChart 메소드
        BarChart barChart = (BarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0; i < valList.size();i++){
            entries.add(new BarEntry((Integer) valList.get(i), i));
        }

        BarDataSet depenses = new BarDataSet (entries, ""); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.RIGHT);
        barChart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0; i < labelList.size(); i++){
            labels.add((String) labelList.get(i));
        }

        BarData data = new BarData(labels,depenses); // 라이브러리 v3.x 사용하면 에러 발생함
        //depenses.setColors(ColorTemplate.LIBERTY_COLORS); //

        barChart.setData(data);
        barChart.animateXY(1000,1000);
        barChart.invalidate();
    }

    private String DateChange(String sysdate){

        String year = sysdate.split("-")[0];

        String month = sysdate.split("-")[1];

        String day = sysdate.split("-")[2];


        StringBuilder finalString = new StringBuilder();

        finalString.append(year.substring(2,4));

        finalString.append(".");

        finalString.append(month);

        //finalString.append(".");

        //finalString.append(day);

        return finalString.toString();

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

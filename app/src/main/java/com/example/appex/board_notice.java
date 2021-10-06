package com.example.appex;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class board_notice extends AppCompatActivity {
    TextView no_title, no_author, no_content;
    String boardnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardnotice);

        //툴바 설정
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); //왼쪽 버튼 사용 여부

        //fragment3에서 전달해준 게시글번호 받기
        Intent intent = new Intent(this.getIntent());
        boardnum = intent.getStringExtra("boardnum");
        Toast.makeText(getApplicationContext(), boardnum, Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://115.85.181.116:8080/android/webapp/notice.jsp?boardnum="+boardnum;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String xml = response.trim();

                            no_title = (TextView) findViewById(R.id.notice_title);
                            no_content = (TextView) findViewById(R.id.notice_content);
                            no_author = (TextView) findViewById(R.id.notice_author);

                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(true);
                            XmlPullParser parser = factory.newPullParser();
                            parser.setInput(new StringReader(xml));

                            String title="", content="", author=""; //제목, 내용, 작성자, 게시글번호
                            int evenType = parser.getEventType();
                            boolean a = false, b = false, c = false, d = false;
                            while (evenType != XmlPullParser.END_DOCUMENT){
                                if (evenType == XmlPullParser.START_TAG){
                                    if (parser.getName().equals("title")) a = true;
                                    if (parser.getName().equals("content")) b = true;
                                    if (parser.getName().equals("author")) c = true;
                                }else if(evenType == XmlPullParser.TEXT){
                                    if (a){
                                        title = parser.getText();
                                        no_title.setText(title);
                                        a = false;
                                    }
                                    if (b){
                                        content = parser.getText();
                                        no_content.setText(content);
                                        b = false;
                                    }
                                    if (c){
                                        author = parser.getText();
                                        no_author.setText(author);
                                        c = false;
                                    }
                                }
                                evenType = parser.next();
                            }
                        } catch (XmlPullParserException | IOException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

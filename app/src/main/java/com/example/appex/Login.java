package com.example.appex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    //로그인버튼, 회원가입
    Button btn_login, btn_signup;
    //입력 값
    EditText ed_id, ed_pwd;
    //자동로그인
    String loginId, loginPwd, loginNick, userid, userpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //edit 입력 값
        ed_id=(EditText) findViewById(R.id.editId);
        ed_pwd=(EditText) findViewById(R.id.editPwd);
        //버튼 동작
        btn_login=(Button) findViewById(R.id.login_btn);
        btn_signup=(Button) findViewById(R.id.signup_btn);

        //자동로그인 기능
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);
        loginNick = auto.getString("inputNick", null);
        if(loginId != null && loginPwd != null){
            Toast.makeText(this, loginNick +"님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else if (loginId == null && loginPwd == null){
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userid = ed_id.getText().toString();
                    userpwd = ed_pwd.getText().toString();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://115.85.181.116:8080/android/webapp/applogin.jsp?id="+userid+"&pwd="+userpwd;

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //response 값 확인을 위한 토스트메시지
                                    //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                    try {
                                        String a = response.trim();
                                        if(a.equals("아이디 또는 비밀번호가 틀렸습니다")){
                                            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                                        }
                                        JSONObject jsonresponse = new JSONObject(a);
                                        String jres = jsonresponse.getString("success");
                                        String jres2 = jsonresponse.getString("nickname");
                                        if (jres.equals("success")) {
                                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                            SharedPreferences.Editor autoLogin = auto.edit();
                                            autoLogin.putString("inputId", userid);
                                            autoLogin.putString("inputPwd", userpwd);
                                            autoLogin.putString("inputNick", jres2);
                                            autoLogin.commit();
                                            Toast.makeText(getApplicationContext(), jres2+"님 환영합니다.", Toast.LENGTH_SHORT).show();
                                            finish();

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("nickname", jres2);
                                            startActivity(intent);
                                        }
                                    }catch (Exception e){
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    requestQueue.add(stringRequest);
                }
            });

        }

        //회원가입 클릭
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivityForResult(intent, 101);
            }
        });

    }
}

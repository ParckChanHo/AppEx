package com.example.appex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    //아이디, 비밀번호, 닉네임, 이메일
    EditText su_id, su_pwd, su_nick, su_check;
    //중복확인 버튼
    Button btn_id, btn_nick, btn_signup;
    //입력 값
    String userid, userpwd, usernick, usercheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //입력 값, 버튼
        su_id=(EditText) findViewById(R.id.su_id);
        su_pwd=(EditText) findViewById(R.id.su_pwd);
        su_nick=(EditText) findViewById(R.id.su_nickname);
        su_check=(EditText) findViewById(R.id.su_pwd_check);
        btn_id=(Button) findViewById(R.id.check_id);
        btn_nick=(Button) findViewById(R.id.check_nickname);
        btn_signup=(Button) findViewById(R.id.signup_btn);

        btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid = su_id.getText().toString();
                if(userid.equals("")){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://115.85.181.116:8080/android/webapp/idCheck.jsp?id="+userid;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    String a = response.trim();
                                    if(a.equals("사용 가능한 아이디입니다")){
                                        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                                    }
                                    JSONObject jsonresponse = new JSONObject(a);
                                    String jres = jsonresponse.getString("use");
                                    if (jres.equals("use")) {
                                        Toast.makeText(getApplicationContext(), "이미 사용중인 아이디입니다", Toast.LENGTH_SHORT).show();
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

        //닉네임 중복 확인
        btn_nick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernick = su_nick.getText().toString();
                if(usernick.equals("")){
                    Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://115.85.181.116:8080/android/webapp/nnCheck.jsp?nickname="+usernick;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    String a = response.trim();
                                    if(a.equals("사용 가능한 닉네임입니다")){
                                        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                                    }
                                    JSONObject jsonresponse = new JSONObject(a);
                                    String jres = jsonresponse.getString("use");
                                    if (jres.equals("use")) {
                                        Toast.makeText(getApplicationContext(), "이미 사용중인 닉네임입니다", Toast.LENGTH_SHORT).show();
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

        //회원가입 버튼 클릭
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //string에 값 담아두기
                userid = su_id.getText().toString();
                userpwd = su_pwd.getText().toString();
                usernick = su_nick.getText().toString();
                usercheck = su_check.getText().toString();

                //값을 입력하지 않으면 return
                if(userid.equals("") || userpwd.equals("") || usernick.equals("")){
                    Toast.makeText(getApplicationContext(),"아이디와 비밀번호, 닉네임을 모두 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                //비밀번호가 같으면 가입
                if (userpwd.equals(usercheck)){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://115.85.181.116:8080/android/webapp/appRegisterDB.jsp?id="+userid+"&pwd="+userpwd+"&nickname="+usernick;

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonresponse = new JSONObject(response);
                                        String jres = jsonresponse.getString("success");
                                        if (jres.equals("success")) {  //회원가입 성공시
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                        }
                                    }catch (Exception e){
                                        /*tx.append("\n\n error is:"+e.getMessage());*/
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(getApplicationContext(),"비밀번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}

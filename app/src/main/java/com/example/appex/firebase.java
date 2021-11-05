package com.example.appex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class firebase extends AppCompatActivity {
    RecyclerView recyclerView;
    RequestQueue requestQueue;

    Button sendbt, btn_save;
    TextView tx;
    int b = 0;
    String []a;
    EditText et_user_name,et_user_email;
    //private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    //private DatabaseReference databaseReference = mDatabase.getReference();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        requestQueue = Volley.newRequestQueue(this);



        sendbt = (Button) findViewById(R.id.button123);
        et_user_name = findViewById(R.id.editText);
        et_user_email = findViewById(R.id.editText2);
        btn_save = findViewById(R.id.button2);
        tx =findViewById(R.id.textView123);

        sendbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 버튼 누르면 수행 할 명령
                mDatabase.child("message").push().setValue("2");
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        readUser();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUserName = et_user_name.getText().toString();
                String getUserEmail = et_user_email.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("title", getUserName);
                result.put("content", getUserEmail);

                writeNewUser(Integer.toString(b),getUserName,getUserEmail);
                b++;
            }
        });
    }
    private void writeNewUser(String userId, String title, String content) {
        User user = new User(title, content);

        mDatabase.child("user").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(firebase.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(firebase.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void readUser(){

        for (int i=0; i<4; i++){
            String c = Integer.toString(i);
            mDatabase.child("user").child(c).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if(dataSnapshot.getValue(User.class) != null){
                        User post = dataSnapshot.getValue(User.class);
                        //Log.w("FireBaseData", "getData" + post.toString());
                        Toast.makeText(firebase.this, post.toString(), Toast.LENGTH_SHORT).show();
                        tx.setText(post.toString());
                    } else {
                        Toast.makeText(firebase.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    //Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }

    }
}

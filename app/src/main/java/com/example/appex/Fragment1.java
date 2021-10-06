package com.example.appex;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    TextView tx_wth, tx_main, tx_temp, tx_maxmin, tx_time, comfort;
    RequestQueue requestQueue;
    ImageView img_wth, time1, time2, time3;

    Double latitude, longitude;
    String area1, area2;

    int t1 = 0;
    int t2 = 0;
    int t3 = 0;
    int count = 0;
    int sum = 0;
    boolean t1_flag = false;
    boolean t2_flag = false;
    boolean t3_flag = false;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment1, container, false);

        tx_wth = (TextView) v.findViewById(R.id.tx_weather);
        tx_main = (TextView) v.findViewById(R.id.tx_main);
        tx_temp = (TextView) v.findViewById(R.id.tx_temp);
        tx_maxmin = (TextView) v.findViewById(R.id.tx_MaxMin);
        comfort = (TextView) v.findViewById(R.id.tx_last);
        tx_time = (TextView) v.findViewById(R.id.tx_time);
        img_wth = (ImageView) v.findViewById(R.id.img_weather);
        time1 = (ImageView)v.findViewById(R.id.imageView4);
        time2 = (ImageView)v.findViewById(R.id.imageView5);
        time3 = (ImageView)v.findViewById(R.id.imageView6);

        //시간구하기
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 a hh시 mm분");
        String getTime = simpleDateFormat.format(date);
        tx_time.setText(getTime);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }

        /// 나의 기분 날씨
        final String[] hw = new String[]{"너무 좋아요^~^", "좋아요!", "괜찮아요~", "안좋아요ㅡ.ㅡ", "엄청 힘들어요ㅠ.ㅠ"};
        // 다이어로그 창 - 기분 고르는 곳(값은 2 4 6 8 10)
        final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
        dlg.setTitle("나의 기분을 선택하세요");

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1_flag == false){
                    dlg.setItems(hw, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(hw[which].equals("너무 좋아요^~^")){
                                count++;
                                t1 = 10;
                                sum+=t1;
                                time1.setImageResource(R.drawable.face1);
                                t1_flag = true;
                            }else if(hw[which].equals("좋아요!")){
                                count++;
                                t1 = 8;
                                sum+=t1;
                                time1.setImageResource(R.drawable.face2);
                                t1_flag = true;
                            }else if(hw[which].equals("괜찮아요~")){
                                count++;
                                t1 = 6;
                                sum+=t1;
                                time1.setImageResource(R.drawable.face3);
                                t1_flag = true;
                            }else if(hw[which].equals("안좋아요ㅡ.ㅡ")){
                                count++;
                                t1 = 4;
                                sum+=t1;
                                time1.setImageResource(R.drawable.face5);
                                t1_flag = true;
                            }else if(hw[which].equals("엄청 힘들어요ㅠ.ㅠ")){
                                count++;
                                t1 = 2;
                                sum+=t1;
                                time1.setImageResource(R.drawable.face6);
                                t1_flag = true;
                            }
                            cf();
                        }
                    });
                    dlg.show();
                }else
                    Toast.makeText(getActivity(), "이미 하셨습니다!", Toast.LENGTH_SHORT).show();
            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t2_flag == false){
                    dlg.setItems(hw, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(hw[which].equals("너무 좋아요^~^")){
                                count++;
                                t2 = 10;
                                sum+=t2;
                                time2.setImageResource(R.drawable.face1);
                                t2_flag = true;
                            }else if(hw[which].equals("좋아요!")){
                                count++;
                                t2 = 8;
                                sum+=t2;
                                time2.setImageResource(R.drawable.face2);
                                t2_flag = true;
                            }else if(hw[which].equals("괜찮아요~")){
                                count++;
                                t2 = 6;
                                sum+=t2;
                                time2.setImageResource(R.drawable.face3);
                                t2_flag = true;
                            }else if(hw[which].equals("안좋아요ㅡ.ㅡ")){
                                count++;
                                t2 = 4;
                                sum+=t2;
                                time2.setImageResource(R.drawable.face5);
                                t2_flag = true;
                            }else if(hw[which].equals("엄청 힘들어요ㅠ.ㅠ")){
                                count++;
                                t2 = 2;
                                sum+=t2;
                                time2.setImageResource(R.drawable.face6);
                                t2_flag = true;
                            }
                            cf();
                        }
                    });
                    dlg.show();
                }else
                    Toast.makeText(getActivity(), "이미 하셨습니다!", Toast.LENGTH_SHORT).show();
            }
        });
        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t3_flag == false){
                    dlg.setItems(hw, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(hw[which].equals("너무 좋아요^~^")){
                                count++;
                                t3 = 10;
                                sum+=t3;
                                time3.setImageResource(R.drawable.face1);
                                t3_flag = true;
                            }else if(hw[which].equals("좋아요!")){
                                count++;
                                t3 = 8;
                                sum+=t3;
                                time3.setImageResource(R.drawable.face2);
                                t3_flag = true;
                            }else if(hw[which].equals("괜찮아요~")){
                                count++;
                                t3 = 6;
                                sum+=t3;
                                time3.setImageResource(R.drawable.face3);
                                t3_flag = true;
                            }else if(hw[which].equals("안좋아요ㅡ.ㅡ")){
                                count++;
                                t3 = 4;
                                sum+=t3;
                                time3.setImageResource(R.drawable.face5);
                                t3_flag = true;
                            }else if(hw[which].equals("엄청 힘들어요ㅠ.ㅠ")){
                                count++;
                                t3 = 2;
                                sum+=t3;
                                time3.setImageResource(R.drawable.face6);
                                t3_flag = true;
                            }
                            cf();
                        }
                    });
                    dlg.show();
                }else
                    Toast.makeText(getActivity(), "이미 하셨습니다!", Toast.LENGTH_SHORT).show();
            }
        });

        //메인에서 값 받아오기
        Bundle bundle = getArguments();
        if (bundle != null){
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
            area1 = bundle.getString("a_one");
            area2 = bundle.getString("a_two");
        }
        tx_wth.setText(area1+" "+area2);
        //q=Seoul
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=e8f45ea2d762cd16bdb9c04523126aa5";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String json = response;
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject jsonweather = jsonArray.getJSONObject(0);
                            //weather jsonarray에서 날씨 main 값 가져오기
                            String main = jsonweather.getString("main");
                            //날씨에 따라 이미지 바꾸기
                            transferImage(main);
                            //한글 날씨로 변환
                            main = transferWeather(main);
                            //main jsonobject에서 온도, 습도 값 가져오기
                            JSONObject jsonmain = jsonObject.getJSONObject("main");
                            Integer maintemp = jsonmain.getInt("temp");
                            Integer temp_max = jsonmain.getInt("temp_max");
                            Integer temp_min = jsonmain.getInt("temp_min");
                            //섭씨로 변환
                            maintemp -= 273;
                            temp_max -= 273;
                            temp_min -= 273;

                            //tx_wth.setText("현 위치 날씨: " + main + ", 습도: " + mainhumidity + "%, 온도: " + maintemp);
                            tx_main.setText(main);
                            tx_temp.setText(maintemp + "°C");
                            tx_maxmin.setText(temp_max + "°C /" + temp_min + "°C");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tx_wth.setText(error.getMessage());
                    }
                }
        );
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

        return v;
    }

    private void transferImage(String main) {
        String img = main.toLowerCase();
        if (img.equals("clear")) {
            img_wth.setImageResource(R.drawable.clear);
        } else if (img.equals("clouds")) {
            img_wth.setImageResource(R.drawable.cloud);
        } else if (img.equals("rain")) {
            img_wth.setImageResource(R.drawable.rain_cloud);
        } else if (img.equals("snow")) {
            img_wth.setImageResource(R.drawable.snow);
        }
    }

    private String transferWeather(String weather) {
        String a = weather.toLowerCase();
        if (a.equals("clear"))
            return "맑음";
        else if (a.equals("thunderstorm"))
            return "뇌우";
        else if (a.equals("clouds"))
            return "구름";
        else if (a.equals("snow"))
            return "눈";
        else if (a.equals("rain"))
            return "비";
        else if (a.equals("drizzle"))
            return "이슬비";
        else if (a.equals("mist"))
            return "안개";
        else if (a.equals("fog"))
            return "안개";
        else if (a.equals("dust"))
            return "먼지";
        else return "안녕";
    }

    public void cf(){
        int average = sum/count;
        comfort.setText("" + average);
        if (2<= average && 4> average)
            comfort.append("오늘 엄청 힘들어 하시네요.ㅠㅠ 그래도 화이팅!!");
        else if (4<= average && 6> average)
            comfort.append("\n오늘은 기분이 별로시군요 음악을 들으면서 쉬는 걸 추천드려요 화이팅!");
        else if (6<= average && 8> average)
            comfort.append("\n기분이 더 좋아지게 하고 싶었던걸 고민하지말고 해보세요!");
        else if (8<= average && 10> average)
            comfort.append("\n오늘은 기분이 좋아보이네요. 더 좋은 일만 일어나길~");
        else if (average == 10)
            comfort.append("\n오늘은 기분이 매우 좋아보여요. 모든 일이 다 잘 될겁니다!!");
    }

}

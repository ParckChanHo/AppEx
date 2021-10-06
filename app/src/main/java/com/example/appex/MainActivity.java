package com.example.appex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    //네비게이션 드로어
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    //홈이미지
    ImageView imgv;
    //바텀바 프래그먼트
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;
    //바텀 네비게이션
    BottomNavigationView bottomNavigationView;
    TextView title;

    LocationManager locationManager;
    Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바 설정
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); //왼쪽 버튼 사용 여부

        //네비게이션 메뉴 동작 지정
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = item.getItemId();
                String title = item.getTitle().toString();

                //로그인, 검진결과, 상담센터, 이용방법 페이지 이동
                if (id==R.id.logout){ //로그아웃
                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = auto.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(context,"로그아웃",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }else if(id==R.id.test){
                    Intent intent = new Intent(getApplicationContext(), TestGraph.class);
                    startActivity(intent);
                }else if(id==R.id.connect){
                    Toast.makeText(context,"상담센터",Toast.LENGTH_SHORT).show();
                }else if(id==R.id.using){
                    Toast.makeText(context,"이용방법",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        title = (TextView) findViewById(R.id.title);
        //프래그먼트 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        //홈 버튼 설정
        imgv = (ImageView) findViewById(R.id.imgv);
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        //바텀 네비게이션 뷰 설정
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        //제일 처음 띄워줄 뷰
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment1).commitAllowingStateLoss();
        //바텀 네비게이션 아이콘 선택 시 프래그먼트 이동
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment4).commitAllowingStateLoss();
                        title.setText("Healing Action");
                        return true;
                    case R.id.cure:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment2).commitAllowingStateLoss();
                        title.setText("자기분석 테스트");
                        return true;
                    case R.id.board:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment3).commitAllowingStateLoss();
                        title.setText("게시판");
                        return true;
                    default:return false;
                }
            }
        });

        //프래그먼트에 위치정보 전달
        AutoPermissions.Companion.loadAllPermissions(this,101);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                List<Address> list = geocoder.getFromLocation(latitude, longitude, 10);
                String area_one = list.get(0).getAdminArea().toString();
                String area_two = list.get(0).getThoroughfare().toString();
                Bundle bundle = new Bundle(2);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putString("a_one", area_one);
                bundle.putString("a_two", area_two);
                fragment1.setArguments(bundle);
            } else if (location == null) {
                latitude = 37.5846;
                longitude = 126.9252;

                List<Address> list = geocoder.getFromLocation(latitude, longitude, 10);
                String area_one = list.get(0).getAdminArea().toString();
                String area_two = list.get(0).getThoroughfare().toString();

                //메인프레그먼트로 경도 위도, 지역명 넘겨주기
                Bundle bundle = new Bundle(2);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                bundle.putString("a_one", area_one);
                bundle.putString("a_two", area_two);
                fragment1.setArguments(bundle);
            }
        } catch (SecurityException | IOException e) {
            Toast.makeText(getApplicationContext(), "GPS오류", Toast.LENGTH_SHORT).show();
        }

        GPSListener gpsListener = new GPSListener();
        long minTime = 20000;
        float minDistance = 0;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
        }
    }
}

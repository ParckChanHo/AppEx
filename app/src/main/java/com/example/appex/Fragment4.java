package com.example.appex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {
    RecyclerView recyclerView;
    FragmentPagerAdapter adapterViewPager;

    public Fragment4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment4, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.RecyclerView);

        //viewpager 이미지 전환
        ViewPager vpPager = (ViewPager) v.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        //아이콘 추가
        CircleIndicator indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);

        final ArrayList<Music> list = new ArrayList<>();
        list.add(new Music("인연",R.drawable.music));
        list.add(new Music("스물다섯 스물하나",R.drawable.music2));
        list.add(new Music("안드로이드",R.drawable.music3));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MusicAdapter(list));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);
                String url = "https://www.youtube.com/watch?v=kUJv343NHsU&ab_channel=KENKAMIKITAKENKAMIKITA";
                String url2 = "https://www.youtube.com/watch?v=qvJ1FHRR1n8&ab_channel=KENKAMIKITAKENKAMIKITA";
                String url3 = "https://www.youtube.com/watch?v=y7mfblGo_gw&list=RDMMNOr0l9n5dSE&index=8&ab_channel=XYNSIA%EC%8B%A0%EC%8B%9C%EC%95%84XYNSIA%EC%8B%A0%EC%8B%9C%EC%95%84";
                switch (position){
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    case 1:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                        startActivity(intent);
                        return true;
                    case 2:
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url3));
                        startActivity(intent);
                        return true;
                    default:return false;
                }
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return v;
    }

    //무한 viewpager을 만드려면 Indicator을 포기해야됨 그냥 3개로 동작
    public static class MyPagerAdapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManagerr){
            super(fragmentManagerr);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position%3){
                case 0:
                    return Fragment_Img1.newInstace(0, "Page # 1");
                case 1:
                    return Fragment_Img2.newInstace(1, "Page # 2");
                case 2:
                    return Fragment_Img3.newInstace(2, "Page # 3");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }


}

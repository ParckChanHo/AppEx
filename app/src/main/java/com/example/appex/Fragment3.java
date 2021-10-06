package com.example.appex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    RecyclerView recyclerView;
    RequestQueue requestQueue;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment3, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://115.85.181.116:8080/android/webapp/board.jsp";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String xml = response.trim();

                            //try안에 view와 list 생성 후 사용
                            recyclerView = (RecyclerView)v.findViewById(R.id.RecyclerView);
                            ArrayList<Table> list = new ArrayList<>();

                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(true);
                            XmlPullParser parser = factory.newPullParser();
                            parser.setInput(new StringReader(xml));

                            //Toast.makeText(getActivity(), xml, Toast.LENGTH_SHORT).show();

                            String title="", content="", author="", boardnum=""; //제목, 내용, 작성자, 게시글번호
                            int count = 0;
                            int evenType = parser.getEventType();
                            boolean a = false, b = false, c = false, d = false;
                            while (evenType != XmlPullParser.END_DOCUMENT){
                                if (evenType == XmlPullParser.START_TAG){
                                    if (parser.getName().equals("title")) a = true;
                                    if (parser.getName().equals("content")) b = true;
                                    if (parser.getName().equals("author")) c = true;
                                    if (parser.getName().equals("boardnum")) d = true;
                                }else if(evenType == XmlPullParser.TEXT){
                                    if (a){
                                        title = parser.getText();
                                        count++;
                                        a = false;
                                    }
                                    if (b){
                                        content = parser.getText();
                                        count++;
                                        b = false;
                                    }
                                    if (c){
                                        author = parser.getText();
                                        count++;
                                        c = false;
                                    }
                                    if (d){
                                        boardnum = parser.getText();
                                        count++;
                                        d = false;
                                    }
                                }
                                if (count == 4){
                                    list.add(new Table(title, content, author, boardnum));
                                    count = 0;
                                }
                                evenType = parser.next();
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(new TableAdapter(list, getContext()));
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

        return v;
    }

}

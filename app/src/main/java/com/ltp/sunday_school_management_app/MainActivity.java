package com.ltp.sunday_school_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.adapter.DepartmentAdapter;
import com.ltp.sunday_school_management_app.adapter.RecyclerItemClickListener;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView departmentRecyclerView;
    DepartmentAdapter departmentAdapter;

    static String MY_URL_BASE = "http://192.168.29.159:88/api/";
    ArrayList<DepartmentEntity> departmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        departmentList = new ArrayList<>();

        departmentRecyclerView = findViewById(R.id.department_recycler_view);
        Log.d("tag","asddsd");

        String URL = MY_URL_BASE + "department";
        Ion.with(this)
                .load("GET",URL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        departmentList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(result));
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject department = jsonArray.getJSONObject(i);
                                String myDept = department.getString("name");
                                int myId = department.getInt("id");

                                DepartmentEntity departmentEntity = new DepartmentEntity();
                                departmentEntity.setName(myDept);
                                departmentEntity.setId(myId);

                                departmentList.add(departmentEntity);
                            }

                        departmentAdapter = new DepartmentAdapter(departmentList,getApplicationContext());
                        departmentRecyclerView.setAdapter(departmentAdapter);
                        departmentRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

                        departmentRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), departmentRecyclerView,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        int id = departmentList.get(position).getId();
                                        String name = departmentList.get(position).getName();

                                        Intent intent = new Intent(getApplicationContext(),TeacherActivity.class);
                                        intent.putExtra("departmentId",id);
                                        intent.putExtra("departmentName",name);

                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }
                                }));
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                });


    }
}
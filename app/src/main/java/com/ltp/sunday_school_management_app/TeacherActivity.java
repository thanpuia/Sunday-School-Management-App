package com.ltp.sunday_school_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.adapter.RecyclerItemClickListener;
import com.ltp.sunday_school_management_app.adapter.TeacherAdapter;
import com.ltp.sunday_school_management_app.entity.TeacherEntity;
import com.ltp.sunday_school_management_app.form.TeacherFormActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {
    int departmentId;
    String departmentName;

    RecyclerView teacherRecyclerView;
    TextView departmentNameTv;

    TeacherAdapter teacherAdapter;

    ArrayList<TeacherEntity> teacherEntities;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        teacherRecyclerView = findViewById(R.id.teacher_recycler_view);
        departmentNameTv = findViewById(R.id.department_name);

        teacherEntities = new ArrayList<>();
        sharedPreferences = this.getSharedPreferences("com.example.root.sharedpreferences", this.MODE_PRIVATE);

        departmentId = sharedPreferences.getInt("departmentId",0);
        departmentName = sharedPreferences.getString("departmentName","");


        departmentNameTv.setText(departmentName);
        String URL = MainActivity.MY_URL_BASE+"teacherByDepartment/"+departmentId;
        Ion.with(this)
                .load("GET",URL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        teacherEntities.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(result));
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(jsonArray.length()==0){

                            }else {

                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject teacher = jsonArray.getJSONObject(k);
                                    String myTeacher = teacher.getString("name");
                                    int myId = teacher.getInt("id");

                                    TeacherEntity teacherEntity = new TeacherEntity();
                                    teacherEntity.setName(myTeacher);
                                    teacherEntity.setId(myId);

                                    teacherEntities.add(teacherEntity);
                                }


                                teacherAdapter = new TeacherAdapter(teacherEntities, departmentId, getApplicationContext());
                                teacherRecyclerView.setAdapter(teacherAdapter);
                                teacherRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                teacherRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), teacherRecyclerView,
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                int id = teacherEntities.get(position).getId();
                                                String name = teacherEntities.get(position).getName();

                                                sharedPreferences.edit().putInt("teacherId" , id).apply();
                                                sharedPreferences.edit().putString("teacherName" , name).apply();

                                                Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                                                intent.putExtra("teacherId",id);
                                                intent.putExtra("teacherName",name);

                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onLongItemClick(View view, int position) {

                                            }
                                        }));
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                });


    }

    public void addTeacherClick(View view) {
        Intent intent = new Intent(this, TeacherFormActivity.class);
        intent.putExtra("departmentId",departmentId);

        startActivity(intent);
    }

}
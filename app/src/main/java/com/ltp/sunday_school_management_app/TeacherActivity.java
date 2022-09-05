package com.ltp.sunday_school_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.adapter.DepartmentAdapter;
import com.ltp.sunday_school_management_app.adapter.TeacherAdapter;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        teacherRecyclerView = findViewById(R.id.teacher_recycler_view);
        departmentNameTv = findViewById(R.id.department_name);

        teacherEntities = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            departmentId = extras.getInt("departmentId");
            departmentName = extras.getString("departmentName");
        }else{
            departmentId = 0;
            departmentName ="";
        }

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

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject teacher = jsonArray.getJSONObject(i);
                                String myTeacher = teacher.getString("name");
                                int myId = teacher.getInt("id");

                                TeacherEntity teacherEntity = new TeacherEntity();
                                teacherEntity.setName(myTeacher);
                                teacherEntity.setId(myId);

                                teacherEntities.add(teacherEntity);
                            }

                            Log.d("tag",""+teacherEntities.get(1));

                            teacherAdapter = new TeacherAdapter(teacherEntities,getApplicationContext());
                            teacherRecyclerView.setAdapter(teacherAdapter);
                            teacherRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                });


    }

    public void addTeacherClick(View view) {
        Intent intent = new Intent(this, TeacherFormActivity.class);
        intent.putExtra("","");

        startActivity(intent);
    }
}
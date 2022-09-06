package com.ltp.sunday_school_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.adapter.StudentAdapter;
import com.ltp.sunday_school_management_app.adapter.TeacherAdapter;
import com.ltp.sunday_school_management_app.entity.StudentEntity;
import com.ltp.sunday_school_management_app.entity.TeacherEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    int teacherId;
    String teacherName;

    RecyclerView studentRecyclerView;
    TextView teacherNameTv;

    StudentAdapter studentAdapter;

    ArrayList<StudentEntity> studentEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        studentRecyclerView = findViewById(R.id.student_recycler_view);
        teacherNameTv = findViewById(R.id.teacher_name);

        studentEntities = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            teacherId = extras.getInt("teacherId");
            teacherName = extras.getString("teacherName");
        }else{
            teacherId = 0;
            teacherName ="";
        }

        teacherNameTv.setText(teacherName);
        String URL = MainActivity.MY_URL_BASE+"studentByTeacher/"+teacherId;
        Ion.with(this)
                .load("GET",URL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        studentEntities.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(result));
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(jsonArray.length()==0){

                            }else {

                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject student = jsonArray.getJSONObject(k);
                                    String myName = student.getString("name");
                                    int myId = student.getInt("id");

                                    StudentEntity studentEntity = new StudentEntity();
                                    studentEntity.setName(myName);
                                    studentEntity.setId(myId);

                                    studentEntities.add(studentEntity);
                                }


                                studentAdapter = new StudentAdapter(studentEntities, getApplicationContext());
                                studentRecyclerView.setAdapter(studentAdapter);
                                studentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                });


    }

    public void addStudentClick(View view) {
    }
}
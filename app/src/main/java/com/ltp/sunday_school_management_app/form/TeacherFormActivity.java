package com.ltp.sunday_school_management_app.form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.R;
import com.ltp.sunday_school_management_app.TeacherActivity;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherFormActivity extends AppCompatActivity {

    EditText name;
    EditText dob;
    EditText phone;
    EditText bial;
    EditText section;
    EditText location;
    CircleImageView circleImageView;
    Button teacherNewSubmitButton;

    ArrayList<DepartmentEntity> departmentEntities;

    static String MY_URL_BASE = "https://electricveng.herokuapp.com/api/";
    int departmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        departmentId = getIntent().getIntExtra("departmentId",0);
        departmentEntities = new ArrayList<>();

        name = findViewById(R.id.teacher_name_et);
        dob = findViewById(R.id.dob_et);
        phone = findViewById(R.id.phone_et);
        bial = findViewById(R.id.bial_et);
        section = findViewById(R.id.section_et);
        location = findViewById(R.id.location_et);
        circleImageView = findViewById(R.id.teacher_picture_circle);
        teacherNewSubmitButton = findViewById(R.id.teacher_submit);

       teacherNewSubmitButton.setEnabled(false);
        getTheDepartmentList();

    }


    public void teacherSubmitClick(View view) {
        String name,dob,phone,bial,section,location,circleImageView;

        name = this.name.getText().toString();
        dob = this.dob.getText().toString();
        phone = this.phone.getText().toString();
        bial = this.bial.getText().toString();
        section = this.section.getText().toString();
        location = this.location.getText().toString() ;


        JsonObject teacher = new JsonObject();
        teacher.addProperty("name",name);
        teacher.addProperty("dob",dob);
        teacher.addProperty("phone",phone);
        teacher.addProperty("bial",bial);
        teacher.addProperty("section",section);
        teacher.addProperty("location",location);
        teacher.addProperty("department_id",departmentId);


        Ion.with(this)
                .load("POST","https://electricveng.herokuapp.com/api/teacher")
                .setJsonObjectBody(teacher)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        String mDeptName="";
                        for (int i=0;i<departmentEntities.size();i++){
                            if(departmentId==departmentEntities.get(i).getId()){
                                mDeptName = departmentEntities.get(i).getName();
                            }
                        }
                        Intent intent = new Intent(getApplicationContext(),TeacherActivity.class);
                        intent.putExtra("departmentId",departmentId);
                        intent.putExtra("departmentName",mDeptName);
                        startActivity(intent);
                        finish();
                    }
                });


    }

    private void getTheDepartmentList() {
        Ion.with(this)
                .load("GET",MY_URL_BASE+"department")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
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

                                departmentEntities.add(departmentEntity);


                            }
                            teacherNewSubmitButton.setEnabled(true);


                        }catch (Exception exception){

                        }

                    }
                });
    }
}
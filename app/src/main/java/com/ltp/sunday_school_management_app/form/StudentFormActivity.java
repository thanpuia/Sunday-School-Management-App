package com.ltp.sunday_school_management_app.form;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.R;
import com.ltp.sunday_school_management_app.StudentActivity;
import com.ltp.sunday_school_management_app.TeacherActivity;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;
import com.ltp.sunday_school_management_app.entity.TeacherEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentFormActivity extends AppCompatActivity {

    EditText name;
    EditText dob;
    EditText fathersName;
    EditText mothersName;
    EditText guardianName;
    EditText phone1;
    EditText phone2;
    Button submitButton;
    EditText bial;
    EditText section;
    EditText location;
    Spinner departmentSpinner;
    Spinner teacherSpinner;

    ArrayList<DepartmentEntity> departmentEntities;
    ArrayList<TeacherEntity> teacherEntities;

    int teacherId;
    int departmentId;

    String tempo;
    CircleImageView circleImageView;
    static String MY_URL_BASE = "https://electricveng.herokuapp.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.root.sharedpreferences", this.MODE_PRIVATE);

        teacherId = sharedPreferences.getInt("teacherId",0);
        departmentId = sharedPreferences.getInt("departmentId",0);

        teacherEntities = new ArrayList<>();
        departmentEntities = new ArrayList<>();

        name = findViewById(R.id.name_student_et_new);
        dob = findViewById(R.id.dob_student_et_new);
        fathersName = findViewById(R.id.fathers_name_student_et_new);
        mothersName = findViewById(R.id.mothers_name_student_et_new);
        guardianName = findViewById(R.id.guardian_name_student_et_new);

        phone1 = findViewById(R.id.phone1_student_et_new);
        phone2 = findViewById(R.id.phone2_student_et_new);

        bial = findViewById(R.id.bial_name_student_et_new);
        section = findViewById(R.id.section_name_student_et_new);
        location = findViewById(R.id.location_name_student_et_new);

        circleImageView = findViewById(R.id.picture_student_et_new);
        submitButton = findViewById(R.id.student_submit_new);

        getTheDepartmentList();

    }

    public void studentSubmitClick(View view) {
        String name,dob,phone1,phone2,fathersName,mothersName,guardian
                ,bial,section,location,circleImageView;
        name = this.name.getText().toString();
        fathersName = this.fathersName.getText().toString();
        mothersName = this.mothersName.getText().toString();
        guardian = this.guardianName.getText().toString();
        dob = this.dob.getText().toString();
        phone1 = this.phone1.getText().toString();
        phone2 = this.phone2.getText().toString();

        bial = this.bial.getText().toString();
        section = this.section.getText().toString();
        location = this.location.getText().toString() ;

        JsonObject student = new JsonObject();
        student.addProperty("name",name);
        student.addProperty("dob",dob);
        student.addProperty("phone1",phone1);
        student.addProperty("phone2",phone2);
        student.addProperty("fathers_name",fathersName);
        student.addProperty("mothers_name",mothersName);
        student.addProperty("guardian",guardian);
        student.addProperty("bial",bial);
        student.addProperty("section",section);
        student.addProperty("location",location);
        student.addProperty("department_id",departmentId);
        student.addProperty("teacher_id",teacherId);

        Ion.with(this)
                .load("POST","https://electricveng.herokuapp.com/api/student")
                .setJsonObjectBody(student)
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
                        Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
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
                            submitButton.setEnabled(true);


                        }catch (Exception exception){

                        }

                    }
                });
    }
}
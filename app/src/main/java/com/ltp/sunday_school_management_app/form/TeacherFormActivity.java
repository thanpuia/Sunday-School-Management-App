package com.ltp.sunday_school_management_app.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.MainActivity;
import com.ltp.sunday_school_management_app.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherFormActivity extends AppCompatActivity {

    EditText name;
    EditText dob;
    EditText phone;
    EditText bial;
    EditText section;
    EditText location;
    Spinner department;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        name = findViewById(R.id.teacher_name_et);
        dob = findViewById(R.id.dob_et);
        phone = findViewById(R.id.phone_et);
        bial = findViewById(R.id.bial_et);
        section = findViewById(R.id.section_et);
        location = findViewById(R.id.location_et);
        department = findViewById(R.id.department_spinner);
        circleImageView = findViewById(R.id.teacher_picture_circle);


    }

    public void teacherSubmitClick(View view) {
        String name,dob,phone,bial,section,location,circleImageView;
        int department;

        name = this.name.getText().toString();
        dob = this.dob.getText().toString();
        phone = this.phone.getText().toString();
        bial = this.bial.getText().toString();
        section = this.section.getText().toString();
        location = this.location.getText().toString() ;
        //department = this.department.getVa

        JsonObject teacher = new JsonObject();
        teacher.addProperty("name",name);
        teacher.addProperty("dob",dob);
        teacher.addProperty("phone",phone);
        teacher.addProperty("bial",bial);
        teacher.addProperty("section",section);
        teacher.addProperty("location",location);
        //teacher.addProperty("departmentId",location);


        Ion.with(this)
                .load("POST","http://192.168.29.159:88/api/teacher")
                .setJsonObjectBody(teacher)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                    }
                });


    }
}
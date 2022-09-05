package com.ltp.sunday_school_management_app.form;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.R;
import com.ltp.sunday_school_management_app.TeacherActivity;
import com.ltp.sunday_school_management_app.adapter.DepartmentAdapter;
import com.ltp.sunday_school_management_app.adapter.RecyclerItemClickListener;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherEditActivity extends AppCompatActivity {
    EditText name;
    EditText dob;
    EditText phone;
    EditText bial;
    EditText section;
    EditText location;
    Spinner department;
    CircleImageView circleImageView;
    int teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit);

        teacherId = getIntent().getIntExtra("teacherId",0);

        name = findViewById(R.id.teacher_name_et_edit);
        dob = findViewById(R.id.dob_et_edit);
        phone = findViewById(R.id.phone_et_edit);
        bial = findViewById(R.id.bial_et_edit);
        section = findViewById(R.id.section_et_edit);
        location = findViewById(R.id.location_et_edit);
        department = findViewById(R.id.department_spinner_edit);
        circleImageView = findViewById(R.id.teacher_picture_circle_edit);

        fillTheForm(teacherId);


    }

    public void teacherSubmitEditClick(View view) {

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

        Ion.with(this)
                .load("PUT","http://192.168.29.159:88/api/teacher/"+teacherId)
//                .setJsonObjectBody(teacher)
                .setBodyParameter("name",name)
                .setBodyParameter("dob",dob)
                .setBodyParameter("phone", phone)

                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        startActivity(new Intent(getApplicationContext(),TeacherActivity.class));
                    }
                });

    }

    public void fillTheForm(int teacherId){
        Ion.with(this)
                .load("GET","http://192.168.29.159:88/api/teacher/"+teacherId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(result));
                            JSONObject myData = jsonObject.getJSONObject("data");

                            name.setText(myData.get("name").toString());
                            dob.setText(myData.get("dob").toString());
                            phone.setText(myData.get("phone").toString());
                            bial.setText(myData.get("bial").toString());
                            section.setText(myData.get("section").toString());
                            location.setText(myData.get("location").toString());

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                });
    }
}
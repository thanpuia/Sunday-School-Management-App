package com.ltp.sunday_school_management_app.form;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.Part;
import com.koushikdutta.async.http.body.StringPart;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.R;
import com.ltp.sunday_school_management_app.TeacherActivity;
import com.ltp.sunday_school_management_app.adapter.DepartmentAdapter;
import com.ltp.sunday_school_management_app.adapter.RecyclerItemClickListener;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherEditActivity extends AppCompatActivity {
    EditText name;
    EditText dob;
    EditText phone;
    EditText bial;
    EditText section;
    EditText location;
    Spinner departmentSpinner;
    CircleImageView circleImageView;
    int teacherId;
    int departmentId;
    int mDepartmentId;

    static String MY_URL_BASE = "https://electricveng.herokuapp.com/api/";
    ArrayList<DepartmentEntity> departmentList;
    List<String> deptName;
    ArrayAdapter<String> adapter;
    String nameString,dobString,phoneString,bialString,sectionString,locationString,circleImageViewString, deptNameString;
    String myDeptName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_edit);

        myDeptName ="";
        departmentList = new ArrayList<>();
        deptName = new ArrayList<>();
        mDepartmentId=0;
        teacherId = getIntent().getIntExtra("teacherId",0);
        departmentId = getIntent().getIntExtra("departmentId",0);

        name = findViewById(R.id.teacher_name_et_edit);
        dob = findViewById(R.id.dob_et_edit);
        phone = findViewById(R.id.phone_et_edit);
        bial = findViewById(R.id.bial_et_edit);
        section = findViewById(R.id.section_et_edit);
        location = findViewById(R.id.location_et_edit);
        departmentSpinner = findViewById(R.id.department_spinner_edit);
        circleImageView = findViewById(R.id.teacher_picture_circle_edit);

        getTheDepartmentList(teacherId);


    }

    public void teacherSubmitEditClick(View view) {

        nameString = this.name.getText().toString();
        dobString = this.dob.getText().toString();
        phoneString = this.phone.getText().toString();
        bialString = this.bial.getText().toString();
        sectionString = this.section.getText().toString();
        locationString = this.location.getText().toString() ;
        deptNameString = this.departmentSpinner.getSelectedItem().toString();


        for(int i=0;i<departmentList.size();i++){
            if(deptNameString == departmentList.get(i).getName())
                mDepartmentId = departmentList.get(i).getId();

        }

        if(mDepartmentId!=departmentId){
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setMessage("I Naupang te chu zirtirtu nei loh ah dah an ni dawn").setTitle("Department i thlak duh tak tak em?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateTeacher();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else{
            updateTeacher();

        }



    }

        private void updateTeacher(){


            JsonObject teacher = new JsonObject();
            teacher.addProperty("name",nameString);
            teacher.addProperty("dob",dobString);
            teacher.addProperty("phone",phoneString);
            teacher.addProperty("bial",bialString);
            teacher.addProperty("section",sectionString);
            teacher.addProperty("location",locationString);
            teacher.addProperty("department_id",mDepartmentId);

            Ion.with(getApplicationContext())
                    .load("PUT","https://electricveng.herokuapp.com/api/teacher/"+teacherId)
//                .setJsonObjectBody(teacher)
                    .addHeader("Accept","application/json")
                    .setHeader("Content-Type", "application/json")
                    //.addMultipartParts(parts)
                    .setJsonObjectBody(teacher)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Intent intent = new Intent(getApplicationContext(),TeacherActivity.class);
                            intent.putExtra("departmentId",departmentId);
                            intent.putExtra("departmentName",myDeptName);
                            startActivity(intent);
                            finish();
                        }
                    });
        }

        private void getTheDepartmentList(int teacherId) {
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

                                    departmentList.add(departmentEntity);


                                }
                                for (int j=0;j<departmentList.size();j++){
                                    deptName.add(departmentList.get(j).getName());
                                }

                                adapter = new ArrayAdapter<String>(
                                        getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,deptName);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                departmentSpinner.setAdapter(adapter);

                                fillTheForm(teacherId);


                            }catch (Exception exception){

                            }

                        }
                    });
        }
        public void fillTheForm(int teacherId){
            Ion.with(this)
                    .load("GET","https://electricveng.herokuapp.com/api/teacher/"+teacherId)
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

                                int departmentId = myData.getInt("department_id");
                                for(int i=0;i<departmentList.size();i++){
                                    if(departmentId==departmentList.get(i).getId()){
                                        myDeptName=departmentList.get(i).getName();
                                    }
                                }

                                departmentSpinner.setSelection(adapter.getPosition(myDeptName));

                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }

                        }
                    });
        }




}
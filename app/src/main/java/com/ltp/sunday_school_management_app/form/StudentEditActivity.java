package com.ltp.sunday_school_management_app.form;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ltp.sunday_school_management_app.R;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;
import com.ltp.sunday_school_management_app.entity.TeacherEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentEditActivity extends AppCompatActivity {

    EditText name;
    EditText dob;
    EditText fathersName;
    EditText mothersName;
    EditText guardianName;
    EditText phone1;
    EditText phone2;

    EditText bial;
    EditText section;
    EditText location;
    Spinner departmentSpinner;
    Spinner teacherSpinner;

    CircleImageView circleImageView;
    int teacherId;
    int studentId;
    int departmentId;
    int mDepartmentId;

    static String MY_URL_BASE = "http://192.168.29.159:88/api/";
    ArrayList<DepartmentEntity> departmentList;
    ArrayList<TeacherEntity> teacherList;

    List<String> deptNameSimpleList;
    List<String> teacherNameSimpleList;

    ArrayAdapter<String> departmentAdapter;
    ArrayAdapter<String> teacherAdapter;

    String nameString,dobString,phone1String,phone2String,mothersNameString,fathersNameString,guardianNameString,
            bialString,sectionString,locationString,circleImageViewString, deptNameString;
    String myDeptName;
    String myTeachName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);

        studentId =getIntent().getIntExtra("studentId",0);

        myDeptName ="";
        myTeachName ="";

        departmentList = new ArrayList<>();
        teacherList = new ArrayList<>();

        deptNameSimpleList = new ArrayList<>();
        teacherNameSimpleList = new ArrayList<>();

        mDepartmentId=0;
        teacherId = getIntent().getIntExtra("teacherId",0);
        departmentId = getIntent().getIntExtra("departmentId",0);

        name = findViewById(R.id.student_name_et_edit_student);
        dob = findViewById(R.id.dob_et_edit_student);
        fathersName = findViewById(R.id.fathers_name_edit);
        mothersName = findViewById(R.id.mothers_name_edit);
        guardianName = findViewById(R.id.guardina_edit);

        phone1 = findViewById(R.id.phone1_et_edit_student);
        phone2 = findViewById(R.id.phone2_et_edit_student);

        bial = findViewById(R.id.bial_et_edit_student);
        section = findViewById(R.id.section_et_edit_student);
        location = findViewById(R.id.location_et_edit_student);
        departmentSpinner = findViewById(R.id.department_spinner_edit_student);
        teacherSpinner = findViewById(R.id.teacher_spinner_edit_student);
        circleImageView = findViewById(R.id.student_picture_circle_edit_student);

        fillTheForm(studentId);
    }

    public void fillTheForm(int studentId){
        Ion.with(this)
                .load("GET",MY_URL_BASE+"student/"+studentId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(result));
                            JSONObject myStudent = jsonObject.getJSONObject("data");
                            JSONArray myDepartment = jsonObject.getJSONArray("department");
                            JSONArray myTeacher = jsonObject.getJSONArray("teacher");

                            name.setText(myStudent.get("name").toString());
                            dob.setText(myStudent.get("dob").toString());
                            phone1.setText(myStudent.get("phone1").toString());
                            phone2.setText(myStudent.get("phone2").toString());
                            fathersName.setText(myStudent.get("fathers_name").toString());
                            mothersName.setText(myStudent.get("mothers_name").toString());
                            guardianName.setText(myStudent.get("guardian").toString());
                            bial.setText(myStudent.get("bial").toString());
                            section.setText(myStudent.get("section").toString());
                            location.setText(myStudent.get("location").toString());

                            //SET UP DEPT
                            for (int i = 0; i < myDepartment.length(); i++) {
                                JSONObject department = myDepartment.getJSONObject(i);
                                String myDept = department.getString("name");
                                int myId = department.getInt("id");

                                DepartmentEntity departmentEntity = new DepartmentEntity();
                                departmentEntity.setName(myDept);
                                departmentEntity.setId(myId);

                                departmentList.add(departmentEntity);
                            }
                            //CONVERT TO LIST
                            for (int j=0;j<departmentList.size();j++){
                                deptNameSimpleList.add(departmentList.get(j).getName());
                            }
                            //USE THE ADAPTER
                            departmentAdapter = new ArrayAdapter<String>(
                                    getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,deptNameSimpleList);
                            departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            departmentSpinner.setAdapter(departmentAdapter);

                            //PRE-POPULATE DEPARTMENT
                            int departmentId = myStudent.getInt("department_id");
                            for(int i=0;i<departmentList.size();i++){
                                if(departmentId==departmentList.get(i).getId()){
                                    myDeptName=departmentList.get(i).getName();
                                }
                            }
                            departmentSpinner.setSelection(departmentAdapter.getPosition(myDeptName));


                            //SET UP TEACHER
                            for (int i = 0; i < myTeacher.length(); i++) {
                                JSONObject teacher = myTeacher.getJSONObject(i);
                                String myTeach = teacher.getString("name");
                                int myId = teacher.getInt("id");

                                TeacherEntity teacherEntity = new TeacherEntity();
                                teacherEntity.setName(myTeach);
                                teacherEntity.setId(myId);

                                teacherList.add(teacherEntity);
                            }

                            //CONVERT TO LIST
                            for (int j=0;j<teacherList.size();j++){
                                teacherNameSimpleList.add(teacherList.get(j).getName());
                            }
                            //USE THE ADAPTER
                            teacherAdapter = new ArrayAdapter<String>(
                                    getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,teacherNameSimpleList);
                            teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            teacherSpinner.setAdapter(teacherAdapter);

                            //PRE-POPULATE TEACHER
                            int mTeacherId = myStudent.getInt("teacher_id");
                            for(int i=0;i<teacherList.size();i++){
                                if(mTeacherId==teacherList.get(i).getId()){
                                    myTeachName=teacherList.get(i).getName();
                                }
                            }
                            teacherSpinner.setSelection(teacherAdapter.getPosition(myTeachName));

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                    }
                });
    }

    public void studentSubmitEditClick(View view) {
    }
}
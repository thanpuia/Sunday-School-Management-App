package com.ltp.sunday_school_management_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltp.sunday_school_management_app.R;
import com.ltp.sunday_school_management_app.entity.StudentEntity;
import com.ltp.sunday_school_management_app.form.StudentEditActivity;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    ArrayList<StudentEntity> studentEntities;
    Context context;
   // int departmentId;
    public StudentAdapter(ArrayList<StudentEntity> mStudentEntities, Context mContext){
        this.studentEntities = mStudentEntities;
        this.context = mContext;
        //this.departmentId = mDepartmentId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View frame = layoutInflater.inflate(R.layout.student_home,parent,false);
        ViewHolder viewHolder = new ViewHolder(frame);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(studentEntities.get(position).getName());

        holder.editStudentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.editStudentImageView);
                popupMenu.getMenuInflater().inflate(R.menu.teacher_popup,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {


                        Intent intent = new Intent(context, StudentEditActivity.class);
                        intent.putExtra("studentId",studentEntities.get(holder.getAdapterPosition()).getId());
                        //intent.putExtra("departmentId",departmentId);
                        view.getContext().startActivity(intent);

                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView editStudentImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.student_text);
            this.editStudentImageView = itemView.findViewById(R.id.edit_student_imageview);

        }
    }
}

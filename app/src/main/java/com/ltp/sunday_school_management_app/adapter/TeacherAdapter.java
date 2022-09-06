package com.ltp.sunday_school_management_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.ltp.sunday_school_management_app.TeacherActivity;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;
import com.ltp.sunday_school_management_app.entity.TeacherEntity;
import com.ltp.sunday_school_management_app.form.TeacherEditActivity;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {
    ArrayList<TeacherEntity> teacherEntities;
    Context context;
    int departmentId;
    public TeacherAdapter(ArrayList<TeacherEntity> mTeacherEntities, int mDepartmentId, Context mContext){
        this.teacherEntities = mTeacherEntities;
        this.context = mContext;
        this.departmentId = mDepartmentId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View frame = layoutInflater.inflate(R.layout.teacher_home,parent,false);
        ViewHolder viewHolder = new ViewHolder(frame);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(teacherEntities.get(position).getName());

        holder.editTeacherImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.editTeacherImageView);
                popupMenu.getMenuInflater().inflate(R.menu.teacher_popup,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {


                        Intent intent = new Intent(context, TeacherEditActivity.class);
                        intent.putExtra("teacherId",teacherEntities.get(holder.getAdapterPosition()).getId());
                        intent.putExtra("departmentId",departmentId);
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
        return teacherEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView editTeacherImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.teacher_text);
            this.editTeacherImageView = itemView.findViewById(R.id.edit_teacher_imageview);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
////                    int mId = teacherEntities.get(getAdapterPosition()).getId();
////                    Intent intent = new Intent(context, TeacherActivity.class);
////                    intent.putExtra("departmentId",mId);
////
////                    context.startActivity(intent);
//                }
//            });
        }
    }
}

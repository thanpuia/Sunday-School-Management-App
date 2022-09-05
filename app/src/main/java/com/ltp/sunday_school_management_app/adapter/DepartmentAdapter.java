package com.ltp.sunday_school_management_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ltp.sunday_school_management_app.R;
import com.ltp.sunday_school_management_app.TeacherActivity;
import com.ltp.sunday_school_management_app.entity.DepartmentEntity;

import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> implements View.OnClickListener {
    ArrayList<DepartmentEntity> departmentEntities;
    Context context;

    public DepartmentAdapter(ArrayList<DepartmentEntity> mDepartmentEntities, Context mContext){
        this.departmentEntities = mDepartmentEntities;
        this.context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View frame = layoutInflater.inflate(R.layout.department_home,parent,false);
        ViewHolder viewHolder = new ViewHolder(frame);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(departmentEntities.get(position).getName());
        int[] myBgResources = new int[]{R.drawable.bg1,R.drawable.bg2,R.drawable.bg3,R.drawable.bg4,
                R.drawable.bg5,R.drawable.bg6,R.drawable.bg7,R.drawable.bg8};
        holder.cardView.setBackgroundResource(myBgResources[position]);
    }

    @Override
    public int getItemCount() {
        return departmentEntities.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.department_text);
            this.cardView = itemView.findViewById(R.id.card_view);
        }
    }
}

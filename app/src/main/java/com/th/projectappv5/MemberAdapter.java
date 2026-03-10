package com.th.projectappv5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    private ArrayList<Member> memberArrayList;
    private OnMemberListener mOnMemberListener;
    public MemberAdapter(ArrayList<Member> memberArrayList, OnMemberListener onMemberListener) {
        this.memberArrayList = memberArrayList;
        this.mOnMemberListener = onMemberListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_rv_item, parent, false);
        return new ViewHolder(view,mOnMemberListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member modal = memberArrayList.get(position);
        holder.idTVMemberName.setText(modal.getNames());
        holder.idTVTel.setText(modal.getTel());
        holder.idTVEmail.setText(modal.getEmail());
    }

    @Override
    public int getItemCount() {
        return memberArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView idTVMemberName, idTVTel, idTVEmail;
        OnMemberListener onMemberListener;

        public ViewHolder(@NonNull View itemView,OnMemberListener onMemberListener) {
            super(itemView);
            idTVMemberName = itemView.findViewById(R.id.idTVMemberName);
            idTVTel = itemView.findViewById(R.id.idTVTel);
            idTVEmail = itemView.findViewById(R.id.idTVEmail);
            this.onMemberListener = onMemberListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onMemberListener.onMemberClick(getAdapterPosition());
        }

    }
    public interface OnMemberListener {
        void onMemberClick(int position);
    }
}
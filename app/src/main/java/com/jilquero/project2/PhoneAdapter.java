package com.jilquero.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private List<Phone> mListPhones;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mLayoutInflater;
    interface OnItemClickListener
    {
        void onItemClickListener(Phone item);
    }
    public PhoneAdapter(Context context){
        mOnItemClickListener = (OnItemClickListener) context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mListPhones = null;
    }
    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.phone, parent, false);
        return new PhoneViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        holder.TVManufacturer.setText(mListPhones.get(position).getManufacturer());
        holder.TVModel.setText(mListPhones.get(position).getModel());
    }
    @Override
    public int getItemCount(){
        if(mListPhones != null)
            return mListPhones.size();
        return 0;
    }
    public void setListPhones(List<Phone> itemList){
        this.mListPhones = itemList;
        notifyDataSetChanged();
    }
    public class PhoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView TVManufacturer;
        public TextView TVModel;
        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            TVManufacturer=itemView.findViewById(R.id.TVManufacturer);
            TVModel=itemView.findViewById(R.id.TVModel);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            int position = this.getAdapterPosition();
            mOnItemClickListener.onItemClickListener(mListPhones.get(position));
        }
    }

}

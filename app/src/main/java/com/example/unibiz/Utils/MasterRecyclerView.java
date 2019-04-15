package com.example.unibiz.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.unibiz.Model.Employe;
import com.example.unibiz.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MasterRecyclerView extends RecyclerView.Adapter<MasterRecyclerView.ViewHolder> {


    private List<Employe> mData;
    private LayoutInflater mInflater;

    public MasterRecyclerView(Context context, List<Employe> data) {
        mData = data;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MasterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_master, parent, false);

        return new MasterRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterRecyclerView.ViewHolder holder, int position) {
        Employe employe = mData.get(position);
        holder.bind(employe);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Employe> data) {
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView MasterName, MasterJob,MasterPhone;
        CircleImageView MasterImage;
        private Employe mEmploye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MasterName = itemView.findViewById(R.id.list_item_master_name);
            MasterJob = itemView.findViewById(R.id.list_item_master_position);
            MasterPhone = itemView.findViewById(R.id.list_item_master_nomer);
            MasterImage = itemView.findViewById(R.id.list_item_master_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

         void bind(Employe employe) {
            mEmploye = employe;
            MasterName.setText(mEmploye.getName());
            MasterJob.setText(mEmploye.getJob());
            MasterPhone.setText((mEmploye.getNomer()));
            MasterImage.setImageBitmap(getBitmap());

        }

        private Bitmap getBitmap() {
            return BitmapFactory.decodeByteArray(mEmploye.getImage(), 0, mEmploye.getImage().length);
        }
    }
}

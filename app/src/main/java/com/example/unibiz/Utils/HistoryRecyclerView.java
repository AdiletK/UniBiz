package com.example.unibiz.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.unibiz.DB.DatabaseOperation;
import com.example.unibiz.Model.Category;
import com.example.unibiz.Model.Client;
import com.example.unibiz.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryRecyclerView extends RecyclerView.Adapter<HistoryRecyclerView.ViewHolder> {
    private List<Client> mData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ClientRecyclerView.ItemClickListener mClickListener;

    public HistoryRecyclerView(Context inflater, List<Client> data) {
        mData = data;
        mInflater =LayoutInflater.from(inflater);
        mContext = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_history_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mData.get(position).getName();

        DatabaseOperation category = DatabaseOperation.get(mContext);
        Category selected = category.getCategory(mData.get(position).getId_category());
        Bitmap bitmap = BitmapFactory.decodeByteArray(selected.getImage(), 0, selected.getImage().length);


        holder.name.setText(name);
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Client> data) {
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        CircleImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_of_client);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}

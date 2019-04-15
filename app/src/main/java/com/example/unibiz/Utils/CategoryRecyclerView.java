package com.example.unibiz.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.unibiz.Model.Category;
import com.example.unibiz.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryRecyclerView extends RecyclerView.Adapter<CategoryRecyclerView.ViewHolder> {


    private List<Category> mData;
    private LayoutInflater mInflater;

    public CategoryRecyclerView(Context context, List<Category> data) {
        mData = data;
        mInflater =LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_category,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mData.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Category> data) {
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView CategoryTitle,CategoryPrice;
        CircleImageView CategoryImage;
        private Category mCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryTitle = itemView.findViewById(R.id.list_item_category_title);
            CategoryPrice = itemView.findViewById(R.id.list_item_category_price);
            CategoryImage = itemView.findViewById(R.id.list_item_category_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
        public void bind(Category category){
            mCategory = category;
            CategoryTitle.setText(mCategory.getName());
            CategoryPrice.setText(String.valueOf(mCategory.getPrice()));
            CategoryImage.setImageBitmap(getBitmap());

        }
        private Bitmap getBitmap() {
            return BitmapFactory.decodeByteArray(mCategory.getImage(), 0, mCategory.getImage().length);
        }

    }
}

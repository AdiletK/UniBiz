package com.example.unibiz.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unibiz.DB.DatabaseOperation;
import com.example.unibiz.MainActivity;
import com.example.unibiz.Model.Category;
import com.example.unibiz.Model.Client;
import com.example.unibiz.ProfileActivity;
import com.example.unibiz.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;
import rm.com.longpresspopup.PopupInflaterListener;
import rm.com.longpresspopup.PopupStateListener;

public class ClientRecyclerView extends RecyclerView.Adapter< ClientRecyclerView.ViewHolder> {

    private List<Client> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private String mActivity;


    // data is passed into the constructor
    public ClientRecyclerView(Context context, List<Client> data,LayoutInflater inflater,String activity) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mContext =context;
        mActivity = activity;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_client, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Client client = mData.get(position);
        holder.bind(client);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Client> data) {
        mData = data;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements PopupInflaterListener, View.OnClickListener, PopupStateListener {
        TextView name,date,time,Title;
        CircleImageView mCircleImageView,QuickViewImage;
        MaterialButton deleteBtn,MoreBtn;
        private Client mClient;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            mCircleImageView =itemView.findViewById(R.id.profile_image);
            itemView.setOnClickListener(this);
            LongPressPopup popup = new LongPressPopupBuilder(mContext)// A Context object for the builder constructor
                    .setTarget(itemView)// The View which will open the popup if long pressed
                    .setPopupView(R.layout.dialog_quick_view, this)// The View to show when long pressed
                    .setAnimationType(LongPressPopup.ANIMATION_TYPE_FROM_CENTER)
                    .setLongPressReleaseListener(this)
                    .setPopupListener(this)
                    .build();// This will give you a LongPressPopup object
            popup.register();
        }

        void bind(Client client){
            mClient = client;
            String name1 = mClient.getName();
            String nomer = mClient.getNomer();


            Bitmap bitmap = getBitmap();

            name.setText(name1);
            date.setText(nomer);
            time.setText(mClient.getVisitDate());
            mCircleImageView.setImageBitmap(bitmap);
        }

        private Bitmap getBitmap() {
            DatabaseOperation category = DatabaseOperation.get(mContext);
            Category selected = category.getCategory(mClient.getId_category());
            return BitmapFactory.decodeByteArray(selected.getImage(), 0, selected.getImage().length);
        }

        @Override
        public void onViewInflated(@Nullable String popupTag, View root) {
            //Layout
            Title = root.findViewById(R.id.dialog_quickview_title_txt);
            QuickViewImage = root.findViewById(R.id.quickview_prof_image);
            deleteBtn = root.findViewById(R.id.quick_view_delete_btn);
            MoreBtn = root.findViewById(R.id.quick_view_more_btn);
        }

        @Override
        public void onClick(View v) {

            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
            if (deleteBtn!=null && v.getId()==deleteBtn.getId()){
                alertDialog();
            }
            else if (MoreBtn!=null && v.getId()==MoreBtn.getId()){
                Toast.makeText(mContext, "More!", Toast.LENGTH_SHORT).show();
            }
            else {
              Intent intent = ProfileActivity.Companion.newIntent(mContext,mClient.getId());
               mContext.startActivity(intent);
            }
        }

        private void alertDialog() {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
            builder1.setMessage(mContext.getString(R.string.delete_msg) +mClient.getName());
            builder1.setTitle(mContext.getString(R.string.delete));
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    mContext.getString(R.string.option_yes),
                    (dialog, which) -> {
                        DatabaseOperation.get(mContext)
                                .delete(mClient);
                        if (mActivity!=null && mActivity.equals(Messages.mainactivity)){
                            ((MainActivity) mContext).updateUI();
                        }
                    });
                    builder1.setNegativeButton(
                            mContext.getString(R.string.option_no),
                            (dialog, id) -> dialog.cancel());
            builder1.setIcon(R.drawable.ic_delete_forever_black_24dp);

            AlertDialog alert11 = builder1.create();
            alert11.show();


        }


        @Override
        public void onPopupShow(@Nullable String popupTag) {
            Title.setText(mClient.getName());
            Bitmap bitmap = getBitmap();
            QuickViewImage.setImageBitmap(bitmap);
        }

        @Override
        public void onPopupDismiss(@Nullable String popupTag) {
        }
    }




    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

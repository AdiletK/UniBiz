package com.example.unibiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.unibiz.CalendarItems.DynamicFragment;
import com.example.unibiz.DB.CreationDataBase;
import com.example.unibiz.DB.DatabaseOperation;
import com.example.unibiz.Model.Client;
import com.example.unibiz.Utils.ClientRecyclerView;
import com.example.unibiz.Utils.HistoryRecyclerView;
import com.example.unibiz.Utils.Messages;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout OrderLayout,ClientLayout,MastersLayout,CategorysLayout;
    private Animation mShowButton ;
    private Animation mHideButton ;
    private Animation mHideLayout ;
    private Animation mShowLayout ;
    private FloatingActionButton floatBtn;
    private ClientRecyclerView mClientRecyclerViewAdapter;

    private RelativeLayout mMainLayout,Layout;
    private DrawerLayout mDrawerLayout;
    private SQLiteDatabase mDataBase;


    private int NewProductActivity_Request_code = 222;
    private int NewMasterActivty_Request_code = 223;
    private int RESULT_FOR_ADD = 224;
    private int RESULT_FOR_ADD_CLIENT = 225;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initFloatButton();
        updateUI();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.icon_for_menu);

    }


    private void initComponents() {
        //Creation db
        CreationDataBase dataBase = new CreationDataBase(this);
        mDataBase =  dataBase.getWritableDatabase();
        //getData

        OrderLayout = findViewById(R.id.order_layout);
        ClientLayout = findViewById(R.id.client_layout);
        MastersLayout = findViewById(R.id.master_layout);
        CategorysLayout = findViewById(R.id.category_layout);

        mMainLayout = findViewById(R.id.main_layout);

        NavigationView navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(this);

//        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (scrollY > oldScrollY) {
//                if (OrderLayout.getVisibility() == View.VISIBLE | ClientLayout.getVisibility() == View.VISIBLE) {
//                    setup_float_method(View.GONE, mHideLayout, mHideButton, R.mipmap.floating_button);
//                }
//                floatBtn.hide();
//            } else {
//                floatBtn.show();
//            }
//        });
    }


    @SuppressLint("StaticFieldLeak")
    public void updateUI() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Date mDate =new GregorianCalendar(year, month, day).getTime();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = DynamicFragment.Companion.newInstance(mDate, true, Messages.client);
        fm.beginTransaction()
                .replace(R.id.clients_rec, fragment)
                .commit();


    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initFloatButton() {
        floatBtn = findViewById(R.id.floatingActionButton);
        FloatingActionButton mOrderBtn = findViewById(R.id.floatingActionButton_order);
        FloatingActionButton mClientBtn = findViewById(R.id.floatingActionButton_client);
        FloatingActionButton mNewMaster = findViewById(R.id.floatingActionButton_master);
        FloatingActionButton mNewCategory = findViewById(R.id.floatingActionButton_category);

        mShowButton = AnimationUtils.loadAnimation(MainActivity.this,R.anim.show_button);
        mHideButton = AnimationUtils.loadAnimation(MainActivity.this,R.anim.hide_button);
        mHideLayout = AnimationUtils.loadAnimation(MainActivity.this,R.anim.hide_layout);
        mShowLayout = AnimationUtils.loadAnimation(MainActivity.this,R.anim.show_layout);

        floatBtn.setOnClickListener(this);
        mOrderBtn.setOnClickListener(this);
        mClientBtn.setOnClickListener(this);
        mNewCategory.setOnClickListener(this);
        mNewMaster.setOnClickListener(this);

        mDrawerLayout = findViewById(R.id.drawer_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingActionButton_client:
                openDialogClient();
                handlerMethod();
                return;
            case R.id.floatingActionButton_order:
                //open Product dialog

                    Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                    startActivityForResult(intent,NewProductActivity_Request_code);

                handlerMethod();
                return;
            case R.id.floatingActionButton_master:
                Intent intent1 = new Intent(MainActivity.this, NewMasterActivty.class);
                startActivityForResult(intent1,NewMasterActivty_Request_code);
                handlerMethod();
                return;
            case R.id.floatingActionButton_category:
                openCategoryDialog();
                handlerMethod();

                return;
            case R.id.floatingActionButton:
                if (OrderLayout.getVisibility()==View.VISIBLE && ClientLayout.getVisibility()==View.VISIBLE){
                    setup_float_method(View.GONE, mHideLayout, mHideButton, R.mipmap.floating_button);

                }else {
                    setup_float_method(View.VISIBLE, mShowLayout, mShowButton, R.mipmap.changed_bg_float);
                }

        }
    }

    private void handlerMethod() {
        final Handler handler = new Handler();
        if (OrderLayout.getVisibility()==View.VISIBLE && ClientLayout.getVisibility()==View.VISIBLE) {
            handler.postDelayed(() -> setup_float_method(View.GONE, mHideLayout, mHideButton, R.mipmap.floating_button), 200);
        }
    }

    private void setup_float_method(int gone, Animation hideLayout, Animation hideButton, int p) {
        OrderLayout.setVisibility(gone);
        ClientLayout.setVisibility(gone);
        MastersLayout.setVisibility(gone);
        CategorysLayout.setVisibility(gone);
        ClientLayout.startAnimation(hideLayout);
        OrderLayout.startAnimation(hideLayout);
        MastersLayout.startAnimation(hideLayout);
        CategorysLayout.startAnimation(hideLayout);
        floatBtn.startAnimation(hideButton);
        floatBtn.setImageResource(p);
    }

    private void openDialogClient() {
        Intent clientIntent = new Intent(MainActivity.this, NewClientActivity.class);
        startActivity(clientIntent);
    }
    private void openCategoryDialog(){
        NewCategoryDialog.Companion.display(getSupportFragmentManager());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case   R.id.menu_add:
                startAddActivity();
                return true;
            case R.id.menu_all_data:
                 startAllDataIntent();
                return true;
            case R.id.menu_calendar:
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
                closeDrawer();
                return true;
            case R.id.menu_reporting:
                startActivity(new Intent(this,ReportActivity.class));
                closeDrawer();
                return true;
            case R.id.menu_setting:
                closeDrawer();
                return true;
            case R.id.menu_about:
                AboutDialog.Companion.display(getSupportFragmentManager());
                closeDrawer();
                return true;
            default:
                closeDrawer();
        }
        closeDrawer();
        return false;
    }

    private void startAllDataIntent() {
        Intent alldataIntent = new Intent(MainActivity.this, AllDataActivity.class);
        startActivity(alldataIntent);
        closeDrawer();
    }

    private void startAddActivity() {
        Intent addIntent = new Intent(MainActivity.this, AddThingsActivity.class);
        startActivity(addIntent);
        closeDrawer();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                handlerMethod();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("RtlHardcoded")
    public void closeDrawer(){

        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_FOR_ADD_CLIENT){
            if (resultCode==RESULT_OK){
                updateUI();
                Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

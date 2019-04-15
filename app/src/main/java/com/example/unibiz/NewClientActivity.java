package com.example.unibiz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.unibiz.DB.DatabaseOperation;
import com.example.unibiz.Model.Category;
import com.example.unibiz.Model.Client;
import com.example.unibiz.Model.Employe;
import com.example.unibiz.Model.Items;
import com.example.unibiz.Utils.ScannerUtils.BarcodeCaptureActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewClientActivity extends AppCompatActivity  implements View.OnClickListener {
    private static final String TAG = "example_dialog";
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final int PERMISSION_REQUEST_CODE = 9002;
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    private ProgressBar mProgressBar;

    private Toolbar toolbar;
    private TextInputEditText mtxtName,mtxtPrice,txtPhone,txtImei,txtItem;
    private CircleImageView mProfileImage;
    private Spinner mCategorySpinner,mMasterSpinner;
    private Button btn_Time,btn_Date;
    private Date mDate = new Date();

    private List<TextInputEditText> allEds = new ArrayList<>();
    private int Items_count = 1;
    private UUID id_empl,id_categ;
    private LinearLayout items_layout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        initComponents();
        setupDateandTimeButtons();

    }



    private void setupDateandTimeButtons() {
        Calendar calendar = Calendar.getInstance();
        int startYear = calendar.get(Calendar.YEAR);
        int starthMonth =  calendar.get(Calendar.MONTH);
        int startDay =  calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay =calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mDate= new GregorianCalendar(startYear,starthMonth,startDay,hourOfDay,minute).getTime();
        setupDataText(mDate);
        setupTimeText(mDate);
    }

    private void initComponents( ) {

        toolbar= findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        //Input texts
        mtxtName = findViewById(R.id.txt_client_name);
        mtxtPrice =findViewById(R.id.txt_client_price);
        txtPhone = findViewById(R.id.txt_client_phone);
        txtImei =  findViewById(R.id.txt_client_imei);
        txtItem =  findViewById(R.id.txt_client_item_1);

        //ImageView
        mProfileImage = findViewById(R.id.profile_image);
        //Buttons
        ImageButton btnIncrease = findViewById(R.id.btn_imcrease);
        ImageButton btnDecrease = findViewById(R.id.btn_decrease);
        btnIncrease.setOnClickListener(this);
        btnDecrease.setOnClickListener(this);

        btn_Date = findViewById(R.id.btn_client_date);
        btn_Time = findViewById(R.id.btn_client_time);

        btn_Date.setOnClickListener(this);
        btn_Time.setOnClickListener(this);

        ImageButton btnCall = findViewById(R.id.new_client_call_btn);
        ImageButton btnSms = findViewById(R.id.new_client_sms_btn);

        btnCall.setOnClickListener(this);
        btnSms.setOnClickListener(this);

        MaterialButton btnCreateNewItem = findViewById(R.id.btn_create_new_item);
        MaterialButton btnDeleteItem = findViewById(R.id.btn_delete);

        btnCreateNewItem.setOnClickListener(this);
        btnDeleteItem.setOnClickListener(this);

        ImageButton mbtnGetInfoFromContactList = findViewById(R.id.btn_get_info_from_contact_list);
        mbtnGetInfoFromContactList.setOnClickListener(this);
        //floating button
        FloatingActionButton fbtnScaner = findViewById(R.id.client_scaner_btn);
        FloatingActionButton fbtnSave = findViewById(R.id.client_save_btn);

        fbtnScaner.setOnClickListener(this);
        fbtnSave.setOnClickListener(this);

        //spinners
        mCategorySpinner = findViewById(R.id.spinner_category);
        mMasterSpinner = findViewById(R.id.spinner_master);


        items_layout = findViewById(R.id.extra_items_layout);

        //Progress bar
        mProgressBar = findViewById(R.id.product_progress);

        //Get info from db about Masters and Category
        setupMasterSpinner();
        setupCategorySpinner();

    }


    private void add_new_item() {
        // add edittext
        Items_count ++;
        TextInputEditText et = new TextInputEditText(this);

        TableLayout.LayoutParams param = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT+80);
        param.setMargins(5, 15, 5, 0);

        allEds.add(et);
        et.setLayoutParams(param);
        et.setHint(getString(R.string.item_label)+Items_count);
        et.setId(Items_count);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(getResources().getColor(R.color.semi_gray));
        items_layout.addView(et);
    }
    private void remove_item() {
        if(Items_count==1){
            Toast.makeText(this,getString(R.string.nothing_to_delete),Toast.LENGTH_SHORT).show();
        }else {
            items_layout.removeViewAt(items_layout.getChildCount() - 1);
            Items_count--;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_new_item:
                add_new_item();
                break;
            case R.id.btn_delete:
                remove_item();
                break;
            case R.id.btn_imcrease:
                increasePrice();
                break;
            case R.id.btn_decrease:
                decreasePrice();
                break;
            case R.id.client_scaner_btn:
                startCamera();
                break;
            case R.id.new_client_call_btn:
                startCall();
                break;
            case R.id.new_client_sms_btn:
                startSMS();
                break;
            case R.id.btn_get_info_from_contact_list:
                startGetContact();
                break;
            case R.id.btn_client_date:
                startDatePicker();
                break;
            case R.id.btn_client_time:
                startTimePicker();
                break;
            case R.id.client_save_btn:

                        try {
                            saveInfo();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                break;
        }
    }

    private void saveInfo() throws ParseException {

        @SuppressLint("SimpleDateFormat") String sc =new SimpleDateFormat("yyyy-MM-dd").format(mDate);

        ProgressDialog dialog = ProgressDialog.show(NewClientActivity.this, "",
                "Loading. Please wait...", true);
        dialog.show();
        if (checkTextFields()) {
            Client new_client = new Client();
            UUID uuid = UUID.randomUUID();
            UUID it_id = UUID.randomUUID();
            new_client.setId(uuid);
            new_client.setId_item(uuid);
            new_client.setName(Objects.requireNonNull(mtxtName.getText()).toString());
            new_client.setPrice(Double.parseDouble(Objects.requireNonNull(mtxtPrice.getText()).toString()));
            new_client.setNomer(Objects.requireNonNull(txtPhone.getText()).toString());
            new_client.setId_empl(id_empl);
            new_client.setId_category(id_categ);
            new_client.setDate(new Date());
            new_client.setVisitDate(sc);
            new_client.setId_item(it_id);

            if (!TextUtils.isEmpty(txtImei.getText())){
                new_client.setImei(txtImei.getText().toString());
            }
            if (!TextUtils.isEmpty(txtItem.getText())){
                Items items = new Items();
                items.setId_UUID(it_id);
                items.setItem(txtItem.getText().toString());
            }
            if (Items_count>1){
                for (int i=1;i<Items_count;i++){
                    Items items = new Items();
                    items.setId_UUID(it_id);
                    items.setItem(allEds.get(i-1).getText().toString());
                }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DatabaseOperation insert = DatabaseOperation.get(NewClientActivity.this);
                    insert.add(new_client);
                }
            }).start();
            finish();
        }else {
            Toast.makeText(this,getString(R.string.msg_for_empty_texts),Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkTextFields() {
        return !TextUtils.isEmpty(mtxtName.getText()) & !TextUtils.isEmpty(mtxtPrice.getText()) &
                !TextUtils.isEmpty(txtPhone.getText());
    }

    private void startTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay =calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay1, minute1) -> {
            Date date = new Date(1,1,1,hourOfDay1,minute1);
            setupTimeText(date);
            mDate.setHours(hourOfDay1);
            mDate.setMinutes(minute1);
        },hourOfDay,minute,true);

        timePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void setupTimeText(Date date) {
        String s = DateFormat.getTimeInstance(
                DateFormat.SHORT).format(date);
        btn_Time.setText(s);
    }

    private void startDatePicker()  {


        Calendar calendar = Calendar.getInstance();
        int startYear = calendar.get(Calendar.YEAR);
        int starthMonth =  calendar.get(Calendar.MONTH);
        int startDay =  calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(
               this, (view, year, month, dayOfMonth) -> {
            mDate = new GregorianCalendar(year,month,dayOfMonth).getTime();

            setupDataText(mDate);
        }, startYear, starthMonth, startDay);
        dialog.show();
    }

    private void setupDataText(Date date) {
        String s = DateFormat.getDateInstance(
                DateFormat.MEDIUM).format(date);
        btn_Date.setText(s);
    }

    private void startGetContact() {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI),
                REQUEST_CODE_PICK_CONTACTS);

    }

    private void startSMS() {
        if (checkPermission()) {
            sendSMS();
        } else {
//If the app doesn’t have the SEND_SMS permission, request it//
            requestPermission();
        }
    }

    private void startCall() {
        if (checkPermission()) {
            call();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {

        int CallPermissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        return CallPermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]
                {
                        Manifest.permission.CALL_PHONE
                }, PERMISSION_REQUEST_CODE);

    }

    private void startCamera() {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    private void decreasePrice() {
        int koef =10;
        if (Double.parseDouble(Objects.requireNonNull(mtxtPrice.getText()).toString())>=koef){
            double price = Double.parseDouble(Objects.requireNonNull(mtxtPrice.getText()).toString());
            mtxtPrice.setText(String.valueOf(price-koef));
        }else {
            Toast.makeText(this,getString(R.string.msg_when_price_less_10),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void increasePrice() {
        double price = Double.parseDouble(Objects.requireNonNull(mtxtPrice.getText()).toString());
        int koef =10;
        mtxtPrice.setText(String.valueOf(price+koef));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //                    statusMessage.setText(R.string.barcode_failure);
                    txtImei.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
//                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            }
        }
        else if(requestCode==REQUEST_CODE_PICK_CONTACTS & resultCode==RESULT_OK){
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();
            retrieveContactName();
            retrieveContactNumber();
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean CallPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (CallPermission ) {
                        Toast.makeText(this,
                                getString(R.string.permission_accepted), Toast.LENGTH_LONG).show();

//If the permission is denied….//
                    } else {
                        Toast.makeText(this,
                                getString(R.string.permission_denied), Toast.LENGTH_LONG).show();
                    }
                    break;
                }
        }
    }

    private void call() {
        String phoneNum = Objects.requireNonNull(txtPhone.getText()).toString();
        if(!TextUtils.isEmpty(phoneNum)) {
            String dial = "tel:" + phoneNum;
            startActivity(new Intent(Intent.ACTION_DIAL,
                    Uri.parse(dial)));
        }else {
            Toast.makeText(this, getString(R.string.msg_for_empty_phone_text), Toast.LENGTH_SHORT).show();
        }

    }

    private void sendSMS(){
        // Use format with "smsto:" and phone number to create smsNumber.
        String phoneNum = Objects.requireNonNull(txtPhone.getText()).toString();
        if(!TextUtils.isEmpty(phoneNum)) {
            String smsNumber = String.format("smsto: %s",
                    phoneNum);
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse(smsNumber));
            // If package resolves (target app installed), send intent.
            if (smsIntent.resolveActivity(this.getPackageManager()) != null) {
                startActivity(smsIntent);
            } else {
                Log.d(TAG, "Can't resolve app for ACTION_SENDTO Intent");
            }
        }
        else {
            Toast.makeText(this, getString(R.string.msg_for_empty_phone_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = this.getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        assert cursorID != null;
        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
        txtPhone.setText(contactNumber);
    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = this.getContentResolver().query(uriContact, null, null, null, null);

        assert cursor != null;
        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);
        mtxtName.setText(contactName);

    }

    private void setupMasterSpinner(){
        DatabaseOperation masterLab = DatabaseOperation.get(this);
        List<Employe> employes = masterLab.getEmployes();
        final String[] emps = new String[employes.size()];
        for (int i=0;i<employes.size();i++){
            try {
                emps[i] = employes.get(i).getName();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mMasterSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,emps));
        mMasterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //something
                String name = emps[position];
                Employe employe = masterLab.getEmploye(name);
                id_empl = employe.getUUID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupCategorySpinner(){
        DatabaseOperation category = DatabaseOperation.get(this);
        List<Category> categories = category.getCategories();
        final String[] categs = new String[categories.size()];
        for (int i=0;i<categories.size();i++){
            try {
                categs[i] = categories.get(i).getName();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        mCategorySpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categs));
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //something
                String name = categs[position];
                Category selected = category.getCategory(name);
                id_categ = selected.getId();
                if (position==0){
                    mProfileImage.setImageResource(R.drawable.img_add_klient);
                    mtxtPrice.setText("0");

                }else {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(selected.getImage(), 0, selected.getImage().length);
                    mProfileImage.setImageBitmap(bitmap);
                    if (!TextUtils.isEmpty(selected.getPrice().toString())){
                        mtxtPrice.setText(String.valueOf(selected.getPrice()));
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

package no.nordicsemi.android.blinky;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ContactActivity extends AppCompatActivity {

    public int cValue = 0;
    public String personName1="";
    public String personPhone1="";
    public String personName2="";
    public String personPhone2="";
    public String personName3="";
    public String personPhone3="";
    public String personName4="";
    public String personPhone4="";
    public String personName5="";
    public String personPhone5="";
    public String personName6="";
    public String personPhone6="";

    Button bt21;
    Button bt22;
    Button bt23;
    Button bt24;
    Button bt25;
    Button bt26;

    ImageView civ1;
    ImageView civ2;
    ImageView civ3;
    ImageView civ4;
    ImageView civ5;
    ImageView civ6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_contact);

        bt21 = (Button) findViewById(R.id.bt21);
        bt22 = (Button) findViewById(R.id.bt22);
        bt23 = (Button) findViewById(R.id.bt23);
        bt24 = (Button) findViewById(R.id.bt24);
        bt25 = (Button) findViewById(R.id.bt25);
        bt26 = (Button) findViewById(R.id.bt26);

        civ1 = (ImageView) findViewById(R.id.civ1);
        civ2 = (ImageView) findViewById(R.id.civ2);
        civ3 = (ImageView) findViewById(R.id.civ3);
        civ4 = (ImageView) findViewById(R.id.civ4);
        civ5 = (ImageView) findViewById(R.id.civ5);
        civ6 = (ImageView) findViewById(R.id.civ6);

        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        personName1 = sharedPref.getString(getString(R.string.saved_p1_name), "");
        personPhone1 = sharedPref.getString(getString(R.string.saved_p1_phone), "");
        if(personName1.equals("")){
            civ1.setImageResource(R.drawable.p);
            bt21.setText("");
        } else {
            civ1.setImageResource(R.drawable.pp);
            bt21.setText(personName1);
        }

        personName2 = sharedPref.getString(getString(R.string.saved_p2_name), "");
        personPhone2 = sharedPref.getString(getString(R.string.saved_p2_phone), "");
        if(personName2.equals("")){
            civ2.setImageResource(R.drawable.p);
            bt22.setText("");
        } else {
            civ2.setImageResource(R.drawable.pp);
            bt22.setText(personName2);
        }

        personName3 = sharedPref.getString(getString(R.string.saved_p3_name), "");
        personPhone3 = sharedPref.getString(getString(R.string.saved_p3_phone), "");
        if(personName3.equals("")){
            civ3.setImageResource(R.drawable.p);
            bt23.setText("");
        } else {
            civ3.setImageResource(R.drawable.pp);
            bt23.setText(personName3);
        }

        personName4 = sharedPref.getString(getString(R.string.saved_p4_name), "");
        personPhone4 = sharedPref.getString(getString(R.string.saved_p4_phone), "");
        if(personName4.equals("")){
            civ4.setImageResource(R.drawable.p);
            bt24.setText("");
        } else {
            civ4.setImageResource(R.drawable.pp);
            bt24.setText(personName4);
        }

        personName5 = sharedPref.getString(getString(R.string.saved_p5_name), "");
        personPhone5 = sharedPref.getString(getString(R.string.saved_p5_phone), "");
        if(personName5.equals("")){
            civ5.setImageResource(R.drawable.p);
            bt25.setText("");
        } else {
            civ5.setImageResource(R.drawable.pp);
            bt25.setText(personName5);
        }

        personName6 = sharedPref.getString(getString(R.string.saved_p6_name), "");
        personPhone6 = sharedPref.getString(getString(R.string.saved_p6_phone), "");
        if(personName6.equals("")){
            civ6.setImageResource(R.drawable.p);
            bt26.setText("");
        } else {
            civ6.setImageResource(R.drawable.pp);
            bt26.setText(personName6);
        }

        civ1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                CustomDialog customDialog = new CustomDialog(ContactActivity.this);
                cValue = 1;
                customDialog.callFunction();
            }
        });

        civ2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(ContactActivity.this);
                cValue = 2;
                customDialog.callFunction();
            }
        });

        civ3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(ContactActivity.this);
                cValue = 3;
                customDialog.callFunction();
            }
        });

        civ4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(ContactActivity.this);
                cValue = 4;
                customDialog.callFunction();
            }
        });

        civ5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(ContactActivity.this);
                cValue = 5;
                customDialog.callFunction();
            }
        });

        civ6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(ContactActivity.this);
                cValue = 6;
                customDialog.callFunction();
            }
        });

    }

    public void setValue(String s1, String s2){
        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(cValue == 1){
            editor.putString(getString(R.string.saved_p1_name), s1);
            editor.putString(getString(R.string.saved_p1_phone), s2);
            if(personName1.equals("")){
                civ1.setImageResource(R.drawable.p);
                bt21.setText("");
            } else {
                civ1.setImageResource(R.drawable.pp);
                bt21.setText(personName1);
            }
        } else if(cValue == 2) {
            editor.putString(getString(R.string.saved_p2_name), s1);
            editor.putString(getString(R.string.saved_p2_phone), s2);
            if(personName2.equals("")){
                civ2.setImageResource(R.drawable.p);
                bt22.setText("");
            } else {
                civ2.setImageResource(R.drawable.pp);
                bt22.setText(personName2);
            }
        } else if(cValue == 3) {
            editor.putString(getString(R.string.saved_p3_name), s1);
            editor.putString(getString(R.string.saved_p3_phone), s2);
            if(personName3.equals("")){
                civ3.setImageResource(R.drawable.p);
                bt23.setText("");
            } else {
                civ3.setImageResource(R.drawable.pp);
                bt23.setText(personName3);
            }
        } else if(cValue == 4) {
            editor.putString(getString(R.string.saved_p4_name), s1);
            editor.putString(getString(R.string.saved_p4_phone), s2);
            if(personName4.equals("")){
                civ4.setImageResource(R.drawable.p);
                bt24.setText("");
            } else {
                civ4.setImageResource(R.drawable.pp);
                bt24.setText(personName4);
            }
        } else if(cValue == 5) {
            editor.putString(getString(R.string.saved_p5_name), s1);
            editor.putString(getString(R.string.saved_p5_phone), s2);
            if(personName5.equals("")){
                civ5.setImageResource(R.drawable.p);
                bt25.setText("");
            } else {
                civ5.setImageResource(R.drawable.pp);
                bt25.setText(personName5);
            }
        } else {
            editor.putString(getString(R.string.saved_p6_name), s1);
            editor.putString(getString(R.string.saved_p6_phone), s2);
            if(personName6.equals("")){
                civ6.setImageResource(R.drawable.p);
                bt26.setText("");
            } else {
                civ6.setImageResource(R.drawable.pp);
                bt26.setText(personName6);
            }
        }


        editor.commit();
    }
}
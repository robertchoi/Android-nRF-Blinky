package no.nordicsemi.android.blinky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

    public int p1,p11,p12,p2,p3,p4,p5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting);

        LinearLayout sl0 = findViewById(R.id.sl0);
        LinearLayout sl1 = findViewById(R.id.sl1);
        LinearLayout sl2 = findViewById(R.id.sl2);

        ViewGroup.LayoutParams params = sl0.getLayoutParams();

        Button sl1Button = findViewById(R.id.sl1Button);
        Button sl2Button = findViewById(R.id.sl2Button);

        EditText editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber1.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "5")});
        EditText editTextNumber2 = findViewById(R.id.editTextNumber2);
        editTextNumber2.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "2")});

        final MaterialToolbar toolbar = findViewById(R.id.toolbarMain);
        /* toolbar.setTitle("LUHELO"); */
        setSupportActionBar(toolbar);


        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        p1 = sharedPref.getInt(getString(R.string.preset1), 1);
        p11 = sharedPref.getInt("preset11", 5);
        p12 = sharedPref.getInt("preset12", 2);
        p2 = sharedPref.getInt(getString(R.string.preset2), 1);
        p3 = sharedPref.getInt(getString(R.string.preset3), 1);
        p4 = sharedPref.getInt(getString(R.string.preset4), 1);
        p5 = sharedPref.getInt(getString(R.string.preset5), 1);


        CheckBox cb1 = findViewById(R.id.checkBox1);
        CheckBox cb2 = findViewById(R.id.checkBox2);
        CheckBox cb3 = findViewById(R.id.checkBox3);
        CheckBox cb4 = findViewById(R.id.checkBox4);
        CheckBox cb5 = findViewById(R.id.checkBox5);

        if(p1 == 1) {
            cb1.setChecked(true);
            sl1.setVisibility(View.VISIBLE);
            sl2.setVisibility(View.VISIBLE);
        }
        else {
            cb1.setChecked(false);
            sl1.setVisibility(View.GONE);
            sl2.setVisibility(View.GONE);
        }
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        sl0.setLayoutParams(params);

        if(p2 == 1)
            cb2.setChecked(true);
        else
            cb2.setChecked(false);
        if(p3 == 1)
            cb3.setChecked(true);
        else
            cb3.setChecked(false);
        if(p4 == 1)
            cb4.setChecked(true);
        else
            cb4.setChecked(false);
        if(p5 == 1)
            cb5.setChecked(true);
        else
            cb5.setChecked(false);

        editTextNumber1.setText(String.valueOf(p11));
        editTextNumber2.setText(String.valueOf(p12));


        sl1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "알람 반복 간격을 설정하였습니다.", Toast.LENGTH_SHORT).show();

                p11= Integer.parseInt(String.valueOf(editTextNumber1.getText()));
                editor.putInt(getString(R.string.preset11), p11);
                editor.commit();
            }
        });
        sl2Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "알람 반복 횟수를 설정하였습니.", Toast.LENGTH_SHORT).show();
                p12= Integer.parseInt(String.valueOf(editTextNumber2.getText()));
                editor.putInt(getString(R.string.preset12), p12);
                editor.commit();
            }
        });


        cb1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
                if(cb1.isChecked()){
                    p1 = 1;
                    sl1.setVisibility(View.VISIBLE);
                    sl2.setVisibility(View.VISIBLE);
                } else {
                    p1 = 0;
                    sl1.setVisibility(View.GONE);
                    sl2.setVisibility(View.GONE);
                }
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                sl0.setLayoutParams(params);

                editor.putInt(getString(R.string.preset1), p1);
                editor.commit();
            }
        });

        cb2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
                if(cb2.isChecked()){
                    p2 = 1;
                } else {
                    p2 = 0;
                }
                editor.putInt(getString(R.string.preset2), p2);
                editor.commit();
            }
        });

        cb3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
                if(cb3.isChecked()){
                    p3 = 1;
                } else {
                    p3 = 0;
                }
                editor.putInt(getString(R.string.preset3), p3);
                editor.commit();
            }
        });

        cb4.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
                if(cb4.isChecked()){
                    p4 = 1;
                } else {
                    p4 = 0;
                }
                editor.putInt(getString(R.string.preset4), p4);
                editor.commit();
            }
        });

        cb5.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
                if(cb5.isChecked()){
                    p5 = 1;
                } else {
                    p5 = 0;
                }
                editor.putInt(getString(R.string.preset5), p5);
                editor.commit();
            }
        });




    }
}
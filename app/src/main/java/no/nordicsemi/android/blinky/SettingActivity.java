package no.nordicsemi.android.blinky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

    public int p1,p2,p3,p4,p5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final MaterialToolbar toolbar = findViewById(R.id.toolbarMain);
        /* toolbar.setTitle("LUHELO"); */
        setSupportActionBar(toolbar);


        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        p1 = sharedPref.getInt(getString(R.string.preset1), 0);
        p2 = sharedPref.getInt(getString(R.string.preset2), 0);
        p3 = sharedPref.getInt(getString(R.string.preset3), 0);
        p4 = sharedPref.getInt(getString(R.string.preset4), 0);
        p5 = sharedPref.getInt(getString(R.string.preset5), 0);


        CheckBox cb1 = findViewById(R.id.checkBox1);
        CheckBox cb2 = findViewById(R.id.checkBox2);
        CheckBox cb3 = findViewById(R.id.checkBox3);
        CheckBox cb4 = findViewById(R.id.checkBox4);
        CheckBox cb5 = findViewById(R.id.checkBox5);

        if(p1 == 1)
            cb1.setChecked(true);
        else
            cb1.setChecked(false);

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


        cb1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : process the click event.
                if(cb1.isChecked()){
                    p1 = 1;
                } else {
                    p1 = 0;
                }
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
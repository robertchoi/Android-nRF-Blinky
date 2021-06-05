package no.nordicsemi.android.blinky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final MaterialToolbar toolbar = findViewById(R.id.toolbarMain);
        /* toolbar.setTitle("LUHELO"); */
        setSupportActionBar(toolbar);

    }
}
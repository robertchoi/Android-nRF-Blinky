package no.nordicsemi.android.blinky;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MaterialToolbar toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Button bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });

        Button bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });

        Button bt3 = (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:
                Toast.makeText(getApplicationContext(), "새로고침 메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_settings:
                Toast.makeText(getApplicationContext(), "설정 메뉴", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
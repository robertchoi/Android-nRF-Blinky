package no.nordicsemi.android.blinky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import no.nordicsemi.android.ble.livedata.state.ConnectionState;
import no.nordicsemi.android.blinky.adapter.DiscoveredBluetoothDevice;
import no.nordicsemi.android.blinky.viewmodels.BlinkyViewModel;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_DEVICE = "no.nordicsemi.android.blinky.EXTRA_DEVICE";

    private BlinkyViewModel viewModel;
    boolean deviceConnect  = FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        final DiscoveredBluetoothDevice device = intent.getParcelableExtra(EXTRA_DEVICE);


        TextView tv2 = (TextView) findViewById(R.id.textView2);

        if(device != null){
            final String deviceName = device.getName();
            final String deviceAddress = device.getAddress();

            // Configure the view model.
            viewModel = new ViewModelProvider(this).get(BlinkyViewModel.class);
            viewModel.connect(device);
            deviceConnect = TRUE;


            viewModel.getConnectionState().observe(this, state -> {
                switch (state.getState()) {
                    case CONNECTING:
                        //progressContainer.setVisibility(View.VISIBLE);
                        //notSupported.setVisibility(View.GONE);
                        //connectionState.setText(R.string.state_connecting);
                        break;
                    case INITIALIZING:
                        //connectionState.setText(R.string.state_initializing);
                        break;
                    case READY:
                        //progressContainer.setVisibility(View.GONE);
                        //content.setVisibility(View.VISIBLE);
                        //onConnectionStateChanged(true);
                        tv2.setText("연결됨");
                        break;
                    case DISCONNECTED:
                        if (state instanceof ConnectionState.Disconnected) {
                            tv2.setText("연결안");
                            //final ConnectionState.Disconnected stateWithReason = (ConnectionState.Disconnected) state;
                            //됨if (stateWithReason.isNotSupported()) {
                            //    progressContainer.setVisibility(View.GONE);
                            //    notSupported.setVisibility(View.VISIBLE);
                            //}
                        }
                        // fallthrough
                    case DISCONNECTING:
                        //onConnectionStateChanged(false);
                        break;
                }
            });
        }


        final MaterialToolbar toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        Button bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alram);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
            }
        });

        Button bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                final Intent intent = new Intent(this, ScannerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
            case R.id.menu2:
                if( deviceConnect == TRUE){
                    Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show();
                    //tv2.setText("연결안됨");
                }
                break;
            case R.id.menu3:

                moveTaskToBack(true);						        // 태스크를 백그라운드로 이동
                finishAndRemoveTask();						                // 액티비티 종료 + 태스크 리스트에서 지우기
                android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
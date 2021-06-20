package no.nordicsemi.android.blinky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import no.nordicsemi.android.ble.livedata.state.ConnectionState;
import no.nordicsemi.android.blinky.adapter.DiscoveredBluetoothDevice;
import no.nordicsemi.android.blinky.viewmodels.BlinkyViewModel;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_DEVICE = "no.nordicsemi.android.blinky.EXTRA_DEVICE";

    public static Context context_main;
    NotificationManager manager;
    NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "luhero_disconnect";

    private double gpslati;
    private double gpslong;

    private BlinkyViewModel viewModel;
    boolean deviceConnect  = FALSE;
    public  boolean btStatus = FALSE;

    private BroadcastReceiver broadcastReceiver;

    private GpsTracker gpsTracker;
    private CircleProgressBar mCustomProgressBar;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public String batteryLevel;
    public boolean alraming = false;

    DiscoveredBluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        context_main = this;

        final Intent intent = getIntent();
        device = intent.getParcelableExtra(EXTRA_DEVICE);

        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        startService(i);


        TextView tv2 = (TextView) findViewById(R.id.textView2);
        mCustomProgressBar = findViewById(R.id.days_graph);
        mCustomProgressBar.setProgress(0);

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
                        //mCustomProgressBar.setProgress(90);

                        break;
                    case DISCONNECTED:
                        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                        int p2 = sharedPref.getInt(getString(R.string.preset2), 1);

                        if(p2 == 1) {
                            showNoti();
                        }
                        if (state instanceof ConnectionState.Disconnected) {
                            tv2.setText("연결끊김");
                            mCustomProgressBar.setProgress(0);
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

            viewModel.getButtonState().observe(this,
                    pressed -> {
                        if(pressed){
                            //Toast.makeText(MainActivity.this, "비상버", Toast.LENGTH_LONG).show();


                            if(alraming == false){
                                alraming = true;
                                alramProcess();
                            }

                        }
                    });

            viewModel.getButtonLevel().observe(this,
                    buttonLevel -> {
                        //if(buttonLevel.)
                        {
                            String setString = buttonLevel.substring(5);
                            int setValue = Integer.parseInt(setString,16);
                            mCustomProgressBar.setProgress(setValue);
                            //Toast.makeText(MainActivity.this, "버튼 ", Toast.LENGTH_LONG).show();
                        }
                    });
        }


        final MaterialToolbar toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        Button bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alramProcess();
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

    public void btTest(){

        if(deviceConnect){
            if(btStatus == TRUE){
                if(alraming == false){
                    alraming = true;
                    alramProcess();
                }
            }
        }

    }

    public void alramProcess(){
        // Do something in response to button click
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alram);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                alraming = false;
            }

        });

        Log.d("test", "alramProcess~~~~~");


        //gpsTracker = new GpsTracker(MainActivity.this);

        //double latitude = gpsTracker.getLatitude();
        //double longitude = gpsTracker.getLongitude();
        double latitude = gpslati;
        double longitude = gpslong;

        String address = getCurrentAddress(latitude, longitude);
        //textview_address.setText(address);

        //Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

        //LUHERO 위급상황 알림 : http://maps.google.com/?q=37.4730555,127.1003179


        //String sms = "LUHERO 위급상황 알림 :" + latitude + "," + longitude;
        String sms = "LUHERO 위급상황 알림 : http://maps.google.com/?q="+latitude + "," + longitude;


        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String pP1 = sharedPref.getString(getString(R.string.saved_p1_phone), "");
        String pP2 = sharedPref.getString(getString(R.string.saved_p2_phone), "");
        String pP3 = sharedPref.getString(getString(R.string.saved_p3_phone), "");
        String pP4 = sharedPref.getString(getString(R.string.saved_p4_phone), "");
        String pP5 = sharedPref.getString(getString(R.string.saved_p5_phone), "");
        String pP6 = sharedPref.getString(getString(R.string.saved_p6_phone), "");

        if(pP1.equals("")){

        } else {
            try {
                //전송
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pP1, null, sms, null, null);
                //Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                //Toast.makeText(getApplicationContext(), "SMS1 faild, please try again later!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        if(pP2.equals("")){

        } else {
            try {
                //전송
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pP2, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS2 faild, please try again later!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        if(pP3.equals("")){

        } else {
            try {
                //전송
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pP3, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS3 faild, please try again later!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        if(pP4.equals("")){

        } else {
            try {
                //전송
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pP4, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS4 faild, please try again later!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        if(pP5.equals("")){

        } else {
            try {
                //전송
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pP5, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS5 faild, please try again later!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        if(pP6.equals("")){

        } else {
            try {
                //전송
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pP6, null, sms, null, null);
                Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS6 faild, please try again later!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }

    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            //Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            //Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }
        if (addresses == null || addresses.size() == 0) {
            //Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

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
                if( deviceConnect == TRUE) {
                    Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show();
                    //tv2.setText("연결안됨");

                } else {


                }
                deviceConnect = TRUE;
                viewModel.disconnect();

                break;
            case R.id.menu3:

                moveTaskToBack(true);						        // 태스크를 백그라운드로 이동
                //finishAndRemoveTask();						                // 액티비티 종료 + 태스크 리스트에서 지우기

                ActivityCompat.finishAffinity(this);
                System.runFinalizersOnExit(true);
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
/*            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {

            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }*/
        }
    };
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
//        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
//        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
//        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 서비스 종료하기
        Log.d("test", "액티비티-서비스 RESUME 종료버튼클릭");
        Intent intent = new Intent(
                getApplicationContext(),//현재제어권자
                MyService.class); // 이동할 컴포넌트
        stopService(intent); // 서비스 종료

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    gpslong = intent.getDoubleExtra("glong",0.0);
                    gpslati = intent.getDoubleExtra("glati", 0.0);
                    //textView.append("count: "+cnt+"\n" +intent.getExtras().get("coordinates")+"\n");

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));


    }
    @Override
    protected void onPause() {
        super.onPause();

        Log.d("test", "액티비티-서비스 시작버튼클릭");
        Intent intent = new Intent(
                getApplicationContext(),//현재제어권자
                MyService.class); // 이동할 컴포넌트
        startService(intent); // 서비스 시작

        btStatus = FALSE;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService(mServiceConnection);
        //mBluetoothLeService = null;

        // 서비스 종료하기
        Log.d("test", "액티비티-서비스 종료버튼클릭");
        Intent intent = new Intent(
                getApplicationContext(),//현재제어권자
                MyService.class); // 이동할 컴포넌트
        stopService(intent); // 서비스 종료

        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        stopService(i);

        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    public void showNoti(){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            manager.createNotificationChannel( new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT) );
            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
            //하위 버전일 경우
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        //알림창 제목
        builder.setContentTitle("LUHERO PRO");
        //알림창 메시지
        builder.setContentText("연결이 끊어졌습니다.");
        //알림창 아이콘
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon));
        builder.setSmallIcon(R.drawable.icon);
        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);
    }


    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
        //super.onBackPressed();
        moveTaskToBack(false);
    }



}
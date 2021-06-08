package no.nordicsemi.android.blinky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class CustomDialog {

    private Context context;
    ContactActivity contactActivity;


    public CustomDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);
        contactActivity = (ContactActivity)context;

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final EditText message2 = (EditText) dlg.findViewById(R.id.mesgase2);
        final Button okSave = (Button) dlg.findViewById(R.id.okSave);
        final Button okCancel = (Button) dlg.findViewById(R.id.okCancel);
        final Button okDelete = (Button) dlg.findViewById(R.id.okDelete);

        if(contactActivity.cValue == 1){
            message.setText(contactActivity.personName1);
            message2.setText(contactActivity.personPhone1);
        } else if(contactActivity.cValue == 2){
            message.setText(contactActivity.personName2);
            message2.setText(contactActivity.personPhone2);
        } else if(contactActivity.cValue == 3){
            message.setText(contactActivity.personName3);
            message2.setText(contactActivity.personPhone3);
        } else if(contactActivity.cValue == 4){
            message.setText(contactActivity.personName4);
            message2.setText(contactActivity.personPhone4);
        } else if(contactActivity.cValue == 5){
            message.setText(contactActivity.personName5);
            message2.setText(contactActivity.personPhone5);
        } else {
            message.setText(contactActivity.personName6);
            message2.setText(contactActivity.personPhone6);
        }



        okSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "\"" +  message.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                if(contactActivity.cValue == 1){
                    contactActivity.personName1 = message.getText().toString();
                    contactActivity.personPhone1 = message2.getText().toString();
                } else if(contactActivity.cValue == 2){
                    contactActivity.personName2 = message.getText().toString();
                    contactActivity.personPhone2 = message2.getText().toString();
                } else if(contactActivity.cValue == 3){
                    contactActivity.personName3 = message.getText().toString();
                    contactActivity.personPhone3 = message2.getText().toString();
                } else if(contactActivity.cValue == 4){
                    contactActivity.personName4 = message.getText().toString();
                    contactActivity.personPhone4 = message2.getText().toString();
                } else if(contactActivity.cValue == 5){
                    contactActivity.personName5 = message.getText().toString();
                    contactActivity.personPhone5 = message2.getText().toString();
                } else {
                    contactActivity.personName6 = message.getText().toString();
                    contactActivity.personPhone6 = message2.getText().toString();
                }
                contactActivity.setValue(message.getText().toString(), message2.getText().toString());



                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        okCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        okDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "삭제 했습니다.", Toast.LENGTH_SHORT).show();
                if(contactActivity.cValue == 1){
                    contactActivity.personName1 = "";
                    contactActivity.personPhone1 = "";
                } else if(contactActivity.cValue == 2){
                    contactActivity.personName2 = "";
                    contactActivity.personPhone2 = "";
                } else if(contactActivity.cValue == 3){
                    contactActivity.personName3 = "";
                    contactActivity.personPhone3 = "";
                } else if(contactActivity.cValue == 4){
                    contactActivity.personName4 = "";
                    contactActivity.personPhone4 = "";
                } else if(contactActivity.cValue == 5){
                    contactActivity.personName5 = "";
                    contactActivity.personPhone5 = "";
                } else {
                    contactActivity.personName6 = "";
                    contactActivity.personPhone6 = "";
                }
                contactActivity.setValue("", "");
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
}
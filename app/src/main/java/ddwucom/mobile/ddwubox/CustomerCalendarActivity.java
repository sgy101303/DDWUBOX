package ddwucom.mobile.ddwubox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerCalendarActivity extends AppCompatActivity {
    CalendarView reservDate;

    LoginDBManager logManager;
    String date;;
    String nowDate;
    String userId;
    String mName;
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_calendar);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        mName = intent.getStringExtra("movieName");

        logManager = new LoginDBManager(this);

//        userId = (String) getIntent().getSerializableExtra("userId");
//        mName = (String) getIntent().getSerializableExtra("movieName");

        date = logManager.checkAlreadyReserv(userId, mName);

        reservDate = findViewById(R.id.calendarView);

        reservDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                nowDate = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                if(date.equals("noR") == false) {
                    boolean result = logManager.modify(nowDate, userId);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("reservDate", nowDate);
                    date = nowDate;

                    if(result) {
                        setResult(RESULT_OK, resultIntent);
                    }
                     /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerCalendarActivity.this);

                    builder.setTitle("예매 날짜 바꾸기 확인")
                            .setIcon(R.mipmap.somsom)
                            .setMessage("예매 날짜를 바꾸시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent resultIntent = new Intent();
//                                    resultIntent.putExtra("reservDate", nowDate);
//                                    date = nowDate;
                                    //추가
//                                    boolean result = logManager.modify(nowDate, userId);

                                    if(result) {
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("reservDate", nowDate);
                                        date = nowDate;
                                        setResult(RESULT_OK, resultIntent);
                                    }
                                }
                            })
                            .setNegativeButton("취소",  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setResult(RESULT_CANCELED);
                                }
                            });
                    Dialog dlg = builder.create();
                    dlg.setCanceledOnTouchOutside(false);
                    dlg.show();
                }
                      */
                }
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("reservDate", nowDate);
                    // 추가
                    boolean result = logManager.modify(nowDate, userId);
                    date = nowDate;
                    if(result) {
                        setResult(RESULT_OK, resultIntent);
                    }
                }
                finish();
                break;








            /*
                if(!date.equals("noR")) {
                    result = logManager.modify(nowDate, userId);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerCalendarActivity.this);

                    builder.setTitle("예매 날짜 바꾸기 확인")
                            .setIcon(R.mipmap.somsom)
                            .setMessage("예매 날짜를 바꾸시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent resultIntent = new Intent();
//                                    resultIntent.putExtra("reservDate", nowDate);
//                                    date = nowDate;
                                    //추가
//                                    boolean result = logManager.modify(nowDate, userId);
                                    
                                    if(result) {
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("reservDate", nowDate);
                                        date = nowDate;
                                        setResult(RESULT_OK, resultIntent);
                                    }
                                }
                            })
                            .setNegativeButton("취소",  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setResult(RESULT_CANCELED);
                                }
                            });
                    Dialog dlg = builder.create();
                    dlg.setCanceledOnTouchOutside(false);
                    dlg.show();
                }
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("reservDate", nowDate);
                    // 추가
                    boolean result = logManager.modify(nowDate, userId);
                    date = nowDate;
                    if(result) {
                        setResult(RESULT_OK, resultIntent);
                    }
                }
                finish();
                break;

             */

            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
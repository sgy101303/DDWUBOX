package ddwucom.mobile.ddwubox;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    final static int MENU_FIRST = 100;
    final static int MENU_SECOND = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdmin :
                Intent intent1 = new Intent(MainActivity.this, AdminMainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnCustomer :
                Intent intent2 = new Intent(MainActivity.this, CustomerMainActivity.class);
                startActivity(intent2);
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first, menu); //한번만 호출이 됨, 바꿔치기 하면서 생성 가능
        return true; //잘 만들어졌을 경우에는 true, 넘길 경우 false
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_introduce:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("개발자 소개")
                        .setIcon(R.mipmap.somsom)
                        .setMessage("소프트웨어 경진대회에 참여하게 된\n" + "컴퓨터학과 이승혜, 심가연입니다.\n" + "어플을 사용해주셔서 감사합니다.")
                        .setPositiveButton("확인", null);
                Dialog dlg = builder.create();
                dlg.setCanceledOnTouchOutside(false);
                dlg.show();

                break;
            case R.id.menu_exit:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("취소", null);
                Dialog dlg2 = builder2.create();
                dlg2.setCanceledOnTouchOutside(false);
                dlg2.show();
                break;
        }
        return true;
    }

//
}
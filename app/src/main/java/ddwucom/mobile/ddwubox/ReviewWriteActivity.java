package ddwucom.mobile.ddwubox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewWriteActivity extends AppCompatActivity {

    EditText etReview;
    LoginDBManager loginDBManager;
    ArrayList<Login> loginList;
    boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        etReview = findViewById(R.id.etReview);
        loginDBManager = new LoginDBManager(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                Intent intent = getIntent();
                String userId = intent.getStringExtra("userId");
                String movieName = intent.getStringExtra("movieName");
                String userPw = intent.getStringExtra("userPw");

                if(loginDBManager.newUserReview(userId, movieName).equals("true")) {
                    result = loginDBManager.modifyUser(userId, movieName, etReview.getText().toString());
                }
                else {
                    result = loginDBManager.addNewUser(new Login(userId, userPw, movieName, etReview.getText().toString(), null));
                }


                if(result) {
                    Toast.makeText(this, "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "리뷰 등록이 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
            case R.id.btnCancel:
                Toast.makeText(this, "리뷰 입력을 취소했습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
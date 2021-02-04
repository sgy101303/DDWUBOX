package ddwucom.mobile.ddwubox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewReadActivity extends AppCompatActivity {
//    final int SHOW_CODE = 100;

    ListView listView;
    LoginAdapter loginAdapter;
    ArrayList<Login> loginList;
    LoginDBManager loginDBManager;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_read);
        intent2 = getIntent();

        listView = findViewById(R.id.customLoginView); //adapter 지정 전에 데이터 준비돼있어야함
        loginDBManager = new LoginDBManager(this);
        loginList = loginDBManager.getThisMovieReviewUser(intent2.getStringExtra("movieName"));
        if(loginList.size() == 0) {
            Toast.makeText(ReviewReadActivity.this, "작성된 리뷰가 없습니다. 다시 돌아가주세요.", Toast.LENGTH_SHORT).show();
        }
//        movieList.addAll(movieDBManager.getAllMovie());
        loginAdapter = new LoginAdapter(this, R.layout.review_adapter_view, loginList);
        //둘을 결합
        listView.setAdapter(loginAdapter);
    }
}
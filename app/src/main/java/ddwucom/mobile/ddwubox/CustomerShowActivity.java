package ddwucom.mobile.ddwubox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class CustomerShowActivity extends AppCompatActivity {
    final int CALENDER_RESERV = 100;

    Movie movie;

    EditText etName;
    EditText etNum;
    EditText etGenre;
    ImageView mvImage;
    EditText etActor;
    EditText etDirector;
    EditText etDate;
    RatingBar ratingBar;
    EditText etRating;
    EditText etPlot;
    CheckBox checkBoxReservation;
    // String date;
    // ArrayList<String> dateList;
    MovieDBManager movieDBManager;
    LoginDBManager loginDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        loginDBManager = new LoginDBManager(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        mvImage = findViewById(R.id.mvImage);
        etName = findViewById(R.id.etName);
        etNum = findViewById(R.id.etNum);
        etGenre = findViewById(R.id.etGenre);
        etActor = findViewById(R.id.etActor);
        etDirector = findViewById(R.id.etDirector);
        etDate = findViewById(R.id.etDate);
        ratingBar = findViewById(R.id.ratingBar);
        etRating = findViewById(R.id.etRating);
        etPlot = findViewById(R.id.etPlot);
        checkBoxReservation = findViewById(R.id.checkBoxReservation);

        mvImage.setImageResource(movie.getImageView());
        etName.setText(movie.getMovie());
        etNum.setText(movie.getNum());
        etGenre.setText(movie.getGenre());
        etActor.setText(movie.getActor());
        etDirector.setText(movie.getDirector());
        etDate.setText(movie.getDate());


        if(movie.getReservation().equals("TRUE"))
            checkBoxReservation.setChecked(TRUE);
        else
            checkBoxReservation.setChecked(FALSE);


//        if(movie.getDate() != null) {
//            ArrayList<String> arrayList = new ArrayList();
//            int len = movie.getDate().length();
//            int cnt = 0;
//            for(int i=0; i<len; i++) {
//                if(movie.getDate().charAt(i) != '/'){
//                    arrayList.add(String.valueOf(movie.getDate().charAt(i)));
//                }
//                else {
//                    dateList.add(arrayList.get(cnt));
//                    cnt++;
//                }
//            }
//            etDate.setHint(movie.getDate());
//        }

        if(movie.getRating() == null) {
            ratingBar.setRating(0);
        }
        else {
            etRating.setText(movie.getRating());
            ratingBar.setRating(Float.parseFloat(movie.getRating()));
        }
        etPlot.setText(movie.getPlot());

//        ratingBar.setIsIndicator(false);
//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                etRating.setText(String.valueOf(ratingBar.getRating()));
//                ratingBar.setRating(rating);
//            }
//        });

        movieDBManager = new MovieDBManager(this);
    }

    public void onClick(View v) {
        final ConstraintLayout login = (ConstraintLayout) View.inflate(this, R.layout.activity_login, null);
        switch(v.getId()) {
            case R.id.button:
                AlertDialog.Builder builderGenreSearch = new AlertDialog.Builder(this)
                        .setTitle("회원 로그인")
                        .setView(login)
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText etId = login.findViewById(R.id.userId);
                                EditText etPw = login.findViewById(R.id.userPw);
                                if(etId.getText().toString().getBytes().length <= 0 || etPw.getText().toString().getBytes().length <= 0)
                                    Toast.makeText(CustomerShowActivity.this, "아이디 혹은 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                else {
                                    String result = loginDBManager.searchUser(etId.getText().toString(), etPw.getText().toString());
                                    if(result.equals("true")) {
                                        Intent intent = new Intent(CustomerShowActivity.this, CustomerCalendarActivity.class);
                                        intent.putExtra("movieName", etName.getText().toString());
                                        intent.putExtra("userId", etId.getText().toString());
                                        startActivityForResult(intent, CALENDER_RESERV);
                                    }
                                    else if(result.equals("failPW"))
                                        Toast.makeText(CustomerShowActivity.this, "비밀번호가 틀렸습니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                                    else {
                                        Boolean resultAdd = loginDBManager.addNewUser(new Login(etId.getText().toString(), etPw.getText().toString(), etName.getText().toString(), null, null));
                                        if(resultAdd) {
                                            Intent intent = new Intent(CustomerShowActivity.this, CustomerCalendarActivity.class);
                                            intent.putExtra("movieName", etName.getText().toString());
                                            intent.putExtra("userId", etId.getText().toString());
                                            startActivityForResult(intent, CALENDER_RESERV);
                                            Toast.makeText(CustomerShowActivity.this, "새로운 로그인 정보입니다.\n 아이디와 비밀번호를 잊지 말고 기억해주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            Toast.makeText(CustomerShowActivity.this, "다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CustomerShowActivity.this, "로그인이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Dialog dglG = builderGenreSearch.create();
                dglG.setCanceledOnTouchOutside(false);
                dglG.show();
                break;
            case R.id.btnReviewShow:
                Intent intent2 = new Intent(CustomerShowActivity.this, ReviewReadActivity.class);
                intent2.putExtra("movieName", etName.getText().toString());
                startActivity(intent2);
                break;
            case R.id.btnReviewWrite:

                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerShowActivity.this);
                builder.setTitle("리뷰 작성")
                        .setMessage("영화를 관람하셨습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builderReviewSearch = new AlertDialog.Builder(CustomerShowActivity.this)
                                        .setTitle("회원 로그인")
                                        .setView(login)
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                EditText etId = login.findViewById(R.id.userId);
                                                EditText etPw = login.findViewById(R.id.userPw);
                                                if(etId.getText().toString().getBytes().length <= 0 || etPw.getText().toString().getBytes().length <= 0)
                                                    Toast.makeText(CustomerShowActivity.this, "아이디 혹은 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                else {
                                                    String result = loginDBManager.searchUser(etId.getText().toString(), etPw.getText().toString());
                                                    if(result.equals("true")) {
                                                        Intent intent = new Intent(CustomerShowActivity.this, ReviewWriteActivity.class);
                                                        intent.putExtra("userId", etId.getText().toString());
                                                        intent.putExtra("userPw", etPw.getText().toString());
                                                        intent.putExtra("movieName", etName.getText().toString());
                                                        startActivity(intent);
                                                    }
                                                    else if(result.equals("failPW"))
                                                        Toast.makeText(CustomerShowActivity.this, "비밀번호가 틀렸습니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                                                    else {
                                                        Boolean resultAdd = loginDBManager.addNewUser(new Login(etId.getText().toString(), etPw.getText().toString(), etName.getText().toString(), null, null));
                                                        if(resultAdd) {
                                                            Intent intent = new Intent(CustomerShowActivity.this, ReviewWriteActivity.class);
                                                            intent.putExtra("userId", etId.getText().toString());
                                                            intent.putExtra("movieName", etName.getText().toString());
                                                            startActivity(intent);
                                                            Toast.makeText(CustomerShowActivity.this, "새로운 로그인 정보입니다.\n 아이디와 비밀번호를 잊지 말고 기억해주세요.", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                            Toast.makeText(CustomerShowActivity.this, "다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(CustomerShowActivity.this, "로그인이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                Dialog dglG2 = builderReviewSearch.create();
                                dglG2.setCanceledOnTouchOutside(false);
                                dglG2.show();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CustomerShowActivity.this, "영화 관람 후 작성해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Dialog dlg = builder.create();
                dlg.setCanceledOnTouchOutside(false);
                dlg.show();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALENDER_RESERV) {  // AddActivity 호출 후 결과 확인
            switch (resultCode) {
                case RESULT_OK:
                    String date = data.getStringExtra("reservDate");
                    Toast.makeText(CustomerShowActivity.this, date + "에 예매가 확정되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "예매가 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
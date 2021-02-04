package ddwucom.mobile.ddwubox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class AddActivity extends AppCompatActivity {

    EditText etName;
    EditText etNum;
    EditText etGenre;
    EditText etActor;
    EditText etDirector;
//    CalendarView calendarView;
    EditText etDate;
    RatingBar ratingBar;
    EditText etRating;
    EditText etPlot;
    CheckBox checkBoxReservation;
    String reservation = "";
    String date;
//    ImageView mvImage;

    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

//        mvImage.setImageResource(R.mipmap.me);
        etNum = findViewById(R.id.etNum);
//        etNum.setText("");
        etGenre = findViewById(R.id.etGenre);
//        etGenre.setText("");
        etName = findViewById(R.id.etName);
//        etName.setText("");
        etActor = findViewById(R.id.etActor);
//        etActor.setText("");
        etDirector = findViewById(R.id.etDirector);
//        etDirector.setText("");
        etDate = findViewById(R.id.etDate);
        ratingBar = findViewById(R.id.ratingBar);
        etRating = findViewById(R.id.etRating);
        etPlot = findViewById(R.id.etPlot);
        checkBoxReservation = findViewById(R.id.checkBoxReservation);


//        etPlot.setText("");

//        final Calendar calendar = Calendar.getInstance();
        /*
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "/" + (month + 1) + "/" + dayOfMonth;
                etDate.setText(date);
            }
        });
*/
        ratingBar.setIsIndicator(false);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                etRating.setText(String.valueOf(ratingBar.getRating()));
                ratingBar.setRating(rating);
            }
        });

        movieDBManager = new MovieDBManager(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnAdd:
                if(etNum.getText().toString().getBytes().length > 0 && etGenre.getText().toString().getBytes().length > 0 && etName.getText().toString().getBytes().length > 0 && etActor.getText().toString().getBytes().length > 0 && etRating.getText().toString().getBytes().length > 0) {
                    if(checkBoxReservation.isChecked())
                        reservation = "TRUE";
                    else
                        reservation = "FALSE";
                    boolean result = movieDBManager.addNewMovie(
                            new Movie(etNum.getText().toString(), R.mipmap.ddwuicon, etGenre.getText().toString(), etName.getText().toString(), etActor.getText().toString(), etDirector.getText().toString(), etDate.getText().toString(), String.valueOf(ratingBar.getRating()), etPlot.getText().toString(), reservation));

                    if (result) {    // 정상수행에 따른 처리
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("movie", etName.getText().toString());
                        setResult(RESULT_OK, resultIntent);
                    } else {        // 이상에 따른 처리
                        Toast.makeText(this, "새로운 영화 추가 실패!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                else {
                    Toast.makeText(this, "필수 요소 입력 부재로 영화 추가에 실패하였습니다.\n다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancle:   // 취소에 따른 처리
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
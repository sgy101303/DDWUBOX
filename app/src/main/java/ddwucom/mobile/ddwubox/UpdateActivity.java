package ddwucom.mobile.ddwubox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
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
            case R.id.btnUpdate:

                mvImage.setImageResource(movie.getImageView());
                movie.setMovie(etName.getText().toString());
                movie.setNum(etNum.getText().toString());
                movie.setGenre(etGenre.getText().toString());
                movie.setActor(etActor.getText().toString());
                movie.setDirector(etDirector.getText().toString());
                movie.setDate(etDate.getText().toString());
                movie.setRating(String.valueOf(ratingBar.getRating()));
                movie.setPlot(etPlot.getText().toString());

                if(checkBoxReservation.isChecked())
                    movie.setReservation("TRUE");
                else
                    movie.setReservation("FALSE");

                if(etNum.getText().toString().getBytes().length > 0 && etGenre.getText().toString().getBytes().length > 0 && etName.getText().toString().getBytes().length > 0 && etActor.getText().toString().getBytes().length > 0) {
                    if (movieDBManager.modifyMovie(movie)) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("movie", movie);
                        setResult(RESULT_OK, resultIntent);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                }
                else {
                    Toast.makeText(this, "필수 요소 입력 부재로 영화 수정에 실패하였습니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancle:
                setResult(RESULT_CANCELED);
                finish();
                break;

        }
    }
}
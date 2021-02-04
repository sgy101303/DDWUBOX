package ddwucom.mobile.ddwubox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class RatingActivity extends AppCompatActivity {

    ListView listView;
    RatingAdapter ratingAdapter;
    ArrayList<Movie> movieList;
//    ArrayList<Movie> ratingList;
    MovieDBManager movieDBManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        listView = findViewById(R.id.customListView);
        movieDBManager = new MovieDBManager(this);
        movieList = movieDBManager.getAllMovieByRating();

//        ratingList = movieList;

//        Comparator<Movie> comparator = new Comparator<Movie>() {
//            @Override
//            public int compare(ddwucom.mobile.ddwubox.Movie o1, ddwucom.mobile.ddwubox.Movie o2) {
//                Float v1 = Float.parseFloat(o1.getRating());
//                Float v2 = Float.parseFloat(o2.getRating());
//
//                return ((Comparable) v2).compareTo(v1);
//            }
//        };
//        Collections.sort(ratingList, comparator);


//        Collections.sort(ratingList,new Comparator<Movie>() {
//            public int compare(Movie m1,Movie m2) {
//
//                Float v1 = Float.parseFloat(m1.getRating());
//                Float v2 = Float.parseFloat(m2.getRating());
//
//                return ((Comparable) v2).compareTo(v1);
//            }
//        });



//        movieList.addAll(movieDBManager.getAllMovie());
        ratingAdapter = new RatingAdapter(this, R.layout.activity_rating, movieList);

        listView.setAdapter(ratingAdapter);
    }


    protected void onResume() {
        super.onResume();
        movieList.clear();
        movieList.addAll(movieDBManager.getAllMovieByRating());
        ratingAdapter.notifyDataSetChanged();
    }
}
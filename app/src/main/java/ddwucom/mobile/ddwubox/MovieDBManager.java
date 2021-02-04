package ddwucom.mobile.ddwubox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

public class MovieDBManager {

    MovieDBHelper movieDBHelper = null;
    Cursor cursor = null;


    public MovieDBManager(Context context) {
        movieDBHelper = new MovieDBHelper(context);
    }

    //    DB의 모든 movie 반환
    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            String num = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_NUM));
            int image = cursor.getInt((cursor.getColumnIndex(MovieDBHelper.COL_IMAGE)));
            String genre = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE));
            String movie = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_MOVIE));
            String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
            String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
            String date = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DATE));
            String rating = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RATING));
            String plot = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_PLOT));
            String reservation = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RESERVATION));
            movieList.add(new Movie(id, num, image, genre, movie, actor, director, date, rating, plot, reservation));
        }
        cursor.close();
        movieDBHelper.close();
        return movieList;
    }

    public ArrayList<Movie> getAllMovieByRating() {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME + " order by rating desc", null);

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            String num = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_NUM));
            int image = cursor.getInt((cursor.getColumnIndex(MovieDBHelper.COL_IMAGE)));
            String genre = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE));
            String movie = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_MOVIE));
            String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
            String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
            String date = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DATE));
            String rating = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RATING));
            String plot = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_PLOT));
            String reservation = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RESERVATION));
            movieList.add(new Movie(id, num, image, genre, movie, actor, director, date, rating, plot, reservation));
        }
        cursor.close();
        movieDBHelper.close();
        return movieList;
    }

    //    DB 에 새로운 food 추가
    public boolean addNewMovie(Movie newMovie) {
        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MovieDBHelper.COL_NUM, newMovie.getNum());
        value.put(movieDBHelper.COL_IMAGE, newMovie.getImageView());
        value.put(MovieDBHelper.COL_GENRE, newMovie.getGenre());
        value.put(MovieDBHelper.COL_MOVIE, newMovie.getMovie());
        value.put(MovieDBHelper.COL_ACTOR, newMovie.getActor());
        value.put(MovieDBHelper.COL_DIRECTOR, newMovie.getDirector());
        value.put(MovieDBHelper.COL_DATE, newMovie.getDate());
        value.put(MovieDBHelper.COL_RATING, newMovie.getRating());
        value.put(MovieDBHelper.COL_PLOT, newMovie.getPlot());
        value.put(MovieDBHelper.COL_RESERVATION, newMovie.getReservation());

//      insert 메소드를 사용할 경우 데이터 삽입이 정상적으로 이루어질 경우 1 이상, 이상이 있을 경우 0 반환 확인 가능
        long count = db.insert(MovieDBHelper.TABLE_NAME, null, value);
        movieDBHelper.close();
        if (count > 0) return true;
        return false;
    }

    //    _id 를 기준으로 food 의 이름과 nation 변경
    public boolean modifyMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(MovieDBHelper.COL_NUM, movie.getNum());
        row.put(movieDBHelper.COL_IMAGE, movie.getImageView());
        row.put(MovieDBHelper.COL_GENRE, movie.getGenre());
        row.put(MovieDBHelper.COL_MOVIE, movie.getMovie());
        row.put(MovieDBHelper.COL_ACTOR, movie.getActor());
        row.put(MovieDBHelper.COL_DIRECTOR, movie.getDirector());
        row.put(MovieDBHelper.COL_DATE, movie.getDate());
        row.put(MovieDBHelper.COL_RATING, movie.getRating());
        row.put(MovieDBHelper.COL_PLOT, movie.getPlot());
        row.put(MovieDBHelper.COL_RESERVATION, movie.getReservation());
        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(movie.get_id()) };
        int result = sqLiteDatabase.update(MovieDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        movieDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    //    _id 를 기준으로 DB에서 Movie 삭제
    public boolean removeMovie(long id) {
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int result = sqLiteDatabase.delete(MovieDBHelper.TABLE_NAME, whereClause,whereArgs);
        movieDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    //    나라 이름으로 DB 검색
    public ArrayList<Movie> getMoviesByTitle(String searchTitle) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            String movie = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_MOVIE));
            if (movie.equals(searchTitle)) {
                long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
                String num = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_NUM));
                int image = cursor.getInt((cursor.getColumnIndex(MovieDBHelper.COL_IMAGE)));
                String genre = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE));
                String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
                String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
                String date = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DATE));
                String rating = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RATING));
                String plot = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_PLOT));
                String reservation = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RESERVATION));
                movieList.add(new Movie(id, num, image, genre, movie, actor, director, date, rating, plot, reservation));
            }
        }
        cursor.close();
        movieDBHelper.close();
        return movieList;
    }

    public ArrayList<Movie> getMoviesByGenre(String searchGenre) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String genre = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_GENRE));
            if (genre.equals(searchGenre)) {
                long id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
                String num = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_NUM));
                int image = cursor.getInt((cursor.getColumnIndex(MovieDBHelper.COL_IMAGE)));
                String movie = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_MOVIE));
                String actor = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_ACTOR));
                String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
                String date = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DATE));
                String rating = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RATING));
                String plot = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_PLOT));
                String reservation = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RESERVATION));
                movieList.add(new Movie(id, num, image, genre, movie, actor, director, date, rating, plot, reservation));
            }
        }
        cursor.close();
        movieDBHelper.close();
        return movieList;
    }

    //    close 수행
    public void close() {
        if (movieDBHelper != null) movieDBHelper.close();
        if (cursor != null) cursor.close();
    };
}

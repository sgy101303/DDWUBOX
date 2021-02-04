package ddwucom.mobile.ddwubox;

import java.io.Serializable;

public class Movie implements Serializable {
    private long _id;
    private String num;
    private int imageView;
    private String genre;
    private String movie;
    private String actor;
    private String director;
    private String date;
    private String rating;
    private String plot;
    private String reservation;

//    private ImageView imageView;


    public Movie(long _id, String num, int imageView, String genre, String movie, String actor, String director, String date, String rating, String plot, String reservation) {
        this._id = _id;
        this.num = num;
        this.imageView = imageView;
        this.genre = genre;
        this.movie = movie;
        this.actor = actor;
        this.director = director;
        this.date = date;
        this.rating = rating;
        this.plot = plot;
        this.reservation = reservation;
    }

    public Movie(String num, int imageView, String genre, String movie, String actor, String director, String date, String rating, String plot, String reservation) {
        this.num = num;
        this.imageView = imageView;
        this.genre = genre;
        this.movie = movie;
        this.actor = actor;
        this.director = director;
        this.date = date;
        this.rating = rating;
        this.plot = plot;
        this.reservation = reservation;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }
}

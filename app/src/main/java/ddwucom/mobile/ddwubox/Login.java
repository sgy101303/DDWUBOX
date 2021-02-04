package ddwucom.mobile.ddwubox;

import java.io.Serializable;

public class Login implements Serializable {
    private long _id;
    private String userId;
    private String userPw;
    private String movie;
    private String review;
    private String reservation;

    public Login(long _id, String userId, String userPw, String movie, String review, String reservation) {
        this._id = _id;
        this.userId = userId;
        this.userPw = userPw;
        this.movie = movie;
        this.review = review;
        this.reservation = reservation;
    }

    public Login(String userId, String userPw, String movie, String review, String reservation) {
        this.userId = userId;
        this.userPw = userPw;
        this.movie = movie;
        this.review = review;
        this.reservation = reservation;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }
}

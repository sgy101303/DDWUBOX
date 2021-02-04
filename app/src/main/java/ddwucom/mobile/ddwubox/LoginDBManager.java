package ddwucom.mobile.ddwubox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginDBManager {
    LoginDBHelper loginDBHelper = null;
    Cursor cursor = null;


    public LoginDBManager(Context context) {
        loginDBHelper = new LoginDBHelper(context);
    }

    public boolean modify(String reservation, String userId) {
        SQLiteDatabase sqLiteDatabase = loginDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(LoginDBHelper.COL_RESERVATION, reservation);

        String whereClause = LoginDBHelper.COL_USERID + "=?";
        String[] whereArgs = new String[] { userId };

        int result = sqLiteDatabase.update(LoginDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        loginDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    public String checkAlreadyReserv(String userId, String mName) {
        SQLiteDatabase db = loginDBHelper.getReadableDatabase();
        String result = "noR";

       Cursor cursor = db.rawQuery("SELECT * FROM " + LoginDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_USERID));
            String name = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_MOVIE));
            String reserv = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_RESERVATION));

            if (id.equals(userId)) {
                if(name.equals(mName)) {
                    if (reserv == null) {
                        result = "noR";
                    }
                    else {
                        result = reserv;
                    }
                }
            }
        }
        return result;
    }





        /*
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_USERID));
            if (id.equals(userId)) {
                String name = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_MOVIE));
                if(name.equals(mName)) {
                    if(cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_RESERVATION)).getBytes().length <= 0 || !(cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_RESERVATION)).equals("")))
                        return cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_RESERVATION));
                }
            }
        }
        return "noR";

    }

         */

    public ArrayList<Login> getAllUser() {
        ArrayList<Login> loginList = new ArrayList<Login>();
        SQLiteDatabase db = loginDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LoginDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            if(cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_REVIEW)).length() != 0) {
                long id = cursor.getInt(cursor.getColumnIndex(LoginDBHelper.COL_ID));
                String userId = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_USERID));
                String userPw = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_PW));
                String movie = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_MOVIE));
                String review = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_REVIEW));
                String reservation = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_RESERVATION));
                loginList.add(new Login(id, userId, userPw, movie, review, reservation));
            }
        }
        cursor.close();
        loginDBHelper.close();
        return loginList;
    }

    public ArrayList<Login> getThisMovieReviewUser(String mName) {
        ArrayList<Login> loginList = new ArrayList<Login>();
        SQLiteDatabase db = loginDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LoginDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            if(mName.equals(cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_MOVIE)))) {
                /*
                if (cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_REVIEW)).length() != 0) {
                    long id = cursor.getInt(cursor.getColumnIndex(LoginDBHelper.COL_ID));
                    String userId = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_USERID));
                    String userPw = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_PW));
                    String review = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_REVIEW));
                    String reservation = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_RESERVATION));
                    loginList.add(new Login(id, userId, userPw, mName, review, reservation));
                }

                 */
                if (cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_REVIEW)) != null) {
                    long id = cursor.getInt(cursor.getColumnIndex(LoginDBHelper.COL_ID));
                    String userId = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_USERID));
                    String userPw = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_PW));
                    String review = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_REVIEW));
                    String reservation = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_RESERVATION));
                    loginList.add(new Login(id, userId, userPw, mName, review, reservation));
                }
            }
        }
        cursor.close();
        loginDBHelper.close();
        return loginList;
    }

    public boolean addNewUser(Login newLogin) {
        SQLiteDatabase db = loginDBHelper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(LoginDBHelper.COL_USERID, newLogin.getUserId());
        value.put(LoginDBHelper.COL_PW, newLogin.getUserPw());
        value.put(LoginDBHelper.COL_MOVIE, newLogin.getMovie());
        value.put(LoginDBHelper.COL_REVIEW, newLogin.getReview());
        value.put(LoginDBHelper.COL_RESERVATION, newLogin.getReservation());


//      insert 메소드를 사용할 경우 데이터 삽입이 정상적으로 이루어질 경우 1 이상, 이상이 있을 경우 0 반환 확인 가능
        long count = db.insert(LoginDBHelper.TABLE_NAME, null, value);
        loginDBHelper.close();
        if (count > 0) return true;
        return false;
    }

    public String searchUser(String userId, String userPW) {
        SQLiteDatabase sqLiteDatabase = loginDBHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + LoginDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_USERID));
            if (id.equals(userId)) {
                String pw = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_PW));
                if(userPW.equals(pw))
                    return "true";
                else
                    return "failPW";
            }
        }
        return "fail";
    }

    public String newUserReview(String userId, String movieName) {
        SQLiteDatabase sqLiteDatabase = loginDBHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + LoginDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_USERID));
            if(id.equals(userId)) {
                String name = cursor.getString(cursor.getColumnIndex(LoginDBHelper.COL_MOVIE));
                if(movieName.equals(name))
                    return "true";
                else
                    return "fail";
            }
        }
        return "fail";
    }


    public boolean modifyUser(String userId, String movie, String review) {
        SQLiteDatabase sqLiteDatabase = loginDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(LoginDBHelper.COL_REVIEW, review);
        row.put(LoginDBHelper.COL_MOVIE, movie);

        String whereClause = LoginDBHelper.COL_USERID + "=?";
        String[] whereArgs = new String[] { userId };

        int result = sqLiteDatabase.update(LoginDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        loginDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    //    close 수행
    public void close() {
        if (loginDBHelper != null) loginDBHelper.close();
        if (cursor != null) cursor.close();
    }
}
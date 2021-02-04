package ddwucom.mobile.ddwubox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "login.db";
    public final static String TABLE_NAME = "login_table";

    public final static String COL_ID = "_id";
    public final static String COL_USERID = "userid";
    public final static String COL_PW = "password";
    public final static String COL_MOVIE = "movie";
    //    public final static String COL_RATING = "rating";
    public final static String COL_REVIEW = "review";
    public final static String COL_RESERVATION = "reservation";

    public LoginDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    //     + COL_RATING + " TEXT, "
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " + COL_USERID + " TEXT, " + COL_PW + " TEXT, "
                + COL_MOVIE + " TEXT, " + COL_REVIEW + " TEXT, " + COL_RESERVATION + " TEXT)";

        db.execSQL(sql);
        db.execSQL("insert into " + TABLE_NAME + " values (null, 'sky101303', 'sky4250513', '아이언맨', '그냥 그래씀', null);");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, 'sky101303', 'sky4250513', '아이언맨', '그냥 그래씀', '2008-04-30');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '20180977', 'sky4250513', '라라랜드', '미친사랑해 너무좋아요', '2018-10-31');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '0310mir', 'sky4250513', '스타 이즈 본', '마이갓 내가 제일 좋아해요 갓댐 맨', '2019-9-20');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
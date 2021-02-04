package ddwucom.mobile.ddwubox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

public class MovieDBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "ddwubox.db";
    public final static String TABLE_NAME = "movie_table";

    public final static String COL_ID = "_id";
    public final static String COL_NUM = "num";
    public final static String COL_IMAGE = "mvImage";
    public final static String COL_GENRE = "genre";
    public final static String COL_MOVIE = "movie";
    public final static String COL_ACTOR = "actor";
    public final static String COL_DIRECTOR = "director";
    public final static String COL_DATE = "date";
    public final static String COL_RATING = "rating";
    public final static String COL_PLOT = "plot";
    public final static String COL_RESERVATION = "reservation";

    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, "
                + COL_NUM + " TEXT, " + COL_IMAGE + " INTEGER, " + COL_GENRE + " TEXT, " + COL_MOVIE + " TEXT, " + COL_ACTOR + " TEXT, "
                + COL_DIRECTOR + " TEXT, " + COL_DATE + " TEXT, " + COL_RATING + " TEXT, " + COL_PLOT + " TEXT, " + COL_RESERVATION + " TEXT)";

//        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
//                COL_NUM + " TEXT, " + COL_GENRE + " TEXT, " + COL_MOVIE + " TEXT)";
        db.execSQL(sql);

//        int ironman = R.mipmap.ionman;
//        int batman = R.mipmap.batman;
//        int hulk = R.mipmap.hulk;
//        int wonderwoman = R.mipmap.wonderwoman;

//        db.execSQL("insert into " + TABLE_NAME + " values (null, '12345', '마블히어로', '아이언맨');");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '23456', 'DC히어로', '배트맨');");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '34567', '마블히어로', '헐크');");
//        db.execSQL("insert into " + TABLE_NAME + " values (null, '45678', 'DC히어로', '원더우먼');");
//
        db.execSQL("insert into " + TABLE_NAME + " values (null, '12345', '"+R.mipmap.ironman+"', '마블히어로', '아이언맨', '로버트 다우니 주니어', '존 파브로', '2008-04-30', '4.5', null, 'TRUE');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '23456', '"+R.mipmap.batman+"', 'DC히어로', '배트맨', '마이클 키튼, 잭 니콜슨', '팀 버튼', '1990-07-07', 1.0, '심야 영웅 배트맨과 미래 악당 죠커의 정면 대결과 미모의 기자 비키와의 어드벤쳐 로맨스가 시작된다.', 'FALSE');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '34567', '"+R.mipmap.hulk+"', '마블히어로', '헐크', '에릭 바나', null, '2003-07-04', '4.0', null, 'FALSE');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '45678', '"+R.mipmap.wonderwoman+"', 'DC히어로', '원더우먼', '갤 가돗', null, '2017-05-31', 2.5, null, 'FALSE');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '45678', '"+R.mipmap.starisborn+"', '멜로', '스타이즈본', '레이디 가가', '브래들리 쿠퍼', '2018-10-09', '5.0', null, 'TRUE');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '45678', '"+R.mipmap.lalaland+"', '뮤지컬', '라라랜드', '엠마 스톤', null , '2016-12-07', '4.5', '인생에서 가장 빛나는 순간 만난 두 사람은 미완성인 서로의 무대를 만들어가기 시작한다.', 'TRUE');");

//        db.execSQL("insert into " + TABLE_NAME + " values (null,'"+ironman+"', '마블히어로', '아이언맨');");
//        db.execSQL("insert into " + TABLE_NAME + " values (null,'"+batman+"', 'DC히어로', '배트맨');");
//        db.execSQL("insert into " + TABLE_NAME + " values (null,'"+hulk+"', '마블히어로', '헐크');");
//        db.execSQL("insert into " + TABLE_NAME + " values (null,'"+wonderwoman+"', 'DC히어로', '원더우먼');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

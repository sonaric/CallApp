package com.example.stanislav.programcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Stanislav on 24.02.2017.
 */

public class DB {
   /* private static final String DB_NAME="contactdb";
    private static final int DB_VERSION=1;
    private static final String DB_TABLE="contacts";

    public  static final String COLUMN_ID="_id";
    public  static final String COLUMN_NAME="name";
    public  static final String COLUMN_NUMBER="number";
    public  static final String COLUMN_SEX="sex";

    private static final String DB_CREATE="create table "+
            DB_TABLE+"("+
            COLUMN_ID+" integer primary key autoincrement, "+
            COLUMN_NAME+" text, "+
            COLUMN_NUMBER+" text, "+
            COLUMN_SEX+" text);";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx=ctx;
    }

    public void open() {
        mDBHelper=new DBHelper( mCtx, DB_NAME, null, DB_VERSION );
        mDB=mDBHelper.getWritableDatabase();
    }

    public void close(){
        if(mDBHelper!=null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query( DB_TABLE,null,null,null,null,null,null );
    }

    public void addContact(String name, String number, String sex){
        ContentValues cv=new ContentValues(  );
        cv.put( COLUMN_NAME, name );
        cv.put( COLUMN_NUMBER, number );
        cv.put( COLUMN_SEX, sex );
        mDB.insert( DB_TABLE, null, cv );
    }

    public void delContact(long id) {
        mDB.delete( DB_TABLE, COLUMN_ID+" = ", null );
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context,name,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL( DB_CREATE );

            ContentValues cv=new ContentValues( );
            for(int i=1; i<5; i++) {
                cv.put(COLUMN_NAME, "Name "+i);
                cv.put( COLUMN_NUMBER, i );
                cv.put( COLUMN_SEX, "1");
                db.insert( DB_TABLE, null, cv );
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
*/
   private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_TXT = "txt";
    public static final String COLUMN_NUMBER="phone";
    public static final String COLUMN_SEX="sex";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_IMG + " integer, " +
                    COLUMN_TXT + " text, " +
                    COLUMN_NUMBER + " text, " +
                    COLUMN_SEX + " text" +
                    ");";

    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public Cursor getSearchData(CharSequence s) {
        String selection=COLUMN_TXT+" = ?";
        String[] selectionArgs=new String[]{s.toString()};
        return mDB.query(DB_TABLE, null, selection, selectionArgs, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String txt, int img, String number, String sex) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, txt);
        cv.put(COLUMN_IMG, img);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_SEX, sex);
        mDB.insert(DB_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void updateRec(long idRec, String txt, int img, String number, String sex){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, txt);
        cv.put(COLUMN_IMG, img);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_SEX, sex);
        mDB.update( DB_TABLE, cv, COLUMN_ID+" = " +idRec, null);
    }

    public Cursor searchById(long id){
        return mDB.query(DB_TABLE, null, COLUMN_ID+" = "+id, null, null, null, null);
    }
    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            /*ContentValues cv = new ContentValues();
            for (int i = 1; i < 50; i++) {
                cv.put(COLUMN_TXT, "sometext " + i);
                if((i%2)==0) {
                    cv.put(COLUMN_IMG, R.drawable.ic_action_contact_man);
                    cv.put(COLUMN_SEX, "man");
                }else{
                    cv.put(COLUMN_IMG, R.drawable.ic_action_contact_woman);
                    cv.put(COLUMN_SEX, "woman");
                }
                cv.put(COLUMN_NUMBER, "38099" + i+"2"+i+"554"+i);
                db.insert(DB_TABLE, null, cv);

            }*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}


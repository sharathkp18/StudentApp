package kps.example.studentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class sqliteDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "studentApp.db";

    public static final String USERS_TABLE_NAME = "userTable";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_NAME = "userName";
    public static final String USER_COLUMN_PHONE = "phone";
    public static final String USER_COLUMN_PASSWORD = "password";

    public static final String STUD_TABLE_NAME = "studentTable";
    public static final String STUD_COLUMN_ID = "id";
    public static final String STUD_COLUMN_NAME = "studentName";
    public static final String STUD_COLUMN_IMAGE = "image";
    public static final String STUD_COLUMN_CLASS = "class";
    public static final String STUD_COLUMN_SECTION = "section";
    public static final String STUD_COLUMN_SCHOOL = "school";
    public static final String STUD_COLUMN_GENDER = "gender";
    public static final String STUD_COLUMN_DOB = "dateOfBirth";
    public static final String STUD_COLUMN_BLOOD = "bloodGroup";
    public static final String STUD_COLUMN_FATHER = "fatherName";
    public static final String STUD_COLUMN_MATHER = "matherName";
    public static final String STUD_COLUMN_PARCONTACT = "parentContact";
    public static final String STUD_COLUMN_ADD1 = "address1";
    public static final String STUD_COLUMN_ADD2 = "address2";
    public static final String STUD_COLUMN_CITY = "city";
    public static final String STUD_COLUMN_STATE = "state";
    public static final String STUD_COLUMN_ZIP = "zip";
    public static final String STUD_COLUMN_EMECONTACT = "emergencyContact";
    public static final String STUD_COLUMN_LOCATION = "location";
    private HashMap hp;

    public sqliteDB(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table "+USERS_TABLE_NAME+ "("+USER_COLUMN_ID+" integer primary key," +
                " "+USER_COLUMN_NAME+" text," +
                " "+USER_COLUMN_PHONE+" text," +
                " "+USER_COLUMN_PASSWORD+" text)");

        db.execSQL("create table "+STUD_TABLE_NAME+ "("+STUD_COLUMN_ID+" integer primary key," +
                " "+STUD_COLUMN_NAME+" text," +
                " "+STUD_COLUMN_IMAGE+" BLOB," +
                " "+STUD_COLUMN_CLASS+" text," +
                " "+STUD_COLUMN_SECTION+" text," +
                " "+STUD_COLUMN_SCHOOL+" text," +
                " "+STUD_COLUMN_GENDER+" text," +
                " "+STUD_COLUMN_DOB+" text," +
                " "+STUD_COLUMN_BLOOD+" text," +
                " "+STUD_COLUMN_FATHER+" text," +
                " "+STUD_COLUMN_MATHER+" text," +
                " "+STUD_COLUMN_PARCONTACT+" text," +
                " "+STUD_COLUMN_ADD1+" text," +
                " "+STUD_COLUMN_ADD2+" text," +
                " "+STUD_COLUMN_CITY+" text," +
                " "+STUD_COLUMN_STATE+" text," +
                " "+STUD_COLUMN_ZIP+" text," +
                " "+STUD_COLUMN_EMECONTACT+" text," +
                " "+STUD_COLUMN_LOCATION+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+USERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+STUD_TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser (String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_NAME, name);
        contentValues.put(USER_COLUMN_PHONE, phone);
        contentValues.put(USER_COLUMN_PASSWORD, password);
        db.insert(USERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USERS_TABLE_NAME+" where "+USER_COLUMN_NAME+"="+username+"", null );
        return res;
    }
    public boolean checkUserData(String username,String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USERS_TABLE_NAME+" where "+USER_COLUMN_NAME+"='"+username+"' AND "+USER_COLUMN_PASSWORD+"='"+password+"'", null );
        if(res.moveToNext()){
            return true;
        }
        return false;
    }

    public int numberOfUserRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateUser (Integer id, String name, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        db.update(USERS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteUser (String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USERS_TABLE_NAME,USER_COLUMN_NAME +"= ? ",
                new String[] { userName});
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ USERS_TABLE_NAME+"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(USER_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    public boolean addStudent (String name, byte[] img, String studClass, String section, String school,
                               String gender, String dob, String blood, String father, String mother,
                               String parContact, String add1, String add2, String city, String state,
                               String zip, String emeContact, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUD_COLUMN_NAME, name);
        contentValues.put(STUD_COLUMN_IMAGE, img);
        contentValues.put(STUD_COLUMN_CLASS, studClass);
        contentValues.put(STUD_COLUMN_SECTION, section);
        contentValues.put(STUD_COLUMN_SCHOOL, school);

        contentValues.put(STUD_COLUMN_GENDER, gender);
        contentValues.put(STUD_COLUMN_DOB, dob);
        contentValues.put(STUD_COLUMN_BLOOD, blood);
        contentValues.put(STUD_COLUMN_FATHER, father);
        contentValues.put(STUD_COLUMN_MATHER, mother);

        contentValues.put(STUD_COLUMN_PARCONTACT, parContact);
        contentValues.put(STUD_COLUMN_ADD1, add1);
        contentValues.put(STUD_COLUMN_ADD2, add2);
        contentValues.put(STUD_COLUMN_CITY, city);
        contentValues.put(STUD_COLUMN_STATE, state);

        contentValues.put(STUD_COLUMN_ZIP, zip);
        contentValues.put(STUD_COLUMN_EMECONTACT, emeContact);
        contentValues.put(STUD_COLUMN_LOCATION, location);

        if(db.insert(STUD_TABLE_NAME, null, contentValues)<0){
            return false;
        }else{
        return true;}
     }
    public Cursor getAllStudent() {

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ STUD_TABLE_NAME+" ", null );
        return res;
    }
}

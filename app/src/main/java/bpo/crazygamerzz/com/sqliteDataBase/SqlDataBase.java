<<<<<<< HEAD
package bpo.crazygamerzz.com.sqliteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bpo.crazygamerzz.com.pojo.NumberData;
import bpo.crazygamerzz.com.pojo.SqlitePojo;

/**
 * Created by Kumar on 12/16/2017.
 */

public class SqlDataBase extends SQLiteOpenHelper {

    public static int DATA_BASE_VERSION=3;
    public static String DATA_BASE_NAME="numbers";
    public static String TABLE_NAME="clientnumber";
    public static String CONTACT_ID="id";
    public static String CONTACT_NUMBER="numbers";
    public static String SERVER_NUMBER_ID="serverid";
    public static String NUMBER_FLAG = "number_flag";

    public SqlDataBase(Context context)
    {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqLiteDatabase= "CREATE TABLE " + TABLE_NAME + "("+ CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+SERVER_NUMBER_ID + " TEXT,"+ CONTACT_NUMBER + " TEXT,"+NUMBER_FLAG+" TEXT)";
        db.execSQL(sqLiteDatabase);
        Log.d("Table_Created","Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS numbers");
        onCreate(db);
    }

    public boolean addNumbers(ContentValues contentValues)
    {
        Log.d("","");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        if (sqLiteDatabase.insert(TABLE_NAME,null,contentValues) !=-1 )
        {
            Log.d("ContactNumber","added");
            return true;
        }
        else
        {
            Log.d("ContactNumber","Not Working");
        }
        return false;
    }

    public boolean addCallForwardedNumber(ContentValues contentValues, String serverId)
    {
        Log.d("server_id_",""+serverId);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +SERVER_NUMBER_ID+"=?", new String[]{serverId});

        if(cursor != null && cursor.getCount() == 0){
            if (sqLiteDatabase.insert(TABLE_NAME,null,contentValues) !=-1 )
            {
                Log.d("tst_ContactNumber","added");
                return true;
            }
            else
            {
                Log.d("tst_ContactNumber","Not Working");
            }
        }
        return false;
    }





    public List<SqlitePojo> getAllContactNumber()
    {
        List<SqlitePojo> sqlitePojos=new ArrayList<SqlitePojo>();
        String s="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(s,null);

        if (cursor.moveToFirst())
        {
            do {
                Log.d("Id: ",""+cursor.getString(0));
                Log.d("server_Id: : ",""+cursor.getString(1));
                Log.d("Number: ",""+cursor.getString(2));
                /*SqlitePojo sqlitePojo = new SqlitePojo();
                sqlitePojo.setId(cursor.getString(0));
                sqlitePojo.setNumber(cursor.getString(1));
                sqlitePojos.add(sqlitePojo);*/
            }while (cursor.moveToNext());
        }
        return sqlitePojos;
    }

    public int checkDataBaseDump(){

        String checkDbQuery = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(checkDbQuery,null);
        if (cursor.moveToFirst())
        {
           return cursor.getCount();
        }
        return 0;
    }

    public SqlitePojo getPhoneNumberFromDB() {
        SQLiteDatabase database = this.getReadableDatabase();

        // NUMBER_FLAG defines type of number - 0 -> normal contact from number_dump; 1 -> contacted number; 2 -> remainder number; 3 -> call transfered number
        Cursor cursor = database.rawQuery("SELECT " + CONTACT_ID + "," + CONTACT_NUMBER + " FROM " + TABLE_NAME + " WHERE " + " NUMBER_FLAG = ?", new String[]{"3"});
        if(cursor != null && cursor.getCount() != 0){
            if (cursor != null && cursor.getCount() != 0){
                cursor.moveToFirst();
                SqlitePojo numberPozo = new SqlitePojo();
                numberPozo.setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                numberPozo.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                numberPozo.setManualCallFlag(false);
                numberPozo.setForwardedCall(true);
                ContentValues values = new ContentValues();
                values.put(SqlDataBase.NUMBER_FLAG,"1");
                if(updateNumberFlag(values,cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID))) != 0){
                    return numberPozo;
                }else {
                    Log.d("tst_update_","un successful");
                }
            }
        }else {
            cursor = database.rawQuery("SELECT " + CONTACT_ID + "," + CONTACT_NUMBER + " FROM " + TABLE_NAME + " WHERE " + " NUMBER_FLAG = ?", new String[]{"0"});
            if (cursor != null && cursor.getCount() != 0){
                Log.d("cursor_count: ", String.valueOf(cursor.getCount()));
                cursor.moveToFirst();
                SqlitePojo numberPozo = new SqlitePojo();
                numberPozo.setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                numberPozo.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                numberPozo.setManualCallFlag(false);
                numberPozo.setForwardedCall(false);
                return numberPozo;
            }else {
                cursor = database.rawQuery("SELECT " + CONTACT_ID + "," + CONTACT_NUMBER + " FROM " + TABLE_NAME + " WHERE " + " NUMBER_FLAG = ?", new String[]{"2"});
                if (cursor != null && cursor.getCount() != 0){
                    cursor.moveToFirst();
                    SqlitePojo numberPozo = new SqlitePojo();
                    numberPozo.setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                    numberPozo.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                    numberPozo.setManualCallFlag(false);
                    numberPozo.setForwardedCall(false);
                    ContentValues values = new ContentValues();
                    values.put(SqlDataBase.NUMBER_FLAG,"1");
                    if(updateNumberFlag(values,cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID))) != 0){
                        return numberPozo;
                    }else {
                        Log.d("tst_update_","un successful");
                    }
                }
            }
        }

        return null;
    }

    public int updateNumberFlag(ContentValues values,String Id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.update(TABLE_NAME,values,""+CONTACT_ID+"=?",new String[]{Id});

    }

    public void testValues() {
        SQLiteDatabase database = this.getReadableDatabase();
        //Cursor cursor = database.rawQuery("SELECT "+CONTACT_ID+","+CONTACT_NUMBER+","+CONTACT_NUMBER+" FROM "+TABLE_NAME+" WHERE "+" NUMBER_FLAG = ?",new String[]{"1"});
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Log.d("tst_CONTACT_ID ",cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                Log.d("tst_NUMBER ",cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                Log.d("tst_FLAG ",cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_FLAG)));
            }while (cursor.moveToNext());
//            SqlitePojo numberPozo = new SqlitePojo();
//            numberPozo.setId(cursor.getString(0));
//            numberPozo.setNumber(cursor.getString(2));

        }else {
            Log.d("tst_db_","null");
        }
    }

    public int getNoFCalls(){
        SQLiteDatabase database = this.getReadableDatabase();
       // Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" NUMBER_FLAG = ?",new String[]{"1"});
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+" NUMBER_FLAG = ?",new String[]{"1"});

        if (cursor != null){
            cursor.moveToFirst();
            return cursor.getCount();
        }

        return 0;
    }
}
=======
package bpo.crazygamerzz.com.sqliteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bpo.crazygamerzz.com.pojo.SqlitePojo;

/**
 * Created by Kumar on 12/16/2017.
 */

public class SqlDataBase extends SQLiteOpenHelper {

    public static int DATA_BASE_VERSION=3;
    public static String DATA_BASE_NAME="numbers";
    public static String TABLE_NAME="clientnumber";
    public static String CONTACT_ID="id";
    public static String CONTACT_NUMBER="numbers";
    public static String SERVER_NUMBER_ID="serverid";
    public static String NUMBER_FLAG = "number_flag";

    public SqlDataBase(Context context)
    {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqLiteDatabase= "CREATE TABLE " + TABLE_NAME + "("+ CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+SERVER_NUMBER_ID + " TEXT,"+ CONTACT_NUMBER + " TEXT,"+NUMBER_FLAG+" TEXT)";
        db.execSQL(sqLiteDatabase);
        Log.d("Table_Created","Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS numbers");
        onCreate(db);
    }

    public boolean addNumbers(ContentValues contentValues)
    {
        Log.d("","");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        if (sqLiteDatabase.insert(TABLE_NAME,null,contentValues) !=-1 )
        {
            Log.d("ContactNumber","added");
            return true;
        }
        else
        {
            Log.d("ContactNumber","Not Working");
        }
        return false;
    }

    public boolean addCallForwardedNumber(ContentValues contentValues, String serverId)
    {
        Log.d("server_id_",""+serverId);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +SERVER_NUMBER_ID+"=?", new String[]{serverId});

        if(cursor != null && cursor.getCount() == 0){
            if (sqLiteDatabase.insert(TABLE_NAME,null,contentValues) !=-1 )
            {
                Log.d("tst_ContactNumber","added");
                return true;
            }
            else
            {
                Log.d("tst_ContactNumber","Not Working");
            }
        }
        return false;
    }





    public List<SqlitePojo> getAllContactNumber()
    {
        List<SqlitePojo> sqlitePojos=new ArrayList<SqlitePojo>();
        String s="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(s,null);

        if (cursor.moveToFirst())
        {
            do {
                Log.d("Id: ",""+cursor.getString(0));
                Log.d("server_Id: : ",""+cursor.getString(1));
                Log.d("Number: ",""+cursor.getString(2));
                /*SqlitePojo sqlitePojo = new SqlitePojo();
                sqlitePojo.setId(cursor.getString(0));
                sqlitePojo.setNumber(cursor.getString(1));
                sqlitePojos.add(sqlitePojo);*/
            }while (cursor.moveToNext());
        }
        return sqlitePojos;
    }

    public int checkDataBaseDump(){

        String checkDbQuery = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(checkDbQuery,null);
        if (cursor.moveToFirst())
        {
           return cursor.getCount();
        }
        return 0;
    }

    public SqlitePojo getPhoneNumberFromDB() {
        SQLiteDatabase database = this.getReadableDatabase();

        // NUMBER_FLAG defines type of number - 0 -> normal contact from number_dump; 1 -> contacted number; 2 -> remainder number; 3 -> call transfered number; 4 -> Unreachable Calls after second trail
        Cursor cursor = database.rawQuery("SELECT " + CONTACT_ID + "," + CONTACT_NUMBER + " FROM " + TABLE_NAME + " WHERE " + " NUMBER_FLAG = ?", new String[]{"3"});
        if(cursor != null && cursor.getCount() != 0){
            if (cursor != null && cursor.getCount() != 0){
                cursor.moveToFirst();
                SqlitePojo numberPozo = new SqlitePojo();
                numberPozo.setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                numberPozo.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                numberPozo.setManualCallFlag(false);
                numberPozo.setForwardedCall(true);
                ContentValues values = new ContentValues();
                values.put(SqlDataBase.NUMBER_FLAG,"1");
                if(updateNumberFlag(values,cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID))) != 0){
                    return numberPozo;
                }else {
                    Log.d("tst_update_","un successful");
                }
            }
        }else {
            cursor = database.rawQuery("SELECT " + CONTACT_ID + "," + CONTACT_NUMBER + " FROM " + TABLE_NAME + " WHERE " + " NUMBER_FLAG = ?", new String[]{"0"});
            if (cursor != null && cursor.getCount() != 0){
                Log.d("cursor_count: ", String.valueOf(cursor.getCount()));
                cursor.moveToFirst();
                SqlitePojo numberPozo = new SqlitePojo();
                numberPozo.setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                numberPozo.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                numberPozo.setManualCallFlag(false);
                numberPozo.setForwardedCall(false);
                return numberPozo;
            }else {
                cursor = database.rawQuery("SELECT " + CONTACT_ID + "," + CONTACT_NUMBER + " FROM " + TABLE_NAME + " WHERE " + " NUMBER_FLAG = ?", new String[]{"2"});
                if (cursor != null && cursor.getCount() != 0){
                    cursor.moveToFirst();
                    SqlitePojo numberPozo = new SqlitePojo();
                    numberPozo.setId(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                    numberPozo.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                    numberPozo.setManualCallFlag(false);
                    numberPozo.setForwardedCall(false);
                    ContentValues values = new ContentValues();
                    values.put(SqlDataBase.NUMBER_FLAG,"1");
                    if(updateNumberFlag(values,cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID))) != 0){
                        return numberPozo;
                    }else {
                        Log.d("tst_update_","un successful");
                    }
                }
            }
        }

        return null;
    }

    public int updateNumberFlag(ContentValues values,String Id){
        // NUMBER_FLAG defines type of number - 0 -> normal contact from number_dump; 1 -> contacted number; 2 -> remainder number; 3 -> call transfered number; 4 -> Unreachable Calls after second trail also
        SQLiteDatabase database = this.getWritableDatabase();
        return database.update(TABLE_NAME,values,""+CONTACT_ID+"=?",new String[]{Id});
    }

    public void testValues() {
        SQLiteDatabase database = this.getReadableDatabase();
        //Cursor cursor = database.rawQuery("SELECT "+CONTACT_ID+","+CONTACT_NUMBER+","+CONTACT_NUMBER+" FROM "+TABLE_NAME+" WHERE "+" NUMBER_FLAG = ?",new String[]{"1"});
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (cursor != null && cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                Log.d("tst_CONTACT_ID ",cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_ID)));
                Log.d("tst_NUMBER ",cursor.getString(cursor.getColumnIndexOrThrow(CONTACT_NUMBER)));
                Log.d("tst_FLAG ",cursor.getString(cursor.getColumnIndexOrThrow(NUMBER_FLAG)));
            }while (cursor.moveToNext());
        }else {
            Log.d("tst_db_","null");
        }
    }

    public int getNoFCalls(){
        SQLiteDatabase database = this.getReadableDatabase();
       // Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" NUMBER_FLAG = ?",new String[]{"1"});
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+" NUMBER_FLAG = ?",new String[]{"1"});

        if (cursor != null){
            cursor.moveToFirst();
            return cursor.getCount();
        }

        return 0;
    }

    public int getTotalNoFCalls() {
        SQLiteDatabase database = this.getReadableDatabase();
        // Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" NUMBER_FLAG = ?",new String[]{"1"});
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        if (cursor != null){
            cursor.moveToFirst();
            return cursor.getCount();
        }

        return 0;
    }


    public int getTotalNoFinshedCalls() {
        int totalNormalCalls = 0,totalNtwrkBusyCalls = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE NUMBER_FLAG = ?",new String[]{"1"});

        if (cursor != null){

            cursor.moveToFirst();
            totalNormalCalls = cursor.getCount();
            cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE NUMBER_FLAG = ?", new String[]{"2"});
            if(cursor != null){
                cursor.moveToFirst();
                totalNtwrkBusyCalls = cursor.getCount();
            }
            return (totalNormalCalls+totalNtwrkBusyCalls);
        }

        return 0;
    }

    public boolean checkNdClearDatabase() {

        SQLiteDatabase database = this.getWritableDatabase();
        int totalNoCalls = getTotalNoFCalls();
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+" NUMBER_FLAG = ?",new String[]{"1"});

        if (cursor != null){
            cursor.moveToFirst();
            int noFFinishedCalls = cursor.getCount();
            if (totalNoCalls == noFFinishedCalls)
            {
                return database.delete(TABLE_NAME,null,null) > 0;
            }else {
                Log.d("db_not_","cleared");
            }
        }
        database.close();
        return false;
    }

    public int getNormalCalls() {
        SQLiteDatabase database = this.getReadableDatabase();
       Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE NUMBER_FLAG = ?", new String[]{"0"});

        if (cursor != null){
            cursor.moveToFirst();
            return cursor.getCount();
        }
        return 0;
    }
}
>>>>>>> Added no of calls connected and other stuff

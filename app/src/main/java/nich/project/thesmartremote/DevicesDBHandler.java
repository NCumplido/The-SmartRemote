package nich.project.thesmartremote;

/*
Some code from:
Dr. Tom Owen: CSC306
Swansea University
thenewboston: https://www.youtube.com/playlist?list=PL6gx4Cwl9DGBsvRxJJOzG4r4k_zLKrnxl
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DevicesDBHandler extends SQLiteOpenHelper {

    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "devices.db";
    private static final String TABLE_DEVICES = "Devices";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DEVICE_NAME = "deviceName";
    private static final String COLUMN_DEVICE_BEARING = "deviceBearing";

    public DevicesDBHandler(@Nullable Context context, @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_DEVICES = "CREATE TABLE " + TABLE_DEVICES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_DEVICE_NAME + " TEXT, " +
                COLUMN_DEVICE_BEARING + " INTEGER " +
                ");"; //From Tom's code samples, this is last line of create table query + Student.KEY_email + " TEXT )";

        db.execSQL(CREATE_TABLE_DEVICES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_DEVICES);
        onCreate(db);
    }

    public void addBearingDevice(Device device){

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DEVICE_NAME, device.getName());
        contentValues.put(COLUMN_DEVICE_BEARING, device.getBearing());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_DEVICES, null, contentValues);
        db.close();

    }

    /*
        public void update(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_age, student.age);
        values.put(Student.KEY_email,student.email);
        values.put(Student.KEY_name, student.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
        db.close(); // Closing database connection
    }
     */

    public void deleteBearingDevice(String deviceName){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_DEVICES + " WHERE " + COLUMN_DEVICE_NAME + "=\"" + deviceName + "\";");

    }

    public void clearTableBearingDevice(){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE " + TABLE_DEVICES + ";");

    }

    public ArrayList dbToList(){

        String deviceName = "";
        int deviceBearing;
        ArrayList<Device> deviceList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_DEVICES + " WHERE 1";

        //Cursor to point to location in the results
        Cursor cursor = db.rawQuery(query, null);
        //Move to first row of results
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            if(cursor.getString(cursor.getColumnIndex("deviceName"))!=null){
                deviceName = cursor.getString(cursor.getColumnIndex("deviceName"));
                deviceBearing = cursor.getInt(cursor.getColumnIndex("deviceBearing"));

                Device device = new Device(deviceName, deviceBearing);
                deviceList.add(device);
            }
        }

        db.close();
        return deviceList;

    }
}

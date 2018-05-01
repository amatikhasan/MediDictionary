package com.example.daffodil.medidictionary;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 3/21/2018.
 */

public class DBExternal extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH;

    //private static String DB_NAME = "lsumdserver_final.db";
    private static String DB_NAME= "lsumd.db";

    private static String Table_Words = "mwords";
    private static String NAme = "MedicalWord";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @paramcontext
     */
    public DBExternal(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
        //call open DB
        openDataBase();
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            //do nothing - database already exist
        } else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //call createDB
        try {
            createDataBase();
            Log.d(TAG, "openDataBase: createDB called");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.


    //method to get word id and name from db to display in list at Main activity
    public ArrayList<WordData> showWord() {
        //SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select id,word from " + Table_Words + " order by word";
        Cursor cur = myDataBase.rawQuery(query, null);
        ArrayList<WordData> data = new ArrayList<>();
        Bitmap bitmap = null;

        cur.moveToFirst();
        Log.d(TAG, "Cur Value: " + cur.toString());
        if (cur.moveToFirst()) {
            do {
                int id = cur.getInt(0);
                String name = cur.getString(1);
                data.add(new WordData(id, name));
            } while (cur.moveToNext());
        }
        cur.close();
        return data;
    }

    //method to get word details from db to display at WordDetails activity
    public ArrayList<WordData> showWordDetail(int id) {
        //SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + Table_Words + " where id=' " + id + "' ";
        Cursor cur = myDataBase.rawQuery(query, null);
        ArrayList<WordData> data = new ArrayList<>();

        cur.moveToFirst();
        Log.d(TAG, "Cur Value: " + cur.toString());
        if (cur.moveToFirst()) {
            do {
                id = cur.getInt(0);
                String word = cur.getString(1);
                String definition = cur.getString(2);
                String resource = cur.getString(3);
                String imageByte = cur.getString(4);

                data.add(new WordData(id, word, definition, resource, imageByte));
            } while (cur.moveToNext());
        }
        cur.close();
        Log.d(TAG, "showWords: " + data.get(0).getWord());
        return data;
    }


}
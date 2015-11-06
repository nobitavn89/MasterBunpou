package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/10/23.
 */
public class MasterBunpouDbHelper extends SQLiteOpenHelper {
    private static final String TAG = MasterBunpouDbHelper.class.getSimpleName();
    private final Context mContext;
    //for sql command
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + BunpouEntry.TABLE_NAME + " (" +
            BunpouEntry._ID + " INTEGER PRIMARY KEY," +
            BunpouEntry.COLUMN_BUNPOU_ENTRY_ID + INTEGER_TYPE + COMMA_SEP +
            BunpouEntry.COLUMN_BUNPOU_ENTRY_TYPE + TEXT_TYPE + COMMA_SEP +
            BunpouEntry.COLUMN_BUNPOU_ENTRY_TITLE + TEXT_TYPE + COMMA_SEP +
            BunpouEntry.COLUMN_BUNPOU_ENTRY_MEANING + TEXT_TYPE + COMMA_SEP +
            BunpouEntry.COLUMN_BUNPOU_ENTRY_USAGE + TEXT_TYPE + COMMA_SEP +
            BunpouEntry.COLUMN_BUNPOU_ENTRY_EXAMPLE + TEXT_TYPE + COMMA_SEP +
            BunpouEntry.COLUMN_BUNPOU_ENTRY_BOOKMARK + INTEGER_TYPE +
            " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + BunpouEntry.TABLE_NAME;

    /*SQLite Helper*/
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = Constants.DATABASE_NAME;
    public static final String DATABASE_PATH = "/data/data/masterbunpou.nobita.com.masterbunpou/databases/";

    public MasterBunpouDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
//        File dbFile = mContext.getDatabasePath(DATABASE_NAME);
        File dbFile = new File(DATABASE_PATH+DATABASE_NAME);
        if(dbFile.exists()) {
            Log.i(TAG, "db already exist");
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //we will copy database from assets folder, so leave onCreate empty
//        db.execSQL(SQL_CREATE_ENTRIES);
        try{
            db = mContext.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            db.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private void copyDatabase() throws IOException {
        InputStream isStream = mContext.getAssets().open(DATABASE_NAME);
        //getdb path
        OutputStream outStream = new FileOutputStream(DATABASE_PATH+DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = isStream.read(buffer)) > 0) {
            outStream.write(buffer, 0 , length);
        }
        outStream.flush();
        outStream.close();
        isStream.close();
        Log.i(TAG, "a new db is copied to device");
    }
}

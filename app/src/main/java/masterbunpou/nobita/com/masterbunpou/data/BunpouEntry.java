package masterbunpou.nobita.com.masterbunpou.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import masterbunpou.nobita.com.masterbunpou.utils.Constants;

/**
 * Created by nobitavn89 on 15/10/23.
 */
public abstract class BunpouEntry implements BaseColumns {

    /* inner class that defines the table contents */
    public static final String TABLE_NAME = "master_bunpou";
    public static final String COLUMN_BUNPOU_ENTRY_ID = "entry_id";
    public static final String COLUMN_BUNPOU_ENTRY_TYPE = "entry_type";
    public static final String COLUMN_BUNPOU_ENTRY_TITLE = "entry_title";
    public static final String COLUMN_BUNPOU_ENTRY_MEANING = "entry_meaning";
    public static final String COLUMN_BUNPOU_ENTRY_USAGE = "entry_usage";
    public static final String COLUMN_BUNPOU_ENTRY_EXAMPLE = "entry_example";
    public static final String COLUMN_BUNPOU_ENTRY_BOOKMARK = "entry_bookmark";
}

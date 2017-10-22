package rayanalkhelaiwi.me.habittrackerappdb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rayanalkhelaiwi.me.habittrackerappdb.database.HabitContract.HabitEntry;

/**
 * Created by Rean on 10/21/2017.
 */

public class HabitDBHelper extends SQLiteOpenHelper {


    //To implement the following schema: CREATE TABLE habits(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, action INTEGER NOT NULL DEFAULT 0);
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + HabitEntry.TABLE_NAME + " (" + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, " + HabitEntry.COLUMN_HABIT_ACTION + " INTEGER NOT NULL DEFAULT 0);";

    //To implement deletion of the table, if it's exists by the given name
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "habit_tracker.db";

    //Constructor
    public HabitDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //To create the table if it's not created at the beginning
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //To update the rows of the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

package rayanalkhelaiwi.me.habittrackerappdb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import rayanalkhelaiwi.me.habittrackerappdb.database.HabitContract.HabitEntry;
import rayanalkhelaiwi.me.habittrackerappdb.database.HabitDBHelper;

public class MainActivity extends AppCompatActivity {

    //Private variables needed
    private HabitDBHelper habitDBHelper;
    private Button mDummyInsertion;
    private Button mEnterInsertion;

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the variables in the onCreate method
        habitDBHelper = new HabitDBHelper(this);

        mDummyInsertion = (Button) findViewById(R.id.btn_dummy_insertion);
        mEnterInsertion = (Button) findViewById(R.id.btn_enter_insetion);

        //OnClick to insert the dummy data and display the table
        mDummyInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insert();
                displayDBInfo();
            }
        });

        //OnClick to move to the edit habit activity
        mEnterInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        //display method
        displayDBInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDBInfo();
    }

    //A readRow method that returns a Cursor object with the data from the db
    private Cursor readRow() {

        SQLiteDatabase db = habitDBHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_ACTION
        };

        String selection = HabitEntry.COLUMN_HABIT_NAME + "== ?";

        //"Reading the book" acts as a placeholder which selects the dummy data,
        // although you can place any other value or remove it and selection variable to view all the db content
        String[] selectionArgs = {"Reading the book"};

        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        return cursor;

    }

    //To display the content of the db as a verification step
    private void displayDBInfo() {

        Cursor cursor = readRow();

        TextView displayDB = (TextView) findViewById(R.id.text_view);

        try {

            //Create a text view to display the content of the db as a double check
            displayDB.setText("The habit table contains " + cursor.getCount() + " habits to track." + "\n\n");

            displayDB.append(HabitEntry._ID + " - " + HabitEntry.COLUMN_HABIT_NAME + " - " + HabitEntry.COLUMN_HABIT_ACTION + "\n");

            //Get the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int actionColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_ACTION);

            //Move to all rows of the table until there is none, which moveToNext returns false
            while (cursor.moveToNext()) {

                //Display the values from each column of the current row in the cursor in the TextView
                displayDB.append("\n" +
                        cursor.getInt(idColumnIndex) + " - " +
                        cursor.getString(nameColumnIndex) + " - " +
                        cursor.getInt(actionColumnIndex));
            }
        } finally {

            //Close the cursor to prevent any memory leaks
            cursor.close();
        }
    }

    //Inserts a dummy data
    private void insert() {

        //Declare an SQL db
        SQLiteDatabase sqLiteDatabase = habitDBHelper.getWritableDatabase();

        //Use content values to add the rows info needed (Fake data in this case)
        ContentValues contentValues = new ContentValues();

        contentValues.put(HabitEntry.COLUMN_HABIT_NAME, "Reading the book");
        contentValues.put(HabitEntry.COLUMN_HABIT_ACTION, HabitEntry.OPTION_NOT_DONE);

        //Save the data to a long decimal to check if it's saved correctly
        long dummyRowId = sqLiteDatabase.insert(HabitEntry.TABLE_NAME, null, contentValues);

        if (dummyRowId == -1) {
            Toast.makeText(this, "Error with saving the dummy habit!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Row ID: " + dummyRowId, Toast.LENGTH_SHORT).show();
        }

    }
}

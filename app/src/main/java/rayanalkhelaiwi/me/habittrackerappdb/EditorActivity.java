package rayanalkhelaiwi.me.habittrackerappdb;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rayanalkhelaiwi.me.habittrackerappdb.database.HabitContract;
import rayanalkhelaiwi.me.habittrackerappdb.database.HabitDBHelper;

/**
 * Created by Rean on 10/21/2017.
 */

public class EditorActivity extends AppCompatActivity {

    private EditText mHabitName;
    private EditText mHabitAction;
    private Button mInsert;

    private HabitDBHelper habitDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertion_activity);

        habitDBHelper = new HabitDBHelper(this);

        mInsert = (Button) findViewById(R.id.btn_insertion);

        mInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the entered data by the user to the database
                insertHabit();

                //Exit activity to the previous one
                finish();
            }
        });

        //Find all relevant views that we will need to read the user's input
        mHabitName = (EditText) findViewById(R.id.habit_entry_edit_text);
        mHabitAction = (EditText) findViewById(R.id.action_entry_edit_text);
    }

    private void insertHabit() {

        //Declare an SQL db
        SQLiteDatabase sqLiteDatabase = habitDBHelper.getWritableDatabase();

        //Get the user's input
        String dbHabitName = mHabitName.getText().toString().trim();
        int dbHabitAction = Integer.parseInt(mHabitAction.getText().toString().trim());

        //Use content values to add the rows info needed
        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, dbHabitName);
        contentValues.put(HabitContract.HabitEntry.COLUMN_HABIT_ACTION, dbHabitAction);

        //Save the data to a long decimal to check if it's saved correctly
        long rowID = sqLiteDatabase.insert(HabitContract.HabitEntry.TABLE_NAME, null, contentValues);

        if (rowID == -1) {
            Toast.makeText(this, "Error with saving the habit!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Row ID: " + rowID, Toast.LENGTH_SHORT).show();
        }
    }
}

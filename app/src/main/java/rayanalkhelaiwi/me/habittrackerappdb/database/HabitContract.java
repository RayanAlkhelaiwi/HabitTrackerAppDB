package rayanalkhelaiwi.me.habittrackerappdb.database;

import android.provider.BaseColumns;

/**
 * Created by Rean on 10/21/2017.
 */

public final class HabitContract {

    /**
     * Create a constructor that is empty to prevent creating one.
     */
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        //Name of the table
        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_ACTION = "action";

        /**
         * Constants of the habit's two options.
         */
        public static final int OPTION_NOT_DONE = 0;
        public static final int OPTION_DONE = 1;
    }
}
package sam.pillpal.models.database_contracts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sam.pillpal.models.ApplicationInstance;

import static sam.pillpal.models.database_contracts.ApplicationContract.ApplicationRow.TABLE_NAME;

public class ApplicationContract {

    public static class ApplicationRow implements BaseColumns {
        public static final String TABLE_NAME = "applications";
        public static final String COLUMN_NAME_MEDICATION_ID = "medication_id";
        public static final String COLUMN_NAME_APPLICATION_DATE = "application_date";
    }

    public static class ApplicationDbHelper extends SQLiteOpenHelper {
        private static final String SQL_CREATE_APPLICATIONS =
                "CREATE TABLE " + TABLE_NAME + "(" + ApplicationRow._ID + " INTEGER PRIMARY KEY," +
                        ApplicationRow.COLUMN_NAME_MEDICATION_ID + " INTEGER," +
                        ApplicationRow.COLUMN_NAME_APPLICATION_DATE + " DATETIME)";
        private static final String SQL_DELETE_APPLICATIONS = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;
        private int databaseVerison = 0;
        private String databaseName = "";

        public ApplicationDbHelper(Context context, int databaseVerison, String databaseName) {
            super(context, databaseName, null, databaseVerison);
            this.context = context;
            this.databaseName = databaseName;
            this.databaseVerison = databaseVerison;
        }

        public void onCreate(SQLiteDatabase db) {
            db. execSQL(SQL_CREATE_APPLICATIONS);
        }

        public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
            db.execSQL(SQL_DELETE_APPLICATIONS);
            onCreate(db);
        }

        public List<ApplicationInstance> getApplications() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            List<ApplicationInstance> applications = new ArrayList<>();
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(ApplicationRow._ID));
                long medicationId = cursor.getLong(cursor.getColumnIndexOrThrow(
                        ApplicationRow.COLUMN_NAME_MEDICATION_ID));
                Date applicationDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(
                        ApplicationRow.COLUMN_NAME_APPLICATION_DATE)));
                applications.add(new ApplicationInstance(this.context, id, medicationId,
                        applicationDate));
            }
            cursor.close();
            return applications;
        }

        public long insertApplication(long medicationId, long applicationDate) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ApplicationRow.COLUMN_NAME_MEDICATION_ID, medicationId);
            values.put(ApplicationRow.COLUMN_NAME_APPLICATION_DATE, applicationDate);
            return db.insert(TABLE_NAME, null, values);
        }
    }
}
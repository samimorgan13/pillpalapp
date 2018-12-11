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

import sam.pillpal.models.Medication;

import static sam.pillpal.models.database_contracts.MedicationContract.MedicationRow.TABLE_NAME;

public final class MedicationContract {
    private MedicationContract() {}

    public static class MedicationRow implements BaseColumns {
            public static final String TABLE_NAME = "medication";
            public static final String COLUMN_NAME_NAME = "name";
            public static final String COLUMN_NAME_DOSAGE = "dosage";
            public static final String COLUMN_NAME_INSTRUCTIONS = "instructions";
            public static final String COLUMN_NAME_REFILL_DATE = "refill_date";
    }

    public static class MedicationDbHelper extends SQLiteOpenHelper {
        private static final String SQL_CREATE_MEDICATIONS =
                "CREATE TABLE " + TABLE_NAME + "(" + MedicationRow._ID + " INTEGER PRIMARY KEY," +
                        MedicationRow.COLUMN_NAME_NAME + " TEXT," + MedicationRow.COLUMN_NAME_DOSAGE + " TEXT," +
                        MedicationRow.COLUMN_NAME_INSTRUCTIONS + " TEXT," + MedicationRow.COLUMN_NAME_REFILL_DATE + " DATETIME)";
        private static final String SQL_DELETE_MEDICATIONS = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private int databaseVerison = 0;
        private String databaseName = "";

        public MedicationDbHelper(Context context, int databaseVerison, String databaseName) {
            super(context, databaseName, null, databaseVerison);
            this.databaseName = databaseName;
            this.databaseVerison = databaseVerison;
        }

        public void onCreate(SQLiteDatabase db) {
            db. execSQL(SQL_CREATE_MEDICATIONS);
        }

        public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
            db.execSQL(SQL_DELETE_MEDICATIONS);
            onCreate(db);

        }
        public List<Medication> getMedications() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            List<Medication> medications = new ArrayList<>();
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MedicationRow._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MedicationRow.COLUMN_NAME_NAME));
                String dosage = cursor.getString(cursor.getColumnIndexOrThrow(MedicationRow.COLUMN_NAME_DOSAGE));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(MedicationRow.COLUMN_NAME_INSTRUCTIONS));
                Date refillDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(MedicationRow.COLUMN_NAME_REFILL_DATE)));
                medications.add(new Medication(id, name, dosage, instructions, refillDate));
            }
            cursor.close();
            return medications;
        }
        public void insertMedication(String name, String dosage, String instructions, Date refillDate){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MedicationRow.COLUMN_NAME_NAME, name);
            values.put(MedicationRow.COLUMN_NAME_DOSAGE, dosage);
            values.put(MedicationRow.COLUMN_NAME_INSTRUCTIONS, instructions);
            values.put(MedicationRow.COLUMN_NAME_REFILL_DATE, refillDate.getTime());
            db.insert(TABLE_NAME, null, values);
        }
    }
}

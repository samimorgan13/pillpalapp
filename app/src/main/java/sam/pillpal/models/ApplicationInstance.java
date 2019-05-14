package sam.pillpal.models;

import android.content.Context;

import java.util.Date;

import sam.pillpal.models.database_contracts.ApplicationContract;

public class ApplicationInstance {

    private final ApplicationContract.ApplicationDbHelper applicationDbHelper;
    private long id = 0;
    private long medicationId = 0;
    private Date applicationDate = null;
    private DatabaseHelper databaseHelper = null;
    private Medication medication = null;

    public ApplicationInstance(Context context, long id, long medicationId, Date applicationDate) {
        this.databaseHelper = new DatabaseHelper(context);
        this.applicationDbHelper = this.databaseHelper.getApplicationDbHelper();
        this.id = id;
        this.medicationId = medicationId;
        //this.medication = this.applicationDbHelper.getApplication(this.medicationId);
        this.applicationDate = applicationDate;
    }

    public Medication getMedication() {
        return this.medication;
    }

    public long getId() {
        return this.id;
    }

    public long getMedicationId() {
        return this.medicationId;
    }

    public Date getApplicationDate() {
        return this.applicationDate;
    }
}

package sam.pillpal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import sam.pillpal.R;
import sam.pillpal.controllers.MedicationAdapter;
import sam.pillpal.models.DatabaseHelper;
import sam.pillpal.models.Medication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewMedications;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        mRecyclerViewMedications = findViewById(R.id.recyclerViewMedications);
        mRecyclerViewMedications.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewMedications.setLayoutManager(mLayoutManager);
        mRecyclerViewMedications.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getMedicationDbHelper().insertMedication("pill","2",
                "test", (new GregorianCalendar(2018, Calendar.NOVEMBER,
                        13)).getTime());
        mAdapter = new MedicationAdapter(this);
        mRecyclerViewMedications.setAdapter(mAdapter);
    }

}
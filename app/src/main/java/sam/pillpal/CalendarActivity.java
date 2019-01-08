package sam.pillpal;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sam.pillpal.R;
import sam.pillpal.controllers.MedicationAdapter;
import sam.pillpal.models.DatabaseHelper;
import sam.pillpal.models.Medication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    public static class AddMedicationDialogFragment extends DialogFragment {

        private DatabaseHelper databaseHelper = null;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            databaseHelper = new DatabaseHelper(this.getContext());
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.add_medication_title)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            databaseHelper.getMedicationDbHelper().insertMedication("pill","2",
                                    "test", (new GregorianCalendar(2018, Calendar.NOVEMBER,
                                            13)).getTime());
                            ((CalendarActivity) getActivity()).refreshRecyclerInserted();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


    private RecyclerView mRecyclerViewMedications;
    private RecyclerView.LayoutManager mLayoutManager;
    private MedicationAdapter mAdapter;
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

        FloatingActionButton myFab = findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                (new AddMedicationDialogFragment()).show(fragmentManager, "addMedication");
            }
        });
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        mAdapter = new MedicationAdapter(this);
        mRecyclerViewMedications.setAdapter(mAdapter);
    }

    public void refreshRecyclerInserted() {
        mAdapter.resetDataSet();
    }

}
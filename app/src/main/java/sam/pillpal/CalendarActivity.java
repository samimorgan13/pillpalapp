package sam.pillpal;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import sam.pillpal.R;
import sam.pillpal.controllers.MedicationAdapter;
import sam.pillpal.models.DatabaseHelper;
import sam.pillpal.models.Medication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    public static class AddMedicationDialogFragment extends DialogFragment {

        private DatabaseHelper databaseHelper = null;
        private int MinDosage = 1; // min dosage for med
        private int MaxDosage = 7; // max dosage
        private EditText medName = null;
        private NumberPicker dosage = null;
        private Spinner freqSpinner = null;
        private TimePicker timePicker = null;
        private long originalDate = 0;

        private void createObjectFromDialog(){
            String medicationName = this.medName.getText().toString();
            int dosageNum = this.dosage.getValue();
            String dosageString = String.valueOf(dosageNum);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTimeInMillis(this.originalDate);
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
            cal.setTimeInMillis(this.timePicker.getDrawingTime());
            Date date = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DATE), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
            databaseHelper.getMedicationDbHelper().insertMedication(medicationName,dosageString,
                    "test", date);
            ((CalendarActivity) getActivity()).refreshRecyclerInserted();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.databaseHelper = new DatabaseHelper(this.getContext());
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            this.originalDate = this.getArguments().getLong(CalendarActivity.DATE_KEY);

            // layout
            GridLayout dialogLayout = new GridLayout(getActivity());
            dialogLayout.setOrientation(GridLayout.VERTICAL);

            // title row
            LinearLayout title = new LinearLayout(getActivity());
            title.setHorizontalGravity(Gravity.FILL_HORIZONTAL);
            title.setOrientation(LinearLayout.HORIZONTAL);
            title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            TextView medicationName = new TextView(getActivity());
            medicationName.setText(R.string.medication_name);
            medicationName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f));
            title.addView(medicationName);

            TextView dosageNumber = new TextView(getActivity());
            dosageNumber.setText(R.string.dosage_number);
            dosageNumber.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f));
            title.addView(dosageNumber);

            TextView frequency = new TextView(getActivity());
            frequency.setText(R.string.frequency);
            frequency.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f));
            title.addView(frequency);

            dialogLayout.addView(title);

            LinearLayout layout1 = new LinearLayout(getActivity());
            layout1.setOrientation(LinearLayout.HORIZONTAL);
            layout1.setGravity(Gravity.FILL_HORIZONTAL);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            // medication name text
            this.medName = new EditText(getActivity());
            this.medName.setHint(R.string.med_name);
            this.medName.setHintTextColor(getResources().getColor(R.color.colorDialogHint));
            this.medName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f));
            layout1.addView(this.medName);

            // dosage picker
            this.dosage = new NumberPicker(getActivity());
            this.dosage.setMinValue(MinDosage);
            this.dosage.setMaxValue(MaxDosage);
            this.dosage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f));
            layout1.addView(this.dosage);

            // frequency
            this.freqSpinner = new Spinner(getActivity());
            ArrayAdapter freqAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.freq_array, R.layout.support_simple_spinner_dropdown_item);
            this.freqSpinner.setAdapter(freqAdapter);
            this.freqSpinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f));
            layout1.addView(this.freqSpinner);

            dialogLayout.addView(layout1);

            // time picker
            this.timePicker = new TimePicker(getActivity());
            dialogLayout.addView(this.timePicker);

            builder.setView(dialogLayout);
            builder.setMessage(R.string.add_medication_title)
                    .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            AddMedicationDialogFragment.this.createObjectFromDialog();
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

    private static final String DATE_KEY = "date";

    private RecyclerView mRecyclerViewMedications;
    private CalendarView mCalendarView;
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
        mCalendarView = findViewById(R.id.calendarView);

        mRecyclerViewMedications = findViewById(R.id.recyclerViewMedications);
        mRecyclerViewMedications.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewMedications.setLayoutManager(mLayoutManager);
        mRecyclerViewMedications.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton myFab = findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Bundle data = new Bundle();
                data.putLong(CalendarActivity.DATE_KEY, CalendarActivity.this.mCalendarView.getDate());
                AddMedicationDialogFragment fragment = new AddMedicationDialogFragment();
                fragment.setArguments(data);
                (fragment).show(fragmentManager, "addMedication");
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
package sam.pillpal.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sam.pillpal.models.DatabaseHelper;
import sam.pillpal.models.Medication;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationHolder> {
    private List<Medication> mDataset;

    @NonNull
    @Override
    public MedicationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationHolder medicationHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class MedicationHolder extends RecyclerView.ViewHolder {

        TextView mNameTextView;

        MedicationHolder(TextView v) {
            super(v);
            mNameTextView = v;
        }
    }

    public MedicationAdapter(Context context){
        mDataset = (new DatabaseHelper(context)).getMedicationDbHelper().getMedications();
    }

}

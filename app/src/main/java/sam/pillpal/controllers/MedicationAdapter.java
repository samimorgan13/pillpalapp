package sam.pillpal.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sam.pillpal.R;
import sam.pillpal.models.DatabaseHelper;
import sam.pillpal.models.Medication;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationHolder> {
    private List<Medication> mDataset;
    private Context mContext;

    @NonNull
    @Override
    public MedicationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medication_view, viewGroup, false);
        return new MedicationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationHolder medicationHolder, int i) {
        medicationHolder.bindData(mDataset.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class MedicationHolder extends RecyclerView.ViewHolder {
        CardView mMedicationCard;
        TextView mNameTextView;
        TextView mTextViewDosage;
        TextView mTextViewRefillDate;

        MedicationHolder(View v) {
            super(v);
            mMedicationCard = (CardView)v.findViewById(R.id.medicationCard);
            mNameTextView = (TextView)mMedicationCard.findViewById(R.id.textViewName);
            mTextViewDosage = (TextView)mMedicationCard.findViewById(R.id.textViewDosage);
            mTextViewRefillDate = (TextView)mMedicationCard.findViewById(R.id.textViewRefillDate);
        }

        void bindData(Medication med){
            mNameTextView.setText(med.getName());
            mTextViewDosage.setText(med.getDosage());
            mTextViewRefillDate.setText(med.getRefillDate());
        }
    }

    public MedicationAdapter(Context context){
        mContext = context;
        mDataset = (new DatabaseHelper(context)).getMedicationDbHelper().getMedications();
        Log.d("Medication",mDataset.toString());
    }

}

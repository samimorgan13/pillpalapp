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
import sam.pillpal.models.ApplicationInstance;
import sam.pillpal.models.DatabaseHelper;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationHolder> {
    private List<ApplicationInstance> mDataset;
    private Context mContext;

    @NonNull
    @Override
    public ApplicationAdapter.ApplicationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.application_view, viewGroup, false);
        return new ApplicationAdapter.ApplicationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationAdapter.ApplicationHolder applicationHolder, int i) {
        applicationHolder.bindData(mDataset.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class ApplicationHolder extends RecyclerView.ViewHolder {
        CardView mApplicationCard;
        TextView mNameTextView;
        TextView mTextViewDosage;
        TextView mTextViewApplicationDate;

        ApplicationHolder(View v) {
            super(v);
            mApplicationCard = (CardView)v.findViewById(R.id.applicationCard);
            mNameTextView = (TextView) mApplicationCard.findViewById(R.id.textViewName);
            mTextViewDosage = (TextView) mApplicationCard.findViewById(R.id.textViewDosage);
            mTextViewApplicationDate =
                    (TextView) mApplicationCard.findViewById(R.id.textViewApplicationDate);
        }

        void bindData(ApplicationInstance appInst){
            mNameTextView.setText(appInst.getMedication().getName());
            mTextViewDosage.setText(appInst.getMedication().getDosage());
            mTextViewApplicationDate.setText(appInst.getMedication().getRefillDate() + " UTC");
        }
    }

    public ApplicationAdapter(Context context){
        mContext = context;
        mDataset = (new DatabaseHelper(context)).getApplicationDbHelper().getApplications();
        Log.d("Application", mDataset.toString());
    }

    public void resetDataSet() {
        mDataset = (new DatabaseHelper(mContext)).getApplicationDbHelper().getApplications();
        this.notifyDataSetChanged();
    }

}

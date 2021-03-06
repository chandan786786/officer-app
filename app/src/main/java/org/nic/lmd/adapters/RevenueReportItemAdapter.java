package org.nic.lmd.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.NatureOfBusiness;
import org.nic.lmd.entities.RevenueReportEntity;
import org.nic.lmd.entities.SubDivision;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.officerapp.MonthlyRevenueEntryActivity;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;

import java.util.List;


/**
 * Created by chandan on 24.02.2021
 */

public class RevenueReportItemAdapter extends RecyclerView.Adapter<RevenueReportItemAdapter.MyViewHolder> {

    Activity activity;
    private String subDiv;
    private boolean isPre;
    List<NatureOfBusiness> premisesTypeEntities;

    public interface SumListener {
        void success();
    }

    private static SumListener sumlisten;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView text_vf_previous, text_vf_tot_sum, text_af_previous, text_af_tot_sum, text_cf_previous;
        public EditText edit_vf_current, edit_af_current, edit_cf_current;
        public TextView text_cf_total_sum, text_title, text_row_tot1, text_row_tot2, text_row_tot3;

        public MyViewHolder(View view) {
            super(view);
            text_title = view.findViewById(R.id.text_title);
            text_vf_previous = view.findViewById(R.id.text_vf_previous);
            edit_vf_current = view.findViewById(R.id.edit_vf_current);
            text_vf_tot_sum = view.findViewById(R.id.text_vf_tot_sum);

            text_af_previous = view.findViewById(R.id.text_af_previous);
            edit_af_current = view.findViewById(R.id.edit_af_current);
            text_af_tot_sum = view.findViewById(R.id.text_af_tot_sum);

            text_cf_previous = view.findViewById(R.id.text_cf_previous);
            edit_cf_current = view.findViewById(R.id.edit_cf_current);
            text_cf_total_sum = view.findViewById(R.id.text_cf_total_sum);

            text_row_tot1 = view.findViewById(R.id.text_row_tot1);
            text_row_tot2 = view.findViewById(R.id.text_row_tot2);
            text_row_tot3 = view.findViewById(R.id.text_row_tot3);
        }
    }


    public RevenueReportItemAdapter(Activity activity, String subDiv, boolean isPre) {
        this.activity = activity;
        this.subDiv = subDiv;
        this.isPre = isPre;
        this.premisesTypeEntities = new DataBaseHelper(activity).getNatureofBusiness();
        setHasStableIds(true);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.revenue_report_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final NatureOfBusiness premisesTypeEntity = premisesTypeEntities.get(position);
        RevenueReportEntity revenueReportEntity_pre = null;
        if (MonthlyRevenueEntryActivity.revenueReportEntities!=null){
            revenueReportEntity_pre = MonthlyRevenueEntryActivity.revenueReportEntities.stream().filter((re) -> premisesTypeEntity.getId().equals(re.getType_of_bussiness().getId())).findAny().orElse(new RevenueReportEntity());
        }
        else {
            revenueReportEntity_pre = new RevenueReportEntity();
        }
        holder.text_title.setText("" + premisesTypeEntity.getValue());

        calculateTotal(holder);
        addTextChange(holder.edit_vf_current, premisesTypeEntity, holder);
        addTextChange(holder.edit_af_current, premisesTypeEntity, holder);
        addTextChange(holder.edit_cf_current, premisesTypeEntity, holder);
        addFocusChangeListner(holder.edit_af_current);
        addFocusChangeListner(holder.edit_vf_current);
        addFocusChangeListner(holder.edit_cf_current);
        if (!isPre) {
            holder.text_vf_previous.setText("" + (revenueReportEntity_pre.getVf_total_current() - revenueReportEntity_pre.getVf_current()));
            holder.text_af_previous.setText("" + (revenueReportEntity_pre.getAf_total_current() - revenueReportEntity_pre.getAf_current()));
            holder.text_cf_previous.setText("" + (revenueReportEntity_pre.getCf_total_current() - revenueReportEntity_pre.getCf_current()));

            holder.edit_vf_current.setText("" + revenueReportEntity_pre.getVf_current());
            holder.edit_af_current.setText("" + revenueReportEntity_pre.getAf_current());
            holder.edit_cf_current.setText("" + revenueReportEntity_pre.getCf_current());
        }else {
            holder.text_vf_previous.setText("" + revenueReportEntity_pre.getVf_total_current());
            holder.text_af_previous.setText("" + revenueReportEntity_pre.getAf_total_current());
            holder.text_cf_previous.setText("" + revenueReportEntity_pre.getCf_total_current());
            holder.edit_vf_current.setText("0");
            holder.edit_af_current.setText("0");
            holder.edit_cf_current.setText("0");

        }
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return premisesTypeEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        return position;
    }

    private void addFocusChangeListner(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (editText.getText().toString().trim().equals("0")||editText.getText().toString().trim().equals("0.0")) {
                    editText.setText("");
                }
            } else {
                if (editText.getText().toString().trim().equals("")) {
                    editText.setText("0");
                }
            }
        });
    }

    private void addTextChange(EditText editText, NatureOfBusiness natureOfBusiness, MyViewHolder holder) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!holder.edit_vf_current.getText().toString().trim().equals("") && !holder.edit_af_current.getText().toString().trim().equals("") && !holder.edit_cf_current.getText().toString().trim().equals("")) {
                    if (Double.parseDouble(holder.edit_vf_current.getText().toString().trim()) >= 0 && Double.parseDouble(holder.edit_af_current.getText().toString().trim()) >= 0 && Double.parseDouble(holder.edit_cf_current.getText().toString().trim()) >= 0) {
                        RevenueReportEntity revenueReportEntity_pre = MonthlyRevenueEntryActivity.revenueReportEntities_entry.stream().filter((re) -> natureOfBusiness.getId().equals(re.getType_of_bussiness().getId())).findAny().orElse(new RevenueReportEntity());
                        RevenueReportEntity revenueReportEntity = revenueReportEntity_pre;
                        revenueReportEntity.setMonth(MonthlyRevenueEntryActivity.monthSelected);
                        revenueReportEntity.setYear(MonthlyRevenueEntryActivity.yearSelected);
                        revenueReportEntity.setVf_current(Double.parseDouble(holder.edit_vf_current.getText().toString().trim()));
                        revenueReportEntity.setAf_current(Double.parseDouble(holder.edit_af_current.getText().toString().trim()));
                        revenueReportEntity.setCf_current(Double.parseDouble(holder.edit_cf_current.getText().toString().trim()));
                        revenueReportEntity.setVf_total_current(revenueReportEntity.getVf_current() + Double.parseDouble(holder.text_vf_previous.getText().toString().trim()));
                        revenueReportEntity.setAf_total_current(revenueReportEntity.getAf_current() + Double.parseDouble(holder.text_af_previous.getText().toString().trim()));
                        revenueReportEntity.setCf_total_current(revenueReportEntity.getCf_current() + Double.parseDouble(holder.text_cf_previous.getText().toString().trim()));
                        SubDivision sub = new SubDivision();
                        sub.setId(Integer.parseInt(subDiv));
                        revenueReportEntity.setSub_div(sub);
                        UserData userData = CommonPref.getUserDetails(activity);
                        revenueReportEntity.setUser_id(userData.getUserid());
                        revenueReportEntity.setType_of_bussiness(natureOfBusiness);
                        if (revenueReportEntity.getRev_rep_id() == 0) {
                            GlobalVariable.m_id++;
                            revenueReportEntity.setRev_rep_id(GlobalVariable.m_id);
                            MonthlyRevenueEntryActivity.revenueReportEntities_entry.add(revenueReportEntity);
                        } else {
                            int index = MonthlyRevenueEntryActivity.revenueReportEntities_entry.indexOf(revenueReportEntity);
                            MonthlyRevenueEntryActivity.revenueReportEntities_entry.set(index, revenueReportEntity);
                        }
                        holder.text_vf_tot_sum.setText("" + revenueReportEntity.getVf_total_current());
                        holder.text_af_tot_sum.setText("" + revenueReportEntity.getAf_total_current());
                        holder.text_cf_total_sum.setText("" + revenueReportEntity.getCf_total_current());
                        calculateTotal(holder);
                        sumlisten.success();
                    }
                } else {
                    Log.e("e-log", "blank data");
                }

            }
        });
        editText.setText("0");
    }

    private void calculateTotal(MyViewHolder holder) {
        holder.text_row_tot1.setText("" + (Double.parseDouble(holder.text_vf_previous.getText().toString().trim()) + Double.parseDouble(holder.text_af_previous.getText().toString().trim()) + Double.parseDouble(holder.text_cf_previous.getText().toString().trim())));
        holder.text_row_tot3.setText("" + (Double.parseDouble(holder.text_vf_tot_sum.getText().toString().trim()) + Double.parseDouble(holder.text_af_tot_sum.getText().toString().trim()) + Double.parseDouble(holder.text_cf_total_sum.getText().toString().trim())));
        holder.text_row_tot2.setText("" + (Double.parseDouble(holder.edit_vf_current.getText().toString().trim()) + Double.parseDouble(holder.edit_af_current.getText().toString().trim()) + Double.parseDouble(holder.edit_cf_current.getText().toString().trim())));
    }


    public static void listenForSum(SumListener listener) {
        sumlisten = listener;
    }
}
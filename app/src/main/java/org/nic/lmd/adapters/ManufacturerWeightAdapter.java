package org.nic.lmd.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.recyclerview.widget.RecyclerView;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.StatusForRenewalData;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.officerapp.VerificationLMOActivity;
import org.nic.lmd.retrofitPojo.WeightManufacturePojo;
import java.util.List;


/**
 * Created by chandan on 09.01.2021
 */

public class ManufacturerWeightAdapter extends RecyclerView.Adapter<ManufacturerWeightAdapter.MyViewHolder> {

    Activity activity;
    Button button_renew;
    List<WeightManufacturePojo> weightManufacturePojos;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_tag,text_name, text_cat, text_vcid, text_qty, text_cur_qtr, text_next_vc_qtr;
        public ToggleButton toggle_request_for_vc, toggle_delete;
        public RadioGroup redio_group_action;
        public LinearLayout ll_ac;

        public MyViewHolder(View view) {
            super(view);
            text_tag = view.findViewById(R.id.text_tag);
            text_name = view.findViewById(R.id.text_name);
            text_cat = view.findViewById(R.id.text_cat);
            text_vcid = view.findViewById(R.id.text_vcid);
            text_qty = view.findViewById(R.id.text_qty);
            text_cur_qtr = view.findViewById(R.id.text_cur_qtr);
            text_next_vc_qtr = view.findViewById(R.id.text_next_vc_qtr);
            toggle_request_for_vc = view.findViewById(R.id.toggle_request_for_vc);
            toggle_delete = view.findViewById(R.id.toggle_delete);
            redio_group_action = view.findViewById(R.id.redio_group_action);
            ll_ac = view.findViewById(R.id.ll_ac);

        }
    }


    public ManufacturerWeightAdapter(Activity activity, List<WeightManufacturePojo> weightManufacturePojo) {
        this.activity = activity;
        this.weightManufacturePojos = weightManufacturePojo;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.renewal_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //RenawalActivity.jsonObject.getJSONArray("weights").getJSONObject(position).accumulate("action", "R");
        holder.ll_ac.setVisibility(View.GONE);
        if (position==0) {
            holder.text_tag.setVisibility(View.VISIBLE);
            holder.text_tag.setText("Weights");
        }
        VerificationLMOActivity.statusForRenewalDataWeight.add(new StatusForRenewalData());
        final WeightManufacturePojo manufacturerPoso = weightManufacturePojos.get(position);
        DataBaseHelper db = new DataBaseHelper(activity);
        holder.text_name.setText("" + db.getWeightDenominationByID(String.valueOf(manufacturerPoso.denomination)).getDenominationDesc());
        holder.text_cat.setText("" + db.getCategoryById(String.valueOf(manufacturerPoso.category)).getValue());
        holder.text_vcid.setText("" + manufacturerPoso.manufacturerId);
        /*weight.nextverificationQtr = (weight.nextverificationQtr == null) ? "N/A" : weight.nextverificationQtr;
        holder.text_next_vc_qtr.setText("" + weight.nextverificationQtr);
        if (weight.status != null) {
            if (weight.status.equals("D")) {
                holder.toggle_request_for_vc.setClickable(true);
                holder.toggle_request_for_vc.setChecked(true);
                holder.toggle_request_for_vc.setClickable(false);
            }
        }
        holder.toggle_request_for_vc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((RadioButton) holder.redio_group_action.getChildAt(0)).setChecked(isChecked);
                if (isChecked) {
                    holder.toggle_delete.setClickable(true);
                    holder.toggle_delete.setChecked(false);
                    holder.toggle_delete.setClickable(false);
                } else {
                    holder.toggle_delete.setClickable(true);
                    //holder.redio_group_action.setClickable(false);
                }
                VerificationLMOActivity.statusForRenewalDataWeight.get(position).setRenewed(isChecked);
                //VerificationLMOActivity.vendorDataResponse.vendorPoso.weights.get(position).mCheck= isChecked;
            }
        });

        holder.toggle_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.redio_group_action.clearCheck();
                if (isChecked) {
                    holder.toggle_request_for_vc.setChecked(false);
                    holder.toggle_request_for_vc.setClickable(false);
                } else {
                    holder.toggle_request_for_vc.setClickable(true);
                }
                VerificationLMOActivity.statusForRenewalDataWeight.get(position).setDeleted(isChecked);
            }
        });

        holder.redio_group_action.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    if (checkedId == R.id.radio_renew) {
                        //RenawalActivity.jsonObject.getJSONArray("weights").getJSONObject(position).put("isDeleted", isChecked);
                        Toast.makeText(activity, "Renew Checked", Toast.LENGTH_SHORT).show();
                    } else if (checkedId == R.id.radio_repair) {

                    } else if (checkedId == R.id.radio_unrep) {

                    } else {
                        Toast.makeText(activity, "cleared", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return weightManufacturePojos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
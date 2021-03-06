package org.nic.lmd.officerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.nic.lmd.adapters.InstrumentAddedAdapter;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.utilities.Utiilties;


public class InstrumetAddedActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_denomination);
        toolbar =  findViewById(R.id.toolbar_den_list);
        toolbar.setTitle("Instruments Added");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> InstrumetAddedActivity.super.onBackPressed());

        TextView textView=findViewById(R.id.tv_added_cart);
        textView.setText(""+new DataBaseHelper(InstrumetAddedActivity.this).getInstrumentAddedCount());
        Utiilties.didTapButton(textView,InstrumetAddedActivity.this);
        listView =  findViewById(R.id.list_denomination);
        listView.setAdapter(new InstrumentAddedAdapter(InstrumetAddedActivity.this,textView));
    }
}
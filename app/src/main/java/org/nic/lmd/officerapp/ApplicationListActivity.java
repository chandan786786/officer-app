package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.nic.lmd.adapters.VendorAdapter;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.interfaces.VenderListListener;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;
import org.nic.lmd.services.VenderDataForBillingUpdateLoader;
import org.nic.lmd.services.VenderListLoader;
import org.nic.lmd.utilities.Utiilties;

public class ApplicationListActivity extends AppCompatActivity {

    ListView list_vender;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        toolbar = findViewById(R.id.toolbar_vender_list);
        toolbar.setTitle("Applications");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationListActivity.super.onBackPressed();
            }
        });
        //callService();
        list_vender = findViewById(R.id.list_vender);
        populateData();

    }

    private void populateData() {
        String which = getIntent().getStringExtra("which");
        list_vender.invalidate();
        if (which.equals("pending")) {
            toolbar.setTitle("Fee Calculation List");
            list_vender.setAdapter(new VendorAdapter(ApplicationListActivity.this, MainActivity.pending_vender, which));
        }
        else if (which.equals("verified")) {
            toolbar.setTitle("Verification List");
            list_vender.setAdapter(new VendorAdapter(ApplicationListActivity.this, MainActivity.verified_vendor, which));
        }
    }

    /*private void callService(){
        VenderListLoader.listenVendorList(new VenderListListener() {
            @Override
            public void responseFound(String res) {
                list_vender=findViewById(R.id.list_vender);
                list_vender.setAdapter(new VendorAdapter(ApplicationListActivity.this,res));
            }

            @Override
            public void responseNotFound(String msg) {
                Toast.makeText(ApplicationListActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        if (!Utiilties.isOnline(ApplicationListActivity.this)){
            Toast.makeText(this, "Internet Not Avalable !", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please go online !", Toast.LENGTH_SHORT).show();
        }else {
            UserData userData = CommonPref.getUserDetails(ApplicationListActivity.this);
            Log.d("SubdivID", userData.getEstbSubdivId());
            new VenderListLoader(ApplicationListActivity.this).execute("SUB", userData.getEstbSubdivId());
        }
    }*/


}
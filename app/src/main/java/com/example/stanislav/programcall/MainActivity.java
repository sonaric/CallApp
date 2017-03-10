package com.example.stanislav.programcall;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

    TabHost tabHost;
    public final String CALL_TAB = "tag1";
    public final String HISTORY_TAB = "tag2";
    public final String CONTACT_TAB = "tag3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         tabHost=getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec=tabHost.newTabSpec(CALL_TAB);
        tabSpec.setIndicator(getString(R.string.tab_call));
        tabSpec.setContent(new Intent(this,Calls.class));
        tabHost.addTab(tabSpec);

        tabSpec=tabHost.newTabSpec(HISTORY_TAB);
        tabSpec.setIndicator(getString(R.string.tab_history));
        tabSpec.setContent(new Intent(this,Histories.class));
        tabHost.addTab(tabSpec);

        tabSpec=tabHost.newTabSpec(CONTACT_TAB);
        tabSpec.setIndicator(getString(R.string.tab_contact));
        tabSpec.setContent(new Intent(this,Contacts.class));
        tabHost.addTab(tabSpec);
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor( Color.parseColor("#ffffff"));
            tv.setTextSize( 14 );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optnmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_exit:
                AlertDialog.Builder builder = new  AlertDialog.Builder(this);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setMessage("Exit?").setTitle("Info").setIcon(android.R.drawable.ic_dialog_alert);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item); // aqkkh jarxd
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sPref=getSharedPreferences( "contactTransport",MODE_PRIVATE );
        SharedPreferences.Editor ed=sPref.edit();
        ed.putBoolean( "read_contact",false );
        ed.commit();
    }

    public void switchTab(String tag){
        tabHost.setCurrentTabByTag( tag );
    }
}

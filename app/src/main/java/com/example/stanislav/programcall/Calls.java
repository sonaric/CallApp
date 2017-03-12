package com.example.stanislav.programcall;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Calls extends AppCompatActivity implements View.OnClickListener {

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bstar, bsharp, badd;
    ImageButton bcall, bbackspace, bmessage;
    TextView numberInput;

    final int MAX_STREAMS=1;

    SoundPool sp;
    int soundIdClick;

    int streamIDClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);

        b0 = (Button) findViewById(R.id.b0);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        bstar = (Button) findViewById(R.id.bstar);
        bsharp = (Button) findViewById(R.id.bsharp);

        badd = (Button) findViewById(R.id.badd);

        bcall = (ImageButton) findViewById(R.id.bcall);
        bmessage = (ImageButton) findViewById(R.id.bmessage);
        bbackspace = (ImageButton) findViewById(R.id.bbackspace);

        numberInput = (TextView) findViewById(R.id.numberInput);

        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        bstar.setOnClickListener(this);
        bsharp.setOnClickListener(this);

        badd.setOnClickListener(this);

        bcall.setOnClickListener(this);
        bmessage.setOnClickListener(this);
        bbackspace.setOnClickListener(this);

        registerForContextMenu(numberInput);

        sp=new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC,0);

        soundIdClick=sp.load(this,R.raw.buttonclick,1);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch(v.getId()) {
            case R.id.numberInput:
                menu.add(0, 1, 0, getString( R.string.menu_clear_number ));
                menu.add(0, 2, 0, getString( R.string.menu_add_to_contact ));
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
                numberInput.setText("");
                break;
            case 2:
                Intent intent3 = new Intent(this,AddContactActivity.class);
                intent3.putExtra("phone",numberInput.getText().toString());
                startActivity(intent3);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.release();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b0:
                numberInput.setText(numberInput.getText().toString() + "0");
                break;
            case R.id.b1:
                numberInput.setText(numberInput.getText().toString() + "1");
                break;
            case R.id.b2:
                numberInput.setText(numberInput.getText().toString() + "2");
                break;
            case R.id.b3:
                numberInput.setText(numberInput.getText().toString() + "3");
                break;
            case R.id.b4:
                numberInput.setText(numberInput.getText().toString() + "4");
                break;
            case R.id.b5:
                numberInput.setText(numberInput.getText().toString() + "5");
                break;
            case R.id.b6:
                numberInput.setText(numberInput.getText().toString() + "6");
                break;
            case R.id.b7:
                numberInput.setText(numberInput.getText().toString() + "7");
                break;
            case R.id.b8:
                numberInput.setText(numberInput.getText().toString() + "8");
                break;
            case R.id.b9:
                numberInput.setText(numberInput.getText().toString() + "9");
                break;
            case R.id.bstar:
                numberInput.setText(numberInput.getText().toString() + "*");
                break;
            case R.id.bsharp:
                numberInput.setText(numberInput.getText().toString() + "#");
                break;
            case R.id.bcall:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numberInput.getText().toString()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);

                break;
            case R.id.bmessage:
                /*Intent intent2 = new Intent(Intent.ACTION_SEND);
                startActivity(intent2);*/
                DialogFragment newFragment = new FireMissilesDialogFragment();
                newFragment.show( getFragmentManager(),"Info");
                break;
            case R.id.bbackspace:
                if(!numberInput.getText().toString().equals(""))
                   numberInput.setText(numberInput.getText().toString().substring(0,numberInput.getText().toString().length()-1));
                else
                    Toast.makeText(this,getString( R.string.toast_text_empty ),Toast.LENGTH_SHORT).show();
                break;

            case R.id.badd:
                streamIDClick=sp.play(soundIdClick,1,1,1,0,1);
                String []data={getString(R.string.menu_add_contact),getString(R.string.menu_update_contact)};
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle( getString(R.string.btn_add_contact) )
                        .setItems( data, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent intent3 = new Intent(getBaseContext(),AddContactActivity.class);
                                        intent3.putExtra("phone",numberInput.getText().toString());
                                        startActivity(intent3);
                                        break;
                                    case 1:

                                }
                            }
                        } );
                AlertDialog dialog = builder.create();
                dialog.show();
                    /*Intent intent3 = new Intent(this,AddContactActivity.class);
                    intent3.putExtra("AddPhoneNumber",numberInput.getText().toString());
                    startActivity(intent3);*/
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sPref = getSharedPreferences( "contactTransport", MODE_PRIVATE);
        boolean contact_add=sPref.getBoolean( "read_contact", false );
        if(contact_add) {
            String savedText = sPref.getString( "contact", "" );
            SharedPreferences.Editor ed=sPref.edit();
            ed.putBoolean( "read_contact",false );
            ed.commit();
            numberInput.setText( savedText );
        }

    }
}

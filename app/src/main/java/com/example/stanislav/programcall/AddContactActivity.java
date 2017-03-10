package com.example.stanislav.programcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener{
    EditText inputName, inputPhone;
    Button btsave, btcancel;
    RadioButton sexRadio1, sexRadio2;
    RadioGroup rbgroup;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        inputName = (EditText) findViewById(R.id.inputName);
        inputPhone = (EditText) findViewById(R.id.inputPhone);

        btsave = (Button) findViewById(R.id.btsave);
        btcancel = (Button) findViewById(R.id.btcancel);

        rbgroup=(RadioGroup) findViewById( R.id.rbgroup );

        btsave.setOnClickListener(this);
        btcancel.setOnClickListener(this);

        Intent intent = getIntent();
        inputPhone.setText( intent.getStringExtra( "phone" ) );
        if(intent.getLongExtra( "id", -11 )>=0)
        {
            id=intent.getLongExtra( "id", 0 );
            inputName.setText( intent.getStringExtra( "name" ) );
            inputPhone.setText( intent.getStringExtra( "phone" ) );
            String temp_str=intent.getStringExtra( "sex" );
            if(temp_str.equals( "man" )){
                rbgroup.check( R.id.sex1 );
            }else{
                rbgroup.check( R.id.sex2 );
            }
        };

        //inputPhone.setText(intent.getStringExtra("AddPhoneNumber"));

        inputName = (EditText) findViewById(R.id.inputName);
        inputPhone = (EditText) findViewById(R.id.inputPhone);

        sexRadio1=(RadioButton) findViewById( R.id.sex1 );
        sexRadio2=(RadioButton) findViewById( R.id.sex2 );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btsave:
                String sexData;
                if(sexRadio1.isChecked()){
                    sexData="man";
                }else{
                    sexData="woman";
                }
                Intent intent=new Intent(  );
                intent.putExtra( "id", id);
                intent.putExtra( "name",inputName.getText().toString() );
                intent.putExtra( "number",inputPhone.getText().toString() );
                intent.putExtra( "sex",sexData );
                setResult( RESULT_OK,intent );
                finish();
                break;
            case R.id.btcancel:
                Intent intent2=new Intent(  );
                setResult( RESULT_CANCELED,intent2 );
                finish();
                break;
        }
    }
}

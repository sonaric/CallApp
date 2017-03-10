package com.example.stanislav.programcall;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;


import java.util.concurrent.TimeUnit;

public class Contacts extends FragmentActivity implements  View.OnClickListener, LoaderCallbacks<Cursor>{
    FloatingActionButton addbtn;

    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;
    //Cursor cursor;
    TextView searchField;
    Animation anim=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        addbtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addbtn.setOnClickListener(this);

        searchField=(TextView) findViewById( R.id.searchNumber );

        db=new DB( this );
        db.open();

        /*String[] from=new String[] {DB.COLUMN_NAME};
        int[] to=new int[]{R.id.tvText};

        scAdapter=new SimpleCursorAdapter( this, R.layout.item, null, from, to, 0 );
        lvData=(ListView) findViewById( R.id.lvData );
        lvData.setAdapter( scAdapter );
        getSupportLoaderManager().initLoader( 0,null,this);*/
        // получаем курсор
        /*cursor = db.getAllData();
        startManagingCursor(cursor);*/

        // формируем столбцы сопоставления
        String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_TXT };
        int[] to = new int[] { R.id.ivImg, R.id.tvText };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to,0);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        registerForContextMenu( lvData );

        getSupportLoaderManager().initLoader( 0,null,this );




        searchField.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*cursor=db.getSearchData(s);
                cursor.requery();*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );


        anim= AnimationUtils.loadAnimation(this,R.anim.button_anim);
        //txtLogo.startAnimation(anim);
        //addbtn.startAnimation(anim);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.floatingActionButton:
                Intent intent = new Intent(this,AddContactActivity.class);
                startActivityForResult(intent,1);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult( requestCode, resultCode, data );
        if(data==null){return;};
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case 1:
                    String name = data.getStringExtra( "name" );
                    String number = data.getStringExtra( "number" );
                    String sex = data.getStringExtra( "sex" );
                    int imageIcon;
                    if (sex.equals( "man" )) {
                        imageIcon = R.drawable.ic_action_contact_man;
                    } else {
                        imageIcon = R.drawable.ic_action_contact_woman;
                    }
                    db.addRec( name, imageIcon, number, sex );
                    break;
                case 2:
                    long id=data.getLongExtra( "id",0 );
                    String name2 = data.getStringExtra( "name" );
                    String number2 = data.getStringExtra( "number" );
                    String sex2 = data.getStringExtra( "sex" );
                    int imageIcon2;
                    if (sex2.equals( "man" )) {
                        imageIcon2 = R.drawable.ic_action_contact_man;
                    } else {
                        imageIcon2 = R.drawable.ic_action_contact_woman;
                    }
                    db.updateRec(id, name2, imageIcon2, number2, sex2 );
                    break;

            }

            getSupportLoaderManager().getLoader( 0 ).forceLoad();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader( this, db );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor( data );
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            /*try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            return cursor;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        addbtn.startAnimation( anim );
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, getString(R.string.menu_delete_contact));
        menu.add(0, 2, 0, getString(R.string.menu_update_contact));
        menu.add(0, 3, 0, getString(R.string.menu_contact_to_call));
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            // получаем новый курсор с данными
            getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        if (item.getItemId() == 2) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            Cursor localCursor=db.searchById(acmi.id);
            if(localCursor.moveToFirst())
            {
                int txtCol=localCursor.getColumnIndex( "txt" );
                int phoneCol=localCursor.getColumnIndex( "phone" );
                int sexCol=localCursor.getColumnIndex( "sex" );
                //Toast.makeText( getBaseContext(),localCursor.getString( txtCol )+localCursor.getString( phoneCol )+localCursor.getString( sexCol ),Toast.LENGTH_SHORT ).show();
                Intent intent3=new Intent( this,AddContactActivity.class );
                intent3.putExtra( "id",acmi.id );
                intent3.putExtra( "name",localCursor.getString( txtCol ) );
                intent3.putExtra( "phone",localCursor.getString( phoneCol ) );
                intent3.putExtra( "sex",localCursor.getString( sexCol ) );
                localCursor.close();
                startActivityForResult( intent3,2 );

            }
            // получаем новый курсор с данными
            //getSupportLoaderManager().getLoader(0).forceLoad();
            return true;
        }
        if (item.getItemId() == 3) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            Cursor localCursor=db.searchById(acmi.id);
            if(localCursor.moveToFirst()) {
                int phoneCol = localCursor.getColumnIndex( "phone" );
                String phone_trasert=localCursor.getString( phoneCol );
                localCursor.close();
                SharedPreferences sPref=getSharedPreferences( "contactTransport",MODE_PRIVATE );
                SharedPreferences.Editor ed=sPref.edit();
                ed.putString( "contact",phone_trasert );
                ed.putBoolean( "read_contact",true );
                ed.commit();

                MainActivity parent=(MainActivity)getParent();
                parent.switchTab( parent.CALL_TAB );
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

   /* @Override
    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader( this, db );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Object data) {
        //scAdapter.swapCursor( cursor )
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }

    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super( context );
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
           /*try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }
    }*/

    }

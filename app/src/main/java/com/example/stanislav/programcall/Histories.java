package com.example.stanislav.programcall;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Histories extends Activity {

    File directory;
    final int TYPE_PHOTO=1;
    final int REQUEST_CODE_PHOTO=1;
    ImageView ivPhoto;
    Handler h;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histories);

        createDirectory();
        ivPhoto=(ImageView)findViewById(R.id.ivPhoto);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);


        SharedPreferences sPref=getSharedPreferences( "contactTransport",MODE_PRIVATE );
        String savedText = sPref.getString( "photobackground", "" );

        h=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        ivPhoto.setImageBitmap((Bitmap) msg.obj);
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                }

            }
        };

        getBackgroundBitmap(savedText,this);
}

    private void getBackgroundBitmap(final String uri, final Histories parent){
        progressBar.setVisibility(View.VISIBLE);
        Thread t=new Thread(new Runnable() {
            Message msg;
            @Override
            public void run() {
                Bitmap bitmap = null;
                if(!uri.equals("")) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(parent.getContentResolver(), Uri.parse(uri));
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        msg=h.obtainMessage(1,0,0,bitmap);
                        h.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }



    public void onClickPhoto(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,generateFileUri(TYPE_PHOTO));
        startActivityForResult(intent,REQUEST_CODE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_PHOTO){
            if (resultCode==RESULT_OK){
                SharedPreferences sPref=getSharedPreferences( "contactTransport",MODE_PRIVATE );
                String savedText = sPref.getString( "photobackground", "" );
                getBackgroundBitmap(savedText,this);
            }
        }
    }

    private  Uri generateFileUri(int type){
        File file=null;
        switch (type){
            case TYPE_PHOTO:
                file=new File(directory.getPath()+"/"+"photo_"+ String.valueOf(System.currentTimeMillis())+".jpg");

                break;
        }
        SharedPreferences sPref=getSharedPreferences( "contactTransport",MODE_PRIVATE );
        SharedPreferences.Editor ed=sPref.edit();
        ed.putString( "photobackground",Uri.fromFile(file).toString());
        ed.commit();
        return Uri.fromFile(file);
    }

    private void createDirectory(){
        directory=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"CallProgramFolder");
        if(!directory.exists()){
            directory.mkdirs();
        }
    }
}

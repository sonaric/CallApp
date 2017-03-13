package com.example.stanislav.programcall;

import android.content.SharedPreferences;
import android.graphics.Matrix;
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

public class Histories extends AppCompatActivity {

    File directory;
    final int TYPE_PHOTO=1;

    final int REQUEST_CODE_PHOTO=1;

    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histories);
        createDirectory();
        ivPhoto=(ImageView)findViewById(R.id.ivPhoto);
        SharedPreferences sPref=getSharedPreferences( "contactTransport",MODE_PRIVATE );
        String savedText = sPref.getString( "photobackground", "" );
        if(!savedText.equals("")){
            Bitmap bitmap=null;
            try {
                bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.parse(savedText));
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivPhoto.setImageBitmap(bitmap);
        }
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
                Bitmap bitmap=null;
                try {
                    bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),Uri.parse(savedText));
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ivPhoto.setImageBitmap(bitmap);
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

package com.avash.tourstory.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avash.tourstory.R;
import com.avash.tourstory.adapter.MomentRowAdapter;
import com.avash.tourstory.database.DatabaseHelper;
import com.avash.tourstory.database.MomentManager;
import com.avash.tourstory.model.MomentModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MomentsActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_INTENT = 1;
    private static final int GALLERY_IMAGE_REQUEST_INTENT = 2;
    ImageView currentImage;
    Bitmap imageBitmap;
    String currentImagePath;

    int eid;

    EditText titleET,descriptionET;

    MomentManager momentManager;
    private ListView momentListView;
    private MomentRowAdapter momentRowAdapter;
    private ArrayList<MomentModel> momentModels=null;
    private DatabaseHelper databaseHelper;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        eid = getIntent().getIntExtra("eid",0);

        momentManager=new MomentManager(this);
        databaseHelper=new DatabaseHelper(this);
        momentListView= (ListView) findViewById(R.id.momentDetailsListView);
        momentModels=new ArrayList<>();

        momentModels=momentManager.getEventAllMoment(eid);
        if (momentModels != null){
            momentRowAdapter=new MomentRowAdapter(this,momentModels);
            momentListView.setAdapter(momentRowAdapter);
        }

        currentImage= (ImageView) findViewById(R.id.currentImageIV);

        titleET= (EditText) findViewById(R.id.momentTitleEditText);
        descriptionET= (EditText) findViewById(R.id.momentDescriptionEditText);


    }
    public void addMoment(View view) {
        String date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String title=titleET.getText().toString().trim();
        String description=descriptionET.getText().toString().trim();

        MomentModel momentModel=new MomentModel(0,eid,title,description,currentImagePath,date);
        long momentAddResult=momentManager.addMoment(momentModel);
        if (momentAddResult > 0){
            Toast.makeText(this,"Moment Added",Toast.LENGTH_SHORT).show();
        }

    }















    public void startCamera(View view) {
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try {
                photoFile=getImageFile();
            }catch (IOException e){
                Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null){
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent,CAMERA_REQUEST_INTENT);
            }

        }

    }

    private File getImageFile() throws IOException {
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName="JPEG_"+timeStamp+"_";
        File storageDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(imageFileName,".jpg",storageDirectory);
        currentImagePath=image.getAbsolutePath();
        return  image;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_INTENT && resultCode== RESULT_OK){

            int targetW=currentImage.getWidth();
            int targetH=currentImage.getHeight();

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(currentImagePath,options);
            int photoW=options.outWidth;
            int photoH=options.outHeight;

            int scaleFactor=Math.min(photoW/targetW,photoH/targetH);
            options.inJustDecodeBounds=false;
            options.inSampleSize=scaleFactor;

            imageBitmap=BitmapFactory.decodeFile(currentImagePath,options);
            currentImage.setImageBitmap(imageBitmap);
            Toast.makeText(this,""+currentImagePath,Toast.LENGTH_SHORT).show();

        }
        if (requestCode == GALLERY_IMAGE_REQUEST_INTENT && resultCode == RESULT_OK){
            Uri selectedImage=data.getData();
            String[] filePathColumn={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(selectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();
            int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
            currentImagePath=cursor.getString(columnIndex);
            cursor.close();

            int targetW=currentImage.getWidth();
            int targetH=currentImage.getHeight();

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(currentImagePath,options);

            int photoW=options.outWidth;
            int photoH=options.outHeight;
            int scaleFactor=Math.min(photoW/targetW,photoH/targetH);

            options.inJustDecodeBounds=false;
            options.inSampleSize=scaleFactor;

            imageBitmap=BitmapFactory.decodeFile(currentImagePath,options);
            currentImage.setImageBitmap(imageBitmap);
            Toast.makeText(this,""+currentImagePath,Toast.LENGTH_SHORT).show();
        }

    }

    public void pickImageFromGallery(View view) {
        Intent getPictureIntent=new Intent();
        getPictureIntent.setType("image/*");
        getPictureIntent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(getPictureIntent,"select picture"),GALLERY_IMAGE_REQUEST_INTENT);
    }


}

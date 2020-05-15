package com.course.deteksi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    VisualRecognition mVisualRecognition;
    CameraHelper mCameraHelper;

    private ImageView preview;
    private Button btn_takePicture;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVisualRecognition = new VisualRecognition("{2018-03-19}");
        mVisualRecognition.setApiKey(getString(R.string.api_key));

        mCameraHelper = new CameraHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
//            final Bitmap photo = mCameraHelper.getBitmap(resultCode);
//            final File photoFile = mCameraHelper.getFile(resultCode);
//            ImageView preview = findViewById(R.id.preview);
//            preview.setImageBitmap(photo);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //load a bitmap from camera capture
            final Bitmap photo = (Bitmap) extras.get(String.valueOf(resultCode));
            //scsalling a bitmap width x height
//            Bitmap photoScaled = Bitmap.createScaledBitmap(photo, 150, 100, true);

//            final File photoFile = (File) extras.get(String.valueOf(resultCode));
            preview = findViewById(R.id.preview);
            preview.setImageBitmap(photo);

//            AsyncTask.execute(new Runnable() {
//                @Override
//                public void run() {
//                    InputStream imagesStream = null; //abstract class
//                    try {
//                        imagesStream = new FileInputStream(photoFile); //berisi input bytes dr file spt image
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    //classify the picture
//                    ClassifyOptions classifyOptions = new ClassifyOptions.Builder().imagesFile(imagesStream)
//                            .imagesFilename(photoFile.getName())
//                            .threshold((float)0.6) //threshold dari data test
//                            .owners(Arrays.asList("me"))
//                            .build();
//
//                    //classify() to handle multiple picture at once
//                    ClassifiedImages result = mVisualRecognition.classify(classifyOptions).execute();
//                    Gson gson = new Gson(); //java library, used to convert Java objects into JSON representation
//                    String json = gson.toJson(result);
//                    String name = null;
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(json);
//                        JSONArray jsonArray = jsonObject.getJSONArray("images");
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                        JSONArray jsonArray1 = jsonObject1.getJSONArray("classifiers");
//                        JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
//                        JSONArray jsonArray2 = jsonObject2.getJSONArray("classes");
//                        JSONObject jsonObject3 = jsonArray2.getJSONObject(0);
//                        name = jsonObject3.getString("class"); //nama penyakit atau gejala yang menjangkit
//                    } catch (JSONException e){
//                        e.printStackTrace();
//                    }
//
//                    //untuk menampilkan detail gejala yang menjangkit tanaman
//                    final String finalName = name;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            TextView detectedObjects = findViewById(R.id.detected_objects);
//                            detectedObjects.setText(finalName);
//                        }
//                    });
//                }
//            });
        }
    }

    public void takePicture(View view) {
//        mCameraHelper.dispatchTakePictureIntent();
        dispatchTakePictureIntent();
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
           startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}

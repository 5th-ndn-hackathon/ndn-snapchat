package com.snapchat.fan.snapchat.Chat;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by fan on 12/16/2017.
 */

public class FileSelectActivity extends AppCompatActivity {
    // Initialize the Activity
    final List<String> filesList = new ArrayList<String>();
    private AppCompatImageButton mImageView;
    Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Set up an Intent to send back to apps that request a file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selective_file);

    }

    public void file_select(View view) {
        final ListView lv = (ListView) findViewById(R.id.listview);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Get the files/ subdirectory of internal storage
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, 0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(lv.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("You selected a file").setMessage("File:" + filesList.get(pos)).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filesList);
        lv.setAdapter(adapter);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

}

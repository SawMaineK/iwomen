package com.smk.iwomen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import com.path.android.jobqueue.JobManager;
import com.smk.application.DownloadManager;
import com.smk.application.MyApplication;
import com.smk.clientapi.NetworkEngine;
import com.smk.model.Download;
import com.smk.model.User;

import de.greenrobot.event.EventBus;

import android.support.v7.app.ActionBarActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Menu;	
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActionBarActivity {

	private static final int PIC_CROP = 100;
	private static final int CAMERA_CAPTURE = 200;
	private static final int SELECT_PICTURE = 300;
	private Button btn_register;
	protected String token;
	private Uri picUri;
	private Uri selectedImageUri;
	private String selectedImagePath;
	private MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
	private Button btn_photo_upload;
	protected String UserPhotoName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// This is simple for downloading | play to .mp3
		EventBus.getDefault().register(this);
		JobManager jobManager = MyApplication.getInstance().getJobManager();
		DownloadManager downloadManager = new DownloadManager("http://api.shopyface.com/posts_audio/intro.zip");
		jobManager.addJob(downloadManager);
		
		// This is simple how to upload user photo, it will process user register after uploaded.
		btn_photo_upload = (Button) findViewById(R.id.btn_upload);
		btn_photo_upload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
		        intent.setType("image/*");
		        intent.setAction(Intent.ACTION_GET_CONTENT);
		        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			}
		});
		
		
		// This is simple how to register user with my APIs.
		// This is only user register without user photo;
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// This is simple how to use my APIs.
				postUser();
			}
		});
		
	}
	
	public void postUser(){
		NetworkEngine.getInstance().postUser(MyApplication._token,"saw", "sawmk@gmail.com", "sawsaw", "0923434232", "Saw", "K", "", UserPhotoName, "User", new Callback<User>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if(arg0.getResponse() != null){
					switch (arg0.getResponse().getStatus()) {
					case 400:
						String error = (String) arg0.getBodyAs(String.class);
						Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
						break;

					default:
						break;
					}
					
				}
				
			}

			@Override
			public void success(User arg0, Response arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, arg0.toString(), Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void uploadUserPhoto(){
		multipartTypedOutput.addPart("access_token", new TypedString(MyApplication._token));
		NetworkEngine.getInstance().uploadUserPhoto(multipartTypedOutput, new Callback<String>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				if(arg0.getResponse() != null){
					switch (arg0.getResponse().getStatus()) {
					case 400:
						String error = (String) arg0.getBodyAs(String.class);
						Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
						break;

					default:
						break;
					}
					
				}
				
			}

			@Override
			public void success(String arg0, Response arg1) {
				// TODO Auto-generated method stub
				UserPhotoName = arg0;
				postUser();
			}
		});
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
        	// This is image result of choosing from gallery, and it will process image croping.
            if (requestCode == SELECT_PICTURE) {
            	picUri = data.getData();
            	Bitmap bmp = null;
				try {
					bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(picUri));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		performCrop(bmp); 
            }
            // This is image result of taking from camera, and it will process image croping.
            if (requestCode == CAMERA_CAPTURE) {
            	picUri = data.getData();
        		Bundle extras = data.getExtras();
				Bitmap bm = extras.getParcelable("data");
        		if(picUri == null) {
        			picUri = getImageUri(this, bm);
				}
        		performCrop(bm); 
            }
            
            // This is receive image uri from image croping.
            if(requestCode == PIC_CROP){
            	selectedImageUri = data.getData();
            	if(selectedImageUri == null){
            		Bundle extras = data.getExtras();
    				Bitmap bm = extras.getParcelable("data");
            		selectedImageUri = getImageUri(this, bm);
            	}
                selectedImagePath = getPath(selectedImageUri);
				multipartTypedOutput.addPart("image",new TypedFile("image/png",new File(selectedImagePath)));
				uploadUserPhoto();
            }
        }
    }
    
	private void performCrop(Bitmap bitmap){
		try {
			//call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			//indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			//set crop properties
			cropIntent.putExtra("crop", "true");
			//indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			//indicate output X and Y
			cropIntent.putExtra("outputX", 250);
			cropIntent.putExtra("outputY", 250);
			//retrieve data on return
			cropIntent.putExtra("return-data", true);
			//start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		}
		catch(ActivityNotFoundException anfe){
		    //display an error message
		    String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		}
	}
	
	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
		String path = Images.Media.insertImage(inContext.getContentResolver(), decoded, "Title", null);
		return Uri.parse(path);
	}
    
	//This Method is to get the Image Path from Gallery
	public String getPath(Uri uri) {
		
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
        
    }
	
	// This is processing when downloading file.
	public void onEventMainThread(final Download downloading) {
		// the posted event can be processed in the main thread
		Log.i("","Hello downloading precent is : "+ downloading.getDownloadPercent());
	}
	
	// This is processing when download was finished.
	public void onEventMainThread(Boolean finishedDownload) {
		// the posted event can be processed in the main thread
		if(finishedDownload){
			String fileName = "01 Intro.mp3";
			final String filePath = Environment.getExternalStorageDirectory() + "/SKStorage/audio_files/" + fileName;
			// TODO Auto-generated method stub
			File file = new File(filePath); 
			Uri audioUri = Uri.fromFile(file);
			
			MediaPlayer mMedia = MediaPlayer.create(MainActivity.this,audioUri);
			mMedia.start();
			
		}
	}
	
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

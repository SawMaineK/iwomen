package com.smk.iwomen;

import java.io.File;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.path.android.jobqueue.JobManager;
import com.smk.application.DownloadManager;
import com.smk.application.MyApplication;
import com.smk.clientapi.NetworkEngine;
import com.smk.model.Download;
import com.smk.model.User;

import de.greenrobot.event.EventBus;

import android.support.v7.app.ActionBarActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;	
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private Button btn_register;
	protected String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// This is simple for downloading | play to .mp3
		EventBus.getDefault().register(this);
		JobManager jobManager = MyApplication.getInstance().getJobManager();
		DownloadManager downloadManager = new DownloadManager("http://api.shopyface.com/posts_audio/intro.zip");
		jobManager.addJob(downloadManager);
		
		
		// This is simple how to register user with my APIs.
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// This is simple how to use my APIs.
				NetworkEngine.getInstance().postUser("saw", "sawmk@gmail.com", "sawsaw", "0923434232", "Saw", "K", "", "", "User", new Callback<User>() {

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
		});
		
	}
	
	public void onEventMainThread(final Download downloading) {
		// the posted event can be processed in the main thread
		Log.i("","Hello downloading precent is : "+ downloading.getDownloadPercent());
	}
	
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

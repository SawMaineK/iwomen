package com.smk.iwomen;

import java.io.File;

import com.path.android.jobqueue.JobManager;
import com.smk.application.DownloadManager;
import com.smk.application.MyApplication;
import com.smk.model.Download;

import de.greenrobot.event.EventBus;

import android.support.v7.app.ActionBarActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;	
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EventBus.getDefault().register(this);
		JobManager jobManager = MyApplication.getInstance().getJobManager();
		DownloadManager downloadManager = new DownloadManager("http://api.shopyface.com/posts_audio/intro.zip");
		jobManager.addJob(downloadManager);
		
		String fileName = "01 Intro.mp3";
		String filePath = Environment.getExternalStorageDirectory() + "/SKStorage/audio_files/" + fileName;
		Log.i("","Hello "+ filePath);
		File file = new File(filePath); 
		Uri audioUri = Uri.fromFile(file);
		
		MediaPlayer mMedia = MediaPlayer.create(this,audioUri);
		//mMedia.start();
		
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

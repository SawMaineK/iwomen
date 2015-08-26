package com.smk.iwomen;

import com.smk.application.MyApplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActionBarActivity extends ActionBarActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// Pre check if already get access token from APIs.
		if(MyApplication._token == null){
			MyApplication.getInstance().getAccessToken();
		}
	}
}

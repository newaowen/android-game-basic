package com.warsong.game.ui;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.warsong.game.R;

public class MainActivity extends Activity {

	public static TabHost	mTabHost;
	LocalActivityManager	mlam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (TabHost) findViewById(R.id.tab_host);
		mlam = new LocalActivityManager(this, false);
		mlam.dispatchCreate(savedInstanceState);
		mTabHost.setup(mlam);

		createTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void createTabs() {
		TabSpec ts1 = mTabHost.newTabSpec("2d");
		
		View indicator2d = LayoutInflater.from(this).inflate(R.layout.tab_2d_indicator,
				 null, false);
		ts1.setIndicator(indicator2d);
		ts1.setContent(new Intent(this, Basic2DTabActivity.class));
		mTabHost.addTab(ts1);

		TabSpec ts2 = mTabHost.newTabSpec("3d");
		
		View indicator3d = LayoutInflater.from(this).inflate(R.layout.tab_3d_indicator,
				 null, false);
		ts2.setIndicator(indicator3d);
		ts2.setContent(new Intent(this, Basic3DTabActivity.class));
		mTabHost.addTab(ts2);

		mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mlam.dispatchResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mlam.dispatchPause(isFinishing());
	}

}

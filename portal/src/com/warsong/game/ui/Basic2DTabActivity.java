package com.warsong.game.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.warsong.game.DemoItem;
import com.warsong.game.DemoListAdapter;
import com.warsong.game.R;
import com.warsong.game.helper.PackageHelper;

public class Basic2DTabActivity extends Activity {

	private List<DemoItem>	demos;

	private final int		CLASSNAME	= 0;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_2d_main);

		initDemos();
		initView();
	}

	public void initDemos() {
		demos = new ArrayList<DemoItem>();
		demos.add(new DemoItem("基本绘制", PackageHelper.PACKAGE_NAME + ".draw.MainActivity"));
		demos.add(new DemoItem("俄罗斯方块", PackageHelper.PACKAGE_NAME + ".tetris.MainActivity"));
		demos.add(new DemoItem("SpaceBlaster", PackageHelper.PACKAGE_NAME + ".sb.SpaceBlaster"));
		demos.add(new DemoItem("Asteroids", PackageHelper.PACKAGE_NAME + ".asteroids.AsteroidsActivity"));
		//demos.add(new DemoItem("声音", "sound", "SoundActivity"));
	}

	public void initView() {
		ListAdapter la = new DemoListAdapter(this, demos);
		ListView lv = (ListView) findViewById(R.id.listView);

		lv.setAdapter(la);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					String className = (String) view.getTag();
					Class cls = Class.forName(className);
					Intent i = new Intent();
					i.setClass(Basic2DTabActivity.this, cls);
					Basic2DTabActivity.this.startActivity(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

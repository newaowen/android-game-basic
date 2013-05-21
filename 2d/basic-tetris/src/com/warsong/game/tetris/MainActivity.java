package com.warsong.game.tetris;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.view.Menu;
import com.warsong.game.basic.GameApp;

public class MainActivity extends Activity {

    protected TetrisGameApp tetrisGame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        tetrisGame = new TetrisGameApp(this);
        tetrisGame.createSurface();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

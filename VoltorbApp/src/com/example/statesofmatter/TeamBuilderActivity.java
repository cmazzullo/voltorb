package com.example.statesofmatter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TeamBuilderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_builder);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_team_builder, menu);
		return true;
	}

}

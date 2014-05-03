package com.example.statesofmatter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void createTeamAction(View view){
        //Toast.makeText(this, "Create Team button clicked", Toast.LENGTH_LONG).show();
	    Intent intent = new Intent(this, CreateTeamActivity.class);
	    //EditText editText = (EditText) findViewById(R.id.edit_message);
	    //String message = editText.getText().toString();
	    //intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	public void findGameAction(View view){
        //Toast.makeText(this, "Find Game button clicked", Toast.LENGTH_LONG).show();
	    Intent intent = new Intent(this, FindGameActivity.class);
	    startActivity(intent);
	}
	
	public void settingsAction(View view){
        //Toast.makeText(this, "Settings button clicked", Toast.LENGTH_LONG).show();
	    Intent intent = new Intent(this, SettingsActivity.class);
	    startActivity(intent);
	}
	
}

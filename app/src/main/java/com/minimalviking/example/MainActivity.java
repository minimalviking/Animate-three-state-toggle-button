package com.minimalviking.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.minimalviking.animatedthreestatetogglebutton.AnimatedThreeStateToggleButton;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final AnimatedThreeStateToggleButton button = (AnimatedThreeStateToggleButton) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				button.animateTo((button.getState()+1)%3);
			}
		});
	}
}

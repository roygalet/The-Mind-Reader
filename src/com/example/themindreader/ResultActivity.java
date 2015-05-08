package com.example.themindreader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ResultActivity extends Activity {

	@Override
	public void onBackPressed() {
		// do nothing.
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		ImageView resultCard = (ImageView) findViewById(R.id.imageView1);
		Bundle bundle = getIntent().getExtras();
		String result = bundle.getString("guess");
		resultCard.setImageResource(getResources().getIdentifier(result,
				"drawable", getPackageName()));
		Animation animation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.zoom);
		resultCard.startAnimation(animation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	public void tryAgain(View view) {
		Intent playAgain = new Intent(getApplicationContext(),
				FirstActivity.class);
		startActivity(playAgain);
	}

}

package com.example.themindreader;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstActivity extends Activity {
	public static int totalNumberOfCards = 54;
	public static int numberOfRows = 7;
	public static int numberOfColumns = 3;
	String[] cards = new String[totalNumberOfCards];
	String[][] matrixCards = new String[numberOfRows][numberOfColumns];
	int clickCount;
	TextView messageTop, messageBottom;

	@Override
	public void onBackPressed() {
		// do nothing.
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);

		messageTop = (TextView) findViewById(R.id.txtTop);
		messageTop.setTextColor(Color.WHITE);
		messageTop.setText("Choose a card, KEEP it in MIND.");

		messageBottom = (TextView) findViewById(R.id.txtBottom);
		messageBottom.setTextColor(Color.WHITE);
		messageBottom.setText("TAP the COLUMN where it's at.");

		generateCards();
		randomizeCards();
		createMatrix();
		drawTheCards();

		clickCount = 0;
	}

	private void createMatrix() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				matrixCards[row][column] = cards[row * numberOfColumns + column];
			}
		}
	}

	private void generateCards() {
		for (int index = 0; index < cards.length; index++) {
			cards[index] = "c".concat(String.valueOf(index + 1));
		}
	}

	private void randomizeCards() {

		int i, j;
		String temp;
		for (int index = 0; index < cards.length; index++) {
			i = (int) (Math.random() * totalNumberOfCards);
			j = (int) (Math.random() * totalNumberOfCards);
			if (i != j) {
				temp = cards[i];
				cards[i] = cards[j];
				cards[j] = temp;
			}
		}
	}

	private void drawTheCards() {

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ImageView selectedImageView;
				String[] randomizedCards = new String[numberOfRows];
				int i, j;
				String temp;
				for (int column = 0; column < numberOfColumns; column++) {
					for (int index = 0; index < numberOfRows; index++) {
						randomizedCards[index] = matrixCards[index][column];
					}
					for (int index = 0; index < randomizedCards.length; index++) {
						i = (int) (Math.random() * randomizedCards.length);
						j = (int) (Math.random() * randomizedCards.length);
						if (i != j) {
							temp = randomizedCards[i];
							randomizedCards[i] = randomizedCards[j];
							randomizedCards[j] = temp;
						}
					}
					for (int row = 0; row < numberOfRows; row++) {

						selectedImageView = (ImageView) findViewById(getResources()
								.getIdentifier(
										"imageView".concat(
												String.valueOf(row + 1))
												.concat(String
														.valueOf(column + 1)),
										"id",
										getApplicationContext()
												.getPackageName()));
						selectedImageView.setImageResource(getResources()
								.getIdentifier(randomizedCards[row],
										"drawable", getPackageName()));
						selectedImageView.setVisibility(View.VISIBLE);
					}
				}
			}
		}, 100);
	}

	private void flipCard(ImageView card, ImageView frontView,
			ImageView backView) {

		FlipAnimation flipAnimation = new FlipAnimation(frontView, backView);

		if (frontView.getVisibility() == View.GONE) {
			flipAnimation.reverse();
		}
		card.startAnimation(flipAnimation);
	}

	private void faceCardsDown() {

		ImageView selectedImageView;
		for (int column = 0; column < numberOfColumns; column++) {
			for (int row = 0; row < numberOfRows; row++) {
				selectedImageView = (ImageView) findViewById(getResources()
						.getIdentifier(
								"imageView".concat(String.valueOf(row + 1))
										.concat(String.valueOf(column + 1)),
								"id", getApplicationContext().getPackageName()));

				selectedImageView.setImageResource(getResources()
						.getIdentifier("back", "drawable", getPackageName()));
				flipCard(selectedImageView, selectedImageView,
						selectedImageView);
			}
		}

	}

	public void firstColumnSelected(View view) {
		faceCardsDown();
		for (int index = 0; index < numberOfRows; index++) {
			cards[index] = matrixCards[index][2];
			cards[numberOfRows + index] = matrixCards[index][0];
			cards[2 * numberOfRows + index] = matrixCards[index][1];
		}

		createMatrix();
		countNumberOfClicks();

		drawTheCards();
	}

	public void secondColumnSelected(View view) {
		faceCardsDown();
		for (int index = 0; index < numberOfRows; index++) {
			cards[index] = matrixCards[index][2];
			cards[numberOfRows + index] = matrixCards[index][1];
			cards[2 * numberOfRows + index] = matrixCards[index][0];
		}
		createMatrix();
		countNumberOfClicks();
		drawTheCards();
	}

	public void thirdColumnSelected(View view) {
		faceCardsDown();
		for (int index = 0; index < numberOfRows; index++) {
			cards[index] = matrixCards[index][1];
			cards[numberOfRows + index] = matrixCards[index][2];
			cards[2 * numberOfRows + index] = matrixCards[index][0];
		}
		createMatrix();
		countNumberOfClicks();
		drawTheCards();
	}

	private void countNumberOfClicks() {
		clickCount++;
		if (clickCount >= 3) {
			Bundle bundle = new Bundle();
			bundle.putString("guess", matrixCards[3][1]);
			Intent showResult = new Intent(getApplicationContext(),
					ResultActivity.class);
			showResult.putExtras(bundle);
			startActivity(showResult);
		}else{
			messageTop.setText("You card is still there but has been MOVED somewhere.");
			messageBottom.setText("Again, TAP the COLUMN where it's at.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

}

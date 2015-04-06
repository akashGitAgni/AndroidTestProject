package com.example.testproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public interface UIupdate {
		public void updateImages(ArrayList<String> listOfAllImages,
				Bitmap defaultImage);
	}

	SlidingUpPanelLayout mPane;

	ArrayList<String> listOfAllImages;
	List<UIupdate> fragments;

	final Thread mThread = new Thread() {
		@Override
		public void run() {
			listOfAllImages = new ArrayList<String>();

			if (getImagesPath() == 0) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast toast = Toast.makeText(getApplicationContext(),
								"No Images vailable", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
			}
			final Bitmap defaultImage = BitmapFactory.decodeResource(
					getResources(), R.drawable.abc_cab_background_top_material);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					for (UIupdate obj : fragments) {
						obj.updateImages(listOfAllImages, defaultImage);
					}
				}
			});

		}
	};

	public int getImagesPath() {
		try {

			Uri uri;
			Cursor cursor;
			int column_index_data;

			String PathOfImage = null;
			uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			String[] projection = { MediaColumns.DATA };
			cursor = this.getContentResolver().query(uri, projection, null,
					null, null);
			if (cursor == null) {
				Log.e("mylogs", "cursor nullllll");
				return 0;

			}
			column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			while (cursor.moveToNext()) {
				PathOfImage = cursor.getString(column_index_data);
				listOfAllImages.add(PathOfImage);
				}
		} catch (Exception e) {
			Log.e("mylogs", "Some problems with cursor");
		}
		return listOfAllImages.size();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);

		fragments = new ArrayList<UIupdate>();
		mPane = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

		BottomFragment bFrag = new BottomFragment();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.bottom_pane, bFrag, "bottom").commit();
		fragments.add(bFrag);

		TopFragment tFrag = new TopFragment();
		getSupportFragmentManager().beginTransaction()
				.add(R.id.top_pane, tFrag, "top").commit();
		fragments.add(tFrag);

		mThread.start();

	}

	@Override
	protected void onDestroy() {
		if (listOfAllImages != null)
			listOfAllImages.clear();
		if (fragments != null)
			fragments.clear();
		super.onDestroy();
	}

}

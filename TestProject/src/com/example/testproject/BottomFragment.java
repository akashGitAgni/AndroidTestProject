package com.example.testproject;

import java.util.ArrayList;

import com.example.testproject.MainActivity.UIupdate;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BottomFragment extends Fragment implements UIupdate {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.bottom_fragment, container, false);

		return view;
	}

	@Override
	public void updateImages(ArrayList<String> listOfAllImages,
			Bitmap defaultImage) {
		ImageView img = (ImageView) getView().findViewById(R.id.bottomImage);
		if (listOfAllImages == null || listOfAllImages.size() == 0) {

			img.setImageBitmap(defaultImage);
			return;
		}

		final DecodeBitMapTask task = new DecodeBitMapTask(img,300);
		final Utils.AsyncDrawable asyncDrawable = new Utils.AsyncDrawable(
				getResources(), defaultImage, task);
		img.setImageDrawable(asyncDrawable);
		task.execute(listOfAllImages.get(0));
	}

}

package com.example.testproject;

import java.util.ArrayList;

import com.example.testproject.MainActivity.UIupdate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BottomFragment extends Fragment implements UIupdate,
		View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.bottom_fragment, container, false);

		ImageButton back = (ImageButton)view.findViewById(R.id.backbutton);
		back.setOnClickListener(this);		
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

		final DecodeBitMapTask task = new DecodeBitMapTask(img, 300);
		final Utils.AsyncDrawable asyncDrawable = new Utils.AsyncDrawable(
				getResources(), defaultImage, task);
		img.setImageDrawable(asyncDrawable);
		task.execute(listOfAllImages.get(0));
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.backbutton) {
			Log.d("mylog", "Back Button pressed");
			getActivity().onBackPressed();

		}

	}

}

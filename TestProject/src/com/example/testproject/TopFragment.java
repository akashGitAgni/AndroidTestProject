package com.example.testproject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.example.testproject.MainActivity.UIupdate;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TopFragment extends Fragment implements UIupdate {

	private RecyclerView mRecyclerView;
	private ImageAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.top_fragment, container, false);
		Button amt_button = (Button) view.findViewById(R.id.amt_button);
		amt_button.setOnClickListener(backButtonPress);
		return view;
	}

	View.OnClickListener backButtonPress = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Toast toast = Toast.makeText(getActivity(),
					"Amount Button Pressed", Toast.LENGTH_SHORT);
			toast.show();

		}
	};

	@Override
	public void updateImages(ArrayList<String> listOfAllImages,
			Bitmap defaultImage) {

		mRecyclerView = (RecyclerView) getView().findViewById(R.id.imageGrid);
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mAdapter = new ImageAdapter(listOfAllImages, R.layout.card_layout,
				getActivity(), defaultImage);
		mRecyclerView.setAdapter(mAdapter);

	}

	public class ImageAdapter extends RecyclerView.Adapter<ViewHolder> {
		private List<String> imagePaths;
		private int cardLayout;
		Bitmap icon = null;

		public ImageAdapter(List<String> listOfAllImages, int cardLayout,
				Context context, Bitmap defaultImage) {
			this.imagePaths = listOfAllImages;
			this.cardLayout = cardLayout;
			icon = defaultImage;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			View v = LayoutInflater.from(viewGroup.getContext()).inflate(
					cardLayout, viewGroup, false);
			return new ViewHolder(v);
		}

		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int i) {

			if (imagePaths == null || imagePaths.size() == 0)
				return;
			String path = imagePaths.get(i + 1);
			viewHolder.imageName.setText("Image " + (i + 1));

			if (Utils.cancelPotentialWork(path, viewHolder.image)) {
				final DecodeBitMapTask task = new DecodeBitMapTask(
						viewHolder.image, 100);
				final Utils.AsyncDrawable asyncDrawable = new Utils.AsyncDrawable(
						getResources(), icon, task);
				viewHolder.image.setImageDrawable(asyncDrawable);
				task.execute(path);
			}

		}

		@Override
		public int getItemCount() {
			return imagePaths == null ? 0 : (imagePaths.size() - 1);
		}

	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView imageName;
		public ImageView image;

		public ViewHolder(View itemView) {
			super(itemView);
			imageName = (TextView) itemView.findViewById(R.id.imageText);
			image = (ImageView) itemView.findViewById(R.id.image);

		}
	}

}

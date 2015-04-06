package com.example.testproject;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

class DecodeBitMapTask extends AsyncTask<String, Void, Bitmap> {
	private final WeakReference<ImageView> imageViewReference;
	String path;
	int size = 100;

	public DecodeBitMapTask(ImageView imageView, int size) {
		imageViewReference = new WeakReference<ImageView>(imageView);

	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}

		if (imageViewReference != null && bitmap != null) {
			final ImageView imageView = imageViewReference.get();
			final DecodeBitMapTask bitmapWorkerTask = Utils
					.getBitmapWorkerTask(imageView);
			Log.d("mylogs", "bitmapWorkerTask");
			if (this == bitmapWorkerTask && imageView != null) {
				imageView.setImageBitmap(bitmap);
				Log.d("mylogs", "setting bitmap");
			}
		}
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		path = params[0];

		return Utils.decodeSampledBitmapFromPath(path, size, size);

	}

}

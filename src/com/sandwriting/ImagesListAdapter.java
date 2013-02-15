package com.sandwriting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagesListAdapter extends ArrayAdapter<String> {

	private Activity mcontext;
	private ArrayList<String> listElements;
	private ArrayList<String> listElementsPath;
	private int resId;
	private LayoutInflater mInflater;
	HashMap<String, String> dates;

	public ImagesListAdapter(Activity context, int secondList,
			ArrayList<String> listElements,ArrayList<String> listElementsPath,
			ViewImages viewImages) {
		// TODO Auto-generated constructor stub
		super(context, secondList);
		mcontext =  context;
		this.listElements = listElements;
		this.resId = secondList;
		mInflater = mcontext.getLayoutInflater();
		this.listElementsPath = listElementsPath;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listElements.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		final int pos = position;
		// When convertView is not null, we can reuse it directly, there is no
		// need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.

		if (convertView == null) {
			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			convertView = mInflater.inflate(resId, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.pic = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		} else {
			// // // Get the ViewHolder back to get fast access to the TextView
			// // // and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		holder.title.setText(listElements.get(position));
		
		File imgFile = new File(listElementsPath.get(position));
		if (imgFile.exists()) {

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());
			holder.pic.setImageBitmap(myBitmap);
		}

		return convertView;
	}

	static class ViewHolder {

		private TextView title;
		
		private ImageView pic;
		

	}

}

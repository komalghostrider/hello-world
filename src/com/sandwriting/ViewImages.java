package com.sandwriting;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class ViewImages extends Activity {

	File dir ;
	private ArrayList<String> directoryEntries = new ArrayList<String>();
	private ArrayList<String> directoryEntriesPath = new ArrayList<String>()  ;
	ImagesListAdapter imagesListAdapter;
	ListView imageListView;
	 RelativeLayout rl2;     
     private AdView ad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.image_list);
		rl2 = (RelativeLayout) findViewById(R.id.relative_list);
		 String id = "a15092090f79166";
		 ad = new AdView(this, AdSize.BANNER, id);    
		 rl2.addView(ad);
		 ad.loadAd(new AdRequest());
		imageListView = (ListView)findViewById(R.id.lv_image_list);
		String filePath = Environment.getExternalStorageDirectory()
				+ File.separator + "sandwriting";

		dir = new File(filePath);   
		try {
			dir.mkdirs();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		browseTo(dir);
		
		
		setListAdapter();
	

	}
	
	private void browseTo(File aDirectory) {
		// if we want to browse directory
		if (aDirectory.isDirectory()) {
			// fill list with files from this directory
			fill(aDirectory.listFiles());


		} 
	}

	private void fill(File[] files) {
		// clear list
		this.directoryEntries.clear();
		this.directoryEntriesPath.clear();

		// add every file into list
		if (files != null) {
			for (File file : files) {
				// if(isFileSupported(file))
				// {
				if(file.getName().contains("temp"))
					continue;
				this.directoryEntries.add(file.getName());
				this.directoryEntriesPath.add(file.getAbsolutePath());
				// }
			}
		}
		// create array adapter to show everything
		// FileListAdapter directoryList = new FileListAdapter(this,
		// R.layout.row,
		// this.directoryEntries);
		// fileList.setAdapter(directoryList);
	}

	
	private void setListAdapter() {
		// TODO Auto-generated method stub
		imagesListAdapter = new ImagesListAdapter(this,
				R.layout.list_elements, directoryEntries,directoryEntriesPath, this);
		imageListView.setAdapter(imagesListAdapter);
		
		imageListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), FsImage.class);
				intent.putExtra("path", directoryEntriesPath.get(position));
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		ViewImages.this.finish();         
		super.onPause();
	}

}

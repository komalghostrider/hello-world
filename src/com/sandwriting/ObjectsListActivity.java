/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sandwriting;

import java.util.Set;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class ObjectsListActivity extends Activity {
	// Debugging
	private static final String TAG = "ObjectsListActivity";

	private ArrayAdapter<String> mObjectsArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup the window
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.objects_list);

		Button cancelButton = (Button) findViewById(R.id.button_cancel);            
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		// Initialize array adapters.
	
		// Find and set up the ListView for paired devices
		ListView objectListView = (ListView) findViewById(R.id.lv_objects);
		objectListView.setAdapter(new StudentListAdapter(this));

		objectListView.setOnItemClickListener(new OnItemClickListener() {     
	        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
	        	System.out.println(position);
				if(position==0)
					FingerPaint.showImage1 = true;
				else
					FingerPaint.showImage2=true;
				finish();
			}
	       
	        });
		
		
		
	}

	 private class StudentListAdapter extends BaseAdapter {
		 private Context mContext;
		 private String[] mStudents = 
	        {
	                "Stone1",   
	                "Stone2"
	                
	        };
		 private int[] mDetailsStudent = 
	        {
				   
	           R.drawable.pic1,R.drawable.pic2
	          
	              
	        };
	        public StudentListAdapter(Context context) {
	            mContext = context;
	        }
	        
	        public int getCount() {
	            return mStudents.length;
	        }
	        public Object getItem(int position) {
	            return position;
	        }
	        public long getItemId(int position) {
	            return position;
	        }
	        public View getView(int position, View convertView, ViewGroup parent) {
	        	View v = convertView;
	        	if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.object_list1, null);
                }
	        	TextView tvname = (TextView)v.findViewById(R.id.myTextView);
	        	ImageView tvdetail = (ImageView)v.findViewById(R.id.myImageView);
	        //	TextView abc = (TextView)v.findViewById(R.id.abc);

	        	tvname.setText(mStudents[position]);
	        	tvdetail.setImageResource(mDetailsStudent[position]);
	        //	abc.setText("SudheerReddy");
	        	
	        	
	            return v;
	        }
	 };
	 

}

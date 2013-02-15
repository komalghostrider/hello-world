package com.sandwriting;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class FsImage extends Activity{
	
	ImageView image;
	 RelativeLayout rl2;     
     private AdView ad;
     Button b1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen_image);
		rl2 = (RelativeLayout) findViewById(R.id.relative_full_image);
		 String id = "a15092090f79166";
		 ad = new AdView(this, AdSize.BANNER, id);    
		 rl2.addView(ad);
		 ad.loadAd(new AdRequest());
		image = (ImageView)findViewById(R.id.fs_image);
		b1=(Button)findViewById(R.id.button1);
		File imgFile = new File(this.getIntent().getExtras().getString("path"));
		if (imgFile.exists()) {

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());
			image.setImageBitmap(myBitmap);
		} 
		 b1.setOnClickListener(new OnClickListener() {
				
				@Override  
				public void onClick(View v) {
					// TODO Auto-generated method stub
					File imgFile = new File(FsImage.this.getIntent().getExtras().getString("path"));
					imgFile.delete();
				  FsImage.this.startActivity(new Intent(FsImage.this,ViewImages.class));
				}
			});
	}              
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub       
	FsImage.this.finish();
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {                    
		// TODO Auto-generated method stub
		
		 if ((keyCode == KeyEvent.KEYCODE_BACK)) {          
			 FsImage.this.startActivity(new Intent(FsImage.this,ViewImages.class));  
		    }
		return super.onKeyDown(keyCode, event);      
	}
	
}

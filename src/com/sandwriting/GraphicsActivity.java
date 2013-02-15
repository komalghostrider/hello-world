package com.sandwriting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class GraphicsActivity extends Activity implements View.OnClickListener,UberColorPickerDialog.OnColorChangedListener {

	private static final int COLOR_MENU_ID = Menu.FIRST;
	private static final int EMBOSS_MENU_ID = Menu.FIRST + 1;   
	private static final int BLUR_MENU_ID = Menu.FIRST + 2;   
	private static final int ERASE_MENU_ID = Menu.FIRST + 3;
	private static final int SRCATOP_MENU_ID = Menu.FIRST + 4;      
	private AdView ad;
	RelativeLayout rl2;
	MediaPlayer mPlay;
	public static int mColor = 0x22000000;
	String dt;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	File imageFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		rl2 = (RelativeLayout) findViewById(R.id.relativeLayout4);               
		String id = "a150659622426f4";   
		ad = new AdView(this, AdSize.BANNER, id);
		rl2.addView(ad);
		ad.loadAd(new AdRequest());
		Button b1 = (Button) findViewById(R.id.button_erase);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FingerPaint.showImage1 = false;
				FingerPaint.showImage2 = false;
				GraphicsActivity.this.finish();
				GraphicsActivity.this.startActivity(new Intent(
						GraphicsActivity.this, GraphicsActivity.class));
			}
		});  

	}  

	   
	
	public void onClickObjects(View v) {
//		Intent intent = new Intent(this, ObjectsListActivity.class);
//		startActivity(intent);
		new UberColorPickerDialog(this, this, 0, false).show(); 
	}

	public void onClickSave(View v) {
		save();
	}

	public void onClickGallery(View v) {
		Intent intent = new Intent(this, ViewImages.class);    
		startActivity(intent);
	}

	public void onClickShare(View v) {
		FingerPaint image = (FingerPaint) findViewById(R.id.fingerpaint);
		image.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(image.getDrawingCache());
		image.setDrawingCacheEnabled(false);
		share(bitmap);
	}

	private void share(Bitmap bitmap) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("image/jpeg");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		File f = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "sandwriting" + File.separator
				+ ".temporary_file.jpg");
		try {
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		share.putExtra(Intent.EXTRA_STREAM,
				Uri.parse("file:///sdcard/sandwriting/.temporary_file.jpg"));
		startActivity(Intent.createChooser(share, "Share Image"));
	}

	public void save() {

		Bitmap bitmap;
		/*
		 * ImageView i = (ImageView) findViewById(R.id.imageView1);
		 * i.setScaleType(ScaleType.FIT_XY); ImageView im = (ImageView)
		 * findViewById(R.id.imageView2); im.setVisibility(View.INVISIBLE);
		 */
		FingerPaint v = (FingerPaint) findViewById(R.id.fingerpaint);
		String mPath = Environment.getExternalStorageDirectory().toString();

		// View v1 = i.getRootView();
		v.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v.getDrawingCache());
		v.setDrawingCacheEnabled(false);

		OutputStream fout = null;
		File directory = new File(mPath, "sandwriting");
		directory.mkdirs();

		try {

			imageFile = new File(directory, "sandwriting" + "_" + getDate()
					+ ".jpg");
			fout = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
			fout.flush();
			fout.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * rl = (RelativeLayout) findViewById(R.id.relativeLayout1);
		 * rl.setVisibility(View.VISIBLE);
		 * 
		 * rl2 = (RelativeLayout) findViewById(R.id.relativeLayout4);
		 * rl2.setVisibility(View.VISIBLE); im.setVisibility(View.VISIBLE);
		 */
		Toast.makeText(getApplicationContext(), "'Image saved to SDCARD'",
				Toast.LENGTH_SHORT).show();
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	private String getDate() {
		// TODO Auto-generated method stub
		dt = dateFormat.format(new Date());
		return dt;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mPlay = MediaPlayer.create(this, R.raw.sequence);
		mPlay.start();
		FingerPaint v = (FingerPaint) findViewById(R.id.fingerpaint);
		if (v != null)
			v.invalidate();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mPlay.stop();
		mPlay.release();
		mPlay = null;
		super.onPause();
	}

	// @Override
	// protected void onDestroy() {
	// // TODO Auto-generated method stub
	// mPlay.stop();
	// mPlay.release();
	// mPlay = null ;
	// super.onDestroy();
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
		// menu.add(0, EMBOSS_MENU_ID, 0, "Emboss").setShortcut('4', 's');
		// menu.add(0, BLUR_MENU_ID, 0, "Blur").setShortcut('5', 'z');
		// menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'z');
		// menu.add(0, SRCATOP_MENU_ID, 0, "pic stone").setShortcut('5', 'z');

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * mPaint.setXfermode(null); mPaint.setAlpha(0xFF);
		 */

		switch (item.getItemId()) {
		/*
		 * case COLOR_MENU_ID: new ColorPickerDialog(this, this,
		 * mPaint.getColor()).show(); return true; case EMBOSS_MENU_ID: if
		 * (mPaint.getMaskFilter() != mEmboss) { mPaint.setMaskFilter(mEmboss);
		 * 
		 * System.out.println("hello"); } else { mPaint.setMaskFilter(null); }
		 * return true; case BLUR_MENU_ID: if (mPaint.getMaskFilter() != mBlur)
		 * { mPaint.setMaskFilter(mBlur); } else { mPaint.setMaskFilter(null); }
		 * return true;
		 */
		case ERASE_MENU_ID:
			// mPath.reset();
			GraphicsActivity.this.finish();
			GraphicsActivity.this.startActivity(new Intent(
					GraphicsActivity.this, GraphicsActivity.class));
			// mPaint.setXfermode(new
			// PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			// mPaint.setColor(0xFFa7764d);
			return true;
		case SRCATOP_MENU_ID:
			// mPaint.setXfermode(new
			// PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
			// mPaint.setAlpha(0x80);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GraphicsActivity.this);

			// set title
			alertDialogBuilder.setTitle("pic stone");

			// set dialog message
			alertDialogBuilder

					.setCancelable(false)
					.setPositiveButton("stone 1",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									FingerPaint.image1();
								}
							})
					.setNegativeButton("stone 2",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void colorChanged(int color) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		mColor=color;
		System.out.println(mColor);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}





}

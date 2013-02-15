package com.sandwriting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class FingerPaint extends View implements
		ColorPickerDialog.OnColorChangedListener {
	// private Path mPath;
	private Paint mPaint;
	private MaskFilter mEmboss;
	private MaskFilter mBlur;            

	private Paint boundaryPaint;    
	
//	4d402f
	
	int color1 = 0x22000000;
	int color2 = 0xBB827759;
	int color3 = 0xBBa29783;
	int color4 = 0xBBCC9966;

	int a = 0;
	
	private static final float MINP = 0.25f;
	private static final float MAXP = 0.75f;

	public static Bitmap mBitmap, mBitmap1,myimage,myimage1;
	public static Canvas mCanvas;
	private Path mPath;
	private Path mPath2;
	private Paint mBitmapPaint;
	int width,height;
	RectF rect1,rect2;
	Paint paintRect;
	PointF image1Point,image2Point;
	Boolean moveImage1 = false,moveImage2 = false,drawPath;
	static Boolean showImage1 = false;
	static Boolean showImage2 = false;

	public void colorChanged(int color) {
		//mPaint.setColor(color);
	}
	
	
		public FingerPaint(Context c,AttributeSet attrs) {
			super(c,attrs);    
			
			
			
			Typeface plain = Typeface.createFromAsset(c.getAssets(), "parchment.ttf");
//			Typeface bold = Typeface.create(plain, Typeface.DEFAULT_BOLD);
			Typeface bold = Typeface.create(plain, Typeface.BOLD);
			
			paintRect = new Paint();
			paintRect.setColor(Color.BLACK);
			
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			
//			mPaint.setColor(0xFFFF9933);
			
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
//			mPaint.setMaskFilter(new BlurMaskFilter(20, Blur.INNER));
			
	//		mPaint.setShadowLayer(10, 3, 3, Color.WHITE);
			
	 
			mPaint.setColor(color1);
//			mPaint.setShader(new RadialGradient(0f, 0f, 8f,color1,color1, Shader.TileMode.MIRROR));
			
			mPaint.setStrokeCap(Paint.Cap.ROUND);   
			mPaint.setStrokeWidth(14);
			
			boundaryPaint = new Paint();
			boundaryPaint.setAntiAlias(true);
			boundaryPaint.setDither(false);
//			boundaryPaint.setColor(0xFFFF8C1A);
			boundaryPaint.setColor(0x22FFFFFF);
			boundaryPaint.setStyle(Paint.Style.STROKE);
			boundaryPaint.setStrokeJoin(Paint.Join.ROUND);       
			boundaryPaint.setStrokeCap(Paint.Cap.ROUND);
			boundaryPaint.setStrokeWidth(22);
			
//			boundaryPaint.setMaskFilter(new BlurMaskFilter(18, Blur.OUTER));

			mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);   

			mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);           
			WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			 width = display.getWidth();
			 height = display.getHeight();
			// mBitmap = Bitmap.createBitmap(width,
			// height,Bitmap.Config.ARGB_8888);
			// System.out.println("bitmap is  "+mBitmap );
			// mCanvas = new Canvas(mBitmap);

			mBitmap1 = BitmapFactory.decodeResource(getResources(),
					R.drawable.bg1);
			myimage = BitmapFactory.decodeResource(getResources(),
					R.drawable.pic1);
			myimage1 = BitmapFactory.decodeResource(getResources(),
					R.drawable.pic2);
			
			mBitmap1 = Bitmap.createScaledBitmap(mBitmap1, width, height, true);
			mBitmap = mBitmap1.copy(Bitmap.Config.ARGB_8888, true);
			mCanvas = new Canvas(mBitmap1);
            
			mPath = new Path();
			mPath.moveTo(0, -10);
			mPath.lineTo(5, 0);     
			mPath.lineTo(-5, 0);
			mPath2 = new Path();
			mPath2.moveTo(0, -10);
			mPath2.lineTo(5, 0);
			mPath2.lineTo(-5, 0);
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
			
			image1Point = new PointF(width/2, height/2);
			image2Point = new PointF(100,100);
			rect1 = new RectF(image1Point.x, image1Point.y, image1Point.x+51, image1Point.y+72);
			rect2 = new RectF(image2Point.x, image2Point.y, image2Point.x+51, image2Point.y+72);

		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
		}

		@Override
		protected void onDraw(Canvas canvas) {
//			canvas.drawColor(0x6FAAAAAA);
		  mPaint.setColor(GraphicsActivity.mColor);
		color1=GraphicsActivity.mColor;
			
			
			
			
			
			
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
			
			canvas.drawPath(mPath, boundaryPaint);
			canvas.drawPath(mPath, mPaint);
			if(showImage1)
			canvas.drawBitmap(myimage, image1Point.x-25, image1Point.y-36,null);
			if(showImage2)
			canvas.drawBitmap(myimage1, image2Point.x-25, image2Point.y-36,null);
			
			Log.d("TAG", "here");
		}

		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;

		private void touch_start(float x, float y) {
			// mPath.reset();
			// mPaint.reset();
			
			mPath.moveTo(x, y);
		//	mPath2.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			//	mPath2.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			
				mX = x;
				mY = y;
			}
		}

		public static void image1()
		{
			myimage.recycle();
		}
		private void touch_up() {
			mPath.lineTo(mX, mY);
		//	mPath2.lineTo(mX, mY);
			// commit the path to our offscreen
//			mCanvas.drawPath(mPath, mPaint);
          //  mPath2.reset();
			// kill this so we don't double draw
			
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(rect1.contains(x, y)){
					moveImage1 = true;
					return true;
				}else if(rect2.contains(x, y)){
					moveImage2 = true;
					return true;
				}else{
					drawPath = true;
				}
				touch_start(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				if(moveImage1||moveImage2){
					if(moveImage1&&(!rect1.contains(x, y))){
						image1Point.x = x;
						image1Point.y = y;
						rect1.set(x, y, x+51, y+72);
					}else if(moveImage2&&(!rect2.contains(x, y))){
						image2Point.x = x;
						image2Point.y = y;
						rect2.set(x, y, x+51, y+72);
					}
					invalidate();
					return true;
				}
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				if(moveImage1||moveImage2){
					moveImage1 = false;
					moveImage2 = false;
					drawPath = false;
				}
				touch_up();
				invalidate();
				
				break;
			}
			return true;
		}
	}

	
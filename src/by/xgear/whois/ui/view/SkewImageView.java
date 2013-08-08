package by.xgear.whois.ui.view;

import by.xgear.whois.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SkewImageView extends ImageView {
	
	private Bitmap data;
	private Bitmap[] bArr;
	private Matrix skew;
	private Camera mCamera;
	private int angle = 0;
	
	private boolean isInOffsetMode;
	private int mOffset;

	public SkewImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		int[] attrsArray = new int[] {
//		        android.R.attr.src, // 0
//		    };
//		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SkewImageView);
//		Drawable background = ta.getDrawable(R.styleable.SkewImageView_img);
//		ta.recycle();
		
//		int width = background.getIntrinsicWidth();
//	    width = width > 0 ? width : 1;
//	    int height = background.getIntrinsicHeight();
//	    height = height > 0 ? height : 1;
//	    data = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//	    Canvas canvas = new Canvas(data); 
//	    background.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//	    background.draw(canvas);
//	    Bitmap.
	    data = BitmapFactory.decodeResource(getResources(), R.drawable.strictdroid_lined);
	    data = Bitmap.createScaledBitmap(data, 400, 400, true);
	    bArr = new Bitmap[4];

	    bArr[0] = Bitmap.createBitmap(data, 0, 0, 400, 100); 
	    bArr[1] = Bitmap.createBitmap(data, 0, 100, 400, 100); 
	    bArr[2] = Bitmap.createBitmap(data, 0, 200, 400, 100); 
	    bArr[3] = Bitmap.createBitmap(data, 0, 300, 400, 100); 
	    
	    skew = new Matrix();
	    mCamera = new Camera();
	}

	public SkewImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SkewImageView(Context context) {
		this(context, null, 0);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		Matrix m0 = getRotationMatrix(angle);
		canvas.drawBitmap(bArr[0], m0, null);
		
		Matrix m1 = getRotationMatrix(angle);
		m1.postTranslate(0, 100);
		canvas.drawBitmap(bArr[1], m1, null);
		
		Matrix m2 = getRotationMatrix(angle);
		m2.postTranslate(0, 200);
		canvas.drawBitmap(bArr[2], m2, null);
		
		Matrix m3 = getRotationMatrix(angle);
		m3.postTranslate(0, 300);
		canvas.drawBitmap(bArr[3], m3, null);
//		canvas.drawBitmap(applyMatrix(bArr[1], 1), 0, 100, null);
//		canvas.drawBitmap(applyMatrix(bArr[2], 2), 0, 200, null);
//		canvas.drawBitmap(applyMatrix(bArr[3], 3), 0, 300, null);

////		canvas.drawBitmap(data, 0, 0, null);
//		skewCanvas(canvas);
		super.onDraw(canvas);
		canvas.restore();
	}
	
	public void skewCanvas(Canvas canvas) {
		  mCamera.save();
		  mCamera.rotateX(angle);
		  mCamera.getMatrix(skew);
		  mCamera.restore(); 

		  int CenterX = 200;
		  int CenterY = 50; 
		  skew.preTranslate(-CenterX, -CenterY); //This is the key to getting the correct viewing perspective
		  skew.postTranslate(CenterX, CenterY); 
		  
		  canvas.concat(skew);
	}
	
	public Bitmap applyMatrix(Bitmap bmp, int i) {
		  mCamera.save();
		  mCamera.rotateX(angle);
		  mCamera.rotateY(0);
		  mCamera.rotateZ(0);
		  mCamera.getMatrix(skew);
		  mCamera.restore(); 

		  int CenterX = 200;
		  int CenterY = 50+i*100; 
		  skew.preTranslate(-CenterX, -CenterY); //This is the key to getting the correct viewing perspective
		  skew.postTranslate(CenterX, CenterY); 
		  
		  Bitmap result = Bitmap.createBitmap(bmp, 0, 0, 400, 400, skew, true);
		  return result;
		}
	
	private Matrix getRotationMatrix(int angle) {
		  mCamera.save();
		  mCamera.rotateX(angle);
		  mCamera.rotateY(0);
		  mCamera.rotateZ(0);
		  mCamera.getMatrix(skew);
		  mCamera.restore(); 

		  int CenterX = 200;
		  int CenterY = 50; 
		  skew.preTranslate(-CenterX, -CenterY); //This is the key to getting the correct viewing perspective
		  skew.postTranslate(CenterX, CenterY); 
		  return skew;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	
}

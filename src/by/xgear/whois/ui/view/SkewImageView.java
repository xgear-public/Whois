package by.xgear.whois.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SkewImageView extends ImageView {
	
	private Bitmap data;
	private Matrix skew;
	private Camera mCamera;

	public SkewImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		int[] attrsArray = new int[] {
		        android.R.attr.src, // 0
		    };
		TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
		Drawable background = ta.getDrawable(0);
		ta.recycle();
		
		int width = background.getIntrinsicWidth();
	    width = width > 0 ? width : 1;
	    int height = background.getIntrinsicHeight();
	    height = height > 0 ? height : 1;
	    data = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	    Canvas canvas = new Canvas(data); 
	    background.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    background.draw(canvas);
	    data = Bitmap.createScaledBitmap(data, 300, 300, false);
	    
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
		applyMatrix(canvas);
		super.onDraw(canvas);
		canvas.restore();
	}
	
	public void applyMatrix(Canvas canvas) {
		  mCamera.save();
		  mCamera.rotateX(45);
		  mCamera.rotateY(0);
		  mCamera.rotateZ(0);
		  mCamera.getMatrix(skew);

		  int CenterX = 300 / 2;
		  int CenterY = 300 / 2; 
		  skew.preTranslate(-CenterX, -CenterY); //This is the key to getting the correct viewing perspective
		  skew.postTranslate(CenterX, CenterY); 
		  canvas.concat(skew);
		  mCamera.restore();    
		}
	
	

	
}

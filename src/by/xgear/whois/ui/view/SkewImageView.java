package by.xgear.whois.ui.view;

import by.xgear.whois.R;
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
	private Bitmap[] bArr;
	private Matrix skew;
	private Camera mCamera;
	private int angle = 0;

	public SkewImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		int[] attrsArray = new int[] {
//		        android.R.attr.src, // 0
//		    };
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SkewImageView);
		Drawable background = ta.getDrawable(R.styleable.SkewImageView_img);
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
	    bArr = new Bitmap[3];

	    bArr[0] = Bitmap.createBitmap(data, 0, 0, 300, 100); 
	    bArr[1] = Bitmap.createBitmap(data, 0, 100, 300, 100); 
	    bArr[2] = Bitmap.createBitmap(data, 0, 200, 300, 100); 
	    
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
		canvas.drawBitmap(applyMatrix(bArr[0], 0), 0, 0, null);
		canvas.drawBitmap(applyMatrix(bArr[1], 1), 0, 100, null);
		canvas.drawBitmap(applyMatrix(bArr[2], 2), 0, 200, null);
//		applyMatrix(canvas);
		super.onDraw(canvas);
		canvas.restore();
	}
	
	public Bitmap applyMatrix(Bitmap bmp, int i) {
		  mCamera.save();
		  mCamera.rotateX(angle);
		  mCamera.rotateY(0);
		  mCamera.rotateZ(0);
		  mCamera.getMatrix(skew);

		  int CenterX = 150;
		  int CenterY = 50; 
		  skew.preTranslate(-CenterX, -CenterY); //This is the key to getting the correct viewing perspective
		  skew.postTranslate(CenterX, CenterY); 
		  
		  Bitmap result = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), skew, true);
//		  canvas.concat(skew);
		  mCamera.restore(); 
		  return result;
		}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	
}

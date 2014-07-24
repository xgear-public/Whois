package by.xgear.whois.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ImageView;
import by.xgear.whois.R;

public class SkewImageView extends ImageView {

    public enum Direction{
        AXIS_X,
        AXIS_Y;
    }

    public enum MovementDirection{
        LEFT,
        RIGHT,
        UP,
        DOWN;
    }
	
	private Direction mDirection = Direction.AXIS_Y;
	private MovementDirection mMDirection = MovementDirection.DOWN;

	private Bitmap[] bArr;
	private Matrix skew;
	private Camera mCamera;
	private int angle;
	private int mLouversCount = 8;
	
	private boolean isInOffsetMode;
	private int mOffset = 0;

	private float mLouverWidth = 100;
	private float MAX_ANGLE = 40;

    private boolean mIsScrolling;
    private GestureDetectorCompat mGestureDetector;
//    private VelocityTracker mVelocityTracker;

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

        mGestureDetector = new GestureDetectorCompat(context, mGestureListener);

        Bitmap data = BitmapFactory.decodeResource(getResources(), R.drawable.skew);
	    data = Bitmap.createScaledBitmap(data, 400, 800, true);
	    bArr = new Bitmap[8];

	    bArr[0] = Bitmap.createBitmap(data, 0, 0, 400, 100); 
	    bArr[1] = Bitmap.createBitmap(data, 0, 100, 400, 100); 
	    bArr[2] = Bitmap.createBitmap(data, 0, 200, 400, 100); 
	    bArr[3] = Bitmap.createBitmap(data, 0, 300, 400, 100); 
	    bArr[4] = Bitmap.createBitmap(data, 0, 400, 400, 100); 
	    bArr[5] = Bitmap.createBitmap(data, 0, 500, 400, 100); 
	    bArr[6] = Bitmap.createBitmap(data, 0, 600, 400, 100); 
	    bArr[7] = Bitmap.createBitmap(data, 0, 700, 400, 100);

        data.recycle();
        data = null;
	    
	    skew = new Matrix();
	    mCamera = new Camera();

        mLockEdge = (int) ((mLouversCount-1) * mLouverWidth - mLouverWidth * 3/4 - (mLouversCount-1) * mLouverWidth/4 + mLouverWidth);
	}

	public SkewImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SkewImageView(Context context) {
		this(context, null, 0);
	}

	private int i;
	private boolean isLocked;
    private int mLockEdge;
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.drawColor(Color.WHITE);

        isLocked = mLockEdge < mOffset;
        for(i=mLouversCount-1; i>=0;i--){
            Matrix m= getRotationMatrix(getAngleByOffset(mOffset, i));
            if(!isLocked)
                m.postTranslate(0, i*mLouverWidth+getMarginByOffset(mOffset, i));
            else{
                mOffset = mLockEdge;
                m.postTranslate(0, i*mLouverWidth+getMarginByOffset(mOffset, i));
            }
            canvas.drawBitmap(bArr[i], m, null);
        }
		
/*		Matrix m6 = getRotationMatrix(getAngleByOffset(mOffset, 6));
		m6.postTranslate(0, 600+getMarginByOffset(mOffset, 6));
		canvas.drawBitmap(bArr[6], m6, null);
		
		Matrix m5 = getRotationMatrix(getAngleByOffset(mOffset, 5));
		m5.postTranslate(0, 500+getMarginByOffset(mOffset, 5));
		canvas.drawBitmap(bArr[5], m5, null);
		
		Matrix m4 = getRotationMatrix(getAngleByOffset(mOffset, 4));
		m4.postTranslate(0, 400+getMarginByOffset(mOffset, 4));
		canvas.drawBitmap(bArr[4], m4, null);
		
		Matrix m3 = getRotationMatrix(getAngleByOffset(mOffset, 3));
		m3.postTranslate(0, 300+getMarginByOffset(mOffset, 3));
		canvas.drawBitmap(bArr[3], m3, null);
		
		Matrix m2 = getRotationMatrix(getAngleByOffset(mOffset, 2));
		m2.postTranslate(0, 200+getMarginByOffset(mOffset, 2));
		canvas.drawBitmap(bArr[2], m2, null);
		
		Matrix m1 = getRotationMatrix(getAngleByOffset(mOffset, 1));
		m1.postTranslate(0, 100+getMarginByOffset(mOffset, 1));
		canvas.drawBitmap(bArr[1], m1, null);*/
		
//		Matrix m0 = getRotationMatrix(getAngleByOffset(mOffset, 0));
//		m0.postTranslate(0, 0+getMarginByOffset(mOffset, 0));
//		canvas.drawBitmap(bArr[0], m0, null);
		
//		canvas.drawBitmap(applyMatrix(bArr[1], 1), 0, 100, null);
//		canvas.drawBitmap(applyMatrix(bArr[2], 2), 0, 200, null);
//		canvas.drawBitmap(applyMatrix(bArr[3], 3), 0, 300, null);

////		canvas.drawBitmap(data, 0, 0, null);
//		skewCanvas(canvas);
		super.onDraw(canvas);
		canvas.restore();
	}
	
	private float getAngleByOffset(float offset, int i) {
//		return 0;
		int angle = 0;
		offset+=i*30;
		if(offset < (i+1)*mLouverWidth-mLouverWidth*3/4)
			return angle;
		else if(offset > (i+1)*mLouverWidth-mLouverWidth/4)
			return MAX_ANGLE;
		else
			return MAX_ANGLE*Math.abs((offset - ((i)*mLouverWidth+mLouverWidth/4)))/(mLouverWidth/2);
	}
	
	private int getMarginByOffset(int offset, int i) {
		if(offset < i*mLouverWidth-mLouverWidth*3/4 - i*mLouverWidth/4 + mLouverWidth)
			return 0;
		else
			return (int) (offset - (i*mLouverWidth - mLouverWidth*3/4 - i*mLouverWidth/4 + mLouverWidth));
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
	
	private Matrix getRotationMatrix(float angle) {
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
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP)
            mIsScrolling = false;
        if(event.getAction() == MotionEvent.ACTION_CANCEL)
            mIsScrolling = false;
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            mIsScrolling = isBorderHit(event.getX(), event.getY());
        }

//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            if(mIsScrolling ) {
//                Log.d("OnTouchListener --> onTouch ACTION_UP");
//                mIsScrolling  = false;
//                handleScrollFinished();
//            };
//        }
        if(mIsScrolling)
		    mGestureDetector.onTouchEvent(event);
		return true;
	}
	
	private final GestureDetector.SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener(){


		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
//			Log.d("Pelkin", "x1="+e1.getX()+" y1="+e1.getY()+" mOffset = "+mOffset);
//			Log.d("Pelkin", "x2="+e2.getX()+" y2="+e2.getY());
//			if(isBorderHit(e2.getX(), e2.getY())) {
                mOffset-=distanceY;
                mOffset = mOffset < 0 ? 0 : mOffset;
                invalidate();
//			}
			Log.d("Pelkin", "mOffset=" + mOffset + "\tdistanceY = " + distanceY);
//			Log.d("Pelkin", "x="+e1.getX()+" y="+e1.getY()+" onScroll distanceX = "+distanceX+"\tdistanceY = "+distanceY);
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
		
	};
	
	
	//TODO look if scroll started near border and only then hit!
	private boolean isBorderHit(float x, float y) {
        Log.d("Pelkin", "isBorderHit");
		switch (mDirection) {
			case AXIS_X:{//TODO mLouverWidth change to constants multiplexed on dp
				return x < mOffset + mLouverWidth*1.5 && x > mOffset-mLouverWidth*0.5;
			}
			case AXIS_Y:{
                if(y < mLouverWidth * 1.5) {
                    mMDirection = MovementDirection.DOWN;
                    return true;
                }else if(y > mLouverWidth * (mLouversCount - 1)) {
                    mMDirection = MovementDirection.UP;
                    mOffset = mLockEdge;
                    return true;
                } else {
                    return false;
                }
//				return y < mOffset + mLouverWidth*1.5 && y > mOffset-mLouverWidth*0.5;
			}
			default:{
				return false;
			}
		}
	}


    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int mOffset) {
        this.mOffset = mOffset;
    }
}

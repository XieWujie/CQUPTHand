package com.example.xiewujie.cqupthand.tool;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.xiewujie.cqupthand.R;

public class CircleImageView extends View {
    private Bitmap bitmap;
    private Paint mPaint;
    private int size;
    private int resId;
    private int resultSize;
    int paddingLeft;
    int paddingTop;
    public CircleImageView(Context context) {
        super(context);
    }

    private PorterDuffXfermode duffXfermode;
    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        resId = array.getResourceId(R.styleable.CircleImageView_src,R.drawable.ic_launcher_background);
        initPaint();
        array.recycle();
    }
    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        duffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        bitmap  = initBitmap(resId);
        size = getSize();
    }
    public void setImageBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        size = getSize();
        invalidate();
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public void setImageResource(int resId){
        bitmap=initBitmap(resId);
        size = getSize();
        invalidate();
    }

    private Bitmap initBitmap(int resId){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resId);
        size = getSize();
        return bitmap;
    }
    public int getSize(){
        int size = 0;
        if (bitmap!=null){
            int width = bitmap.getWidth();
            int hight = bitmap.getHeight();
            size = getMin(width,hight);
            if (size==0){

            }
        }
        return size;
    }
    private Bitmap makeCircle(){
        if (size==0)
            size =40;
        Bitmap bitmap1 = Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(resultSize/2+paddingLeft,resultSize/2+paddingTop,resultSize/2,paint);
        return bitmap1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode==heightMode&&widthMode==MeasureSpec.AT_MOST){
            resultSize = size;
        }else if (widthMode==MeasureSpec.AT_MOST){
            resultSize = height;
        }else if (heightMode==MeasureSpec.AT_MOST){
            resultSize = width;
        }else {
            resultSize = getMin(width,height);
        }
        setMeasuredDimension(resultSize,resultSize);
        paddingLeft = getPaddingLeft();
        int paddingRigth = getPaddingRight();
        paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        resultSize = getMin(resultSize-paddingLeft-paddingRigth,resultSize-paddingBottom-paddingTop);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0,0,size,size,mPaint,Canvas.ALL_SAVE_FLAG);
        Bitmap de = makeCircle();
        canvas.drawBitmap(de,0,0,mPaint);
        mPaint.setXfermode(duffXfermode);
        if (size!=resultSize)
            bitmap = zooImag(bitmap,resultSize,resultSize);
        canvas.drawBitmap(bitmap,paddingLeft,paddingTop,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    private int getMin(int a, int b){
        if (a>b)
            return b;
        return a;
    }
    /*
     *缩放图片
     */
    private Bitmap zooImag(Bitmap bm,int newWidth,int newHight){
        int width = bm.getWidth();
        int height = bm.getHeight();
        int size = getMin(width,height);
        float scaleWidth = ((float)newWidth)/size;
        float scaleHeight = ((float)newHight)/size;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bm,0,0,width,height,matrix,true);
        return newBitmap;
    }
}

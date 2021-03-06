package tech.liujin.drawable.progress.text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * @author Liujin 2019/5/13:15:37:53
 */
public class PieInRingProgressDrawable extends TextCenterProgressDrawable {

      private RectF mRectF;
      private float mRadius;
      private float mRingWidth;
      private float mSweepAngles;

      public PieInRingProgressDrawable ( ) {

            mRectF = new RectF();

            mPaint.setStyle( Style.FILL );
            mPaint.setColor( Color.RED );
            mTextPaint.setColor( Color.WHITE );
      }

      @Override
      protected void onBoundsChange ( Rect bounds ) {


            int width = bounds.width();
            int height = bounds.height();
            int size = Math.min( width, height );

            mRingWidth = size * 1f / 16;
            float cx = width * 1f / 2;
            float cy = height * 1f / 2;
            mRadius = size * 1f / 2 - 2 - mRingWidth / 2;

            float radius = mRadius - mRingWidth;
            mRectF.set(
                cx - radius,
                cy - radius,
                cx + radius,
                cy + radius
            );

            super.onBoundsChange( bounds );

      }

      @Override
      public void draw ( @NonNull Canvas canvas ) {

            Rect bounds = getBounds();
            int width = bounds.width();
            int height = bounds.height();

            mPaint.setStyle( Style.STROKE );
            mPaint.setStrokeWidth( mRingWidth );
            canvas.drawCircle( width >> 1, height >> 1, mRadius, mPaint );

            mPaint.setStyle( Style.FILL );
            canvas.drawArc( mRectF, 90, mSweepAngles, true, mPaint );

            super.draw( canvas );
      }

      @Override
      public void onProcessChange ( float progress ) {

            mProgress = progress;

            mSweepAngles = 360 * progress;

            invalidateSelf();
      }

      public void setColor ( @ColorInt int color ) {

            mPaint.setColor( color );
      }
}

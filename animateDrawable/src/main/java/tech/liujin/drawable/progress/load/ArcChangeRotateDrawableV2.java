package tech.liujin.drawable.progress.load;

import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;
import tech.liujin.drawable.progress.ProgressDrawable;

/**
 * @author Liujin 2018-11-23:12:20
 */
public class ArcChangeRotateDrawableV2 extends ProgressDrawable {

      private RectF            mRectF;
      private TimeInterpolator mInterpolator = new AccelerateDecelerateInterpolator();

      private float mStart;
      private float mSweep;
      private float mLastProgress;
      private int   mFlag = 1;

      public ArcChangeRotateDrawableV2 ( ) {

            mPaint.setColor( Color.RED );
            mPaint.setStyle( Style.STROKE );
            mRectF = new RectF();
      }

      @Override
      protected void onBoundsChange ( Rect bounds ) {

            int size = Math.min( bounds.width(), bounds.height() );
            int width = size >> 4;
            mPaint.setStrokeWidth( width );
            size = size - width * 2;
            int offset = size / 2;
            mRectF.set( -offset, -offset, offset, offset );

            super.onBoundsChange( bounds );
      }

      @Override
      public void draw ( @NonNull Canvas canvas ) {

            int dx = getWidth() / 2;
            int dy = getHeight() / 2;
            canvas.translate( dx, dy );

            canvas.drawArc( mRectF, mStart, -mSweep, false, mPaint );
      }

      @Override
      public void onProcessChange ( float progress ) {

            mProgress = progress;

            mStart += 7;
            if( mStart > 360 ) {
                  mStart -= 360;
            }

            mSweep += ( 5 * mFlag );

            if( mSweep > 320 ) {
                  mSweep = 320;
                  mFlag = -1;
            } else if( mSweep < 12 ) {
                  mSweep = 12;
                  mFlag = 1;
            }

            invalidateSelf();
      }
}

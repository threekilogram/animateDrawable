package com.example.wuxio.animatedrawable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.drawable.anim.BiliBiliLoadingDrawable;
import com.example.drawable.anim.CircleLoadingDrawable;
import com.example.drawable.anim.CircleRectAnimDrawable;

/**
 * @author wuxio
 */
public class MainActivity extends AppCompatActivity {

      protected ImageView mAnimate;
      protected ImageView mImageView;
      private   TextView  mCountDownText;

      @Override
      protected void onCreate (Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            super.setContentView(R.layout.activity_main);
            initView();
      }

      private void initView () {

            /* bili bili */

            final BiliBiliLoadingDrawable biliLoadingDrawable = new BiliBiliLoadingDrawable(300);
            biliLoadingDrawable.setStrokeWidth(10);
            biliLoadingDrawable.setDuration(3500);
            biliLoadingDrawable.setRepeat(3000);
            biliLoadingDrawable.setColor(getResources().getColor(R.color.colorAccent));
            mAnimate = findViewById(R.id.animate);
            mAnimate.setImageDrawable(biliLoadingDrawable);

            mAnimate.setOnClickListener(new OnClickListener() {

                  @Override
                  public void onClick (View v) {

                        if(biliLoadingDrawable.isRunning()) {
                              biliLoadingDrawable.stop();
                        } else {
                              biliLoadingDrawable.start();
                        }
                  }
            });

            /* circle */

            mImageView = (ImageView) findViewById(R.id.imageView);

            final CircleLoadingDrawable circleLoadingDrawable = new CircleLoadingDrawable(300);
            circleLoadingDrawable.setStrokeColor(getResources().getColor(R.color.colorAccent));
            circleLoadingDrawable.setStrokeWidth(12);

            mImageView.setImageDrawable(circleLoadingDrawable);
            mImageView.setOnClickListener(new OnClickListener() {

                  @Override
                  public void onClick (View v) {

                        if(circleLoadingDrawable.isRunning()) {

                              circleLoadingDrawable.stop();
                        } else {

                              circleLoadingDrawable.start(1500);
                        }
                  }
            });

            mCountDownText = (TextView) findViewById(R.id.countDownText);
            CircleRectAnimDrawable rectAnimDrawable = new CircleRectAnimDrawable();
            rectAnimDrawable.setColor(Color.BLUE);
            rectAnimDrawable.setStrokeWidth(16);
            rectAnimDrawable.setDuration(1500);
            mCountDownText.setBackgroundDrawable(rectAnimDrawable);

            mCountDownText.setOnClickListener(new OnClickListener() {

                  private int[] modes = {
                      CircleRectAnimDrawable.CLOCK_WISE_ADD,
                      CircleRectAnimDrawable.CLOCK_WISE_SUB,
                      CircleRectAnimDrawable.COUNTER_CLOCK_WISE_ADD,
                      CircleRectAnimDrawable.COUNTER_CLOCK_WISE_SUB
                  };

                  private int time = 0;

                  @Override
                  public void onClick (View v) {

                        if(rectAnimDrawable.isRunning()) {
                              return;
                        }

                        int index = time % modes.length;
                        rectAnimDrawable.setMode(modes[index]);
                        rectAnimDrawable.start();

                        time++;
                  }
            });
      }

      public void toWechatBottom (View view) {

            WechatBottomActivity.start(this);
      }
}

package com.threekilogram.drawable.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.threekilogram.drawable.AlphaProgressDrawable;
import com.threekilogram.drawable.R;

/**
 * @author Liujin 2018-11-03:20:05
 */
public class TabItemBuilder {

      protected TabLayout mTabLayout;
      protected ViewPager mViewPager;

      protected ProgressColorTextView[] mTextViews;
      protected int                     mImageViewId;
      protected AlphaProgressDrawable[] mDrawables;

      protected boolean isTabSelect;

      public TabItemBuilder ( TabLayout tabLayout, ViewPager viewPager ) {

            this(
                tabLayout,
                viewPager,
                R.layout.tab_layout_progress_item,
                R.id.tabText,
                R.id.tabImage
            );
      }

      public TabItemBuilder (
          TabLayout tabLayout,
          ViewPager viewPager,
          @LayoutRes int itemLayout,
          @IdRes int textId,
          @IdRes int imageId ) {

            mTabLayout = tabLayout;
            mViewPager = viewPager;
            mImageViewId = imageId;

            mTabLayout.setupWithViewPager( mViewPager );

            Context context = tabLayout.getContext();
            LayoutInflater inflater = LayoutInflater.from( context );

            int tabCount = mTabLayout.getTabCount();

            mTextViews = new ProgressColorTextView[ tabCount ];
            mDrawables = new AlphaProgressDrawable[ tabCount ];

            for( int i = 0; i < tabCount; i++ ) {
                  Tab tab = mTabLayout.getTabAt( i );
                  View view = inflater.inflate( itemLayout, null );
                  tab.setCustomView( view );

                  mTextViews[ i ] = view.findViewById( textId );
            }
      }

      public TabItemBuilder setTitles ( String... titles ) {

            for( int i = 0; i < mTextViews.length; i++ ) {
                  mTextViews[ i ].setText( titles[ i ] );
            }
            return this;
      }

      public TabItemBuilder setTextColor ( @ColorInt int colorNormal, @ColorInt int colorSelect ) {

            for( ProgressColorTextView mTextView : mTextViews ) {
                  mTextView.setTextColor( colorNormal, colorSelect );
            }
            return this;
      }

      public TabItemBuilder setTextColorRes (
          @ColorRes int colorNormal, @ColorRes int colorSelect ) {

            Context context = mTabLayout.getContext();
            Resources resources = context.getResources();
            int normal = resources.getColor( colorNormal );
            int select = resources.getColor( colorSelect );

            for( ProgressColorTextView mTextView : mTextViews ) {
                  mTextView.setTextColor( normal, select );
            }
            return this;
      }

      public TabItemBuilder setDrawable (
          int position, @DrawableRes int normalDrawable, @DrawableRes int selectDrawable ) {

            Context context = mTabLayout.getContext();
            Resources resources = context.getResources();

            Bitmap normal = BitmapFactory.decodeResource( resources, normalDrawable );
            Bitmap select = BitmapFactory.decodeResource( resources, selectDrawable );
            AlphaProgressDrawable drawable = new AlphaProgressDrawable( normal, select );

            return setDrawable( position, drawable );
      }

      public TabItemBuilder setDrawable ( int position, AlphaProgressDrawable drawable ) {

            Tab tab = mTabLayout.getTabAt( position );
            View customView = tab.getCustomView();
            ImageView imageView = customView.findViewById( mImageViewId );
            imageView.setImageDrawable( drawable );
            mDrawables[ position ] = drawable;
            return this;
      }

      public void build ( ) {

            mTabLayout.addOnTabSelectedListener( new TabItemSelectListener() );
            mViewPager.addOnPageChangeListener( new PagerScrollListener() );

            mViewPager.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        int currentItem = mViewPager.getCurrentItem();
                        mTextViews[ currentItem ].setTextColorProgress( 1 );
                        mDrawables[ currentItem ].setProgress( 1 );
                  }
            } );
      }

      protected void setProgress ( int current, int next, float progress ) {

            float abs = Math.abs( progress );

            mTextViews[ current ].setTextColorProgress( 1 - abs );
            mDrawables[ current ].setProgress( 1 - abs );

            mTextViews[ next ].setTextColorProgress( abs );
            mDrawables[ next ].setProgress( abs );
      }

      protected class PagerScrollListener implements ViewPager.OnPageChangeListener {

            /**
             * 当前滚动状态
             */
            protected int mState = ViewPager.SCROLL_STATE_IDLE;
            /**
             * 按下时位置
             */
            protected int mDragPosition;
            /**
             * 选中时位置
             */
            protected int mSettPosition;

            @Override
            public void onPageScrolled (
                int position, float positionOffset, int positionOffsetPixels ) {

                  if( mState == ViewPager.SCROLL_STATE_DRAGGING ) {

                        if( position == mDragPosition ) {
                              onScrolled(
                                  mState, mDragPosition, -positionOffset, positionOffsetPixels );
                        }

                        if( position == mDragPosition - 1 ) {
                              onScrolled( mState, mDragPosition, 1 - positionOffset,
                                          positionOffsetPixels
                              );
                        }
                  }

                  if( mState == ViewPager.SCROLL_STATE_SETTLING ) {

                        if( positionOffset == 0 ) {

                              if( mDragPosition + 1 == mSettPosition ) {
                                    onScrolled( mState, mDragPosition, -1f, positionOffsetPixels );
                              }
                        }

                        if( position == mDragPosition ) {
                              onScrolled(
                                  mState, mDragPosition, -positionOffset, positionOffsetPixels );
                        }
                        if( position == mDragPosition - 1 ) {
                              onScrolled( mState, mDragPosition, 1 - positionOffset,
                                          positionOffsetPixels
                              );
                        }
                  }
            }

            protected void onScrolled ( int state, int current, float offset, int offsetPixels ) {

                  if( isTabSelect ) {
                        return;
                  }

                  int next = current;
                  if( offset < 0 ) {
                        next += 1;
                  }
                  if( offset > 0 ) {
                        next -= 1;
                  }

                  if( next != current ) {
                        setProgress( current, next, offset );
                  }
            }

            @Override
            public void onPageSelected ( int position ) { }

            @Override
            public void onPageScrollStateChanged ( int state ) {

                  if( state == ViewPager.SCROLL_STATE_DRAGGING ) {
                        mDragPosition = mViewPager.getCurrentItem();
                  }

                  if( state == ViewPager.SCROLL_STATE_SETTLING ) {
                        mSettPosition = mViewPager.getCurrentItem();
                  }

                  if( state == ViewPager.SCROLL_STATE_IDLE ) {
                        isTabSelect = false;
                  }

                  mState = state;
            }
      }

      protected class TabItemSelectListener implements OnTabSelectedListener {

            @Override
            public void onTabSelected ( Tab tab ) {

                  isTabSelect = true;

                  int position = tab.getPosition();
                  mDrawables[ position ].setProgress( 1 );
                  mTextViews[ position ].setTextColorProgress( 1 );
            }

            @Override
            public void onTabUnselected ( Tab tab ) {

                  int position = tab.getPosition();
                  mDrawables[ position ].setProgress( 0 );
                  mTextViews[ position ].setTextColorProgress( 0 );
            }

            @Override
            public void onTabReselected ( Tab tab ) {

            }
      }
}
package com.threekilogram.wuxio.animatedrawable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.threekilogram.drawable.widget.TabItemBuilder;

/**
 * @author wuxio
 */
public class WeChatBottomActivity extends AppCompatActivity {

      private static final String TAG = WeChatBottomActivity.class.getSimpleName();

      protected ViewPager mPager;
      private   TabLayout mTabLayout;

      private String[] mTitles = { "微信", "通信录", "发现", "我" };

      public static void start ( Context context ) {

            Intent starter = new Intent( context, WeChatBottomActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            super.setContentView( R.layout.activity_wechat_bottom );
            initView();
      }

      private void initView ( ) {

            mPager = findViewById( R.id.pager );
            PagerAdapter adapter = new PagerAdapter( getSupportFragmentManager() );
            mPager.setAdapter( adapter );
            mTabLayout = findViewById( R.id.tabLayout );

            TabItemBuilder builder = new TabItemBuilder( mTabLayout, mPager );
            builder.setTextColorRes( R.color.textColorNormal, R.color.textColorSelected );
            builder.setTitles( mTitles );
            builder.setDrawable( 0, R.drawable.home_normal, R.drawable.home_selected );
            builder.setDrawable( 1, R.drawable.category_normal, R.drawable.category_selected );
            builder.setDrawable( 2, R.drawable.find_normal, R.drawable.find_selected );
            builder.setDrawable( 3, R.drawable.mine_normal, R.drawable.mine_selected );
            builder.build();
      }

      private class PagerAdapter extends FragmentStatePagerAdapter {

            private TextFragment[] mFragments = {
                TextFragment.newInstance( mTitles[ 0 ] ),
                TextFragment.newInstance( mTitles[ 1 ] ),
                TextFragment.newInstance( mTitles[ 2 ] ),
                TextFragment.newInstance( mTitles[ 3 ] )
            };

            public String[] getTitles ( ) {

                  return mTitles;
            }

            public PagerAdapter ( FragmentManager fm ) {

                  super( fm );
            }

            @Override
            public Fragment getItem ( int position ) {

                  return mFragments[ position ];
            }

            @Override
            public int getCount ( ) {

                  return mFragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle ( int position ) {

                  return mTitles[ position ];
            }
      }
}

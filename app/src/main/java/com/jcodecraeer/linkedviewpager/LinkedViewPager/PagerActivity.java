package com.jcodecraeer.linkedviewpager.LinkedViewPager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import io.lahphim.mamoon.R;

/**
 * 成功的实现了TextView显示不同的日期
 */
public class PagerActivity extends FragmentActivity {
    private RelativeLayout[] relativeLayouts;
    private int mCurrentYearMonthPosition = Integer.MAX_VALUE / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        final MultiViewPager pager = (MultiViewPager) findViewById(R.id.pager);

        final MyAdapter adapter = new MyAdapter();
        pager.setAdapter(adapter);
        pager.setCurrentItem(mCurrentYearMonthPosition);

        relativeLayouts = new RelativeLayout[9];
        for(int i=0; i< relativeLayouts.length; i++){
            relativeLayouts[i] = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.date_page, null);
        }
    }

    public class MyAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        /**
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            // 对ViewPager页号求模取出View列表中要显示的项
            String yearMonth = getYearMonth(position);

            int newPos = position % relativeLayouts.length;
            if (newPos < 0) {
                newPos = relativeLayouts.length + newPos;
            }
            RelativeLayout view = relativeLayouts[newPos];
            TextView textView = (TextView)view.findViewById(R.id.date_text);
            textView.setText(yearMonth);

            // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent viewGroup = view.getParent();
            if (viewGroup != null) {
                ViewGroup parent = (ViewGroup) viewGroup;
                parent.removeView(view);
            }
            container.addView(view);
            // add listeners here if necessary
            return view;
        }
    }

    /**
     * 获取对应的年月值
     * @param position
     * @return
     */
    private String getYearMonth(int position) {
        int value = position - mCurrentYearMonthPosition;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, value);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        return String.valueOf(year) + "年" + String.valueOf(month) + "月";
    }
}
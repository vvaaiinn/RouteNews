package com.example.roadnews;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;
	private ViewPager mPageVp;// viewpager
	private TextView showTV, settingTV;// 查看 和设置
	private ImageButton addBtn;// 发送
	private ImageView mTabLineIv;// 下面的下划线
	private PicShow mPicShow;// 查看的 fg
	private AddShow mAddShow; // 发送的 fg
	private SettingShow mSettingShow;// 设置的 fg
	private int currentIndex;// 当前的位置
	private int screenWidth; // 屏幕宽度
	private LinearLayout l1, l2, l3; // 背景设置
	private Resources res = null;
	private Drawable bgc = null;
	private Drawable bgc2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findById();
		initMainActivity();
		initTabLineWidth();
	}

	private void findById() {
		settingTV = (TextView) findViewById(R.id.setting);
		showTV = (TextView) findViewById(R.id.show);
		addBtn = (ImageButton) findViewById(R.id.add);
		l1 = (LinearLayout) findViewById(R.id.ll1);
		l2 = (LinearLayout) findViewById(R.id.ll2);
		l3 = (LinearLayout) findViewById(R.id.ll3);
		l3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				resetTextView();
				l3.setBackground(bgc);
				mPageVp.setCurrentItem(2);
			}
		});
		l1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				resetTextView();
				l1.setBackground(bgc);
				mPageVp.setCurrentItem(0);
			}
		});
		l2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				resetTextView();
				l2.setBackground(bgc);
				mPageVp.setCurrentItem(1);
			}
		});

		mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);
		mPageVp = (ViewPager) this.findViewById(R.id.vp);
	}

	private void initMainActivity() {
		mAddShow = new AddShow();
		mSettingShow = new SettingShow();
		mPicShow = new PicShow(); // 三个fg
		mFragmentList.add(mPicShow);
		mFragmentList.add(mAddShow);
		mFragmentList.add(mSettingShow);// 添加到fgAdapter
		res = getResources();
		bgc = res.getDrawable(R.drawable.top_red);
		bgc2 = res.getDrawable(R.drawable.top_blue);// 2个背景
		mFragmentAdapter = new FragmentAdapter(
				this.getSupportFragmentManager(), mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setCurrentItem(0);

		mPageVp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int state) {
			}

			@Override
			public void onPageScrolled(int position, float offset,
					int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
						.getLayoutParams();
				Log.e("offset:", offset + "");
				if (currentIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 0) // 1->0
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				} else if (currentIndex == 2 && position == 1) // 2->1
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				}
				mTabLineIv.setLayoutParams(lp);
			}

			@Override
			public void onPageSelected(int position) {
				resetTextView();

				switch (position) {
				case 0:
					l1.setBackground(bgc);
					break;
				case 1:
					l2.setBackground(bgc);
					break;
				case 2:
					l3.setBackground(bgc);
					break;
				}
				currentIndex = position;
			}
		});
	}

	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		lp.width = screenWidth / 3;
		mTabLineIv.setLayoutParams(lp);
	}

	private void resetTextView() {
		l1.setBackground(bgc2);
		l2.setBackground(bgc2);
		l3.setBackground(bgc2);
	}

}
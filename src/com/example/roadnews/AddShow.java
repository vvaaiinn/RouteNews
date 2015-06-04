package com.example.roadnews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AddShow extends Fragment {
	private ImageView img1, img2, img3, img4;
	View addView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		addView = inflater.inflate(R.layout.addshow, container, false);
		img1 = (ImageView) addView.findViewById(R.id.img1);
		img2 = (ImageView) addView.findViewById(R.id.img2);
		img3 = (ImageView) addView.findViewById(R.id.img3);
		img4 = (ImageView) addView.findViewById(R.id.img4);
		img2.setEnabled(false);
		img3.setEnabled(false);
		img4.setEnabled(false);
		img1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 使用startActivityForResult启动SelectPicPopupWindow当返回到此Activity的时候就会调用onActivityResult函数
				startActivityForResult(new Intent(getActivity()
						.getApplicationContext(), SelectPicPopupWindow.class),
						1);
			}
		});
		img2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 使用startActivityForResult启动SelectPicPopupWindow当返回到此Activity的时候就会调用onActivityResult函数
				startActivityForResult(new Intent(getActivity()
						.getApplicationContext(), SelectPicPopupWindow.class),
						1);
			}
		});

		img3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 使用startActivityForResult启动SelectPicPopupWindow当返回到此Activity的时候就会调用onActivityResult函数
				startActivityForResult(new Intent(getActivity()
						.getApplicationContext(), SelectPicPopupWindow.class),
						1);
			}
		});

		img4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 使用startActivityForResult启动SelectPicPopupWindow当返回到此Activity的时候就会调用onActivityResult函数
				startActivityForResult(new Intent(getActivity()
						.getApplicationContext(), SelectPicPopupWindow.class),
						1);
			}
		});


		return addView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 1:
			if (data != null) {
				// 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
				Uri mImageCaptureUri = data.getData();
				// 返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
				if (mImageCaptureUri != null) {
					Bitmap image;
					try {
						// 这个方法是根据Uri获取Bitmap图片的静态方法
						image = MediaStore.Images.Media.getBitmap(getActivity()
								.getApplicationContext().getContentResolver(),
								mImageCaptureUri);
						if (image != null) {
							img1.setImageBitmap(image);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Bundle extras = data.getExtras();
					if (extras != null) {
						// 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
						Bitmap image = extras.getParcelable("data");
						if (image != null) {
							img1.setImageBitmap(image);
						}
					}
				}
			}
			break;
		default:
			break;

		}
	}

}

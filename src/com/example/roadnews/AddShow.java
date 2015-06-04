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
				// ʹ��startActivityForResult����SelectPicPopupWindow�����ص���Activity��ʱ��ͻ����onActivityResult����
				startActivityForResult(new Intent(getActivity()
						.getApplicationContext(), SelectPicPopupWindow.class),
						1);
			}
		});
		img2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ʹ��startActivityForResult����SelectPicPopupWindow�����ص���Activity��ʱ��ͻ����onActivityResult����
				startActivityForResult(new Intent(getActivity()
						.getApplicationContext(), SelectPicPopupWindow.class),
						1);
			}
		});

		img3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ʹ��startActivityForResult����SelectPicPopupWindow�����ص���Activity��ʱ��ͻ����onActivityResult����
				startActivityForResult(new Intent(getActivity()
						.getApplicationContext(), SelectPicPopupWindow.class),
						1);
			}
		});

		img4.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ʹ��startActivityForResult����SelectPicPopupWindow�����ص���Activity��ʱ��ͻ����onActivityResult����
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
				// ȡ�÷��ص�Uri,������ѡ����Ƭ��ʱ�򷵻ص�����Uri��ʽ���������������еû�����Uri�ǿյģ�����Ҫ�ر�ע��
				Uri mImageCaptureUri = data.getData();
				// ���ص�Uri��Ϊ��ʱ����ôͼƬ��Ϣ���ݶ�����Uri�л�á����Ϊ�գ���ô���Ǿͽ�������ķ�ʽ��ȡ
				if (mImageCaptureUri != null) {
					Bitmap image;
					try {
						// ��������Ǹ���Uri��ȡBitmapͼƬ�ľ�̬����
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
						// ��������Щ���պ��ͼƬ��ֱ�Ӵ�ŵ�Bundle�е��������ǿ��Դ��������ȡBitmapͼƬ
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

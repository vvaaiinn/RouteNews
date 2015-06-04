package com.example.roadnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import de.tavendo.autobahn.WebSocketConnection;

public class PicShow extends Fragment {
	private List<Map<String, Object>> list;
	// private String[] from = { "title", "source", "image", "time" };
	// private int[] to = { R.id.title, R.id.source, R.id.img1, R.id.time };
	// SimpleAdapter adapter;
	MyAdapter adapter = null;
	private ListView lv;
	private ImageButton img1, img2, img3, img4;
	WebSocketConnection wsc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View picView = inflater.inflate(R.layout.picshow, container, false);
		lv = (ListView) picView.findViewById(R.id.listview);
		wsc = new WebSocketConnection();
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", "今天");
			map.put("source", "新华社");
			map.put("image", R.drawable.cat);
			map.put("time", "2015年6月3日16:35:07");
			list.add(map);
		}
		adapter = new MyAdapter(getActivity().getApplicationContext(), list);
		// adapter = new SimpleAdapter(getActivity().getApplicationContext(),
		// list, R.layout.picitem, from, to);
		lv.setAdapter(adapter);
		return picView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public class MyAdapter extends BaseAdapter {
		private Context mContext;
		private List<Map<String, Object>> list;

		public MyAdapter(Context applicationContext,
				List<Map<String, Object>> list) {
			// TODO 自动生成的构造函数存根
			this.mContext = applicationContext;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return list.size();
		}

		@Override
		public Map<String, Object> getItem(int position) {
			// TODO 自动生成的方法存根
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO 自动生成的方法存根
			return position;
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(
						getActivity().getApplicationContext()).inflate(
						R.layout.picitem, null);
				
				holder.image1 = (ImageView) convertView.findViewById(R.id.img1);
				holder.image2 = (ImageView) convertView.findViewById(R.id.img2);
				holder.image3 = (ImageView) convertView.findViewById(R.id.img3);
				holder.image4 = (ImageView) convertView.findViewById(R.id.img4);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.source = (TextView) convertView
						.findViewById(R.id.source);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position % 2 == 0) {
				holder.image1.setVisibility(0);
				holder.image2.setVisibility(8);
				holder.image3.setVisibility(8);
				holder.image4.setVisibility(8);
				holder.image1.setImageResource(R.drawable.cat);
			} else {
				holder.image1.setVisibility(8);
				holder.image2.setVisibility(0);
				holder.image3.setVisibility(0);
				holder.image4.setVisibility(0);
				holder.image2.setImageResource(R.drawable.cat);
				holder.image3.setImageResource(R.drawable.cat);
				holder.image4.setImageResource(R.drawable.cat);
			}
			holder.title.setText((String) getItem(position).get("title"));
			holder.source.setText((String) getItem(position).get("source"));
			holder.time.setText((String) getItem(position).get("time"));

			return convertView;
		}

		final class ViewHolder {
			ImageView image1;
			ImageView image2;
			ImageView image3;
			ImageView image4;
			TextView title;
			TextView time;
			TextView source;
		}
	}

}

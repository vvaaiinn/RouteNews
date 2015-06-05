package com.example.roadnews;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class LoginActivity extends Activity {

	private EditText name, password;
	private Button loginBtn;
	private CheckBox remember;
	SharedPreferences sp;
	private NetUtil netUtil;
	private myHandler handler;
	private Boolean isconnected = false;
	private static final int REQUEST = 1;
	WebSocketConnection wsc;
	private JSONObject params, obj;
	private String res;
	private String pwdStr, nameStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		InitView();
		BindListener();
	}

	public void InitView() {
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);
		remember = (CheckBox) findViewById(R.id.remember);
		loginBtn = (Button) findViewById(R.id.login);
		sp = getSharedPreferences("user_info", 0);
		String nameStr = sp.getString("name", "");
		String pwdStr = sp.getString("pwd", "");
		Boolean flag = sp.getBoolean("remember", false);
		if (flag) {
			name.setText(nameStr);
			password.setText(pwdStr);
			remember.setChecked(true);
		}
		wsc = new WebSocketConnection();
		params = new JSONObject();
		obj = new JSONObject();
		handler = new myHandler();
		res = null;
		netUtil = new NetUtil(getApplicationContext());// 获取网络状态
		if (!netUtil.isNetworkConnected()) {
			Toast.makeText(getApplicationContext(), "网络异常，请检查网络",
					Toast.LENGTH_SHORT).show();
		} else {
			isconnected = true;
		}
		if (isconnected) {
			connect();
		}
	}

	private void BindListener() {
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				nameStr = name.getText().toString();
				pwdStr = password.getText().toString();
				Editor editor = sp.edit();
				editor.putString("name", nameStr);
				editor.putString("pwd", pwdStr);
				if (remember.isChecked())
					editor.putBoolean("remember", true);
				editor.putBoolean("remember", false);
				editor.commit();
				try {
					params.put("action", "GetValidKey");
					params.put("phone_num", nameStr);
					obj.put("event", "action");
					obj.put("params", params);
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				if (isconnected) {
					wsc.sendTextMessage(obj.toString());
				}

			}
		});
	}

	private void connect() {
		System.out.println("开始连接websocket///");
		try {
			wsc.connect("ws://www.betterman.ren:8080", new WebSocketHandler() {

				@Override
				public void onTextMessage(String payload) {
					System.out.println("onTextMessage" + payload);
					res = payload;
					handler.sendEmptyMessage(REQUEST);
				}

				@Override
				public void onOpen() {
					System.out.println("onOpen");
					showtext("连接成功");
				}

				@Override
				public void onClose(int code, String reason) {
					System.out.println("onClose reason=" + reason);
				}

				@Override
				public void onRawTextMessage(byte[] payload) {
					System.out.println("onRawTextMessage size="
							+ payload.length);
				}

				@Override
				public void onBinaryMessage(byte[] payload) {
					System.out
							.println("onBinaryMessage size=" + payload.length);
				}

			});
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class myHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQUEST:
				if (res != null)
					try {
						JSONObject json = new JSONObject(res);
						if (json.getInt("code") == 200) {
							Intent intent = new Intent(LoginActivity.this,
									MainActivity.class);
							startActivity(intent);
						}
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				break;

			default:
				break;
			}
		}
	}

	private void showtext(String msg) {
		Toast.makeText(this, msg, 0).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (wsc.isConnected())
			wsc.disconnect();
	}
}

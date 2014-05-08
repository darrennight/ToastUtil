package com.dk.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class DemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		ToastUtil.init(getApplication());
		ToastUtil.setBackGroundColor(0xffdddddd);
		
		findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
			private int count=0;
			
			@Override
			public void onClick(View arg0) {
				
				ToastUtil.showMessage("Hello,World!"+count);
				count++;
			}
		});
	}
}

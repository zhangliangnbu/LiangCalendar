package com.liang.liangcalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "onCreate");
		final SimpleHorizontalCalendar calendarView = (SimpleHorizontalCalendar) findViewById(R.id.horizontal_calendar);
		calendarView.initCalendarParas(43);
		calendarView.setOnItemClickListener(new SimpleHorizontalCalendar.OnItemClickListener<Long>() {
			@Override
			public void onClick(View view, Long aLong) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(aLong);
				Toast.makeText(getApplicationContext(), calendar.getTime().toLocaleString(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onStart() {
		Log.d(TAG, "onCreate");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
	}
}

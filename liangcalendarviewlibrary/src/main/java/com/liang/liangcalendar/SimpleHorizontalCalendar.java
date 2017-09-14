package com.liang.liangcalendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liang.liangcalendarviewlibrary.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * Created by zhangliang on 11/09/2017.
 */

public class SimpleHorizontalCalendar extends FrameLayout{
	private static final String TAG = "Calendar";
	private RecyclerView recyclerView;
	private SHCAdapter adapter;
	private Locale locale = Locale.CHINA;
	private TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");

	public SimpleHorizontalCalendar(@NonNull Context context) {
		super(context);
		init(context, null, 0);
	}

	public SimpleHorizontalCalendar(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public SimpleHorizontalCalendar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		LayoutInflater.from(context).inflate(R.layout.simple_horizontal_calendar, this, true);
		recyclerView = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
		RecyclerView.LayoutManager lm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
		recyclerView.setLayoutManager(lm);

		adapter = new SHCAdapter();
		recyclerView.setAdapter(adapter);
		Log.d(TAG, "init");
	}

	/**
	 * 必须设置
	 */
	public void initCalendarParas(int dateCount) {
		adapter.setDates(createDates(System.currentTimeMillis(), dateCount));
		adapter.setSelectedIndex(adapter.getItemCount() / 2);
		recyclerView.scrollToPosition(adapter.getSelectedIndex() - 2 >= 0 ? adapter.getSelectedIndex() - 2 : adapter.getSelectedIndex());
	}

	public void setOnItemClickListener(OnItemClickListener<Long> l) {
		adapter.setItemClickListener(l);
	}

	/**
	 * @param currentDate 当前日期
	 * @param dateCount 共dateCount个日期
	 * @return dates
	 */
	private List<Long> createDates(long currentDate, int dateCount) {
		List<Long> dates = new ArrayList<>();
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, -(dateCount / 2));

		for(int i = 0; i < dateCount; i ++) {
			dates.add(calendar.getTimeInMillis());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dates;
	}

	public class SHCAdapter extends RecyclerView.Adapter<SHCAdapter.SHCViewHolder>{
		private final Calendar calendar = Calendar.getInstance(timeZone);
		private List<Long> dates = new ArrayList<>();
		private OnItemClickListener<Long> itemClickListener;
		private int selectedIndex;

		public void setSelectedIndex(int selectedIndex) {
			this.selectedIndex = selectedIndex;
		}

		public int getSelectedIndex() {
			return selectedIndex;
		}

		public void setItemClickListener(OnItemClickListener<Long> itemClickListener) {
			this.itemClickListener = itemClickListener;
		}

		public List<Long> getDates() {
			return dates;
		}

		public void setDates(List<Long> dates) {
			this.dates = dates;
		}

		@Override
		public SHCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Log.d(TAG, "onCreateViewHolder");
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_calendar_item, parent, false);
			return new SHCViewHolder(view);
		}

		@Override
		public void onBindViewHolder(SHCViewHolder holder, final int position) {
			Log.d(TAG, "onBindViewHolder");
			final long date = dates.get(position);
			holder.setContent(date);
			if(itemClickListener != null) {
				holder.parent.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						selectedIndex = position;
						itemClickListener.onClick(v, date);
						adapter.notifyDataSetChanged();
					}
				});
			}
			if(position == selectedIndex) {
				holder.parent.setBackgroundColor(Color.BLUE);
			} else {
				holder.parent.setBackgroundColor(Color.WHITE);
			}
		}

		@Override
		public int getItemCount() {
			return dates.size();
		}

		class SHCViewHolder extends RecyclerView.ViewHolder {
			View parent;
			SHCViewHolder(View itemView) {
				super(itemView);
				this.parent = itemView;
			}
			void setContent(long date) {
				calendar.setTimeInMillis(date);
				((TextView)(parent.findViewById(R.id.tv_day))).setText(String.format(locale, "%02d", calendar.get(Calendar.DAY_OF_MONTH)));
				((TextView)(parent.findViewById(R.id.tv_week))).setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale));
				((TextView)(parent.findViewById(R.id.tv_month))).setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
				this.parent.setBackgroundColor(0xdddddd);
			}
		}
	}

	public interface OnItemClickListener<T> {
		void onClick(View view, T t);
	}
}

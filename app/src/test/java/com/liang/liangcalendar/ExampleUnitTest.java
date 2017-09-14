package com.liang.liangcalendar;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void testCalendar() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		List<Long> list = createDates(calendar.getTimeInMillis(), 6);
		for (long l : list) {
			calendar.setTimeInMillis(l);
			System.out.println(calendar.get(Calendar.DAY_OF_MONTH) +
					"/" + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CHINA));
		}
	}

	private List<Long> createDates(long currentDate, int dateCount) {
		List<Long> dates = new ArrayList<>();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTimeInMillis(currentDate);
		calendar.add(Calendar.DAY_OF_MONTH, -(dateCount / 2));

//		System.out.println(-(dateCount / 2));
//		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));


		for(int i = 0; i < dateCount; i ++) {
			dates.add(calendar.getTimeInMillis());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dates;
	}
}
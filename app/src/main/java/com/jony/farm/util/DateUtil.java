package com.jony.farm.util;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.jony.farm.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil{


	public static Date str2date(String str) throws ParseException {

		DateFormat fmt =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = fmt.parse(str);
		return date;
	}

	public static String beginDate(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String calStr = simpleDateFormat.format(calendar.getTime());
		//Date date1 = str2date(calStr);
		return calStr;
	}

	public static String dateOff(Date date, int num)throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, num);//日期偏移,正数向前,负数向后!
		String calStr = simpleDateFormat.format(calendar.getTime());
		//Date date1 = str2date(calStr);
		return calStr;
		/*String ss = simpleDateFormat.format(String.valueOf(calendar.getTime()));
		DateFormat fmt =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date1 = fmt.parse(ss);

		return date1;*/
	}

	public static String currentDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar();

		String calStr = simpleDateFormat.format(calendar.getTime());
		//Date date1 = str2date(calStr);
		return calStr;
	}

	/**
	 * 旋转
	 *
	 * @param type : 1.向上 2.向下
	 */
	public static void rote(Context context, int type, ImageView iv) {
		try {
			Animation animation = null;
			if (1 == type) {
				animation = AnimationUtils.loadAnimation(context, R.anim.rote_playtype_up);
			} else if (2 == type) {
				animation = AnimationUtils.loadAnimation(context, R.anim.rote_playtype_down);
			}
			LinearInterpolator lin = new LinearInterpolator();
			animation.setInterpolator(lin);
			animation.setFillAfter(true);
			if (iv != null) {
				iv.startAnimation(animation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
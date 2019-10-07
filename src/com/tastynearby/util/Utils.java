package com.tastynearby.util;

import android.text.TextUtils;

public class Utils {
	public final static boolean isValidEmail(CharSequence target) {
		  if (TextUtils.isEmpty(target)) {
		    return false;
		  } else {
		    return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		  }
		}
}
package com;

import inputHelp.Utf7ImeHelper;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import android.os.RemoteException;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.db.SqliteHelper;
import com.db.TxtHelper;

public class UIautomatorTest extends UiAutomatorTestCase {
	private String yanjiu = "广州软件测试研究会";

	public void testDemo() throws UiObjectNotFoundException, RemoteException,
			IOException {
		new CopyJi().fudu(yanjiu,20*1000+Math.round(Math.random()*60*1000));
	}
	
	void guaJi(){
		UiDevice device = getUiDevice();
		while(true){
		device.click(500, 900);
		}
	}

}

package com;

import inputHelp.Utf7ImeHelper;

import java.io.IOException;
import java.util.Random;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.db.TxtHelper;

public class ZhuanFa extends UiAutomatorTestCase {

	public void zhuanFa(String chat,long timeLoop) throws RemoteException,
			UiObjectNotFoundException, IOException {
		//UiDevice device = getUiDevice();
		// 唤醒屏幕
		//device.wakeUp();
		//assertTrue("screenOn: can't wakeup", device.isScreenOn());

		sleep(1000);

		while (true) {
			sleep(1000);
			UiObject uio4 = getDescUiO(1);
			if(!uio4.exists()){
				System.out.println("!!!!warn: uio4 not exits");
				continue;
			}
			zhuanFa(chat,uio4);
			sleep(timeLoop);
		}
	}

	// index=0复制第1个，其他第1个
	public static UiObject getDescUiO(int index)
			throws UiObjectNotFoundException {
		// 一层层获取
		UiObject uio1 = new UiObject(new UiSelector().className(
				"android.widget.FrameLayout").index(1));
		UiObject uio2 = uio1.getChild(new UiSelector().className(
				"android.widget.RelativeLayout").index(0));
		UiObject uio3 = uio2.getChild(new UiSelector().className(
				"android.view.View").index(1));
		UiObject uio4 = uio3.getChild(new UiSelector().className(
				"android.widget.RelativeLayout").index(index));

		return uio4;
	}

	public static void zhuanFa(String chat, UiObject uio)
			throws UiObjectNotFoundException {
		uio.longClick();
		new UiObject(new UiSelector().text("转发")).clickAndWaitForNewWindow();
		UiScrollable tItems = new UiScrollable(
				new UiSelector().scrollable(true));
		UiObject mItem = tItems.getChildByText(new UiSelector().text(chat),
				chat, true);
		mItem.clickAndWaitForNewWindow();
		new UiObject(new UiSelector().text("发送")).clickAndWaitForNewWindow();

	}

	public static void copyImg(String chat, UiObject uio)
			throws UiObjectNotFoundException {
		UiObject img = uio.getChild(
				new UiSelector().className("android.widget.RelativeLayout"))
				.getChild(
						new UiSelector().className("android.widget.ImageView"));
		if (!img.exists()) {
			img = uio.getChild(
					new UiSelector().className("android.widget.LinearLayout"))
					.getChild(
							new UiSelector()
									.className("android.widget.ImageView"));
		}
		if (img.exists()) {

			img.clickAndWaitForNewWindow(5000);
			// sleep(5000);
			new UiObject(
					new UiSelector().className("android.widget.ImageButton"))
					.clickAndWaitForNewWindow(1000);
			// sleep(1000);
			new UiObject(new UiSelector().text("发送给好友"))
					.clickAndWaitForNewWindow();
			UiScrollable tItems = new UiScrollable(
					new UiSelector().scrollable(true));
			UiObject mItem = tItems.getChildByText(new UiSelector().text(chat),
					chat, true);
			mItem.clickAndWaitForNewWindow();
			new UiObject(new UiSelector().text("发送"))
					.clickAndWaitForNewWindow();
		}
	}

}

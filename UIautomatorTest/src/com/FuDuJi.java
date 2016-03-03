package com;

import inputHelp.Utf7ImeHelper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.db.TxtHelper;

public class FuDuJi extends UiAutomatorTestCase {

	public void fudu(String chat, long timeLoop) throws RemoteException,
			UiObjectNotFoundException, IOException {
		// UiDevice device = getUiDevice();
		// 唤醒屏幕
		// device.wakeUp();
		// assertTrue("screenOn: can't wakeup", device.isScreenOn());

		String text = "", desct = "", desc = "", desc2 = "";
		sleep(1000);

		TxtHelper txtHelper = new TxtHelper();

		int count = 0;
		int oImgCount = 0;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        System.out.println(sdf.format(new Date()));  
		

		while (true) {
			sleep(10*1000);
			System.out.println(sdf.format(new Date())+" loop!!!!!!!!!!!!");
//			if(oImgCount>=10)
//				sleep(60000);
//			else
//				sleep(1000);
			UiObject uioabs = getDescUiO(0);
			if (!uioabs.exists()) {
				System.out.println("!!!!warn: uio4 not exits");
				oImgCount++;
				continue;
			}

			// 单图片
			if (uioabs.getChild(
					new UiSelector().className("android.widget.RelativeLayout"))
					.exists()) {
				// copyImg(chat, uio4);
				System.out.println("!!ONLY IMG!!");
				copyImg(chat, uioabs);
			}

			// 有图/有地点
			/*if (uioabs.getChild(
					new UiSelector().className("android.widget.LinearLayout"))
					.exists()) {
				for (int i = 0; i < uioabs.getChildCount(); i++) {
					UiObject uio5 = uioabs.getChild(new UiSelector().index(i));
					if ("android.widget.LinearLayout".equals(uio5.getClassName())) {
						if(uio5.getChild(new UiSelector().className("android.widget.TextView")).exists()
								&&uio5.getChild(new UiSelector().className("android.widget.ImageView")).exists()){
							System.out.println("!!IMG&text!!");
						}
					}
				}
			}*/
			// 无图无地点
			//else {
				System.out.println("%%%%%%%% uio4 getChildCount: "
						+ uioabs.getChildCount());
				count++;
				System.out.println("$$$ Times:  " + count);
				for (int i = 0; i < uioabs.getChildCount(); i++) {
					UiObject uio5 = uioabs.getChild(new UiSelector().index(i));
					if (uio5.exists() && uio5.isFocusable()) {

						text = uioabs.getChild(new UiSelector().index(i))
								.getText();
						text = text.replace("@", "at");
						if(txtHelper.isExist(text)){
							System.out.println("!!text exist!!");
							continue;
						}else{
							txtHelper.insertMsg(text);
						}
					
						//text = strRandom(text);
						sendText(text);
						System.out.println("~~~~"+text);
					}
				}
			//}

			sleep(timeLoop);

		}
	}

	// 字符串倒转
	public static String upDown(String str) {
		char[] arr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = arr.length; i > 0; i--) {
			sb.append(arr[i - 1]);
		}
		return sb.toString();
	}

	// 字符串无序
	public static String strRandom(String str) {
		char[] sequence = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < sequence.length; i++) {
			int p = random.nextInt(sequence.length);
			char tmp = sequence[i];
			sequence[i] = sequence[p];
			sequence[p] = tmp;
		}
		random = null;
		for (int t = 0; t < sequence.length; t++) {
			sb.append(sequence[t]);
		}
		return sb.toString();
	}

	// 处理desc，针对“说”、“我说”、“发送了图片”等字样
	public static String descHandle(String desc) {
		if (desc.contains(":说")) {
			desc = desc.substring(desc.indexOf(":说") + 2, desc.length());
		} else if (desc.contains("我说")) {
			desc = desc.substring(desc.indexOf("我说") + 2, desc.length());
		} else if (desc.contains("发送了图片")) {
			desc = "";
		}
		return desc;
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
				"android.widget.AbsListView").index(1));
		UiObject uio4 ;
		if(!uio3.getChild(new UiSelector().className(
				"android.widget.RelativeLayout").index(index)).exists()){
			uio4 = uio3.getChild(new UiSelector().className(
					"android.widget.RelativeLayout").index(1));
		}else{
		
			 uio4 = uio3.getChild(new UiSelector().className(
				"android.widget.RelativeLayout").index(0));
		}

		return uio4;
	}

	public static void sendText(String desct) throws UiObjectNotFoundException {
		UiObject textform = new UiObject(
				new UiSelector().className("android.widget.EditText"));
		textform.click();
		// System.out.println("###"+desct);
		textform.setText(Utf7ImeHelper.e(desct));
		new UiObject(new UiSelector().text("发送")).click();
		// sleep(50000);
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

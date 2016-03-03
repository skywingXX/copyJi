package com;

import inputHelp.Utf7ImeHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.db.TxtHelper;

public class Xiaohua extends UiAutomatorTestCase {


	public static void sendText(String desct) throws UiObjectNotFoundException{
		UiObject textform = new UiObject(
				new UiSelector().className("android.widget.EditText"));
		textform.click();
		//System.out.println("###"+desct);
		textform.setText(Utf7ImeHelper.e(desct));
		new UiObject(new UiSelector().text("发送")).click();
		//sleep(50000);
	}
	
	
	
	
	//读取聊天记录，如果没重复就放入txt文件中。
	
	
	public void qqTucao(String chat) throws RemoteException, UiObjectNotFoundException, IOException {
		UiDevice device = getUiDevice();
		// 唤醒屏幕
		device.wakeUp();
		assertTrue("screenOn: can't wakeup", device.isScreenOn());
		// 回到HOME
		device.pressHome();
		sleep(1000);
		device.click(500, 1600);
		sleep(1000);
		
		new UiObject(new UiSelector().text("QQ")).click();

		sleep(1000);
		UiScrollable tableItems = new UiScrollable(
				new UiSelector().scrollable(true));

		UiObject myItem = tableItems.getChildByText(
				new UiSelector().text(chat), chat, true);
		myItem.clickAndWaitForNewWindow();
		
		
		sleep(1000);
		
		TxtHelper txtHelper = new TxtHelper();
		
		int count=0;  //计数器每5秒记录一次对话， 每10次做一次服毒
		while(true){	
			String tucao=txtHelper.getHowToTucao();
			if(tucao.contains("No Answer")) continue;
			sendText(tucao);
			
			//sleep(((int)Math.random()*3+3)*60000);
			sleep(100000);
			System.out.println("sent:"+count+1+" times");
			count++;
		}
	}
	
	public void qqXiaohua(String chat) throws RemoteException, UiObjectNotFoundException, IOException {
		UiDevice device = getUiDevice();
		// 唤醒屏幕
		device.wakeUp();
		assertTrue("screenOn: can't wakeup", device.isScreenOn());
		// 回到HOME
		device.pressHome();
		sleep(1000);
		device.click(500, 1600);
		sleep(1000);
		
		new UiObject(new UiSelector().text("QQ")).click();

		sleep(1000);
		UiScrollable tableItems = new UiScrollable(
				new UiSelector().scrollable(true));

		UiObject myItem = tableItems.getChildByText(
				new UiSelector().text(chat), chat, true);
		myItem.clickAndWaitForNewWindow();
		
		
		sleep(1000);
		
		TxtHelper txtHelper = new TxtHelper();
		
		int count=0;  //计数器每5秒记录一次对话， 每10次做一次服毒
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(true){	
			String tucao=txtHelper.getXiaohua();
			if(tucao.contains("No Answer")) continue;
			sendText(tucao);
			System.out.println("time:"+ format.format(new java.util.Date()).toString()+"  sent:"+(count+1)+" times");
			//sleep(((int)Math.random()*3+3)*60000);
			
			
			sleep(2*60*1000);
			
			count++;
		}
	}
}

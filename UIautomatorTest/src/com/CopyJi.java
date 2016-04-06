package com;

import inputHelp.Utf7ImeHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.os.RemoteException;


import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.db.TxtHelper;

public class CopyJi extends UiAutomatorTestCase {
	static String serverIP = "192.168.0.150";
	
	
	//复读主方法， 主要是获取元素，再获取语句，进行转换后发送
	public void fudu(String chat, long timeLoop) throws RemoteException,
			UiObjectNotFoundException, IOException {
		// UiDevice device = getUiDevice();
		// 唤醒屏幕
		// device.wakeUp();
		// assertTrue("screenOn: can't wakeup", device.isScreenOn());

		String text = "", desct = "", desc = "", desc2 = "" ,textF = "";
		
		sleep(1000);

		TxtHelper txtHelper = new TxtHelper();

		int count = 0;
		int oImgCount = 0;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        System.out.println(sdf.format(new Date()));  
		

		while (true) {
			sleep(3*1000);
			//System.out.println(sdf.format(new Date())+" loop!!!!!!!!!!!!");

			//取text元素，如果没有就continue
			UiObject uioabs = getDescUiO(2);
			if (null==uioabs||!uioabs.exists()) {
				//System.out.println("!!!!warn: uio4 not exits");
				//oImgCount++;
				continue;
			}


			//打印text元素有多少子元素
				//System.out.println("%%% uio4 getChildCount: "+ uioabs.getChildCount());
				
				//循环取元素，判断哪个是别人说的text
				for (int i = 0; i < uioabs.getChildCount(); i++) {
					UiObject uio5 = uioabs.getChild(new UiSelector().index(i));
					if (uio5.exists() && uio5.isFocusable()) {

						text = uioabs.getChild(new UiSelector().index(i))
								.getText();
						System.out.println("原话："+text);
						text = text.replace("@", "at");
						//如果已经复读过，就不复读了
						//如果没复读就加入库里面
						
						if(txtHelper.isExist(text)){
							System.out.println(sdf.format(new Date())+ "!!text exist!!");
							continue;
						}else{
							txtHelper.insertMsg(text);
							
							//使用中文语言服务器的summary方法进行转换
							textF = summary(text);
							txtHelper.insertMsg(textF);
						}
					
						//text = strRandom(text);
						sendText(textF);
						System.out.println(sdf.format(new Date())+"转换后的文本是："+textF);
						count++;
						System.out.println("$$$ Times:  " + count);
						continue;
					}else{
						//System.out.println("***no text没有text");
					}
				}

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
		if(uio1.exists()){
			UiObject uio2 = uio1.getChild(new UiSelector().className(
					"android.widget.RelativeLayout").index(0));
			if(uio2.exists()){
				UiObject uio3 = uio2.getChild(new UiSelector().className(
						"android.widget.AbsListView").index(1));
				if(uio3.exists()){
					UiObject uio4 ;
					if(uio3.getChild(new UiSelector().className(
							"android.widget.RelativeLayout").index(index)).exists()){
						uio4 = uio3.getChild(new UiSelector().className(
								"android.widget.RelativeLayout").index(index));
					}else if(uio3.getChild(new UiSelector().className(
							"android.widget.RelativeLayout").index(index+1)).exists()){
					
						 uio4 = uio3.getChild(new UiSelector().className(
							"android.widget.RelativeLayout").index(index+1));
					}else return null;
					return uio4;
				}else return null;
			}else return null;
		}else return null;
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
	
public static String summary(String text) throws UnknownHostException, IOException{
		
		//向服务器端发送请求，服务器IP地址和服务器监听的端口号
		Socket client = new Socket(serverIP, 4242);
		
                  //通过printWriter 来向服务器发送消息
		PrintWriter printWriter = new PrintWriter(client.getOutputStream());
		//System.out.println("连接已建立...");
                  
                  //发送消息
		printWriter.println(text);
		
		printWriter.flush();
		
		//InputStreamReader是低层和高层串流之间的桥梁
		//client.getInputStream()从Socket取得输入串流
		InputStreamReader streamReader = new InputStreamReader(client.getInputStream());
		
		//链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder
		BufferedReader reader = new BufferedReader(streamReader);
		String advice =reader.readLine();
		
		
		//System.out.println("接收到服务器的消息 ："+advice);
		reader.close();
		
		if(advice.startsWith("["))
			advice = advice.substring(1);
		if(advice.endsWith("]"))
			advice = advice.substring(0, advice.length()-1);
		
		return advice;
	}
	
}

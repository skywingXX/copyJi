package com;


import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

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

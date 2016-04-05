package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test2 {

	public void go(String text) throws UnknownHostException, IOException{
		
		//向服务器端发送请求，服务器IP地址和服务器监听的端口号
		Socket client = new Socket("192.168.0.150", 4242);
		
                  //通过printWriter 来向服务器发送消息
		PrintWriter printWriter = new PrintWriter(client.getOutputStream());
		System.out.println("连接已建立...");
                  
                  //发送消息
		printWriter.println(text);
		
		printWriter.flush();
		
		//InputStreamReader是低层和高层串流之间的桥梁
		//client.getInputStream()从Socket取得输入串流
		InputStreamReader streamReader = new InputStreamReader(client.getInputStream());
		
		//链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder
		BufferedReader reader = new BufferedReader(streamReader);
		String advice =reader.readLine();
		
		
		System.out.println("接收到服务器的消息 ："+advice);
		reader.close();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Test2 c = new Test2();
		c.go("算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" +
		        "算法可以宽泛的分为三类，\n" +
		        "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n" +
		        "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n" +
		        "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。"
);
	}
}


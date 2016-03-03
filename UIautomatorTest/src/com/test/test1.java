package com.test;

import java.io.IOException;
import java.util.List;

import com.db.TxtHelper;

public class test1 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TxtHelper th = new TxtHelper();
		String desc = th.getXiaohua();
		System.out.println("@"+desc);
		
	}

}

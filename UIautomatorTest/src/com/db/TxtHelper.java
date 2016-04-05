package com.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;



public class TxtHelper {

	static String filePath = "//sdcard//msgHistory.txt";
	static String zhihuPath = "//sdcard//howtoTucao.txt";
	static String xiaohuaPath = "//sdcard//xiaohua.txt";
	//static String zhihuPath = "F://howtoTucao.txt";
	//static String filePath = "F://msgHistory.txt";
	static String huifuPath = "F://shenhuifu.txt";
	static int start = 1 ,megNum = 1000000;
	
	public TxtHelper(){
		
	}
	
	public String getSendMsg(String desc) throws IOException {
		String s = "";
		desc = desc.replace("@", "at");
		if(isExist(desc)){
			s = getRandomMsg();
			System.out.println("getRandomMsgRRRRRR");
			return s;
		}
		insertMsg(desc);
		System.out.println("returnDescDDDDDDDDDD");
		return desc;
	}
	
	// 判断复制的话 是否存在
	public boolean isExist(String desc) throws IOException{
		boolean b = false;
		List all = getMeg(0, 0, false);
		if(null==all || all.size()==0)
		{
			System.out.println("all is null!!!");
		}else 
		{
			/*for(int i=0;i<all.size();i++){
				System.out.println(all.get(i).toString());
			}*/
			
			b = all.contains(desc);
		}
		return b;
	}
	
	//复制的话存在， 则获取随机的话 发送
	public String getRandomMsg() throws IOException{
		String desc = "";
		int ranNum = megNum;
		List randomList = getMeg(start, megNum, true);
		if(randomList.size()<megNum) ranNum = randomList.size();
		int select = (int) (Math.random()*ranNum);
		desc = randomList.get(select).toString();	
		return desc;
	}
	
	//获取对话记录 ，random为1则随机获取，start和msgNum决定获取的区间         
	//getMeg(0, 0, false)表示获取全部对话记录， getMeg(100, 50, true)表示获取100到150行的记录
	@SuppressWarnings("null")
	public List<String> getMeg(int start,int msgNum,boolean random) throws IOException{
		List list = new ArrayList() ;
		File file = new File(filePath);
		String encoding="utf-8";
		if (!file.exists()) {
		    file.createNewFile();
		   }
		
		if(random){
		 InputStreamReader read = new InputStreamReader(
                 new FileInputStream(file),encoding);//考虑到编码格式
                 BufferedReader bufferedReader = new BufferedReader(read);
                 String lineTxt = "";
                 int count = 0;
                 while((lineTxt = bufferedReader.readLine()) != null){
                     count++;
                     if(count > start && count<(start+msgNum)){
                    	 list.add(lineTxt);
                     }
                 }
                 read.close();
		}else{
			InputStreamReader read = new InputStreamReader(
	                 new FileInputStream(file),encoding);//考虑到编码格式
	                 BufferedReader bufferedReader = new BufferedReader(read);
	                 String lineTxt = "";
	                 while((lineTxt = bufferedReader.readLine()) != null){
	                    	 list.add(lineTxt);
	                 }
	                 read.close();
		}
		return list;
	}
	
	// 插入对话记录
	public void insertMsg(String desc){
		BufferedWriter out = null; 
		try {

			   File file = new File(filePath);

			   // if file doesnt exists, then create it
			   if (!file.exists()) {
			    file.createNewFile();
			   }
			   out = new BufferedWriter(new OutputStreamWriter(                        
	                    new FileOutputStream(file, true)));                              
	             out.write(desc+"\n"); 
			 	/*RandomAccessFile randomFile = new RandomAccessFile(filePath, "rw");     
	            // 文件长度，字节数                                                      
	            long fileLength = randomFile.length();                                   
	            // 将写文件指针移到文件尾。                                              
	             randomFile.seek(fileLength);                                            
	             randomFile.writeBytes(desc);                                         
	             randomFile.close(); */
			
			

			   System.out.println("Done insert");
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		 	finally {                                                                 
	            try {                                                                    
	                 out.close();                                                        
	             } catch (IOException e) {                                               
	                 e.printStackTrace();                                                
	             }                                                                       
	         }        
		}
	
	
	public String getHowToTucao() throws IOException{
		List list = new ArrayList() ;
		File file = new File(zhihuPath);
		String oneTucao ="";
		String encoding="utf-8";
		if (!file.exists()) {
		    file.createNewFile();
		   }
		InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "";
                String tucao = "";
                
                while((lineTxt = bufferedReader.readLine()) != null){
                	
                	if(!lineTxt.equals("##@##")){
                		//System.out.println(lineTxt);
                		tucao = tucao + lineTxt;
                	}else{
                		//System.out.println(tucao);
                		list.add(tucao);
                		tucao = "";
                	}
                   	 
                }
                read.close();
                //System.out.println("@@@@@@listsize"+list.size());
                int select = (int) (Math.random()*list.size());
                oneTucao = list.get(select).toString();
         return oneTucao;
	}
	
	public String getXiaohua() throws IOException{
		List list = new ArrayList() ;
		File file = new File(xiaohuaPath);
		String xiaohua ="";
		String encoding="GBK";
		if (!file.exists()) {
		    file.createNewFile();
		   }
		InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "";
                String xiaohua_t = "";
                int i=1;
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(lineTxt.length()<1) continue;
                	//System.out.println(i+" line ~:"+lineTxt);
                	xiaohua_t = lineTxt;
                	list.add(xiaohua_t);
                	xiaohua_t = "";
                	i++;
                }
                read.close();
                //System.out.println("@@@@@@listsize"+list.size());
                int select = (int) (Math.random()*list.size());
                xiaohua = list.get(select).toString();
         return xiaohua;
	}
	
}

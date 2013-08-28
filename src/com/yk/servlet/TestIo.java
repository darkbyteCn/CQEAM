package com.yk.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class TestIo {

	public static void main(String[] args) throws IOException {
		// FileInputStream fs=new
		// FileInputStream("D://For Java//Design Pattern//files.txt");
		// FileOutputStream fos=new
		// FileOutputStream("D://For Java//Design Pattern//files1.txt");
		// int c=-1;
		// while((c=fs.read())!=-1){
		// char ch=(char) c;
		// fos.write(ch);
		// //System.out.println(ch);
		// }
		// fs.close();
		// fos.close();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		System.out.println(cal.getTime());
	}

}

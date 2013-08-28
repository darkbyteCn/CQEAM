package com.yk.servlet;

public class TestSingle {
	
	private static  TestSingle t=null;
	
	private TestSingle(){
		super();
	}
	
	public  TestSingle getInstance(){
		if (t==null){
			t=new TestSingle();
		}
		return t;
	}

}

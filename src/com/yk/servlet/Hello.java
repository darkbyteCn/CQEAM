package com.yk.servlet;

public class Hello {
	public static void main(String[] ars) {
		A ab = new B();
		ab = new B();
		// 执行到此处,结果: 1a2bab = new B();
		// 执行到此处,结果: 1a2bab
	}
}

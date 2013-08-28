package com.yk.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestCollection {
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(3);
		list.add(33);
		list.add(13);
		list.add(23);
		list.add(93);
//		Collections.sort(list,new Comparator<List<String>>() {
//
//			public int compare(List o1, List o2) {
//				// TODO Auto-generated method stub
//				return o1.get(0)-o2.get(0);
//			}
//			 
//		});
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

}

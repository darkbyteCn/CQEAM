package com.yk.servlet;

import java.util.Collections;

public class TestArray {

	public static void main(String[] args) {
		// int d[][]=new int[][]{{1,1,1,1},{2,2,2,2},{3,3,3,3}};
		// System.out.println(retResult(d));
		// 1+16+81=98

		// 0 1 3 6
		// 2 4 7
		// 5 8
		// 9
		//		String d[][] = { { "0", "1", "3", "6" }, { "", "2", "4", "7" },
		//				{ "", "", "5", "8" }, { "", "", "", "9" } };
		//		sysOut(d);
		
//		int a[]={1,5,7,2,8,3,0,9,4};
//		sortPx(a);
		int b[]=new int[]{1,2,3,4};
		System.out.println(b.length);
		
		int c[][]=new int[3][];
		
//		String s="acbadsacada";
//		System.out.println(s.replace("a", "A"));
		
//		  String a[]=new String[5]; for(int i=0;i<5;a[i++]=""){
//			  System.out.println("1:"+a[i]);
//		  } 
//		  System.out.println();
//		  String b[]={"","","","",""}; 
//		  for (int i = 0; i < b.length; i++) {
//			System.out.println("2:"+b[i]);
//		  }
//		  String a[5]; 
//		  String[5]a; 
//		  String []c=new String[5]; for( int i=0;i<5;c[i++]=null){
//			  System.out.println("3:"+c[i]);
//		  } 
		  
//		   for(int i=0; i<3; i++)
//			for(int j=0;j<3;j++){
//				   if(j>1) break;
//				    System.out.println(j+"and"+i);
//		    }  
		
		
//		  int a[][] = new int[][]; 
//
//		  int b[10][10] = new int[][]; 

//		  int c[][] = new int[10][10]; 

		  int [][]d= new int[10][10]; 

		  int []e[] = new int[10][10];  
//		  System.out.println(e.length);
		  
		  
	}

	// public void sortArray(){
	// Collections.sort(a);
	//		
	// }

	public static void sortPx(int a[]) {
		for (int i = 0; i < a.length; i++) {
			int k = 0;
			for (int j = 0; j < a.length - 1; j++) {
				if (a[i] < a[j]) {
					k = a[i];
					a[i] = a[j];
					a[j] = k;
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

	public static int retResult(int d[][]) {
		int result = 0;
		for (int i = 0; i < d.length; i++) {
			int rowRult = 0;
			for (int j = 0; j < d[i].length; j++) {
				int k = d[i][j];
				if (j == 0) {
					rowRult = k;
				} else {
					rowRult *= k;
				}
			}
			result += rowRult;
		}
		return result;
	}

	public static void sysOut(String d[][]) {
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				String k = d[i][j];
				if (k.equals("")) {
					System.out.print(d[i][j] + "  ");
				} else {
					System.out.print(d[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
}

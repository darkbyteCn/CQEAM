package com.sino.ams.appbase.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 * 
 * @系统名称: 条码打印
 * @功能描述: 
 * @修改历史: 起始版本1.0
 * @公司名称: 北京思诺搏信息技术有限公司
 * @当前版本：1.0
 * @开发作者: sj
 * @创建时间: Sep 14, 2011
 */
public class BarCodeService {
	private int barcodeDpi = 150;
	private double barHeight = 8;
	private double wideFactor = 3;

	public static final String PIC_PATH = "/uploadFiles/pic/";
	 
	
	private String fileRealPath = "";
	private String picFullName = ""; 		 // 生成图片的完全绝对路径
	private String abstractPicFullName = ""; // 图片相对工程路径
	private HttpServletRequest req = null;
	private OutputStream outputStream = null;
	private Code39Bean code39Bean = null;
	
	public BarCodeService( HttpServletRequest req ){
		this.req = req;
		fileRealPath = req.getSession().getServletContext().getRealPath( "/");
		init();
	}
	
	public BarCodeService( String fileRealPath ){
		this.fileRealPath = fileRealPath ;
		init();
	}
	
	/**
	 * 初始化
	 */
	public void init(){
		checkPicPath();
//		initPath();
		initCode39Bean();
	}
	
	
	public BitmapCanvasProvider getCanvas() throws FileNotFoundException{
		File outputFile = new File(picFullName);
		outputStream = new FileOutputStream(outputFile); 
		BitmapCanvasProvider canvas = new BitmapCanvasProvider( outputStream ,
				"image/jpeg", barcodeDpi , BufferedImage.TYPE_BYTE_BINARY, false, 0);
		return canvas;
	}
	
	/**
	 * 检查目录是否存在，不存在则创建
	 */
	public void checkPicPath(){
		// 获取web应用的绝对路径 
		int len = fileRealPath.length();

		if (fileRealPath.substring(len - 1, len).equals("/")
				|| fileRealPath.substring(len - 1, len).equals("\\")) {
			fileRealPath = fileRealPath.substring(0, fileRealPath.length() - 1);
		} 
		File tmpDir = new File(fileRealPath + PIC_PATH);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
	}
	/**
	 * 路径初始化
	 * @param ordernum
	 */
	public void initPath(){
		// 生成图片的完全绝对路径
		String uuid = UUID.randomUUID().toString();
		picFullName = fileRealPath + PIC_PATH + uuid + ".jpg";
		abstractPicFullName = PIC_PATH + uuid + ".jpg";
		picFullName = picFullName.replace("\\", "/");
		abstractPicFullName = abstractPicFullName.replace("\\", "/"); 
	}
	
	public void initCode39Bean(){
		code39Bean = new Code39Bean(); 
		final int dpi = barcodeDpi; 
		// Configure the barcode generator
		code39Bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); // makes the
		// narrow bar
		// width exactly one pixel 
		code39Bean.setBarHeight(barHeight);
		code39Bean.setWideFactor(wideFactor);
		code39Bean.doQuietZone(false);  
	}
	public String createScancodePic(String ordernum ) throws IOException { 
		this.initPath();
		try { 
			BitmapCanvasProvider canvas = getCanvas();
			// BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
			// "image/jpeg", dpi, BufferedImage.TYPE_4BYTE_ABGR, false, 0); 
			// Generate the barcode
			code39Bean.generateBarcode(canvas, ordernum + ""); 
			// Signal end of generation
			canvas.finish();
		} catch (NullPointerException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if( null != outputStream ){
					outputStream.close();
				} 
			} catch (IOException e) {
				throw e;
			}
		}
		// return picFullName;
		return abstractPicFullName;
	}

	public int getBarcodeDpi() {
		return barcodeDpi;
	}

	public void setBarcodeDpi(int barcodeDpi) {
		this.barcodeDpi = barcodeDpi;
	}

	public double getBarHeight() {
		return barHeight;
	}

	public void setBarHeight(double barHeight) {
		this.barHeight = barHeight;
	}

	public double getWideFactor() {
		return wideFactor;
	}

	public void setWideFactor(double wideFactor) {
		this.wideFactor = wideFactor;
	}
 
 
}

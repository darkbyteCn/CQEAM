package com.sino.hn.todo.xfire.util;

import java.lang.reflect.InvocationTargetException;

//import com.sino.hn.todo.xfire.beans.Close;
//import com.sino.hn.todo.xfire.beans.Open;
import com.mochasoft.todo.beans.Close;
import com.mochasoft.todo.beans.Open;
import com.sino.sinoflow.todo.dto.OaTodoDTO;

public class ConvertUtil {
	public static Open getOpenFromDTO(OaTodoDTO oaTodoDTO ) throws IllegalAccessException, InvocationTargetException{
		Open open = new Open();
//		BeanUtils.copyProperties( open , oaTodoDTO );
		open.setDoc_id( oaTodoDTO.getDocId() ); 
		open.setWork_id( oaTodoDTO.getWorkId() ); 
		open.setUser_id( oaTodoDTO.getUserId().toLowerCase() ); 
		open.setTitle( oaTodoDTO.getTitle() ); 
		open.setStart_time( oaTodoDTO.getStartTime() ); 
		open.setUrl( oaTodoDTO.getTodoUrl() );
		open.setType( oaTodoDTO.getTodoType() );
		open.setPri( oaTodoDTO.getPri() ); 
		open.setDoc_type( oaTodoDTO.getDocType() ); 
		open.setSender( oaTodoDTO.getSender() ); 
		open.setSource_id( oaTodoDTO.getSourceId() ); 
		open.setSys_id( oaTodoDTO.getSysId() ); 
		return open;
	}
	
	public static Close getCloseFromDTO(OaTodoDTO oaTodoDTO ) throws IllegalAccessException, InvocationTargetException{
		Close close = new Close();
//		BeanUtils.copyProperties( close , oaTodoDTO );
		
		close.setDoc_id( oaTodoDTO.getDocId() ); 
		close.setWork_id( oaTodoDTO.getWorkId() ); 
		close.setUser_id( oaTodoDTO.getUserId().toLowerCase() ); 
		close.setSource_id( oaTodoDTO.getSourceId() );  
		close.setSys_id( oaTodoDTO.getSysId() ); 
		close.setClose_time( oaTodoDTO.getCloseTime() ); 
		
		return close;
	}
}

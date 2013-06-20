package com.sino.hn.todo.xfire.service;

import java.util.ResourceBundle;

public class ConfigReader {
	public static String TODO_URL;
	public static String TODO_USERNAME;
	public static String TODO_PASSWORD;

	static {
		ResourceBundle rb = ResourceBundle.getBundle("config.HnOa");
		TODO_URL = rb.getString("todo_url");
		TODO_USERNAME = rb.getString("todo_username");
		TODO_PASSWORD = rb.getString("todo_password");
	}

}

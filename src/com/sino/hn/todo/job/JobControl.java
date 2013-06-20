package com.sino.hn.todo.job;

public class JobControl {
	public static boolean todoStart = true;
	public static boolean todoDeleteStart = true;

	public static synchronized void setTodoStart(boolean todoStart) {
		JobControl.todoStart = todoStart;
	}

	public static synchronized void setTodoDeleteStart(boolean todoDeleteStart) {
		JobControl.todoDeleteStart = todoDeleteStart;
	}

	public static boolean isTodoStart() {
		return todoStart;
	}

	public static boolean isTodoDeleteStart() {
		return todoDeleteStart;
	}
}

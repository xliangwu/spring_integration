package com.learn.spring.integration.file.monitor;

public interface Listener<T> {

	public boolean update(T file, Object arg);
}

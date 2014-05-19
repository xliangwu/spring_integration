package com.learn.spring.integration.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassA {
	public static final Logger LOGGER = LoggerFactory.getLogger(ClassA.class);

	public void output() {
		LOGGER.info("Output A");
		ClassB.output();
	}
}

package com.learn.spring.integration.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassB {
	public static final Logger LOGGER = LoggerFactory.getLogger(ClassB.class);

	public static void output() {
		LOGGER.info("Output B");
	}
}

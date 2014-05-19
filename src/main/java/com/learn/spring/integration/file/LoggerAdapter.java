package com.learn.spring.integration.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAdapter {

	// public static final Logger LOGGER =
	// LoggerFactory.getLogger(LoggerAdapter.class);

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Logger loggera = LoggerFactory.getLogger("a");
		ClassA ca = new ClassA();
		ca.output();

		Logger loggerb = LoggerFactory.getLogger("b");
		LogManager.resetConfiguration();
		Properties property = new Properties();
		property.load(new FileInputStream(new File("D:\\java\\workplace\\learn-spirng_integration\\src\\main\\resources\\log4j.properties")));

		int i = 0;
		Map<Object, Object> aa = new HashMap<Object, Object>();
		for (Entry<Object, Object> entry : property.entrySet()) {
			if (entry.getValue().toString().contains("${1}")) {
				aa.put(entry.getKey(), entry.getValue());
			}
		}

		for (Object key : aa.keySet()) {
			String value = aa.get(key).toString();
			value = value.replace("${1}", "a" + String.valueOf(i++));
			aa.put(key, value);
		}

		property.putAll(aa);

		for (Entry<Object, Object> entry : property.entrySet()) {
			System.out.println(entry.getKey() + "--->" + entry.getValue());

			if (entry.getValue().toString().contains("${1}")) {
				aa.put(entry.getKey(), entry.getValue());
			}
		}
		PropertyConfigurator.configure(property);

		ca.output();
	}

}

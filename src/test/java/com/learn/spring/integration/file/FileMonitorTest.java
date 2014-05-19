package com.learn.spring.integration.file;

import java.util.Timer;

import org.junit.Test;

import com.learn.spring.integration.file.monitor.StableFileNotifier;

public class FileMonitorTest {

	@Test
	public void test() {
		Timer timer = new Timer(true);
		StableFileNotifier filerNotifier = new StableFileNotifier("C:\\data");
		timer.schedule(filerNotifier, 10, 5000);
		while (true) {
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

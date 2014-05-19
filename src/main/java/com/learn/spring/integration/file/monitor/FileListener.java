package com.learn.spring.integration.file.monitor;

import java.io.File;

public interface FileListener {
	public void fileChanged(File file);
}

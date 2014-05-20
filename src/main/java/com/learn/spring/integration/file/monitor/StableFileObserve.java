package com.learn.spring.integration.file.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class StableFileObserve extends TimerTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(StableFileObserve.class);
	private volatile File directory;
	private Map<String, Long> modifiedTimeCache = new ConcurrentHashMap<String, Long>();

	private List<Listener<File>> observers;

	public StableFileObserve(String file) {
		this.directory = new File(file);
	}

	public void addObserver(Listener<File> e) {
		if (observers == null)
			observers = new ArrayList<Listener<File>>();

		observers.add(e);
	}

	@Override
	public void run() {
		File[] listFiles = directory.listFiles();
		for (File file : listFiles) {
			if (file.exists() && file.isDirectory())
				continue;

			String key = file.getAbsolutePath();
			if (!modifiedTimeCache.containsKey(key)) {
				modifiedTimeCache.put(key, file.lastModified());
				continue;
			}

			long newModifiedTime = file.lastModified();
			long oldModifiedTime = modifiedTimeCache.get(key);
			if (newModifiedTime != oldModifiedTime) {
				modifiedTimeCache.put(key, file.lastModified());
			} else {
				// notify listener
				if (!CollectionUtils.isEmpty(observers)) {
					for (Listener<File> listener : observers) {
						listener.update(file, null);
					}

				}
			}
		}
		LOGGER.debug("Size #" + listFiles.length);
	}
}

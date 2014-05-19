package com.learn.spring.integration.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockTest2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(LockTest2.class);
	private static ConcurrentMap<File, FileChannel> channelCache = new ConcurrentHashMap<File, FileChannel>();

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					FileLock fileLock = null;
					File file = new File("C:\\data\\lock.txt");
					try {
						while (fileLock == null) {
							fileLock = LockTest2.tryLockFor(file);
							LOGGER.info(fileLock != null ? fileLock.toString() : "NULL");
							Thread.sleep(10*5000);
						}
						LOGGER.info("Release LOCK");
						fileLock.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}).start();
		}

	}

	public static FileLock tryLockFor(File fileToLock) throws IOException {
		FileChannel channel = channelCache.get(fileToLock);
		if (channel == null) {
			FileChannel newChannel = new RandomAccessFile(fileToLock, "rw").getChannel();
			FileChannel original = channelCache.putIfAbsent(fileToLock, newChannel);
			channel = (original != null) ? original : newChannel;
		}
		FileLock lock = null;
		if (channel != null) {
			try {
				lock = channel.tryLock();
			} catch (OverlappingFileLockException e) {
				// File is already locked in this thread or virtual machine
			}
		}
		return lock;
	}

}

package com.lplanque.maetel.util;

import java.io.File;
import java.io.FilenameFilter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleFilenameFilter implements FilenameFilter {
	
	public static final String ALL_FILE = ".*";
	
	private final String pattern;
	
	public SimpleFilenameFilter(final String pattern) {
		this.pattern = pattern == null ? ALL_FILE: pattern;
	}
	
	@Override
	public boolean accept(final File dir, final String name) {
		final Path full = Paths.get(dir.getPath(), name);
		final File file = full.toFile();
		return file.canRead() && file.canWrite() && pattern.matches(name);
	}
}

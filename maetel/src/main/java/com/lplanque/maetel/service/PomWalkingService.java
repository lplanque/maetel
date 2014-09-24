package com.lplanque.maetel.service;

import static java.util.Collections.singletonMap;

import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lplanque.maetel.core.Function;
import com.lplanque.maetel.core.MaetelVisitor;
import com.lplanque.maetel.util.SimpleFilenameFilter;

@Component
public abstract class PomWalkingService {
	
	@Value("${path:.}") 
	protected String path;
	
	@Value("${filter:pom.xml}")
	protected String filter;
	
	@Value("${xpath:/project/version}")
	protected String xpath;
	
	protected abstract Function<String, String> function();
	
	public void walk() throws IOException {
		final Path p = Paths.get(path);
		final FilenameFilter f = new SimpleFilenameFilter(filter);
		final FileVisitor<Path> v = new MaetelVisitor(singletonMap(xpath, function()), f);
		Files.walkFileTree(p, v);
	}
}

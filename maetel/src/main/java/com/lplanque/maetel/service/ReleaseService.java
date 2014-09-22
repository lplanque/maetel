/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lplanque.maetel.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lplanque.maetel.core.Function;
import com.lplanque.maetel.core.MaetelVisitor;
import com.lplanque.maetel.util.UpdateVersion;

@Component
public class ReleaseService {

	public static final String MAJOR_PROFILE = "major";
	public static final String MINOR_PROFILE = "minor";
	public static final String FIX_PROFILE   = "fix"  ;
	
	@Value("${profile:fix}")         private String profile;
	@Value("${fs:.}")                private String fs;
	@Value("${groupid:com.intuiko}") private String groupId;

	public boolean release() {
		
		final String format = "/project/parent[starts-with(groupId,\"%s\")]/version";
		final Path path = Paths.get(fs);
		final Map<String, Function<String, String>> functions = new HashMap<>();
		final String xpath = String.format(format, groupId);
		final FilenameFilter filter = new FilenameFilter() {	
			@Override public boolean accept(File dir, String name) {
				return name.equals("pom.xml");
			}
		};
		final FileVisitor<Path> visitor = new MaetelVisitor(functions, filter);
		
		boolean ok = true;
		
		switch(profile) {
		case MAJOR_PROFILE:
			functions.put(xpath, new UpdateVersion(0)); break;
		case MINOR_PROFILE:
			functions.put(xpath, new UpdateVersion(1)); break;
		case FIX_PROFILE:
			functions.put(xpath, new UpdateVersion(2)); break;
		}
		
		try {
			Files.walkFileTree(path, visitor);
		} catch (IOException e) {
			ok = false;
		}
		return ok;
	}
}

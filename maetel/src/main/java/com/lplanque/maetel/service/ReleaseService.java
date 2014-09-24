package com.lplanque.maetel.service;

import org.springframework.stereotype.Component;

import com.lplanque.maetel.core.Function;
import com.lplanque.maetel.util.RemoveSuffix;

@Component
public final class ReleaseService extends PomWalkingService {

	@Override
	public Function<String, String> function() {
		return new RemoveSuffix("-SNAPSHOT");
	}
}

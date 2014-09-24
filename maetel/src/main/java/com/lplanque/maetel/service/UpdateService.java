package com.lplanque.maetel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lplanque.maetel.core.Function;
import com.lplanque.maetel.util.UpdateVersion;

@Component
public final class UpdateService extends PomWalkingService {

	public static final String MAJOR_PROFILE = "major";
	public static final String MINOR_PROFILE = "minor";
	
	@Value("${profile:_}")
	private String profile;
	
	@Override
	protected Function<String, String> function() {
		switch(profile.toLowerCase()) {
		case MAJOR_PROFILE: return new UpdateVersion(0);
		case MINOR_PROFILE: return new UpdateVersion(1);
		default:            return new UpdateVersion(2);
		}
	}
}

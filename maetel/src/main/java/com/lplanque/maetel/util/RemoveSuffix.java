package com.lplanque.maetel.util;

import com.lplanque.maetel.core.Function;


/**
 * This function delete the given {@link #suffix} if it exists.
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 * @since 1.0
 */
public class RemoveSuffix implements Function<String, String> {
	
	private final String suffix;
	
	/**
	 * Builds the function given a suffix to remove when calling {@link #apply(String)} method.
	 * If <code>suffix</code> is <code>null</code>, then it will be replace by the empty string.
	 * @param suffix The suffix to remove.
	 */
	public RemoveSuffix(final String suffix) {
		this.suffix = suffix == null? "": suffix;
	}
	
	/**
	 * Delete {@link #suffix} from <code>param</code> if it exists.
	 * @param param A string value
	 * @return The string parameter, minus the <code>suffix</code>.
	 */
	@Override
	public String apply(String param) {
		if(param != null && param.endsWith(suffix)) {
			final int offset = suffix.length();
			param = param.substring(0, param.length() - offset);
		}
		return param;
	}
}

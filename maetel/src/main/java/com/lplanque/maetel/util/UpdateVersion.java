package com.lplanque.maetel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lplanque.maetel.core.Function;

/**
 * Updates a version number matching {@link #VERSION_PATTERN} by running {@link #apply(String)} method.
 * 
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 * @since 1.0
 */
public class UpdateVersion implements Function<String, String> {
	
	private static final String NUMBER_PATTERN = "[0-9]+";
	private static final String SUFFIX_PATTERN = "[^0-9\\.]*$";
	
	/**
	 * Pattern for a version number : <code>([0-9]+\\.)*[0-9]+[^0-9\\.]*$</code>. (<i>E.g.</i>
	 * <code>1</code>, <code>1.0</code>, <code>1.0-SNAPSHOT</code>, <code>1.0.42</code>, ...)
	 */
	public static final String VERSION_PATTERN = "(" 
			+ NUMBER_PATTERN 
			+ "\\.)*" 
			+ NUMBER_PATTERN
			+ SUFFIX_PATTERN;
	
	private final int index;
	
	/**
	 * Builds an instance with the position to increment
	 * @param index The position to increment.
	 */
	public UpdateVersion(int index) {
		this.index = index;
	}
	
	/**
	 * Default constructor, equivalent to : </code>this(0)</code>.
	 */
	public UpdateVersion() {
		this(0);
	}

	/**
	 * This method increments a version number matching {@link #VERSION_PATTERN} as shown with the following example.
	 * <br/>Let <code>v = 3.0.1-SNAPSHOT</code> be a standard version number for a snapshot Maven project.
	 * <br/>Let <i>f(i)</i><code> = new UpdateVersion(</code><i>i</i><code>)</code> with <i>i</i> a positive integer. Then :
	 * <ul>
	 *    <li><i>f(0)</i><code>.apply(v)</code> returns the string <code>"4.0.0-SNAPSHOT"</code>,</li>
	 *    <li><i>f(1)</i><code>.apply(v)</code> returns the string <code>"3.1.0-SNAPSHOT"</code>,</li>
	 *    <li><i>f(2)</i><code>.apply(v)</code> returns the string <code>"3.0.2-SNAPSHOT"</code>,</li>
	 *    <li><i>f(n)</i><code>.apply(v)</code> returns the string <code>v</code> itself for all <i>n</i> greater or equal than 3.</li>
	 * </ul><br/>
	 */
	@Override
	public String apply(String param) {
		return param != null && param.matches(VERSION_PATTERN)
			? build(findNumbers(param), suffix(param))
			: param;
	}

	private String suffix(String param) {
		final Pattern pattern = Pattern.compile(SUFFIX_PATTERN);
		final Matcher matcher = pattern.matcher(param);
		matcher.find();
		return matcher.group();
	}

	private List<Integer> findNumbers(String param) {
		
		final Pattern pattern = Pattern.compile(NUMBER_PATTERN);
		final Matcher matcher = pattern.matcher(param);
		final List<Integer> numbers = new ArrayList<Integer>();
		
		for(int i = 0; matcher.find(); i++) {
			int n = 0;
			if(i < index) {
				n = Integer.parseInt(matcher.group());
			} else if(i == index) {
				n = Integer.parseInt(matcher.group()) + 1;
			}
			numbers.add(n);
		}
		return numbers;
	}

	private String build(List<Integer> numbers, String prefix) {
		StringBuilder sb = new StringBuilder();
		for(Integer n: numbers) {
			sb.append(n).append(".");
		}
		if(sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(prefix);
		return sb.toString();
	}

	/**
	 * Get the index.
	 * @return The index.
	 */
	public int getIndex() {
		return index;
	}
}

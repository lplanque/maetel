package com.lplanque.maetel.core;

/**
 * SAM interface for a generic function.
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 * @param <X> The input parameter.
 * @param <Y> The output parameter.
 * @since 1.0
 */
public interface Function<X, Y> {
	/**
	 * A function that applies a parameter of type <code>X</code> to
	 * a value of type <code>Y</code>.
	 * @param param The input parameter.
	 * @return The function applied to <code>param</code>.
	 */
	Y apply(X param);
}

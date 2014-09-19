package com.lplanque.maetel.core;

/**
 * Implements the <i>identity</i> function.
 * @author <a href="https://github.com/lplanque" target="_blank">Laurent Planque</a>
 * @param <E> The type of the input/output set 
 */
public class Identity<E> implements Function<E, E> {

	/**
	 * Implements the <i>identity</i> function. In fact, it returns <code>param</code> itself.
	 * @see Function#apply(Object)
	 */
	@Override public final E apply(final E param) {
		return param;
	}
}

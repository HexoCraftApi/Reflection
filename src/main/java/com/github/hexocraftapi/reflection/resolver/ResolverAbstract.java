package com.github.hexocraftapi.reflection.resolver;

/*
 * Copyright 2016 hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract resolver class
 *
 * @param <T> resolved type
 * @see ClassResolver
 * @see ConstructorResolver
 * @see FieldResolver
 * @see MethodResolver
 */
public abstract class ResolverAbstract<T> {

	protected final Map<ResolverQuery, T> resolvedObjects = new ConcurrentHashMap<ResolverQuery, T>();

	/**
	 * Same as {@link #resolve(ResolverQuery...)} but throws no exceptions
	 *
	 * @param queries Array of possible queries
	 * @return the resolved object if it was found, <code>null</code> otherwise
	 */
	protected T resolveSilent(ResolverQuery... queries) {
		try {
			return resolve(queries);
		} catch (Exception ignored) {
		}
		return null;
	}

	/**
	 * Attempts to resolve an array of possible queries to an object
	 *
	 * @param queries Array of possible queries
	 * @return the resolved object (if it was found)
	 * @throws ReflectiveOperationException if none of the possibilities could be resolved
	 * @throws IllegalArgumentException     if the given possibilities are empty
	 */
	protected T resolve(ResolverQuery... queries) throws ReflectiveOperationException {
		if (queries == null || queries.length <= 0) { throw new IllegalArgumentException("Given possibilities are empty"); }
		for (ResolverQuery query : queries) {
			//Object is already resolved, return it directly
			if (resolvedObjects.containsKey(query)) { return resolvedObjects.get(query); }

			//Object is not yet resolved, try to find it
			try {
				T resolved = resolveObject(query);
				//Store if it was found
				resolvedObjects.put(query, resolved);
				return resolved;
			} catch (ReflectiveOperationException e) {
				//Not found, ignore the exception
			}
		}

		//Couldn't find any of the possibilities
		throw notFoundException(Arrays.asList(queries).toString());
	}

	protected abstract T resolveObject(ResolverQuery query) throws ReflectiveOperationException;

	protected ReflectiveOperationException notFoundException(String joinedNames) {
		return new ReflectiveOperationException("Objects could not be resolved: " + joinedNames);
	}

}

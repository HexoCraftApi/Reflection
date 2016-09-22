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

import com.github.hexocraftapi.reflection.resolver.wrapper.WrapperAbstract;

import java.lang.reflect.Member;

/**
 * abstract class to resolve members
 *
 * @param <T> member type
 * @see ConstructorResolver
 * @see FieldResolver
 * @see MethodResolver
 */
public abstract class MemberResolver<T extends Member> extends ResolverAbstract<T> {

	protected Class<?> clazz;

	public MemberResolver(Class<?> clazz) {
		if (clazz == null) { throw new IllegalArgumentException("class cannot be null"); }
		this.clazz = clazz;
	}

	public MemberResolver(String className) throws ClassNotFoundException {
		this(new ClassResolver().resolve(className));
	}

	/**
	 * Resolve a member by its index
	 *
	 * @param index index
	 * @return the member
	 * @throws IndexOutOfBoundsException    if the specified index is out of the available member bounds
	 * @throws ReflectiveOperationException if the object could not be set accessible
	 */
	public abstract T resolveIndex(int index) throws IndexOutOfBoundsException, ReflectiveOperationException;

	/**
	 * Resolve member by its index (without exceptions)
	 *
	 * @param index index
	 * @return the member or <code>null</code>
	 */
	public abstract T resolveIndexSilent(int index);

	/**
	 * Resolce member wrapper by its index
	 *
	 * @param index index
	 * @return the wrapped member
	 */
	public abstract WrapperAbstract resolveIndexWrapper(int index);

}

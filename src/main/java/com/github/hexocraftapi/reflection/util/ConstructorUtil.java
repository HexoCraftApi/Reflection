package com.github.hexocraftapi.reflection.util;

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

import com.github.hexocraftapi.reflection.resolver.ConstructorResolver;

import java.lang.reflect.Constructor;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
@SuppressWarnings("unused")
public class ConstructorUtil
{
	private ConstructorUtil() {}

	/**
	 * @param clazz Class containing the constructor
	 * @param parameterTypes the list of parameters
	 *
	 * @return the {@code Constructor<?>} that matches the specified
	 * {@code clazz}  and {@code parameterTypes}
	 *
	 * @throws NoSuchMethodException If not exist in {@code clazz}
	 */
	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException
	{
		return new ConstructorResolver(clazz).resolve(parameterTypes.clone());

		/*Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
		if(constructor!=null) {
			return AccessUtil.setAccessible(constructor);
		}

		throw new NoSuchMethodException(clazz.getName());*/
	}

	/**
	 * @param clazz Class containing the constructor
	 * @param parameterTypes the list of parameters
	 *
	 * @return the {@code Constructor<?>} that matches the specified
	 * {@code clazz}  and {@code parameterTypes}
	 */
	public static Constructor<?> getConstructorSilent(Class<?> clazz, Class<?>... parameterTypes)
	{
		return new ConstructorResolver(clazz).resolveSilent(parameterTypes.clone());

		/*try
		{
			Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
			if(constructor!=null) {
				return AccessUtil.setAccessible(constructor);
			}
		}
		catch(ReflectiveOperationException e) {}

		return null;*/
	}

}

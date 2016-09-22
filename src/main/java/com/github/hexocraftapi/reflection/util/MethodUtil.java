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

import com.github.hexocraftapi.reflection.resolver.MethodResolver;
import com.github.hexocraftapi.reflection.resolver.ResolverQuery;

import java.lang.reflect.Method;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
@SuppressWarnings("unused")
public class MethodUtil
{
	private MethodUtil() {}

	/**
	 * @param clazz Class that should contain the method
	 * @param name the name of the method
	 * @param parameterTypes the list of parameters
	 *
	 * @return the {@code Method} object that matches the specified
	 * {@code clazz} and {@code name} and {@code parameterTypes}
	 *
	 * @throws NoSuchMethodException If not exist in {@code clazz}
	 */
	public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException
	{
		return new MethodResolver(clazz).resolve(new ResolverQuery(name, parameterTypes));

		/*Method declaredMethod = clazz.getDeclaredMethod(name, parameterTypes);
		if(declaredMethod!=null) {
			declaredMethod.setAccessible(true);
			return declaredMethod;
		}

		throw new NoSuchMethodException(clazz.getName() + "." + name);*/
	}

	/**
	 * @param clazz Class that should contain the method
	 * @param name the name of the method
	 * @param parameterTypes the list of parameters
	 *
	 * @return the {@code Method} object that matches the specified
	 * {@code clazz} and {@code name} and {@code parameterTypes}
	 */
	public static Method getMethodSilent(Class<?> clazz, String name, Class<?>... parameterTypes)
	{
		try
		{
			return new MethodResolver(clazz).resolve(new ResolverQuery(name, parameterTypes));
		}
		catch(NoSuchMethodException e) {}

		return null;
	}

}

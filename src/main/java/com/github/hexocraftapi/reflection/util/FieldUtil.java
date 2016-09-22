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

import com.github.hexocraftapi.reflection.resolver.FieldResolver;

import java.lang.reflect.Field;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
@SuppressWarnings("unused")
public class FieldUtil
{
	private FieldUtil() {}

	/**
	 * @param clazz Class that should contain the method
	 * @param name the name of the method
	 *
	 * @return the {@code Field} that matches the specified
	 * {@code clazz} and {@code name}
	 *
	 * @throws NoSuchFieldException If not exist in {@code clazz}
	 */
	public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException
	{
		return new FieldResolver(clazz).resolve(name);
	}

	/**
	 * @param clazz Class that should contain the method
	 * @param name the name of the method
	 * @param from The object to get the filed from
	 *
	 * @return the {@code Object} object that matches the specified
	 * {@code clazz} and {@code name} and {@code from}
	 *
	 * @throws NoSuchFieldException If not exist in {@code clazz}
	 * @throws IllegalAccessException if this {@code Field} object is enforcing Java language access control and the underlying field is inaccessible.
	 */
	public static Object getField(Class<?> clazz, String name, Object from) throws NoSuchFieldException, IllegalAccessException
	{
		return getField(clazz, name).get(from);
	}

	/**
	 * @param <T>  Class to cast
	 * @param name the name of the method
	 * @param from The object to get the filed from
	 *
	 * @return the {@code Object} object that matches the specified
	 * {@code clazz} and {@code name} and {@code from}
	 *
	 * @throws NoSuchFieldException If not exist in {@code clazz}
	 * @throws IllegalAccessException if this {@code Field} object is enforcing Java language access control and the underlying field is inaccessible.
	 */
	public static <T> T getField(String name, Object from) throws NoSuchFieldException, IllegalAccessException
	{
		return ((T) getField(from.getClass(), name).get(from));
	}

	/**
	 * @param clazz Class that should contain the method
	 * @param name the name of the method
	 *
	 * @return the {@code Field} that matches the specified
	 * {@code clazz} and {@code name}
	 */
	public static Field getFieldSilent(Class<?> clazz, String name)
	{
		return new FieldResolver(clazz).resolveSilent(name);
	}

	/**
	 * @param clazz Class that should contain the method
	 * @param name the name of the method
	 * @param from The object to get the filed from
	 *
	 * @return the {@code Object} that matches the specified
	 * {@code clazz} and {@code name} and {@code from}
	 */
	public static Object getFieldSilent(Class<?> clazz, String name, Object from)
	{
		try
		{
			return getFieldSilent(clazz, name).get(from);
		}
		catch(IllegalAccessException e) {}

		return null;
	}

	/**
	 * @param <T>  Class to cast
	 * @param name the name of the method
	 * @param from The object to get the filed from
	 *
	 * @return the {@code <T>} that matches the specified
	 * {@code clazz} and {@code name} and {@code from}
	 */
	public static <T> T getFieldSilent(String name, Object from)
	{
		try
		{
			return ((T) new FieldResolver(from.getClass()).resolveSilent(name).get(from));
		}
		catch(IllegalAccessException e) {}

		return null;
	}

}

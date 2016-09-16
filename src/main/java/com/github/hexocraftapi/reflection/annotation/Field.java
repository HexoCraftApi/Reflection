package com.github.hexocraftapi.reflection.annotation;

/*
 * Copyright 2015 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexocraftapi.reflection.resolver.wrapper.FieldWrapper;
import com.github.hexocraftapi.reflection.minecraft.Minecraft;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Resolves the annotated {@link FieldWrapper} or {@link java.lang.reflect.Field} field to the first matching field name.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

	/**
	 * Name of the class to load this field from
	 *
	 * @return name of the class
	 */
	String className();

	/**
	 * Possible names of the field. Use <code>&gt;</code> or <code>&lt;</code> as a name prefix in combination with {@link #versions()} to specify versions newer- or older-than.
	 *
	 * @return names of the field
	 */
	String[] value();

	/**
	 * Specific versions for the names.
	 *
	 * @return Array of versions for the class names
	 */
	Minecraft.Version[] versions() default {};

	/**
	 * Whether to ignore any reflection exceptions thrown. Defaults to <code>true</code>
	 *
	 * @return whether to ignore exceptions
	 */
	boolean ignoreExceptions() default true;
}

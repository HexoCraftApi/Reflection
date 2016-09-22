package com.github.hexocraftapi.reflection.annotation;

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

import com.github.hexocraftapi.reflection.minecraft.Minecraft;
import com.github.hexocraftapi.reflection.resolver.wrapper.ClassWrapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Resolves the annotated {@link ClassWrapper} or {@link java.lang.Class} field to the first matching class name.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Class {

	/**
	 * Name of the class. Use <code>{nms}.MyClass</code> for NMS classes, or <code>{obc}.MyClass</code> for OBC classes. Use <code>&gt;</code> or <code>&lt;</code> as a name prefix in combination with {@link #versions()} to specify versions newer- or older-than.
	 *
	 * @return the class name
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

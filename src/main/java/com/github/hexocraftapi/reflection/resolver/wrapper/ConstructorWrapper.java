package com.github.hexocraftapi.reflection.resolver.wrapper;

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

import java.lang.reflect.Constructor;

public class ConstructorWrapper<R> extends WrapperAbstract {

	private final Constructor<R> constructor;

	public ConstructorWrapper(Constructor<R> constructor) {
		this.constructor = constructor;
	}

	@Override
	public boolean exists() {
		return this.constructor != null;
	}

	public R newInstance(Object... args) {
		try {
			return this.constructor.newInstance(args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public R newInstanceSilent(Object... args) {
		try {
			return this.constructor.newInstance(args);
		} catch (Exception e) {
		}
		return null;
	}

	public Class<?>[] getParameterTypes() {
		return this.constructor.getParameterTypes();
	}

	public Constructor<R> getConstructor() {
		return constructor;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) { return true; }
		if (object == null || getClass() != object.getClass()) { return false; }

		ConstructorWrapper<?> that = (ConstructorWrapper<?>) object;

		return constructor != null ? constructor.equals(that.constructor) : that.constructor == null;

	}

	@Override
	public int hashCode() {
		return constructor != null ? constructor.hashCode() : 0;
	}
}

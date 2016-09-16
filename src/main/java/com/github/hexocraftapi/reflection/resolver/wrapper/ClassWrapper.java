package com.github.hexocraftapi.reflection.resolver.wrapper;

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

public class ClassWrapper<R> extends WrapperAbstract
{

	private final Class<R> clazz;

	public ClassWrapper(Class<R> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean exists() {
		return this.clazz != null;
	}

	public Class<R> getClazz() {
		return clazz;
	}

	public String getName() {
		return this.clazz.getName();
	}

	public R newInstance() {
		try {
			return this.clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public R newInstanceSilent() {
		try {
			return this.clazz.newInstance();
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) { return true; }
		if (object == null || getClass() != object.getClass()) { return false; }

		ClassWrapper<?> that = (ClassWrapper<?>) object;

		return clazz != null ? clazz.equals(that.clazz) : that.clazz == null;

	}

	@Override
	public int hashCode() {
		return clazz != null ? clazz.hashCode() : 0;
	}
}

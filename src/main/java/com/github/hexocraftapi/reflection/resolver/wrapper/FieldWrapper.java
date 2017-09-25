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

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class FieldWrapper<R> extends WrapperAbstract {

	private final Field field;

	public FieldWrapper(Field field) {
		this.field = field;
	}

	@Override
	public boolean exists() {
		return this.field != null;
	}

	public String getName() {
		return this.field.getName();
	}

	public R get(Object object) {
		try {
			return (R) this.field.get(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public R getSilent(Object object) {
		try {
			return (R) this.field.get(object);
		} catch (Exception ignored) {
		}
		return null;
	}

	public void set(Object object, R value) {
		try {
			this.field.set(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSilent(Object object, R value) {
		try {
			this.field.set(object, value);
		} catch (Exception ignored) {
		}
	}

	public Field getField() {
		return field;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) { return true; }
		if (object == null || getClass() != object.getClass()) { return false; }

		FieldWrapper<?> that = (FieldWrapper<?>) object;

		return field != null ? field.equals(that.field) : that.field == null;
	}

	@Override
	public int hashCode() {
		return field != null ? field.hashCode() : 0;
	}
}

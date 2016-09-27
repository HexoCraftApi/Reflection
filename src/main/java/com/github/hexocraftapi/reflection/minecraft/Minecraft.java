package com.github.hexocraftapi.reflection.minecraft;

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
import com.github.hexocraftapi.reflection.resolver.FieldResolver;
import com.github.hexocraftapi.reflection.resolver.MethodResolver;
import com.github.hexocraftapi.reflection.resolver.minecraft.NMSClassResolver;
import com.github.hexocraftapi.reflection.resolver.minecraft.OBCClassResolver;
import com.github.hexocraftapi.reflection.util.AccessUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to access minecraft/bukkit specific objects
 */
public class Minecraft {
	static final Pattern NUMERIC_VERSION_PATTERN = Pattern.compile("v([0-9])_([0-9])*_R([0-9])");

	public static final Version VERSION;

	private static NMSClassResolver nmsClassResolver = new NMSClassResolver();
	private static OBCClassResolver obcClassResolver = new OBCClassResolver();
	private static Class<?> NmsEntity;
	private static Class<?> CraftEntity;

	static {
		VERSION = Version.getVersion();

		try {
			NmsEntity = nmsClassResolver.resolve("Entity");
			CraftEntity = obcClassResolver.resolve("entity.CraftEntity");
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the current NMS/OBC version (format <code>&lt;version&gt;.</code>
	 */
	public static String getVersion() {
		return VERSION.name();
	}

	public static Object getHandle(Object object) throws ReflectiveOperationException {
		Method method;
		try {
			method = AccessUtil.setAccessible(object.getClass().getDeclaredMethod("getHandle"));
		} catch (ReflectiveOperationException e) {
			method = AccessUtil.setAccessible(CraftEntity.getDeclaredMethod("getHandle"));
		}
		return method.invoke(object);
	}

	public static Entity getBukkitEntity(Object object) throws ReflectiveOperationException {
		Method method;
		try {
			method = AccessUtil.setAccessible(NmsEntity.getDeclaredMethod("getBukkitEntity"));
		} catch (ReflectiveOperationException e) {
			method = AccessUtil.setAccessible(CraftEntity.getDeclaredMethod("getHandle"));
		}
		return (Entity) method.invoke(object);
	}

	public static Object getHandleSilent(Object object) {
		try {
			return getHandle(object);
		} catch (Exception e) {
		}
		return null;
	}

	public enum Version {
		UNKNOWN(-1) {
			@Override
			public boolean matchesPackageName(String packageName) {
				return false;
			}
		},

		v1_7_R1(10701),
		v1_7_R2(10702),
		v1_7_R3(10703),
		v1_7_R4(10704),

		v1_8_R1(10801),
		v1_8_R2(10802),
		v1_8_R3(10803),
		v1_8_R4(10804),

		v1_9_R1(10901),
		v1_9_R2(10902),

		v1_10_R1(11001);

		private int version;

		Version(int version) {
			this.version = version;
		}

		/**
		 * @return the version-number
		 */
		public int version() {
			return version;
		}

		/**
		 * @param version the version to check
		 * @return <code>true</code> if this version is older than the specified version
		 */
		public boolean olderThan(Version version) {
			return version() < version.version();
		}

		/**
		 * @param version the version to check
		 * @return <code>true</code> if this version is newer than the specified version
		 */
		public boolean newerThan(Version version) {
			return version() >= version.version();
		}

		/**
		 * @param version the version to check
		 * @return <code>true</code> if this version is equals to the specified version
		 */
		public boolean equals(Version version) {
			return version() == version.version();
		}

		/**
		 * @param oldVersion The older version to check
		 * @param newVersion The newer version to check
		 * @return <code>true</code> if this version is newer than the oldVersion and older that the newVersion
		 */
		public boolean inRange(Version oldVersion, Version newVersion) {
			return newerThan(oldVersion) && olderThan(newVersion);
		}

		public boolean matchesPackageName(String packageName) {
			return packageName.toLowerCase().contains(name().toLowerCase());
		}

		public static Version getVersion() {
			String name = Bukkit.getServer().getClass().getPackage().getName();
			String versionPackage = name.substring(name.lastIndexOf('.') + 1) + ".";
			for (Version version : values()) {
				if (version.matchesPackageName(versionPackage)) { return version; }
			}
			System.err.println("[Reflection] Failed to find version enum for '" + name + "'/'" + versionPackage + "'");

			System.out.println("[Reflection] Generating dynamic constant...");
			Matcher matcher = NUMERIC_VERSION_PATTERN.matcher(versionPackage);
			while (matcher.find()) {
				if (matcher.groupCount() < 3) { continue; }

				String majorString = matcher.group(1);
				String minorString = matcher.group(2);
				if (minorString.length() == 1) { minorString = "0" + minorString; }
				String patchString = matcher.group(3);
				if (patchString.length() == 1) { patchString = "0" + patchString; }

				String numVersionString = majorString + minorString + patchString;
				int numVersion = Integer.parseInt(numVersionString);
				String packge = versionPackage.substring(0, versionPackage.length() - 1);

				try {
					// Add enum value
					Field valuesField = new FieldResolver(Version.class).resolve("$VALUES");
					Version[] oldValues = (Version[]) valuesField.get(null);
					Version[] newValues = new Version[oldValues.length + 1];
					System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
					Version dynamicVersion = (Version) newEnumInstance(Version.class, new Class[] {
							String.class,
							int.class,
							int.class }, new Object[] {
							packge,
							newValues.length - 1,
							numVersion });
					newValues[newValues.length - 1] = dynamicVersion;
					valuesField.set(null, newValues);

					System.out.println("[Reflection] Injected dynamic version " + packge + " (#" + numVersion + ").");
					System.out.println("[Reflection] Please inform about the outdated version, as this is not guaranteed to work.");
					return dynamicVersion;
				} catch (ReflectiveOperationException e) {
					e.printStackTrace();
				}
			}

			return UNKNOWN;
		}

		@Override
		public String toString() {
			return name() + " (" + version() + ")";
		}
	}

	public static Object newEnumInstance(Class clazz, Class[] types, Object[] values) throws ReflectiveOperationException {
		Constructor constructor = new ConstructorResolver(clazz).resolve(types);
		Field accessorField = new FieldResolver(Constructor.class).resolve("constructorAccessor");
		ConstructorAccessor constructorAccessor = (ConstructorAccessor) accessorField.get(constructor);
		if (constructorAccessor == null) {
			new MethodResolver(Constructor.class).resolve("acquireConstructorAccessor").invoke(constructor);
			constructorAccessor = (ConstructorAccessor) accessorField.get(constructor);
		}
		return constructorAccessor.newInstance(values);

	}
}

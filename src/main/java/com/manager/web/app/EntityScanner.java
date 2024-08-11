package com.manager.web.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import jakarta.persistence.Entity;

public class EntityScanner {

	public static List<Class<?>> findEntitiesInPackage(String packageName) throws IOException, ClassNotFoundException {
		List<Class<?>> entityClasses = new ArrayList<>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(packagePath);

		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			String filePath = resource.getFile();
			List<Class<?>> classes = getClasses(filePath, packageName);
			for (Class<?> clazz : classes) {
				if (clazz.isAnnotationPresent(Entity.class)) {
					entityClasses.add(clazz);
				}
			}
		}

		return entityClasses;
	}

	private static List<Class<?>> getClasses(String directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		File directoryFile = new File(directory);
		if (!directoryFile.exists()) {
			return classes;
		}

		File[] files = directoryFile.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".class")) {
					String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
					classes.add(Class.forName(className));
				} else if (file.isDirectory()) {
					classes.addAll(getClasses(file.getAbsolutePath(), packageName + '.' + file.getName()));
				}
			}
		}

		return classes;
	}

	public static void main(String[] args) {
		try {
			List<Class<?>> entities = findEntitiesInPackage("com.manager.web.app.api.vo");
			for (Class<?> entity : entities) {
				System.out.println(entity.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

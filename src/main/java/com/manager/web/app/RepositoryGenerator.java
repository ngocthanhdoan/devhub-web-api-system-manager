package com.manager.web.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RepositoryGenerator {

	public static <T> void generateRepository(Class<T> entityClass, String packageName, String directoryPath)
			throws IOException {
		String className = entityClass.getSimpleName() + "Repository";
		String fullPackageName = packageName + "." + className;
		File file = new File(directoryPath + "/" + className + ".java");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write("package " + packageName + ";\n\n");
			writer.write("import com.manager.web.app.api.vo." + entityClass.getSimpleName() + ";\n");
			writer.write("import org.springframework.data.jpa.repository.JpaRepository;\n");
			writer.write("import java.io.Serializable;\n\n");
			writer.write("public interface " + className + " extends JpaRepository<" + entityClass.getSimpleName()
					+ ", Serializable> {\n");
			writer.write("    // Custom query methods if needed\n");
			writer.write("}\n");
		}
	}

	public static List<Class<?>> entities() {
		List<Class<?>> entities = null;
		try {
			entities = EntityScanner.findEntitiesInPackage("com.manager.web.app.api.vo");
			for (Class<?> entity : entities) {
				System.out.println(entity.getName());
				generateRepository(entity, "com.manager.web.app.api.repository",
						"src/main/java/com/manager/web/app/api/repository");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entities;
	}

	public static void main(String[] args) throws IOException {
		// Example
		try {
			List<Class<?>> entities = EntityScanner.findEntitiesInPackage("com.manager.web.app.api.vo");
			for (Class<?> entity : entities) {
				System.out.println(entity.getName());
				generateRepository(entity, "com.manager.web.app.api.repository",
						"src/main/java/com/manager/web/app/api/repository");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

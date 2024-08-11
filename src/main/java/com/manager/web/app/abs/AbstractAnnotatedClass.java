package com.manager.web.app.abs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.manager.web.app.annotation.ParamName;

public abstract class AbstractAnnotatedClass {

    private static final Properties methodParameterProperties = new Properties();

    private final Map<String, String[]> methodParameterMap = new HashMap<>();

    private static final String propertiesFilePath;

    static {
        propertiesFilePath = new File("").getAbsolutePath() + "/method-parameters.properties";
    }

    public AbstractAnnotatedClass() {
        loadExistingProperties();
        loadMethodParameterNames();
    }

    private void loadExistingProperties() {
        // Tải các properties hiện có từ file
        try (FileInputStream input = new FileInputStream(propertiesFilePath)) {
            methodParameterProperties.load(input);
        } catch (IOException e) {
            System.out.println("No existing properties file found, creating a new one.");
        }
    }

    private void loadMethodParameterNames() {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            String[] paramNames = new String[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                ParamName paramNameAnnotation = parameters[i].getAnnotation(ParamName.class);
                if (paramNameAnnotation != null) {
                    paramNames[i] = paramNameAnnotation.value();
                } else {
                    paramNames[i] = null; // hoặc bạn có thể ném một ngoại lệ ở đây
                }
            }
            methodParameterMap.put(method.getName(), paramNames);

            // Lưu thông tin tham số vào Properties
            for (int i = 0; i < paramNames.length; i++) {
                if (paramNames[i] != null) {
                    methodParameterProperties.setProperty(method.getName() + ".arg" + i, paramNames[i]);
                }
            }
        }

        // Lưu properties vào file
        try (FileOutputStream output = new FileOutputStream(propertiesFilePath)) {
            methodParameterProperties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getMethodParameters(String methodName) {
        return methodParameterMap.get(methodName);
    }

    public static Object callMethod(String className, String methodName, Map<String, Object> params) throws Exception {
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.getDeclaredConstructor().newInstance();

        // Tải properties từ file
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        }

        // Lấy danh sách tên tham số từ properties
        int paramCount = 0;
        while (properties.containsKey(methodName + ".arg" + paramCount)) {
            paramCount++;
        }

        String[] paramNames = new String[paramCount];
        for (int i = 0; i < paramCount; i++) {
            paramNames[i] = properties.getProperty(methodName + ".arg" + i);
        }

        Method targetMethod = null;
        Object[] methodParams = new Object[paramNames.length];

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                if (paramNames.length != method.getParameterCount()) {
                    throw new IllegalArgumentException("Parameter count mismatch");
                }

                for (int i = 0; i < paramNames.length; i++) {
                    String paramName = paramNames[i];
                    if (params.containsKey(paramName)) {
                        methodParams[i] = convertType(method.getParameterTypes()[i], params.get(paramName));
                    } else {
                        throw new IllegalArgumentException("Missing parameter: " + paramName);
                    }
                }

                targetMethod = method;
                break;
            }
        }

        if (targetMethod == null) {
            throw new NoSuchMethodException("No matching method found");
        }

        return targetMethod.invoke(instance, methodParams);
    }

    private static Object convertType(Class<?> targetType, Object value) {
        if (value == null) {
            return null;
        }

        if (targetType.isInstance(value)) {
            return value;
        }

        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value.toString());
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value.toString());
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value.toString());
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (targetType == String.class) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("Unsupported parameter type: " + targetType);
        }
    }
}

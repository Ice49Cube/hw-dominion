package dominion.frontend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dominion.routing.ResultBase;
import dominion.routing.CommandBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Routing {

    private final HashMap<String, Method> methods;
    private final String url;
    private final ObjectMapper mapper;
    private final Object target;

    public Routing(String url, Object target) {
        this.mapper = new ObjectMapper();
        this.methods = new HashMap();
        this.target = target;
        this.url = url;
        this.loadMethods();
    }

    public Method getMethod(String name) {
        return this.methods.get(name);
    }

    public ResultBase invoke(CommandBase command) throws Exception {
        String json = post(mapper.writeValueAsString(command));
        JsonNode node = mapper.readTree(json);
        JsonNode methodNode = node.findValue("method");
        System.out.println(methodNode.asText());
        Method method = this.methods.get(methodNode.asText());
        Class clazz = method.getParameterTypes()[0];
        return (ResultBase) mapper.readValue(json, clazz);
    }

    private void loadMethods() {
        for (Method method : this.target.getClass().getMethods()) {
            Class<?> types[] = method.getParameterTypes();
            if (types.length > 0 && ResultBase.class.isAssignableFrom(types[0])) {
                methods.put(method.getName(), method);
            }
        }
    }

    private String post(String data) throws Exception {
        try {
            URL request = new URL(this.url);
            URLConnection connection = request.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
                out.write(data);
                out.close();
            }
            StringBuilder builder = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    builder.append(line); // + "\r\n"(no need, json has no line breaks!)
                }
                in.close();
            }
            return builder.toString();
        } catch (IOException e) {
            Logger.getLogger(Routing.class.getName()).log(Level.SEVERE, "Error in request...", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and subpackages.
     * http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     * http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
     *
     * @param directory The base directory
     * @param packageName The package name for classes found inside the base
     * directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}

package dominion.frontend;

import com.fasterxml.jackson.databind.*;

import dominion.routing.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

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

    private String dispatch(HttpURLConnection http) throws Exception {
        System.out.println("HTTP: " + http.getResponseCode() + " " + http.getResponseMessage());
        try {
            return readStream(http.getInputStream());
        } catch(Exception ex) {
            readAndThrowError(http);
            return null; // <- never gets here, previous statement throws an error
        }
    }

    public Method getMethod(String name) {
        return this.methods.get(name);
    }

    public ResultBase invoke(CommandBase command) throws Exception {
        try
        {
            String json = post(mapper.writeValueAsString(command));
            JsonNode node = mapper.readTree(json);
            JsonNode methodNode = node.findValue("method");
            Method method = this.methods.get(methodNode.asText());
            if (method == null) {
                throw new NoSuchMethodException("Method '" + methodNode.asText() + "' not found.");
            }
            Class clazz = method.getParameterTypes()[0];
            return (ResultBase) mapper.readValue(json, clazz);
        }
        catch(java.lang.reflect.InvocationTargetException e) {
            throw new Exception(e.getCause() != null ? e.getCause() : e);
        }
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
        System.out.println("JSON: " + data);
        URL request = new URL(this.url);
        HttpURLConnection http = (HttpURLConnection) request.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");
        http.connect();
        try (OutputStreamWriter out = new OutputStreamWriter(http.getOutputStream())) {
            out.write(data);
            out.close();
        }
        String result = dispatch(http);
        return result;
    }

    private void readAndThrowError(HttpURLConnection http) throws Exception {
        if (http.getContentLengthLong() > 0 && http.getContentType().contains("application/json")) {
            String json = this.readStream(http.getErrorStream());
            Object oson = this.mapper.readValue(json, Object.class);
            json = this.mapper.writer().withDefaultPrettyPrinter().writeValueAsString(oson);
            throw new IllegalStateException(http.getResponseCode() + " " + http.getResponseMessage() + "\n" + json);
        } else {
            throw new IllegalStateException(http.getResponseCode() + " " + http.getResponseMessage());
        }
    }

    private String readStream(InputStream stream) throws Exception {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line); // + "\r\n"(no need, json has no line breaks!)
            }
            in.close();
        }
        System.out.println("JSON: " + builder.toString());
        return builder.toString();
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

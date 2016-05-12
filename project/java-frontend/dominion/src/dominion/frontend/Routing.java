
package dominion.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dominion.routing.ResultBase;
import dominion.routing.CommandBase;

import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Routing 
{
    private final String url;
    private final ObjectMapper mapper;
    
    public Routing (String url)
    {
        this.url = url;
        this.mapper = new ObjectMapper();
    }

    private String post(String data) throws Exception
    {
        URL request = new URL(this.url);
        URLConnection connection = request.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter out = new OutputStreamWriter();
        out.write(data);
        out.close();
        BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line); // + "\r\n"(no need, json has no line breaks!)
        }
        in.close();
        return builder.toString();
    }
    
    public ResultBase invoke(String method, CommandBase command) throws Exception
    {        
        command.method = method;
        String result = post(mapper.writeValueAsString(command));
        Class[] classes = getClasses("dominion.game.commands");
        for(Class clazz : classes)
        {
            if(ResultBase.class.isAssignableFrom(clazz))
            {
                try {
                    return (ResultBase)mapper.readValue(result, clazz);
                } catch (IOException ex) {
                    Logger.getLogger(Routing.class.getName()).log(Level.INFO, "Skipping class '" + clazz.getName() + "'...", ex);
                }
            }
        }        
        return null;
    }
    
   /**
    * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
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
       List<File> dirs = new ArrayList<File>();
       while (resources.hasMoreElements()) {
           URL resource = resources.nextElement();
           dirs.add(new File(resource.getFile()));
       }
       ArrayList<Class> classes = new ArrayList<Class>();
       for (File directory : dirs) {
           classes.addAll(findClasses(directory, packageName));
       }
       return classes.toArray(new Class[classes.size()]);
   }

   /**
    * Recursive method used to find all classes in a given directory and subdirs.
    * http://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
    * 
    * @param directory   The base directory
    * @param packageName The package name for classes found inside the base directory
    * @return The classes
    * @throws ClassNotFoundException
    */
   private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
       List<Class> classes = new ArrayList<Class>();
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

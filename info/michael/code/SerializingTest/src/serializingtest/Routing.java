/**
 * @author Michael
 */

package serializingtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.lang.reflect.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Routing 
{
    // The object on which functions are called, 
    // routed into.
    private final Object server;
    private final ObjectMapper mapper;

    private final Map<String, String> argumentMap;
    
    /**
     * Creates a new routing object.
     * @param server The object on which functions are called.
     */
    public Routing(Object server)
    {
        this.server = server;
        this.argumentMap = new HashMap();
        this.mapper = new ObjectMapper();
        this.createAndFillArgumentMap();        
    }

    private void createAndFillArgumentMap()
    {
        Method[] methods = server.getClass().getMethods();
        for(Method method : methods)
        {
            CommandName argumentName = method.getAnnotation(CommandName.class);
            if(argumentName != null)
            {
                this.argumentMap.put(method.getName(), argumentName.name());
            }
        }
    }

    public ResultBase invoke(String json)
    {
        CommandBase commandBase;
        Class<?> argumentType;
        String argumentTypeName;
        CommandBase command;
        Method method;
        try
        {
            commandBase = this.serializeJsonToCommandBase(json);
        } catch (IOException ex) {
            Logger.getLogger(Routing.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorResult(null, ex.getMessage(), "Failed to serialize the json string to a command.");
        }
        argumentTypeName = this.methodNameToArgumentTypeName(commandBase.method);
        try
        {
            argumentType = this.argumentTypeNameToArgumentType(argumentTypeName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Routing.class.getName()).log(Level.SEVERE, null, ex);
            if (argumentTypeName == null) argumentTypeName = "(null)";
            return new ErrorResult(commandBase.method, ex.getMessage(), "Failed to look up type with name '" + argumentTypeName + "'.");
        }
        try {
            command = this.serializeJsonToCommand(json, argumentType);
        } catch (IOException ex) {
            Logger.getLogger(Routing.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorResult(commandBase.method, ex.getMessage(), "Failed to serialize json to type '" + argumentTypeName + "'.");
        }
        try {
            method = this.methodNameToMethod(commandBase.method, argumentType);
            // A better way would be to use the method to get the argument it's type info ... :)
            //Class<?>[] types = method.getParameterTypes();
            //for(Class<?> type : types)
            //{
            //    System.out.println("type: " + type.getName());
            //}
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Routing.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorResult(commandBase.method, ex.getMessage(), "Failed to find the method.");
        }            
        try {
            return this.invokeCommand(method, command);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Routing.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorResult(commandBase.method, ex.getMessage(), "Failed to invoke the comand.");
        }      
    }
    
    
    public String resultToJson(ResultBase result)
    {
        try {
            return this.mapper.writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Routing.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Method methodNameToMethod(String methodName, Class<?> argumentType) throws NoSuchMethodException
    {
        return server.getClass().getMethod(methodName, argumentType);
    }

    public ResultBase invokeCommand(Method method, CommandBase command) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException 
    {
        return (ResultBase)method.invoke(server, command);
    }
    
    public Class<?> argumentTypeNameToArgumentType(String argumentTypeName) throws ClassNotFoundException
    {
        return Class.forName(argumentTypeName);
    }

    public String methodNameToArgumentTypeName(String methodName)
    {
        return argumentMap.get(methodName);
    }
    
    public CommandBase serializeJsonToCommand(String json, Class<?> argumentType) throws IOException
    {
        return (CommandBase)mapper.readValue(json, argumentType);
    }
    
    public CommandBase serializeJsonToCommandBase(String json) throws IOException
    {
        return mapper.readValue(json, CommandBase.class);
    }
}
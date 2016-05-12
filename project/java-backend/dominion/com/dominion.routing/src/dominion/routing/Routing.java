package dominion.routing;

import java.io.*;
import java.lang.reflect.*;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;

/**
 * The Routing class. Routes JSON requests into a command object and writes back
 * the result.
 */
public class Routing 
{
    private final GenericDeserializer<CommandBase> deserializer;
    private final Object target;
    private final ObjectMapper commandMapper;
    private final ObjectMapper resultMapper;
   
    /**
     * Constructs the Routing object.
     * @param target The target object on which to call commands.
     * @throws NullPointerException The target argument was null. 
     * Or another unexpected reason.
     */
    public Routing(Object target)
    {
        this.deserializer = new GenericDeserializer<>(CommandBase.class, "_method_");
        this.target = target;
        this.commandMapper = new ObjectMapper();
        this.resultMapper = new ObjectMapper();
        this.initialize();
    }

    /**
     * Translates the data in the reader onto a JSON string, serializes that
     * JSON string into an CommandBase derived object and calls the related 
     * method. After that the returned object, derived of ResultBase, 
     * is written to writer.
     * @param reader A BufferedReader that points to a JSON stream that 
     * represents an object derived of CommandBase.
     * @return 
     */
    public ResultBase execute(BufferedReader reader) 
    {        
        try {
            return invoke(reader);
        } catch (JsonParseException | JsonMappingException e) {
            return new ErrorResult(e);
        } catch (NoSuchMethodException | SecurityException e) {
            return new ErrorResult(e);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            return new ErrorResult(e);
        } catch (InvocationTargetException e) {
        	Exception e2 = (Exception)e.getCause();
        	if (e2 != null)
        		return new ErrorResult(e2);
        	else
        		return new ErrorResult(e);
        } catch (IOException e) {
            if (e instanceof MethodFieldMissingException) {
               return new ErrorResult((MethodFieldMissingException)e);
            } else if (e instanceof PropertyMissingException) {
                return new ErrorResult((PropertyMissingException)e);
            } else if (e instanceof MethodMissingException) {
                return new ErrorResult((MethodMissingException)e);
            } else {
                return new ErrorResult(e);
            }
        }
    }
    
    public void writeResult(PrintWriter writer, ResultBase result) 
    		throws JsonGenerationException, JsonMappingException, IOException
    {
    	this.resultMapper.writeValue(writer, result);
    }
    
    /*
     * Serializes an exception, with extra information, into a ErrorResult 
     * object and writes that object to the printwriter.
     * @param ex The exception to serialize into JSON 
     * and write to a PrintWriter.
     * @param information The extra information to serialize into JSON 
     * and write to a PrintWriter.
     * @param writer The writer to write the serialized exception to.
     * @throws java.io.IOException Exception thrown by a jackson ObjectMapper.
     */
    /*public void exception(Exception ex, String information, PrintWriter writer) throws IOException
    {
        ErrorResult result = new ErrorResult(null, ex.getMessage(), information);
        this.resultMapper.writeValue(writer, result);
    }*/
    
    /**
     * Initializes the Routing object's members.
     */
    private void initialize()
    {
        this.initializeDeserializer();
        this.initializeCommandMapper();
    }
    
    /**
     * Initializes the Routing object's command mapper. Registerin the 
     * deserializer's module.
     */
    private void initializeCommandMapper()
    {
        this.commandMapper.registerModule(this.deserializer.getModule());
    }
    
    /**
     * Initializes the deserializer, loading the target it's annotated methods
     * and the corresponding argument types into the GenericDeserializer.
     */
    @SuppressWarnings("unchecked")
    private void initializeDeserializer()
    {
        Class<?> targetClass = target.getClass();
        Method[] methods = targetClass.getMethods();
        for (Method method : methods)
        {
            RoutedCommand routingCommand = 
                    method.getAnnotation(RoutedCommand.class);
            if (routingCommand != null)
            {
                Class<?>[] argumentTypes = method.getParameterTypes();
                if (argumentTypes.length != 1)
                    throw new MalformedParametersException();
                if (!CommandBase.class.isAssignableFrom(argumentTypes[0])
                || !ResultBase.class.isAssignableFrom(method.getReturnType()))
                    throw new ClassCastException();
                this.deserializer.registerCommand(method.getName(), 
                        (Class<CommandBase>)argumentTypes[0]);
            }
        }
    }

    /**
     * Converts the JSON string into a CommandBase object, looks up the method
     * and invokes the method with a argument that inherits of CommandBase.
     * @param reader The reader object that represents a JSON stream.
     * @return Returns a object that inherits of ResultBase.
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    @SuppressWarnings("deprecation")
    private ResultBase invoke(BufferedReader reader) throws 
			JsonParseException, JsonMappingException, IOException,
			NoSuchMethodException, SecurityException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        CommandBase command = (CommandBase)this.commandMapper.readValue(
                reader, CommandBase.class);
        if(command != null)
        {
            //Method method = this.target.getClass().getMethod(command.method, 
            //        command.getClass());
            Method method = this.target.getClass().getMethod(command.getMethod(), 
                    command.getClass());
            return (ResultBase)method.invoke(target, command);        
        } else {
            throw new JsonMappingException("Failed to map the request into a command.");
        }
    }
}

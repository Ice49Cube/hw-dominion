package dominion.routing;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;
import com.fasterxml.jackson.databind.module.*;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.annotation.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;


/**
 * Allows to deserialize a JSON stream into a type based on one attribute.
 * @author Michael
 */
class GenericDeserializer<T> extends StdDeserializer<T>
{
	private static final long serialVersionUID = 5788864385429184239L;
	private final String jsonPropertyName;
    private final Map<String, Class<? extends T>> types;
    private final SimpleModule module;
    
    public GenericDeserializer(Class<T> clazz, String jsonPropertyName)
    {
        super(clazz);
        @SuppressWarnings("deprecation")
        Version version = new Version(1, 0, 0, null);
        this.jsonPropertyName = jsonPropertyName;
        this.module =  new SimpleModule("CommandDeserializer", version);
        this.module.addDeserializer(clazz, this);
        //this.types = new HashMap<String, Class<? extends T>>();
        this.types = new HashMap<>();
    }

    private String findMethodName(ObjectNode root) throws MethodFieldMissingException
    {
        JsonNode node = root.findValue(jsonPropertyName);
        if (node == null) 
            throw new MethodFieldMissingException("Method field '" + jsonPropertyName + "' is missing.", jsonPropertyName);
        return node.textValue();
    }
    
    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc) 
            throws IOException, JsonProcessingException
    {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();  
        JsonNode node = mapper.readTree(jp);
        ObjectNode root = (ObjectNode)node ;
        // Get the "_method_" property it's value
        String methodName = findMethodName(root);
        // Get the into a type
        Class<? extends T> commandType = this.types.get(methodName);
        if (commandType == null) {
            methodName = (methodName == null ? "(null)" : methodName);
            throw new MethodMissingException("Method '" + methodName + "' could not be resolved.", methodName);
        }
        // Validate if field exists
        for(Field field : commandType.getFields())
        {
            String fieldName = field.getName();
            JsonProperty prop = field.getAnnotation(JsonProperty.class);
            if (prop != null)
            {
                if (prop.value() != null && !prop.value().equals(""))
                    fieldName = prop.value();
                if(root.findValue(fieldName) == null)
                    throw new PropertyMissingException("Property with name '" + fieldName + "' is missing for command '" + methodName + "'.", methodName, fieldName);
            }
        }
        //return (T)mapper.readValue(node.traverse(), commandType);
        return (T)new ObjectMapper().treeToValue(node, commandType);
    }    

    public void registerCommand(String method, Class<? extends T> commandType)  
    {
        this.types.put(method, commandType);
    }
    
    public Module getModule()
    {
        return this.module;
    }
}

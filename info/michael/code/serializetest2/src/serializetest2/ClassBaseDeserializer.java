/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializetest2;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.*;
import com.fasterxml.jackson.databind.node.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Michael
 */

class ClassBaseDeserializer<T> extends StdDeserializer<T>
{
    private final String jsonPropertyName;
    private Map<String, Class<? extends T>> types = new HashMap<String, Class<? extends T>>();
   
    public ClassBaseDeserializer(String jsonPropertyName, Class<? extends T> type)
    {
        super(type);
        this.jsonPropertyName = jsonPropertyName;
    }    
    
    public void registerCommand(String method, Class<? extends T> commandType)  
    {
        this.types.put(method, commandType);
    }    
    
    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException 
    {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();  
        ObjectNode root = (ObjectNode) mapper.readTree(jp);
        JsonNode jsonNode =  root.findValue(jsonPropertyName);
        String key = jsonNode.textValue();
        Class<? extends T> commandType = this.types.get(key);
        return mapper.treeToValue(root, commandType);
    }    
}

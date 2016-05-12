/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializetest2;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public final class SerializeTest2 {

    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        GenericClass<ClassBase> x = new GenericClass(ClassBase.class);
        
        System.out.println(x.getGenericType().getName());
        
        try {
            ClassBaseDeserializer<ClassBase> deserializer = new ClassBaseDeserializer("_method_", ClassBase.class);
            deserializer.registerCommand("aaa", ClassA.class);
            deserializer.registerCommand("bbb", ClassB.class);
            
            SimpleModule module =  new SimpleModule("PolymorphicAnimalDeserializerModule", new Version(1, 0, 0, null));
            module.addDeserializer(ClassBase.class, deserializer);
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(module);
            
            ClassBase base;
            base = mapper.readValue("{\"_method_\":\"aaa\",\"propa\":true}", (Class<ClassBase>)ClassBase.class);
            System.out.println(((ClassA)base).propa);
            base = mapper.readValue("{\"_method_\":\"bbb\",\"propb\":true}", (Class<ClassBase>)ClassBase.class);
            System.out.println(((ClassB)base).propb);
            /*
            try {
            ObjectMapper mapper = getObjectMapperForDeserialization();
            ClassBase base = mapper.readValue("{\"_method_\":\"ok\",\"propa\":true}", (Class<ClassBase>)ClassBase.class);
            //Foo foo = new Foo(10, "Foo");
            } catch (IOException ex) {
            Logger.getLogger(SerializeTest2.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
        } catch (IOException ex) {
            Logger.getLogger(SerializeTest2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    /*static ObjectMapper getObjectMapperForDeserialization() {
        ObjectMapper mapper = new ObjectMapper();
        StdTypeResolverBuilder typeResolverBuilder = new DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        mapper.setDefaultTyping(typeResolverBuilder
                .init(JsonTypeInfo.Id.MINIMAL_CLASS, new ClassResolver())
                .inclusion(JsonTypeInfo.As.PROPERTY)
                .typeProperty("_method_"));
        return mapper;
    }*/
    
}

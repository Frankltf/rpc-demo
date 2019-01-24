package com.ltf.rpc.client.util;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

public class YamlUtils {

    public static Map<String,Object> yaml2Map(String yamlSource){
        try {
            YamlMapFactoryBean yaml=new YamlMapFactoryBean();
            yaml.setResources(new ClassPathResource(yamlSource));
            return yaml.getObject();
        }catch (Exception e){
            return  null;
        }
    }

    public static Properties yaml2Properties(String yamlSource){
        try {
            YamlPropertiesFactoryBean yamlPropertiesFactoryBean=new YamlPropertiesFactoryBean();
            yamlPropertiesFactoryBean.setResources(new ClassPathResource(yamlSource));
            return yamlPropertiesFactoryBean.getObject();
        }catch (Exception e){
            return  null;
        }
    }
}

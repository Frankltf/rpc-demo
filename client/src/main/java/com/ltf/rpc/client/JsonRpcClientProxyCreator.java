package com.ltf.rpc.client;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.googlecode.jsonrpc4j.spring.JsonProxyFactoryBean;
import com.ltf.rpc.client.util.ClassUtil;
import com.ltf.rpc.client.util.YamlUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static java.lang.String.format;

@Component
public class JsonRpcClientProxyCreator implements BeanFactoryPostProcessor {
    static Properties properties= YamlUtils.yaml2Properties("application.yml");

    @SuppressWarnings({"rawtypes","unchecked"})
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        if(properties.getProperty("jsonrpc.baseurl") ==null || properties.getProperty("jsonrpc.scan") == null){
            return;
        }
        DefaultListableBeanFactory defaultListableBeanFactory= (DefaultListableBeanFactory) configurableListableBeanFactory;
        for (Class clazz : ClassUtil.getClasses(properties.getProperty("jsonrpc.scan"))) {
            if (clazz.isAnnotationPresent(JsonRpcService.class)) {
                JsonRpcService jrs = (JsonRpcService) clazz.getAnnotation(JsonRpcService.class);
                registerJsonProxyBean(defaultListableBeanFactory, clazz.getName(), jrs.value());
            }
        }
    }

    private void registerJsonProxyBean(DefaultListableBeanFactory defaultListableBeanFactory, String className, String path) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(JsonProxyFactoryBean.class)
                .addPropertyValue("serviceUrl", appendBasePath(path)).addPropertyValue("serviceInterface", className);

        defaultListableBeanFactory.registerBeanDefinition(className + "-clientProxy", beanDefinitionBuilder.getBeanDefinition());
    }

    private String appendBasePath(String path) {
        try {
            return new URL(properties.getProperty("jsonrpc.baseurl") + path).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(format("Cannot combine URLs '%s' and '%s' to valid URL.", "http://localhost:8080/", path), e);
        }
    }
}

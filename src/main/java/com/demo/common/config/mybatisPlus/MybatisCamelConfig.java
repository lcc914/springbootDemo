package com.demo.common.config.mybatisPlus;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.google.common.base.CaseFormat;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * mybatis查询Map返回值下划线转峰坨
 *
 * @author lcc
 */
@Configuration
public class MybatisCamelConfig {
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        //   使用自定义的解析器工厂
        return configuration -> configuration.setObjectWrapperFactory(new MapWrapperFactory());
    }

    /**
     * 实现工厂
     */
    static class MapWrapperFactory implements ObjectWrapperFactory {
        @Override
        public boolean hasWrapperFor(Object object) {
            //  判断是Map就使用该解析
            return object instanceof Map;
        }

        @Override
        public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
            //  将默认的解析器替换成峰坨转换
            return new CamelWrapper(metaObject, (Map) object);
        }
    }

    /**
     * 重写转换器；添加转换方法
     */
    static class CamelWrapper extends MapWrapper {

        CamelWrapper(MetaObject metaObject, Map<String, Object> map) {
            super(metaObject, map);
        }

        /**
         * mybatis查询结果下划线转峰坨
         *
         * @param name
         * @param useCamelCaseMapping 是否进行转换。yaml 中配置map-underscore-to-camel-case=true
         * @return
         */
        @Override
        public String findProperty(String name, boolean useCamelCaseMapping) {
            if (useCamelCaseMapping) {
                //    使用guava的转换方法,覆盖Map的默认返回值
                return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
            }
            return name;
        }

    }
}
package com.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: LiChangChen
 * @Date: 2020/10/14
 */
public class MybatisPlusGenerator2 {
    private static String jdbc_drivername = "com.mysql.cj.jdbc.Driver";
    private static String ip_port = "localhost:3306/";
    private static String database = "mydb";
    private static String jdbc_connectionURL = "jdbc:mysql://" + ip_port + database + "?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
    private static String jdbc_username = "root";
    private static String jdbc_password = "123456";
    private static String parterPackage = "com.quartz.scheduler.demo.";//自定义包名
    private static String xmlMapper = "mapper";//生成resource目录下存放mapperxml的文件夹名


    //    private static String tableName = "\"^((?!qrtz_).)*$\"";//移除包含qrtz_的表
    private static String tableName = "log_table";//移除包含qrtz_的表


    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        AutoGenerator mpg = new AutoGenerator();

        String Author = "LiChangChen";//开发者
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setFileOverride(true);
        gc.setSwagger2(true);
        gc.setActiveRecord(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor(Author);
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);

        //自定义类型转换
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });

        //数据库配置
        Properties prop = new Properties();
//        InputStream in = new MyBatisPlusGenerator().getClass().getResourceAsStream("/generator.properties");
//        prop.load(in);
        dsc.setDriverName(jdbc_drivername);
        dsc.setUsername(jdbc_username);
        dsc.setPassword(jdbc_password);
        dsc.setUrl(jdbc_connectionURL);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
//        strategy.setTablePrefix("qrtz_");//移除表前缀
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setExclude("^((?!qrtz_).)*$");//只生成包含qrtz_的表
        strategy.setInclude(tableName);
        strategy.setEnableSqlFilter(false);//setExclude setInclude 属性需要设置为false
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        mpg.setPackageInfo(pc);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 执行生成

        customPackagePath(pc, mpg);
        mpg.execute();
    }


    /**
     * 自定义包路径，文件生成路径，这边配置更灵活
     * 虽然也可以使用InjectionConfig设置FileOutConfig的方式设置路径
     * 这里直接使用Map方式注入ConfigBuilder配置对象更加直观
     *
     * @param pc
     * @param mpg
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void customPackagePath(PackageConfig pc, AutoGenerator mpg) throws NoSuchFieldException, IllegalAccessException {

        String projectPath = System.getProperty("user.dir");
        String mavenPath = "\\src\\main\\java\\";
        String srcPath = projectPath + mavenPath;

        /**
         * packageInfo配置controller、service、serviceImpl、entity、mapper等文件的包路径
         * 这里包路径可以根据实际情况灵活配置
         */
        Map<String, String> packageInfo = new HashMap<>();
        packageInfo.put(ConstVal.CONTROLLER, parterPackage + "controller");
        packageInfo.put(ConstVal.SERVICE, parterPackage + "service");
        packageInfo.put(ConstVal.SERVICE_IMPL, parterPackage + "service.impl");
        packageInfo.put(ConstVal.ENTITY, parterPackage + "pojo");
        packageInfo.put(ConstVal.MAPPER, parterPackage + "dao");

        /**
         * pathInfo配置controller、service、serviceImpl、entity、mapper、mapper.xml等文件的生成路径
         * srcPath也可以更具实际情况灵活配置
         * 后面部分的路径是和上面packageInfo包路径对应的源码文件夹路径
         * 这里你可以选择注释其中某些路径，可忽略生成该类型的文件，例如:注释掉下面pathInfo中Controller的路径，就不会生成Controller文件
         */
        Map pathInfo = new HashMap<>();
        pathInfo.put(ConstVal.CONTROLLER_PATH, srcPath + packageInfo.get(ConstVal.CONTROLLER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.SERVICE_PATH, srcPath + packageInfo.get(ConstVal.SERVICE).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, srcPath + packageInfo.get(ConstVal.SERVICE_IMPL).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.ENTITY_PATH, srcPath + packageInfo.get(ConstVal.ENTITY).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.MAPPER_PATH, srcPath + packageInfo.get(ConstVal.MAPPER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.XML_PATH, projectPath + "\\src\\main\\resources\\" + xmlMapper + "\\");
        pc.setPathInfo(pathInfo);

        /**
         * 创建configBuilder对象，传入必要的参数
         * 将以上的定义的包路径packageInfo配置到赋值到configBuilder对象的packageInfo属性上
         * 因为packageInfo是私有成员变量，也没有提交提供公共的方法，所以使用反射注入
         * 为啥要这么干，看源码去吧
         */
        ConfigBuilder configBuilder = new ConfigBuilder(mpg.getPackageInfo(), mpg.getDataSource(), mpg.getStrategy(), mpg.getTemplate(), mpg.getGlobalConfig());
        Field packageInfoField = configBuilder.getClass().getDeclaredField("packageInfo");
        packageInfoField.setAccessible(true);
        packageInfoField.set(configBuilder, packageInfo);

        /**
         * 设置配置对象
         */
        mpg.setConfig(configBuilder);
    }

}

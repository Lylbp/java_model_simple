package com.lylbp.mybatisPlus.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.config.*;
import com.lylbp.mybatisPlus.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {

    /**
     * 数据连接信息
     *
     * @param dbType   数据库类型
     * @param dbUrl    连接地址
     * @param username 用户名
     * @param password 密码
     * @param driver   驱动
     * @return DataSourceConfig
     */
    private static DataSourceConfig dataSourceConfig(DbType dbType, String dbUrl, String username, String password, String driver) {
        return new DataSourceConfig()
                .setDbType(dbType)
                .setUrl(dbUrl)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(driver)
                ;
    }

    /**
     * 全局配置对象
     *
     * @return GlobalConfig
     */
    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
                .setAuthor(Config.AUTHOR)
                .setOutputDir(Config.OUT_OUT_DIR)
                .setFileOverride(true) // 是否覆盖已有文件
                .setOpen(false) // 是否打开输出目录
                .setDateType(DateType.ONLY_DATE) // 时间采用java 8，（操作工具类：JavaLib => DateTimeUtils）
                .setActiveRecord(true)// 不需要ActiveRecord特性的请改为false
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setEnableCache(false)// XML enableCache
                .setKotlin(false) //是否生成 kotlin 代码
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName(Config.FILE_NAME_MODEL)
                .setMapperName(Config.FILE_NAME_DAO)
                .setXmlName(Config.FILE_NAME_XML)
                .setServiceName(Config.FILE_NAME_SERVICE)
                .setServiceImplName(Config.FILE_NAME_SERVICE_IMPL)
                .setControllerName(Config.FILE_NAME_CONTROLLER)
                .setIdType(IdType.ASSIGN_UUID) // 主键类型
                .setSwagger2(Config.SWAGGER_SUPPORT) // model swagger2
                ;
    }

    /**
     * 策略配置对象
     *
     * @param tablePrefixes 表前缀
     * @param tableNames    表前缀
     * @param fieldPrefixes 字段前缀
     * @return StrategyConfig
     */
    private static StrategyConfig strategyConfig(String[] tablePrefixes, String[] tableNames, String[] fieldPrefixes) {
        return new StrategyConfig()
                .setCapitalMode(true) // 全局大写命名 ORACLE 注意
                .setSkipView(false) // 是否跳过视图
                //.setDbColumnUnderline(true)
                .setTablePrefix(tablePrefixes)// 此处可以修改为您的表前缀(数组)
                .setFieldPrefix(fieldPrefixes) // 字段前缀
                .setNaming(NamingStrategy.underline_to_camel) // 表名生成策略
                .setInclude(tableNames)//修改替换成你需要的表名，多个表名传数组
                //.setExclude(new String[]{"test"}) // 排除生成的表
                .setEntityLombokModel(true) // lombok实体
                .setChainModel(false) // 【实体】是否为构建者模型（默认 false）
                .setEntityColumnConstant(false) // 【实体】是否生成字段常量（默认 false）// 可通过常量名获取数据库字段名 // 3.x支持lambda表达式
                .setLogicDeleteFieldName(Config.FIELD_LOGIC_DELETE_NAME) // 逻辑删除属性名称
                .setRestControllerStyle(true) //是否为RestController
                .setEntityTableFieldAnnotationEnable(true)
//                .setSuperControllerClass("com.baomidou.ant.common.BaseController")// 公共父类
                ;
    }

    /**
     * 包配置对象
     *
     * @return PackageConfig
     */
    private static PackageConfig packageConfig() {
        return new PackageConfig()
                .setParent(Config.PACKAGE_NAME)
                .setController(Config.PACKAGE_NAME_CONTROLLER)
                .setEntity(Config.PACKAGE_NAME_MODEL)
                .setMapper(Config.PACKAGE_NAME_DAO)
                .setService(Config.PACKAGE_NAME_SERVICE)
                .setServiceImpl(Config.PACKAGE_NAME_SERVICE_IMPL)
                ;
    }

    /**
     * 自定义配置对象
     *
     * @param injectionConfig 表配置
     * @return InjectionConfig
     */
    private static InjectionConfig injectionConfig(InjectionConfig injectionConfig) {
        // 自定义输出Mapper配置
        List<FileOutConfig> fileOutConfigList = new ArrayList<FileOutConfig>();
        fileOutConfigList.add(new FileOutConfig("/templates/generate/myMapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.PROJECT_PATH + Config.RESOURCES_PATH + Config.PACKAGE_NAME_XML + "/" + tableInfo.getXmlName() + StringPool.DOT_XML;
            }
        });

        // 自定义输出VO配置
        fileOutConfigList.add(new FileOutConfig("/templates/generate/entity.vo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.PROJECT_PATH + Config.CODE_PATH + Config.PACKAGE_NAME_VO + "/" + tableInfo.getEntityName() + "VO.java";
            }
        });

        // 自定义输出QO配置
        fileOutConfigList.add(new FileOutConfig("/templates/generate/entity.qo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.PROJECT_PATH + Config.CODE_PATH + Config.PACKAGE_NAME_QO + "/" + tableInfo.getEntityName() + "QO.java";
            }
        });

        // 自定义输出DTO配置
        fileOutConfigList.add(new FileOutConfig("/templates/generate/entity.dto.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return Config.PROJECT_PATH + Config.CODE_PATH + Config.PACKAGE_NAME_DTO + "/" + tableInfo.getEntityName() + "DTO.java";
            }
        });

        injectionConfig.setFileOutConfigList(fileOutConfigList);

        return injectionConfig;
    }

    /**
     * 执行
     *
     * @param dbType        数据库类型
     * @param dbUrl         数据库地址
     * @param username      数据库用户名
     * @param password      数据库密码
     * @param driver        数据库驱动
     * @param tablePrefixes 表前缀
     * @param tableNames    表明
     * @param fieldPrefixes 字段前缀
     */
    public static void execute(DbType dbType, String dbUrl, String username, String password, String driver,
                               String[] tablePrefixes, String[] tableNames, String[] fieldPrefixes) {
        if (ArrayUtil.isAllEmpty(tableNames)) {
            return;
        }
        GlobalConfig globalConfig = globalConfig();
        DataSourceConfig dataSourceConfig = dataSourceConfig(dbType, dbUrl, username, password, driver);

        // 策略配置
        StrategyConfig strategyConfig = strategyConfig(tablePrefixes, tableNames, fieldPrefixes);
        // 策略配置--controller父类
        if (ObjectUtil.isNotEmpty(Config.SUPER_CONTROLLER_CLASS)) {
            strategyConfig.setSuperControllerClass(Config.SUPER_CONTROLLER_CLASS);
        }
        //包信息配置
        PackageConfig packageConfig = packageConfig();
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("VOPackage", packageConfig.getParent() + "." + Config.PACKAGE_NAME_VO);
                map.put("QOPackage", packageConfig.getParent() + "." + Config.PACKAGE_NAME_QO);
                map.put("DTOPackage", packageConfig.getParent() + "." + Config.PACKAGE_NAME_DTO);
                map.put("DBType", dbType.getDb());
                map.put("FieldlogicDeleteName", Config.FIELD_LOGIC_DELETE_NAME);
                this.setMap(map);
            }
        };
        injectionConfig = injectionConfig(injectionConfig);

        TemplateConfig tc = new TemplateConfig();
        tc.setMapper("/templates/generate/myMapper.java.vm");
        tc.setService("/templates/generate/myService.java.vm");
        tc.setServiceImpl("/templates/generate/myServiceImpl.java.vm");
        tc.setController("/templates/generate/myController.java.vm");

        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setCfg(injectionConfig)
                .setTemplateEngine(new VelocityTemplateEngine())
                .setTemplate(tc)
                .execute();
    }


}


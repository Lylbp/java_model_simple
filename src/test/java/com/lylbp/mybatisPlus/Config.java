package com.lylbp.mybatisPlus;

/**
 * @author weiwenbin
 * @since 2019-04-17 12:04
 */
public class Config {
    /**
     * 生成代码的包名
     */
    public static final String PACKAGE_NAME = "com.lylbp";

    /**
     * 作者
     */
    public static final String AUTHOR = "weiwenbin";

    /**
     * 公共父类
     */
    public static final String SUPER_CONTROLLER_CLASS = "";

    /**
     * 项目路径
     */
    public static final String PROJECT_PATH = "/Library/WebServer/java_project/lylbp_oa";

    /**
     * 代码路径
     */
    public static final String CODE_PATH = "/src/main/java/com/lylbp/oa/";

    /**
     * 输出路径
     */
    public static final String OUT_OUT_DIR = PROJECT_PATH + "/src/main/java/";

    /////////////////////////////////////////////dto///////////////////////////////////////////////////////////
    /**
     * 包名：dto
     */
    public static final String PACKAGE_NAME_DTO = "dto";


    /////////////////////////////////////////////qo///////////////////////////////////////////////////////////

    /**
     * 包名：qo
     */
    public static final String PACKAGE_NAME_QO = "qo";

    /////////////////////////////////////////////vo///////////////////////////////////////////////////////////

    /**
     * 包名：vo
     */
    public static final String PACKAGE_NAME_VO = "vo";

    /////////////////////////////////////////////*Mapprt.xml///////////////////////////////////////////////////////////
    /**
     * 资源文件路径
     */
    public static final String RESOURCES_PATH = "/src/main/resources/";
    /**
     * 包名：xml
     */
    public static final String PACKAGE_NAME_XML = "mapper";

    /**
     * 文件名后缀：Mapper
     */
    public static final String FILE_NAME_XML = "%sMapper";


    /////////////////////////////////////////////controller///////////////////////////////////////////////////////////
    /**
     * 包名：controller
     */
    public static final String PACKAGE_NAME_CONTROLLER = "controller";

    /**
     * 文件名后缀：Controller
     */
    public static final String FILE_NAME_CONTROLLER = "%sController";

    /////////////////////////////////////////////service///////////////////////////////////////////////////////////
    /**
     * 包名：service
     */
    public static final String PACKAGE_NAME_SERVICE = "service";

    /**
     * MP开头，Service结尾
     */
    public static final String FILE_NAME_SERVICE = "%sService";

    /**
     * 包名：service.impl
     */
    public static final String PACKAGE_NAME_SERVICE_IMPL = "service.impl";

    /**
     * 文件名后缀：ServiceImpl
     */
    public static final String FILE_NAME_SERVICE_IMPL = "%sServiceImpl";

    /////////////////////////////////////////////entity///////////////////////////////////////////////////////////
    /**
     * 包名：entity
     */
    public static final String PACKAGE_NAME_MODEL = "entity";

    /**
     * 文件名后缀：Entity
     */
    public static final String FILE_NAME_MODEL = "%s";

    /////////////////////////////////////////////mapper///////////////////////////////////////////////////////////
    /**
     * 包名：mapper
     */
    public static final String PACKAGE_NAME_DAO = "mapper";

    /**
     * 文件名后缀：Mapper
     */
    public static final String FILE_NAME_DAO = "%sMapper";


    /**
     * 逻辑删除字段
     */
    public static final String FIELD_LOGIC_DELETE_NAME = "is_valid";


    /**
     * 是否支持Swagger，默认不支持
     */
    public static final Boolean SWAGGER_SUPPORT = true;

}


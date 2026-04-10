import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestMain {

    public static  void main(String[] args){
     String fileoutDir = "D:\\pre";
     documentGeneration(fileoutDir);
    }
    /**
     * 文档生成
     */
    static  void documentGeneration(String fileOutputDir) {
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setAutoCommit(true);

        hikariConfig.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        hikariConfig.setUsername("postgres");

        hikariConfig.setPassword("root");

        hikariConfig.addDataSourceProperty("serverName", "47.98.167.66");

        hikariConfig.addDataSourceProperty("portNumber", 5432);

        hikariConfig.addDataSourceProperty("databaseName","db_xzzd");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
                //生成文件路径
                .fileOutputDir(fileOutputDir)
                //打开目录
                .openOutputDir(true)
                //文件类型
                .fileType(EngineFileType.HTML)
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
                .fileName("湖南公安电子数据综合研判系统-数据库设计文档2").build();

        //忽略表
        ArrayList<String> ignoreTableName = new ArrayList<>();
        ignoreTableName.add("Sheet1");
        ignoreTableName.add("test_group");
        //忽略表前缀
        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("t_web_");
        ignorePrefix.add("t_weibo_");
        ignorePrefix.add("t_sync_");
        ignorePrefix.add("t_safe_");
        ignorePrefix.add("t_phone_");
        ignorePrefix.add("t_other_");
        ignorePrefix.add("dataset_uc_");
        ignorePrefix.add("dataset_browse_");
        ignorePrefix.add("dataset_weixin_");
        ignorePrefix.add("dataset_mail_");
        ignorePrefix.add("rh_t_im_");
        ignorePrefix.add("interact_");
        //忽略表后缀
        ArrayList<String> ignoreSuffix = new ArrayList<>();
        ignoreSuffix.add("_test");
        ProcessConfig processConfig = ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix).build();
        //配置
        Configuration config = Configuration.builder()
                //版本
                .version("1.0.2")
                //描述
                .description("数据库设计文档生成")
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(engineConfig)
                //生成配置
                .produceConfig(processConfig)
                .build();
        //执行生成
        new DocumentationExecute(config).execute();
    }
}

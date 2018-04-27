package jim.demo.spring.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author jim
 * @date 2018/4/23
 * 由于MapperScannerConfigurer执行的比较早，所以必须有AutoConfigureAfter
 */
@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
public class MapperScannerConfig {

    private static final Logger log = LoggerFactory.getLogger(MapperScannerConfig.class);

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer () {
        log.info("注册MyBatis通用Mapper");
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("jim.demo.spring.cloud.mapper");
        Properties properties = new Properties();
        // 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。
        properties.setProperty("mappers", CommonMapper.class.getName());
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }


}

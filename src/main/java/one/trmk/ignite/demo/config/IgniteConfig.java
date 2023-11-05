package one.trmk.ignite.demo.config;

import one.trmk.ignite.demo.data.Instrument;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.QueryIndex;
import org.apache.ignite.cache.QueryIndexType;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;

@Configuration
public class IgniteConfig {

    @Bean
    Ignite ignite(ApplicationContext context) {
        IgniteConfiguration configuration = new IgniteConfiguration();
        configuration.setGridLogger(new Slf4jLogger());

        configureInstrumentCache(configuration);
        configureDataStorage(configuration);

        var bean = new IgniteSpringBean();
        bean.setConfiguration(configuration);
        bean.setApplicationContext(context);

        return bean;
    }

    private void configureDataStorage(IgniteConfiguration configuration) {
        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();

        dataStorageConfiguration.getDefaultDataRegionConfiguration().setPersistenceEnabled(false);

        configuration.setDataStorageConfiguration(dataStorageConfiguration);
    }

    private void configureInstrumentCache(IgniteConfiguration configuration) {
        CacheConfiguration<String, Instrument> cacheConfiguration = new CacheConfiguration<>();
        cacheConfiguration.setName("instrument");
        cacheConfiguration.setIndexedTypes(String.class, Instrument.class);

        QueryEntity queryEntity = new QueryEntity(String.class, Instrument.class)
                .setFields(new LinkedHashMap<>() {{
                               put("symbol", String.class.getName());
                           }}
                ).setIndexes(List.of(
                        new QueryIndex(List.of(
                                "symbol"
                        ), QueryIndexType.SORTED
                        )
                                .setName("SYMBOL_IDX")));

        cacheConfiguration.setQueryEntities(List.of(queryEntity));
        configuration.setCacheConfiguration(cacheConfiguration);
    }
}

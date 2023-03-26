package edu.asoldatov.nir.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.importer.cadet.CadetImporter;
import edu.asoldatov.nir.importer.cadet.TulaSVUCadetImporter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "datasource.db-cooperation.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public Map<School, CadetImporter> importers(TulaSVUCadetImporter tulaSVUCadetImporter) {
        Map<School, CadetImporter> importers = new EnumMap<>(School.class);
        importers.put(School.TULASVU, tulaSVUCadetImporter);
        return importers;
    }
}

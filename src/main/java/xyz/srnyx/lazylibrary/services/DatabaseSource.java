package xyz.srnyx.lazylibrary.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.freya022.botcommands.api.core.db.HikariSourceSupplier;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.flywaydb.core.Flyway;

import org.jetbrains.annotations.NotNull;
import xyz.srnyx.lazylibrary.LazyLibrary;


@BService
public class DatabaseSource implements HikariSourceSupplier {
    @NotNull private final HikariDataSource source;

    public DatabaseSource(@NotNull LazyLibrary settings) {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(settings.fileSettings.database);
        source = new HikariDataSource(config);
        Flyway.configure()
                .dataSource(source)
                .schemas("bc")
                .locations("bc_database_scripts")
                .validateMigrationNaming(true)
                .loggers("slf4j")
                .load()
                .migrate();
    }

    @Override @NotNull
    public HikariDataSource getSource() {
        return source;
    }
}

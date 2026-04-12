package xyz.srnyx.lazylibrary.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.freya022.botcommands.api.core.db.HikariSourceSupplier;
import io.github.freya022.botcommands.api.core.service.annotations.BService;

import org.flywaydb.core.Flyway;

import org.jetbrains.annotations.NotNull;

import xyz.srnyx.lazylibrary.LazyLibrary;


/**
 * A service that provides a HikariDataSource for database connections.
 * It initializes the connection pool and applies any pending database migrations using Flyway.
 */
@BService
public class DatabaseSource implements HikariSourceSupplier {
    @NotNull private final HikariDataSource source;

    /**
     * Constructs a new DatabaseSource using the provided LazyLibrary settings.
     * It configures the HikariDataSource and applies database migrations.
     *
     * @param   library the LazyLibrary instance containing database configuration
     */
    public DatabaseSource(@NotNull LazyLibrary library) {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(library.fileSettings.database);
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

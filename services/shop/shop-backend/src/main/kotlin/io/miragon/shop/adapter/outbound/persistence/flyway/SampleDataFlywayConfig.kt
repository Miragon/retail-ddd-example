package io.miragon.shop.adapter.outbound.persistence.flyway

import io.github.oshai.kotlinlogging.KotlinLogging
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.MigrationVersion
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.ApplicationRunner
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(SampleDataFlywayProperties::class)
class SampleDataFlywayConfig(
    private val dataSource: DataSource,
    private val properties: SampleDataFlywayProperties
) {

    private val log = KotlinLogging.logger {}

    @Bean
    @ConditionalOnProperty(prefix = "sample-data", name = ["enabled"], havingValue = "true")
    fun sampleDataFlywayRunner(): ApplicationRunner = ApplicationRunner {
        if (properties.locations.isEmpty()) {
            log.info { "Flyway sample data is enabled but no locations are configured. Skipping sample data migration." }
            return@ApplicationRunner
        }

        val sampleDataFlyway = Flyway.configure()
            .dataSource(dataSource)
            .table("flyway_sample_data_history")
            .baselineOnMigrate(true)
            .baselineVersion(MigrationVersion.fromVersion("0"))
            .locations(*properties.locations.toTypedArray())
            .load()

        val result = sampleDataFlyway.migrate()
        log.info { "Applied ${result.migrationsExecuted} Flyway sample-data migrations from ${properties.locations}" }
    }
}

@ConfigurationProperties(prefix = "sample-data")
data class SampleDataFlywayProperties(
    var enabled: Boolean = false,
    var locations: List<String> = emptyList()
)

package org.sid.cabinet_medical_bigdata;


import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Objects;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "cabinet_medical";
    }

    @Bean
    @Primary
    public CqlSessionFactoryBean session() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setLocalDatacenter("datacenter1");
        session.setContactPoints("localhost");
        session.setKeyspaceName(getKeyspaceName());
        session.setPort(9042);

        return session;
    }

    @Bean
    public CqlTemplate cqlTemplate(CqlSessionFactoryBean session) throws Exception {
        return new CqlTemplate(Objects.requireNonNull(session.getObject()));
    }

    @Bean
    public CassandraTemplate customCassandraTemplate(CqlSession session) throws Exception {
        return new CassandraTemplate(session);
    }

}

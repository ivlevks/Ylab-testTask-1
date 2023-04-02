package io.ylab.intensive.lesson05.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.eventsourcing.api")
public class Config {

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        return dataSource;
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue saveQueue() {
        return new Queue("saveQueue", false);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue("deleteQueue", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("person-exchange");
    }

    @Bean
    Binding saveBinding(Queue saveQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(saveQueue).to(topicExchange).with("person.save");
    }

    @Bean
    Binding deleteBinding(Queue deleteQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(deleteQueue).to(topicExchange).with("person.delete");
    }

}

package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.DbUtil;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Configuration
@EnableRabbit
@ComponentScan("io.ylab.intensive.lesson05.messagefilter")
public class Config {
  
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
  public DataSource dataSource() throws SQLException {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);
    return dataSource;
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
  public Queue inputQueue() {
    return new Queue("input", false);
  }

  @Bean
  public Queue outputQueue() {
    return new Queue("output", false);
  }

  @Bean
  TopicExchange topicExchange() {
    return new TopicExchange("word-exchange");
  }

  @Bean
  Binding outputBinding(Queue outputQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(outputQueue).to(topicExchange).with("word");
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setConcurrentConsumers(3);
    factory.setMaxConcurrentConsumers(10);
    return factory;
  }

  @PostConstruct
  public void initAndPopulateDB() throws Exception {
    String createDDL = ""
            + "create table profanity (\n"
            + "word varchar\n"
            + ")";

    if (!DbUtil.checkDbIfExists("profanity", dataSource())) {
      DbUtil.applyDdl(createDDL, dataSource());
      List<String> dataFromFile = ReaderUtil.getDataFromFile("bad_word.txt");
      DbUtil.writeDataFromDb("profanity", dataSource(), dataFromFile);
    }
  }
}

package com.learningKafka.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * Configuration class for automatic Kafka topic creation.
 *
 * <p>This class registers a {@link NewTopic} bean with the Spring application context,
 * which is automatically detected and utilized by Spring Kafka's {@link org.springframework.kafka.core.KafkaAdmin}.
 * The {@link KafkaAdmin} internally uses Kafka's {@link org.apache.kafka.clients.admin.AdminClient}
 * to create or validate topics on the Kafka broker during application startup.</p>
 *
 * <p><strong>Key Components:</strong></p>
 * <ul>
 *   <li>{@link NewTopic}: Represents the configuration for the Kafka topic to be created.</li>
 *   <li>{@link org.springframework.kafka.core.KafkaAdmin}: A Spring-managed component responsible for
 *       managing Kafka topics using the AdminClient.</li>
 *   <li>{@link org.apache.kafka.clients.admin.AdminClient}: The low-level Kafka client API used for
 *       administrative tasks such as creating, deleting, or describing topics.</li>
 * </ul>
 *
 * <p><strong>How It Works:</strong></p>
 * <ol>
 *   <li>The {@link NewTopic} bean is registered in the Spring context when the application starts.</li>
 *   <li>Spring Kafka's {@link KafkaAdmin} detects all {@link NewTopic} beans in the context.</li>
 *   <li>The {@link KafkaAdmin} internally uses Kafka's {@link AdminClient} to interact with the Kafka broker.</li>
 *   <li>If the topic (e.g., <strong>library-events</strong>) does not exist on the broker,
 *       the {@link AdminClient} creates it with the specified configurations (e.g., partitions and replicas).</li>
 * </ol>
 *
 * <p><strong>Why is this important?</strong></p>
 * <p>The {@link AdminClient} plays a crucial role in programmatically managing Kafka infrastructure.
 * It simplifies the creation and validation of topics in development and testing environments,
 * reducing manual intervention. However, in production environments, direct usage of {@link AdminClient}
 * or tools like Kafka CLI is preferred for stricter control and governance.</p>
 *
 * <p><strong>Why not for production?</strong></p>
 * <p>In production environments, topics are typically managed manually or through automation tools
 * (e.g., Kafka CLI, Terraform, or Confluent Control Center) to ensure consistency, governance, and compliance.</p>
 */
@Configuration
@Profile("local")
public class AutoCreateConfig {

	/**
	 * Creates a Kafka topic named <strong>library-events</strong> with the following properties:
	 * <ul>
	 *   <li>3 partitions: Enables scalability and parallel processing.</li>
	 *   <li>3 replicas: Provides fault tolerance by maintaining redundant copies of the data.</li>
	 * </ul>
	 *
	 * <p>The method uses the {@link TopicBuilder} API to configure the topic programmatically.</p>
	 *
	 * @return a {@link NewTopic} object representing the Kafka topic configuration.
	 *
	 * <p><strong>Note:</strong> This method is not suited for production use as topics in production
	 * are typically managed manually or with automation tools.</p>
	 */
	@Bean
	public NewTopic libraryEvents() {

		return TopicBuilder.name("library-events").partitions(3).replicas(3).build();
	}

}

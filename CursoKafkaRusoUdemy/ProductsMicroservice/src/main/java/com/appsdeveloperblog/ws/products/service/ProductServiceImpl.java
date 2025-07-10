package com.appsdeveloperblog.ws.products.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.ws.products.rest.CreateProductRestModel;

@Service
public class ProductServiceImpl implements ProductService {
	
	KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate; //inyeccion por constructor de Spring Boot
	private final Logger LOGGER  = LoggerFactory.getLogger(this.getClass());
	
	public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public String createProduct(CreateProductRestModel productRestModel) throws Exception {
		
		String productId = UUID.randomUUID().toString();
		
		// TODO: Persist Product Details into database table before publishing an Event
		
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
				productRestModel.getTitle(), productRestModel.getPrice(), 
				productRestModel.getQuantity());
		
		LOGGER.info("Before publishing a ProductCreatedEvent");

		/*
		Asincrono
		CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
				kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);
		future.whenComplete((result, ex) -> {
			if (ex != null) {
				LOGGER.error("Error while sending message to Kafka: " + ex.getMessage());
			} else {
				LOGGER.info("Partition: " + result.getRecordMetadata().partition());
				LOGGER.info("Topic: " + result.getRecordMetadata().topic());
				LOGGER.info("Offset: " + result.getRecordMetadata().offset());
			}
			});
			//si agregamos future.join() el metodo sí espera y se vuelve sincrono, pero no es lo recomendable pq queda poco claro el codigo.
		   LOGGER.info("***** Returning product id"); --> notar que este log se se ejecuta antes de la respuesta del completableFuture, lo q evidencia q no espera para devolver un productId al cliente ya q es asincrono.
		 */

		//Sincrono -->the advantage of sending message synchronously is that we can configure our microservice to wait
		//for acknowledgement from all Kafka brokers that the message is successfully stored in Kafka topic,

		//Seccion Idempotent Consumer
		ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
				"product-created-event",
				productId, //este es el unique identifier
				productCreatedEvent);
		record.headers().add("messageId",UUID.randomUUID().toString().getBytes());

		SendResult<String, ProductCreatedEvent> result = //Barath no devuelve ningun tipo al ejecutar kafkaTemplate.send, lo deja como void.  Creo q es mas util dejarlo como lo tiene el ruso, ya q permite obtener metadata mas abajo.
				kafkaTemplate.
					//	send("product-created-events-topic",productId, productCreatedEvent).get(); //lo cambio a la linea siguiente en seccion Include a unique id into message header
							send(record).get();
		
		LOGGER.info("Partition: " + result.getRecordMetadata().partition());
		LOGGER.info("Topic: " + result.getRecordMetadata().topic());
		LOGGER.info("Offset: " + result.getRecordMetadata().offset());
		LOGGER.info("***** Returning product id");
		
		return productId;
	}

}

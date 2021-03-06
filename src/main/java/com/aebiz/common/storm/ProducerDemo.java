package com.aebiz.common.storm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;


public class ProducerDemo {
	
	//创建主题
	public static void main(String[] args) throws InterruptedException {
		send();
		
		Thread.sleep(1000);
		
		//consumer();
	}
	
	
	//创建主图
	public static void topic(){
		 //创建topic
	    Properties props = new Properties();
	    props.put("bootstrap.servers", "192.168.180.128:9092");
	    AdminClient adminClient = AdminClient.create(props);
	    ArrayList<NewTopic> topics = new ArrayList<NewTopic>();
	    NewTopic newTopic = new NewTopic("topic-test", 1, (short) 1);
	    topics.add(newTopic);
	    CreateTopicsResult result = adminClient.createTopics(topics);
	    try {
	        result.all().get();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    } catch (ExecutionException e) {
	        e.printStackTrace();
	    }
		
	}
	
	
	
	//发送消息
	
	public static void send(){
		Properties props = new Properties();
	    props.put("bootstrap.servers", "127.0.0.1:9092");
	    props.put("acks", "all");
	    props.put("retries", 0);
	    props.put("batch.size", 16384);
	    props.put("linger.ms", 1);
	    props.put("buffer.memory", 33554432);
	    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

	    Producer<String, String> producer = new KafkaProducer<String, String>(props);
	   // for (int i = 0; i < 100; i++)
	        producer.send(new ProducerRecord<String, String>("test", "测试发送信息44"));

	    producer.close();

	
	}
	
	
	//消费消息
	public static void consumer(){
		
		   Properties props = new Properties();
		    props.put("bootstrap.servers", "127.0.0.1:9092");
		    props.put("group.id", "test");
		    props.put("enable.auto.commit", "true");
		    props.put("auto.commit.interval.ms", "1000");
		    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		    final KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
		    consumer.subscribe(Arrays.asList("access-log"),new ConsumerRebalanceListener() {
		        public void onPartitionsRevoked(Collection<TopicPartition> collection) {
		        }
		        public void onPartitionsAssigned(Collection<TopicPartition> collection) {
		            //将偏移设置到最开始
		            consumer.seekToBeginning(collection);
		        }
		    });
		    while (true) {
		        ConsumerRecords<String, String> records = consumer.poll(100);
		        for (ConsumerRecord<String, String> record : records)
		            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		    }
		
	}
	
	
}

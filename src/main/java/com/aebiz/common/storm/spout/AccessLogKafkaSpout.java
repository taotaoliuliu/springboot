package com.aebiz.common.storm.spout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * kafka消费数据的spout
 */
public class AccessLogKafkaSpout extends BaseRichSpout {

	private static final long serialVersionUID = 8698470299234327074L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogKafkaSpout.class);

	private SpoutOutputCollector collector;

	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		
		System.out.println("###########################");
		startKafkaConsumer();
	}

	@SuppressWarnings("rawtypes")
	private void startKafkaConsumer() {
		try {
			Properties props = new Properties();
			props.put("zookeeper.connect", "localhost:2181");
			props.put("group.id", "testlsl222");
			props.put("zookeeper.session.timeout.ms", "40000");
			//props.put("zookeeper.sync.time.ms", "2000");
			props.put("auto.commit.interval.ms", "1000");
			ConsumerConfig consumerConfig = new ConsumerConfig(props);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@####");

			ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
			String topic = "access-log";

			Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
			topicCountMap.put(topic, 1);

			System.out.println("consumerMap@@@@@@@@@@@@@@@@@@@@@@@@@@@####"+topic);

			Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
					.createMessageStreams(topicCountMap);
			
			System.out.println("consumerMap@@@@@@@@@@@@@@@@@@@@@@@@@@@####"+consumerMap);

			
			//List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
			KafkaStream<byte[], byte[]> kafkaStream = consumerMap.get(topic).get(0);
			
			System.out.println(kafkaStream+"@@@@@@");

			/*for (KafkaStream stream : streams) {
				ConsumerIterator<byte[], byte[]> it = stream.iterator();
				while (it.hasNext()) {
					String message = new String(it.next().message());
					LOGGER.info("【AccessLogKafkaSpout中的Kafka消费者接收到一条日志】message=" + message);
					try {
						collector.emit(new Values(message));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}*/
			
			ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
			while (it.hasNext()) {
				String message = new String(it.next().message());  
				System.out.println(message);
				LOGGER.info("【AccessLogKafkaSpout中的Kafka消费者接收到一条日志】message=" + message);
				try {
					collector.emit(new Values(message));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	public void nextTuple() {
	
			
	}
	 

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));  
	}
}
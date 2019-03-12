package com.aebiz.common.storm;

import java.util.ArrayList;
import java.util.List;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import com.aebiz.common.storm.bolt.MyKafkaBolt;

public class StormKafkaTopo {
	public static void main(String[] args) throws Exception {
		 String topic = "test" ;
	        ZkHosts zkHosts = new ZkHosts("127.0.0.1:2181");
	        SpoutConfig spoutConfig = new SpoutConfig(zkHosts, topic, 
	                "/data", 
	                "MyTrack") ;
	        List<String> zkServers = new ArrayList<String>() ;
	        zkServers.add("127.0.0.1");
	        spoutConfig.zkServers = zkServers;
	        spoutConfig.zkPort = 2181;
	        spoutConfig.socketTimeoutMs = 60 * 1000 ;
	        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme()) ; 

	        TopologyBuilder builder = new TopologyBuilder() ;
	        builder.setSpout("spout", new KafkaSpout(spoutConfig) ,1) ;
	        builder.setBolt("bolt1", new MyKafkaBolt(), 1).shuffleGrouping("spout") ;

	        Config conf = new Config ();
	        conf.setDebug(false) ;

	        if (args.length > 0) {
	            try {
	                StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
	            } catch (AlreadyAliveException e) {
	                e.printStackTrace();
	            } catch (InvalidTopologyException e) {
	                e.printStackTrace();
	            }
	        }else {
	            LocalCluster localCluster = new LocalCluster();
	            localCluster.submitTopology("mytopology", conf, builder.createTopology());
	        }

	    }
	    
}

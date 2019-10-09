package pl.stan;

import java.io.IOException;

import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import pl.stan.service.zookeeper.ZookeeperClusterService;

@SpringBootApplication
@ComponentScan({"pl.stan.controller", "pl.stan.service"})
@EnableAutoConfiguration
public class Application {
    
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public ZooKeeper zookeeperConnection(ZookeeperClusterService clusterService) throws IOException {
        return new ZooKeeper("127.0.0.1:9998", 3000, (we) -> {
            if (we.getState() == KeeperState.SyncConnected) {
                LOG.info("ZooKeeper node connected");
                try {
                    clusterService.joinCluster();
                } catch (Exception e) {
                    LOG.error("Could not join ZooKeeper cluster");
                }
            }
        });
    }

}

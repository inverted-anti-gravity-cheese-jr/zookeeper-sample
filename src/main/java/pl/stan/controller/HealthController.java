package pl.stan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.stan.dto.HealthDto;
import pl.stan.service.zookeeper.ZookeeperClusterService;

@RestController
@RequestMapping("/health")
public class HealthController {
    
    private String version = "1.0.0";
    
    @Autowired
    private ZookeeperClusterService clusterService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<HealthDto> checkHealth() {
        boolean zkAlive = clusterService.isAlive();
        if (!zkAlive) {
            return new ResponseEntity<HealthDto>(new HealthDto(version, "ALIVE", "NO_CONNECTION", null), HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<HealthDto>(new HealthDto(version, "ALIVE", "ALIVE", clusterService.getNodeId()), HttpStatus.OK);
    }

}

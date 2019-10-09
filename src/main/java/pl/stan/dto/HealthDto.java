package pl.stan.dto;

public class HealthDto {
    
    private String version;
    
    private String state;
    
    private String zookeeperState;
    
    private String nodeId;
    
    public HealthDto() {}
    
    public HealthDto(String version, String state, String zookeeperState, String nodeId) {
        this.version = version;
        this.state = state;
        this.zookeeperState = zookeeperState;
        this.nodeId = nodeId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZookeeperState() {
        return zookeeperState;
    }

    public void setZookeeperState(String zookeeperState) {
        this.zookeeperState = zookeeperState;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}

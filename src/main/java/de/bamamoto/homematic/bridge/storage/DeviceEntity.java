package de.bamamoto.homematic.bridge.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author barmeier
 */
public class DeviceEntity {


    private String address;
    private String type;
    private List<Object> paramSet;
    private Map<String,DeviceEntity> children;
    
    
    /**
     * HEATING_CLIMATECONTROL_TRANSCEIVER
     */
    
    public DeviceEntity() {
        paramSet = new ArrayList<>();
        children = new HashMap<>();
    }
    
    public void addChildren (Map<String, Object> child) {
        String childAddress = (String)child.get("ADDRESS");
        if (childAddress.contains(":")) {
            String[] parts = childAddress.split(":");
            if (parts[0].equalsIgnoreCase(getAddress())) {
                DeviceEntity de = new DeviceEntity();
                de.setAddress(childAddress);
                de.setType((String)child.get("TYPE"));
                de.setParamSet(new ArrayList<Object>(Arrays.asList((Object[])child.get("PARAMSETS"))));
                children.put(childAddress, de);
            }            
        }
    }
    
    
        /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the paramSet
     */
    public List<Object> getParamSet() {
        return paramSet;
    }

    /**
     * @param paramSet the paramSet to set
     */
    public void setParamSet(List<Object> paramSet) {
        this.paramSet = paramSet;
    }

    /**
     * @return the children
     */
    public Map<String,DeviceEntity> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(Map<String,DeviceEntity> children) {
        this.children = children;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.ci.yakatl;

/**
 *
 * @author sergio
 */
public class Sensor {
    
    private String id_wasp;
    private String sensor;
    private String value; 
    private String timestamp;

    /**
     * @return the id_wasp
     */
    public String getId_wasp() {
        return id_wasp;
    }

    /**
     * @param id_wasp the id_wasp to set
     */
    public void setId_wasp(String id_wasp) {
        this.id_wasp = id_wasp;
    }

    /**
     * @return the sensor
     */
    public String getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
    
}

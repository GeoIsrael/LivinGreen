package com.livingreen.dto;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "sensors",
        "time"
})
public class Dto {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("sensors")
    private List<Sensor> sensors = new ArrayList<Sensor>();
    @JsonProperty("time")
    private String time;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Dto withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("sensors")
    public List<Sensor> getSensors() {
        return sensors;
    }

    @JsonProperty("sensors")
    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Dto withSensors(List<Sensor> sensors) {
        this.sensors = sensors;
        return this;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    public Dto withTime(String time) {
        this.time = time;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Dto withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(sensors).append(time).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Dto) == false) {
            return false;
        }
        Dto rhs = ((Dto) other);
        return new EqualsBuilder().append(id, rhs.id).append(sensors, rhs.sensors).append(time, rhs.time).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}

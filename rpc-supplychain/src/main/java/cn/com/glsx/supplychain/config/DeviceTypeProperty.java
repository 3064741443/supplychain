package cn.com.glsx.supplychain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("devicetype")
public class DeviceTypeProperty {

	public Integer obd;
	public Integer dvd;
	public Integer dog;
	public Integer tbox;
	public Integer record;
	public Integer bbox;
	public Integer tracker;
	public Integer carwifi;
	public Integer cloudmirror;
	public Integer carcontrol;
	public Integer carefree;
	public Integer getObd() {
		return obd;
	}
	public void setObd(Integer obd) {
		this.obd = obd;
	}
	public Integer getDvd() {
		return dvd;
	}
	public void setDvd(Integer dvd) {
		this.dvd = dvd;
	}
	public Integer getDog() {
		return dog;
	}
	public void setDog(Integer dog) {
		this.dog = dog;
	}
	public Integer getTbox() {
		return tbox;
	}
	public void setTbox(Integer tbox) {
		this.tbox = tbox;
	}
	public Integer getRecord() {
		return record;
	}
	public void setRecord(Integer record) {
		this.record = record;
	}
	public Integer getBbox() {
		return bbox;
	}
	public void setBbox(Integer bbox) {
		this.bbox = bbox;
	}
	public Integer getTracker() {
		return tracker;
	}
	public void setTracker(Integer tracker) {
		this.tracker = tracker;
	}
	public Integer getCarwifi() {
		return carwifi;
	}
	public void setCarwifi(Integer carwifi) {
		this.carwifi = carwifi;
	}
	public Integer getCloudmirror() {
		return cloudmirror;
	}
	public void setCloudmirror(Integer cloudmirror) {
		this.cloudmirror = cloudmirror;
	}
	public Integer getCarcontrol() {
		return carcontrol;
	}
	public void setCarcontrol(Integer carcontrol) {
		this.carcontrol = carcontrol;
	}
	public Integer getCarefree() {
		return carefree;
	}
	public void setCarefree(Integer carefree) {
		this.carefree = carefree;
	}
	
	
}

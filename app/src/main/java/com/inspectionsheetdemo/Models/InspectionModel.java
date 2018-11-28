package com.inspectionsheetdemo.Models;

public class InspectionModel {
	public String model;
	public String customer;
	public String serialNumber;
	public String operatingHr;
	public String grConditions;
	public String impact;
	public String country;
	public String abrasive;
	public String moisture;
	public String packing;
	public String shoeType;
	public String shoeWidth;
	public String travelPerCent;
	public String trackTension;
	
	public InspectionModel(String model, String customer){
		this.model = model;
		this.customer = customer;
	}
}

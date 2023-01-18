package com.nb.web.product.dto;

public class RestockAlarmDTO {

	private String userCode;
	private String pdCode;
	private String color;
	private String size;
	private String colorCode;
	private String pdName;
	private String imgUrl;
	

	public RestockAlarmDTO() {
		super();
	}

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPdCode() {
		return pdCode;
	}
	public void setPdCode(String pdCode) {
		this.pdCode = pdCode;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "RestockAlarmDTO [userCode=" + userCode + ", pdCode=" + pdCode + ", color=" + color + ", size=" + size
				+ ", colorCode=" + colorCode + ", pdName=" + pdName + ", imgUrl=" + imgUrl + "]";
	}
	
	

	
}

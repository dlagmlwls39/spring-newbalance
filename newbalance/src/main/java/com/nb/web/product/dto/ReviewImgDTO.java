package com.nb.web.product.dto;

import java.math.BigDecimal;

public class ReviewImgDTO {

	private int revimgSeq;
	private int revSeq;
	private String revImg;
	
	
	public ReviewImgDTO() {
		super();
	}

	public ReviewImgDTO(int revSeq, String revImg) {
		super();
		this.revSeq = revSeq;
		this.revImg = revImg;
	}


	public int getRevimgSeq() {
		return revimgSeq;
	}
	public void setRevcimgSeq(int revimgSeq) {
		this.revimgSeq = revimgSeq;
	}
	public int getRevSeq() {
		return revSeq;
	}
	public void setRevSeq(int revSeq) {
		this.revSeq = revSeq;
	}
	public String getRevImg() {
		return revImg;
	}
	public void setRevImg(String revImg) {
		this.revImg = revImg;
	}

	@Override
	public String toString() {
		return "ReviewImgDTO [revimgSeq=" + revimgSeq + ", revSeq=" + revSeq + ", revImg=" + revImg + "]";
	}
	
}

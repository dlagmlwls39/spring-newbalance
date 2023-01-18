package com.nb.web.product.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.nb.web.product.dto.BuyProductDTO;
import com.nb.web.product.dto.ProductAjaxDTO;
import com.nb.web.product.dto.ProductColorDTO;
import com.nb.web.product.dto.ProductDTO;
import com.nb.web.product.dto.ProductImageDTO;
import com.nb.web.product.dto.ProductQnaDTO;
import com.nb.web.product.dto.ProductSizeDTO;
import com.nb.web.product.dto.RestockAlarmDTO;
import com.nb.web.product.dto.ReviewDTO;
import com.nb.web.product.dto.ReviewImgDTO;
import com.nb.web.product.dto.WishlistDTO;


public interface ProductDao {

	// 상품 정보
	ProductDTO selectProduct(String pdCode) throws SQLException; 
	
	// 상품별 이미지 리스트
	ArrayList<ProductImageDTO> selectImage(String pdCode) throws SQLException;
	
	// 상품별 사이즈 리스트
	ArrayList<ProductSizeDTO> selectSize(String pdCode) throws SQLException;

	// 연관 상품 리스트
	ArrayList<ProductDTO> selectRelatedProduct(String pdCode) throws SQLException;
	
	// 상품명이 같고 컬러만 다른 상품 리스트
	ArrayList<ProductColorDTO> diffColorProduct(String pdCode) throws SQLException;

	// 현재 상품의 컬러 정보
	ProductColorDTO getPdColor(String pdCode) throws SQLException;
	
	// 상품별 리뷰 리스트
	ArrayList<ReviewDTO> review(String pdCode) throws SQLException;

	// 리뷰별 이미지 리스트
	ArrayList<ReviewImgDTO> reviewImg(int revSeq) throws SQLException;

	// 리뷰 집계(개수, 별점 평균)
	ReviewDTO totalReview(String pdCode) throws SQLException;

	// 상품별 문의 리스트
	ArrayList<ProductQnaDTO> selectProductQna(String pdCode) throws SQLException;

	// 재입고 알림 상품 카운트(특정 회원이 특정 상품 알림 신청했는지 여부)
	int alarmCount(RestockAlarmDTO dto) throws SQLException;
	
	// 재입고알림 상품 추가
	int applyAlarm(RestockAlarmDTO dto) throws SQLException;
	
	// 상품문의 등록
	//int addProductQna(ProductQnaDTO dto) throws SQLException;

	//ProductAjaxDTO selectProductAjax(String pdCode) throws SQLException;
}

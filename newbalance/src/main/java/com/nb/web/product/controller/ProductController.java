package com.nb.web.product.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nb.web.product.dao.ProductDao;
import com.nb.web.product.dto.ProductColorDTO;
import com.nb.web.product.dto.ProductDTO;
import com.nb.web.product.dto.ProductImageDTO;
import com.nb.web.product.dto.ProductQnaDTO;
import com.nb.web.product.dto.ProductSizeDTO;
import com.nb.web.product.dto.RestockAlarmDTO;
import com.nb.web.product.dto.ReviewDTO;
import com.nb.web.product.dto.ReviewImgDTO;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	private ProductDao productDao = null;
	
	// 상품 상세보기
	@RequestMapping("productDetail.action")
	public String selectProduct(@RequestParam("pdCode") String pdCode, Model model) throws Exception {
		
		ProductDTO pdDto = this.productDao.selectProduct(pdCode); 
		ArrayList<ProductImageDTO> imgList = this.productDao.selectImage(pdCode); 
		ArrayList<ProductSizeDTO> sizeList = this.productDao.selectSize(pdCode); 
		ArrayList<ProductDTO> relatedPdList = this.productDao.selectRelatedProduct(pdCode);
		ArrayList<ProductColorDTO> diffColorList = this.productDao.diffColorProduct(pdCode); 
		ProductColorDTO colorDto = this.productDao.getPdColor(pdCode);
		ArrayList<ReviewDTO> revList= this.productDao.review(pdCode);
		ReviewDTO rDto = this.productDao.totalReview(pdCode);
		ArrayList<ProductQnaDTO> qnaList = this.productDao.selectProductQna(pdCode);
		
		// BigDecimal -> int, double
		rDto.setCount(Integer.valueOf(rDto.getCount()));
		rDto.setAvg(Double.valueOf(rDto.getAvg()));
		
		// BigDecimal -> int
		for (int i = 0; i < qnaList.size(); i++) {
			qnaList.get(i).setQnaPrivate(Integer.valueOf(qnaList.get(i).getQnaPrivate()));
		}
		
		// 리뷰 해시맵 생성
		LinkedHashMap<ReviewDTO, ArrayList<ReviewImgDTO>> revMap = new LinkedHashMap<ReviewDTO, ArrayList<ReviewImgDTO>>();
		for (int i = 0; i < revList.size(); i++) {
			ArrayList<ReviewImgDTO> revImgList = null;
			int revSeq = revList.get(i).getRevSeq();
			revImgList = this.productDao.reviewImg(revSeq);  
			revMap.put(revList.get(i), revImgList);
		}
		
		model.addAttribute("userCode", "M1");
		model.addAttribute("pdDto", pdDto);
		model.addAttribute("imgList", imgList);
		model.addAttribute("sizeList", sizeList);
		model.addAttribute("relatedPdList", relatedPdList);
		model.addAttribute("diffColorList", diffColorList);
		model.addAttribute("colorDto", colorDto);
		model.addAttribute("revMap", revMap);
		model.addAttribute("rDto", rDto);
		model.addAttribute("qnaList", qnaList);
		
		return "/WEB-INF/views/product/productDetail.jsp";

	}
	
	
	// 상품 크게보기(EXPAND VIEW)
	@RequestMapping("productExpandImage.action")
	public String expandImage(@RequestParam("pdCode") String pdCode, Model model) throws Exception {

		ProductDTO pdDto = this.productDao.selectProduct(pdCode);
		ArrayList<ProductImageDTO> imgList = this.productDao.selectImage(pdCode);
				
		model.addAttribute("pdDto", pdDto);
		model.addAttribute("imgList", imgList);
	      
	    return "/WEB-INF/views/product/productExpandImage.jsp";
	}


	// 재입고 알림 신청 페이지
	@RequestMapping(value={"warehousingAlarmApply.action"}, method=RequestMethod.GET)
	public String alarmApply(@RequestParam("pdCode") String pdCode, Model model) throws SQLException {

		ProductDTO pdDto = this.productDao.selectProduct(pdCode);
		ArrayList<ProductSizeDTO> sizeList = this.productDao.selectSize(pdCode);
		ArrayList<ProductColorDTO> diffColorList =this.productDao.diffColorProduct(pdCode);
		ProductColorDTO colorDto = this.productDao.getPdColor(pdCode);

		model.addAttribute("pdDto", pdDto);
		model.addAttribute("sizeList", sizeList);
		model.addAttribute("diffColorList", diffColorList);
		model.addAttribute("colorDto", colorDto);

		return "/WEB-INF/views/product/warehousingAlarmApply.jsp";

	}

	// 재입고 알림 신청 성공
	@RequestMapping(value={"warehousingAlarmComplete.action"}, method=RequestMethod.POST) 
	public String alarmComplete(Model model, RestockAlarmDTO alarmDto) throws Exception {

		alarmDto.setUserCode("M1");

		int count = this.productDao.alarmCount(alarmDto);
		if(count == 0) {
			this.productDao.applyAlarm(alarmDto);
		}
		
		model.addAttribute("alarmDto", alarmDto);
		
		return "/WEB-INF/views/product/warehousingAlarmComplete.jsp";
		
	}

	// 카드혜택 팝업창
	@RequestMapping("benefitCardPop.action")
	public String benefitCardPop() throws Exception {
		return "/WEB-INF/views/product/benefitCardPop.jsp";
	}
	
	// 성인 의류 사이즈
	@RequestMapping("clothesSize.action")
	public String clothesSize() throws Exception {
		return "/WEB-INF/views/product/clothesSize.jsp";
	}
	
	// 성인 신발 사이즈
	@RequestMapping("shoesPop.action")
	public String shoesPop() throws Exception {
		return "/WEB-INF/views/product/shoesPop.jsp";
	}
	
	// 성인 발볼 넓이
	@RequestMapping("widthGuidePop.action")
	public String widthGuidePop() throws Exception {
		return "/WEB-INF/views/product/widthGuidePop.jsp";
	}

}

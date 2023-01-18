package com.nb.rest.product.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nb.web.product.dao.IProductAjax;
import com.nb.web.product.dto.BuyProductDTO;
import com.nb.web.product.dto.ProductQnaDTO;
import com.nb.web.product.dto.WishlistDTO;

@RestController
@RequestMapping("/product/*")
public class RestProductController {

	@Autowired
	private IProductAjax productAjaxDao = null;
	
	// 생성자
	public RestProductController() {
	}

	// 관심상품 담기
	@RequestMapping(value={"wishList.json"}, method=RequestMethod.POST)
	public @ResponseBody String addWishList(HttpServletRequest request) throws Exception {
		String result = "";

		String[] wishCodeList = request.getParameterValues("wishCodeList[]");
		System.out.println(Arrays.toString(wishCodeList));
		String wishlist = Arrays.toString(wishCodeList).replace("[", "").replace("]","");

		WishlistDTO dto = new WishlistDTO(wishlist, "M1"); 

		int rowCount = this.productAjaxDao.addWishlist(dto);

		System.out.println(rowCount);
		if(rowCount >= 1) {
			result = "00";
		}else {
			result = "99";
		}

		return result;
	}
	
	// 장바구니 담기
	@RequestMapping(value={"addCart.json"}, method=RequestMethod.POST)
	public @ResponseBody String addCartList(HttpServletRequest request) throws Exception {
		String result = "";

		String pdCode = request.getParameter("pdCode");
		int pdPrice = Integer.parseInt(request.getParameter("pdPrice").replaceAll(",", ""));
		int sizeCode = Integer.parseInt(request.getParameter("sizeCode"));
		int pdAmount = Integer.parseInt(request.getParameter("pdAmount"));
		String pdColor = request.getParameter("pdColor");
		String pdImage = request.getParameter("pdImage");
		String pdName = request.getParameter("pdName");

		BuyProductDTO cartDto = new BuyProductDTO(pdCode, pdPrice, sizeCode, pdAmount, pdColor, pdImage, pdName);

		int rowCount = this.productAjaxDao.addCartList(cartDto, "M1");

		System.out.println(rowCount);
		if(rowCount >= 1) {
			result = "00";
		}else {
			result = "99";
		}

		return result;
	}
	
	
	// 문의 등록
	@RequestMapping(value={"addProductQna.json"}, method=RequestMethod.POST)
	public @ResponseBody String addProductQna(HttpServletRequest request) throws Exception {
		String result = "";
		
		String userCode = request.getParameter("userCode");
		String pdCode = request.getParameter("pdCode");
		String qnaTitle = request.getParameter("qnaTitle");
		String qnaContent = request.getParameter("qnaContent");
		String qnaFile = request.getParameter("qnaFile");
		int qnaPrivate = Integer.parseInt(request.getParameter("qnaPrivate"));

		ProductQnaDTO qnaDto = new ProductQnaDTO(userCode, pdCode, qnaTitle, qnaContent, qnaFile, qnaPrivate);

		int rowCount = this.productAjaxDao.addProductQna(qnaDto);

		System.out.println(rowCount);
		if(rowCount >= 1) {
			result = "00";
		}else {
			result = "99";
		}
		
		return result;
	}
}

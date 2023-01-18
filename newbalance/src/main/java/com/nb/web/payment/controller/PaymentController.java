package com.nb.web.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nb.web.product.dto.BuyProductDTO;

@Controller
@RequestMapping("/payment/*")
public class PaymentController {

		@RequestMapping(value={"order.action"}, method=RequestMethod.POST)
		public String buyProduct(BuyProductDTO buyDto, Model model) throws Exception {

			System.out.println(buyDto.toString());
			
			model.addAttribute("buyDto", buyDto);  

			return "/WEB-INF/views/payment/order.jsp";
		}
		
}

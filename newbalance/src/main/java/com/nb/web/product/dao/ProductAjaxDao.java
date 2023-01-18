package com.nb.web.product.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nb.web.product.dto.BuyProductDTO;
import com.nb.web.product.dto.ProductQnaDTO;
import com.nb.web.product.dto.WishlistDTO;

@Repository
public class ProductAjaxDao implements IProductAjax {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate; 

	// 관심상품 추가
	public int addWishlist(WishlistDTO dto) throws SQLException {

		int rowCount = 0;
		System.out.println("WishList dao 호출됨");

		String sql = "SELECT count(*) count "
				+ "FROM wishlist "
				+ "WHERE user_code = :userCode  and pd_code  in ( :wishlist ) ";
		
		/*
		String[] wishList = dto.getPdCode();
		String str = "";
		for (int i = 0; i < wishList.length; i++) {
			str += String.format("'%s'", wishList[i]) + (i != wishList.length - 1? ", " : ") ");		
		}
		sql += str;
		 */
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("userCode", dto.getUserCode());
		paramSource.addValue("wishlist", dto.getPdCode());
		
		System.out.println(sql);
		
		String[] wishList = dto.getPdCode().split(",");
		
		int count = jdbcTemplate.queryForObject(sql, paramSource, Integer.class);

		if(count == 0) {
			for(int j = 0; j < wishList.length; j++) {
				String sql2 = "INSERT INTO wishlist VALUES ( "
						+ " wish_code.nextval, :pdCode, :userCode, SYSDATE, ADD_MONTHS(SYSDATE, 1)) ";

				MapSqlParameterSource paramSource2 = new MapSqlParameterSource();
				paramSource2.addValue("pdCode", wishList[j]);
				paramSource2.addValue("userCode", dto.getUserCode());

				rowCount = jdbcTemplate.update(sql2, paramSource2);
			}
		} else {
			rowCount = 0;
		}
		return rowCount;
	}


	// 장바구니 추가
	public int addCartList(BuyProductDTO cartDto, String userCode) throws SQLException {
		
		int rowCount = 0;
		System.out.println("addCartList dao 호출됨");
		
		String sql = "SELECT COUNT(*) count "
				+ " FROM cart "
				+ " WHERE user_code = :userCode and pd_code = :pdCode and size_code = :sizeCode ";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userCode", userCode);
		paramMap.put("pdCode", cartDto.getPdCode());
		paramMap.put("sizeCode", cartDto.getSizeCode());
		
		int count = jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
		
		if(count == 0) {
				String sql2 = "INSERT INTO cart VALUES ( "
						+ " cart_num.nextval, :userCode, :pdCode, :sizeCode, :pdColor, :pdAmount, SYSDATE, ADD_MONTHS(SYSDATE, 1), :pdImage, :pdName, :pdPrice) ";

				Map<String, Object> paramMap2 = new HashMap<String, Object>();
				paramMap2.put("userCode", userCode);
				paramMap2.put("pdCode", cartDto.getPdCode());
				paramMap2.put("sizeCode", cartDto.getSizeCode());
				paramMap2.put("pdColor", cartDto.getPdColor());
				paramMap2.put("pdAmount", cartDto.getPdAmount());
				paramMap2.put("pdImage", cartDto.getPdImage());
				paramMap2.put("pdName", cartDto.getPdName());
				paramMap2.put("pdPrice", cartDto.getPdPrice());

				rowCount = jdbcTemplate.update(sql2, paramMap2);
				
		}else {
				String sql2 = "UPDATE cart "
						+ "SET cart_count = cart_count + :pdAmount "
						+ "    , cart_insertdate = SYSDATE "
						+ "    , cart_expiredate = ADD_MONTHS(SYSDATE, 1) "
						+ "WHERE user_code = :userCode and pd_code = :pdCode and size_code = :sizeCode ";
				
				Map<String, Object> paramMap2 = new HashMap<String, Object>();
				paramMap2.put("pdAmount", cartDto.getPdAmount());
				paramMap2.put("userCode", userCode);
				paramMap2.put("pdCode", cartDto.getPdCode());
				paramMap2.put("sizeCode", cartDto.getSizeCode());
				
				rowCount = jdbcTemplate.update(sql2, paramMap2);		
		}

		return rowCount;
	}

	// 상품문의 등록
	public int addProductQna(ProductQnaDTO qnaDto) throws SQLException {
		
		String sql = "INSERT INTO qna "
				+ "VALUES (qna_code.nextval, :userCode, 3, :pdCode, null, SYSDATE, '접수', null"
				+ ", :qnaTitle, :qnaContent, :qnaFile, :qnaPrivate, null)";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userCode", qnaDto.getUserCode());
		paramMap.put("pdCode", qnaDto.getPdCode());
		paramMap.put("qnaTitle", qnaDto.getQnaTitle());
		paramMap.put("qnaContent", qnaDto.getQnaContent());
		paramMap.put("qnaFile", qnaDto.getQnaFile());
		paramMap.put("qnaPrivate", qnaDto.getQnaPrivate());

		int rowCount = jdbcTemplate.update(sql, paramMap);

		return rowCount;
	}



}

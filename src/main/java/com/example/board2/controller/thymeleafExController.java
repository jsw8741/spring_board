package com.example.board2.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board2.dto.ItemDto;

@Controller // 1. controller 역할 클래스, 2. bean 객체가 된다
@RequestMapping(value = "/thymeleaf")
public class thymeleafExController {

	@GetMapping(value = "/ex01")
	public String thymeleafEx01(Model model) {
		model.addAttribute("data", "타임리프 예제입니다.");

		return "thymeleafEx/thymeleafEx01";
	}

	@GetMapping(value = "/ex02")
	public String thymeleafEx02(Model model) {
		ItemDto itemDto = new ItemDto();
		itemDto.setItemDetail("상품 상세 설명");
		itemDto.setItemNm("test 상품1");
		itemDto.setPrice(10000);
		itemDto.setRegTime(LocalDateTime.now());

		model.addAttribute("itemDto", itemDto);
		return "thymeleafEx/thymeleafEx02";
	}

	@GetMapping(value = "/ex03")
	public String thymeleafEx03(Model model) {
		List<ItemDto> itemDtoList = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			ItemDto itemDto = new ItemDto();
			itemDto.setItemDetail("상품 상세 설명" + i);
			itemDto.setItemNm("test 상품" + i);
			itemDto.setPrice(10000 * i);
			itemDto.setRegTime(LocalDateTime.now());

			itemDtoList.add(itemDto);
		}

		model.addAttribute("itemDtoList", itemDtoList);
		return "thymeleafEx/thymeleafEx03";
	}

	@GetMapping(value = "/ex04")
	public String thymeleafEx04(Model model) {
		List<ItemDto> itemDtoList = new ArrayList<>();

		for (int i = 1; i <= 10; i++) {
			ItemDto itemDto = new ItemDto();
			itemDto.setItemDetail("상품 상세 설명" + i);
			itemDto.setItemNm("test 상품" + i);
			itemDto.setPrice(10000 * i);
			itemDto.setRegTime(LocalDateTime.now());

			itemDtoList.add(itemDto);
		}

		model.addAttribute("itemDtoList", itemDtoList);

		return "thymeleafEx/thymeleafEx04";
	}
	
	@GetMapping(value = "/ex05")
	public String thymeleafEx05() {
		return "thymeleafEx/thymeleafEx05";
	}
	
	@GetMapping(value = "/ex06")
	public String thymeleafEx06(Model model, String param1, String param2) {
		System.out.println(param1+ "   " + param2);
		
		model.addAttribute("param1", param1);
		model.addAttribute("param2", param2);
		return "thymeleafEx/thymeleafEx06";
	}
	
	@GetMapping(value = "/ex07")
	public String thymeleafEx07() {
		return "thymeleafEx/thymeleafEx07";
	}
}

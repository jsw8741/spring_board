package com.example.board2.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.board2.dto.Board;
import com.example.board2.service.BoardService;
import com.example.board2.util.MyUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	MyUtil myUtil;
	
	@RequestMapping(value = "/") // localhost로 접속
	public String index() {
		return "index";
	}
	
	// Get 방식으로 Request 들어올 때
	@RequestMapping(value = "/created", method = RequestMethod.GET)
	public String created() {
		return "bbs/created";
	}
	
	// 게시물 등록(Post로 Request 들어올 때)
	@RequestMapping(value = "/created", method = RequestMethod.POST)
	public String createdOk(Board board, HttpServletRequest request, Model model) {
		try {
			int maxNum = boardService.maxNum();
			
			board.setNum(maxNum + 1);
			board.setIpAddr(request.getRemoteAddr()); // 클라이언트의 IP 주소를 구해준다.
			
			boardService.insertData(board);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/list";
	}
	
	// 게시글 목록 보여주기(Get, Post 방식 전부 여기로 들어온다.)
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Board board, HttpServletRequest request, Model model) {
		
		try {
			
			String pageNum = request.getParameter("pageNum"); // 바뀌는 페이지 번호
			int currentPage = 1; // 현재 페이지 번호(디폴트는 1)
			
			if(pageNum != null) currentPage = Integer.parseInt(pageNum);
			String searchKey = request.getParameter("searchKey"); // 검색 키워드(subject, name, content)
			String searchValue = request.getParameter("searchValue");
			
			if(searchValue == null) {
				searchKey = "subject"; // 검색 키워드의 디폴트는 subject
				searchValue = "";	// 검색어의 디폴트는 빈문자열
			}else {
				if(request.getMethod().equalsIgnoreCase("GET")) {
					// get 방식으로 request가 왔다면
					// 관리 파라메타의 값(searchValue)을 디코딩(사람이 알 수 있게 변환)해준다.
					searchValue = URLDecoder.decode(searchValue, "UTF-8");
					
				}
			}
			// 1. 전체 게시물의 갯수를 가져온다.(페이징 처리에 필요)
			int dataCount = boardService.getDataCount(searchKey, searchValue);
			
			// 2. 페이징 처리를한다.(준비 단계)
			int numPerPage = 5; // 페이지당 보여둘 게시글의 갯수(5개)
			int totalPage = myUtil.getPageCount(numPerPage, dataCount); // 페이지의 전체 갯수
			
			if(currentPage > totalPage) currentPage = totalPage; // totalPage 보다 크면 안됨
				
			int start = (currentPage -1) * numPerPage + 1; // 1, 6, 11, 16 ....
			int end = currentPage * numPerPage; // 5, 10, 15, 20 ....
			
			// 3. 전체 게시물을 가져온다.
			List<Board> lists = boardService.getLists(searchKey, searchValue, start, end);
						
			// 4. 페이징 처리를한다.
			String param = "";
			
			if(searchValue != null && searchValue.equals("")) {
				// searchValue에 검색어가 있으면
				param = "searchKey=" + searchKey;
				param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); // 컴퓨터의 언어로 인코딩
			}
			
			String listUrl = "/list";
			
			// list?searchKey=name&searchValue=춘식
			if(!param.equals("")) listUrl += "?" + param;
			
			String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "bbs/list";
	}
	
	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public String article() {
		return "bbs/article";
	}
	
	// 수정 페이지를 보여줌
	@RequestMapping(value = "/updated", method = RequestMethod.GET)
	public String updated(HttpServletRequest request, Model model) {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue != null) {
			
		}
		
		return "bbs/updated";
	}
	
	// 게시물 수정
	@RequestMapping(value = "/updated_ok", method = RequestMethod.POST)
	public String updated_ok() {
		
		return "redirect:/list";
	}
	
}

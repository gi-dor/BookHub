package com.example.bookhub.main.controller;


import com.example.bookhub.main.dto.BookListDto;
import com.example.bookhub.main.dto.SearchCriteria;
import com.example.bookhub.main.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class SearchController {
    private final SearchService searchService;

    //  출시일 순 검색 했을때
    @GetMapping("/search")
    public String searchBooks(SearchCriteria searchCriteria, Model model) {;
        BookListDto dto = searchService.searchBooks(searchCriteria);
        model.addAttribute("book", dto.getBooks()); // 모델에 책 정보를 담아서 HTML로 전달.
        model.addAttribute("criteria", dto.getCriteria());
        return "main/searchList"; // searchList.html로 반환
    }

}


    /*
    // 검색 페이지로 이동 및 검색 수행
    @GetMapping("/search")
    public String searchBooks(SearchCriteria searchCriteria, Model model) {
        // 검색어가 있을 경우에만 실시간 검색어 랭킹 업데이트
        if (searchCriteria.getKeyword() != null && !searchCriteria.getKeyword().isEmpty()) {
            searchService.updateSearchRanking();
        }

        // 검색 수행
        BookListDto dto = searchService.keywordSearch(searchCriteria);
        model.addAttribute("book", dto.getBooks()); // 모델에 책 정보를 담아서 HTML로 전달.
        model.addAttribute("criteria", dto.getCriteria());
        return "main/searchList"; // searchList.html로 반환
    }

*/
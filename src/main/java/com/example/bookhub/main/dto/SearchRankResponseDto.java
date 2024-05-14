//package com.example.bookhub.main.dto;
//
//import lombok.Data;
//import org.springframework.data.redis.core.ZSetOperations;
//
//@Data
//public class SearchRankResponseDto {
//    private String keyword;
//    private double rank;
//
//    public SearchRankResponseDto(String keyword, double rank) {
//        this.keyword = keyword;
//        this.rank = rank;
//    }
//
//    public static SearchRankResponseDto convertToResponseRankingDto(ZSetOperations.TypedTuple<String> typedTuple) {
//        return new SearchRankResponseDto(typedTuple.getValue(), typedTuple.getScore());
//    }
//
//}//
package com.example.bookhub.product.service;

import com.example.bookhub.product.dto.CartBookDto;
import com.example.bookhub.product.mapper.BookMapper;
import com.example.bookhub.product.mapper.CartMapper;
import com.example.bookhub.user.mapper.UserMapper;
import com.example.bookhub.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    public List<CartBookDto> findCartList(String userId){
        User user = userMapper.selectUserById(userId);
        return cartMapper.findCartList(user.getNo());
    }

    public void deleteBookByCartNo(long cartNo){
        cartMapper.deleteBookByCartNo(cartNo);
    }

    public void updateBookCountByCartNo(long cartNo, int count) {
        Map<String, Object> map = new HashMap<>();
        map.put("cartNo", cartNo);
        map.put("count", count);
        cartMapper.updateBookCountByCartNo(map);
    }

    public String createCart(long bookNo, int count, String userId) {
        User user = userMapper.selectUserById(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("bookNo", bookNo);
        map.put("userNo", user.getNo());
        map.put("count", count);
        Optional<CartBookDto> optional = cartMapper.getCartByBookNoAndUserNo(map);
        int stock = bookMapper.checkStock(bookNo);
        if(optional.isEmpty()){
            if(stock >= count) {
                cartMapper.createCart(map);
                return "new";
            }
            else
                return "fail";
        }
        else {
            CartBookDto cart = optional.get();
            if(stock - cart.getCount() - count >= 0) {
                cartMapper.increaseBookCountByCartNo(cart.getCartNo(), count);
                return "exist";
            }
            else{
                return "fail";
            }
        }
    }

    public void addCart(List<Long> bookNoList, String userId) {
        User user = userMapper.selectUserById(userId);

        for (Long bookNo : bookNoList) {
            createCart(bookNo, 1, userId);
        }

    }
}

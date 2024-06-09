package com.example.bookhub.product.service;

import com.example.bookhub.product.dto.BookDto;
import com.example.bookhub.product.dto.BuyForm;
import com.example.bookhub.product.exception.BookHubException;
import com.example.bookhub.product.mapper.BookMapper;
import com.example.bookhub.product.mapper.BuyMapper;
import com.example.bookhub.product.vo.*;
import com.example.bookhub.user.mapper.UserMapper;
import com.example.bookhub.user.vo.User;
import com.example.bookhub.user.vo.UserDelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BuyService {

    private final UserMapper userMapper;
    private final BuyMapper buyMapper;
    private final BookMapper bookMapper;
    private BuyForm buyForm;

    public List<CouponProduced> getCouponsByUserNo(String userId) {
        User user = userMapper.selectUserById(userId);
        return buyMapper.getCouponsByUserNo(user.getNo());
    }

    public int getPointByUserNo(String userId) {
        User user = userMapper.selectUserById(userId);
        return buyMapper.getPointByUserNo(user.getNo());
    }

    @Transactional
    public void createBuy(BuyForm buyForm, String tid, String userId) {
        this.buyForm = buyForm;
        User user = userMapper.selectUserById(userId);

        // 재고 감소
        updateBookStock(buyForm);

        // BUY 테이블
        long generatedNo = insertBuy(tid, user);

        // BUYBOOK 테이블
        insertBuyBook(generatedNo);

        // COUPON_USED 테이블, COUPON_PRODUCED 테이블
        insertAndUpdateCoupon(generatedNo);

        // 포인트 차감
        updatePoint(user);

        // 포인트 적립
        accumulatePoint(user);
    }

    public long insertBuy(String tid, User user){
        UserDelivery userDelivery = new UserDelivery();
        userDelivery.setNo(buyForm.getUserDeliveryNo());

        BuyDeliveryRequest buyDeliveryRequest = new BuyDeliveryRequest();
        buyDeliveryRequest.setBuyDeliveryRequestNo(buyForm.getBuyDeliveryRequestNo());

        BuyPayMethod buyPayMethod = new BuyPayMethod();
        buyPayMethod.setBuyPayMethodNo(buyForm.getBuyPayMethodNo());

        Buy buy = Buy.builder()
                .totalPrice(buyForm.getTotalPrice())
                .totalBookDiscountPrice(buyForm.getTotalBookDiscountPrice())
                .totalCouponDiscountAmount(buyForm.getTotalCouponDiscountAmount())
                .totalPointUseAmount(buyForm.getTotalPointUseAmount())
                .pointAccumulationAmount(buyForm.getPointAccumulationAmount())
                .finalPrice(buyForm.getFinalPrice())
                .commonEntranceApproach(buyForm.getCommonEntranceApproach())
                .giftYn("N")
                .userDelivery(userDelivery)
                .buyDeliveryRequest(buyDeliveryRequest)
                .buyPayMethod(buyPayMethod)
                .build();

        buy.setOrderId(tid);

        buy.setUser(user);

        buyMapper.createBuy(buy);
        return buy.getBuyNo();
    }

    public void insertBuyBook(long generatedNo){
        for(int i = 0; i < buyForm.getBuyBookNoList().size(); i++){
            long bookNo = buyForm.getBuyBookNoList().get(i);
            int count = buyForm.getBuyBookCountList().get(i);

            BuyBook buyBook = BuyBook.builder()
                    .bookNo(bookNo)
                    .count(count)
                    .build();

            buyBook.setBuyNo(generatedNo);

            buyMapper.createBuyBook(buyBook);
            //buyMapper.updateBookStock(bookNo, count);
        }
    }

    public void insertAndUpdateCoupon(long generatedNo){
        if(buyForm.getCouponProducedNoList() != null) {
            for (int i = 0; i < buyForm.getCouponProducedNoList().size(); i++) {
                long couponProducedNo = buyForm.getCouponProducedNoList().get(i);
                int couponDiscountAmount = buyForm.getCouponDiscountAmountList().get(i);

                CouponUsed couponUsed = new CouponUsed();
                couponUsed.setBuyNo(generatedNo);
                couponUsed.setCouponProducedNo(couponProducedNo);
                couponUsed.setDiscountAmount(couponDiscountAmount);

                buyMapper.createCouponUsed(couponUsed);

                int lastAmount = buyMapper.getCouponProducedLastAmount(couponProducedNo);
                if(lastAmount > couponDiscountAmount)
                    buyMapper.updateCouponProducedUsed(couponProducedNo, couponDiscountAmount, "N");
                else
                    buyMapper.updateCouponProducedUsed(couponProducedNo, couponDiscountAmount, "Y");
            }
        }
    }

    public void updatePoint(User user){
        int totalPointUseAmount = buyForm.getTotalPointUseAmount();
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", user.getNo());
        map.put("totalPointUseAmount", totalPointUseAmount);
        buyMapper.updatePointUsed(map);
    }

    private void accumulatePoint(User user) {
        int pointAccumulationAmount = buyForm.getPointAccumulationAmount();
        buyMapper.updatePointAccumulated(user.getNo(), pointAccumulationAmount);
    }

    public List<UserDelivery> getUserDeliveryByUserNo(String userId) {
        User user = userMapper.selectUserById(userId);

        return buyMapper.getUserDeliveryByUserNo(user.getNo());
    }

    public List<BuyDeliveryRequest> getBuyDeliveryRequest() {
        return buyMapper.getBuyDeliveryRequest();
    }

    public void updateDefaultUserDelivery(long selectedUserDeliveryNo) {
        buyMapper.updateDefaultUserDeliveryN(selectedUserDeliveryNo);
        buyMapper.updateDefaultUserDeliveryY(selectedUserDeliveryNo);
    }

    public UserDelivery createUserDelivery(String userId, UserDelivery userDelivery) {
        User user = userMapper.selectUserById(userId);

        userDelivery.setUser(user);

        buyMapper.createUserDelivery(userDelivery);
        return userDelivery;
    }

    public String getBuyerYn(long bookNo, String userId) {
        long userNo = 0;
        if(!"guest".equals(userId)) {
            User user = userMapper.selectUserById(userId);
            userNo = user.getNo();
        }
        String buyerYn = buyMapper.getBuyerYn(bookNo, userNo);

        return buyerYn;
    }

    @Transactional
    public void updateBookStock(BuyForm buyForm){
        for(int i = 0; i < buyForm.getBuyBookNoList().size(); i++) {
            long bookNo = buyForm.getBuyBookNoList().get(i);
            int count = buyForm.getBuyBookCountList().get(i);

            BookDto book = bookMapper.getBookByBookNo(bookNo);
            if(book.getStock() >= count){
                buyMapper.updateBookStock(bookNo, count);
                int updatedStock = buyMapper.getBookStock(bookNo);
                if(updatedStock == 0){
                    bookMapper.updateBookStatus(bookNo);
                }
            }
            else {
                throw new BookHubException("재고가 부족하여 주문이 취소되었습니다");
            }
        }
    }

    public int getCouponCountByUserNo(String userId) {
        User user = userMapper.selectUserById(userId);
        return buyMapper.getCouponCountByUserNo(user.getNo());
    }

}

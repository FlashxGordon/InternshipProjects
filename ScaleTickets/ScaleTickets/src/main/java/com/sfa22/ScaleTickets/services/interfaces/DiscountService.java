package com.sfa22.ScaleTickets.services.interfaces;


import com.sfa22.ScaleTickets.dtos.DiscountDto;
import com.sfa22.ScaleTickets.dtos.DiscountUpdateDto;

import java.time.LocalDate;
import java.util.List;


public interface DiscountService {

    DiscountDto getDiscountById(int id);

    List<DiscountDto> getAllDiscounts();

    List<DiscountDto> getDiscountByExpirationDateAfterAndEqual(LocalDate expirationDate);

    List<DiscountDto> getDiscountByExpirationDateBefore(LocalDate expirationDate);

    DiscountDto getDiscountByCode(String code);

    DiscountDto getDiscountByCodeAndExpirationDateAfterAndEqual(String code, LocalDate expirationDate);

    int addNewDiscount(DiscountDto discountDto);

    void updateDiscountExpirationDateFoundByCode(String code, DiscountUpdateDto discountDto);
}
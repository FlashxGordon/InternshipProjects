package com.sfa22.ScaleTickets.services;

import com.sfa22.ScaleTickets.data.DiscountCodeRepository;
import com.sfa22.ScaleTickets.dtos.DiscountDto;
import com.sfa22.ScaleTickets.dtos.DiscountUpdateDto;
import com.sfa22.ScaleTickets.entities.Discount;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.DiscountMapper;
import com.sfa22.ScaleTickets.services.implementations.DiscountServiceImpl;
import com.sfa22.ScaleTickets.validations.DiscountValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceImplTests {

    @Mock
    DiscountCodeRepository discountCodeRepository;

    @Spy
    DiscountValidation discountValidation;

    @Spy
    DiscountMapper discountMapper;

    @InjectMocks
    DiscountServiceImpl discountService;

    private int discountID;

    private int percentage;

    private String code;

    private String incorrectCode;

    private LocalDate expirationDate;

    private LocalDate newDate;

    private List<Discount> testDiscounts;

    Discount discount;

    Discount testDiscount;

    DiscountDto discountDto;

    @BeforeEach
    public void setUp() {

        discountID = 1;

        percentage = 5;

        code = "summer25";

        incorrectCode = "SuM3R1S5";

        expirationDate = LocalDate.of(2020, 1, 8);

        newDate = LocalDate.of(2022, 5, 5);

        discount = new Discount(discountID, percentage, code, expirationDate);

        discountDto = new DiscountDto(discountID, percentage, code, expirationDate);

        testDiscount = new Discount(discountID, percentage, code, newDate);

        testDiscounts = new ArrayList<>();

        testDiscounts.add(discount);
    }

    @Test
    void getDiscountById_correctId_okay() {
        when(discountCodeRepository.findById(1)).thenReturn(Optional.ofNullable(discount));

        DiscountDto result = discountService.getDiscountById(1);

        assertEquals(discountDto, result);
    }

    @Test
    void getAllDiscounts_doesItReturnAllDiscounts_okay() {

        when(discountCodeRepository.findAll()).thenReturn(testDiscounts);

        List<DiscountDto> listOfDiscountDto = discountService.getAllDiscounts();

        assertEquals(listOfDiscountDto.size(), testDiscounts.size());
    }

    @Test
    void getDiscountByExpirationDateAfterAndEqual_doesItReturnDiscountsAfterAndEqualExpirationDate_okay() {

        when(discountCodeRepository.findByExpirationDateGreaterThanEqual(expirationDate)).thenReturn(testDiscounts);

        List<DiscountDto> listOfDiscountDto = discountService.getDiscountByExpirationDateAfterAndEqual(expirationDate);

        assertEquals(listOfDiscountDto.size(), testDiscounts.size());
    }

    @Test
    void getDiscountByCodeAndExpirationDateAfterAndEqual_doesItThrowResourceMissingException_okay() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                        discountService.getDiscountByCodeAndExpirationDateAfterAndEqual(code, expirationDate),
                "Discount code was not Found\", \"Discount");

        assertTrue(runtimeException.getMessage().contains("was not Found"));
    }


    @Test
    void getDiscountByExpirationDateBefore_doesItReturnDiscountsByExpirationDateBefore_okay() {

        when(discountCodeRepository.findByExpirationDateLessThan
                (expirationDate.plusDays(1))).thenReturn(testDiscounts);

        List<DiscountDto> listOfDiscountDto =
                discountService.getDiscountByExpirationDateBefore(expirationDate.plusDays(1));

        assertEquals(listOfDiscountDto.size(), listOfDiscountDto.size());
    }

    @Test
    void getDiscountByCode_doesItReturnDiscountByCode_okay() {

        when(discountCodeRepository.findByCode(code)).thenReturn(Optional.ofNullable(discount));

        DiscountDto discountDto = discountService.getDiscountByCode(code);

        assertNotNull(discountDto);
    }

    @Test
    void getDiscountByCode_doesItThrowResourceMissingException_okay() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                discountService.getDiscountByCode(code), "Discount code was not Found\", \"Discount");

        assertTrue(runtimeException.getMessage().contains("was not Found"));
    }

    @Test
    void getDiscountByCode_doesItThrowInvalidUserInputException_okay() {

        Throwable runtimeException = assertThrows(InvalidUserInputException.class, () ->
                discountService.getDiscountByCode(incorrectCode), "Incorrect Discount code Format");

        assertTrue(runtimeException.getMessage().contains("Incorrect Discount"));
    }

    @Test
    void getDiscountByCodeAndExpirationDateAfterAndEqual_doesItReturnDiscountByCodeAndExpirationDateAfterAndEqual() {

        when(discountCodeRepository.findByCodeAndExpirationDateGreaterThanEqual
                (code, expirationDate)).thenReturn(Optional.ofNullable(discount));

        DiscountDto discountDto =
                discountService.getDiscountByCodeAndExpirationDateAfterAndEqual(code, expirationDate);

        assertNotNull(discountDto);
    }

    @Test
    void updateDiscountExpirationDateFoundByCode_doesItReturnDiscountByCodeAndUpdateExpirationDate_okay() {

        when(discountCodeRepository.findByCode(code)).thenReturn(Optional.ofNullable(discount));

        discountService.updateDiscountExpirationDateFoundByCode(code, new DiscountUpdateDto(newDate));

        verify(discountCodeRepository).save(testDiscount);
    }

    @Test
    void updateDiscountExpirationDateFoundByCode_doesItThrowResourceMissingException_okay() {

        Throwable runtimeException = assertThrows(ResourceMissingException.class, () ->
                        discountService.updateDiscountExpirationDateFoundByCode(code, new DiscountUpdateDto(newDate)),
                "Discount code was not Found\", \"Discount");

        assertTrue(runtimeException.getMessage().contains("was not Found"));
    }

    @Test
    void addNewDiscount_doesItAddNewDiscount_okay() {

        when(discountCodeRepository.save(discount)).thenReturn(discount);

        DiscountDto discountDto = discountMapper.mapDiscountToDiscountDto(discount);

        discountService.addNewDiscount(discountDto);

        verify(discountCodeRepository).save(discount);
    }
}
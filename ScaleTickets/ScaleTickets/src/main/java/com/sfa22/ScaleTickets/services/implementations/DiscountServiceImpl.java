package com.sfa22.ScaleTickets.services.implementations;

import com.sfa22.ScaleTickets.data.DiscountCodeRepository;
import com.sfa22.ScaleTickets.dtos.DiscountDto;
import com.sfa22.ScaleTickets.dtos.DiscountUpdateDto;
import com.sfa22.ScaleTickets.entities.Discount;
import com.sfa22.ScaleTickets.exceptions.AlreadyExistsException;
import com.sfa22.ScaleTickets.exceptions.InvalidUserInputException;
import com.sfa22.ScaleTickets.exceptions.ResourceMissingException;
import com.sfa22.ScaleTickets.mappers.DiscountMapper;
import com.sfa22.ScaleTickets.services.interfaces.DiscountService;
import com.sfa22.ScaleTickets.validations.DiscountValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountCodeRepository discountCodeRepository;

    private final DiscountMapper discountMapper;

    private final DiscountValidation discountValidation;

    public DiscountServiceImpl(DiscountCodeRepository discountCodeRepository, DiscountMapper discountMapper,
                               DiscountValidation discountValidation) {

        this.discountCodeRepository = discountCodeRepository;
        this.discountMapper = discountMapper;
        this.discountValidation = discountValidation;
    }

    @Override
    public DiscountDto getDiscountById(int id) {
        return discountMapper.mapDiscountToDiscountDto(discountCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceMissingException("Discount not found", "Discount")));
    }

    @Override
    public List<DiscountDto> getAllDiscounts() {

        List<Discount> allDiscounts = discountCodeRepository.findAll();

        return discountMapper.ListOfDiscountToDiscountDto(allDiscounts);
    }

    @Override
    public List<DiscountDto> getDiscountByExpirationDateAfterAndEqual(LocalDate expirationDate) {

        List<Discount> searchedDiscounts = discountCodeRepository.findByExpirationDateGreaterThanEqual(expirationDate);

        return discountMapper.ListOfDiscountToDiscountDto(searchedDiscounts);
    }

    @Override
    public List<DiscountDto> getDiscountByExpirationDateBefore(LocalDate expirationDate) {

        List<Discount> searchedDiscounts = discountCodeRepository.findByExpirationDateLessThan(expirationDate);

        return discountMapper.ListOfDiscountToDiscountDto(searchedDiscounts);
    }

    @Override
    public DiscountDto getDiscountByCode(String code) {

        isDiscountCodeValid(code);

        Optional<Discount> searchedDiscount = discountCodeRepository.findByCode(code);

        return discountMapper.mapDiscountToDiscountDto(searchedDiscount.orElseThrow(() ->
                new ResourceMissingException("Discount code was not Found", "Discount")));
    }

    @Override
    public DiscountDto getDiscountByCodeAndExpirationDateAfterAndEqual(String code, LocalDate expirationDate) {

        isDiscountCodeValid(code);

        Optional<Discount> searchedDiscount =
                discountCodeRepository.findByCodeAndExpirationDateGreaterThanEqual(code, expirationDate);

        return discountMapper.mapDiscountToDiscountDto(searchedDiscount.orElseThrow(() ->
                new ResourceMissingException("Discount code was not Found", "Discount")));
    }

    @Override
    public int addNewDiscount(DiscountDto newDiscountDto) {

        doesDiscountCodeExist(newDiscountDto.getCode(), discountCodeRepository.findAll());

        isDiscountCodeValid(newDiscountDto.getCode());

        Discount discountToAdd = discountMapper.mapDiscountDtoToDiscount(newDiscountDto);

        Discount newDiscount = discountCodeRepository.save(discountToAdd);

        return newDiscount.getDiscountId();
    }

    @Override
    public void updateDiscountExpirationDateFoundByCode(String code, DiscountUpdateDto discountDto) {

        isDiscountCodeValid(code);

        Optional<Discount> discountToUpdate = discountCodeRepository.findByCode(code);

        Discount discount = discountToUpdate.orElseThrow(() ->
                new ResourceMissingException("Discount code was not Found", "Discount"));

        discount.setExpirationDate(discountDto.getExpirationDate());

        discountCodeRepository.save(discount);
    }

    private void isDiscountCodeValid(String code) {

        if (!discountValidation.isValid(code)) {

            throw new InvalidUserInputException("Incorrect Discount code Format");
        }
    }

    private void doesDiscountCodeExist(String code, List<Discount> allDiscounts) {

            if (discountValidation.doesDiscountCodeExist(code,allDiscounts)) {
                throw new AlreadyExistsException("Discount code already exists.");
        }
    }
}
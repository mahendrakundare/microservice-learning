package com.mk.accounts.service.impl;

import com.mk.accounts.dto.AccountsDto;
import com.mk.accounts.dto.CardsDto;
import com.mk.accounts.dto.CustomerDetailsDto;
import com.mk.accounts.dto.LoansDto;
import com.mk.accounts.entity.Accounts;
import com.mk.accounts.entity.Customer;
import com.mk.accounts.exception.ResourceNotFoundException;
import com.mk.accounts.mapper.AccountsMapper;
import com.mk.accounts.mapper.CustomerMapper;
import com.mk.accounts.repository.AccountsRepository;
import com.mk.accounts.repository.CustomerRepository;
import com.mk.accounts.service.ICustomerService;
import com.mk.accounts.service.client.CardsFeignClient;
import com.mk.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Qualifier("CardsFeignClient")
    private CardsFeignClient cardsFeignClient;
    @Qualifier("LoansFeignClient")
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber  - Input Mobile Number
     * @param correlationId
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber, correlationId);
        if (null != loansDtoResponseEntity) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber, correlationId);
        if (null != cardsDtoResponseEntity) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }

        return customerDetailsDto;
    }
}

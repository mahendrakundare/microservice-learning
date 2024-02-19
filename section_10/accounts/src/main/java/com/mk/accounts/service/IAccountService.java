package com.mk.accounts.service;

import com.mk.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     *
     * @param customerDto - CustomerDto object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Account Details based on a given mobile number
     */
    CustomerDto fetchAccountDetails(String mobileNumber);

    /**
     *
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);

}

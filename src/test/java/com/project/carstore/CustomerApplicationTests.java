package com.project.carstore;

import com.project.carstore.cart.CartException;
import com.project.carstore.customer.*;
import com.project.carstore.exceptions.CustomerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerApplicationTests {
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CustomerController customerController;

    @Test
    @DisplayName(value = "adding customer")
    public void addCustomerTest_validCustomer() throws CustomerException, CartException {
        CustomerDto newcustomerDto = new CustomerDto("padma", "Sri", "padma@gmail.com", "12345", 1023456789L);
        Customer savedCustomer = customerService.AddCustomerToDb(newcustomerDto);
        Assertions.assertNotNull(savedCustomer);
    }

    @Test
    @DisplayName(value = "adding null customer")
    public void addCustomerTest_NullCustomer() throws CustomerException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            customerService.AddCustomerToDb(null);
        });
    }

    @Test
    @DisplayName(value = "deleting customer with valid id")
    public void deleteCustomerTest_ValidId() throws CustomerException, CartException {
        CustomerDto customerDto = new CustomerDto("padma", "Sri", "padma@gmail.com", "12345", 90234L);
        Customer savedCustomer = customerService.AddCustomerToDb(customerDto);
        String deletedCustomer = customerService.DeleteCustomerById(savedCustomer.getId());
        Assertions.assertNull(customerRepository.findById(savedCustomer.getId()).orElse(null));
        Assertions.assertNotNull(deletedCustomer);
    }

    @Test
    @DisplayName(value = "updating null email value to customer")
    public void updateCustomerEmailTest_NullCustomer() throws CustomerException {
        CustomerDto newcustomerDto = new CustomerDto("padma", "Sri", "padma@gmail.com", "12345", 1023456789L);
        ReflectionTestUtils.setField(newcustomerDto, "email", null);
        assertNull(newcustomerDto.getEmail());
    }

    @Test
    @DisplayName(value = "updating null password value to customer")
    public void updateCustomerPwdTest_NullCustomer() throws CustomerException {
        CustomerDto newcustomerDto = new CustomerDto("padma", "Sri", "padma@gmail.com", "12345", 1023456789L);
        ReflectionTestUtils.setField(newcustomerDto, "password", null);
        assertNull(newcustomerDto.getPassword());
    }

    @Test
    @DisplayName(value = "getting all customer")
    public void getAllCustomersTest() throws CustomerException, CartException {
        CustomerDto customerDto1 = new CustomerDto("padma", "Sri", "padma@gmail.com", "12345", 1023456789L);
        CustomerDto customerDto2 = new CustomerDto("niranjana", "sidharthan", "niru@gmail.com", "98765", 7734567312L);
        customerService.AddCustomerToDb(customerDto1);
        customerService.AddCustomerToDb(customerDto2);
        List<Customer> customers = customerService.GetAllCustomers();
        Assertions.assertNotNull(customers);
        Assertions.assertFalse(customers.isEmpty());
    }

    @Test
    @DisplayName(value = "getting customer by id")
    public void getCustomerByIdTest () throws CustomerException, CartException {
        Integer id=2;
        CustomerDto customerDto1 = new CustomerDto("padma", "Sri", "padma@gmail.com", "12345", 1023456789L);
        CustomerDto customerDto2 = new CustomerDto("niranjana", "sidharthan", "niru@gmail.com", "98765", 7734567312L);
        customerService.AddCustomerToDb(customerDto1);
        customerService.AddCustomerToDb(customerDto2);
        Optional<Customer> customers = customerService.getCustomerById(id);
    }

    @Test
    @DisplayName(value = "getting customer by email")
    public void getCustomerByEmailTest () throws CustomerException, CartException {
        String email="padma1@gmail.com";
        CustomerDto customerDto1 = new CustomerDto("padma", "Sri", "padma1@gmail.com", "12345", 1023456789L);
        CustomerDto customerDto2 = new CustomerDto("niranjana", "sidharthan", "niru@gmail.com", "98765", 7734567312L);
        customerService.AddCustomerToDb(customerDto1);
        customerService.AddCustomerToDb(customerDto2);
        List<Customer> customers = Collections.singletonList(customerService.GetCustomerByEmail(email));
    }

    @Test
    @DisplayName(value = "getting customer by mobile no")
    public void getCustomerByMobileNoTest () throws CustomerException, CartException {
        Long no=7734563312L;
        CustomerDto customerDto1 = new CustomerDto("padma", "Sri", "padma1@gmail.com", "12345", 1023456789L);
        CustomerDto customerDto2 = new CustomerDto("niranjana", "sidharthan", "niru@gmail.com", "98765", 7734563312L);
        customerService.AddCustomerToDb(customerDto1);
        customerService.AddCustomerToDb(customerDto2);
        List<Customer> customers = Collections.singletonList(customerService.getCustomerByMobileNo(no));
    }


}
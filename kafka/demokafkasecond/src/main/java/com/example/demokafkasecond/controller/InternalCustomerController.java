package com.example.demokafkasecond.controller;

import com.example.demokafkasecond.model.Customer;
import com.example.demokafkasecond.service.CustomerQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/customers")
public class InternalCustomerController {
    private final CustomerQueryService customerQueryService;

    public InternalCustomerController(
            CustomerQueryService customerQueryService) {

        this.customerQueryService = customerQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getLocalCustomer(
            @PathVariable String id) {

        Customer customer =
                customerQueryService.getCustomer(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }
}

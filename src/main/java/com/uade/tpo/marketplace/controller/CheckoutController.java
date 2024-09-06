package com.uade.tpo.marketplace.controller;

import com.uade.tpo.marketplace.dto.CheckoutDTO;
import com.uade.tpo.marketplace.dto.CreateCheckoutDTO;
import com.uade.tpo.marketplace.dto.CreateCheckoutProductDTO;
import com.uade.tpo.marketplace.dto.UpdateCheckoutProductDTO;
import com.uade.tpo.marketplace.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping()
    public ResponseEntity<CheckoutDTO> getCheckout(@RequestHeader("Authorization") String authHeader){
        return ResponseEntity.ok(this.checkoutService.getCheckout(authHeader));
    }

    @PostMapping()
    public ResponseEntity<String> createCheckout(@RequestHeader("Authorization") String authHeader, @RequestBody CreateCheckoutDTO checkoutDTO){
        this.checkoutService.createCheckOut(authHeader, checkoutDTO);
        return new ResponseEntity<>("Checkout successfully created", HttpStatus.CREATED);
    }

    @PostMapping("/product")
    public ResponseEntity<String> addProductToCheckout(@RequestHeader("Authorization") String authHeader, @RequestBody CreateCheckoutProductDTO checkoutProductDTO) {
        this.checkoutService.addProductToCheckout(authHeader,checkoutProductDTO);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> modifyCheckoutProductQuantity(@RequestHeader("Authorization") String authHeader, @PathVariable long id, @RequestBody UpdateCheckoutProductDTO checkoutProductDTO) {
        this.checkoutService.modifyCheckoutProductQuantity(authHeader, id, checkoutProductDTO);
        return new ResponseEntity<>("Product quantity modified successfully", HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteCheckoutProduct(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        this.checkoutService.deleteCheckoutProduct(authHeader, id);
        return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCheckout(@RequestHeader("Authorization") String authHeader) {
        this.checkoutService.deleteCheckout(authHeader);
        return new ResponseEntity<>("Checkout Deleted Successfully",HttpStatus.OK);
    }

    @PutMapping("/address/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> changeCheckoutAddress(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        this.checkoutService.changeCheckoutAddress(authHeader, id);
        return new ResponseEntity<>("Address Changed Successfully", HttpStatus.OK);
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<String> changeCheckoutPaymentMethod(@RequestHeader("Authorization") String authHeader, @PathVariable long id) {
        this.checkoutService.changeCheckoutPaymentMethod(authHeader, id);
        return new ResponseEntity<>("Payment Method Changed Successfully", HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> generateOrder (@RequestHeader("Authorization") String authHeader) {
        this.checkoutService.generateOrder(authHeader);
        return new ResponseEntity<>("Order Successfully Generated", HttpStatus.CREATED);
    }
}

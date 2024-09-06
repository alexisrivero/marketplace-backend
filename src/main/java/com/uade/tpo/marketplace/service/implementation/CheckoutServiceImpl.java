package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.controller.config.JwtService;
import com.uade.tpo.marketplace.dto.*;
import com.uade.tpo.marketplace.entity.*;
import com.uade.tpo.marketplace.exceptions.*;
import com.uade.tpo.marketplace.mapper.CheckoutMapper;
import com.uade.tpo.marketplace.mapper.CheckoutOrderMapper;
import com.uade.tpo.marketplace.repository.*;
import com.uade.tpo.marketplace.service.CheckoutService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CheckoutProductRepository checkoutProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public CheckoutDTO getCheckout(String authHeader) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);
        Checkout checkout = getCheckout(user);

        CheckoutDTO checkoutDTO = CheckoutMapper.INSTANCE.checkoutToCheckoutDTO(checkout);

        List<CheckoutProductDTO> checkoutProductDTO = new ArrayList<>();
        for (CheckoutProduct checkoutProduct: checkout.getCheckoutProductList()) {
            CheckoutProductDTO dto = CheckoutMapper.INSTANCE.checkoutProductAndProductToProductCheckoutDTO(checkoutProduct,checkoutProduct.getProduct());
            checkoutProductDTO.add(dto);
        }

        checkoutDTO.setCheckoutProducts(checkoutProductDTO);

        checkoutDTO.setSubTotal(calculateCheckoutSubtotal(checkout));
        return checkoutDTO;
    }

    @Override
    public void createCheckOut(String authHeader, CreateCheckoutDTO checkoutDTO) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);

        Checkout getCheckout = checkoutRepository.findByUser(user);

        if (getCheckout != null) throw new ResourceAlreadyExistsException("This user already has a checkout");

        Checkout checkout = createCheckoutBasedOnUser(user);
        Checkout savedCheckout = checkoutRepository.save(checkout);

        for (int i= 0; i < checkoutDTO.getProducts().size();i++) {
            Product getProduct = getProduct(checkoutDTO.getProducts().get(i).getId());

            createCheckoutProduct(getProduct,checkoutDTO.getProducts().get(i).getQuantity(),savedCheckout);
        }
    }

    @Override
    public void addProductToCheckout(String authHeader, CreateCheckoutProductDTO checkoutProductDTO) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);

        Checkout checkout = checkoutRepository.findByUser(user);

        if (checkout == null) {
            checkout = createCheckoutBasedOnUser(user);
            checkout = checkoutRepository.save(checkout);
        }

        Product getProduct = getProduct(checkoutProductDTO.getId());

        CheckoutProduct checkoutProduct = checkoutProductRepository.findByCheckoutAndProduct(checkout,getProduct);

        if (checkoutProduct == null) {
            createCheckoutProduct(getProduct,checkoutProductDTO.getQuantity(),checkout);
            return;
        }

        setCheckoutProductQuantity(checkoutProduct,checkoutProduct.getQuantity() +
                checkoutProductDTO.getQuantity(),getProduct.getStock());
    }

    @Override
    public void modifyCheckoutProductQuantity(String authHeader, long productId, UpdateCheckoutProductDTO updateCheckoutProductDTO) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);

        Checkout checkout = getCheckout(user);
        Product getProduct = getProduct(productId);

        CheckoutProduct checkoutProduct = getCheckoutProduct(checkout,getProduct);

        setCheckoutProductQuantity(checkoutProduct, checkoutProduct.getQuantity() +
                updateCheckoutProductDTO.getQuantity(), getProduct.getStock());

        deleteCheckoutProductWhenQuantityZero(checkoutProduct);
        deleteCheckoutNoProducts(checkout);

    }

    @Override
    public void deleteCheckoutProduct(String authHeader, long productId) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);
        Checkout checkout = getCheckout(user);
        Product getProduct = getProduct(productId);

        CheckoutProduct checkoutProduct = checkoutProductRepository.findByCheckoutAndProduct(checkout,getProduct);
        if (checkoutProduct ==  null) throw new ResourceNotFoundException("We could not find this product in the checkout with the given information");

        checkoutProductDeletion(checkoutProduct);

        deleteCheckoutNoProducts(checkout);
    }

    @Override
    public void deleteCheckout(String authHeader) {
        String email = this.getEmailFromAuthHeader(authHeader);
        User user = getUser(email);
        Checkout checkout = getCheckout(user);

        checkoutProductRepository.deleteAllByCheckout(checkout);
        checkoutRepository.delete(checkout);
    }

    @Override
    public void changeCheckoutAddress(String authHeader, long id) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);

        Checkout checkout = getCheckout(user);

        Address address = addressRepository.findByUserAndId(user,id);

        if (address == null) throw new ResourceNotFoundException("We could not find an address with this id in this user");

        checkout.setAddress(address);
        checkoutRepository.save(checkout);
    }

    @Override
    public void changeCheckoutPaymentMethod(String authHeader, long id) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);
        Checkout checkout = getCheckout(user);

        PaymentMethod paymentMethod = paymentMethodRepository.findByUserAndId(user,id);

        if (paymentMethod == null) {
            throw new ResourceNotFoundException("We could not find a payment method with this id in this user");
        }

        checkout.setPaymentMethod(paymentMethod);
        checkoutRepository.save(checkout);
    }

    @Override
    public void generateOrder(String authHeader) {
        String email = this.getEmailFromAuthHeader(authHeader);

        User user = getUser(email);

        Checkout checkout = getCheckout(user);

        mandatoryCheckoutElementsValidation(checkout);

        Order order = createOrderBasedOnCheckout(checkout);

        double total = createOrderProductsCalculateTotal(checkout,order);
        order.setTotal(total);

        Transaction transaction = generateTransaction(order);
        order.setTransaction(transaction);

        setPaymentMethodFounds(transaction);

        orderRepository.save(order);

        deleteCheckout(authHeader);
    }

    private void setPaymentMethodFounds(Transaction transaction) {
        if (transaction.getAmount() > transaction.getPaymentMethod().getFunds()) {
            throw new NotEnoughFundsException("Not enough funds on your payment method");
        }
        transaction.getPaymentMethod().setFunds(transaction.getPaymentMethod().getFunds() - transaction.getAmount());
        paymentMethodRepository.save(transaction.getPaymentMethod());
    }


    private Transaction generateTransaction (Order order) {
        Transaction transaction = Transaction.builder()
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getTotal())
                .build();
        return transactionRepository.save(transaction);
    }

    private double createOrderProductsCalculateTotal(Checkout checkout, Order order) {
        double total = 0;
        for (int i= 0; i < checkout.getCheckoutProductList().size();i++) {
            OrderProduct orderProduct = CheckoutOrderMapper.INSTANCE.
                    checkoutProductToOrderProduct(checkout.getCheckoutProductList().get(i));
            orderProduct.setOrder(order);

            discountStockFromProducts(orderProduct);
            total += orderProduct.getProduct().getPrice() * orderProduct.getQuantity();

            orderProductRepository.save(orderProduct);
        }

        return total;
    }

    private void discountStockFromProducts(OrderProduct orderProduct)
    {
        if(orderProduct.getQuantity() > orderProduct.getProduct().getStock()) {
            throw new NotEnoughStockException("Not enough "+ orderProduct.getProduct().getName() +
                    " on stock - current stock: " + orderProduct.getProduct().getStock());
        }
        orderProduct.getProduct().setStock(orderProduct.getProduct().getStock() - orderProduct.getQuantity());
        productRepository.save(orderProduct.getProduct());
    }

    private Order createOrderBasedOnCheckout (Checkout checkout)
    {
        Order createOrder = CheckoutOrderMapper.INSTANCE.checkoutToOrder(checkout);

        if (calculateCheckoutSubtotal(checkout) > createOrder.getPaymentMethod().getFunds())
        {
            throw new NotEnoughFundsException("Not enough funds on your payment method");
        }

        return orderRepository.save(createOrder);
    }

    private void mandatoryCheckoutElementsValidation(Checkout checkout)
    {
        if (checkout.getAddress() == null) throw new RequiredInformationNullException("Address is mandatory, please set one to continue");
        if (checkout.getPaymentMethod() == null) throw new RequiredInformationNullException("PaymentMethod is mandatory, please set one to continue");
    }

    private void deleteCheckoutNoProducts(Checkout checkout)
    {
        if (checkout.getCheckoutProductList().isEmpty()) {
            checkoutRepository.delete(checkout);
        }
    }

    private void deleteCheckoutProductWhenQuantityZero(CheckoutProduct checkoutProduct)
    {
        if (checkoutProduct.getQuantity() <= 0)
        {
            checkoutProductDeletion(checkoutProduct);
        }
    }

    private void checkoutProductDeletion(CheckoutProduct checkoutProduct)
    {
        checkoutProduct.getCheckout().getCheckoutProductList().remove(checkoutProduct);
        checkoutProductRepository.delete(checkoutProduct);
    }

    private void setCheckoutProductQuantity(CheckoutProduct checkoutProduct, int quantity, int stock)
    {
        if (quantity > stock) {
            throw new NotEnoughStockException("Not enough "+ checkoutProduct.getProduct().getName() +" on stock - current stock: " + checkoutProduct.getProduct().getStock());
        }
        checkoutProduct.setQuantity(quantity);
        checkoutProductRepository.save(checkoutProduct);
    }

    private void createCheckoutProduct(Product product,int quantity, Checkout checkout)
    {
        CheckoutProduct checkoutProduct = CheckoutProduct.builder()
                .product(product)
                .quantity(quantity)
                .checkout(checkout)
                .build();
        if (checkoutProduct.getQuantity() > product.getStock()) {
            throw new NotEnoughStockException("Not enough "+ product.getName() + " on stock - current stock: " + product.getStock());
        }

        checkoutProductRepository.save(checkoutProduct);
    }

    private double calculateCheckoutSubtotal(Checkout checkout)
    {
        double subTotal = 0;
        for (int i = 0; i < checkout.getCheckoutProductList().size(); i++)
        {
            CheckoutProduct product = checkout.getCheckoutProductList().get(i);
            subTotal += product.getProduct().getPrice() * product.getQuantity();
        }

        return subTotal;
    }

    private Checkout createCheckoutBasedOnUser (User user)
    {
        return Checkout.builder()
                .user(user)
                .address(user.getAddresses().isEmpty() ? null : user.getAddresses().get(0))
                .paymentMethod(user.getPaymentMethods().isEmpty() ? null : user.getPaymentMethods().get(0))
                .build();
    }

    private User getUser (String email)
    {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new ResourceNotFoundException("We could not find a user with the given id");

        return user.get();
    }

    private Product getProduct (long id)
    {
        Optional<Product> getProduct = productRepository.findById(id);
        if (getProduct.isEmpty()) throw new ResourceNotFoundException("We could not find a product with the given id");

        return getProduct.get();
    }

    private Checkout getCheckout (User user) {
        Checkout checkout = this.checkoutRepository.findByUser(user);
        if (checkout == null)  throw new ResourceNotFoundException("We could not find a checkout with the given user, please create one");

        return checkout;
    }

    private CheckoutProduct getCheckoutProduct(Checkout checkout, Product product)
    {
        CheckoutProduct checkoutProduct = this.checkoutProductRepository.findByCheckoutAndProduct(checkout,product);
        if (checkoutProduct ==  null) throw new ResourceNotFoundException("We could not find a product checkout with the given information");

        return checkoutProduct;
    }

    private String getEmailFromAuthHeader(String authHeader) {
        String jwt = authHeader.substring(7);

        System.out.println("JWT: " + jwt);

        return jwtService.extractUsername(jwt);
    }

}

package com.zuhlke.f10.insurance.products.service;

import com.zuhlke.f10.insurance.exception.ResourceNotFoundException;
import com.zuhlke.f10.insurance.model.*;
import com.zuhlke.f10.insurance.products.repository.InvoiceRepository;
import com.zuhlke.f10.insurance.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private InvoiceRepository  invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Invoice buy(String productId, BuyerSpecifications buyerSpecs) {

        //UUID will be used in reference number and policy id
        String uuid = UUID.randomUUID().toString();

        Invoice invoice = new Invoice();
        invoice.setReferenceNumber(uuid + "-" + productId);

        InvoiceDetails details = new InvoiceDetails();

        //Billing address
        BillingAddress address = new BillingAddress();
        address.setName(buyerSpecs.getPersonalDetails().getName());
        address.setMailingAddress(buyerSpecs.getPersonalDetails().getMailingAddress());
        details.billTo(address);

        //Coverage date
        CoverageDate coverDate = new CoverageDate();
        coverDate.setFrom(buyerSpecs.getCoverageStartDate());
        coverDate.setTo(buyerSpecs.getCoverageEndDate());
        details.setCoverageDate(coverDate);

        //Invoice Date
        details.setInvoiceDate(LocalDate.now());

        //Policy Id
        details.setPolicyId(uuid);

        //Premium details
        PremiumDetails premiumDetails = new PremiumDetails();
        premiumDetails.setCurrency("SGD");
        premiumDetails.setLessDiscount(buyerSpecs.getPromotionalDiscount());
        premiumDetails.setPremiumAmt(buyerSpecs.getQuotedPremium());
        premiumDetails.setPremiumAmtBeforeTax(buyerSpecs.getQuotedPremium());
        premiumDetails.setTaxAmt(new BigDecimal(0));
        premiumDetails.setTotalAmt(buyerSpecs.getQuotedPremium());
        details.setPremiumDetails(premiumDetails);

        //Product description
        Product product = getProduct(productId);

        details.setProductDescription(product.getProductDescription());

        details.setProviderId("100");
        details.setProviderName("Zuhlke Insurance");


        invoice.setTaxInvoice(details);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return savedInvoice;
    }

    @Override
    public void deleteProduct(String id) {
        getProduct(id); //call this to throw ResourceNotFoundException if it does not exist
        productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(String id) {

        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("404","Product not found"));
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        getProduct(id); //call this to throw ResourceNotFoundException if it does not exist

        return productRepository.save(product);
    }
}
package com.alibou.paypalintegration.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PayPalController {

    private final PayPalService payPalService;

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @PostMapping("/payment/create")
    public RedirectView createPayment() {
        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success";
            Payment payment = payPalService.createPayment(
                    10.0,
                    "USD",
                    "paypal",
                    "sale",
                    "Payment description",
                    cancelUrl,
                    successUrl
            );

            Optional<Links> approvalUrl = payment.getLinks().stream().filter(links -> links.getRel().equals("approval_url")).findFirst();
            if (approvalUrl.isPresent()) {
                return new RedirectView(approvalUrl.get().getHref());
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }
}

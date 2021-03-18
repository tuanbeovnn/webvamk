package com.vamkthesis.web.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.OrderEntity;
import com.vamkthesis.web.repository.IOrderRepository;
import com.vamkthesis.web.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.UUID;

@RestController
public class PaypalController {

	@Autowired
    private PaypalService service;
	@Autowired
	private EmailService emailService;
	@Autowired
	private IOrderRepository orderRepository;

	public static final String SUCCESS_URL = "api/pay/success";
	public static final String CANCEL_URL = "api/pay/cancel";

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping(value="/api/pay", method = RequestMethod.POST)
	public ResponseEntity payment(@RequestBody Order order) {
		try {
			Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
					order.getIntent(), order.getDescription(), "http://saunakovaasa.ml:8080/" + CANCEL_URL,
					"http://saunakovaasa.ml/" + SUCCESS_URL + "?orderId="+ order.getId());
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return ResponseEntityBuilder.getBuilder().setDetails(link.getHref()).build();
				}
			}
			
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return ResponseEntityBuilder.getBuilder().setMessage("Your payment is not successful").build();
	}
	@RequestMapping(value=CANCEL_URL, method = RequestMethod.GET)
	    public String cancelPay() {
	        return "cancel";
	    }
	@RequestMapping(value=SUCCESS_URL, method = RequestMethod.GET)
	    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam long orderId) {
	        try {
	            Payment payment = service.executePayment(paymentId, payerId);
	            System.out.println(payment.toJSON());
				MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	            if (payment.getState().equals("approved")) {
					OrderEntity orderEntity = orderRepository.findById(orderId).get();
					orderEntity.setStatus(1);
					orderEntity.setCodeOrder(UUID.randomUUID().toString());
					orderEntity = orderRepository.save(orderEntity);

	            	emailService.sendMail(orderEntity.getEmail(),"Your Order", "Your Order Info" + orderEntity.toString());
	                return "success";
	            }
	        } catch (PayPalRESTException | MessagingException e) {
	         System.out.println(e.getMessage());
	        }
	        return "";
	    }

}

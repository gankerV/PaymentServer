package com.example.payment.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class paymentController {

    private final RestTemplate restTemplate;

    // URL của Booking Service (thay đổi nếu khác)
    private final String bookingServiceUrl = "http://localhost:8083/api/book";

    public paymentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookSeat(@RequestBody Map<String, String> request) {
        try {
            // Gửi request nguyên trạng đến Booking Service
            ResponseEntity<String> response = restTemplate.postForEntity(
                    bookingServiceUrl, request, String.class);

            // Trả về response từ Booking Service về frontend luôn
            return ResponseEntity.status(response.getStatusCode())
                    .body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi gọi Booking Service: " + e.getMessage());
        }
    }
}

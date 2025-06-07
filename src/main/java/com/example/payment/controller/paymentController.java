package com.example.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class paymentController {

    private final RestTemplate restTemplate;

    // URL của Booking Service (thay đổi nếu khác)
    private final String bookingServiceUrl = "https://seatserver.onrender.com/api";

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

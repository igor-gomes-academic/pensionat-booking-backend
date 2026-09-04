package com.pensionat.booking;

import com.pensionat.booking.dto.BookingResponse;
import com.pensionat.booking.dto.CreateBookingRequest;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookingApiIntegrationTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @BeforeEach
    void setUp(){
        url = "http://localhost:" + port + "/api/bookings";
    }

    @Test
    void shouldReturn404WhenCustomerDoesNotExist() {
        CreateBookingRequest request = new CreateBookingRequest(
                99999L,
                1L,
                LocalDate.of(2026,11,1),
                LocalDate.of(2026,11,4),false
        );

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        String.class
                );


        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void shouldReturn200WhenGettingBookings(){

        ResponseEntity<String> response =
                restTemplate.getForEntity(url,String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}

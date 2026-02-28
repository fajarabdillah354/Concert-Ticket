package com.fajarcode.concert_ticket;

import com.fajarcode.concert_ticket.booking.dto.CreateBookingRequest;
import com.fajarcode.concert_ticket.booking.service.BookingService;
import com.fajarcode.concert_ticket.concert.domain.Concert;
import com.fajarcode.concert_ticket.concert.repository.ConcertRepository;
import com.fajarcode.concert_ticket.inventory.domain.TicketInventory;
import com.fajarcode.concert_ticket.inventory.repository.TicketInventoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.testng.AssertJUnit.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConcertTicketApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private BookingService bookingService;

	@Autowired
	private TicketInventoryRepository inventoryRepository;

	@Autowired
	private ConcertRepository concertRepository;

	private UUID concertId;
	private UUID categoryId;

	@BeforeAll
	void setup() {

		concertId = UUID.randomUUID();
		categoryId = UUID.randomUUID();

		Concert concert = Concert.create(
				"Test",
				"Artist",
				"Venue",
				OffsetDateTime.now(),
				OffsetDateTime.now().plusHours(2),
				BigDecimal.valueOf(100),
				10
		);

		concert.setId(concertId);
		concertRepository.save(concert);

		TicketInventory inventory =
				TicketInventory.builder()
						.id(UUID.randomUUID())
						.concertId(concertId)
						.categoryId(categoryId)
						.totalStock(10)
						.availableStock(10)
						.build();

		inventoryRepository.save(inventory);
	}

	@Test
	void shouldNotOversell() throws Exception {

		int threads = 20;

		ExecutorService executor =
				Executors.newFixedThreadPool(threads);

		CountDownLatch latch = new CountDownLatch(threads);

		for (int i = 0; i < threads; i++) {

			executor.submit(() -> {
				try {
					bookingService.createBooking(
							new CreateBookingRequest(
									UUID.randomUUID(),
									concertId,
									categoryId,
									1
							),
							UUID.randomUUID().toString()
					);
				} catch (Exception ignored) {}
				latch.countDown();
			});
		}

		latch.await();

		TicketInventory updated =
				inventoryRepository
						.lockInventory(concertId, categoryId)
						.orElseThrow();

		assertTrue(updated.getAvailableStock() >= 0);
		assertTrue(updated.getAvailableStock() <= 10);
	}





}

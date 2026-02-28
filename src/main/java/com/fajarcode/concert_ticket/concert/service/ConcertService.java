package com.fajarcode.concert_ticket.concert.service;



import com.fajarcode.concert_ticket.concert.domain.Concert;
import com.fajarcode.concert_ticket.concert.dto.*;
import com.fajarcode.concert_ticket.concert.repository.ConcertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConcertService {


    private final ConcertRepository concertRepository;

    @Transactional
    public ConcertResponse create(CreateConcertRequest request) {

        Concert concert = Concert.create(
                request.name(),
                request.artist(),
                request.venue(),
                request.startTime(),
                request.endTime(),
                request.basePrice(),
                request.capacity()
        );

        concertRepository.save(concert);

        return ConcertResponse.from(concert);
    }

    public ConcertResponse getById(UUID id) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        return ConcertResponse.from(concert);
    }

    public List<ConcertResponse> listAll() {
        return concertRepository.findAll()
                .stream()
                .map(ConcertResponse::from)
                .toList();
    }

    @Transactional
    public ConcertResponse update(UUID id, UpdateConcertRequest request) {

        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        concert.update(
                request.name(),
                request.artist(),
                request.venue(),
                request.startTime(),
                request.endTime(),
                request.basePrice(),
                request.capacity()
        );

        return ConcertResponse.from(concert);
    }

    @Transactional
    public void delete(UUID id) {
        concertRepository.deleteById(id);
    }

    public long countAll() {
        return concertRepository.count();
    }
}

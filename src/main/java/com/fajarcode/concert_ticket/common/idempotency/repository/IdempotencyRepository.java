package com.fajarcode.concert_ticket.common.idempotency.repository;

import com.fajarcode.concert_ticket.common.idempotency.domain.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IdempotencyRepository extends JpaRepository<IdempotencyKey, String>{
}

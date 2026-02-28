package com.fajarcode.concert_ticket.inventory.domain;




import com.fajarcode.concert_ticket.concert.domain.Concert;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "ticket_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketInventory {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "category_id", columnDefinition = "VARCHAR(36)")
    private UUID categoryId;

    private int totalStock;
    private int availableStock;

    @Version
    private Long version;

    public void decreaseStock(int quantity) {
        if (availableStock < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        this.availableStock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.availableStock += quantity;
    }


    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "concert_id", columnDefinition = "VARCHAR(36)")
    private UUID concertId;



}

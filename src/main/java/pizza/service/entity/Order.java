package pizza.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime dateTime;
    @Column
    private BigDecimal price;
    @ManyToOne
    private List<Pizza> pizzas;
    @Column
    private Boolean isActive = Boolean.TRUE;
    @Column
    private Boolean isPaid = Boolean.FALSE;
    @OneToOne
    private Receipt receipt;

}

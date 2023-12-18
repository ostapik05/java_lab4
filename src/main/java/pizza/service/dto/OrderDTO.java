package pizza.service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pizza.service.entity.Order;
import pizza.service.entity.Pizza;
import pizza.service.entity.Receipt;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull
    private Long id;
    private LocalDateTime dateTime;
    @NotNull
    private BigDecimal price;
    private List<Pizza> pizzas;
    @NotNull
    private Boolean isActive = Boolean.TRUE;
    @NotNull
    private Boolean isPaid = Boolean.FALSE;
    private Receipt receipt;

    public static OrderDTO toDTO(final Order order){
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setIsActive(order.getIsActive());
        orderDTO.setPizzas(order.getPizzas());
        orderDTO.setDateTime(order.getDateTime());
        orderDTO.setIsPaid(order.getIsPaid());
        orderDTO.setReceipt(order.getReceipt());
        return orderDTO;
    }
}

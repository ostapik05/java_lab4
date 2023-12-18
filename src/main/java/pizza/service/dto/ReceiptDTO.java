package pizza.service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pizza.service.entity.Order;
import pizza.service.entity.Receipt;
import pizza.service.entity.User;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {
    @NotNull
    private Long id;
    @NotNull
    private User user;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Boolean isActive = Boolean.TRUE;
    private Order order;

    public static ReceiptDTO toDTO(final Receipt receipt){
        final ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setId(receipt.getId());
        receiptDTO.setUser(receipt.getUser());
        receiptDTO.setPrice(receipt.getPrice());
        receiptDTO.setIsActive(receipt.getIsActive());
        receiptDTO.setOrder(receipt.getOrder());
        return receiptDTO;
    }


}

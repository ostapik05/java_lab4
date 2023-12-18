package pizza.service.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pizza.service.entity.Receipt;
import pizza.service.service.ReceiptService;

@RestController
@RequestMapping("/api/receipt")
@RequiredArgsConstructor
public class ReceiptResource {
    private final ReceiptService receiptService;

    @GetMapping("/{id}")
    public Receipt findById(final @PathVariable Long id) {
        return receiptService.findById(id);
    }

    @PostMapping("/confirm/{id}")
    public void generateReceiptForOrder(final @PathVariable Long id) {
        receiptService.generateReceiptForOrder(receiptService.findById(id));
    }





}

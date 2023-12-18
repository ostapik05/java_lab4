package pizza.service.service;

import pizza.service.entity.Order;
import pizza.service.entity.Pizza;
import pizza.service.entity.User;
import pizza.service.entity.Receipt;

public interface ReceiptService {
    public Receipt findById(Long id);
    void generateReceiptForOrder(Order order);
    void buySomething(Receipt receipt, User user);
    void releaseReceipt(Long receiptId);
}

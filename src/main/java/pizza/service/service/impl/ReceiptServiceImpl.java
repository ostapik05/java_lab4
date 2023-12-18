package pizza.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pizza.service.entity.Order;
import pizza.service.entity.Receipt;
import pizza.service.entity.User;
import pizza.service.exceptions.BadRequestException;
import pizza.service.repository.OrderRepository;
import pizza.service.repository.ReceiptRepository;
import pizza.service.repository.UserRepository;
import pizza.service.service.ReceiptService;

@RequiredArgsConstructor
@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    @Override
    public void buySomething(final Receipt receipt,final User user) {

        user.setMoney(user.getMoney().subtract(receipt.getPrice()));
        userRepository.save(user);

        receipt.setUser(user);
        receiptRepository.save(receipt);

    }

    @Override
    public void releaseReceipt(Long receiptId) {
        final Receipt receipt = findById(receiptId);
        final User user = receipt.getUser();
        user.setMoney(user.getMoney().add(receipt.getPrice()));
        receiptRepository.deleteById(receipt.getId());
        userRepository.save(user);

    }

    @Override
    public void generateReceiptForOrder(Order order) {
        Receipt receipt = new Receipt();
        receipt.setPrice(order.getPrice());
        receipt.setOrder(order.getReceipt().getOrder());
        receiptRepository.save(receipt);
        order.setReceipt(receipt);
        orderRepository.save(order);

    }
    public Receipt findById(final Long id){
        return receiptRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Receipt with id {%s} not found", id)));
    }
}

package pizza.service.repository.stream;

import org.springframework.stereotype.Repository;
import pizza.service.entity.Order;
import pizza.service.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderStreamRepository {
    private List<Order> orderList = new ArrayList<>();

    private Long lastUsedId = 0L;

    private Long generateId() {
        ++lastUsedId;
        return lastUsedId;
    }

    public Order save(final Order order) {
        order.setId(generateId());
        orderList.add(order);
        return order;
    }

    public Optional<Order> findById(final Long id) {
        return orderList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<Order> findAll() {
        return orderList;
    }

    public void deleteById(final Long id) {
        orderList = orderList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
    }

    public Order update(final Order order) {
        Order savedOrder = findById(order.getId())
                .orElseThrow(() -> new BadRequestException(String.format("Order with id {%s} not found", order.getId())));

        return savedOrder;
    }
}


package pizza.service.service;

import pizza.service.dto.OrderDTO;

import java.util.List;


public interface ShoppingCartService {

    OrderDTO findDTOById(Long id);
    List<OrderDTO> findAll();
    void deleteById(Long id);
    OrderDTO save(OrderDTO order);
    OrderDTO create(OrderDTO order);
    OrderDTO update(OrderDTO order);
    OrderDTO addPizza(Long orderId, Long pizzaId);

    OrderDTO addIngredientToPizza(Long orderId, int pizzaInOrderId, Long ingredientsId);

    OrderDTO payOrder(Long userId, Long orderId);

}

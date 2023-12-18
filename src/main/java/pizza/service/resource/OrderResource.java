package pizza.service.resource;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pizza.service.dto.OrderDTO;
import pizza.service.service.IngredientService;
import pizza.service.service.PizzaService;
import pizza.service.service.ShoppingCartService;
import pizza.service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderResource {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final PizzaService pizzaService;
    private final IngredientService ingredientService;



    @GetMapping("/{id}")
    public OrderDTO findById(final @PathVariable Long id) {
        return shoppingCartService.findDTOById(id);
    }

    @GetMapping
    public List<OrderDTO> findAll() {
        return shoppingCartService.findAll();
    }
    @PostMapping
    public OrderDTO createOrder(final @RequestBody OrderDTO orderDTO) {
        return shoppingCartService.create(orderDTO);
    }

    @PutMapping
    public OrderDTO updateOrder(final @RequestBody OrderDTO orderDTO) {
        return shoppingCartService.update(orderDTO);
    }
    @PutMapping("/pay")
    public OrderDTO payOrder(final @RequestParam Long orderId, final @RequestParam Long userId) {
        return shoppingCartService.payOrder(userId, orderId);
    }

    @PutMapping("/{orderId}/add-dish")
    public OrderDTO addDishToOrder(final @NotNull @PathVariable Long orderId, final @NotNull @RequestParam Long dishId) {
        return shoppingCartService.addPizza(orderId, dishId);
    }
    @PutMapping("/{orderId}/add-topping")
    public OrderDTO addToppingToDish(final @NotNull @PathVariable Long orderId, final @NotNull @RequestParam int dishInOrderId, final @NotNull @RequestParam Long toppingId) {
        return shoppingCartService.addIngredientToPizza(orderId,dishInOrderId, toppingId);
    }
    @DeleteMapping("/{id}")
    public void deleteById(final @PathVariable Long id) {
        shoppingCartService.deleteById(id);
    }
}

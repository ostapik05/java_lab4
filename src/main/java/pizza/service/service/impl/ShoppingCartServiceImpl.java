package pizza.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pizza.service.dto.OrderDTO;
import pizza.service.entity.Ingredient;
import pizza.service.entity.Order;
import pizza.service.entity.Pizza;
import pizza.service.entity.User;
import pizza.service.exceptions.BadRequestException;
import pizza.service.repository.IngredientRepository;
import pizza.service.repository.OrderRepository;
import pizza.service.repository.PizzaRepository;
import pizza.service.repository.UserRepository;
import pizza.service.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;

    public OrderDTO findDTOById(Long id) {
        Order order = findById(id);
        return OrderDTO.toDTO(order);
    }

    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Order with id {%s} not found", id)));
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(OrderDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Order order = findById(id);
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = new Order();
        order.setIsActive(orderDTO.getIsActive());
        order.setIsActive(orderDTO.getIsActive());
        order.setPizzas(orderDTO.getPizzas());
        order.setPrice(calculatePrice(orderDTO));
        orderRepository.save(order);
        return OrderDTO.toDTO(order);
    }
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = new Order();
        order.setPrice(calculatePrice(orderDTO));
        orderRepository.save(order);
        return OrderDTO.toDTO(order);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        if (orderDTO.getId() == null) {
            throw new BadRequestException("Id can't be null");
        }
        final Long orderId = orderDTO.getId();
        final Order orderDb = findById(orderId);
        if (!orderDb.getIsActive()) {
            throw new BadRequestException(String.format("Can`t change order #{%s}, order is non active", orderId));
        }

        final Order savedOrder = findById(orderDTO.getId());

        savedOrder.setIsActive(orderDTO.getIsActive());
        savedOrder.setIsActive(orderDTO.getIsActive());
        savedOrder.setPizzas(orderDTO.getPizzas());
        savedOrder.setPrice(calculatePrice(orderDTO));
        orderRepository.save(savedOrder);

        return OrderDTO.toDTO(savedOrder);
    }
    @Override
    public OrderDTO addPizza(Long orderId, Long pizzaId){
        final Optional<Order> order = orderRepository.findById(orderId);
        final Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if(pizza.isEmpty())
            throw new BadRequestException(String.format("Pizza with id#{%s} not found", pizzaId));
        if(order.isEmpty())
            throw new BadRequestException(String.format("Order with id#{%s} not found", orderId));
        if((!order.get().getIsActive())||order.get().getIsPaid())
            throw new BadRequestException("Cannot change order");
        order.get().getPizzas().add(pizza.get());
        orderRepository.save(order.get());
        return null;
        /*orderRepository.save(order.get());
        return null;*/
    }


    @Override
    public OrderDTO addIngredientToPizza(Long orderId, int pizzaInOrderId, Long ingredientsId){
        final Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty())
            throw new BadRequestException(String.format("Order with id#{%s} not found", orderId));
        if(order.get().getPizzas().isEmpty())
            throw new BadRequestException(String.format("Order does not contains pizza #{%s}", pizzaInOrderId));
        final Pizza pizzaInOrder = order.get().getPizzas().get(pizzaInOrderId-1);
        final Long PizzaDbId = pizzaInOrder.getId();
        if(PizzaDbId==null)
            throw new BadRequestException("Cannot find this pizza in database");
        final Optional<Pizza> pizza = pizzaRepository.findById(PizzaDbId);
        final Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientsId);
        if(pizza.isEmpty()) {
            throw new BadRequestException("Pizza not found");
        }
        if(ingredient.isEmpty())
            throw new BadRequestException(String.format("Ingredient with id#{%s} not found", ingredientsId));
        if((!order.get().getIsActive())||order.get().getIsPaid())
            throw new BadRequestException("Cannot change order");
        pizza.get().getIngredients().add(ingredient.get());
        final Pizza newPizza = pizza.get().copy();
        newPizza.getIngredients().add(ingredient.get());
        pizzaRepository.save(newPizza);
        order.get().getPizzas().remove(pizza.get());
        order.get().getPizzas().add(newPizza);
        orderRepository.save(order.get());
        return OrderDTO.toDTO(order.get());
    }

    @Override
    public OrderDTO payOrder(Long userId, Long orderId) {
        if(userId==null)
            throw new BadRequestException("User id is null");
        if(orderId==null)
            throw new BadRequestException("Order id is null");
        final Optional<User> user = userRepository.findById(userId);
        final Optional<Order> order = orderRepository.findById(orderId);

        if(user.isEmpty())
            throw new BadRequestException("User not found");
        if(order.isEmpty())
            throw new BadRequestException("Order not found");
        if (user.get().getMoney().compareTo(order.get().getPrice()) < 0)
            throw new BadRequestException(String.format("Balance {%s} ({%s}) less than order {%s} price ({%s})", user.get().getName() + " " + user.get().getSurname(), user.get().getMoney(), order.get().getId(), user.get().getMoney()));
        user.get().getMoney().subtract(order.get().getPrice());
        order.get().setIsPaid(true);
        orderRepository.save(order.get());
        userRepository.save(user.get());
        return OrderDTO.toDTO(order.get());
    }

    private BigDecimal calculatePrice(Order order) {
        final BigDecimal price = BigDecimal.ZERO;
        if (order == null || order.getPizzas() == null || order.getPizzas().isEmpty()) {
            return price;
        }
        for (Pizza pizza : order.getPizzas()) {
            if (pizza.getIngredients() != null) {
                for (Ingredient ingredient : pizza.getIngredients()) {
                    price.add(ingredient.getPrice());
                }
            }
            price.add(pizza.getPrice());
        }
        return price;
    }
    private BigDecimal calculatePrice(OrderDTO order) {
        BigDecimal price = BigDecimal.ZERO;
        if (order == null || order.getPizzas() == null || order.getPizzas().isEmpty()) {
            return price;
        }
        for (Pizza pizza : order.getPizzas()) {
            if (pizza.getIngredients() != null) {
                for (Ingredient ingredient : pizza.getIngredients()) {
                    price =  price.add(ingredient.getPrice());
                }
            }
            price = price.add(pizza.getPrice());
        }
        return price;
    }
}

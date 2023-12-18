package pizza.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private BigDecimal price;
    @Column
    private String name;
    @ManyToMany
    private List<Ingredient> ingredients;
    @Column
    private Boolean isActive = Boolean.TRUE;
    public Pizza copy(){
        final Pizza pizza = new Pizza();
        pizza.setId(getId());
        pizza.setPrice(getPrice());
        pizza.setName(getName());
        pizza.setIsActive(getIsActive());
        List<Ingredient> clonedIngredients = new ArrayList<>(getIngredients());
        pizza.setIngredients(clonedIngredients);
        return pizza;
    }
}

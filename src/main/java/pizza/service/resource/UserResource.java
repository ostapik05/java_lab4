package pizza.service.resource;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pizza.service.dto.UserDTO;
import pizza.service.service.UserService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser(final @PathVariable Long id) {
        return userService.findDTOById(id);
    }

    @GetMapping("/get/all/users")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/search")
    public UserDTO find(final @RequestParam String name, final @RequestParam String surname) {
        return  userService.searchByNameAndSurname(name, surname);
    }

    @PostMapping
    public UserDTO createUser(final @RequestBody @Valid UserDTO userDTO) {//@Valid -
        return userService.save(userDTO);
    }

    @PatchMapping("/money/add")
    public void addMoney(final @PathVariable Long id, @RequestParam BigDecimal money){
        userService.addMoney(id, money);
    }

    @PatchMapping("/money/remove")
    public void removeMoney(final @RequestParam Long id, final @RequestParam BigDecimal money){
        userService.removeMoney(id, money);
    }

    @PutMapping
    public UserDTO updateUser(final @RequestBody UserDTO user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(final @PathVariable Long id) {
        userService.deleteById(id);
    }

}

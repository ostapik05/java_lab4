package pizza.service.repository.stream;

import org.springframework.stereotype.Repository;
import pizza.service.entity.User;
import pizza.service.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserStreamRepository {
    private List<User> userList = new ArrayList<>();

    private Long lastUsedId = 0L;
    private Long generateId(){
        ++ lastUsedId;
        return lastUsedId;
    }

    public User save(final User user){
         user.setId(generateId());
         userList.add(user);
         return user;
    }

    public Optional<User> findById(final Long id){
        return userList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<User> findAll(){
        return userList;
    }

    public void deleteById(final Long id){
        userList = userList.stream()
                .filter(e -> e.getId().equals(id))
                .collect(Collectors.toList());
    }

    public User update(final User user){
        User savedUser = findById(user.getId())
                .orElseThrow(() -> new BadRequestException(String.format("User with id {%s} not found", user.getId())));


        savedUser.setName(user.getName());
        savedUser.setSurname(user.getSurname());
        savedUser.setMoney(user.getMoney());

        return savedUser;

    }

}

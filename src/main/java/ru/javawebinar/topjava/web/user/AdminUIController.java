package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUIController extends AbstractUserController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @GetMapping
    public List<User> getAll() {
        List<User> list=super.getAll();
        log.info(list.toString());
        return list;
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestParam String name,
                       @RequestParam String email,
                       @RequestParam String password) {
        super.create(new User(null, name, email, password, Role.USER));
    }

    @PostMapping("/enabled")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User updateEnabled(@RequestParam int id, @RequestParam boolean enabled) {
        log.info(id + "update enabled" + enabled);
        return super.updateEnabled(id, enabled);
    }
}

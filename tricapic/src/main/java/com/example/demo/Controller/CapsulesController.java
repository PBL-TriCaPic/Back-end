import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_interfaces.CapsulesRepo;

@RestController
@RequestMapping("/capsules")
public class CapsulesController {

    private final CapsulesRepo capsulesService;

    @Autowired
    public CapsulesController(CapsulesRepo capsulesService) {
        this.capsulesService = capsulesService;
    }

    @PostMapping("/create")
    public CapsulesRepo createCapsule(@RequestBody CapsulesRepo capsule) {
        return capsulesService.createCapsule(capsule);
    }

    @GetMapping("/{id}")
    public CapsulesRepo getCapsule(@PathVariable Long id) {
        return capsulesService.getCapsule(id);
    }
}

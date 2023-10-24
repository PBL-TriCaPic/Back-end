import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.CapsulesService;
import com.example.demo.data_tables.Capsules;

@RestController
@RequestMapping("/capsules")
public class CapsulesController {

    private final CapsulesService capsulesService;

    @Autowired
    public CapsulesController(CapsulesService capsulesService) {
        this.capsulesService = capsulesService;
    }

    @PostMapping("/create")
    public Capsules createCapsule(@RequestBody Capsules capsule) {
        return capsulesService.createCapsule(capsule);
    }

    @GetMapping("/{id}")
    public Capsules getCapsule(@PathVariable Long id) {
        return capsulesService.getCapsule(id);
    }
}

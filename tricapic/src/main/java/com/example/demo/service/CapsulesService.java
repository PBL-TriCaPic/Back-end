import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.CapsulesRepo;
import com.example.demo.data_tables.Capsules;

@Service
public class CapsulesService {

    private final CapsulesRepo capsulesRepo;

    @Autowired
    public CapsulesService(CapsulesRepo capsulesRepo) {
        this.capsulesRepo = capsulesRepo;
    }

    public CapsulesRepo createCapsule(Capsules capsule) {
        return capsulesRepo.save(capsule);
    }

    public CapsulesRepo getCapsule(Long id) {
        return capsulesRepo.findById(id).orElse(null);
    }
}

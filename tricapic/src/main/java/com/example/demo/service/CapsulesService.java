import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.CapsulesRepo;

@Service
public class CapsulesService implements CapsulesRepo {

    private final CapsulesRepo capsulesRepo;

    @Autowired
    public CapsulesService(CapsulesRepo capsulesRepo) {
        this.capsulesRepo = capsulesRepo;
    }

    @Override
    public CapsulesRepo createCapsule(CapsulesRepo capsule) {
        return capsulesRepo.save(capsule);
    }

    @Override
    public CapsulesRepo getCapsule(Long id) {
        return capsulesRepo.findById(id).orElse(null);
    }
}

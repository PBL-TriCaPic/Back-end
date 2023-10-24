import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_tables.Capsules;

@Service
public class CapsulesServiceImpl implements CapsulesService {

    private final CapsulesRepository capsulesRepository;

    @Autowired
    public CapsulesServiceImpl(CapsulesRepository capsulesRepository) {
        this.capsulesRepository = capsulesRepository;
    }

    @Override
    public Capsules createCapsule(Capsules capsule) {
        return capsulesRepository.save(capsule);
    }

    @Override
    public Capsules getCapsule(Long id) {
        return capsulesRepository.findById(id).orElse(null);
    }
}

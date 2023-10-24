import com.example.demo.data_tables.Capsules;

public interface CapsulesService {
    Capsules createCapsule(Capsules capsule);
    Capsules getCapsule(Long id);
}

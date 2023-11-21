package med.guimero.api.services.doctor;
import jakarta.persistence.EntityNotFoundException;
import med.guimero.api.domain.doctor.*;
import org.springframework.data.domain.*;

public interface DoctorService {

    DoctorShowData save(DoctorRegisterData doctorRegisterData);
    Page<DoctorShowData> findAll(boolean active, Pageable paging);
    DoctorShowData findById(Long id) throws EntityNotFoundException;
    DoctorShowData findByName(String name) throws EntityNotFoundException;
    DoctorShowData update(DoctorUpdateData doctorUpdateData) throws EntityNotFoundException;
    boolean turnOffDoctor(Long id) throws EntityNotFoundException;
    boolean reactivateDoctor(Long id) throws EntityNotFoundException;
    //boolean delete(Long id);

}
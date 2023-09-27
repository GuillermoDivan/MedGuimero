package med.guimero.api.services;
import med.guimero.api.domain.doctor.*;
import org.springframework.data.domain.*;

public interface DoctorService {

    DoctorShowData save(DoctorRegisterData doctorRegisterData);
    Page<DoctorShowData> findAll(boolean active, Pageable paging);
    DoctorShowData findById(Long id);
    DoctorShowData findByName(String name);
    DoctorShowData update(DoctorUpdateData doctorUpdateData);
    boolean turnOffDoctor(Long id);
    //boolean delete(Long id);

}
package med.guimero.api.services.patient;
import jakarta.persistence.EntityNotFoundException;
import med.guimero.api.domain.doctor.DoctorShowData;
import med.guimero.api.domain.patient.PatientRegisterData;
import med.guimero.api.domain.patient.PatientShowData;
import med.guimero.api.domain.patient.PatientUpdateData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//Interfaz no debe tener la @Service,sólo su implementación la lleva.
public interface PatientService {

    PatientShowData save(PatientRegisterData data);
    Page<PatientShowData> findAll(Boolean active, Pageable paging);
    PatientShowData findById(Long id) throws EntityNotFoundException;
    PatientShowData findByName(String name) throws EntityNotFoundException;
    PatientShowData update(PatientUpdateData data) throws EntityNotFoundException;
    boolean turnOffPatient(Long id) throws EntityNotFoundException;
    boolean reactivatePatient(Long id) throws EntityNotFoundException;
    //boolean delete(Long id);

}

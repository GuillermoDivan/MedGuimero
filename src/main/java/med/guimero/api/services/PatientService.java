package med.guimero.api.services;
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
    PatientShowData findById(Long id);
    PatientShowData findByName(String name);
    PatientShowData update(PatientUpdateData data);
    boolean turnOffPatient(Long id);
    //boolean delete(Long id);

}

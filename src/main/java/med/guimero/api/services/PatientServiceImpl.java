package med.guimero.api.services;
import med.guimero.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientShowData save(PatientRegisterData data){
        //1° Transformar el DTO register a Entidad.
        Patient patient = new Patient(data);
        //2° Llamar al método de guardar del repository.
        this.patientRepository.save(patient);
        //3° Transformar la entidad en el DTO show y retornarlo.
        return new PatientShowData(patient);
    }

    @Override
    public Page<PatientShowData> findAll(Boolean active, Pageable paging) {
        return this.patientRepository.findAllByActive(active, paging)
                .map(PatientShowData::new);
    }

    @Override
    public PatientShowData findById(Long id) {
        var found = this.patientRepository.findById(id).orElse(null);
        return new PatientShowData(found);
    }

    @Override
    public PatientShowData findByName(String name) {
        var found = this.patientRepository.findByName(name).orElse(null);
        return new PatientShowData(found);
    }

    @Override
    public PatientShowData update(PatientUpdateData data) {
        Patient patient = new Patient(data);
        Patient patientToUpdate = this.patientRepository.findById(patient.getId()).orElse(null);
        if (patientToUpdate.isActive()) {
        if (patient.getName() != null) {
            patientToUpdate.setName(patient.getName());
        }
        if (patient.getEmail() != null) {
            patientToUpdate.setEmail(patient.getEmail());
        }
        if (patient.getPhone() != null) {
            patientToUpdate.setPhone(patient.getPhone());
        }
        if (patient.getAddress() != null) {
            patientToUpdate.setAddress(patient.getAddress());
        }
        this.patientRepository.save(patientToUpdate); }
        else { throw new IllegalArgumentException(); }
        return new PatientShowData(patientToUpdate);
    }

    @Override
    public boolean turnOffPatient(Long id) {
        Patient patientToTurnOff = this.patientRepository.findById(id).orElse(null);
        if (patientToTurnOff.isActive()) {
            patientToTurnOff.setActive(false);
            this.patientRepository.save(patientToTurnOff);
            return true;
        }
        return false;
    }

   /* @Override
    public boolean delete(Long id) {
        this.patientRepository.deleteById(id);
        return true;
    }*/


}

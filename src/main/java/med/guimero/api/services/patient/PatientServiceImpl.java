package med.guimero.api.services.patient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.address.Address;
import med.guimero.api.domain.patient.*;
import med.guimero.api.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientShowData save(PatientRegisterData data){
        Patient patient = new Patient(data);
        this.patientRepository.save(patient);
        return new PatientShowData(patient);
    }

    @Override
    public Page<PatientShowData> findAll(Boolean active, Pageable paging) {
        return this.patientRepository.findAllByActive(active, paging)
                .map(PatientShowData::new);
    }

    @Override
    public PatientShowData findById(Long id) throws EntityNotFoundException {
        var found = this.patientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return new PatientShowData(found);
    }

    @Override
    public PatientShowData findByName(String name) throws EntityNotFoundException {
        var found = this.patientRepository.findByName(name)
                .orElseThrow(EntityNotFoundException::new);
        return new PatientShowData(found);
    }

    @Override
    public PatientShowData update(PatientUpdateData updateData) throws EntityNotFoundException {
        Patient patient = this.patientRepository.findById(updateData.id())
                .orElseThrow(EntityNotFoundException::new);
        if (patient.isActive()) {
        if (updateData.name() != null) {
            patient.setName(updateData.name());
        }
            if (updateData.email() != null) {
                patient.setEmail(updateData.email());
            }
            if (updateData.phone() != null) {
                patient.setPhone(updateData.phone());
            }
            if (updateData.address() != null) {
                var address = new Address(updateData.address());
                patient.setAddress(address);
            }
        this.patientRepository.save(patient); }
        else { throw new IllegalArgumentException(); }
        return new PatientShowData(patient);
    }

    @Override
    public boolean turnOffPatient(Long id)  throws EntityNotFoundException {
        Patient patientToTurnOff = this.patientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (patientToTurnOff.isActive()) {
            patientToTurnOff.setActive(false);
            this.patientRepository.save(patientToTurnOff);
            return true;
        }
        return false;
    }

    @Override
    public boolean reactivatePatient(Long id) throws EntityNotFoundException {
        Patient patient = this.patientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (!patient.isActive()) {
            patient.setActive(true);
            this.patientRepository.save(patient);
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

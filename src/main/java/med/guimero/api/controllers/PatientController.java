package med.guimero.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.guimero.api.domain.doctor.DoctorRegisterData;
import med.guimero.api.domain.doctor.DoctorShowData;
import med.guimero.api.domain.patient.PatientRegisterData;
import med.guimero.api.domain.patient.PatientShowData;
import med.guimero.api.domain.patient.PatientUpdateData;
import med.guimero.api.services.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService){ this.patientService = patientService; }

    @PostMapping
    @Transactional
    public ResponseEntity<PatientShowData> registerPatient(@RequestBody @Valid
         PatientRegisterData patientRegisterData, UriComponentsBuilder uriBuilder){
        var data = this.patientService.save(patientRegisterData);
        URI url = uriBuilder.path("/patient/id/{id}").buildAndExpand(data.id()).toUri();
        return ResponseEntity.created(url).body(data);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PatientShowData>> findPatientList
            (@PageableDefault(size=25) Pageable paging){
        return ResponseEntity.ok(patientService.findAll(true, paging));
    }

    @GetMapping("/allInactive")
    public ResponseEntity<Page<PatientShowData>> findInactivePatientList
            (@PageableDefault(size=25) Pageable paging){
        return ResponseEntity.ok(patientService.findAll(false, paging));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity <PatientShowData> findPatientById(@PathVariable Long id){
        return ResponseEntity.ok(patientService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity <PatientShowData> findPatientByName(@PathVariable String name){
        return ResponseEntity.ok(patientService.findByName(name));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PatientShowData> updatePatient
            (@RequestBody @Valid PatientUpdateData patientUpdateData){
        return ResponseEntity.ok(this.patientService.update(patientUpdateData));
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> turnOffPatient(@PathVariable Long id){
        boolean turnedOff = patientService.turnOffPatient(id);
        if (turnedOff) { return ResponseEntity.noContent().build(); }
        else { return ResponseEntity.badRequest().build(); }
    }

    /*@DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> deletePatient (@PathVariable Long id){
    boolean deleted = patientService.delete(id);
        if (deleted) { return ResponseEntity.noContent().build(); }
        else { return ResponseEntity.badRequest().build(); }
}*/

}

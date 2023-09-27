package med.guimero.api.controllers;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.guimero.api.domain.doctor.DoctorShowData;
import med.guimero.api.domain.doctor.DoctorRegisterData;
import med.guimero.api.domain.doctor.DoctorUpdateData;
import med.guimero.api.services.DoctorService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorShowData> registerDoctor(@RequestBody @Valid DoctorRegisterData doctorRegisterData, UriComponentsBuilder uriBuilder) {
        var data = this.doctorService.save(doctorRegisterData);
        URI url = uriBuilder.path("/doctors/id/{id}").buildAndExpand(data.id()).toUri();
        return ResponseEntity.created(url).body(data);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DoctorShowData>> findDoctorList(@PageableDefault(size=3) Pageable paging){
        return ResponseEntity.ok(doctorService.findAll(true, paging));
    }

    @GetMapping("/allInactive")
    public ResponseEntity<Page<DoctorShowData>> findInactiveDoctorList(@PageableDefault(size=3) Pageable paging){
        return ResponseEntity.ok(doctorService.findAll(false, paging));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DoctorShowData> findDoctorById(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DoctorShowData> findDoctorByName(@PathVariable String name){
        return ResponseEntity.ok(doctorService.findByName(name));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorShowData> updateDoctor(@RequestBody @Valid DoctorUpdateData doctorUpdateData){
        return ResponseEntity.ok(this.doctorService.update(doctorUpdateData));
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> turnOffDoctor(@PathVariable Long id){
        boolean turnedOff = doctorService.turnOffDoctor(id);
        if (turnedOff) { return ResponseEntity.noContent().build(); }
        else { return ResponseEntity.badRequest().build(); }
    }

    /* Este es el Delete posta posta.
    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> deleteDoctor(@PathVariable Long id){
    boolean deleted = doctorService.delete(id);
    if (deleted) { return ResponseEntity.noContent().build(); }
        else { return ResponseEntity.badRequest().build(); }
    }*/
}


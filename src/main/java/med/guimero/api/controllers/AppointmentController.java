package med.guimero.api.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.domain.appointment.AppointmentShowData;
import med.guimero.api.services.appointment.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentShowData> registerAppointment(@RequestBody
    @Valid AppointmentRegisterData appointmentRegisterData,
                                                                   UriComponentsBuilder uriBuilder) {
        var data = this.appointmentService.save(appointmentRegisterData);
        URI url = uriBuilder.path("/appointments/id/{id}").buildAndExpand(data.id()).toUri();
        return ResponseEntity.created(url).body(data);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AppointmentShowData>> findAppointmentsList(@PageableDefault(size=3)
              Pageable paging){
        return ResponseEntity.ok(appointmentService.findAll(paging));
    }

    @GetMapping("/all/doctors/{doctorId}")
    public ResponseEntity<Page<AppointmentShowData>> findDoctorAppointmentsList(@PathVariable Long doctorId,
        @PageableDefault(size=3)  Pageable paging){
        return ResponseEntity.ok(appointmentService.findAllDoctorAppointments(doctorId, paging));
    }

    @GetMapping("/all/patients/{patientId}")
    public ResponseEntity<Page<AppointmentShowData>> findPatientAppointmentsList(@PathVariable Long patientId,
         @PageableDefault(size=3) Pageable paging){
        return ResponseEntity.ok(appointmentService.findAllPatientAppointments(patientId, paging));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id){
    boolean deleted = appointmentService.delete(id);
    if (deleted) {return ResponseEntity.noContent().build(); }
    else {return ResponseEntity.badRequest().body("La cita había sido eliminada previamente");}
    }
}

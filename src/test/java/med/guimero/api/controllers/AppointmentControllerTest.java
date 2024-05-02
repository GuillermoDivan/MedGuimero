package med.guimero.api.controllers;

import med.guimero.api.domain.appointment.*;
import med.guimero.api.domain.doctor.Specialty;
import med.guimero.api.services.appointment.AppointmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest //Test de SpringBoot porque Controller.
@AutoConfigureMockMvc //Configura el manejo de endpoints (post, put, get, delete, patch).
@WithMockUser //Habilita la autenticación (evita el error 403)
@AutoConfigureJsonTesters //Permite manejarse con DTO en lugar de requerir JSON.
class AppointmentControllerTest {

    @Autowired //Como el test está fuera del contexto de SpringBoot no pueden hacerse por constructor.
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentRegisterData> appointmentRegisterJacksonTester;

    @Autowired
    private JacksonTester<AppointmentShowData> appointmentShowJacksonTester;

    @MockBean //Suplanta el service y evita hacer la llamada a base de datos original.
    private AppointmentServiceImpl appointmentServiceImpl;

    @Test
    @DisplayName("Should return http 400 when provided data are invalid.") //Hace post sin Json body.
    void registerAppointmentScenario1() throws Exception {
        var response = mvc.perform(post("/appointment")).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    /*
    @Test
    @DisplayName("Should return http 200 when provided data are valid.")
    void registerAppointmentScenario2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGIA;
        var showedData = new AppointmentShowData(null, 2l, 2l, date, specialty);

        when(appointmentServiceImpl.save(any())).thenReturn(showedData);

        var response = mvc.perform(post("/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(appointmentRegisterJacksonTester.write(new AppointmentRegisterData(2l, 2l, date, specialty))
                        .getJson())
        ).andReturn().getResponse();

        var expectedJson = appointmentShowJacksonTester.write(showedData).getJson();

        assertEquals(response.getContentAsString(), expectedJson);
    }*/

    @Test
    @DisplayName("Should return http 404 when request is for an non existent object.")
    void registerAppointmentScenario3() throws Exception {
        var response = mvc.perform(post("/appointmentO")).andReturn().getResponse();

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
    }
}


//Hacer pruebas para validaciones.

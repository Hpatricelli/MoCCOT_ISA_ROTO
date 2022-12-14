package ar.edu.undef.fie.moccot_isa.interfaces;

import ar.edu.undef.fie.moccot_isa.models.Request.OrganizacionRequest;
import ar.edu.undef.fie.moccot_isa.models.entities.Organizacion;
import ar.edu.undef.fie.moccot_isa.models.entities.Persona;
import ar.edu.undef.fie.moccot_isa.models.responses.OrganizacionResponse;
import ar.edu.undef.fie.moccot_isa.models.responses.PersonaResponse;
import ar.edu.undef.fie.moccot_isa.services.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrganizacionController {

    @Autowired
    private OrganizacionService service;

    public OrganizacionController(OrganizacionService service) {this.service = service;}

    //muestra la lista de organizaciones
    @GetMapping("/organizaciones")
    public List<OrganizacionResponse> consultarTodasLasOrganizaciones() {
        var response =
                service
                        .findAll()
                        .stream()
                        .map(Organizacion::response)
                        .collect(Collectors.toList());
        return response;

    }
    @PostMapping("/organizacion")
    public OrganizacionResponse alta(@RequestBody OrganizacionRequest organizacion) {
        return service
                .save(organizacion.toEntity())
                .response();
    }


    @PatchMapping("/organizacion/{id}")
    public OrganizacionResponse modificarOrganizacion(
            @PathVariable Long id,
            @RequestParam boolean status
    ){
        service.findById(id).setStatus(status);
        service.modificar(service.findById(id));

        return service
                .findById(id)
                .response();
    }

}

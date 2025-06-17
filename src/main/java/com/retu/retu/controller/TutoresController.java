package com.retu.retu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.retu.retu.entity.Rol;
import com.retu.retu.entity.Tutor;
import com.retu.retu.repository.RolRepository;
import com.retu.retu.repository.TutorRepository;

@Controller
@RequestMapping("/tutores")
public class TutoresController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private RolRepository rolRepository;


    @GetMapping
    public String listarTutores(Model model) {
        List<Tutor> tutores = tutorRepository.findAll();
        model.addAttribute("tutores", tutores);
        return "tutores/lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("tutor", new Tutor());
        model.addAttribute("roles", rolRepository.findAll());
        return "tutores/crear";
    }

    @PostMapping
    public String guardarTutor(@ModelAttribute Tutor tutor, Model model) {
        if (!tutor.getCorreo().toLowerCase().endsWith("@ues.edu.sv")) {
            model.addAttribute("errorCorreo", "El correo debe pertenecer al dominio @ues.edu.sv");
            model.addAttribute("tutor", tutor); // para que no se borre lo que el usuario ya escribió
            return "tutores/crear";
        }
    
        String passIngresada = tutor.getContrasena();
        tutor.setContrasena("{noop}" + passIngresada);
        tutorRepository.save(tutor);
        return "redirect:/tutores";
    }
    
        @GetMapping("/editar/{id}")
        public String editarTutor(@PathVariable("id") Long id, Model model) {
            Tutor tutor = tutorRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Tutor no encontrado, ID = " + id));

            // Asignar rol vacío si es null (para evitar errores en el formulario)
            if (tutor.getRol() == null) {
                tutor.setRol(new Rol()); // Asignamos un objeto vacío para evitar null en el form
            }

            model.addAttribute("tutor", tutor);
            model.addAttribute("roles", rolRepository.findAll());
            return "tutores/editar";
        }

    @PostMapping("/actualizar/{id}")
    public String actualizarTutor(@PathVariable("id") Long id, @ModelAttribute Tutor tutor, Model model) {
        if (!tutor.getCorreo().toLowerCase().endsWith("@ues.edu.sv")) {
            model.addAttribute("errorCorreo", "El correo debe pertenecer al dominio @ues.edu.sv");
            model.addAttribute("tutor", tutor);
            model.addAttribute("roles", rolRepository.findAll());
            return "tutores/editar";
        }

        // Obtener el ID del rol seleccionado
        Long rolId = tutor.getRol() != null ? tutor.getRol().getId() : null;
        if (rolId != null) {
            Rol rol = rolRepository.findById(rolId)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID = " + rolId));
            tutor.setRol(rol);
        } else {
            model.addAttribute("errorCorreo", "Debe seleccionar un rol válido");
            model.addAttribute("tutor", tutor);
            model.addAttribute("roles", rolRepository.findAll());
            return "tutores/editar";
        }

        tutor.setId(id);
        tutor.setContrasena("{noop}" + tutor.getContrasena());
        tutorRepository.save(tutor);
        return "redirect:/tutores";
    }
  
    @GetMapping("/eliminar/{id}")
    public String eliminarTutor(@PathVariable("id") Long id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor no encontrado, ID = " + id));
        tutorRepository.delete(tutor);
        return "redirect:/tutores";
    }

    @GetMapping("/api/filtrar")
    @org.springframework.web.bind.annotation.ResponseBody
    public List<Tutor> filtrarTutores(
            @org.springframework.web.bind.annotation.RequestParam(required = false) String nombre,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String materia,
            @org.springframework.web.bind.annotation.RequestParam(required = false) String correo) {
        List<Tutor> tutores = tutorRepository.findAll();
        if (nombre != null && !nombre.isEmpty()) {
            tutores = tutores.stream().filter(t -> t.getNombre().toLowerCase().contains(nombre.toLowerCase())).toList();
        }
        if (materia != null && !materia.isEmpty()) {
            tutores = tutores.stream().filter(t -> t.getMateria().toLowerCase().contains(materia.toLowerCase())).toList();
        }
        if (correo != null && !correo.isEmpty()) {
            tutores = tutores.stream().filter(t -> t.getCorreo().toLowerCase().contains(correo.toLowerCase())).toList();
        }
        return tutores;
    }
}

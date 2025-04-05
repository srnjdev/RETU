package com.retu.retu.controller;

import com.retu.retu.entity.Tutor;
import com.retu.retu.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tutores")
public class TutoresController {

    @Autowired
    private TutorRepository tutorRepository;

    @GetMapping
    public String listarTutores(Model model) {
        List<Tutor> tutores = tutorRepository.findAll();
        model.addAttribute("tutores", tutores);
        return "tutores/lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("tutor", new Tutor());
        return "tutores/crear";
    }

    @PostMapping
    public String guardarTutor(@ModelAttribute Tutor tutor) {
        String passIngresada = tutor.getContrasena(); 
        tutor.setContrasena("{noop}" + passIngresada);
        tutorRepository.save(tutor);
        return "redirect:/tutores";
    }

    @GetMapping("/editar/{id}")
    public String editarTutor(@PathVariable("id") Long id, Model model) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor no encontrado, ID = " + id));
        model.addAttribute("tutor", tutor);
        return "tutores/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarTutor(@PathVariable("id") Long id, @ModelAttribute Tutor tutor) {
        tutor.setId(id);

        String passIngresada = tutor.getContrasena(); 
        tutor.setContrasena("{noop}" + passIngresada);
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
}

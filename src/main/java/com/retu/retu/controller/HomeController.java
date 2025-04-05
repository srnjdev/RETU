package com.retu.retu.controller;

import com.retu.retu.entity.Tarea;
import com.retu.retu.entity.Tutor;
import com.retu.retu.repository.TareaRepository;
import com.retu.retu.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TutorRepository tutorRepository;

    // Ruta principal (dashboard)
    @GetMapping("/home")
    public String mostrarDashboard(Model model) {
        // Obtenemos la lista de todos los tutores y tareas
        List<Tutor> tutores = tutorRepository.findAll();
        List<Tarea> tareas = tareaRepository.findAll();

        // Enviamos ambas listas a la vista
        model.addAttribute("tutores", tutores);
        model.addAttribute("tareas", tareas);

        return "home";
    }
}

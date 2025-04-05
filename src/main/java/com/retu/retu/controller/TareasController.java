package com.retu.retu.controller;

import com.retu.retu.entity.Tarea;
import com.retu.retu.repository.TareaRepository;
import com.retu.retu.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tareas")
public class TareasController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public String listarTareas(Model model) {
        List<Tarea> tareas = tareaRepository.findAll();
        model.addAttribute("tareas", tareas);
        return "tareas/lista";
    }

    @GetMapping("/crear")
    public String crearTareaForm(Model model) {
        model.addAttribute("tarea", new Tarea());
        return "tareas/crear";
    }

    @PostMapping
    public String guardarTarea(@ModelAttribute Tarea tarea) {
        tareaRepository.save(tarea);
        return "redirect:/tareas";
    }

    @GetMapping("/editar/{id}")
    public String editarTarea(@PathVariable("id") Long id, Model model) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada, ID = " + id));
        model.addAttribute("tarea", tarea);
        return "tareas/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarTarea(@PathVariable("id") Long id, @ModelAttribute Tarea tarea) {
        tarea.setId(id);
        tareaRepository.save(tarea);
        return "redirect:/tareas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable("id") Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada, ID = " + id));
        tareaRepository.delete(tarea);
        return "redirect:/tareas";
    }

    @GetMapping("/pdf")
    public String generarPdf() {
        List<Tarea> tareas = tareaRepository.findAll();
        byte[] pdfBytes = pdfService.generarPdfConTareas(tareas);
        return "redirect:/tareas";
    }
}

package com.ticketssoporteUsuario.ticketssoporteUsuario.controllers;

import com.ticketssoporteUsuario.ticketssoporteUsuario.entity.Ticket;
import com.ticketssoporteUsuario.ticketssoporteUsuario.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class TicketController {

    @Autowired
    private ITicketService iTicketService;
    @Value("${spring.config.location}")
    private String rootPath;

    @RequestMapping( {"/","","/Inicio"})
    public String Inicio(){

        return "/layout/layout";
    }


    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String Listar(Model model){

        model.addAttribute("titulo","Lista de Tickets");
        model.addAttribute("tickets",iTicketService.findAll());

        return "listar";
    }

    @RequestMapping(value = "/form")
    public String crear (Map<String,Object> model ){
        Ticket ticket = new Ticket();

        model.put("ticket", ticket);
        model.put("titulo","Formulario del ticket");
        return "form";

    }


    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String guardar(Ticket ticket, RedirectAttributes flash, @RequestParam("file") MultipartFile foto){
        ticket.setCreateAt(new Date());
        if(!foto.isEmpty()){

            String rootPath = "C://WIndows//temp//uploads";

            try {
                byte[] bytes= foto.getBytes();
                String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path RutaArchivo = Paths.get(rootPath + "//" + uniqueFilename);
                Files.write(RutaArchivo, bytes);
                flash.addFlashAttribute("info", "Archivo cargado correctamente");

                ticket.setFoto(uniqueFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String mensajeFls=(ticket.getId() != null)?"Registro editado exitosamente": "Registro de Ticket correcto";
        iTicketService.save(ticket);
        flash.addFlashAttribute("success",mensajeFls);
        return "redirect:listar";
    }




    @RequestMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String,Object>model , RedirectAttributes flash){
        Ticket ticket = iTicketService.findOne(id);

        if (ticket==null){
            flash.addFlashAttribute("error","El ticket no existe");
            return "redirect:listar";
        }
        model.put("ticket", ticket);
        model.put("titulo","Informacion de Tickets");


        return "ver";

    }

    @RequestMapping(value="/form/{id}")
    public String editar(@PathVariable(value="id") Long id, Map<String, Object> model,RedirectAttributes  flash) {
        Ticket ticket  = null;
        if (id > 0) {
            ticket = iTicketService.findOne(id);
            if(ticket==null){
                flash.addFlashAttribute("info","No hay registro de este ticket.");
                return "redirect:/listar";

            }
        }else{

            flash.addFlashAttribute("error","EL id no debe ser 0.");
            return "redirect:/listar";

        }
        model.put("ticket", ticket);
        model.put("titulo", "Editar el ticket");
        return "form";
    }

    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes  flash){
        if(id>0){
            iTicketService.delete(id);
            flash.addFlashAttribute("success","Eliminado con exito.");

        }

        return "redirect:/listar";

    }
}

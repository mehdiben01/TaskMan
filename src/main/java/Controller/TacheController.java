package Controller;

import Model.Tache;
import Repository.ProjectRepository;
import Repository.TacheRepository;
import Service.ProjectService;
import Service.TacheService;
import Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TacheController {

    List<String> errors = new ArrayList<>();
    @Autowired
    private TacheRepository tacheRepository;
    @Autowired

    private TacheService tacheService;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    public  TacheController(TacheRepository tacheRepository, TacheService tacheService){
        this.tacheRepository = tacheRepository;
        this.tacheService = tacheService;
    }

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ProjectService projects;


    @GetMapping("/tache")
    public String getTache(Model model){
        Tache tache = new Tache();
        model.addAttribute("tache",tache);
        model.addAttribute("users",utilisateurService.getAllUtilisateurs() );
        model.addAttribute("projects", projects.getAllProject());
        model.addAttribute("task",tacheService.getAllTask());
        model.addAttribute("activePage", "tache");
        return "admin/tache";
    }
    @PostMapping("/tache/save")
    public String saveUtilisateur(@ModelAttribute @Valid Tache tache, HttpServletRequest request, RedirectAttributes redirectAttributes , BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Il y a des erreurs de validation.");
            return "redirect:/tache";
        }
        // Retrieve the project's start and end dates
        List<Object[]> projectDatesList = projectRepository.findProjectDatesById(tache.getProject().getId());



        Object[] projectDates = projectDatesList.get(0); // Retrieve the first row

        // Check if the task's start date is after the project's start date
        LocalDate taskStartDate = LocalDate.parse(tache.getDate_debut());
        LocalDate projectStartDate = LocalDate.parse(projectDates[0].toString());
        if (taskStartDate.isBefore(projectStartDate)) {
            redirectAttributes.addFlashAttribute("errors", "Vérifier les dates du projet.");
            return "redirect:/tache";
        }

        // Check if the task's end date is before the project's end date
        LocalDate taskEndDate = LocalDate.parse(tache.getDate_fin());
        LocalDate projectEndDate = LocalDate.parse(projectDates[1].toString());
        if (taskEndDate.isAfter(projectEndDate)) {
            redirectAttributes.addFlashAttribute("errors", "Vérifier les dates du projet.");
            return "redirect:/tache";
        }

        // Vérifier si l'utilisateur existe déjà
        boolean champExiste = tacheRepository.existsByTitleAndProjectAndUsers(tache.getTitle(), tache.getProject(), tache.getUsers());
        if (champExiste) {
            // Gérer le cas où l'utilisateur existe déjà
            redirectAttributes.addFlashAttribute("message", "La tache existe déjà.");
            return "redirect:/tache";
        }



        // Enregistrer l'utilisateur en utilisant votre service
        redirectAttributes.addFlashAttribute("messagesu", "La tache a été ajouté avec succès.");
        tacheService.save(tache);

        return "redirect:/tache";
    }
}

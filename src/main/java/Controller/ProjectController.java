package Controller;

import Model.Project;
import Repository.ProjectRepository;
import Service.ClientService;
import Service.ProjectService;
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
import java.util.List;

@Controller
public class ProjectController {

    private ProjectService projectService;

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ClientService service;

    @GetMapping("/projet")
    public String getProjet(Model model){
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("clients", service.getAllClient());
        model.addAttribute("countproj",projectService.countProjects());
        // Retrieve existing projects with average etat
        List<Object[]> existingProjectsAndAverageEtat = projectService.getExistingProjectsAndAverageEtat();
        model.addAttribute("projects", existingProjectsAndAverageEtat);

        model.addAttribute("activePage", "projet");
        return "admin/project";
    }



    @PostMapping("/project/save")
    public String saveUtilisateur(@ModelAttribute @Valid Project project, HttpServletRequest request, RedirectAttributes redirectAttributes , BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Il y a des erreurs de validation.");
            return "redirect:/projet";
        }
        // Vérifier si l'utilisateur existe déjà
        boolean champExiste = projectRepository.existsByTitleOrDescription(project.getTitle(), project.getDescription());
        if (champExiste) {
            // Gérer le cas où l'utilisateur existe déjà
            redirectAttributes.addFlashAttribute("message", "Le projet existe déjà.");
            return "redirect:/projet";
        }



        // Enregistrer l'utilisateur en utilisant votre service
        redirectAttributes.addFlashAttribute("messagesu", "Le projet a été ajouté avec succès.");
        projectService.save(project);

        return "redirect:/projet";
    }


}

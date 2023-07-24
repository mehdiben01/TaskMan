package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {


        @GetMapping("/")
        public String getIndex(Model model){
            model.addAttribute("activePage","index");
            return "admin/index";
        }

        @GetMapping("/profil")
        public String getProfil(Model model){
            model.addAttribute("activePage", "profil");
            return "admin/profil";
        }







}

package Service;

import Model.Project;
import Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;



    public Project save(Project project){
        return projectRepository.save(project);
    }


    public List<Project> getAllProject(){
        return  projectRepository.findAllByIsDeleted("0");
    }

    public long countProjects(){
        return projectRepository.count();
    }

    public List<Object[]> getExistingProjectsAndAverageEtat(){
        return projectRepository.selectExistingProjectsAndAverageEtat();
    }



}

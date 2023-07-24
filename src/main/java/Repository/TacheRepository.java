package Repository;

import Model.Project;
import Model.Tache;
import Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TacheRepository extends JpaRepository<Tache, Integer> {

    boolean existsByTitleAndProjectAndUsers(String title, Project project, Utilisateur users );

    List<Tache> findAllByIsDeleted(String isDeleted);

    long count();

}

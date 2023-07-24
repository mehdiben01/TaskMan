package Repository;

import Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsByTitleOrDescription(String title, String description);

    List<Project> findAllByIsDeleted(String isDeleted);

    long count();


    @Query("SELECT p, AVG(t.etat), COUNT(t.id) FROM Project p LEFT JOIN p.taches t WHERE p.isDeleted = '0' GROUP BY p")
    List<Object[]> selectExistingProjectsAndAverageEtat();



    @Query("SELECT p.date_debut, p.date_fin FROM Project p WHERE p.id = :projectId")
    List<Object[]> findProjectDatesById(@Param("projectId") int projectId);
}

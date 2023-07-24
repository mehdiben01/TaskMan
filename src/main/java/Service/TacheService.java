package Service;

import Model.Tache;
import Repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    public Tache save(Tache tache){
        return  tacheRepository.save(tache);
    }

    public List<Tache> getAllTask(){
        return tacheRepository.findAllByIsDeleted("0");
    }

    public long countTask(){
        return tacheRepository.count();
    }


}

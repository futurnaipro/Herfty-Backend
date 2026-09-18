package artisanat.artisanat.model.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.Entities.ChildPanier;
import artisanat.artisanat.model.Entities.Panier;
import artisanat.artisanat.model.Entities.PanierArticlesPK;
import artisanat.artisanat.model.Repositories.ChildPanierRepository;
import artisanat.artisanat.model.Repositories.PanierArticlesRepository;
import artisanat.artisanat.model.Repositories.PanierRepository;

@Service
public class PanierService {

    @Autowired
    ChildPanierRepository childPanierRepository;

    @Autowired
    PanierRepository panierRepository;

    @Autowired
    PanierArticlesRepository panierArticlesRepository;

    public Panier savePanier(Panier panier){
        return panierRepository.save(panier);
    }

    public ChildPanier saveChildPanier(ChildPanier panier) {
        return childPanierRepository.save(panier);
    }

    public void deleteById(PanierArticlesPK id){
        panierArticlesRepository.deleteById(id);
    }


}

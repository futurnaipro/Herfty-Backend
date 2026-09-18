package artisanat.artisanat.model.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.PanierArticles;
import artisanat.artisanat.model.Entities.PanierArticlesPK;

@Repository
public interface PanierArticlesRepository  extends JpaRepository<PanierArticles,PanierArticlesPK> {

    PanierArticles findByArticleId(Long id);
}

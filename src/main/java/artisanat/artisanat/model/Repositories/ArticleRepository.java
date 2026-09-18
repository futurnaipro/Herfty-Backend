package artisanat.artisanat.model.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import artisanat.artisanat.model.Entities.Article;
import artisanat.artisanat.model.Entities.Article.Categorie;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findAllByArtisanId(Long id);
    List<Article> findAllByCategorie(Categorie categorie);
    Article findArticleById(Long id);
    List<Article> findAllByNomContainingIgnoreCase(String q);
}

package artisanat.artisanat.model.Services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import artisanat.artisanat.model.Entities.Article;
import artisanat.artisanat.model.Entities.Article.Categorie;
import artisanat.artisanat.model.Repositories.ArticleRepository;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public Article addArticle(Article article){
        return articleRepository.save(article);
    }

    public List<Article> getArticlesByArtisanId(Long id){
        return articleRepository.findAllByArtisanId(id);
    }

    public void deleteArticleById(Long id){
        articleRepository.deleteById(id);
    }

    public List<Article> getAllArticles(){
       return articleRepository.findAll();
    }
    public List<Article> getAllByCategorie(Categorie categorie){
        return articleRepository.findAllByCategorie(categorie);
     }
 

    public Article updateArticle(Article article,Long id){
        Article oldArticle=articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found with id provided " ));;
        article.setArtisan(oldArticle.getArtisan());
        article.setId(id);
        BeanUtils.copyProperties(article, oldArticle, "id");
      return  articleRepository.save(oldArticle);
    }

    public Article getArticleById(Long id){
        return articleRepository.findArticleById(id);
    }
    
}

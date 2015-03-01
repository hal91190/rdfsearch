package hal.rdfsearch;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DCTerms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static hal.rdfsearch.RDFSearch.ENVIRONMENT;

/**
 * Permet l'indexation d'un jeu de données RDF contenant des livres.
 *
 * @author hal
 * @version mars 2015
 */
public class RDFBookIndexer {
    /** Journal. */
    public static final Logger logger = LoggerFactory.getLogger(RDFBookIndexer.class);

    /** Modèle Jena pour les livres. */
    private Model bookModel;

    /**
     * Initialise le modèle à partir du jeu de données RDF.
     */
    public RDFBookIndexer() {
        logger.info("Initialisation du modèle Jena à partir de {}", ENVIRONMENT.getRDFFilename());
        bookModel = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(ENVIRONMENT.getRDFFilename());
        bookModel.read(in, "");
    }

    public void indexBooks() {
        logger.info("Indexation des livres, i.e. ressources avec un titre");
        ResIterator iter = bookModel.listResourcesWithProperty(DCTerms.title);
        while (iter.hasNext()) {
            Resource r = iter.nextResource();
            logger.debug("indexation du livre {}", r);
        }
    }
}

package hal.rdfsearch;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DCTerms;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Date;

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

    /** Répertoire où sera stocké l'index. */
    public static final String indexPath = "index";

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

    public void indexBooks() throws IOException {
        logger.info("Indexation des livres, i.e. ressources avec un titre dans le répertoire {}", indexPath);
        Date start = new Date();

        Directory dir = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // remplace l'index s'il existe
        IndexWriter writer = new IndexWriter(dir, iwc);

        ResIterator iter = bookModel.listResourcesWithProperty(DCTerms.title);
        while (iter.hasNext()) {
            Resource r = iter.nextResource();
            indexBook(r, writer);
        }

        writer.close();

        Date end = new Date();
        logger.info("Indexation en {} millisecondes", end.getTime() - start.getTime());
    }

    /**
     * Ajoute un livre dans l'index.
     *
     * @param livre le livre à ajouter
     * @param writer l'index
     */
    private void indexBook(Resource livre, IndexWriter writer) throws IOException {
        Document doc = new Document();

        Field uriField = new StringField("uri", livre.getURI(), Field.Store.YES);
        doc.add(uriField);

        String title = livre.getProperty(DCTerms.title).getString();
        Field titleField = new TextField("title", title, Field.Store.NO);
        doc.add(titleField);

        logger.debug("indexation du livre \"{}\" ({})", title, livre);
        writer.addDocument(doc);
    }
}

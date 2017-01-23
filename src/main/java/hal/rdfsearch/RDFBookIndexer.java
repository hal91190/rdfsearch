package hal.rdfsearch;

import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
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

    public void indexBooks() throws IOException {
        logger.info("Indexation des livres, i.e. ressources avec un titre dans le répertoire {}", indexPath);
        Date start = new Date();

        //TODO(index) Création de l'index
        Directory dir = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE); // remplace l'index s'il existe
        IndexWriter writer = new IndexWriter(dir, iwc);

        //TODO(SPARQL) réécrire la portion de code ci-dessous avec SPARQL
        ResIterator iter = ENVIRONMENT.bookModel.listResourcesWithProperty(DCTerms.title);
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
        //TODO(index) indexation d'un document
        Document doc = new Document();

        Field uriField = new StringField("uri", livre.getURI(), Field.Store.YES);
        doc.add(uriField);

        String title = livre.getProperty(DCTerms.title).getString();
        Field titleField = new TextField("title", title, Field.Store.NO);
        doc.add(titleField);

        //TODO(new fields) ajouter l'indexation de l'isbn et des auteurs

        logger.debug("indexation du livre \"{}\" ({})", title, livre);
        writer.addDocument(doc);
    }
}

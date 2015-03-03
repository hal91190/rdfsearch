package hal.rdfsearch;

import com.hp.hpl.jena.rdf.model.Resource;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static hal.rdfsearch.RDFSearch.ENVIRONMENT;

/**
 * Permet la recherche dans l'index des livres.
 *
 * @author hal
 * @version mars 2015
 */
public class RDFBooKSearcher {
    /** Journal. */
    public static final Logger logger = LoggerFactory.getLogger(RDFBooKSearcher.class);

    /** Répertoire où est stocké l'index. */
    public static final String indexPath = "index";

    /** Champs de recherche par défaut. */
    public static final String defaultField = "title";

    /**
     * Recherche les livres à partir de l'index.
     *
     * @return la liste des films trouvés
     */
    public List<Resource> search() throws IOException, ParseException {
        logger.info("Exécution de la requête \"{}\" sur l'index {}", ENVIRONMENT.getQuery(), indexPath);

        //TODO(search)
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser(defaultField, analyzer);
        Query query = parser.parse(ENVIRONMENT.getQuery());
        TopDocs results = searcher.search(query, 1000);
        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = results.totalHits;
        logger.info("{} résultats", numTotalHits);
        List<Resource> films = new ArrayList<>(numTotalHits);
        for (int i = 0; i < numTotalHits; i++) {
            Document doc = searcher.doc(hits[i].doc);
            logger.trace("Document {} ({})", doc.get("uri"), hits[i].score);
            String uri = doc.get("uri");
            if (uri != null) {
                Resource film = ENVIRONMENT.bookModel.getResource(uri);
                films.add(film);
            }
        }
        return films;
    }
}

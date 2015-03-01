/**
 * La classe principale de l'application.
 *
 */
package hal.rdfsearch;

import com.hp.hpl.jena.rdf.model.Resource;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * La classe principale de l'application.
 *
 * @version mars 2015
 * @author hal
 */
public enum RDFSearch {
    ENVIRONMENT;

    /** Journal. */
    public static final Logger logger = LoggerFactory.getLogger(RDFSearch.class);

    /** Configuration de l'application. */
    private final Properties configuration;

    private RDFSearch() {
        configuration = new Properties();
    }

    /*
     * Main method of the program.
     * @param args command line arguments
     */
    private void run(String[] args) throws IOException, ParseException {
        parseCommandLine(args);
        if (configuration.getProperty("action").equals("index")) {
            RDFBookIndexer bookIndexer = new RDFBookIndexer();
            bookIndexer.indexBooks();
        } else {
            RDFBooKSearcher booKSearcher = new RDFBooKSearcher();
            List<Resource> films = booKSearcher.search();
        }
        logger.info("Fin de l'exécution");
    }

    /**
     * Analyse la ligne de commande et configure l'application.
     *
     * @param args command line arguments
     */
    private void parseCommandLine(String[] args) {
        logger.info("Analyse de la ligne de commande");
        if (args[0].equals("-i")) { // indexation
            if (args.length != 2 || !Files.isReadable(Paths.get(args[1]))) {
                throw new IllegalArgumentException();
            }
            configuration.setProperty("action", "index");
            configuration.setProperty("rdf-file", args[1]);
            logger.info("Configuration : indexation de {}", args[1]);
        } else { // recherche
            configuration.setProperty("action", "search");
            configuration.setProperty("rdf-file", args[0]);
            configuration.setProperty("query", args[1]);
            logger.info("Configuration : recherche de \"{}\"", args[1]);
        }
    }

    /**
     * Retourne le nom du fichier contenant le jeu de données RDF.
     *
     * @return le nom du fichier
     */
    public String getRDFFilename() {
        return configuration.getProperty("rdf-file");
    }

    /**
     * Retourne la requête.
     *
     * @return la requête
     */
    public String getQuery() {
        return configuration.getProperty("query");
    }

    /**
     * Class method call by Java VM when starting the application.
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        ENVIRONMENT.run(args);
    }
}

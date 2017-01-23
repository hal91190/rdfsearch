/**
 * La classe principale de l'application.
 *
 */
package hal.rdfsearch;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private final Logger logger = LoggerFactory.getLogger(RDFSearch.class);

    /** Configuration de l'application. */
    private final Properties configuration;

    /** Modèle Jena pour les livres. */
    final Model bookModel;

    private RDFSearch() {
        configuration = new Properties();
        bookModel = ModelFactory.createDefaultModel();
    }

    /*
     * Main method of the program.
     * @param args command line arguments
     */
    private void run(String[] args) throws IOException, ParseException {
        parseCommandLine(args);
        loadRDFData();
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
     * Charge le modèle Jena à partir du fichier de données RDF.
     * Cette méthode doit impérativement être appelée après la méthode
     * qui analyse les arguments de ligne de dommande.
     *
     */
    private void loadRDFData() throws IOException {
        logger.info("Initialisation du modèle Jena à partir de {}", getRDFFilename());
        InputStream in = FileManager.get().open(getRDFFilename());
        bookModel.read(in, null);
        in.close();
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

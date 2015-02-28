/**
 * La classe principale de l'application.
 *
 */
package hal.rdfsearch;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.RDFS;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * La classe principale de l'application.
 *
 * @version mars 2015
 * @author hal
 */
public enum RDFSearch {
    ENVIRONMENT;

    /** Nom du fichier de configuration de l'application. */
    static final String cfgFileName = "RDFSearch.properties";

    /** Configuration de l'application. */
    private Properties configuration;

    private RDFSearch() {
        configuration = new Properties();
    }

    /*
     * Main method of the program.
     * @param args command line arguments
     */
    public void run(String[] args) throws IOException {
        chargeConfiguration();
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(configuration.getProperty("rdf-file"));
        model.read(in, "");

        ResIterator iter = model.listResourcesWithProperty(DCTerms.title);
        while (iter.hasNext()) {
            Resource r = iter.nextResource();
            System.out.println(r);
        }
        System.out.println("Fini !");
    }

    /**
     * Cherche dans les ressources et charge la configuration.
     * @throws IOException
     */
    public void chargeConfiguration() throws IOException {
        InputStream cfgInputStream = getClass().getClassLoader().getResourceAsStream(cfgFileName);
        configuration.load(cfgInputStream);
    }

    /**
     * Class method call by Java VM when starting the application.
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException {
        ENVIRONMENT.run(args);
    }
}

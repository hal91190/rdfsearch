import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test minimal de l'API Jena..
 *
 * @version mars 2015
 * @author hal
 */
public class JenaTest {
    static final String uri = "http://www.prism.uvsq.fr/~hal";
    static final String fullName = "Stéphane Lopes";
    static final String givenName = "Stéphane";
    static final String familyName = "Lopes";

    Model rdfGraph;

    @BeforeEach
    public void setUp() {
        rdfGraph = ModelFactory.createDefaultModel();
        Resource sLopes = rdfGraph.createResource(uri)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        rdfGraph.createResource()
                                .addProperty(VCARD.Given, givenName)
                                .addProperty(VCARD.Family, familyName));
    }

    @Test
    public void simpleModelBuilding() {
        Resource sLopes = rdfGraph.getResource(uri);;
        assertEquals("Stéphane Lopes", sLopes.getProperty(VCARD.FN).getString(), fullName);

        Resource name = sLopes.getProperty(VCARD.N).getResource();
        assertAll("Nom et prénom",
          () -> assertEquals("Stéphane", name.getProperty(VCARD.Given).getString(), givenName),
          () -> assertEquals("Lopes", name.getProperty(VCARD.Family).getString(), familyName)
        );
    }
}

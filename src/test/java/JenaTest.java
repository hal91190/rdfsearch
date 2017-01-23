import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

    @Before
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
        assertThat("Le nom complet est incorrect.", sLopes.getProperty(VCARD.FN).getString(), is(fullName));
        Resource name = sLopes.getProperty(VCARD.N).getResource();
        assertThat("Le prénom est incorrect.", name.getProperty(VCARD.Given).getString(), is(givenName));
        assertThat("Le nom de famille est incorrect.", name.getProperty(VCARD.Family).getString(), is(familyName));
    }
}
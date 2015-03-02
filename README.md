# rdfsearch
Exemple de projet de recherche par mots-clés dans des données RDF

## Jeu de données RDF
Samples of the Linked Open British National Bibliography data
[lodbnb_books.zip](http://www.bl.uk/bibliographic/datasamples.html)

## Utilisation du projet exemple
### Indexation du jeu de données RDF
```
$ java -cp ... hal.rdfsearch.RDFSearch -i /home/hal/Documents/Devel/rdfsearch/lodbnb_books.rdf
```

### Recherche dans les données RDF
```
$ java -cp ... hal.rdfsearch.RDFSearch /home/hal/Documents/Devel/rdfsearch/lodbnb_books.rdf "-social +life"
```

## Références
### Gestion de données RDF avec Jena
 * [Site officiel](http://jena.apache.org/) du projet Apache Jena
 * [Tutoriels Jena](http://jena.apache.org/tutorials/index.html)
 * [Référence de l'API](http://jena.apache.org/documentation/javadoc/jena/)
 * [Introduction to Jena](http://www.ibm.com/developerworks/library/j-jena/), IBM developerWorks

### Recherche d'information avec Lucene
 * [Site officiel](http://lucene.apache.org/core/)
 * [Documentation](http://lucene.apache.org/core/5_0_0/index.html)
 * [Module de démonstration](http://lucene.apache.org/core/5_0_0/demo/overview-summary.html#overview_description)


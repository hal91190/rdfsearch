# rdfsearch
Exemple de projet de recherche par mots-clés dans des données RDF

## Jeu de données RDF
Samples of the Linked Open British National Bibliography data
[lodbnb_books.zip](http://www.bl.uk/bibliographic/datasamples.html)

* [Publishing the British National Bibliography as Linked Open Data](https://www.bl.uk/bibliographic/pdfs/publishing_bnb_as_lod.pdf)
* [British Library Data Model - Book](https://www.bl.uk/bibliographic/pdfs/bldatamodelbook.pdf)
* [British Library Data Model - Seria l](https://www.bl.uk/bibliographic/pdfs/bldatamodelserial.pdf)

## Utilisation du projet exemple
### Compilation
```
$ ./gradlew build
```

### Indexation du jeu de données RDF
```
$ ./gradlew run --args="-i BNBLODB_sample.nt"
```

### Recherche dans les données RDF
```
$ ./gradlew run --args="BNBLODB_sample.nt '-social +life'"
```

## Questions
### Exploration du jeu de données
 * Quelles données sont décrites dans ce fichier RDF ?
 * Quels vocabulaires RDF sont utilisés dans ce document ? Que représentent-ils ?
 * De quel type sont les livres ?
 * Quelle propriété représente les auteurs d'un livre ?

### Gestion de données RDF avec Jena
 * Quelle interface de la bibliothèque Jena représente un jeu de données RDF (ensemble de triplets) ?
 * Quelles étapes permettent de créer et de charger un jeu de données RDF à partir d'un fichier ?
 * Quelles méthodes permettent de naviguer dans un jeu de données RDF ?
 * Comment utiliser le langage SPARQL avec la bibliothèque Jena ?
   Chercher la chaîne "TODO(SPARQL)" dans le projet.

### Indexation avec Lucene
 * Expliquer le code de construction d'un index Lucene ("TODO(index)")
  * En particulier, quel est le rôle de l'analyseur ?
  * Quels rôles jouent le document et les différents types de champs (StringField, TextField, ...) ?
 * Ajouter l'indexation de l'isbn et des auteurs ("TODO(new fields)")

### Rechercher dans l'index Lucene
 * Expliquer le code de recherche dans un index Lucene ("TODO(search)")
  * En particulier, quels rôles jouent les classes QueryParser, Query, TopDocs et ScoreDoc ?
 * Générer une page HTML présentant les résultats de la recherche (titre du document, score et lien la page de la *British Library*)

## Références
### Gestion de données RDF avec Jena
 * [Site officiel](http://jena.apache.org/) du projet Apache Jena
 * [Tutoriels Jena](http://jena.apache.org/tutorials/index.html)
   * [Introduction au RDF et à l'API RDF de Jena](https://web-semantique.developpez.com/tutoriels/jena/introduction-rdf/)
   * [ARQ - API Application](https://web-semantique.developpez.com/tutoriels/jena/arq/api-application/)
   * [Comment utiliser les entrées/sorties RDF de Jena](https://web-semantique.developpez.com/tutoriels/jena/io/)
   * [Le tutoriel SPARQL](https://web-semantique.developpez.com/tutoriels/jena/arq/introduction-sparql/)
 * [Référence de l'API](http://jena.apache.org/documentation/javadoc/jena/)
 * [Introduction to Jena](http://www.ibm.com/developerworks/library/j-jena/), IBM developerWorks

### Recherche d'information avec Lucene
 * [Site officiel](http://lucene.apache.org/core/)
 * [Documentation](http://lucene.apache.org/core/5_0_0/index.html)
 * [Module de démonstration](http://lucene.apache.org/core/5_0_0/demo/overview-summary.html#overview_description)


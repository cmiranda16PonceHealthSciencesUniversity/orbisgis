package org.orbisgis.wpsservicescripts.scripts.Vector.Create

import org.orbisgis.wpsgroovyapi.attributes.Keyword
import org.orbisgis.wpsgroovyapi.attributes.LanguageString
import org.orbisgis.wpsgroovyapi.input.DataStoreInput
import org.orbisgis.wpsgroovyapi.input.LiteralDataInput
import org.orbisgis.wpsgroovyapi.output.LiteralDataOutput
import org.orbisgis.wpsgroovyapi.process.Process

/********************/
/** Process method **/
/********************/

/**
 * This process is used to create a grid of polygons.
 *
 * @return A datadase table.
 * @author Erwan BOCHER
 * @author Sylvain PALOMINOS
 */
@Process(
        traducedTitles = [
                @LanguageString(value = "Create a grid of polygons", lang = "en"),
                @LanguageString(value = "Création d'une grille de polygones", lang = "fr")
        ],
        traducedResumes = [
                @LanguageString(value = "Create a grid of polygons.", lang = "en"),
                @LanguageString(value = "Création d'une grille de polygones.", lang = "fr")
        ],
        traducedKeywords = [
                @Keyword(traducedKeywords = [
                        @LanguageString(value = "Vector", lang = "en"),
                        @LanguageString(value = "Vecteur", lang = "fr")
                ]),
                @Keyword(traducedKeywords = [
                        @LanguageString(value = "Geometry", lang = "en"),
                        @LanguageString(value = "Géometrie", lang = "fr")
                ]),
                @Keyword(traducedKeywords = [
                        @LanguageString(value = "Create", lang = "en"),
                        @LanguageString(value = "Création", lang = "fr")
                ])
        ])
def processing() {

    //Build the start of the query
    String query = "CREATE TABLE "+outputTableName+" AS SELECT * from ST_MakeGrid('"+inputDataStore+"',"+x_distance+","+y_distance+")"
    
    //Execute the query
    sql.execute(query)
    literalOutput = "Process done"
}


/****************/
/** INPUT Data **/
/****************/

@DataStoreInput(
        traducedTitles = [
                @LanguageString(value = "Input spatial data", lang = "en"),
                @LanguageString(value = "Données spatiales d'entrée", lang = "fr")
        ],
        traducedResumes = [
                @LanguageString(value = "The spatial data source to compute the grid. The extend of grid is based on the full extend of the table.", lang = "en"),
                @LanguageString(value = "La source de données spatiales utilisée pour le calcul de la grille. L'étendue de la grille se base sur l'étendue de la table.", lang = "fr")
        ],
        dataStoreTypes = ["GEOMETRY"])
String inputDataStore

/**********************/
/** INPUT Parameters **/
/**********************/

@LiteralDataInput(
        traducedTitles = [
                @LanguageString(value = "X cell size", lang = "en"),
                @LanguageString(value = "Taille X des cellules", lang = "fr")
        ],
        traducedResumes = [
                @LanguageString(value = "The X cell size.", lang = "en"),
                @LanguageString(value = "La taille X des cellules.", lang = "fr")
        ])
Double x_distance =1

@LiteralDataInput(
        traducedTitles = [
                @LanguageString(value = "Y cell size", lang = "en"),
                @LanguageString(value = "Taille Y des cellules", lang = "fr")
        ],
        traducedResumes = [
                @LanguageString(value = "The Y cell size.", lang = "en"),
                @LanguageString(value = "La taille Y des cellules.", lang = "fr")
        ])
Double y_distance =1


@LiteralDataInput(
        traducedTitles = [
                @LanguageString(value = "Output table name", lang = "en"),
                @LanguageString(value = "Nom de la table de sortie", lang = "fr")
        ],
        traducedResumes = [
                @LanguageString(value = "Name of the table containing the result of the process.", lang = "en"),
                @LanguageString(value = "Nom de la table contenant les résultats du traitement.", lang = "fr")
        ])
String outputTableName

/*****************/
/** OUTPUT Data **/
/*****************/

/** String output of the process. */
@LiteralDataOutput(
        traducedTitles = [
                @LanguageString(value = "Output message", lang = "en"),
                @LanguageString(value = "Message de sortie", lang = "fr")
        ],
        traducedResumes = [
                @LanguageString(value = "The output message.", lang = "en"),
                @LanguageString(value = "Le message de sortie.", lang = "fr")
        ])
String literalOutput


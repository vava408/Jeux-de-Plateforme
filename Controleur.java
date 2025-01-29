sdsimport ihmgui.FrameGrille;
import ihmgui.Controle;

/**
 * Classe Controleur pour l'exercice 5.
 * 
 * @author Flem Anthony | Joigneau Arsène | Bassam Youssif youssif | Lemaitre
 *         Valentin | Si Ahmed Sara |
 *
 * Date: 20/12/2024 - 10h45
 */


// Classe Controleur
public class Controleur extends Controle
{
	/*------------------------*/
	/* Attributs de la classe */
	/*------------------------*/
	private Plateforme  metier;
	private FrameGrille frame;

	private boolean     action;
	private char        direction;

	private String      sRetPerso;


	// Constructeur de la classe controleur:
	public Controleur()
	{
		/*------------------------------*/
		/* Instructions du Constructeur */
		/*------------------------------*/
		this.metier = new Plateforme();
		this.frame  = new FrameGrille(this);

		this.action     = false;
		this.direction  = ' ';

		this.sRetPerso = "lem";

		/*------------------*/
		/* Initialise l'IHM */
		/*------------------*/
		this.frame.setSize(1350, 700);
		this.frame.setLocation(200, 10);
		this.frame.setTitle("Plateforme");
		this.frame.setVisible(true);
	}


	// Méthode qui initialise les différents boutons permettant d'effectuer différentes actions
	public String setBouton(int numBtn)
	{
		String lib;

		/*-----------------------------------*/
		/* Initialise les différents boutons */
		/*-----------------------------------*/
		switch (numBtn)
		{
			case 0: lib = "Niveau suivant";
				break;
			case 1: lib = "Niveau Précédent";
				break;
			case 2: lib = "Recommencer";
				break;
			case 3: lib = "Changer thème";
				break;
			default:
				lib = null;
		}

		return lib;
	}

	// Méthode qui initialise chaque label
	public String setLabel(int numLbl)
	{
		/*---------*/
		/* Données */
		/*---------*/
		String lib;

		/*---------------------------*/
		/* Initialisation des labels */
		/*---------------------------*/
		switch (numLbl)
		{
			case 0:  lib = "";
				break;
			default: lib = null;
		}

		return lib;
	}


	// Méthode qui permet d'effectuer des actions avec les boutons
	public void bouton(int action)
	{
		switch (action)
		{
			/*----------------*/
			/* Niveau suivant */
			/*----------------*/
			case 0 ->
			{
				if(this.metier.estSortie())
				{
					if (this.metier.niveauSuivant() > 0)
					{
						this.frame.majIHM(); 
					}
				}
			}

			/*------------------*/
			/* Niveau Précédent */
			/*------------------*/
			case 1 ->
			{
					this.metier.niveauPrecedent();
					this.metier.initialiserTableau();
					this.metier.remplirTableau();
					this.frame.majIHM();

			}

			/*----------------------*/
			/* Recommence le niveau */
			/*----------------------*/
			case 2 ->
			{
				this.metier.initialiserTableau();
				this.metier.remplirTableau();
				this.frame.majIHM();
			}

			/*-----------------*/
			/* Change de thème */
			/*-----------------*/
			case 3 -> 
			{
				this.changerTheme();
			} 
		} 
	}


	// Méthode qui affiche un message quand on a atteint la porte.
	public String setTextLabel(int numLbl)
	{
		/*---------*/
		/* Données */
		/*---------*/
		String textLbl = "";

		/*-------------------------------*/
		/* Message des différents labels */
		/*-------------------------------*/
		if (this.metier.estSortie() && numLbl == 0) { textLbl = "Gagné(e) !!"; }

		return textLbl;
	}


	// Accesseurs de la classe:
	public int     setNbLigne        () { return metier.getNbLignes  ()               ; }
	public int     setNbColonne      () { return metier.getNbColonnes()               ; }
	public boolean setNumLigneColonne() { return true                                 ; }
	public int     setLargeurImg     () { return 52                                   ; }
	public String  setFondGrille     () { return "images/"+this.sRetPerso+"/fond.png" ; }


	// Méthode qui lance l'IHM pour jouer.
	public void lancer()
	{
		/*--------------------------------------------*/
		/* Lance l'IHM du jeu et permet donc de jouer */
		/*--------------------------------------------*/
		while (!this.metier.estSortie())
		{
			if (this.action)
			{
				this.metier.deplacer(this.direction);
				this.metier.estSortie();
				this.frame.majIHM();

				action = false;
			}

			// Temporisation nécessaire pour ne pas prendre toutes les ressources
			try { Thread.sleep(25); } catch (InterruptedException ex) { }
		}
	}


	// Méthode jouer qui permet d'utiliser les touches du clavier.
	public void jouer(String touche)
	{
		if (touche.equals("FL-H"))
		{
			this.metier.deplacer('N');
			this.direction = 'N';
		}

		if (touche.equals("FL-B"))
		{
			this.metier.deplacer('S');
			this.direction = 'S';
		}

		if (touche.equals("FL-G"))
		{
			this.metier.deplacer('O');
			this.direction = 'O';
		}

		if (touche.equals("FL-D"))
		{
			this.metier.deplacer('E');
			this.direction = 'E';
		}

		if (touche.equals("CR-"))
		{
			this.metier.deplacer('H');
		}
		System.out.println(touche);

		this.frame.majIHM();
	}


	// Méthode setImage qui initialise chaque image en fonction d'un symbole de la grille.
	public String setImage(int ligne, int colonne, int couche)
	{
		/* ------------ */
		/* Données      */
		/* ------------ */
		char symbole = ' ';
		String rep; 
		String sImage = null;

		/* ------------ */
		/* Instructions */
		/* ------------ */
		rep = "images/"+ this.sRetPerso +"/";

		/*-------------------------------------------------------------*/
		/* Détermine Les images à utiliser pour l'environnement du jeu */
		/*-------------------------------------------------------------*/
		if ( couche == 0 )
		{
			symbole = this.metier.getCaseEnvironnement(ligne, colonne);

			if      (symbole == '=') sImage = rep + determinerPosSol    (ligne, colonne);
			else if (symbole == '#') sImage = rep + determinerPosEchelle(ligne, colonne);
			else if (symbole == '@') sImage = rep + "sortie.png"    ;
			else if (symbole == ' ') sImage = rep + "vide52.png"    ;
			else if (symbole == 'b') sImage = rep + "clebleue.png"  ;
			else if (symbole == 'B') sImage = rep + "portebleue.png";
			else if (symbole == 'v') sImage = rep + "cleverte.png"  ;
			else if (symbole == 'V') sImage = rep + "porteverte.png";
			else if (symbole == 'r') sImage = rep + "clerouge.png"  ;
			else if (symbole == 'R') sImage = rep + "porterouge.png";
			else if (symbole == '?') sImage = rep + "boite.png"     ;
		}

		/*-----------------------------------------------------------------------------*/
		/* Détermine Les images à utiliser pour les différents positions du personnage */
		/*-----------------------------------------------------------------------------*/
		if ( couche == 1)
		{
			symbole = this.metier.getCase(ligne, colonne, 1);
			if (symbole == '+') { sImage = rep + determinerPosPerso  (ligne, colonne); }
			else{sImage = "";}
		}
		

		return sImage;
	}


	// Main de la classe qui permet l'exécution du programme.
	public static void main(String[] a)
	{
		new Controleur().lancer();
	}


	// Méthode privée qui renvoie l'image de la position des différents sols.
	private String determinerPosSol(int lig, int col)
	{
		/* ------------ */
		/* Données      */
		/* ------------ */
		String sRet = "";

		boolean gauche, droite;

		/* ------------ */
		/* Instructions */
		/* ------------ */
		gauche = col > 0                             && this.metier.getCaseEnvironnement(lig, col-1) == '=';
		droite = col < this.metier.getNbColonnes()-1 && this.metier.getCaseEnvironnement(lig, col+1) == '=';

		/*------------------------------------------------------------------------*/
		/* Détermine Les images à utiliser pour les différents positions des sols */
		/*------------------------------------------------------------------------*/
		if      ( !gauche && !droite ) sRet = "sol_gauche_droit.png";
		else if ( !gauche            ) sRet = "sol_gauche.png"      ;
		else if ( !droite            ) sRet = "sol_droit.png"       ;
		else if (  gauche &&  droite ) sRet = "sol.png"             ;

		return sRet;
	}


	// Méthode privée qui renvoie l'image de la position de l'échelle.
	private String determinerPosEchelle(int lig, int col)
	{
		/* ------------ */
		/* Données      */
		/* ------------ */
		String sRet = "echelle.png";

		boolean haut, bas;

		/* ------------ */
		/* Instructions */
		/* ------------ */
		bas  = lig > 0                             && this.metier.getCaseEnvironnement(lig - 1, col) == '#';
		haut = lig < this.metier.getNbLignes() - 1 && this.metier.getCaseEnvironnement(lig + 1, col) == '#';

		/*----------------------------------------------------------------------------*/
		/* Détermine Les images à utiliser pour les différents positions des échelles */
		/*----------------------------------------------------------------------------*/
		if      (haut && bas || bas) sRet = "echelle.png"     ;
		else if (haut)               sRet = "echelle_haut.png";

		return sRet;
	}


	// Méthode privée qui renvoie l'image de la position du personnage.
	private String determinerPosPerso(int lig, int col)
	{
		/*-------------*/
		/*   Données   */
		/*-------------*/
		String sRet = "pers_droit.png";

		/*------------------------------------------------------------------------*/
		/* Détermine Les images à utiliser en fonction des différents personnages */
		/*------------------------------------------------------------------------*/
		if ( this.sRetPerso == "link" || this.sRetPerso == "mario" )
		{
			if ( this.direction == 'E' && col % 2 == 0 ) sRet = "pers_droit.png"   ;
			if ( this.direction == 'E' && col % 2 != 0 ) sRet = "pers_droite_2.png";
			if ( this.direction == 'O' && col % 2 == 0 ) sRet = "pers_gauche.png"  ;
			if ( this.direction == 'O' && col % 2 != 0 ) sRet = "pers_gauche_2.png";
		}
		else
		{
			if (this.direction == 'E') sRet = "pers_droit.png";
			if (this.direction == 'O') sRet = "pers_gauche.png";
		}

		/*----------------------------------------------------------------*/
		/* Détermine les images du personnage quand il monte les échelles */
		/*----------------------------------------------------------------*/
		if ( this.metier.getCaseEnvironnement(lig, col) == '#' && lig % 2 == 0 ) { sRet = "pers_monte1.png"; }
		if ( this.metier.getCaseEnvironnement(lig, col) == '#' && lig % 2 != 0 ) { sRet = "pers_monte2.png"; }

		/*-------------------------------------------------------------*/
		/* Détermine l'image du personnage quand il est en chute libre */
		/*-------------------------------------------------------------*/
		if (this.metier.getCase(lig + 1, col, col) == ' ' ) sRet = "pers_tombe.png";

		return sRet;
	}


	// Méthode privée qui change de thème
	private String changerTheme()
	{
		switch ( this.sRetPerso) 
		{
			case "lem"     -> this.sRetPerso = "link";
			case "link"    -> this.sRetPerso = "indiana";
			case "indiana" -> this.sRetPerso = "asterix";
			case "asterix" -> this.sRetPerso = "mario";
			case "mario"   -> this.sRetPerso = "lem";
		}

		this.frame.majIHM();

		return this.sRetPerso;
	}
}

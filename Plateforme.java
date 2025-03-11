import java.util.Scanner;
import java.io.FileInputStream;

/**
 * Classe Controleur pour l'exercice 5.
 * 
 * @author Flem Anthony | Joigneau Arsène | Bassam Youssif youssif | Lemaitre
 *         Valentin | Si Ahmed Sara |
 *
 *         Date: 20/12/2024 - 10h45
 */

// Classe Plateforme
public class Plateforme
{
	/*------------------------*/
	/* Attributs de la classe */
	/*------------------------*/
	private int posLig;
	private int posCol;

	private char[][] grille;

	private int posLigSortie;
	private int posColSortie;

	private int niveau;

	// Constructeur de la classe Plateforme
	public Plateforme()
	{
		/*--------------*/
		/* Instructions */
		/*--------------*/
		this.niveau = 1;

		/*-------------------------------------------*/
		/* Initialisation de la taille de la grille. */
		/*-------------------------------------------*/
		this.initialiserTableau();

		/*--------------------------------------------*/
		/* Lecture et écriture du fichier grille.data */
		/*--------------------------------------------*/
		this.remplirTableau();
	}

	// Accesseurs de la classe:
	public int getNbLignes()
	{
		return this.grille.length;
	}

	public int getNbColonnes()
	{
		return this.grille[0].length;
	}

	public boolean estSortie()
	{
		return this.posLig == this.posLigSortie && this.posCol == this.posColSortie;
	}

	public char getCaseEnvironnement(int lig, int col)
	{
		return this.getCase(lig, col, 0);
	}

	public char getCase(int lig, int col, int couche)
	{
		if (couche == 1 && lig == this.posLig && col == this.posCol)
			return '+';

		return this.grille[lig][col];
	}

	// Méthode qui permet d'initialiser la taille de la grille en fonction des
	// fichiers .data
	public void initialiserTableau()
	{
		/*---------*/
		/* Données */
		/*---------*/
		Scanner sc;
		int ligne2;
		int colonne;

		ligne2 = 0;
		colonne = 0;

		/*-------------------------------------------*/
		/* Initialisation de la taille de la grille. */
		/*-------------------------------------------*/
		try
		{
			sc = new Scanner(new FileInputStream("data/niveau_0" + this.niveau + ".data"), "UTF8");

			while (sc.hasNextLine())
			{
				String currentLine = sc.nextLine();
				ligne2++;
				colonne = Math.max(colonne, currentLine.length());
			}
			sc.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		this.grille = new char[ligne2][colonne];
	}

	// Méthode qui remplit le tableau avec les éléments des fichiers .data
	public void remplirTableau()
	{
		/*---------*/
		/* Données */
		/*---------*/
		Scanner sc;
		int lig;
		String ligne;

		/*--------------------------------------------*/
		/* Lecture et écriture du fichier grille.data */
		/*--------------------------------------------*/
		try
		{
			sc = new Scanner(new FileInputStream("data/niveau_0" + this.niveau + ".data"), "UTF8");
			lig = 0;

			while (sc.hasNextLine())
			{
				ligne = sc.nextLine();
				for (int col = 0; col < ligne.length(); col++)
				{
					this.grille[lig][col] = ligne.charAt(col);

					/*------------------------------*/
					/* initialisation du personnage */
					/*------------------------------*/
					if (getCase(lig, col, 1) == '+')
					{
						this.grille[lig][col] = ' ';
						this.posLig = lig;
						this.posCol = col;
					}

					/*-----------------------------*/
					/* initialisation de la sortie */
					/*-----------------------------*/
					if (getCaseEnvironnement(lig, col) == '@')
					{
						this.posLigSortie = lig;
						this.posColSortie = col;
					}
				}

				lig++;
			}

			sc.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Méthode qui permet d'accéder au niveau suivant.
	public int niveauSuivant()
	{
		this.niveau++;

		this.initialiserTableau();
		this.remplirTableau();

		return this.niveau;
	}

	// Méthode qui permet d'accéder au niveau précédent.
	public int niveauPrecedent()
	{
		this.niveau--;

		if (this.niveau == 0)
			this.niveau++;

		return this.niveau;
	}

	// Méthode qui gère le déplacement du personnage
	public void deplacer(char direction)
	{
		/* ------------ */
		/* Données */
		/* ------------ */
		int futureLig;
		int futureCol;
		boolean sauter;

		/* ------------ */
		/* Instructions */
		/* ------------ */
		futureLig = this.posLig;
		futureCol = this.posCol;
		sauter = false;
		/*----------------------*/
		/* Direction de l'objet */
		/*----------------------*/
		switch (direction)
		{
		case 'N' -> futureLig--;
		case 'O' -> futureCol--;
		case 'S' -> futureLig++;
		case 'E' -> futureCol++;
		case 'H' -> futureLig--;
		}

		/*-----------------------------------*/
		/* Déplacement torique du personnage */
		/*-----------------------------------*/
		if (futureLig < 0)
			futureLig = this.grille.length - 1;
		if (futureCol < 0)
			futureCol = this.grille[0].length - 1;
		if (futureLig > this.grille.length - 1)
			futureLig = 0;
		if (futureCol > this.grille[0].length - 1)
			futureCol = 0;

		if (direction == 'H' && getCaseEnvironnement(futureLig, futureCol) != '='
				&& (getCaseEnvironnement(this.posLig + 1, this.posCol) == '='
						|| getCaseEnvironnement(this.posLig + 1, this.posCol) == '#'))
		{
			this.posLig = futureLig;
			sauter = true;
			System.out.println(!sauter);
		}

		/*---------------------------------------*/
		/* Vérifie si il y a un mur ou une porte */
		/*---------------------------------------*/
		if (getCaseEnvironnement(futureLig, futureCol) != '=' && getCaseEnvironnement(futureLig, futureCol) != 'R'
				&& getCaseEnvironnement(futureLig, futureCol) != 'B'
				&& getCaseEnvironnement(futureLig, futureCol) != 'V')
		{
			this.posLig = futureLig;
			this.posCol = futureCol;
		}

		/*------------------------*/
		/* Gravité du personnage */
		/*------------------------*/
		while ((getCaseEnvironnement(this.posLig + 1, this.posCol) == ' '
				|| getCaseEnvironnement(this.posLig + 1, this.posCol) == 'b'
				|| getCaseEnvironnement(this.posLig + 1, this.posCol) == 'r'
				|| getCaseEnvironnement(this.posLig + 1, this.posCol) == 'v'
				|| getCaseEnvironnement(this.posLig + 1, this.posCol) == '@') && !sauter)
		{
			System.out.println("tombe");
			futureLig = this.posLig + 1;
			this.posLig = futureLig;
			sauter = false;
		}

		/*------------------------------------------------------------*/
		/* Vérifie si le joueur est sur l'echelle et donc peux monter */
		/*------------------------------------------------------------*/
		if (getCaseEnvironnement(this.posLig, this.posCol) == '#')
		{
			if (getCaseEnvironnement(futureLig, futureCol) != '=')
			{
				if (futureLig < 0)
					futureLig = this.grille.length - 1;
				if (futureLig > this.grille.length - 1)
					futureLig = 0;

				this.posLig = futureLig;
				this.posCol = futureCol;
			}
		}

		/*----------------------------------------------------*/
		/* Permet d'enlever la porte pour accéder à la sortie */
		/*----------------------------------------------------*/
		if (getCaseEnvironnement(futureLig, futureCol) == 'r')
		{
			for (int cptLig = 0; cptLig < grille.length; cptLig++)
				for (int cptCol = 0; cptCol < grille[0].length; cptCol++)

					if (getCaseEnvironnement(cptLig, cptCol) == 'R' || getCaseEnvironnement(cptLig, cptCol) == 'r')
						this.grille[cptLig][cptCol] = ' ';
		}

		if (getCaseEnvironnement(futureLig, futureCol) == 'v')
		{
			for (int cptLig = 0; cptLig < grille.length; cptLig++)
				for (int cptCol = 0; cptCol < grille[0].length; cptCol++)

					if (getCaseEnvironnement(cptLig, cptCol) == 'V' || getCaseEnvironnement(cptLig, cptCol) == 'v')
						this.grille[cptLig][cptCol] = ' ';
		}

		if (getCaseEnvironnement(futureLig, futureCol) == 'b')
		{
			for (int cptLig = 0; cptLig < grille.length; cptLig++)
				for (int cptCol = 0; cptCol < grille[0].length; cptCol++)

					if (getCaseEnvironnement(cptLig, cptCol) == 'B' || getCaseEnvironnement(cptLig, cptCol) == 'b')
						this.grille[cptLig][cptCol] = ' ';
		}

	}
}
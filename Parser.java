import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;


class Parser
{	
	private String fichier;
	public static Vector<Shape> formes = new Vector<Shape>();


	public Parser(String s)
	{
		fichier=s;
	}

	private static Point convertir(String s)
	{
		String[] valeurs = new String[2];
		valeurs=s.split(",");

		Double x = new Double(valeurs[0]);
		Double y = new Double(valeurs[1]);
		
		Point p=new Point(x,y);
		return p;
	}


	public static void traiter(String ligne, Point translate)
	{

		String[] couple = new String[100];
		String[] copyCouple;

		Vector<Ligne> l = new Vector<Ligne>();
		Ligne l2;
		Shape s;
		int flag=0;
		boolean avancer=false;
		Point origine = new Point(0,0);
		Point origineDepart;
		Point ancien = new Point(0,0);
		Point nouveau = new Point(0,0);
		Point tireur1 = new Point(0,0);
		Point tireur2 = new Point(0,0);
		Point tmp;

		couple=ligne.split(" ");

		for(int i=0;i<couple.length;i++)
		{
			if(couple[i].contains(String.valueOf('/')))//enleve /
			{
				couple=Arrays.copyOf(couple,couple.length-1);
			}

			else if (couple[i].contains(String.valueOf('"')))
			{
				couple[i]=couple[i].substring(0,couple[i].indexOf('"'));
			}

			else if(couple[i].contains(String.valueOf('e')))//remplace les e par 0
			{
				copyCouple=new String[2];
				copyCouple=couple[i].split(",");
				if(copyCouple[0].contains(String.valueOf('e')))
					copyCouple[0]="0";
				else
					copyCouple[1]="0";

				couple[i]=copyCouple[0]+","+copyCouple[1];
			}

		}

		for(int a=0;a<couple.length;a++)
		{
			System.out.println(couple[a]);
		}

		origineDepart=Point.add(translate,convertir(couple[0]));

		l2=new Ligne(ancien,ancien,ancien,ancien);
		l.add(l2);
		flag=1;

		for(int i=1;i<couple.length;i++)
		{
			if(couple[i].contains(String.valueOf('m')) || couple[i].contains(String.valueOf('l')))
			{
				flag=1;
				avancer=true;
				if(couple[i].contains(String.valueOf('m')))
				{
					origine = convertir(couple[i+1]);
				}
			}
			
			else if(couple[i].contains(String.valueOf('c')))
			{
				flag=2;
				avancer=true;
			}

			else if(couple[i].contains(String.valueOf('M')) || couple[i].contains(String.valueOf('L')))
			{
				flag=3;
				avancer=true;
				if(couple[i].contains(String.valueOf('M')))
				{
					tmp = Point.add(convertir(couple[i+1]),translate);
					origine = Point.sub(tmp,origine);
				}
			}
			
			else if(couple[i].contains(String.valueOf('C')))
			{
				flag=4;
				avancer=true;
			}
			
			else if(couple[i].contains(String.valueOf('z')))
			{
				flag=5;
				avancer=true;
			}

			if(avancer)
			{
				i++;
				avancer=false;	
			}


			switch(flag)
			{
				case 1:
			
					nouveau=Point.add(ancien,convertir(couple[i]));
					l2=new Ligne(ancien,nouveau,ancien,nouveau);

					l.add(l2);
					ancien=nouveau;

					break;

				case 2:
					tireur1=Point.add(ancien,convertir(couple[i]));
					i++;
					tireur2=Point.add(ancien,convertir(couple[i]));
					i++;
					nouveau=Point.add(ancien,convertir(couple[i]));

					l2=new Ligne(ancien,nouveau,tireur1,tireur2);
					l.add(l2);
					ancien=nouveau;

					break;

				case 3:
			
					tmp=Point.add(translate,convertir(couple[i]));
					nouveau=Point.sub(tmp,origineDepart);

					l2=new Ligne(ancien,nouveau,ancien,nouveau);

					l.add(l2);
					ancien=nouveau;

					break;
				case 4:

					tmp=Point.add(translate,convertir(couple[i]));
					tireur1=Point.sub(tmp,origineDepart);
					i++;

					tmp=Point.add(translate,convertir(couple[i]));
					tireur2=Point.sub(tmp,origineDepart);
					i++;

					tmp=Point.add(translate,convertir(couple[i]));
					nouveau=Point.sub(tmp,origineDepart);

					l2=new Ligne(ancien,nouveau,tireur1,tireur2);
					l.add(l2);
					ancien=nouveau;

					break;
				case 5:
					
					if(i<couple.length && !(couple[i].contains(String.valueOf('m'))))
					{
						l2=new Ligne(ancien,origine,ancien,origine);
						l.add(l2);
						ancien=origine;
					}
					else
						i--;

					if(i==(couple.length-1))
					{
						l2=new Ligne(ancien,origine,ancien,origine);
						l.add(l2);
					}

					break;
				default:
					System.out.println("Gros probleme");
					break;
			}
		}
		s= new Shape(l);
		formes.add(s);
	}


	public static void lire(String fic) throws IOException
	{
		BufferedReader br=null;

		Point translateGroupe = new Point(0,0);
		Point translateLocal = new Point(0,0);
		Point translate = new Point(0,0);
		Point origine = new Point(0,0);
		int lettre;
		String ligne;
		char[] mot = new char [8];
		int alarme=0;
		String[] copyCouple;
		String[] buf = new String[100];

		try
		{
			br = new BufferedReader(new FileReader(fic));
		}
		catch(FileNotFoundException exc)
		{
			System.out.println("Erreur d'ouverture");
		}
		while ((lettre=br.read()) != -1)
		{
			if(lettre=='<' && br.read()=='g')
			{
				
				while ((lettre=br.read()) != -1)
				{
					alarme--;

					if(lettre=='t')//recupere le translate du groupe
					{
						br.read(mot,0,5);
						if(mot[0]=='r' && mot[1]=='a' && mot[2]=='n' && mot[3]=='s' && mot[4]=='l')
						{
							ligne=br.readLine();
							buf=ligne.split("\\(");
							buf=buf[1].split("\\)");
							translateGroupe=convertir(buf[0]);
						}
					}

					if(lettre=='g' && br.read()=='>')//alors c'est fini
					{
						br.close();
						return;
					}

					if(lettre=='<')
					{
						alarme=2;
					}


					if(alarme==1 && lettre=='g')//alors on entre dans un vrai groupe
					{
					}

					if(alarme==1 && lettre=='p')//alors on entre dans un path donc une forme
					{
						translateLocal= new Point(0,0);
						br.mark(8000);

						while((lettre=br.read()) != '>')//premier tour de path
						{

							if(lettre=='t')//recupere le translate local
							{
								br.read(mot,0,5);
								if(mot[0]=='r' && mot[1]=='a' && mot[2]=='n' && mot[3]=='s' && mot[4]=='l')
								{
									ligne=br.readLine();

									buf=ligne.split("\\(");
									buf=buf[1].split("\\)");
									translateLocal=convertir(buf[0]);
								}
							}
						}

						br.reset();
						translate=Point.add(translateGroupe,translateLocal);
						System.out.println(translate.affiche());

						while((lettre=br.read()) != '>')//tant que c'est pas la fin de path
						{
							if(lettre=='d')
							{
								br.read(mot,0,4);
								if(mot[0]=='=' && mot[1]=='"' && mot[2]=='m' && mot[3]==' ')
								{
									ligne=br.readLine();
									traiter(ligne,translate);
								}
							}
						}
					}

				}

			}
		}

		br.close();
		return;
		}

} 
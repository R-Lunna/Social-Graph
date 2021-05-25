import java.security.SecureRandom;
import java.util.Formatter;
import java.io.FileNotFoundException;

public class CreateUsers
{

	private static String[] namesMale = {"Alexandre", "Eduardo", "Henrique", "Murilo", "Theo"};
	private static String[] namesFemale = {"Antonela",	"Clara",	"Giovanna",	"Larissa",	"Mariana", "Rafaela"};

	private static String name;
	private static String email;
	private static String password;
	private static String urlPhoto;
	private static String birthday;
	private static String sex;
	private static int positionX = 0;
	private static int positionY = 0;

	public static void main( String[] args )
	{


		if( args.length != 2 || Integer.parseInt( args[ 0 ] ) <= 0 )
		{
			System.out.println("Valor incorreto");
			System.exit( 1 );
		}

		generateUsers( Integer.parseInt( args[ 0 ] ), args[ 1 ] );


	}

	public static void generateUsers( int size, String filename )
	{

		SecureRandom random = new SecureRandom();
		Formatter formatter = null;

		try
		{
			formatter = new Formatter( filename );
		}
		catch( FileNotFoundException e )
		{
			System.out.println("Erro ao abrir arquivo");
			System.exit( 1 );
		}
		

		for( int count = 0; count < size; count++ )
		{

			int auxSex = random.nextInt( 2 ); // 0 Masculino, 1 Feminino
			name = (auxSex == 0)? namesMale[ random.nextInt( namesMale.length ) ] : namesFemale[ random.nextInt( namesFemale.length ) ];
			email = String.format("%s@gmail.com", name);
			sex = ( auxSex == 0 )?"Masculino":"Feminino";

			formatter.format("{");

			formatter.format("\t\"id\" : \"%d\",%n", count + 1L);
			formatter.format("\t\"name\" : \"%s\",%n", name);
			formatter.format("\t\"email\" : \"%s\",%n", email);
			formatter.format("\t\"password\" : \"%s\",%n", "qualquersenha");
			formatter.format("\t\"urlPhoto\" : \"%s\",%n", "https://qualquerurl.com.br");
			formatter.format("\t\"birthday\" : \"%02d/%02d/%04d\",%n", random.nextInt( 31) + 1, random.nextInt( 12 ) + 1, random.nextInt( 100 ) + 1921);
			formatter.format("\t\"sex\" : \"%s\",%n", sex);
			formatter.format("\t\"positionX\" : \"%d\",%n", random.nextInt(1000));
			formatter.format("\t\"positionY\" : \"%d\",%n", random.nextInt(1000));

			formatter.format("\t\"Edges\" : [%n");

			int countEdges = random.nextInt( size ) + 1;
			
			for( int count2 = 0; count < countEdges; count2++ )
			{
				int edge = random.nextInt( size ) + 1;
				if( edge == count + 1 )
					countEdges--;

				if( count2 == countEdges -1 )
					formatter.format("\t\t\"%d\"%n", edge);
				else
					formatter.format("\t\t\"%d\", %n", edge);
			}

			formatter.format("\t]%n");

			formatter.format("}");


			formatter.format("\n");
		}

		formatter.close();
		System.out.println("Arquivo gravado com sucesso");
	}





}
import java.security.SecureRandom;
import java.util.Formatter;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CreateUsers
{

	private static String[] namesMale;
	private static String[] namesFemale;

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

		namesMale = fileToArray("namesMale.txt");
		namesFemale = fileToArray("namesFemale.txt");

		generateUsers( Integer.parseInt( args[ 0 ] ), args[ 1 ] );


	}

	public static String[] fileToArray( String path )
	{
		String[] lines = null;
		try
		{
			lines = Files.lines( Path.of( path ) ).collect( Collectors.toList() ).toArray( new String[1] );
		}
		catch( IOException e )
		{
			System.out.println("Erro ao carregar arquivo: " + path);
			System.exit(1);
		}
		
		return lines;
	}

	public static void generateUsers( int size, String filename )
	{

		SecureRandom random = new SecureRandom();
		Formatter formatter = null;

		try
		{
			formatter = new Formatter( filename, "UTF-8");
		}
		catch( FileNotFoundException | UnsupportedEncodingException e )
		{
			System.out.println("Erro ao abrir arquivo");
			System.exit( 1 );
		}
		
		ArrayList<Position> positions = new ArrayList<>();

		formatter.format("[%n");
		for( int count = 0; count < size; count++ )
		{

			int auxSex = random.nextInt( 2 ); // 0 Masculino, 1 Feminino
			name = (auxSex == 0)? namesMale[ random.nextInt( namesMale.length ) ] : namesFemale[ random.nextInt( namesFemale.length ) ];
			email = String.format("%s@gmail.com", name);
			sex = ( auxSex == 0 )?"Masculino":"Feminino";

			formatter.format("\t{%n");

			formatter.format("\t\t\"id\" : \"%d\",%n", count);
			formatter.format("\t\t\"name\" : \"%s\",%n", name);
			formatter.format("\t\t\"email\" : \"%s\",%n", email.replace(" ", ""));
			formatter.format("\t\t\"password\" : \"%s\",%n", "qualquersenha");
			formatter.format("\t\t\"urlPhoto\" : \"%s\",%n", "");
			formatter.format("\t\t\"birthday\" : \"%02d/%02d/%04d\",%n", random.nextInt( 31) + 1, random.nextInt( 12 ) + 1, random.nextInt( 100 ) + 1921);
			formatter.format("\t\t\"sex\" : \"%s\",%n", sex);

			/* Calcula posições únicas */
			Position position = new Position( 0, 0 );
			int x = 0;
			int y = 0;
			boolean sentinel = true;
			final int MARGIN = 100;
			final int RADIUS = 100;

			while( sentinel )
			{
				sentinel = false;

				x = random.nextInt(10000) * ((random.nextInt(2) == 0)?-1:1);
				y = random.nextInt(10000) * ((random.nextInt(2) == 0)?-1:1);

				for( Position posAux : positions )
				{
					/* Verifica se há colisão */
					if( Math.sqrt( Math.pow( (posAux.getX() - x), 2) + Math.pow( posAux.getY() - y, 2)  ) < MARGIN + RADIUS + RADIUS )
						sentinel = true;
				}
			}

			position.setX( x );
			position.setY( y );

			positions.add( position );

			formatter.format("\t\t\"positionX\" : \"%d\",%n", position.getX());
			formatter.format("\t\t\"positionY\" : \"%d\",%n", position.getY());

			formatter.format("\t\t\"Edges\" : [%n");

			int countEdges = random.nextInt( (int)(size * 0.15) );
			//int countEdges = random.nextInt( size );

			for( int count2 = 0; count2 < countEdges; count2++ )
			{
				sentinel = true;
				int edge = 0;
				while( sentinel )
				{
					edge = random.nextInt( size );
					if( edge != count )
						sentinel = false;
					
				}


				if( count2 == countEdges -1 )
					formatter.format("\t\t\t\"%d\"%n", edge);
				else
					formatter.format("\t\t\t\"%d\", %n", edge);
			}

			formatter.format("\t\t]%n");

			if( count == size -1 )
				formatter.format("\t}");
			else
				formatter.format("\t},");

			formatter.format("\n");
		}
		formatter.format("]%n");

		formatter.close();
		System.out.println("Arquivo gravado com sucesso");
	}





}

class Position
{
	int x;
	int y;

	public Position( int x, int y )
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX( int x )
	{
		this.x = x;
	}

	public void setY( int y )
	{
		this.y = y;
	}
}
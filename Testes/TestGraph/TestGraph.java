/* Classe de teste */

public class TestGraph
{
	public static void main( String[] args )
	{
		Graph graph = new Graph();

		System.out.println("\nTestando a estrutura de dados Graph\n");

		/* toString inicial */
		System.out.printf("toString() com o grafo vazio: %s%n%n", graph );

		User user = new User();
		user.setName("Joao Alves");
		user.setEmail("joaoalves@gmail.com");
		user.setPassword("1234567890");
		user.setUrlPhoto("http://abcdefghijkl.com");
		user.setBirthday("30/01/1999");
		user.setSex("M");

		User user1 = new User();
		user1.setName("Paulo Afonso");
		user1.setEmail("pauloafonso@gmail.com");
		user1.setPassword("0987654321");
		user1.setUrlPhoto("http://asdsadsad.com");
		user1.setBirthday("24/05/1997");
		user1.setSex("M");

		/* Adicionando um vértice ao grafo */
		graph.addVertex( user );

		/* toString após adicionar novo vértice */
		System.out.printf("toString() apos adicionar novo usuario: %s%n%n", graph );

		/* Adicionando um vértice ao grafo */
		graph.addVertex( user1 );

		/* toString após adicionar novo vértice */
		System.out.printf("toString() apos adicionar novo usuario: %s%n%n", graph );

		System.out.printf("graph.length(): %d%n%n", graph.length());

		System.out.printf("graph.getTotalEdge(): %d%n%n", graph.getTotalEdge());

		System.out.printf("Graph contains (\"test@gmail.com\")?: %b%n%n", graph.contains("test@gmail.com"));
		
		System.out.printf("graph.contains(\"joaoalves@gmail.com\")?: %b%n%n", graph.contains("joaoalves@gmail.com"));

		System.out.printf("graph.contains(\"pauloafonso@gmail.com\")?: %b%n%n", graph.contains("pauloafonso@gmail.com"));

		System.out.println("\nConectando arestas:\n");
		System.out.printf("graph.addEdge(\"joaoalves@gmail.com\", \"pauloafonso@gmail.com\")%n%n");
		graph.addEdge("joaoalves@gmail.com", "pauloafonso@gmail.com");

		System.out.printf("graph.getTotalEdge(): %d%n%n", graph.getTotalEdge());

		System.out.printf("graph.isAdjacent(\"joaoalves@gmail.com\", \"pauloafonso@gmail.com\"): %b%n%n", graph.isAdjacent("joaoalves@gmail.com", "pauloafonso@gmail.com"));

		System.out.printf("graph.isAdjacent(\"pauloafonso@gmail.com\", \"joaoalves@gmail.com\"): %b%n%n", graph.isAdjacent("pauloafonso@gmail.com", "joaoalves@gmail.com"));

		User data = graph.getVertexData( "joaoalves@gmail.com" );
		System.out.printf("graph.getVertexData( \"joaoalves@gmail.com\" ): %n\t%s%n%n", String.format("%s %s %s %s %s %s", data.getName(), data.getEmail(), data.getPassword(), data.getUrlPhoto(), data.getBirthday(), data.getSex() ) );
		

		data = graph.getVertexData( 1 );
		System.out.printf("graph.getVertexData( 1 ): %n\t%s%n%n", String.format("%s %s %s %s %s %s", data.getName(), data.getEmail(), data.getPassword(), data.getUrlPhoto(), data.getBirthday(), data.getSex() ) );

		System.out.println("Percorrendo os vertices do grafo com:\n\tfor( int count = 0; count < graph.length(); count++ )\n\t\tdata = graph.getVertexData( count );\n");

		for( int count = 0; count < graph.length(); count++ )
		{
			data = graph.getVertexData( count );
			System.out.println(String.format("\t%s %s %s %s %s %s", data.getName(), data.getEmail(), data.getPassword(), data.getUrlPhoto(), data.getBirthday(), data.getSex()));
		}

		System.out.printf("\ngraph.removeEdge(\"joaoalves@gmail.com\", \"pauloafonso@gmail.com\")%n%n");
		graph.removeEdge("joaoalves@gmail.com", "pauloafonso@gmail.com");
		System.out.printf("graph.getTotalEdge(): %d%n%n", graph.getTotalEdge());

		System.out.printf("graph.removeVertex(\"joaoalves@gmail.com\")%n%n");
		graph.removeVertex("joaoalves@gmail.com");

		System.out.printf("toString() apos removeVertex: %s%n%n", graph);

		System.out.println("\n\n");

		graph = new Graph();

		user = new User();
		user.setName("0");
		user.setEmail("0");
		graph.addVertex( user );

		user = new User();
		user.setName("1");
		user.setEmail("1");
		graph.addVertex( user );

		user = new User();
		user.setName("2");
		user.setEmail("2");
		graph.addVertex( user );

		user = new User();
		user.setName("3");
		user.setEmail("3");
		graph.addVertex( user );

		user = new User();
		user.setName("4");
		user.setEmail("4");
		graph.addVertex( user );

		user = new User();
		user.setName("5");
		user.setEmail("5");
		graph.addVertex( user );

		graph.addEdge( "0", "1" );
		graph.addEdge( "0", "3" );
		graph.addEdge( "1", "3" );
		graph.addEdge( "1", "2" );
		graph.addEdge( "2", "3" );
		graph.addEdge( "2", "2" );
		graph.addEdge( "3", "0" );
		graph.addEdge( "5", "4" );
		
		graph.printGraph();
		System.out.printf("%ngraph.getTotalEdge(): %d%n%n", graph.getTotalEdge());

		graph.removeVertex("3");
		System.out.println("\nRemovendo o vertice 3\n");
		graph.printGraph();
		



	}
}
package classes.uteis;

/**
 * Classe que auxilia na leitura de {@link Personagem Classes} a partir de arquivos.
 * @author matheus
 *
 */
public interface Serializador
{
	/**
	 * Fun��o utilizada para leitura da classe de um arquivo.
	 * @return 
	 */
	public boolean Carrega();
	
	/**
	 * Fun��o utilizada para grava��o da classe em um arquivo.
	 */
	public void Salva();
}

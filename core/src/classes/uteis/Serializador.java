package classes.uteis;

/**
 * Classe que auxilia na leitura de {@link Classe Classes} a partir de arquivos.
 * @author matheus
 *
 */
public interface Serializador
{
	/**
	 * Função utilizada para leitura da classe de um arquivo.
	 */
	public void Carrega();
	
	/**
	 * Função utilizada para gravação da classe em um arquivo.
	 */
	public void Salva();
}

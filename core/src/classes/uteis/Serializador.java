package classes.uteis;

import java.io.IOException;

/**
 * Classe que auxilia na leitura de {@link Personagem Classes} a partir de arquivos.
 * @author matheus
 *
 */
public interface Serializador
{
	/**
	 * Função utilizada para leitura da classe de um arquivo.
	 * @return 
	 * @throws IOException 
	 */
	public boolean Carrega();
	
	/**
	 * Função utilizada para gravação da classe em um arquivo.
	 */
	public void Salva();
}

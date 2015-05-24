package classes.uteis;

import java.io.IOException;

/**
 * Classe que auxilia na leitura de {@link Personagem Classes} a partir de arquivos.
 * Caso o carregamento via arquivo seja muito frequente, guarde os valores lidos da primeira vez e reutilize. Leitura de arquivo é custoso!
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

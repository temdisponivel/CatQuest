package classes.uteis;

import java.io.IOException;

/**
 * Classe que auxilia na leitura de {@link Personagem Classes} a partir de arquivos.
 * Caso o carregamento via arquivo seja muito frequente, guarde os valores lidos da primeira vez e reutilize. Leitura de arquivo � custoso!
 * @author matheus
 *
 */
public interface Serializador
{
	/**
	 * Fun��o utilizada para leitura da classe de um arquivo.
	 * @return 
	 * @throws IOException 
	 */
	public boolean Carrega();
	
	/**
	 * Fun��o utilizada para grava��o da classe em um arquivo.
	 */
	public void Salva();
}

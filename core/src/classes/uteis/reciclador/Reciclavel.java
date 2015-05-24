package classes.uteis.reciclador;

/**
 * Utilizado pelo {@link Reciclador reciclador} para liberar e reutilizar aqueles que implementam.
 * @author matheus
 *
 */
public interface Reciclavel
{
	/**
	 * Fun��o que redefine todos as propriedades do objeto. Depois da chamada desta fun��o, a instancia deve fica como se tivesse acabado de ser criada.
	 */
	public void Recicla();
}

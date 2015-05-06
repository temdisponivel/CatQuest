package classes.uteis;

import java.util.HashMap;
import java.util.Stack;
import classes.gameobjects.GameObject;

/**
 * Classe para auxiliar no gerenciamento de memória.
 * Cria um reciclador para múltiplos tipos de dados, desde que todos herdem de {@link GameObject} e {@link Reciclavel}.
 * @author matheus
 *
 * @param <T> Qualquer tipo de dado que herde de {@link GameObject} e {@link Reciclavel}.
 */
public class Reciclador<T extends Reciclavel>
{
	private HashMap<Class<? extends T>, Stack<T>> _objetos = null;
	private HashMap<Class<? extends T>, Stack<T>> _objetosLivres = null;
	
	/**
	 * Cria um novo reciclador.
	 */
	public Reciclador()
	{
		_objetos = new HashMap<Class<? extends T>, Stack<T>>();
		_objetosLivres = new HashMap<Class<? extends T>, Stack<T>>();
	}
	
	/**
	 * Retorna uma instancia para um {@link T}. Pode ser uma nova instancia ou uma reutilizada.
	 * @param classe {@link Class<? extends T> Classe} do objeto a ser retornado. É instanciado como a classe parametrizada, mas retornado fazendo polimorfismo para {@link T}. 
	 * @return Instancia para a classe parametrizada, mas como {@link T}. Ou null caso ocorra algum problema.
	 */
	public T GetInstancia(Class<? extends T> classe)
	{
		try
		{
			if (_objetosLivres.containsKey(classe))
				return _objetosLivres.get(classe).pop();
			
			T instancia = classe.newInstance();
			this.Recicla(instancia);
			
			return instancia;
		}
		catch (Exception e)
		{
			Log.instancia.Logar("Erro ao instanciar um reciclavel do tipo" + classe.toString(), e, false);
		}
		
		return null;
	}
	
	/**
	 * Libera o {@link T reciclavel} e o torna disponível para reutilização. 
	 * @param reciclavel {@link T Objeto} a ser reciclado.
	 */
	@SuppressWarnings("unchecked")
	public void Recicla(T reciclavel)
	{
		if (_objetos.containsKey(reciclavel.getClass()))
		{
			_objetos.get(reciclavel.getClass()).remove(reciclavel);
		}
		
		if (!_objetosLivres.containsKey(reciclavel.getClass()))
		{
			_objetosLivres.put((Class<? extends T>) reciclavel.getClass(), new Stack<T>());
		}
		
		_objetosLivres.get(reciclavel.getClass()).push(reciclavel);
		reciclavel.Recicla();
	}
	
	/**
	 * Limpa todos os {@link Reciclavel recicláveis} do {@link Reciclador reciclador}.
	 */
	public void Limpa()
	{
		_objetos.clear();
		_objetosLivres.clear();
	}
}

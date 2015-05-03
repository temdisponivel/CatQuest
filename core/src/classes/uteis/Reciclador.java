package classes.uteis;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;
import classes.gameobjects.GameObject;

/**
 * Classe para auxiliar no gerenciamento de mem�ria.
 * Cria um reciclador para m�ltiplos tipos de dados, desde que todos herdem de {@link GameObject} e {@link Reciclavel}.
 * @author matheus
 *
 * @param <T> Qualquer tipo de dado que herde de {@link GameObject} e {@link Reciclavel}.
 */
public class Reciclador<T extends GameObject & Reciclavel>
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
	 * @param classe {@link Class<? extends T> Classe} do objeto a ser retornado. � instanciado como a classe parametrizada, mas retornado fazendo polimorfismo para {@link T}. 
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
	 * Libera o {@link T reciclavel} e o torna dispon�vel para reutiliza��o. 
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
	 * Limpa todos os {@link Reciclavel recicl�veis} do {@link Reciclador reciclador}. {@link GameObject#Encerra() Encerra} todos os {@link GameObject gameobjects}.
	 */
	public void Limpa()
	{
		for (Entry<Class<? extends T>, Stack<T>> entrada : _objetosLivres.entrySet())
		{
			for (T valor : entrada.getValue())
			{
				valor.Encerra();
			}
			
			entrada.getValue().clear();
		}
	}
}

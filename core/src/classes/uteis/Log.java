//TODO: terminar log

package classes.uteis;

import com.badlogic.gdx.Gdx;
import java.util.Date;

public class Log
{
	static StringBuilder _stringBuilder = null;
	
	public Log()
	{
		_stringBuilder = new StringBuilder();
	}
	
	/**
	 * Loga o texto, a data e hora de ocorrencia e o trace da {@link Exception}
	 * @param texto Texto a ser logado.
	 * @param e Exception ocorrida. Pode ser null.
	 */
	static public void Logar(String texto, Exception e)
	{
		if (_stringBuilder == null)
			_stringBuilder = new StringBuilder();
		
		_stringBuilder.delete(0, _stringBuilder.length());
		_stringBuilder.append(new Date().toString()).append(" | ").append(texto).append(" | ");
		
		if (e != null) _stringBuilder.append(e.getMessage());
		
		Gdx.files.local("arquivos\\log.txt").writeString(_stringBuilder.toString(), true);
	}
}

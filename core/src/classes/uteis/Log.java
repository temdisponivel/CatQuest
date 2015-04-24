package classes.uteis;

import catquest.CatQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.Calendar;
import java.util.Date;

public class Log
{
	static StringBuilder _stringBuilder = null;
	public final static FileHandle log = Gdx.files.local("arquivos/log" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + Calendar.getInstance().get(Calendar.MONTH) + Calendar.getInstance().get(Calendar.YEAR)+".txt");
	public static Log instancia = null;
	
	
	public Log()
	{
		if (instancia == null)
		{
			instancia = this;
			_stringBuilder = new StringBuilder();
		}
	}
	
	/**
	 * Loga o texto, a data e hora de ocorrencia e o trace da {@link Exception}
	 * @param texto Texto a ser logado.
	 * @param e Exception ocorrida. Pode ser null.
	 */
	public void Logar(String texto, Exception e, boolean encerra)
	{
		if (_stringBuilder == null)
			_stringBuilder = new StringBuilder();
		
		_stringBuilder.delete(0, _stringBuilder.length());
		_stringBuilder.append("/r/n");
		_stringBuilder.append("Data: ").append(new Date().toString()).append(" | Mensagem: ").append(texto);
		_stringBuilder.append("/r/n");
		
		if (e != null)
		{
			_stringBuilder.append("StackTrace: ");
			_stringBuilder.append("/r/n");
			
			for (StackTraceElement elemento : e.getStackTrace())
			{
				_stringBuilder.append(elemento.toString());
				_stringBuilder.append("/r/n");
			}
		}
		
		if (encerra)
		{
			_stringBuilder.append("O JOGO FOI ENCERRADO POR CAUSA DE: " + (e != null ? e.getMessage() : "Erro irrevers�vel."));
			_stringBuilder.append("/r/n");
			CatQuest.instancia.EncerraJogo();
		}
		
		log.writeString(_stringBuilder.toString(), true);
		System.out.println(_stringBuilder.toString());
	}
	
	/**
	 * Escrever texto no arquivo de log.
	 * @param texto Texto a ser escrito.
	 */
	public void Logar(String texto)
	{
		Log.instancia.Logar(texto, null, false);
		System.out.println(texto);
	}
}

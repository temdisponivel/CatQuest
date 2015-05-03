package classes.uteis;

import catquest.CatQuest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Log
{
	public static Log instancia = null;
	private StringBuilder _stringBuilder = null;
	private FileHandle _log = null;
	
	public Log() throws IOException
	{
		if (instancia == null)
		{
			instancia = this;
			_stringBuilder = new StringBuilder();
			_log = Gdx.files.local("arquivos/log" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + Calendar.getInstance().get(Calendar.MONTH) + 1 + Calendar.getInstance().get(Calendar.YEAR)+".txt");
			
			if (!_log.exists())
				_log.file().createNewFile();
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
		_stringBuilder.append("\r\n");
		_stringBuilder.append("Data: ").append(new Date().toString()).append(" | Mensagem: ").append(texto);
		_stringBuilder.append("\r\n");
		
		if (e != null)
		{
			_stringBuilder.append("StackTrace: ");
			_stringBuilder.append("\r\n");
			
			for (StackTraceElement elemento : e.getStackTrace())
			{
				_stringBuilder.append(elemento.toString());
				_stringBuilder.append("\r\n");
			}
		}
		
		if (encerra)
		{
			_stringBuilder.append("O JOGO FOI ENCERRADO POR CAUSA DE: " + (e != null ? e.getMessage() == null ? texto : e.getMessage() : "Erro irreversivel."));
			_stringBuilder.append("\r\n");
			CatQuest.instancia.EncerraJogo();
		}
		
		_log.writeString(_stringBuilder.toString(), true);
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

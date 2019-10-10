package pkgData;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import pkgMisc.ExceptionHandler;

public class MailWorker extends Task<Double>
{

	private GMailer mailer;
	private int max;
	private final DoubleProperty doubleProp = new SimpleDoubleProperty();

	public MailWorker(GMailer mailer, int max)
	{
		super();
		this.mailer = mailer;
		this.max = max;
	}

	@Override
	protected Double call()
	{
		int i = 0;
		try
		{
			for (i = 1; i <= max && !isCancelled(); i++)
			{
				final double tmpI = ((double) i+1) / max;
				mailer.sendMail();
				
				updateValue(tmpI);

				Platform.runLater(() -> setDoubleProp(tmpI));
			}
		} catch (Exception ex)
		{
			ExceptionHandler.hanldeUnexpectedException(ex);
		} finally
		{
			return (double) i;
		}
	}

	public DoubleProperty getDoubleProp()
	{
		return doubleProp;
	}

	private void setDoubleProp(double val)
	{
		doubleProp.set(val);
	}
}

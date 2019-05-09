package pkgData;

import java.sql.Timestamp;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import pkgController.Dashboard_Controller;

public class TimerWorker extends TimerTask
{

	private SimpleStringProperty sp = new SimpleStringProperty();
	private Dashboard_Controller controller;
	private boolean isEnd = false;
	private long secs = 0;
	private Timestamp startTime;
	private Timestamp endTime;

	public TimerWorker(Dashboard_Controller controller)
	{
		this.controller = controller;
	}

	public void setEnd()
	{
		isEnd = true;
	}

	public SimpleStringProperty getSp()
	{
		return sp;
	}

	private void setSp(String sp)
	{
		this.sp.setValue(sp);
	}

	@Override
	public void run()
	{
		startTime = new Timestamp(System.currentTimeMillis());
		do
		{
			secs += 1;
			String tmp = formatTimeToString();
			Platform.runLater((() -> setSp(tmp)));
			if (isEnd)
			{
				endTime = new Timestamp(System.currentTimeMillis());
				break;
			}
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				Platform.runLater(() -> controller.handleException(e));
			}
			if (isEnd)
			{
				endTime = new Timestamp(System.currentTimeMillis());
				break;
			}
		} while (!isEnd);

	}

	private String formatTimeToString()
	{
		StringBuilder sb = new StringBuilder();
		long min = secs / 60;
		int hours = (int) (min / 60);
		secs %= 60;
		min %= 60;
		sb.append(hours > 10 ? String.valueOf(hours) : "0" + hours);
		sb.append(":");
		sb.append(min > 10 ? String.valueOf(min) : "0" + min);
		sb.append(":");
		sb.append(secs > 10 ? String.valueOf(secs) : "0" + secs);
		return sb.toString();
	}

	public Timestamp getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Timestamp startTime)
	{
		this.startTime = startTime;
	}

	public Timestamp getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Timestamp endTime)
	{
		this.endTime = endTime;
	}

	@Override
	public String toString()
	{
		return "TimerWorker [sp=" + sp + ", controller=" + controller + ", isEnd=" + isEnd + ", secs=" + secs
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}

package values;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.fredtm.core.model.TimeActivity;

public class ToleranceFactor {

	private long intervalTotal;
	private long workingTotal;

	public ToleranceFactor() {
	}

	public ToleranceFactor intervalTimes(List<TimeActivity> times) {
		this.intervalTotal = getIntervalTotal(times);
		return this;
	}

	public ToleranceFactor workingTimes(List<TimeActivity> times) {
		this.workingTotal = getWorkingTotal(times);
		return this;
	}
	
	public void setIntervalTotal(long intervalTotal) {
		this.intervalTotal = intervalTotal;
	}
	
	public void setWorkingTotal(long workingTotal) {
		this.workingTotal = workingTotal;
	}

	public BigDecimal calculate() {
		if(intervalTotal == 0 || workingTotal == 0) return BigDecimal.ONE; 
		double p = (double) intervalTotal / (double) workingTotal ;
		double value = 1d / (1d - p);
		return new BigDecimal(value).setScale(3, RoundingMode.HALF_UP);
	}

	private long getIntervalTotal(List<TimeActivity> times) {
		long intervalTotal = times.stream().mapToLong((t) -> t.getTimed()).sum();
		return intervalTotal;
	}

	private long getWorkingTotal(List<TimeActivity> times) {
		long workingTotal = times.stream().mapToLong((t) -> t.getTimed()).sum();
		return workingTotal;
	}

}

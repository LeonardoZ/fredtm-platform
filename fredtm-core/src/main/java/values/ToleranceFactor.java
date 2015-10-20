package values;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.fredtm.core.model.TimeActivity;

public class ToleranceFactor {

	private List<TimeActivity> intervalTimes;
	private List<TimeActivity> workingTimes;

	public ToleranceFactor() {
		intervalTimes = new ArrayList<>();
		workingTimes = new ArrayList<>();
	}

	public ToleranceFactor intervalTimes(List<TimeActivity> times) {
		this.intervalTimes = times;
		return this;
	}

	public ToleranceFactor workingTimes(List<TimeActivity> times) {
		this.workingTimes = times;
		return this;
	}

	public BigDecimal calculate() {
		long workingTotal = workingTimes.stream()
				.mapToLong((t) -> t.getEllapsedTimeInSeconds()).sum();
		long intervalTotal = intervalTimes.stream()
				.mapToLong((t) -> t.getEllapsedTimeInSeconds()).sum();
		
		long total = workingTotal + intervalTotal;
		long workingPct = workingTotal / total;
		long intervalPct = intervalTotal / total;
		BigDecimal p = BigDecimal.valueOf(intervalPct)
								.divide(BigDecimal.valueOf(workingPct),2,RoundingMode.FLOOR);
		return 
				BigDecimal.valueOf(1).divide(BigDecimal.valueOf(1).subtract(p), 2, RoundingMode.FLOOR);
	}
	
	BiFunction<Double, Double, Double> effectivetTime = (a,b) -> 1 / 1 - (a/b);

}

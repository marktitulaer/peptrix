package pharmaceuticals.nl.peptrix.gui;

import java.util.Calendar;

class JYearChooser extends JSpinField {

	protected JDayChooser dayChooser;

	protected int startYear;

	protected int endYear;

	public JYearChooser() {
		Calendar calendar = Calendar.getInstance();
		dayChooser = null;
		setMinimum(calendar.getMinimum(Calendar.YEAR));
		setMaximum(calendar.getMaximum(Calendar.YEAR));
		setValue(calendar.get(Calendar.YEAR));
	}

	public void setYear(int y) {
		int oldYear = getValue();
		super.setValue(y, true, false);
		if (dayChooser != null) {
			dayChooser.setYear(value);
		}
		spinner.setValue(new Integer(value));
		firePropertyChange("year", oldYear, value);
	}

	@Override
	public void setValue(int value) {
		setYear(value);
	}

	public int getYear() {
		return super.getValue();
	}

	public void setDayChooser(JDayChooser dayChooser) {
		this.dayChooser = dayChooser;
	}

	@Override
	public String getName() {
		return "JYearChooser";
	}

	public int getEndYear() {
		return getMaximum();
	}

	public void setEndYear(int endYear) {
		setMaximum(endYear);
	}

	public int getStartYear() {
		return getMinimum();
	}

	public void setStartYear(int startYear) {
		setMinimum(startYear);
	}

}

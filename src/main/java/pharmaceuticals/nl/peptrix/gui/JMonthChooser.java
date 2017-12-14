package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormatSymbols;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

class JMonthChooser extends JPanel implements ItemListener, ChangeListener {

	protected boolean hasSpinner;

	private Locale locale;

	private int month;

	private int oldSpinnerValue = 0;

	private JDayChooser dayChooser;

	private JYearChooser yearChooser;

	private JComboBox comboBox;

	private JSpinner spinner;

	private boolean initialized;

	private boolean localInitialize;

	public JMonthChooser() {
		this(true);
	}

	public JMonthChooser(boolean hasSpinner) {
		super();
		this.hasSpinner = hasSpinner;
		setLayout(new BorderLayout());
		comboBox = new JComboBox();
		comboBox.addItemListener(this);
		locale = Locale.getDefault();
		initNames();
		if (hasSpinner) {
			spinner = new JSpinner();
			spinner.addChangeListener(this);
			comboBox.setBorder(new EmptyBorder(0, 0, 0, 0));
			spinner.setEditor(comboBox);
			add(spinner, BorderLayout.WEST);
		} else {
			add(comboBox, BorderLayout.WEST);
		}
		initialized = true;
		setMonth(Calendar.getInstance().get(Calendar.MONTH));
	}

	public void initNames() {
		localInitialize = true;
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String[] monthNames = dateFormatSymbols.getMonths();
		if (comboBox.getItemCount() == 12) {
			comboBox.removeAllItems();
		}
		for (int i = 0; i < 12; i++) {
			comboBox.addItem(monthNames[i]);
		}
		localInitialize = false;
		comboBox.setSelectedIndex(month);
	}

	// @Override
	public void stateChanged(ChangeEvent e) {
		SpinnerNumberModel model = (SpinnerNumberModel) ((JSpinner) e.getSource()).getModel();
		int value = model.getNumber().intValue();
		boolean increase = (value > oldSpinnerValue) ? true : false;
		oldSpinnerValue = value;
		int month = getMonth();
		if (increase) {
			month += 1;
			if (month == 12) {
				month = 0;
				if (yearChooser != null) {
					int year = yearChooser.getYear();
					year += 1;
					yearChooser.setYear(year);
				}
			}
		} else {
			month -= 1;
			if (month == -1) {
				month = 11;
				if (yearChooser != null) {
					int year = yearChooser.getYear();
					year -= 1;
					yearChooser.setYear(year);
				}
			}
		}
		setMonth(month);
	}

	// @Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			int index = comboBox.getSelectedIndex();
			if ((index >= 0) && (index != month)) {
				setMonth(index, false);
			}
		}
	}

	private void setMonth(int newMonth, boolean select) {
		if (!initialized || localInitialize) {
			return;
		}
		int oldMonth = month;
		month = newMonth;
		if (select) {
			comboBox.setSelectedIndex(month);
		}
		if (dayChooser != null) {
			dayChooser.setMonth(month);
		}
		firePropertyChange("month", oldMonth, month);
	}

	public void setMonth(int newMonth) {
		if (newMonth < 0 || newMonth > 11) {
			return;
		}
		setMonth(newMonth, true);
	}

	public int getMonth() {
		return month;
	}

	public void setDayChooser(JDayChooser dayChooser) {
		this.dayChooser = dayChooser;
	}

	public void setYearChooser(JYearChooser yearChooser) {
		this.yearChooser = yearChooser;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLocale(Locale l) {
		if (!initialized) {
			super.setLocale(l);
		} else {
			locale = l;
			initNames();
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		comboBox.setEnabled(enabled);
		if (spinner != null) {
			spinner.setEnabled(enabled);
		}
	}

	public Component getComboBox() {
		return this.comboBox;
	}

	public Component getSpinner() {
		return spinner;
	}

	public boolean hasSpinner() {
		return hasSpinner;
	}

	@Override
	public String getName() {
		return "JMonthChooser";
	}

}

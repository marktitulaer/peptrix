package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

class JCalendar extends JPanel implements PropertyChangeListener, ActionListener {

	JFrame calendarframe;

	SimpleDateFormat df;

	TextField field_to_update;

	public JButton btnOK;

	private JButton btnCancel;

	private JPanel PanelOKCancel;

	private Calendar calendar;

	protected JDayChooser dayChooser;

	private boolean initialized = false;

	protected boolean weekOfYearVisible = true;

	protected Locale locale;

	protected JMonthChooser monthChooser;

	private JPanel monthYearPanel;

	protected JYearChooser yearChooser;

	public JCalendar(JFrame calendarframe, TextField field_to_update) {
		this(null, null, true, true, calendarframe, field_to_update);
	}

	public JCalendar(boolean monthSpinner, JFrame calendarframe, TextField field_to_update) {
		this(null, null, monthSpinner, true, calendarframe, field_to_update);
	}

	public JCalendar(Date date, JFrame calendarframe, TextField field_to_update) {
		this(date, null, true, true, calendarframe, field_to_update);
	}

	public JCalendar(Locale locale, JFrame calendarframe, TextField field_to_update) {
		this(null, locale, true, true, calendarframe, field_to_update);
	}

	public JCalendar(Date date, Locale locale, JFrame calendarframe, TextField field_to_update) {
		this(date, locale, true, true, calendarframe, field_to_update);
	}

	public JCalendar(Date date, boolean monthSpinner, JFrame calendarframe, TextField field_to_update) {
		this(date, null, monthSpinner, true, calendarframe, field_to_update);
	}

	public JCalendar(Locale locale, boolean monthSpinner, JFrame calendarframe, TextField field_to_update) {
		this(null, locale, monthSpinner, true, calendarframe, field_to_update);
	}

	public JCalendar(Date date, Locale locale, boolean monthSpinner, boolean weekOfYearVisible, JFrame calendarframe,
			TextField field_to_update) {
		this.field_to_update = field_to_update;
		this.calendarframe = calendarframe;
		dayChooser = null;
		monthChooser = null;
		yearChooser = null;
		this.weekOfYearVisible = weekOfYearVisible;
		this.locale = locale;
		if (locale == null) {
			this.locale = Locale.getDefault();
		}
		calendar = Calendar.getInstance();
		setLayout(new BorderLayout());
		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		PanelOKCancel = new JPanel();
		FlowLayout layoutMgr = new FlowLayout(FlowLayout.LEFT, 2, 2);
		PanelOKCancel.setLayout(layoutMgr);
		PanelOKCancel.add(btnOK);
		PanelOKCancel.add(btnCancel);
		monthYearPanel = new JPanel();
		monthYearPanel.setLayout(new BorderLayout());
		monthChooser = new JMonthChooser(monthSpinner);
		yearChooser = new JYearChooser();
		monthChooser.setYearChooser(yearChooser);
		monthYearPanel.add(monthChooser, BorderLayout.WEST);
		monthYearPanel.add(yearChooser, BorderLayout.CENTER);
		monthYearPanel.setBorder(BorderFactory.createEmptyBorder());
		dayChooser = new JDayChooser(weekOfYearVisible, this);
		dayChooser.addPropertyChangeListener(this);
		monthChooser.setDayChooser(dayChooser);
		monthChooser.addPropertyChangeListener(this);
		yearChooser.setDayChooser(dayChooser);
		yearChooser.addPropertyChangeListener(this);
		add(monthYearPanel, BorderLayout.NORTH);
		add(dayChooser, BorderLayout.CENTER);
		add(PanelOKCancel, BorderLayout.SOUTH);
		if (date != null) {
			calendar.setTime(date);
		}
		initialized = true;
		setCalendar(calendar);
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public JDayChooser getDayChooser() {
		return dayChooser;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	public JMonthChooser getMonthChooser() {
		return monthChooser;
	}

	@Override
	public String getName() {
		return "JCalendar";
	}

	public JYearChooser getYearChooser() {
		return yearChooser;
	}

	public boolean isWeekOfYearVisible() {
		return dayChooser.isWeekOfYearVisible();
	}

	// @Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (calendar != null) {
			Calendar c = (Calendar) calendar.clone();

			if (evt.getPropertyName().equals("day")) {
				c.set(Calendar.DAY_OF_MONTH, ((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("month")) {
				c.set(Calendar.MONTH, ((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("year")) {
				c.set(Calendar.YEAR, ((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("date")) {
				c.setTime((Date) evt.getNewValue());
				setCalendar(c, true);
			}
		}
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);

		if (dayChooser != null) {
			dayChooser.setBackground(bg);
		}
	}

	public void setCalendar(Calendar c) {
		setCalendar(c, true);
	}

	private void setCalendar(Calendar c, boolean update) {
		Calendar oldCalendar = calendar;
		calendar = c;
		if (update) {
			yearChooser.setYear(c.get(Calendar.YEAR));
			monthChooser.setMonth(c.get(Calendar.MONTH));
			dayChooser.setDay(c.get(Calendar.DATE));
		}
		firePropertyChange("calendar", oldCalendar, calendar);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (dayChooser != null) {
			dayChooser.setEnabled(enabled);
			monthChooser.setEnabled(enabled);
			yearChooser.setEnabled(enabled);
		}
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled();
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (dayChooser != null) {
			dayChooser.setFont(font);
			monthChooser.setFont(font);
			yearChooser.setFont(font);
		}
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (dayChooser != null) {
			dayChooser.setForeground(fg);
			monthChooser.setForeground(fg);
			yearChooser.setForeground(fg);
		}
	}

	@Override
	public void setLocale(Locale l) {
		if (!initialized) {
			super.setLocale(l);
		} else {
			Locale oldLocale = locale;
			locale = l;
			dayChooser.setLocale(locale);
			monthChooser.setLocale(locale);
			firePropertyChange("locale", oldLocale, locale);
		}
	}

	public void setWeekOfYearVisible(boolean weekOfYearVisible) {
		dayChooser.setWeekOfYearVisible(weekOfYearVisible);
		setLocale(locale); // hack for doing complete new layout :)
	}

	public boolean isDecorationBackgroundVisible() {
		return dayChooser.isDecorationBackgroundVisible();
	}

	public void setDecorationBackgroundVisible(boolean decorationBackgroundVisible) {
		dayChooser.setDecorationBackgroundVisible(decorationBackgroundVisible);
		setLocale(locale); // hack for doing complete new layout :)
	}

	public boolean isDecorationBordersVisible() {
		return dayChooser.isDecorationBordersVisible();
	}

	public void setDecorationBordersVisible(boolean decorationBordersVisible) {
		dayChooser.setDecorationBordersVisible(decorationBordersVisible);
		setLocale(locale); // hack for doing complete new layout :)
	}

	public Color getDecorationBackgroundColor() {
		return dayChooser.getDecorationBackgroundColor();
	}

	public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
		dayChooser.setDecorationBackgroundColor(decorationBackgroundColor);
	}

	public Color getSundayForeground() {
		return dayChooser.getSundayForeground();
	}

	public Color getWeekdayForeground() {
		return dayChooser.getWeekdayForeground();
	}

	public void setSundayForeground(Color sundayForeground) {
		dayChooser.setSundayForeground(sundayForeground);
	}

	public void setWeekdayForeground(Color weekdayForeground) {
		dayChooser.setWeekdayForeground(weekdayForeground);
	}

	public Date getDate() {
		return new Date(calendar.getTimeInMillis());
	}

	public void setDate(Date date) {
		Date oldDate = calendar.getTime();
		calendar.setTime(date);
		yearChooser.setYear(calendar.get(Calendar.YEAR));
		monthChooser.setMonth(calendar.get(Calendar.MONTH));
		dayChooser.setDay(calendar.get(Calendar.DATE));
		firePropertyChange("date", oldDate, date);
	}

	// @Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnCancel) {
			exitcalendar();
		}
		if (evt.getSource() == btnOK) {
			df = new SimpleDateFormat("yyyy-MM-dd");
			field_to_update.setText(df.format(this.getDate()));
			exitcalendar();
		}
	}

	public void exitcalendar() {
		calendarframe.dispose();
	}

}

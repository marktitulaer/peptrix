package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

class JDayChooser extends JPanel implements ActionListener, KeyListener, FocusListener, MouseListener {

	protected JButton[] days;

	protected JButton[] weeks;

	protected JButton selectedDay;

	protected JPanel weekPanel;

	protected JPanel dayPanel;

	protected int day;

	protected Color oldDayBackgroundColor;

	protected Color selectedColor;

	protected Color sundayForeground;

	protected Color weekdayForeground;

	protected Color decorationBackgroundColor;

	protected String[] dayNames;

	protected Calendar calendar;

	protected Calendar today;

	protected Locale locale;

	protected boolean initialized;

	protected boolean weekOfYearVisible;

	protected boolean decorationBackgroundVisible = true;

	protected boolean decorationBordersVisible;

	private boolean alwaysFireDayProperty;

	JCalendar parentcalendar;

	public JDayChooser() {
		this(false, null);
	}

	public JDayChooser(boolean weekOfYearVisible, JCalendar parentcalendar) {
		this.parentcalendar = parentcalendar;
		setBackground(Color.blue);
		this.weekOfYearVisible = weekOfYearVisible;
		locale = Locale.getDefault();
		days = new JButton[49];
		selectedDay = null;
		calendar = Calendar.getInstance(locale);
		today = (Calendar) calendar.clone();
		setLayout(new BorderLayout());
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(7, 7));
		sundayForeground = new Color(164, 0, 0);
		weekdayForeground = new Color(0, 90, 164);
		decorationBackgroundColor = new Color(210, 228, 238);
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				int index = x + (7 * y);
				if (y == 0) {
					days[index] = new JButton() {

						private static final long serialVersionUID = -5809626126834906203L;

						@Override
						public void addMouseListener(MouseListener l) {
						}

						@Override
						public boolean isFocusable() {
							return false;
						}
					};
					days[index].setContentAreaFilled(decorationBackgroundVisible);
					days[index].setBorderPainted(decorationBordersVisible);
					days[index].setBackground(decorationBackgroundColor);
				} else {
					days[index] = new JButton("x");
					days[index].addActionListener(this);
					days[index].addKeyListener(this);
					days[index].addFocusListener(this);
					days[index].addMouseListener(this);
				}
				days[index].setMargin(new Insets(0, 0, 0, 0));
				days[index].setFocusPainted(false);
				dayPanel.add(days[index]);
			}
		}
		weekPanel = new JPanel();
		weekPanel.setLayout(new GridLayout(7, 1));
		weeks = new JButton[7];
		for (int i = 0; i < 7; i++) {
			weeks[i] = new JButton() {

				private static final long serialVersionUID = 5340957604009810961L;

				@Override
				public void addMouseListener(MouseListener l) {
				}

				@Override
				public boolean isFocusable() {
					return false;
				}
			};
			weeks[i].setMargin(new Insets(0, 0, 0, 0));
			weeks[i].setFocusPainted(false);
			weeks[i].setBackground(decorationBackgroundColor);
			weeks[i].setForeground(new Color(100, 100, 100));
			weeks[i].setContentAreaFilled(decorationBackgroundVisible);
			weeks[i].setBorderPainted(decorationBordersVisible);
			if (i != 0) {
				weeks[i].setText("0" + (i + 1));
			}
			weekPanel.add(weeks[i]);
		}
		init();
		drawWeeks();
		setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		add(dayPanel, BorderLayout.CENTER);
		if (weekOfYearVisible) {
			add(weekPanel, BorderLayout.WEST);
		}
		initialized = true;
	}

	protected void init() {
		JButton testButton = new JButton();
		oldDayBackgroundColor = testButton.getBackground();
		selectedColor = new Color(160, 160, 160);
		Date date = calendar.getTime();
		calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		drawDayNames();
		drawDays();
	}

	private void drawDayNames() {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		dayNames = dateFormatSymbols.getShortWeekdays();
		int day = firstDayOfWeek;
		for (int i = 0; i < 7; i++) {
			days[i].setText(dayNames[day]);
			if (day == 1) {
				days[i].setForeground(sundayForeground);
			} else {
				days[i].setForeground(weekdayForeground);
			}
			if (day < 7) {
				day++;
			} else {
				day -= 6;
			}
		}
	}

	protected void initDecorations() {
		for (int x = 0; x < 7; x++) {
			days[x].setContentAreaFilled(decorationBackgroundVisible);
			days[x].setBorderPainted(decorationBordersVisible);
			weeks[x].setContentAreaFilled(decorationBackgroundVisible);
			weeks[x].setBorderPainted(decorationBordersVisible);
		}
	}

	protected void drawWeeks() {
		Calendar tmpCalendar = (Calendar) calendar.clone();
		for (int i = 1; i < 7; i++) {
			tmpCalendar.set(Calendar.DAY_OF_MONTH, (i * 7) - 6);
			int week = tmpCalendar.get(Calendar.WEEK_OF_YEAR);
			String buttonText = Integer.toString(week);
			if (week < 10) {
				buttonText = "0" + buttonText;
			}
			weeks[i].setText(buttonText);
			if ((i == 5) || (i == 6)) {
				weeks[i].setVisible(days[i * 7].isVisible());
			}
		}
	}

	protected void drawDays() {
		Calendar tmpCalendar = (Calendar) calendar.clone();
		int firstDayOfWeek = tmpCalendar.getFirstDayOfWeek();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
		int firstDay = tmpCalendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
		if (firstDay < 0) {
			firstDay += 7;
		}
		int i;
		for (i = 0; i < firstDay; i++) {
			days[i + 7].setVisible(false);
			days[i + 7].setText("");
		}
		tmpCalendar.add(Calendar.MONTH, 1);
		Date firstDayInNextMonth = tmpCalendar.getTime();
		tmpCalendar.add(Calendar.MONTH, -1);
		Date day = tmpCalendar.getTime();
		int n = 0;
		Color foregroundColor = getForeground();
		while (day.before(firstDayInNextMonth)) {
			days[i + n + 7].setText(Integer.toString(n + 1));
			days[i + n + 7].setVisible(true);
			if ((tmpCalendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR))
					&& (tmpCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR))) {
				days[i + n + 7].setForeground(sundayForeground);
			} else {
				days[i + n + 7].setForeground(foregroundColor);
			}
			if ((n + 1) == this.day) {
				days[i + n + 7].setBackground(selectedColor);
				selectedDay = days[i + n + 7];
			} else {
				days[i + n + 7].setBackground(oldDayBackgroundColor);
			}
			n++;
			tmpCalendar.add(Calendar.DATE, 1);
			day = tmpCalendar.getTime();
		}
		for (int k = n + i + 7; k < 49; k++) {
			days[k].setVisible(false);
			days[k].setText("");
		}
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public void setLocale(Locale locale) {
		if (!initialized) {
			super.setLocale(locale);
		} else {
			this.locale = locale;
			super.setLocale(locale);
			init();
		}
	}

	public void setDay(int d) {
		if (d < 1) {
			d = 1;
		}
		Calendar tmpCalendar = (Calendar) calendar.clone();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
		tmpCalendar.add(Calendar.MONTH, 1);
		tmpCalendar.add(Calendar.DATE, -1);
		int maxDaysInMonth = tmpCalendar.get(Calendar.DATE);
		if (d > maxDaysInMonth) {
			d = maxDaysInMonth;
		}
		int oldDay = day;
		day = d;
		if (selectedDay != null) {
			selectedDay.setBackground(oldDayBackgroundColor);
			selectedDay.repaint();
		}
		for (int i = 7; i < 49; i++) {
			if (days[i].getText().equals(Integer.toString(day))) {
				selectedDay = days[i];
				selectedDay.setBackground(selectedColor);
				break;
			}
		}
		if (alwaysFireDayProperty) {
			firePropertyChange("day", 0, day);
		} else {
			firePropertyChange("day", oldDay, day);
		}
	}

	public void setAlwaysFireDayProperty(boolean alwaysFire) {
		alwaysFireDayProperty = alwaysFire;
	}

	public int getDay() {
		return day;
	}

	public void setMonth(int month) {
		calendar.set(Calendar.MONTH, month);
		boolean storedMode = alwaysFireDayProperty;
		alwaysFireDayProperty = false;
		setDay(day);
		alwaysFireDayProperty = storedMode;
		drawDays();
		drawWeeks();
	}

	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
		drawDays();
		drawWeeks();
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		drawDays();
	}

	@Override
	public void setFont(Font font) {
		if (days != null) {
			for (int i = 0; i < 49; i++) {
				days[i].setFont(font);
			}
		}
	}

	@Override
	public void setForeground(Color foreground) {
		super.setForeground(foreground);
		if (days != null) {
			for (int i = 7; i < 49; i++) {
				days[i].setForeground(foreground);
			}
			drawDays();
		}
	}

	@Override
	public String getName() {
		return "JDayChooser";
	}

	// @Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String buttonText = button.getText();
		int day = new Integer(buttonText).intValue();
		setDay(day);
	}

	// @Override
	public void focusGained(FocusEvent e) {
	}

	// @Override
	public void focusLost(FocusEvent e) {
	}

	// @Override
	public void keyPressed(KeyEvent e) {
		int offset = (e.getKeyCode() == KeyEvent.VK_UP) ? (-7)
				: ((e.getKeyCode() == KeyEvent.VK_DOWN) ? (+7)
						: ((e.getKeyCode() == KeyEvent.VK_LEFT) ? (-1)
								: ((e.getKeyCode() == KeyEvent.VK_RIGHT) ? (+1) : 0)));
		int newDay = getDay() + offset;
		if ((newDay >= 1) && (newDay <= calendar.getMaximum(Calendar.DAY_OF_MONTH))) {
			setDay(newDay);
		}

	}

	// @Override
	public void keyTyped(KeyEvent e) {
	}

	/// @Override
	public void keyReleased(KeyEvent e) {
	}

	// @Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		for (short i = 0; i < days.length; i++) {
			if (days[i] != null) {
				days[i].setEnabled(enabled);
			}
		}
		for (short i = 0; i < weeks.length; i++) {
			if (weeks[i] != null) {
				weeks[i].setEnabled(enabled);
			}
		}
	}

	public boolean isWeekOfYearVisible() {
		return weekOfYearVisible;
	}

	public void setWeekOfYearVisible(boolean weekOfYearVisible) {
		if (weekOfYearVisible == this.weekOfYearVisible) {
			return;
		} else if (weekOfYearVisible) {
			add(weekPanel, BorderLayout.WEST);
		} else {
			remove(weekPanel);
		}
		this.weekOfYearVisible = weekOfYearVisible;
		validate();
		dayPanel.validate();
	}

	public JPanel getDayPanel() {
		return dayPanel;
	}

	public Color getDecorationBackgroundColor() {
		return decorationBackgroundColor;
	}

	public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
		this.decorationBackgroundColor = decorationBackgroundColor;
		if (days != null) {
			for (int i = 0; i < 7; i++) {
				days[i].setBackground(decorationBackgroundColor);
			}
		}
		if (weeks != null) {
			for (int i = 0; i < 7; i++) {
				weeks[i].setBackground(decorationBackgroundColor);
			}
		}
	}

	public Color getSundayForeground() {
		return sundayForeground;
	}

	public Color getWeekdayForeground() {
		return weekdayForeground;
	}

	public void setSundayForeground(Color sundayForeground) {
		this.sundayForeground = sundayForeground;
		drawDayNames();
		drawDays();
	}

	public void setWeekdayForeground(Color weekdayForeground) {
		this.weekdayForeground = weekdayForeground;
		drawDayNames();
		drawDays();
	}

	public void setFocus() {
		if (selectedDay != null) {
			this.selectedDay.requestFocus();
		}
	}

	public boolean isDecorationBackgroundVisible() {
		return decorationBackgroundVisible;
	}

	public void setDecorationBackgroundVisible(boolean decorationBackgroundVisible) {
		this.decorationBackgroundVisible = decorationBackgroundVisible;
		initDecorations();
	}

	public boolean isDecorationBordersVisible() {
		return decorationBordersVisible;
	}

	public void setDecorationBordersVisible(boolean decorationBordersVisible) {
		this.decorationBordersVisible = decorationBordersVisible;
		initDecorations();
	}

	// @Override
	public void mouseClicked(MouseEvent evt) {
		if (evt.getClickCount() > 1) {
			parentcalendar.btnOK.doClick();
		}
	}

	// @Override
	public void mouseEntered(MouseEvent arg0) {
	}

	// @Override
	public void mouseExited(MouseEvent arg0) {
	}

	// @Override
	public void mousePressed(MouseEvent arg0) {
	}

	// @Override
	public void mouseReleased(MouseEvent arg0) {
	}
}

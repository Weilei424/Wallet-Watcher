package UI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;



import org.jfree.chart.ChartPanel;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import com.toedter.calendar.JDateChooser;

import DB.DBUtil;
import persistence.BudgetList;
import persistence.ExpenseInputData;
import persistence.LedgerItem;
import persistence.LedgerList;
import persistence.User;

public class GraphPage implements ActionListener {

	public JFrame anaPageFrame;
	public int source;
	private JPanel anaPanel;
	private JPanel anaPanel1;
	private JLabel title;
	public JTextField expenseNameInput;
	private JLabel expenseCost;
	public JTextField expenseCostInput;
	private JLabel expenseDescription;
	public JTextField expenseDescriptionInput;
	public JTextField expenseDateInput;
	private JButton submit;
	private LedgerItem ledgerItem;
	private EarningPage earn;
	private ExpenseInputData expData;
	
	
	
	private ExpensePage ep;
	private BudgetPage bp;
	
	private NavigatorPage np;
	
	private MiscPage mp;
	private InvestmentPage ip;
	private CardPursePage cp;
	private BillPlannerPage bpp;
	private int framesCreated;
	private JButton resume;
	
	private JTextField othertext;
	private String category;
	private JLabel dateSelector;
	private JDateChooser dateChooser;
	private String formattedDate;
	private static final int Earn=1;
	private static final int Expense=2;
	
	private static final int budget=3;
	private static final int nav=4;
	
	private JPanel panel;
	private ChartPanel piechart;
	private ChartPanel histogram;
	private ChartPanel linegraph;

	 

	public GraphPage(int source) {
		this.source=source;

		this.framesCreated = 0;

		anaPageFrame = new JFrame();
		anaPanel = new JPanel();
		String returns="return to ";
		String head = "Graph for ";
		String choice ="";
		switch(source) {
		case 1:choice="earning";
			break;
		case 2:choice="expense";
		break;
		case 3:choice="budget";
		break;
		case 4:choice="overall";
		break;
		
		case 6:choice="cardpurse";
		break;
		case 7:choice="investment";
		break;
		
		}
		//String choice = source==1?"earning":source==2?"expense":"budget";
		head+=choice;
		returns+=source!=4?choice:"Navigator";
		title = new JLabel(head);
		title.setSize(5, 6);
		title.setFont(new Font("Tahoma", Font.BOLD, 40));

		
		
		//add button to return to previous
		resume = new JButton(new AbstractAction(returns) {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(source==Earn)
					earn = new EarningPage();
				else if(source==Expense)
					ep = new ExpensePage();
				else if(source==budget)
					bp = new BudgetPage();
				else if(source==nav)
					np = new NavigatorPage();
				else if(source==8)
					mp = new MiscPage();
				else if(source==7)
					ip = new InvestmentPage();
				else if (source==6)
					cp = new CardPursePage();
				else 
					bpp = new BillPlannerPage();
				anaPageFrame.dispose();
			}
		});
		resume.setSize(40, 40);
		
		
		
		
		// This panel holds the top elements including the title and the ability to add
				// another button
				anaPanel.setLayout(new GridLayout(1, 3));
				anaPanel.add(title);
				//anaPanel.add(addEarning);
				//anaPanel.add(addana);
				anaPanel.add(resume);

				anaPanel.setBackground(new Color(144, 238, 144));
		
				
				
				anaPanel1 = new JPanel();
				anaPanel1.setLayout(new BorderLayout());
				anaPanel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				anaPanel1.add(anaPanel);
				anaPanel1.setBackground(new Color(144, 238, 144));
				
				
				
				
				anaPageFrame.add(anaPanel1, BorderLayout.NORTH);
				// mainEpFrame.add(ledgerInfo, BorderLayout.CENTER);
				//mainEpFrame.add(export, BorderLayout.SOUTH);
				//mainEpFrame.add(earningScroller, BorderLayout.CENTER);
				anaPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				anaPageFrame.setTitle("Earnings analyze");
				anaPageFrame.setSize(1000, 1000);
				// expensePageFrame.pack(); // when setSize on, then remove pack
				JPanel panel=new JPanel(); 
				panel.setLayout(new GridLayout(1,3));
				if(source==1)
				{ 
					pieChartCategory(User.earnings, choice);
					histogramMonthlyExpense(User.earnings, choice);
					lineGraphDaily(User.earnings, choice);
					panel.add(piechart);
					panel.add(histogram);
					panel.add(linegraph);
				}
				if(source==2)
				{ 
					pieChartCategory(User.expenses, choice);
					histogramMonthlyExpense(User.expenses, choice);
					lineGraphDaily(User.expenses, choice);
					panel.add(piechart);
					panel.add(histogram);
					panel.add(linegraph);
				}
				if(source==3)
				{ 
					histogramBudget(User.allbudgets);
					panel.add(histogram);
				}
				if(source==6)
				{ 
					pieChartCategory(User.cards, choice);
					histogramCard(User.cards);
					panel.add(piechart);
					panel.add(histogram);

					//add histogram of card amounts
					
					
				}
				if(source==7)
				{ 
					pieChartCategory(User.investments, choice);
					histogramMonthlyExpense(User.investments, choice);
					lineGraphDaily(User.investments, choice);
					panel.add(piechart);
					panel.add(histogram);
					panel.add(linegraph);
				}
				
		
				anaPageFrame.add(panel);
				anaPageFrame.setLocationRelativeTo(null);

				anaPageFrame.setVisible(true);
				
		
	}
	public void pieChartCategory(LedgerList list, String choice)
	{
		Map<String,Double> map=list.categorize(choice);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			dataset.setValue(entry.getKey(), entry.getValue());
		} 
		JFreeChart chart = ChartFactory.createPieChart(
			    "Category distribution on Cost",  // chart title
			    dataset,            // dataset
			    true,               // include legend
			    true,               // tooltips
			    false               // urls
		);
		piechart = new ChartPanel(chart);
	}
	public void histogramMonthlyExpense(LedgerList list, String choice)
	{ 
		 DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		 Map<String,Double> map=list.mapfor30days(choice);
	        // Add the data to the dataset
	        for (String xValue : map.keySet()) {
	            Double yValue = map.get(xValue);
	            dataset.addValue(yValue, "Values", xValue);
	        }
	        

	        // Create a JFreeChart object
	        JFreeChart chart = ChartFactory.createBarChart(
	                "Monthly "+choice,
	                "Months",
	                choice,
	                dataset,
	                PlotOrientation.VERTICAL,
	                true,
	                true,
	                false);

		    CategoryPlot plot = chart.getCategoryPlot();
		    CategoryAxis axis = plot.getDomainAxis();
		    axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		    axis.setMaximumCategoryLabelLines(2);
		    
	        // Set chart properties
	        chart.setBackgroundPaint(Color.WHITE);

	        // Create a ChartPanel object and add it to a JFrame
	        histogram = new ChartPanel(chart);
	}

	public void histogramCard(LedgerList list)
	{ 
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    Map<String,Double> map=list.mapforeachcard();
	    // Add the data to the dataset
	    for (String xValue : map.keySet()) {
	        Double yValue = map.get(xValue);
	        dataset.addValue(yValue, "Amount", xValue);
	    }

	    // Create a JFreeChart object
	    JFreeChart chart = ChartFactory.createBarChart(
	            "How much is stored in each card",
	            "Cards",
	            "Amount",
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false);

	    // Set chart properties
	    chart.setBackgroundPaint(Color.WHITE);
	    CategoryPlot plot = chart.getCategoryPlot();
	    CategoryAxis axis = plot.getDomainAxis();
	    axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	    axis.setMaximumCategoryLabelLines(2);

	    // Create a ChartPanel object and add it to a JFrame
	    histogram = new ChartPanel(chart);
	}
	
	public void histogramBudget(BudgetList list)
	{ 
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    Map<String,Double> map=list.mapForEachBudget();
	    // Add the data to the dataset
	    for (String xValue : map.keySet()) {
	        Double yValue = map.get(xValue);
	        dataset.addValue(yValue, "Amount", xValue);
	    }

	    // Create a JFreeChart object
	    JFreeChart chart = ChartFactory.createBarChart(
	            "How much your budgets have been",
	            "Budget",
	            "Amount",
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false);

	    // Set chart properties
	    chart.setBackgroundPaint(Color.WHITE);
	    CategoryPlot plot = chart.getCategoryPlot();
	    CategoryAxis axis = plot.getDomainAxis();
	    axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	    axis.setMaximumCategoryLabelLines(2);

	    // Create a ChartPanel object and add it to a JFrame
	    histogram = new ChartPanel(chart);
	}
	
	
	public void lineGraphDaily(LedgerList list, String choice)
	{ 
		 Map<LocalDate, Double> data = list.mapforEachDay(choice);
	        // Create a TimeSeriesCollection using the HashMap values and keys
	        TimeSeriesCollection dataset = new TimeSeriesCollection();
	        TimeSeries series = new TimeSeries("Data");
	        for (LocalDate key : data.keySet()) {
	            Day day = new Day(key.getDayOfMonth(), key.getMonthValue(), key.getYear());
	            series.add(day, data.get(key));
	        }
	        dataset.addSeries(series);
	        
	        // Create a JFreeChart object with a DateAxis
	        JFreeChart chart = ChartFactory.createTimeSeriesChart(
	                "Daily "+choice, "Date", "Daily Amount", dataset, 
	                true, true, false);
	        DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
	        axis.setDateFormatOverride(new java.text.SimpleDateFormat("dd-MM-yyyy"));

	        // Customize the chart's appearance
	        chart.getXYPlot().setRangeGridlinePaint(Color.BLACK);
	        
	        // Create a ChartPanel object
	        linegraph = new ChartPanel(chart);
	}

	

	public JTextField getExpenseNameInput() {
		return expenseNameInput;
	}

	public JTextField getExpenseCostInput() {
		return expenseCostInput;
	}

	public JTextField getExpenseDescriptionInput() {
		return expenseDescriptionInput;
	}

	public JTextField getExpenseDateInput() {
		return expenseDateInput;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
	
	}

	public LedgerItem getLedgerItem() {
		return ledgerItem;
	}

	public static void main(String[] args) {
		new GraphPage(4);
	}

}

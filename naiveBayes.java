import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Arrays;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;

public class naiveBayes {
	
	public static List<String> vocabulary = new ArrayList<String>();
	public static List<Document> documents = new ArrayList<Document>();
	public static List<Document> trainingDocuments = new ArrayList<Document>();
	public static List<Document> testingDocuments = new ArrayList<Document>();
	
	public static List<ClassLabel> classLabels = new ArrayList<ClassLabel>();
	public static int trueDocuments = 0;
	public static int falseDocuments = 0;
	public static final int threshold = 200;
	
	/*[Creates list of document objects from news.csv]*/
	public static void getNews() {
		try {
			CSVReader reader = new CSVReader(new FileReader("news.csv"), ',' , '"' , 1);
			String[] nextLine;
			int i = 0;
			while ((nextLine = reader.readNext()) != null) {
								
				if (nextLine.length == 5) {
					if (nextLine[4].equals("1")) {
						documents.add(new Document(nextLine[0], nextLine[1], nextLine[2], nextLine[3], true));
					} else {
						documents.add(new Document(nextLine[0], nextLine[1], nextLine[2], nextLine[3], false));
					}
				}
			}
			reader.close();
		} catch(Exception e) {
			System.out.println(e);
			return;
		}
	}
	/*[End]*/
	
	/*[Divides documents into trainingDocuments and testingDocuments]*/
	public static void divide() {
		
		/*
		int rand = (int)Math.random() * 10;
		int tenPercent = documents.size() / 10;
		int startIndex = rand * tenPercent;
		int finishIndex = startIndex + tenPercent;
		
		for (int i = 0; i < documents.size(); i++) {
			
			if (i >= startIndex && i <= finishIndex) {
				trainingDocuments.add(documents.get(i));
				if (documents.get(i).getLabel() == true) {
					trueDocuments++;
				} else {
					falseDocuments++;
				}
			} else {
				testingDocuments.add(documents.get(i));
			}	
		}
		
		*/
		
		int rand = (int)(Math.random() * 4 + 1);
		int training_true = 0;
		int training_false = 0;
		
		for (int i = 0; i < documents.size(); i++) {
			if(training_true < threshold && i % rand == 0 && documents.get(i).getLabel() == true ) {
				trainingDocuments.add(documents.get(i));
				training_true++;
			}
			else if (training_false < threshold && i % rand == 0 && documents.get(i).getLabel() == false) {
				trainingDocuments.add(documents.get(i));
				training_false++;
			}
			
			else {
				testingDocuments.add(documents.get(i));
			}
		}
	}
	/*[End]*/
	
	/*[Creates dictionary by scanning document objects]*/
	public static void createVocabulary() {
		for (int i = 0; i < trainingDocuments.size(); i++) {
				
			Scanner document = new Scanner(trainingDocuments.get(i).getText());
			while (document.hasNextLine()) {
			
				Scanner line = new Scanner(document.nextLine());
				while (line.hasNext()) {
					
					String term = line.next();
					term = term.toLowerCase();
					term = term.replaceAll("[^a-z]", "");
					boolean present = false;
					for (int j = 0; j < vocabulary.size(); j++) {
						
						if (vocabulary.get(j).equals(term)) {
							present = true;
							break;
						}
					}
					if (!present) {
						vocabulary.add(term);
					}
				}	
			}
		}
	}
	/*[End]*/
	
	/*[Adds a true and false class label to classLabels]*/
	public static void configure() {
		classLabels.add(new ClassLabel(true, ((float)threshold / trainingDocuments.size())));
		classLabels.add(new ClassLabel(false, ((float)threshold / trainingDocuments.size())));
	}
	/*[End]*/
	
	/*[Iterates through vocabulary and sums the amount of times each term appears]*/
	public static void vocabulary(List<String> terms, int index) {
		
		int distinctTerms = 0;
		for (int i = 0; i < vocabulary.size(); i++) {
				
			String term  = vocabulary.get(i);
			for (int j = 0; j < terms.size(); j++) {
				
				if (terms.get(j).equals(term)) {
					distinctTerms++;
					break;
				}	
			}
		}
		
		for (int i = 0; i < vocabulary.size(); i++) {
				
			String term  = vocabulary.get(i);
			int termAppearences = 0;
			for (int j = 0; j < terms.size(); j++) {
				
				if (terms.get(j).equals(term)) {
					termAppearences++;
				}
			}
			classLabels.get(index).probability.put(term, ((float)(termAppearences + 1.00) / (distinctTerms + vocabulary.size())));
		}
	}
	/*[End]*/
	
	/*[Calculates probability of terms appearing in trainingDocuments]*/
	public static void calculateProbability() {
		for (int i = 0; i < classLabels.size(); i++) {
			List<String> terms = new ArrayList<String>();
			
			for (int j = 0; j < trainingDocuments.size(); j++) {
				
				if (trainingDocuments.get(j).getLabel() == classLabels.get(i).getLabel()) {
					Scanner document = new Scanner(trainingDocuments.get(j).getText());
					while (document.hasNextLine()) {
					
						Scanner line = new Scanner(document.nextLine());
						while (line.hasNext()) {
							
							String term  = line.next();
							term = term.toLowerCase();
							term = term.replaceAll("[^a-z]", "");
							terms.add(term);
						}	
					}
				}
			}
			vocabulary(terms, i);
		}
	}
	/*[End]*/
	
	/*[Prints probabilites]*/
	public static void print() {
		for (int i = 0; i < classLabels.size(); i++) {
			
			for (int j = 0; j < vocabulary.size(); j++) {
			
				String term  = vocabulary.get(j);
				System.out.println("Class: " + classLabels.get(i).getLabel() + " |  Term: " + term + " |  Probability: " + classLabels.get(i).probability.get(term));
			}	
		}
	}
	/*[End]*/
	
	/*[Training]*/
	public static void train() {
		getNews();
		divide();
		createVocabulary();
		configure();
		calculateProbability();
	}
	/*[End]*/
	
	/*[Testing]*/
	public static void test() {
		int truePositive = 0;
		int falsePositive = 0;
		int trueNegative = 0;
		int falseNegative = 0;
		
		for (int k = 0; k < testingDocuments.size(); k++) {
			
			List<String> terms = new ArrayList<String>();
			Map<Boolean, Double> score = new HashMap<Boolean, Double>();
			Scanner document = new Scanner(testingDocuments.get(k).getText());
			
			while (document.hasNextLine()) {
				Scanner line = new Scanner(document.nextLine());
				
				while (line.hasNext()) {
					
					String term = line.next();
					term = term.toLowerCase();
					term = term.replaceAll("[^a-z]", "");
					
					for (int j = 0; j < vocabulary.size(); j++) {
						if (vocabulary.get(j).equals(term)) {
							terms.add(term);
							break;
						}
					}	
				}	
			}
			
			for (int i = 0; i < classLabels.size(); i++) {
				
				score.put(classLabels.get(i).getLabel(), Math.log(classLabels.get(i).getRatio()));
				for (int j = 0; j < terms.size(); j++) {
					
					score.put(classLabels.get(i).getLabel(), score.get(classLabels.get(i).getLabel()) + Math.log(classLabels.get(i).probability.get(terms.get(j))));
				}	
			}
			
			double trueScore = score.get(true);
			double falseScore = score.get(false);
			
			if (trueScore > falseScore) {
				if (testingDocuments.get(k).getLabel()) {
					truePositive++;
				} else {
					falsePositive++;
				}
			} else {
				if (!testingDocuments.get(k).getLabel()) {
					trueNegative++;
				} else {
					falseNegative++;
				}
			}
		}
		System.out.println("True Positive: " + truePositive);
		System.out.println("False Positive: " + falsePositive);
		System.out.println("True Negative: " + trueNegative);
		System.out.println("False Negative: " + falseNegative);
	}
	/*[End]*/
		
	/*[Main]*/
	public static void main(String[] args) {
		train();
		test();
	}
	/*[End]*/
}

class Document {
   
	private String uuid;
	private String title;
	private String text;
	private String domain;
	private boolean label;
   
	public Document(String uuid, String title, String text, String domain, boolean label) {
		this.uuid = uuid;
	    this.title = title;
	    this.text = text;
	    this.domain = domain;
		this.label = label;
	}
   
	public boolean getLabel() {
		return label;
	}
    
	public String getText() {
		return text;
	}
   
	public String getDomain() {
		return domain;
	}
}

class ClassLabel {
   
	private boolean label;
	private float ratio;
	public Map<String, Float> probability = new HashMap<String, Float>();
  
	public ClassLabel(boolean label, float ratio) {
		this.label = label;
		this.ratio = ratio;
	}
   
	public boolean getLabel() {
		return label;
	}  
   
	public float getRatio() {
		return ratio;
	}  
}
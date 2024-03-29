package ling572;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class SysOutput implements AutoCloseable {
	private static final String SEPARATOR = " ";
	private BufferedWriter writer;
	
	public SysOutput(File sysOutputFile) throws IOException {
		this.writer = new BufferedWriter(new FileWriter(sysOutputFile, true));
	}
	
	public void printHeader(String dataType) throws IOException {
		writer.write("%%%%% ");
		writer.write(dataType);
		writer.write(" data:");
		writer.newLine();
	}
	
	public void printClassProbabilities(int index, Map<String,Double> classProbabilities, String goldLabel) throws IOException {
		writer.write("array:");
		writer.write(Integer.toString(index));
		writer.write(SEPARATOR);
		writer.write(goldLabel);
		writer.write(SEPARATOR);
		
		Map<String,Double> sortedProbabilities = sortMapByValueDesc(classProbabilities);
		
		for(Map.Entry<String,Double> entry: sortedProbabilities.entrySet()) {
			this.printClassProbability(entry.getKey(), entry.getValue());
		}
		
		writer.newLine();
	}
	
	public void newDataType() throws IOException {
		writer.newLine();
		writer.newLine();
		writer.newLine();
	}
	
	public void close() throws IOException {
		this.newDataType();
		this.writer.close();
	}
	
	private void printClassProbability(String classLabel, double probability) throws IOException{
		writer.write(classLabel);
		writer.write(SEPARATOR);
		writer.write(Double.toString(probability));
		writer.write(SEPARATOR);
	}
	
	private static <T> Map<T,Double> sortMapByValueDesc(Map<T,Double> map) {
		List<Entry<T,Double>> list = new LinkedList<Entry<T, Double>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Entry<T,Double>>(){
			public int compare(Entry<T,Double> x, Entry<T,Double> y) {
				int c;
				//	return negative (to reverse)
				c = -(x.getValue()).compareTo(y.getValue());
				
				if (c==0)
					c=(x.getKey().toString()).compareTo(y.getKey().toString());
				
				return c;
			}
		});
			
		Map<T,Double> sortedMap = new LinkedHashMap<T,Double>();
		
		for(Iterator<Entry<T, Double>> i = list.iterator(); i.hasNext();) {			
			Entry<T, Double> entry = i.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}
}

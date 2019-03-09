package com.gso.engines;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gso.model.BombardierWeldingCellInstance;
import com.gso.model.Job;
import com.gso.model.Operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe utilitaire permettant de parser un fichier .xls ou .xlsx pour obtenir une instance
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @author Safwan Al-Shabakji
 * @since 08/03/2019
 * @version 1.0
 */
public class ParsingFileEngine {

	/**
	 * Constuire l'Instance complète
	 * @param fileName
	 * @return
	 */
    public static BombardierWeldingCellInstance BuildInstance(String fileName) {
    	BombardierWeldingCellInstance instance = new BombardierWeldingCellInstance();
    	List<List<String>> data = parseFile(fileName);
    	for(List<String> ligne : data) {
    		Job j = new Job()
    				.setId(new Long(ligne.get(0)))
    				.setSize(ligne.get(3).equals("1.0")? true : false)
    				.setDueDate(new Double(ligne.get(4)))
    				.setPositionTime(new Double(ligne.get(8)));
    		for(int i=1; i<=3; i++) {
    			if(ligne.get(4+i).equals("1.0")) {
    				j.getOperations().add(new Operation()
    						.setWeldingProcess(1)
    						.setProcessingTime(ligne.get(8+i).isEmpty()? new Double(ligne.get(1)) : new Double(ligne.get(8+i))));
    			} else if(ligne.get(4+i).equals("2.0")){
    				j.getOperations().add(new Operation()
    						.setWeldingProcess(2)
    						.setProcessingTime(ligne.get(8+i).isEmpty()? new Double(ligne.get(2)) : new Double(ligne.get(8+i))));
    			}
    		}
    		instance.getJobs().add(j);
    	}
    	displayInstance(instance);
        return instance;
    }

	/**
	 * Parser le fichier
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("resource")
	private static List<List<String>> parseFile(String fileName) {
		List<List<String>> result = new ArrayList<List<String>>();
	    try {
	        FileInputStream excelFile = new FileInputStream(new File(fileName));
	        Sheet datatypeSheet = new XSSFWorkbook(excelFile).getSheetAt(0);
	        Iterator<Row> iterator = datatypeSheet.iterator();
	        while (iterator.hasNext()) {
	        	List<String> ligne = new ArrayList<String>();
	            Row currentRow = iterator.next();
	            boolean first = true;
	            for(int c=0; c<12; c++) {
	                Cell currentCell = currentRow.getCell(c);
	                if(first && (null == currentCell || 0 >= currentCell.getStringCellValue().length())) {
	                	result.remove(0);
	                	return result;
	                }
	                String value = "";
	                if(null != currentCell) {
	                	if (currentCell.getCellType() == CellType.STRING) {
	                    	value = currentCell.getStringCellValue();
	                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
	                    	value = new Double(currentCell.getNumericCellValue()).toString();
	                    }
	                }
	                ligne.add(value);
	                first = false;
	            }
	            result.add(ligne);
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    result.remove(0);
	    return result;
	}
	
	/**
	 * Display an instance job by job
	 * @param i
	 */
	public static void displayInstance(BombardierWeldingCellInstance i) {
    	for(Job j : i.getJobs()) {
    		System.out.println("=== Job : id = "+j.getId()+"; due date = "+j.getDueDate()+"; size = "+j.isSize()+"; position time = "+j.getPositionTime());
    		for(Operation o : j.getOperations()) {
    			System.out.println("operation : time = "+o.getProcessingTime()+"; process type = "+o.getWeldingProcess());
    		}
    	}
	}
}

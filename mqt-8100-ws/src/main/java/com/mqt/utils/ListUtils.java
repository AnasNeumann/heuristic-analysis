package com.mqt.utils;

import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Classe d'outils pour les list et map
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 15/10/2016
 */
public class ListUtils {
	
	/**
	 * Constructeur privé, classe statique
	 */
	private ListUtils(){
		
	}
	
	/**
	 * Recuperation d'uniquement X element d'une liste
	 * 
	 * @param input
	 * @param maxSeeked
	 * @return
	 */
	public static <E> List<E> getOnlyXelements(List<? extends E> input, int maxSeeked) {
		return getElementsInInterval(input, 0, maxSeeked);
	}

	/**
	 * Retourner une liste contenant les [totalElements] éléments de la liste
	 * [input] à partir de [startIndex]
	 * 
	 * @param input
	 * @param startIndex
	 * @param totalElements
	 * @return
	 */
	public static <E> List<E> getElementsInInterval(List<? extends E> input, int startIndex, int totalElements) {
		List<E> result = new ArrayList<E>();
		int total = 0;
		int currentIndex = 0;
		for (E elt : input) {
			if (currentIndex >= startIndex) {
				total++;
				if (totalElements >= total) {
					result.add(elt);
				} else {
					return result;
				}
			}
			currentIndex++;
		}
		return result;
	}
	
	/**
	 * Methode d'inversion d'une liste chainée
	 * @param input
	 * @return
	 */
	public static <E> List<E> reverseList(List<? extends E> input){
		List<E> result = new ArrayList<E>();
		for(int i = input.size()-1; i>=0; i--){
			result.add(input.get(i));
		}
		return result;
	}
	
	/**
	 * Methode permettant de retrouver les keywords d'une query
	 * 
	 * @param query
	 * @return keywords
	 */
	public static List<String> buildKeyworsFromQuery(String query){
		List<String> result = new ArrayList<String>();
		while(isNotEmpty(query) && 1 < query.length()){
			query = query.substring(1);
			String pathArg;
			if(0 < query.indexOf(' ')){
				pathArg = query.substring(0,query.indexOf(' '));
			}else{
				pathArg = query.substring(0);
			}
			result.add(pathArg.toLowerCase());
			query = query.substring(pathArg.length());		
		}
		return result;
	}
	
	/**
	 * Construire une query a partir d'une liste de keywords
	 * @param keywords
	 * @return query
	 */
	public static String buildQueryfromKeywords(List<String> keywords){
		String returnValue = "";
		if(null == keywords){
			return returnValue;
		}
		for(String currentPath : keywords){
			returnValue+=currentPath+" ";
		}
		return returnValue;
	}

}

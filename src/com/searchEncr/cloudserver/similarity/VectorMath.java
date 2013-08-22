package com.searchEncr.cloudserver.similarity;

import java.util.Collection;

public class VectorMath {

	/**
	 * Calculate the Dot Product (inner product) of two vectors
	 * @param vectorOne Vector
	 * @param vectorTwo Vector
	 * @return Dot Product
	 * @throws VectorMathException Thrown if vectors are not of equal length
	 */
	public static double dotp(Collection<Number> vectorOne, Collection<Number> vectorTwo) {
		return dotp(vectorOne.toArray(new Double[0]), vectorTwo.toArray(new Double[0]));
	}

	/**
	 * Calculate the Dot Product (inner product) of two vectors
	 * @param vectorOne Vector
	 * @param vectorTwo Vector
	 * @return Dot Product
	 * @throws VectorMathException Thrown if vectors are not of equal length
	 */
	public static double dotp(Double[] vectorOne, Double[] vectorTwo) {

		double dotProduct = 0;
		for(int i = 0; i < vectorOne.length; i++){
			dotProduct += (vectorOne[i] * vectorTwo[i]);
		}
		return dotProduct;	
	}
	
	/**
	 * Calculate the Magnitude of a vector
	 * @param vector Vector
	 * @return Magnitude of the Vector
	 */
	public static double magnitude(Collection<Number> vector){
		return magnitude(vector.toArray(new Double[0]));
	}
	
	/**
	 * Calculate the Magnitude of a vector
	 * @param vector Vector
	 * @return Magnitude of the Vector
	 */
	public static double magnitude(Double[] vector){
		double magnitude = 0;
		for(int i = 0; i < vector.length; i++){
			magnitude += Math.pow(vector[i], 2);
		}
		return Math.sqrt(magnitude);
	}
	
}

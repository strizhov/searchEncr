package com.searchEncr.dataowner.termweight;

public class TFIDF 
{
	private Number numOfOccurrences;
	private Number totalTermsInDocument;
	private Number totalDocuments;
	private Number numOfDocumentsWithTerm;

	public TFIDF(Number numOfOccurrences, Number totalTermsInDocument, Number totalDocuments, Number numOfDocumentsWithTerm )
	{
		this.numOfOccurrences = numOfOccurrences;
		this.totalTermsInDocument = totalTermsInDocument;
		this.totalDocuments = totalDocuments;
		this.numOfDocumentsWithTerm = numOfDocumentsWithTerm;
	}
	
	/**
	 * Calculates the tf-idf value of the current term. 
     * Because there can be many cases where the current term is not present in any other
     * document in the repository, Float.MIN_VALUE is added to the denominator to avoid
     * DivideByZero exception
     * @return
     */
	public Double getValue()
	{
        double tf =  Math.log10( 1 + (numOfOccurrences.doubleValue() / totalTermsInDocument.doubleValue() ) );
        double idf = Math.log10( 1 + (totalDocuments.doubleValue() /  numOfDocumentsWithTerm.doubleValue()) );
        
        //System.out.println("TF: "+tf+", IDF: "+idf+", Weight: "+(tf*idf));
        return (tf * idf);
	}

}

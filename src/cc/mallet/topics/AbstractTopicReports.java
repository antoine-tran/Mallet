package cc.mallet.topics;

import java.io.*;

public abstract class AbstractTopicReports implements TopicReports {
	
	ParallelTopicModel model;
	
	public AbstractTopicReports(ParallelTopicModel model) {
		this.model = model;
	}
	
	/* These methods will write to the provided writer */
	@Override
	public void printSamplingState(PrintWriter out) throws IOException { }
	@Override
	public void printTopicDocuments(PrintWriter out, int max) throws IOException { }
	@Override
	public void printDocumentTopics(PrintWriter out, double threshold, int max) throws IOException { }
	@Override
	public void printDenseDocumentTopics(PrintWriter out) throws IOException { }
	@Override
	public void printTopicWordWeights(PrintWriter out) throws IOException { }
	@Override
	public void printTypeTopicCounts(PrintWriter out) throws IOException { }
	@Override
	public void printTopicPhrases(PrintWriter out, int numWords) throws IOException { }
	@Override
	public void printSummary(PrintWriter out, int numWords) throws IOException { }

	/* These methods open a file, pass the writer to the above methods, and then close the file */
	@Override
	public void printSamplingState(File file) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printSamplingState(out);
		out.close();
	}
	@Override
	public void printTopicDocuments(File file, int max) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printTopicDocuments(out, max);
		out.close();
	}
	@Override
	public void printDocumentTopics(File file, double threshold, int max) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printDocumentTopics(out, threshold, max);
		out.close();
	}
	@Override
	public void printDenseDocumentTopics(File file) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printDenseDocumentTopics(out);
		out.close();
	}
	@Override
	public void printTopicWordWeights(File file) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printTopicWordWeights(out);
		out.close();
	}
	@Override
	public void printTypeTopicCounts(File file) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printTypeTopicCounts(out);
		out.close();
	}
	@Override
	public void printTopicPhrases(File file, int numWords) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printTopicPhrases(out, numWords);
		out.close();
	}
	@Override
	public void printSummary(File file, int numWords) throws IOException {
		PrintWriter out = new PrintWriter (new FileWriter (file) );
		printSummary(out, numWords);
		out.close();
	}
}
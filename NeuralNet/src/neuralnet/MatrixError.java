package neuralnet;

public class MatrixError extends RuntimeException {

	private static final long serialVersionUID = 2601761949373326421L;
	
	public MatrixError(final Throwable e) {
		super(e);
	}
	
	public MatrixError(final String message) {
		super(message);
	}
}

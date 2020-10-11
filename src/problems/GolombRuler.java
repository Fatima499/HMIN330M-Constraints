package problems;

import org.kohsuke.args4j.Option;


public class GolombRuler extends AbstractProblem {

    @Option(name = "-m", usage = "Golomb ruler order.", required = false)
    private int m = 10;
    private int m2 = m*m;
    private IntVar[] X;
    

    

    @Override
    public void buildModel() {
    	
    	// Build Model here. 
          	model = new Model();
    	 X = new IntVar[m];
    			
  // Declaration des variables Xi
    	
    	for (int i=0; i<=m-1; i++ )
    	{
    		X[i]= model.intVar("X_"+ i, 0 , m2 );
    	}
    	
  // Declaration des contraintes
    	
    	// contrainte X0=0
    	model.arithm(X[0], "=", 0).post();
    	
    	//contrainte Xi < Xi+1
    
    	for(int i=0; i<=m-1; i++)
    	{	
    		model.arithm(X[i], "<", X[i+1]).post();
        }
    	
    	// contrainte Xj-Xi != Xl - Xk
    	double[] coeffs = new double[]{1,-1,-1,1};
    	
    	
    	for( int i=0, j=0, k=0, l=0 ; j<m-1 & l<m-1 & i <j & k <l & ((j!=l) || (i!=k)) ; i++, j++, k++ , l++)
    	{

    		IntVar[] vars = new IntVar[4];
            vars[0]=X[i];
            vars[1]=X[j];
            vars[2]=X[k];
            vars[3]=X[l];	
    		model.scalar(vars , coeffs, "!=", 0).post();
    		
    	}
    	
    }
    }

    @Override
    public void configureSearch() {
    	
    	// Set search here
    }

    @Override
    public void solve() {
  
    	// Set objective if needed;
    	// Solve the instance
    	// Print the solution
        
    }

    public static void main(String[] args) {
        new GolombRuler().execute(args);
    }
}

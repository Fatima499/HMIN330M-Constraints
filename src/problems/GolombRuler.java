package problems;

import org.kohsuke.args4j.Option;


public class GolombRuler extends AbstractProblem {

    @Option(name = "-m", usage = "Golomb ruler order.", required = false)
    private int m = 10;
    private int m2 = m*m;
    private IntVar[] X;
    private IntVar Z;

    

    @Override
    public void buildModel() {
    	
    	
    	model = new Model();
    	 X = new IntVar[m];
    			
  // Declaration des variables Xi
    	
    	for (int i=0; i<m; i++ )
    	{
    		X[i]= model.intVar("X_"+ i, 0 , m2 );
    	}
    	
  // Declaration des contraintes
    	
    	// contrainte X0=0
    	model.arithm(X[0], "=", 0).post();
    	
    	//contrainte Xi < Xi+1
    
    	for(int i=0; i< m-1; i++)
    	{	
    		model.arithm(X[i], "<", X[i+1]).post();
        }
    	
    	// contrainte Xj-Xi != Xl - Xk
    	int[] coeffs = new int[]{1,-1,-1,1};
    	
    	for( int i=0 ; i<=m-2 ; i++)
    	{
            for(int j=i+1 ; j <= m-1; j++)
            {
            	for(int k=0 ; k<=m-2 ; k++)
            	{
            		for(int l=k+1; l <= m-1 && (l !=j); l++)
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
    		
    	}
    	
    	
    }

    @Override
    public void configureSearch() {
    	
    	// Set search here
    }

    @Override
    public void solve() {
  
    	  
     	// Set objective if needed;
    	Z= X[m];
    	 model.setObjective(Model.MINIMIZE, Z);
    	 System.out.println(model.toString());
         
    	 Solver my_solver = model.getSolver();
     	// Solve the instance
         Solution sol = new Solution(model);
         
     	// Print the solution
         while(my_solver.solve()) // tant que le solver find sol
         {
        	 sol.record();
        	 
        	 System.out.println(Z.toString());
        	 
         if(sol != null)
         {
        	 System.out.println(sol.toString());
        	 // affichage de l'opt
        	
         }
         
         }
    }

    public static void main(String[] args) {
        new GolombRuler().execute(args);
    }
}

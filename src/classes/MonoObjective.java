package src.classes;

import jmetal.util.JMException;

import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.core.Solution;





public class MonoObjective extends Problem {

    public MonoObjective(String solution_type, Integer number_of_variables){

        // Check the solution type
        if (solution_type.compareTo("Real") == 0){

            solutionType_ = new RealSolutionType(this);
        }
        else {
            System.out.println("Error, solution type : " + solution_type + "invalid");
        }

        numberOfVariables_ = number_of_variables;
        numberOfConstraints_ = 0;
        problemName_ = "MonoObjective";
        numberOfObjectives_ = 1;

        // Decision variables limits
        lowerLimit_ = new double[numberOfVariables_];
        upperLimit_ = new double[numberOfVariables_];

        for(int i = 0; i < numberOfVariables_ ; i++){
            
            this.lowerLimit_[i] = 0.0;
            this.upperLimit_[i] = 1.0;

        }
    }

    // Compute objective functions
    public void evaluate(Solution solution) throws JMException {

        Variable[] decisionVariables = solution.getDecisionVariables();
        double[] x = new double[this.numberOfVariables_];

        // Get decision variables
        for (int i = 0; i < this.numberOfVariables_; i++){
            x[i] = decisionVariables[i].getValue();
        }

        double f1 = 0.0;
        double f2 = 0.0;
        double gx = 0.0;

        // f1
        f1 = x[0];

        // gx
        double sum_x = 0.0;

        for (int i = 1 ; i < 30; i++){
            sum_x += x[i];
        }

        gx = 1 + 9 * (sum_x / (numberOfVariables_ - 1));

        // f2
        f2 = gx * (1 - Math.sqrt(f1 / gx));

        

        // Fx
        double alpha = 1.0;
        double beta = 1.0;
        double Fx = 0.0;

        // Transform multi objectives in mono objective
        Fx = alpha * f1 + beta * f2;

        System.out.println(Fx);

        solution.setObjective(0, Fx);


    }

}

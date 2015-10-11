package com.example.flowerrec;

import libsvm.*;
import java.io.*;
import java.util.*;

import android.os.Environment;
import android.util.Log;


class svm_predict {
	
	static String TAG = "TEST";
	
	
	private static svm_print_interface svm_print_null = new svm_print_interface()
	{
		public void print(String s) {}
	};

	private static svm_print_interface svm_print_stdout = new svm_print_interface()
	{
		public void print(String s)
		{
			System.out.print(s);
		}
	};

	private static svm_print_interface svm_print_string = svm_print_stdout;

	static void info(String s) 
	{
		svm_print_string.print(s);
	}

	private static double atof(String s)
	{
		return Double.valueOf(s).doubleValue();
	}

	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}

	private static float[][] predict(BufferedReader input, DataOutputStream output, svm_model model, int predict_probability) throws IOException
	{
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;
		
		BeeUtils.values = new Model[BeeUtils.retNumIm+1];

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		double[] prob_estimates=null;
		int[] labels=new int[nr_class];
		
		float[][] ret = new float[nr_class][2];

		if(predict_probability == 1)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
			   svm_type == svm_parameter.NU_SVR)
			{
				svm_predict.info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				
				svm.svm_get_labels(model,labels);
				prob_estimates = new double[nr_class];
				//output.writeBytes("labels");
				for(int j=0;j<nr_class;j++){
					if(j==0){
					output.writeBytes(""+labels[j]);
					}
					output.writeBytes(" "+labels[j]);
				}
				output.writeBytes("\n");
			}
		}
		
		while(true)
		{
			String line = input.readLine();
			if(line == null) break;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			double target = atof(st.nextToken());
			int m = st.countTokens()/2;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
				//x[j].value = 0.5;
			}

			double v;
			if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
			{
				
				v = svm.svm_predict_probability(model,x,prob_estimates);
				
				for(int j=0;j<nr_class;j++){
					output.writeBytes(prob_estimates[j]+" ");
                	ret[j][0] = (float)prob_estimates[j];
                	ret[j][1] = (float)labels[j];
				}
				output.writeBytes("\n");
			}
			else
			{
				v = svm.svm_predict(model,x);
				output.writeBytes(v+"\n");
			}

			if(v == target)
				++correct;
			error += (v-target)*(v-target);
			sumv += v;
			sumy += target;
			sumvv += v*v;
			sumyy += target*target;
			sumvy += v*target;
			++total;
		}
		if(svm_type == svm_parameter.EPSILON_SVR ||
		   svm_type == svm_parameter.NU_SVR)
		{
			svm_predict.info("Mean squared error = "+error/total+" (regression)\n");
			svm_predict.info("Squared correlation coefficient = "+
				 ((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
				 ((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+
				 " (regression)\n");
		}
		else
			svm_predict.info("Accuracy = "+(double)correct/total*100+
				 "% ("+correct+"/"+total+") (classification)\n");
		
		return ret;
	}

	private static void exit_with_help()
	{
		System.err.print("usage: svm_predict [options] test_file model_file output_file\n"
		+"options:\n"
		+"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n"
		+"-q : quiet mode (no outputs)\n");
		System.exit(1);
	}

	public static float[][] run() throws IOException
	{
		float[][] ret = null;
		int i, predict_probability=1;
        	svm_print_string = svm_print_stdout;

		try 
		{
			BufferedReader input = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/testdatascale.txt"));
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/myoutput.txt")));
			svm_model model = svm.svm_load_model(Environment.getExternalStorageDirectory().getPath()+"/FlowerRec/mod.model");
			if(predict_probability == 1)
			{
				if(svm.svm_check_probability_model(model)==0)
				{
					Log.d(TAG,"Model does not support probabiliy estimates\n");
					System.exit(1);
				}
			}
			else
			{
				if(svm.svm_check_probability_model(model)!=0)
				{
					Log.d(TAG,"Model supports probability estimates, but disabled in prediction.\n");
				}
			}
			
			ret = predict(input,output,model,predict_probability);
			
			input.close();
			output.close();
			
		} 
		catch(FileNotFoundException e) 
		{
			System.err.print("I have a file not found ex: " +e.toString());
			exit_with_help();
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
			System.err.print("I have a Array out of bounds ex: " +e.toString());
			exit_with_help();
		}
		
		return ret;
		
	}
}


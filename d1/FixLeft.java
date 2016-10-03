package d1;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class FixLeft {
	public static void main(String[] args) {
		Stack<String> st1 = new Stack<String>();
		Stack<String> st2 = new Stack<String>();
		while(!StdIn.isEmpty()) {
			String s = StdIn.readString();
			if(!s.equals(")")) {
				st1.push(s);
			}
			else {
				st2.push(s);
				
				//push top 3 of stack 1 to stack 2
				for(int i = 0; i < 3; i++) {
					st2.push(st1.pop());
				}
				
				//pop off stack 2, make a new string with left parenthesis
				//and push new string to stack 1
				String newString = "( ";
				for(int i =0; i < 3; i++) {
					newString += st2.pop() + " ";
				}
				newString += st2.pop();
				st1.push(newString);
			}
		}
		StdOut.println(st1.pop());
	}
}

import java.util.*;
import java.io.*;
public class tictactoe {
	public static void main(String[] args)throws IOException{
		
		InputStreamReader read = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(read);
		
		Dictionary<Object, Object> v = new Hashtable<>();
		for (int i=1;i<=9;i++) {
			v.put(i, " ");
		}
		
		int winning_rows[][] = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1, 4, 7}, {2, 5, 8}, {3, 6, 9}, {1, 5, 9}, {3, 5, 7}};
		String board_chart = """
				|1|2|3|
				|4|5|6|
				|7|8|9|
				""";
		
		System.out.println("TicTacToe (vs. Computer)");
		
		System.out.println("\nAssign the value of 'x' in a particlular grid by entering the allotted positon.");
		System.out.println("(Type '0' anytime to get the board chart)\n");
		System.out.println(board_chart);
		
		int i=0;
		int locations[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		String options[] = {"x", "o"};
		int oLoc = 0;
		int xLoc = 0;
		
		while (true) {
			i++;
			while (true) {
				try {
					xLoc = Integer.parseInt(in.readLine());
					if (I_check_element(locations, xLoc)) {
						break;
					} else if (xLoc == 0) {
						System.out.println(board_chart);
					} else {
						System.out.println("Invalid Input, Try Again");
						continue;
					}
					
				} catch (Exception e) {
					System.out.println("Invalid Input, Try Again");
	                continue;
				}
			}
			
			v.put(xLoc, 'x');
			locations = removeFromArr(locations, xLoc);
			
			showBoard(v);
			boolean continueGame = winOrTie(v, winning_rows, i, "x");
			if (continueGame == false) {
				break;
			}
			
			
			//Computer
			System.out.println();
			oLoc = 0;
			
			int get = win(v, oLoc, options, board_chart, winning_rows, locations);
			if (get != 0) {
				oLoc = get;
			} else {
				oLoc = system_logic(v, options, xLoc, oLoc, i, locations);
			}
			
			v.put(oLoc, 'o');
			locations = removeFromArr(locations, oLoc);
			
			showBoard(v);
			continueGame = winOrTie(v, winning_rows, i, "o");
			if (continueGame == false) {
				break;
			}
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static int win(Dictionary v, int oLoc, String[] options, String level, int[][] winning_rows, int[] locations) {
		boolean condition = true;
		for (int[] row : winning_rows) {
			for (int element : row) {
				int position1 = element;
				for (int position2 : row) {
					if (position1 != position2 && v.get(position1) == v.get(position2)) {
						if (v.get(position2).equals('o')) {
							row = removeFromArr(row, position1);
							row = removeFromArr(row, position2);
							
							String vrow0 = ""+v.get(row[0]);
							if (S_check_element(options, vrow0) == false) {
								if (I_check_element(locations, row[0]))
									oLoc = row[0];
									condition = false;
									break;
							}
						} else {
							continue;
						}
					}
				}
			}
		}
		
		if (condition) {
			for (int[] row : winning_rows) {
				for (int element : row) {
					int position1 = element;
					for (int position2 : row) {
						if (position1 != position2 && v.get(position1) == v.get(position2)) {
							if (v.get(position2).equals('x')) {
								row = removeFromArr(row, position1);
								row = removeFromArr(row, position2);
								
								String vrow0 = ""+v.get(row[0]);
								if (S_check_element(options, vrow0) == false && level != "e") {
									oLoc = row[0];
									break;
								}
							} else {
								continue;
							}
						}
					}
				}
			}
		}
		
		return oLoc;
	}
	
	@SuppressWarnings("rawtypes")
	public static int system_logic(Dictionary v, String[] options, int xLoc, int oLoc, int i, int[] locations) {
		if (i == 1) {
			if (xLoc == 5) {
				int sysLocs[] = {7, 3};
				int rnd = new Random().nextInt(sysLocs.length);
				oLoc = sysLocs[rnd];
			} else {
				oLoc = 5;
			}
		}
			
		if (i == 2) {
			while (true) {
				if (v.get(7) == "o" || v.get(3) == "o") {
					int _system_case_[] = {1,9};
					int rnd = new Random().nextInt(_system_case_.length);
					oLoc = _system_case_[rnd];
					break;
				} else {
					int system_case_[] = {2,8,4,6};
					int rnd = new Random().nextInt(system_case_.length);
					oLoc = system_case_[rnd];
					String voloc = ""+v.get(oLoc);
					
					boolean tempConditionCheck = S_check_element(options, voloc);
					if (tempConditionCheck == false) {
						break ;
					} else {
						continue;
					}
				}
			}
		}
		
		if (i!=1 && i!=2) {
			int rnd = new Random().nextInt(locations.length);
			oLoc = locations[rnd];
		}
		
		return oLoc;	
	}
	
	@SuppressWarnings("rawtypes")
	public static int checkWin(Dictionary v, int[][] winning_rows) {
		int won = 10;
		
		for (int[] row : winning_rows) {
			int i = row[0];
			int j = row[1];
			int k = row[2];
			if (v.get(i) == v.get(j)) {
				if (v.get(j) == v.get(k)) {
					if (v.get(k).equals('o')) {
						won = 0;
						break;
					}
					if (v.get(k).equals('x')) {
						won = 1;
						break;
					}
				}
			}
		}
		return won;
	}
	
	public static void showBoard(Dictionary v) {
		System.out.println(
				"|"+v.get(1)+"|"+v.get(2)+"|"+v.get(3)+"|\n"+"|"+v.get(4)+"|"+v.get(5)+"|"+v.get(6)+"|\n"+"|"+v.get(7)+"|"+v.get(8)+"|"+v.get(9)+"|"
		);
	}
	
	public static boolean winOrTie(Dictionary v, int[][] winning_rows, int i, String For)	{ 
		boolean condition = true;
		if (For == "x") {
			if (checkWin(v, winning_rows) == 1) {
				System.out.println("Player Wins!");
				condition = false;
			}
		} else {
			if (checkWin(v, winning_rows) == 0) {
				System.out.println("Computer Wins!");
				condition = false;
			}
		}
		if (condition) {
			if (i == 5) {
				System.out.println("Tie");
				condition = false;
			}
		}
		return condition;
	}
	
	// technical functions
	public static boolean S_check_element(String[] array, String conditional_arg) {
		boolean inThere = false;
		for (String el : array) {
			if (conditional_arg == el) {
				inThere = true;
			}
		}
		return inThere;
	}
	
	public static boolean I_check_element(int[] array, int conditional_arg) {
		boolean inThere = false;
		for (int el : array) {
			if (conditional_arg == el) {
				inThere = true;
			}
		}
		return inThere;
	}
	
	
	public static int[] removeFromArr(int[] arr, int element) {
		int index = 0;
		for (int el : arr) {
			if (el == element) {
				break;
			}
			index++;
		}
		
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        int[] anotherArray = new int[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }
}

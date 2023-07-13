package Cenace;

public class Board {
    
    char[][] elements = new char[3][3];
    int mirror = 0;
    int rotation = 0;

    Board(char fill) {
        int i, j;
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                this.elements[i][j] = fill;
            }
        }
    }

    Board mirror() {
        int i;
        char temp;
        for(i = 0; i < 3; i++) {
            temp = this.elements[i][0];
            this.elements[i][0] = this.elements[i][2];
            this.elements[i][2] = temp;
        }
        mirror++;

        return this;
    }

    Board rotateCW() {
        int N = 3;
        for (int i = 0; i < N / 2; i++) {
            for (int j = i; j < N - i - 1; j++) {
                char temp = this.elements[i][j];
                this.elements[i][j] = this.elements[N - 1 - j][i];
                this.elements[N - 1 - j][i] = this.elements[N - 1 - i][N - 1 - j];
                this.elements[N - 1 - i][N - 1 - j] = this.elements[j][N - 1 - i];
                this.elements[j][N - 1 - i] = temp;
            }
        }
        rotation++;

        return this;
    }

    // Warning: This function works if and only if
    // board was mirrored first or
    // was never mirrored
    Board deleteTransformation() {
        if(rotation % 4 != 0) {
            rotation = rotation % 4;
            int i;
            for(i = 0; i < (4 - rotation); i++) {
                this.rotateCW();
                rotation--;
            }
            rotation = 0;
        }

        if(mirror % 2 != 0) {
            this.mirror();
            mirror = 0;
        }

        return this;
    }

    Board print() {
        int i, j;
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                System.out.print(this.elements[i][j]);
            }
            System.out.println();
        }

        return this;
    }
    
    Board reset() {
    	int i, j;
		for(i = 0; i < 3; i++) {
			for(j = 0; j < 3; j++) {
				this.elements[i][j] = ' ';
			}
		}
		return this;
    }
    
    boolean spaceInBoard() {
    	int i, j;
    	boolean space = false;
		for(i = 0; i < 3; i++) {
			for(j = 0; j < 3; j++) {
				if(this.elements[i][j] == ' ') {
					space = true;
					break;
				}
			}
			if(space) break;
		}
		return space;
    }
    
    int numOfSpaceInBoard() {
    	int i, j;
    	int space = 0;
		for(i = 0; i < 3; i++) {
			for(j = 0; j < 3; j++) {
				if(this.elements[i][j] == ' ') {
					space++;
				}
			}
		}
		return space;
    }
    
    String getString() {
    	String outputString = "";
    	int i, j;
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
            	if(elements[i][j] != ' ') {
            		outputString += elements[i][j];
            	}
            	else {
            		outputString += '-';
            	}
            }
        }
        return outputString;
    }
    
    int getTransformedIndex (int move, boolean mirrored, int numOfCwRotations) { //index is 1 to 9
    	char[][] elements = new char[3][3];
    	int i, j, k = 0;
    	
    	//creating 2d array
    	//using '@' as a given move's index marker only
    	for(i = 0; i < 3; i++) {
    		for(j = 0; j < 3; j++) {
    			if(move == k + 1) {
    				elements[i][j] = '@';
    			} else {
    				elements[i][j] = '-';
    			}
    			k++;
    		}
    	}
    	
    	//now transforming the board
    	if(mirrored) {
    		char temp;
            for(i = 0; i < 3; i++) {
                temp = elements[i][0];
                elements[i][0] = elements[i][2];
                elements[i][2] = temp;
            }
    	}
    	for(k = 0; k < numOfCwRotations; k++) {
    		int N = 3;
            for (i = 0; i < N / 2; i++) {
                for (j = i; j < N - i - 1; j++) {
                    char temp = elements[i][j];
                    elements[i][j] = elements[N - 1 - j][i];
                    elements[N - 1 - j][i] = elements[N - 1 - i][N - 1 - j];
                    elements[N - 1 - i][N - 1 - j] = elements[j][N - 1 - i];
                    elements[j][N - 1 - i] = temp;
                }
            }
    	}
    	
    	//searching the index of '@' in transformed matrix
    	outer:
    	for(i = 0, k = 0; i < 3; i++) {
    		for(j = 0; j < 3; j++) {
    			if(elements[i][j] == '@') {
    				break outer;
    			}
    			k++;
    		}
    	}
    	
    	//returning the index
    	return k + 1;
    }
    
    
    int getUnTransformedIndex (int move, boolean mirrored, int numOfCwRotations) { //index is 1 to 9
    	char[][] elements = new char[3][3];
    	int i, j, k = 0;
    	
    	//creating 2d array
    	//using '@' as a given move's index marker only
    	for(i = 0; i < 3; i++) {
    		for(j = 0; j < 3; j++) {
    			if(move == k + 1) {
    				elements[i][j] = '@';
    			} else {
    				elements[i][j] = '-';
    			}
    			k++;
    		}
    	}
    	
    	//now transforming the board
    	for(k = 0; k < numOfCwRotations; k++) {
    		int N = 3;
            for (i = 0; i < N / 2; i++) {
                for (j = i; j < N - i - 1; j++) {
                    char temp = elements[i][j];
                    elements[i][j] = elements[N - 1 - j][i];
                    elements[N - 1 - j][i] = elements[N - 1 - i][N - 1 - j];
                    elements[N - 1 - i][N - 1 - j] = elements[j][N - 1 - i];
                    elements[j][N - 1 - i] = temp;
                }
            }
    	}
    	
    	if(mirrored) {
    		char temp;
            for(i = 0; i < 3; i++) {
                temp = elements[i][0];
                elements[i][0] = elements[i][2];
                elements[i][2] = temp;
            }
    	}
    	
    	//searching the index of '@' in transformed matrix
    	outer:
    	for(i = 0, k = 0; i < 3; i++) {
    		for(j = 0; j < 3; j++) {
    			if(elements[i][j] == '@') {
    				break outer;
    			}
    			k++;
    		}
    	}
    	
    	//returning the index
    	return k + 1;
    }
    
}

package Cenace;

public class Board {
    
    char[][] elements = new char[3][3];
    int mirror = 0;
    int rotation = 0;

    Board(char fill) {
        int i, j;
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                elements[i][j] = fill;
            }
        }
    }

    Board mirror() {
        int i;
        char temp;
        for(i = 0; i < 3; i++) {
            temp = elements[i][0];
            elements[i][0] = elements[i][2];
            elements[i][2] = temp;
        }
        mirror++;

        return this;
    }

    Board rotateCW() {
        int N = 3;
        for (int i = 0; i < N / 2; i++) {
            for (int j = i; j < N - i - 1; j++) {
                char temp = elements[i][j];
                elements[i][j] = elements[N - 1 - j][i];
                elements[N - 1 - j][i] = elements[N - 1 - i][N - 1 - j];
                elements[N - 1 - i][N - 1 - j] = elements[j][N - 1 - i];
                elements[j][N - 1 - i] = temp;
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
                System.out.print(elements[i][j]);
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
    
    
}

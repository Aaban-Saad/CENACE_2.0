package Cenace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CenaceFileHandler {
    private String filePath;
    private File cenaceFile;

    CenaceFileHandler(String filePath) {
        cenaceFile = new File(filePath);
        this.filePath = filePath;
    }
    
    boolean fileExists() {
    	if(cenaceFile.isFile()) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    void createNewCenaceFile() {
        try {
            cenaceFile.createNewFile();
            FileWriter writer = new FileWriter(cenaceFile);
            writer.write("<!-- CENACE 2.0 -->\n<cenace>\n<player_o>\n</player_o>\n<player_x>\n</player_x>\n</cenace>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    String getFileDataString() {
    	String fileDataString = "";
    	
    	try {
            Scanner input = new Scanner(cenaceFile);
            while(input.hasNextLine()) {
                fileDataString += input.nextLine().trim();
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    	
    	return fileDataString;
    }

    void addNewData(char playerSymbol, String gameBoard, int initialScore) {
        String fileDataString = this.getFileDataString();
        String splitString; 
 
        //adding new data
        if(playerSymbol == 'o' || playerSymbol == 'O') {
            splitString = "</player_o>";
        } else {
            splitString = "</player_x>";
        }

        String[] splittedFileData = fileDataString.split(splitString);
        String temp = "<" + gameBoard + ">";
        int i;
        for(i = 0; i < gameBoard.length(); i++) {
            if(gameBoard.charAt(i) == '-') { //empty space in game board
                temp += "<" + (i + 1) + ">" + initialScore + "</" + (i + 1) + ">";
            }
        }
        temp += "</" + gameBoard + ">";

        fileDataString = "";
        fileDataString += splittedFileData[0];
        fileDataString += temp;
        fileDataString += splitString;
        fileDataString += splittedFileData[1];
        
        String toBeWritten = "";
        for(i = 0; i < fileDataString.length(); i++) {
            if(fileDataString.charAt(i) == '>' && !Character.isDigit(fileDataString.charAt(i - 1)) && (i + 5) <= fileDataString.length() && !Character.isDigit(fileDataString.charAt(i + 4))) {
                toBeWritten += ">\n";
            } else {
                toBeWritten += fileDataString.charAt(i);
            }
        }

        //writing everything back to file
        try {
            FileWriter writer = new FileWriter(cenaceFile);
            writer.write(toBeWritten);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    int getScore(String board, int boardIndex) { //board index in 1 to 9
    	String fileDataString = this.getFileDataString();
    	String begTag = "<" + board + ">";
    	String endTag = "</" + board + ">";
    	String indexBegTag = "<" + boardIndex + ">";
    	String indexEndTag = "</" + boardIndex + ">";
    	String[] boardsSplit1 = fileDataString.split(begTag);
    	String[] boardsSplit2 = boardsSplit1[1].split(endTag);
    	String[] movesSplit1 = boardsSplit2[0].split(indexBegTag);
    	String[] movesSplit2 = movesSplit1[1].split(indexEndTag);
    	
    	return Integer.parseInt(movesSplit2[0]);
    }
    
    
    void updateScore(String board, int boardIndex, int toBeAdded) {
    	String fileDataString = this.getFileDataString();
    	String backupString = fileDataString;
    	String begTag = "<" + board + ">";
    	String endTag = "</" + board + ">";
    	String indexBegTag = "<" + boardIndex + ">";
    	String indexEndTag = "</" + boardIndex + ">";
    	String[] boardsSplit1 = fileDataString.split(begTag);
    	if(boardsSplit1.length == 2) {
    		String[] boardsSplit2 = boardsSplit1[1].split(endTag);
        	String[] movesSplit1 = boardsSplit2[0].split(indexBegTag);
        	String[] movesSplit2 = movesSplit1[1].split(indexEndTag);
        	int score = Integer.parseInt(movesSplit2[0]) + toBeAdded;
        	if(movesSplit2.length == 2) {
        		fileDataString = boardsSplit1[0] + begTag + movesSplit1[0] + indexBegTag + score + indexEndTag + movesSplit2[1] + endTag + boardsSplit2[1];
        	} else {
        		fileDataString = boardsSplit1[0] + begTag + movesSplit1[0] + indexBegTag + score + indexEndTag + endTag + boardsSplit2[1];
    		}
    	} else {
			fileDataString = backupString;
		}
    	
    	String toBeWritten = "";
    	int i;
        for(i = 0; i < fileDataString.length(); i++) {
            if(fileDataString.charAt(i) == '>' && !Character.isDigit(fileDataString.charAt(i - 1)) && (i + 5) <= fileDataString.length() && !Character.isDigit(fileDataString.charAt(i + 4))) {
                toBeWritten += ">\n";
            } else {
                toBeWritten += fileDataString.charAt(i);
            }
        }

        //writing everything back to file
        try {
            FileWriter writer = new FileWriter(cenaceFile);
            writer.write(toBeWritten);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

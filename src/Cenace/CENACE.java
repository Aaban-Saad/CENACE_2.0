package Cenace;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.xml.sax.SAXException;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.TimerTask;
import java.util.spi.CalendarNameProvider;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;
import javax.sound.midi.Soundbank;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.Timer;
import java.awt.SystemColor;
import java.awt.Cursor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class CENACE extends JFrame implements ActionListener{

	private Board board = new Board(' ');
	
	private JPanel contentPane;
	private ImageIcon image_O = new ImageIcon(getClass().getClassLoader().getResource("o.png"));
	private ImageIcon image_X = new ImageIcon(getClass().getClassLoader().getResource("x.png"));
	private ImageIcon image_board = new ImageIcon(getClass().getClassLoader().getResource("board.png"));
	private ImageIcon board_shadow = new ImageIcon(getClass().getClassLoader().getResource("board_shadow.png"));
	private ImageIcon scoreX = new ImageIcon(getClass().getClassLoader().getResource("playerX.png"));
	private ImageIcon scoreO = new ImageIcon(getClass().getClassLoader().getResource("playerO.png"));
	private ImageIcon greenLIght = new ImageIcon(getClass().getClassLoader().getResource("greenLight.png"));
	private ImageIcon winmark_horizintal = new ImageIcon(getClass().getClassLoader().getResource("winmark1.png"));
	private ImageIcon winmark_vertical = new ImageIcon(getClass().getClassLoader().getResource("winmark2.png"));
	private ImageIcon winmark_corner1 = new ImageIcon(getClass().getClassLoader().getResource("winmark3.png"));
	private ImageIcon winmark_corner2 = new ImageIcon(getClass().getClassLoader().getResource("winmark4.png"));
	
	private ImageIcon playerIcon = image_O;
	
	private JButton b1 = new JButton("");
	private JButton b2 = new JButton("");
	private JButton b3 = new JButton("");
	private JButton b4 = new JButton("");
	private JButton b5 = new JButton("");
	private JButton b6 = new JButton("");
	private JButton b7 = new JButton("");
	private JButton b8 = new JButton("");
	private JButton b9 = new JButton("");
	private JButton btnStart = new JButton("Start");
	private JButton btnReset = new JButton("Reset");
	
	private int player = 1; //for "O"
	private int xoImageSize = 84;
	
	private char playerSign = 'o'; //player 1 = 'o' and player 2 = 'x'. Used to assign values of board.elements[][]
	
	private JLabel lbl_greenLightO = new JLabel("");
	private JLabel lbl_greenLightX = new JLabel("");
	private JLabel lbl_winmark = new JLabel("");
	private JLabel label_matches = new JLabel("Match 1");
	private JLabel lbl_oWins = new JLabel("0");
	private JLabel lbl_ties = new JLabel("0");
	private JLabel lbl_xWins = new JLabel("0");
	private JLabel lblPlayerO = new JLabel("CENACE");
	private JLabel lblPlayerX = new JLabel("HUMAN");
	
	private JComboBox comboBox_O;
	private JComboBox comboBox_X;
	
	JCheckBox chckbx_keepPlaying = new JCheckBox("Keep playing");
	
	private Timer timer = new Timer(250, null); //delay time for CENACE... value is changed later
	
	private int matches = 1;
	private int xWin = 0;
	private int oWin = 0;
	private int tie = 0;
	private int ithMoveOfO = 0; //these 2 are needed to update player's BoardList and MoveList arrays
	private int ithMoveOfX = 0;
	
	private boolean oIsWinner = false;
	private boolean xIsWinner = false;
	private boolean gameIsTie = false;
	private boolean gameHasFinished = false;
	
	private boolean oIsRandom = false;
	private boolean xIsRandom = false;
	private boolean oIsCENACE = true;
	private boolean xIsCENACE = false;
	private boolean oIsHuman = false;
	private boolean xIsHuman = true;
	
	private String[] playerOBoardList = new String[5];
	private String[] playerOBoardInKB = new String[5];
	private int[] playerOMoveList = new int[5];
	private int[] playerORotations = new int[5];
	private boolean[] playerOMirrors = new boolean[5];
	
	private String[] playerXBoardList = new String[5];
	private String[] playerXBoardInKB = new String[5];
	private int[] playerXMoveList = new int[5];
	private int[] playerXRotations = new int[5];
	private boolean[] playerXMirrors = new boolean[5];
	
	private CenaceFileHandler cenaceFileHandler = new CenaceFileHandler("Knowledge_base/Training_Data_1.cenace");
	
	
	//change these values for a different result
	private int defaultPoint = 3;
	private int winReward = 3; //reward 
	private int drawReward = 1;
	private int punishment = -1;
	
	
	/**
	 * Create the application.
	 */
	private void reset() {
		for(int i = 0; i < 5; i++) {
			playerOBoardList[i] = null;
			playerOBoardInKB[i] = null;
			playerOMoveList[i] = 0;
			playerORotations[i] = 0;
			playerOMirrors[i] = false;
			
			playerXBoardList[i] = null;
			playerOBoardInKB[i] = null;
			playerXMoveList[i] = 0;
			playerXRotations[i] = 0;
			playerXMirrors[i] = false;
		}
		
		board.reset();
		
		ithMoveOfO = 0; //these 2 are needed to update player's BoardList and MoveList arrays
		ithMoveOfX = 0;
		
		b1.setIcon(null);
		b2.setIcon(null);
		b3.setIcon(null);
		b4.setIcon(null);
		b5.setIcon(null);
		b6.setIcon(null);
		b7.setIcon(null);
		b8.setIcon(null);
		b9.setIcon(null);
		
		lbl_greenLightX.setEnabled(false);
		lbl_greenLightO.setEnabled(true);
		lbl_winmark.setIcon(null);
		
		player = 1;
		playerSign = 'o';
		playerIcon = image_O;
		
		matches++;
		label_matches.setText("Match " + matches);
		
		oIsWinner = false;
		xIsWinner = false;
		gameIsTie = false;
		gameHasFinished = false;
		btnStart.setEnabled(true);
		btnReset.setEnabled(true);
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
		b8.setEnabled(false);
		b9.setEnabled(false);
		
		if(oIsHuman || xIsHuman) {
			btnStart.doClick();
		}
	}
	
	private void choosePlayer(int player) {
		if(player == 1) {
			this.player = 2;
			playerIcon = image_X; //for player 2 (X)
			playerSign = 'x';
			if(!gameHasFinished) {
				lbl_greenLightO.setEnabled(false);
				lbl_greenLightX.setEnabled(true);
			}
			else {
				lbl_greenLightO.setEnabled(false);
				lbl_greenLightX.setEnabled(false);
			}
		} 
		else {				
			this.player = 1;
			playerIcon = image_O; //for player 1 (O)
			playerSign = 'o';
			if(!gameHasFinished) {
				lbl_greenLightX.setEnabled(false);
				lbl_greenLightO.setEnabled(true);
			}
			else {
				lbl_greenLightO.setEnabled(false);
				lbl_greenLightX.setEnabled(false);
			}
		}	
	}
	
	private void compuersMove() {
		int movePosition = 0;
		int r = 0, c = 0;
				
		
		if((oIsRandom && player == 1) || (xIsRandom && player == 2)) {
			do {
				movePosition = (int)(Math.random() * 9 + 1);
				r = (movePosition - 1) / 3;
				c = (movePosition - 1) - 3 * r;

			} while(board.elements[r][c] != ' ');
	
			
			if(movePosition == 1 && !gameHasFinished) {
				b1.doClick();
			}
			else if(movePosition == 2 && !gameHasFinished) {
				b2.doClick();
			}
			else if(movePosition == 3 && !gameHasFinished) {
				b3.doClick();
			}
			else if(movePosition == 4 && !gameHasFinished) {
				b4.doClick();
			}
			else if(movePosition == 5 && !gameHasFinished) {
				b5.doClick();
			}
			else if(movePosition == 6 && !gameHasFinished) {
				b6.doClick();
			}
			else if(movePosition == 7 && !gameHasFinished) {
				b7.doClick();
			}
			else if(movePosition == 8 && !gameHasFinished) {
				b8.doClick();
			}
			else if(movePosition == 9 && !gameHasFinished) {
				b9.doClick();
			}	
		}
		else if((oIsCENACE && player == 1) || (xIsCENACE && player == 2)) {
			if(!cenaceFileHandler.fileExists()) {
				cenaceFileHandler.createNewCenaceFile();
			}
			
			boolean temp = false; //true means CENACE can take input from KB
								  //false means it cannot
			while(true) {
				if(cenaceFileHandler.getFileDataString().contains(board.getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
					
					board.deleteTransformation();
				}
				if(cenaceFileHandler.getFileDataString().contains(board.mirror().getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
					
					
					board.deleteTransformation();
				}
				if(cenaceFileHandler.getFileDataString().contains(board.mirror().rotateCW().getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
					
					board.deleteTransformation();
				}
				if(cenaceFileHandler.getFileDataString().contains(board.mirror().rotateCW().rotateCW().getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
					
					board.deleteTransformation();
				}
				if(cenaceFileHandler.getFileDataString().contains(board.mirror().rotateCW().rotateCW().rotateCW().getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
					
					
					board.deleteTransformation();
				}
				
				board.deleteTransformation();
				if(cenaceFileHandler.getFileDataString().contains(board.rotateCW().getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
					
					board.deleteTransformation();
				}
				if(cenaceFileHandler.getFileDataString().contains(board.rotateCW().rotateCW().getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
				
					board.deleteTransformation();
				}
				if(cenaceFileHandler.getFileDataString().contains(board.rotateCW().rotateCW().rotateCW().getString())) {
					cenaceFileHandler.getMovePriorityArray(board.getString()); //this line is only for debugging..delete it
					temp = true;
					board.deleteTransformation();
					break;
				} else {
					
					board.deleteTransformation();
				}
				break;
			}
			if(temp) {
				System.out.println("can take input from kb");
				System.out.println();
			} else {
				System.out.println("cannot take input from kb");
				System.out.println();
			}
			do {
				movePosition = (int)(Math.random() * 9 + 1);
				r = (movePosition - 1) / 3;
				c = (movePosition - 1) - 3 * r;

			} while(board.elements[r][c] != ' ');
	
			
			if(movePosition == 1 && !gameHasFinished) {
				b1.doClick();
			}
			else if(movePosition == 2 && !gameHasFinished) {
				b2.doClick();
			}
			else if(movePosition == 3 && !gameHasFinished) {
				b3.doClick();
			}
			else if(movePosition == 4 && !gameHasFinished) {
				b4.doClick();
			}
			else if(movePosition == 5 && !gameHasFinished) {
				b5.doClick();
			}
			else if(movePosition == 6 && !gameHasFinished) {
				b6.doClick();
			}
			else if(movePosition == 7 && !gameHasFinished) {
				b7.doClick();
			}
			else if(movePosition == 8 && !gameHasFinished) {
				b8.doClick();
			}
			else if(movePosition == 9 && !gameHasFinished) {
				b9.doClick();
			}
			
			
		}
	}
	
	private void updateScore() {
		if(oIsWinner) {
			oWin++;
			lbl_oWins.setText("" + oWin);
		}
		else if(xIsWinner) {
			xWin++;
			lbl_xWins.setText("" + xWin);
		}
		else if(gameIsTie) {
			tie++;
			lbl_ties.setText("" + tie);
		}
	}
	
	private boolean winGame() {
		boolean win = false; //false means game is running or it is a draw.
							 //true means someone has won
		//checking for o
		
		if     (board.elements[0][0] == 'o' && board.elements[0][1] == 'o' && board.elements[0][2] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_horizintal);
			lbl_winmark.setBounds(0, 0, 250, 84);
			oIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[1][0] == 'o' && board.elements[1][1] == 'o' && board.elements[1][2] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_horizintal);
			lbl_winmark.setBounds(0, 83, 250, 84);
			oIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[2][0] == 'o' && board.elements[2][1] == 'o' && board.elements[2][2] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_horizintal);
			lbl_winmark.setBounds(0, 166, 250, 84);
			oIsWinner = true;
			gameHasFinished = true;
		}
		
		
		
		else if(board.elements[0][0] == 'o' && board.elements[1][0] == 'o' && board.elements[2][0] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_vertical);
			lbl_winmark.setBounds(0, 0, 84, 250);
			oIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[0][1] == 'o' && board.elements[1][1] == 'o' && board.elements[2][1] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_vertical);
			lbl_winmark.setBounds(83, 0, 84, 250);
			oIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[0][2] == 'o' && board.elements[1][2] == 'o' && board.elements[2][2] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_vertical);
			lbl_winmark.setBounds(167, 0, 84, 250);
			oIsWinner = true;
			gameHasFinished = true;
		}
		
		
		
		else if(board.elements[0][0] == 'o' && board.elements[1][1] == 'o' && board.elements[2][2] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_corner2);
			lbl_winmark.setBounds(0, 0, 250, 250);
			oIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[0][2] == 'o' && board.elements[1][1] == 'o' && board.elements[2][0] == 'o') {
			win = true;
			lbl_winmark.setIcon(winmark_corner1);
			lbl_winmark.setBounds(0, 0, 250, 250);
			oIsWinner = true;
			gameHasFinished = true;
		}
			
		
		
		
		//checking for x
		
		else if     (board.elements[0][0] == 'x' && board.elements[0][1] == 'x' && board.elements[0][2] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_horizintal);
			lbl_winmark.setBounds(0, 0, 250, 84);
			xIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[1][0] == 'x' && board.elements[1][1] == 'x' && board.elements[1][2] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_horizintal);
			lbl_winmark.setBounds(0, 83, 250, 84);
			xIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[2][0] == 'x' && board.elements[2][1] == 'x' && board.elements[2][2] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_horizintal);
			lbl_winmark.setBounds(0, 166, 250, 84);
			xIsWinner = true;
			gameHasFinished = true;
		}
		
		
		
		else if(board.elements[0][0] == 'x' && board.elements[1][0] == 'x' && board.elements[2][0] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_vertical);
			lbl_winmark.setBounds(0, 0, 84, 250);
			xIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[0][1] == 'x' && board.elements[1][1] == 'x' && board.elements[2][1] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_vertical);
			lbl_winmark.setBounds(83, 0, 84, 250);
			xIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[0][2] == 'x' && board.elements[1][2] == 'x' && board.elements[2][2] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_vertical);
			lbl_winmark.setBounds(167, 0, 84, 250);
			xIsWinner = true;
			gameHasFinished = true;
		}
		
		
		
		else if(board.elements[0][0] == 'x' && board.elements[1][1] == 'x' && board.elements[2][2] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_corner2);
			lbl_winmark.setBounds(0, 0, 250, 250);
			xIsWinner = true;
			gameHasFinished = true;
		}
		else if(board.elements[0][2] == 'x' && board.elements[1][1] == 'x' && board.elements[2][0] == 'x') {
			win = true;
			lbl_winmark.setIcon(winmark_corner1);
			lbl_winmark.setBounds(0, 0, 250, 250);
			xIsWinner = true;
			gameHasFinished = true;
		}
	
		
		
		else if(!board.spaceInBoard()) {
			gameIsTie = true;
			gameHasFinished = true;
		}
		
		
		if(gameHasFinished) { 
			updateScore(); //to update score in display
			updateKnowledgeBase(); //to update score in KB
		}
		
		return win;
	}
	
	private void updateKnowledgeBase() {
		File knowledgeBase = new File("Knowledge_Base");
		knowledgeBase.mkdir();
		
		if(!cenaceFileHandler.fileExists()) {
			cenaceFileHandler.createNewCenaceFile();
		}
		
		//checking if current game board exists in KB
		if(!gameHasFinished && board.numOfSpaceInBoard() > 1) {
			boolean temp = true;
			while(true) {
				if(!cenaceFileHandler.getFileDataString().contains(board.getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 0;
						playerOMirrors[ithMoveOfO] = false;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 0;
						playerXMirrors[ithMoveOfX] = false;
					}
					board.deleteTransformation();
					break;
				}
				if(!cenaceFileHandler.getFileDataString().contains(board.mirror().getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 0;
						playerOMirrors[ithMoveOfO] = true;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 0;
						playerXMirrors[ithMoveOfX] = true;
					}
					board.deleteTransformation();
					break;
				}
				if(!cenaceFileHandler.getFileDataString().contains(board.mirror().rotateCW().getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 1;
						playerOMirrors[ithMoveOfO] = true;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 1;
						playerXMirrors[ithMoveOfX] = true;
					}
					board.deleteTransformation();
					break;
				}
				if(!cenaceFileHandler.getFileDataString().contains(board.mirror().rotateCW().rotateCW().getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 2;
						playerOMirrors[ithMoveOfO] = true;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 2;
						playerXMirrors[ithMoveOfX] = true;
					}
					board.deleteTransformation();
					break;
				}
				if(!cenaceFileHandler.getFileDataString().contains(board.mirror().rotateCW().rotateCW().rotateCW().getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 3;
						playerOMirrors[ithMoveOfO] = true;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 3;
						playerXMirrors[ithMoveOfX] = true;
					}
					board.deleteTransformation();
					break;
				}
				
				board.deleteTransformation();
				if(!cenaceFileHandler.getFileDataString().contains(board.rotateCW().getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 1;
						playerOMirrors[ithMoveOfO] = false;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 1;
						playerXMirrors[ithMoveOfX] = false;
					}
					board.deleteTransformation();
					break;
				}
				if(!cenaceFileHandler.getFileDataString().contains(board.rotateCW().rotateCW().getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 2;
						playerOMirrors[ithMoveOfO] = false;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 2;
						playerXMirrors[ithMoveOfX] = false;
					}
					board.deleteTransformation();
					break;
				}
				if(!cenaceFileHandler.getFileDataString().contains(board.rotateCW().rotateCW().rotateCW().getString())) {
					temp = false;
					board.deleteTransformation();
				} else {
					temp = true;
					
					if(playerSign == 'o') {
						playerOBoardInKB[ithMoveOfO] = board.getString();
						playerORotations[ithMoveOfO] = 3;
						playerOMirrors[ithMoveOfO] = false;
					} else {
						playerXBoardInKB[ithMoveOfX] = board.getString();
						playerXRotations[ithMoveOfX] = 3;
						playerXMirrors[ithMoveOfX] = false;
					}
					board.deleteTransformation();
					break;
				}
				break;
			}
			//current game board doesn.t exist in KB, adding data
			if(!temp) {
				cenaceFileHandler.addNewData(playerSign, board.getString(), defaultPoint);
				if(playerSign == 'o') {
					playerOBoardInKB[ithMoveOfO] = board.getString();
					playerORotations[ithMoveOfO] = 0;
					playerOMirrors[ithMoveOfO] = false;
				} else {
					playerXBoardInKB[ithMoveOfX] = board.getString();
					playerXRotations[ithMoveOfX] = 0;
					playerXMirrors[ithMoveOfX] = false;
				}
			}
		}
		
		//to update score in KB
		if(gameHasFinished) {

			int i, move;
			
			for(i = 0; (i < 5 && playerOMoveList[i] != 0); i++) {
				move = board.getTransformedIndex(playerOMoveList[i], playerOMirrors[i], playerORotations[i]);

				if(oIsWinner && playerOBoardInKB[i] != null) {
					cenaceFileHandler.updateScore(playerOBoardInKB[i], move, winReward);
				} else if(xIsWinner && playerOBoardInKB[i] != null) {
					cenaceFileHandler.updateScore(playerOBoardInKB[i], move, punishment);
				} else if(gameIsTie && playerOBoardInKB[i] != null){
					cenaceFileHandler.updateScore(playerOBoardInKB[i], move, drawReward);
				}
			}
			
			for(i = 0; (i < 5 && playerXMoveList[i] != 0); i++) {
				move = board.getTransformedIndex(playerXMoveList[i], playerXMirrors[i], playerXRotations[i]);

				if(xIsWinner && playerXBoardInKB[i] != null) {
					cenaceFileHandler.updateScore(playerXBoardInKB[i], move, winReward);
				} else if(oIsWinner  && playerXBoardInKB[i] != null) {
					cenaceFileHandler.updateScore(playerXBoardInKB[i], move, punishment);
				} else if(gameIsTie  && playerXBoardInKB[i] != null){
					cenaceFileHandler.updateScore(playerXBoardInKB[i], move, drawReward);
				}
			}
		}
	}
	
	
	
	
	/**
	 * Create the frame.
	 */
	public CENACE() {
		setBackground(new Color(128, 128, 128));
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\cenace.png"));
		setTitle("CENACE 2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(xoImageSize, xoImageSize, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(227, 230, 230));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));

		setContentPane(contentPane);
		
		lbl_greenLightX.setEnabled(false);
		lbl_greenLightO.setEnabled(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(340, 340));
		layeredPane.setMinimumSize(new Dimension(340, 340));
		layeredPane.setMaximumSize(new Dimension(340, 340));
		
		JPanel panel_bg = new JPanel();
		panel_bg.setOpaque(false);
		panel_bg.setBounds(44, 30, 252, 252);
		panel_bg.setLayout(null);
		
		
		JLabel label_bg = new JLabel("");
		label_bg.setBounds(0, 0, 252, 252);
		label_bg.setIcon(new ImageIcon(image_board.getImage().getScaledInstance(label_bg.getWidth(), label_bg.getHeight(), Image.SCALE_SMOOTH)));
		panel_bg.add(label_bg);
		
		JPanel panel = new JPanel();
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel.setOpaque(false);
		panel.setBounds(44, 30, 250, 250);
		panel.setLayout(new GridLayout(3, 3, 1, 1));
		
		
		b1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b1.setBorderPainted(false);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[0][0] == ' ' && !gameHasFinished) {
					updateKnowledgeBase();
					b1.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 1;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 1;
						ithMoveOfX++;
					}
					board.elements[0][0] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
				
			}
		});
		b1.setContentAreaFilled(false);
		b1.setRequestFocusEnabled(false);
		b1.setFocusTraversalKeysEnabled(false);
		b1.setFocusPainted(false);
		b1.setFocusable(false);
		panel.add(b1);
		

		b2.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b2.setBorderPainted(false);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[0][1] == ' ' && !winGame()) {
					updateKnowledgeBase();
					b2.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 2;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 2;
						ithMoveOfX++;
					}
					board.elements[0][1] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		b2.setContentAreaFilled(false);
		b2.setRequestFocusEnabled(false);
		b2.setFocusTraversalKeysEnabled(false);
		b2.setFocusPainted(false);
		b2.setFocusable(false);
		panel.add(b2);
		

		b3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b3.setBorderPainted(false);
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[0][2] == ' ' && !gameHasFinished) {
					updateKnowledgeBase();
					b3.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 3;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 3;
						ithMoveOfX++;
					}
					board.elements[0][2] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		b3.setContentAreaFilled(false);
		b3.setRequestFocusEnabled(false);
		b3.setFocusTraversalKeysEnabled(false);
		b3.setFocusPainted(false);
		b3.setFocusable(false);
		panel.add(b3);
		

		b4.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[1][0] == ' ' && !gameHasFinished) {
					updateKnowledgeBase();
					b4.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 4;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 4;
						ithMoveOfX++;
					}
					board.elements[1][0] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		b4.setBorderPainted(false);
		b4.setContentAreaFilled(false);
		b4.setRequestFocusEnabled(false);
		b4.setFocusTraversalKeysEnabled(false);
		b4.setFocusPainted(false);
		b4.setFocusable(false);
		panel.add(b4);
		

		b5.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b5.setBorderPainted(false);
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[1][1] == ' ' && !gameHasFinished) {
					updateKnowledgeBase();
					b5.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 5;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 5;
						ithMoveOfX++;
					}
					board.elements[1][1] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		b5.setContentAreaFilled(false);
		b5.setRequestFocusEnabled(false);
		b5.setFocusTraversalKeysEnabled(false);
		b5.setFocusPainted(false);
		b5.setFocusable(false);
		panel.add(b5);
		

		b6.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[1][2] == ' ' && !gameHasFinished) {
					updateKnowledgeBase();
					b6.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 6;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 6;
						ithMoveOfX++;
					}
					board.elements[1][2] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		b6.setBorderPainted(false);
		b6.setContentAreaFilled(false);
		b6.setRequestFocusEnabled(false);
		b6.setFocusTraversalKeysEnabled(false);
		b6.setFocusPainted(false);
		b6.setFocusable(false);
		panel.add(b6);
		

		b7.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[2][0] == ' ' && !gameHasFinished) {
					updateKnowledgeBase();
					b7.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 7;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 7;
						ithMoveOfX++;
					}
					board.elements[2][0] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		b7.setBorderPainted(false);
		b7.setContentAreaFilled(false);
		b7.setRequestFocusEnabled(false);
		b7.setFocusTraversalKeysEnabled(false);
		b7.setFocusPainted(false);
		b7.setFocusable(false);
		panel.add(b7);
		

		b8.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[2][1]== ' ' && !gameHasFinished) {
					b8.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					updateKnowledgeBase();
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 8;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 8;
						ithMoveOfX++;
					}
					board.elements[2][1] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		b8.setBorderPainted(false);
		b8.setContentAreaFilled(false);
		b8.setRequestFocusEnabled(false);
		b8.setFocusTraversalKeysEnabled(false);
		b8.setFocusPainted(false);
		b8.setFocusable(false);
		panel.add(b8);
		

		b9.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(board.elements[2][2] == ' ' && !gameHasFinished) {
					updateKnowledgeBase();
					b9.setIcon(new ImageIcon(playerIcon.getImage().getScaledInstance(xoImageSize, xoImageSize, Image.SCALE_SMOOTH)));
					if(player == 1) {
						playerOBoardList[ithMoveOfO] = board.getString();
						playerOMoveList[ithMoveOfO] = 9;
						ithMoveOfO++;
					}
					else {
						playerXBoardList[ithMoveOfX] = board.getString();
						playerXMoveList[ithMoveOfX] = 9;
						ithMoveOfX++;
					}
					board.elements[2][2] = playerSign;
					winGame();
					choosePlayer(player);
				}
				else if(gameHasFinished) {
					reset();
				}
				else {	//wrong input
					//....
				}
			}
		});
		
		b9.setBorderPainted(false);
		b9.setContentAreaFilled(false);
		b9.setRequestFocusEnabled(false);
		b9.setFocusTraversalKeysEnabled(false);
		b9.setFocusPainted(false);
		b9.setFocusable(false);
		panel.add(b9);
		
		
		JPanel panel_shadow = new JPanel();
		panel_shadow.setOpaque(false);
		panel_shadow.setBounds(40, 35, 270, 270);
		panel_shadow.setLayout(null);
		
		JLabel lblShadow = new JLabel("");
		lblShadow.setBounds(0, 0, 270, 270);
		lblShadow.setIcon(new ImageIcon(board_shadow.getImage().getScaledInstance(lblShadow.getWidth(), lblShadow.getHeight(), Image.SCALE_SMOOTH)));
		panel_shadow.add(lblShadow);
		
		panel_1.add(layeredPane);
		
		JPanel panell_winmark = new JPanel();
		panell_winmark.setOpaque(false);
		panell_winmark.setBounds(44, 30, 250, 250);
		panell_winmark.setLayout(null);
		
		layeredPane.add(panell_winmark);
		layeredPane.add(panel);
		layeredPane.add(panel_bg);
		layeredPane.add(panel_shadow);
		lbl_winmark.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lbl_winmark.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		lbl_winmark.setIcon(null);
		lbl_winmark.setBounds(0, 0, 250, 83);
		panell_winmark.add(lbl_winmark);
		
		JPanel panelScore = new JPanel();
		panelScore.setPreferredSize(new Dimension(340, 150));
		panelScore.setMinimumSize(new Dimension(340, 10));
		panelScore.setMaximumSize(new Dimension(340, 32767));
		panelScore.setOpaque(false);
		panel_1.add(panelScore);
		panelScore.setLayout(null);
		
		JLayeredPane lp_score = new JLayeredPane();
		lp_score.setBounds(0, 0, 340, 150);
		panelScore.add(lp_score);
		
		
		lbl_greenLightO.setBounds(93, 83, 15, 15);
		lbl_greenLightO.setIcon(new ImageIcon(greenLIght.getImage().getScaledInstance(lbl_greenLightO.getWidth(), lbl_greenLightO.getHeight(), Image.SCALE_SMOOTH)));
		lp_score.add(lbl_greenLightO);
		
		
		lbl_greenLightX.setBounds(232, 83, 15, 15);
		lbl_greenLightX.setIcon(new ImageIcon(greenLIght.getImage().getScaledInstance(lbl_greenLightX.getWidth(), lbl_greenLightX.getHeight(), Image.SCALE_SMOOTH)));
		lp_score.add(lbl_greenLightX);
		
		JLabel lbl_vs = new JLabel("VS");
		lbl_vs.setFont(new Font("Consolas", Font.BOLD, 15));
		lbl_vs.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_vs.setBounds(161, 62, 25, 25);
		lp_score.add(lbl_vs);
		
		lblPlayerO.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerO.setFont(new Font("Consolas", Font.BOLD, 15));
		lblPlayerO.setBounds(71, 104, 60, 14);
		lp_score.add(lblPlayerO);
		
		lblPlayerX.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerX.setFont(new Font("Consolas", Font.BOLD, 15));
		lblPlayerX.setBounds(211, 104, 60, 14);
		lp_score.add(lblPlayerX);
		
		JLabel lbl_scoreO = new JLabel("");
		lbl_scoreO.setBounds(50, 0, 101, 150);
		lbl_scoreO.setIcon(new ImageIcon(scoreO.getImage().getScaledInstance(101, 128, Image.SCALE_SMOOTH)));
		lp_score.add(lbl_scoreO);
		
		JLabel lbl_scoreX = new JLabel("");
		lbl_scoreX.setBounds(189, 0, 101, 150);
		lbl_scoreX.setIcon(new ImageIcon(scoreX.getImage().getScaledInstance(101, 128, Image.SCALE_SMOOTH)));
		lp_score.add(lbl_scoreX);
		
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 25));
		contentPane.add(panel_2, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(10, 50));
		contentPane.add(panel_3, BorderLayout.SOUTH);
		
		JPanel panel_left = new JPanel();
		panel_left.setPreferredSize(new Dimension(300, 10));
		contentPane.add(panel_left, BorderLayout.WEST);
		btnStart.setFocusable(false);
		btnStart.setBounds(89, 275, 103, 32);
		
		btnStart.setFocusPainted(false);
		btnStart.addActionListener(this);
		panel_left.setLayout(null);
		btnStart.setFont(new Font("Consolas", Font.BOLD, 20));
		panel_left.add(btnStart);
		
		String[] playerSelect = {"CENACE", "Random", "Human"};
		comboBox_O = new JComboBox(playerSelect);
		comboBox_O.setFocusable(false);
		comboBox_O.setRequestFocusEnabled(false);
		comboBox_O.setFont(new Font("Consolas", Font.PLAIN, 15));
		comboBox_O.setBounds(36, 210, 79, 25);
		comboBox_O.addActionListener(this);
		panel_left.add(comboBox_O);
		
		comboBox_X = new JComboBox(playerSelect);
		comboBox_X.setSelectedIndex(2);
		comboBox_X.setFocusable(false);
		comboBox_X.setRequestFocusEnabled(false);
		comboBox_X.setFont(new Font("Consolas", Font.PLAIN, 15));
		comboBox_X.setBounds(189, 210, 79, 25);
		comboBox_X.addActionListener(this);
		panel_left.add(comboBox_X);
		
		
		btnReset.setFont(new Font("Consolas", Font.BOLD, 20));
		btnReset.setFocusable(false);
		btnReset.setFocusPainted(false);
		btnReset.setBounds(89, 319, 103, 32);
		btnReset.addActionListener(this);
		panel_left.add(btnReset);
		chckbx_keepPlaying.setSelected(true);
		
		chckbx_keepPlaying.setFocusable(false);
		chckbx_keepPlaying.setFont(new Font("Consolas", Font.PLAIN, 12));
		chckbx_keepPlaying.setBounds(89, 362, 115, 37);
		panel_left.add(chckbx_keepPlaying);

		JPanel panel_right = new JPanel();
		panel_right.setBackground(new Color(180, 188, 186));
		panel_right.setPreferredSize(new Dimension(300, 10));
		contentPane.add(panel_right, BorderLayout.EAST);
		panel_right.setLayout(new BoxLayout(panel_right, BoxLayout.Y_AXIS));
		
		JPanel panel_rightTop = new JPanel();
		panel_rightTop.setBackground(new Color(180, 188, 186));
		panel_right.add(panel_rightTop);
		
		JPanel panel_rightBottom = new JPanel();
		panel_rightBottom.setBackground(new Color(188, 183, 180));
		panel_right.add(panel_rightBottom);
		panel_rightBottom.setLayout(null);
		
		
		label_matches.setFont(new Font("Consolas", Font.PLAIN, 18));
		label_matches.setHorizontalAlignment(SwingConstants.CENTER);
		label_matches.setBounds(0, 5, 300, 38);
		panel_rightBottom.add(label_matches);
		
		JLabel lbl_winsAndTies = new JLabel("O wins      Ties      X wins");
		lbl_winsAndTies.setFont(new Font("Consolas", Font.BOLD, 17));
		lbl_winsAndTies.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_winsAndTies.setBounds(0, 54, 300, 30);
		panel_rightBottom.add(lbl_winsAndTies);
		
		JLabel lbl_winsAndTies_1 = new JLabel("___________________________________");
		lbl_winsAndTies_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_winsAndTies_1.setFont(new Font("Consolas", Font.PLAIN, 15));
		lbl_winsAndTies_1.setBounds(0, 69, 300, 30);
		panel_rightBottom.add(lbl_winsAndTies_1);
		
		
		lbl_oWins.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_oWins.setFont(new Font("Consolas", Font.BOLD, 17));
		lbl_oWins.setBounds(0, 93, 100, 30);
		panel_rightBottom.add(lbl_oWins);
		
		
		lbl_ties.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ties.setFont(new Font("Consolas", Font.BOLD, 17));
		lbl_ties.setBounds(100, 93, 100, 30);
		panel_rightBottom.add(lbl_ties);
		
		
		lbl_xWins.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_xWins.setFont(new Font("Consolas", Font.BOLD, 17));
		lbl_xWins.setBounds(200, 93, 100, 30);
		panel_rightBottom.add(lbl_xWins);
		
		//Enables after clicking the start button
		b1.setEnabled(false);
		b2.setEnabled(false);
		b3.setEnabled(false);
		b4.setEnabled(false);
		b5.setEnabled(false);
		b6.setEnabled(false);
		b7.setEnabled(false);
		b8.setEnabled(false);
		b9.setEnabled(false);
		
		//for delay between computer's moves
		timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
            	
            	if(gameHasFinished) {
            		timer.stop();
					btnReset.setEnabled(true);
					if(chckbx_keepPlaying.isSelected()) {
						//if none of the players are human then reset automatically
						if(!oIsHuman && !xIsHuman) {
							reset();
						}
				    	btnStart.doClick();
				    }
				}
				else {
					compuersMove();
				}
            }
        });
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart) {
			btnStart.setEnabled(false);
			btnReset.setEnabled(false);
			b1.setEnabled(true);
			b2.setEnabled(true);
			b3.setEnabled(true);
			b4.setEnabled(true);
			b5.setEnabled(true);
			b6.setEnabled(true);
			b7.setEnabled(true);
			b8.setEnabled(true);
			b9.setEnabled(true);
			
			timer.setDelay(250);
		    timer.start();
		    
		}
		
		else if(e.getSource() == btnReset) {
			if(gameHasFinished) {
				reset();
			}
			//if any one of the players are human then start automatically
		}
		
		else if(e.getSource() == comboBox_O) {
			int index = comboBox_O.getSelectedIndex();
			if(index == 0) {
				oIsCENACE = true;
				oIsRandom = false;
				oIsHuman = false;
				lblPlayerO.setText("CENACE");
			}
			else if(index == 1) {
				oIsCENACE = false;
				oIsRandom = true;
				oIsHuman = false;
				lblPlayerO.setText("RANDOM");
			}
			else {
				oIsCENACE = false;
				oIsRandom = false;
				oIsHuman = true;
				lblPlayerO.setText("HUMAN");
			}
		}
		
		else if(e.getSource() == comboBox_X) {
			int index = comboBox_X.getSelectedIndex();
			if(index == 0) {
				xIsCENACE = true;
				xIsRandom = false;
				xIsHuman = false;
				lblPlayerX.setText("CENACE");
			}
			else if(index == 1) {
				xIsCENACE = false;
				xIsRandom = true;
				xIsHuman = false;
				lblPlayerX.setText("RANDOM");
			}
			else {
				xIsCENACE = false;
				xIsRandom = false;
				xIsHuman = true;
				lblPlayerX.setText("HUMAN");
			}
		}
	}
}

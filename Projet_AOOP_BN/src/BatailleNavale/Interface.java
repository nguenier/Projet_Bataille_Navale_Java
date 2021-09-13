package BatailleNavale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;

public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public int compteur=0;
	public int compteuradv=0;
	public boolean [][] veriteJoueur;
	public boolean [][] veriteAdv;
	public int [][] tabjoueur;
	public int [][] tabadv;
	public int choixdebombe=0;
	public JButton [][] bouton;


	//initialiser la taille des bateaux et les placer
	public static boolean dansTableau(boolean [][] t,int xi,int yi) {
	    if ( xi < 1 )
	      return(false);
	    if ( xi >= t.length )
		      return(false);

	    if ( yi < 1 )
	      return(false);
	    if ( yi >= t.length )
	      return(false);

	    return(true);
	  }

	public static void placement(boolean [][] mat,int direction ,int xi,int yi,int xf,int yf) {
	    if ( direction == 0 ) {
	      for ( int x = xi ; x <= xf ; x++ )
	        mat[yi][x] = true;
	      }
	    else {
	      for ( int y = yi ; y <= yf ; y++ )
	        mat[y][xi] = true;
	      }
	}

	public static boolean posOK(boolean [][] mat,int direction,int x,int y,int xf,int yf) {
	    if ( !dansTableau(mat,x,y) )
	      return(false);
	    if ( !dansTableau(mat,xf,yf) )
	      return(false);
	    if ( direction == 0 ) {
	      for ( int r = x-1 ; r <= xf+1 ; r++ )
	        for ( int t = y-1 ; t <= y+1 ; t++ )
	          if ( dansTableau(mat,r,t) )
	            if ( mat[t][r] )
	              return(false); }
	    else {
	      for ( int r = x-1 ; r <= x+1 ; r++ )
	        for ( int t = y-1 ; t <= yf+1 ; t++ )
	          if ( dansTableau(mat,r,t) )
	            if ( mat[t][r] )
	              return(false); }
	    return(true);
	  }

	public static void Bateau(int longueurBateau,boolean [][] t) {
	    int x,y;
	    int xf = 0,yf = 0;
	    int direction;
	    do {
	      x =(int) (Math.random() * t.length);
	      y =(int) (Math.random() * t.length);
	      direction =(int) (Math.random() * 4);
	      switch (direction) {
	      	case 0 :
	      		yf = y;
	      		xf = x+longueurBateau-1;
	      		break;
	      	case 1 :
	      		yf = y;
	      		xf = x;
	      		x = xf-longueurBateau+1;
	      		break;
	        case 2 :
	        	xf = x;
	            yf = y+longueurBateau-1;
	            break;
	        case 3 :
	        	xf = x;
	            yf = y;
	            y = yf-longueurBateau+1;
	            break; } }
	    while ( !posOK(t,direction/2,x,y,xf,yf) );
	    placement(t,direction/2,x,y,xf,yf);
	  }

	public static boolean [][] genereFlote() {
	    boolean [][] t = new boolean[11][11];
	    for ( int i = 0 ; i < 11 ; i++ ) {
	      for ( int j = 0 ; j < 11 ; j++ ) {
	        t[i][j] = false;}}
	    Bateau(5,t);
	    Bateau(4,t);
	    for ( int i = 0 ; i < 2 ; i++ ) {
	    	Bateau(3,t);}
	    Bateau(2,t);
	    return(t);
	  }

	//bateau present
	public boolean Present(boolean [][]xuv,int x,int y) {
	    if ( !dansTableau(xuv,x,y) )
	      return(false);
	    return(xuv[x][y]);
	}

	//initialiser le plateau a une certaine valeur
	public static int [][] initPlateau(boolean [][] x) throws IOException {
		int [][] mat = new int[11][11];
		for ( int i = 1 ; i < 11 ; i++ ) {
			for ( int j = 1 ; j < 11 ; j++ ) {
				if(x[i][j]==true)mat[i][j]=1;
				else
					mat[i][j] = -1;
		    }
		}
		return(mat);
	}
	//fonction pour que le "bot" joue apres nous
	void Botjoueur() throws IOException {
		int posX,posY;
		posX=(int)(Math.random()* 10)+1;
		posY=(int)(Math.random()* 10)+1;
		while(tabjoueur[posX][posY]==5) {
		posX=(int)(Math.random()* 10)+1;
		posY=(int)(Math.random()* 10)+1;
		}
		if(veriteJoueur[posX][posY]==true) {
			bouton[posX][posY].setBackground(Color.red);
			compteuradv+=1;
			tabjoueur[posX][posY]=5;
		}
		else {
			bouton[posX][posY].setBackground(Color.green);
			tabjoueur[posX][posY]=5;
		}
	}

	//Launch the application.
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Create the frame.
	public Interface() throws IOException {
		super("Bataille Navale");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1220, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		contentPane.add(createToolBar(), BorderLayout.NORTH);
		contentPane.add(createPanelG(), BorderLayout.WEST);
		contentPane.add(createPanelC(), BorderLayout.CENTER);
		contentPane.add(createPanelD(), BorderLayout.EAST);

	}

	//creation de la toolbar contennant les elements pour lancer une nouvelle partie, charger et sauvegarder
	private JToolBar createToolBar() {

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JButton btnPlay = new JButton("Nouvelle partie");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String []args=null;
				Nouvellepartie.main(args);
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		toolBar.add(btnPlay);

		JButton btnSave = new JButton("Sauvegarder");
		toolBar.add(btnSave);

		JButton btnLoad = new JButton("Charger");
		toolBar.add(btnLoad);

		return toolBar;
	}

	//creation du panel contennant les differentes bombes utilisables dans la partie
	private JPanel createPanelG() {
		JPanel panel = new JPanel( new GridLayout (4,1) );

		JButton RadioBH = new JButton("Bombe horizontale");
		RadioBH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choixdebombe=1;
				RadioBH.setEnabled(false);
			}
		});
		panel.add(RadioBH);

		JButton RadioBV = new JButton("Bombe verticale");
		RadioBV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choixdebombe=2;
				RadioBV.setEnabled(false);
			}
		});
		panel.add(RadioBV);

		JButton RadioBC = new JButton("Bombe en croix");
		RadioBC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choixdebombe=3;
				RadioBC.setEnabled(false);
			}
		});
		panel.add(RadioBC);

		return panel;
	}


	//creation du panel contenant tous les boutons qui composent la zone de jeu de l'ordinateur
	private JPanel createPanelD() throws IOException {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(11,11));
		String lettre[]= {"A","B","C","D","E","F","G","H","I","J"};
		String chiffre[]= {"1","2","3","4","5","6","7","8","9","10"};
		veriteAdv= genereFlote();
		tabadv =initPlateau(veriteJoueur);
		JButton [][] bouton;
		bouton = new JButton[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				if( (i==0)  && (j == 0)) {
					bouton[i][j] = new JButton("");
					panel.add(bouton[i][j]);
					bouton[i][j].setEnabled(false);
				}
				else if ( (i==0)  && (j != 0)) {
					JButton button = new JButton(lettre[j-1]);
                    button.setEnabled(false);
                    panel.add(button);
				}
				else if (( i!=0 ) && (j == 0)) {
					bouton[i][j] = new JButton(chiffre[i-1]);
					panel.add(bouton[i][j]);
					bouton[i][j].setEnabled(false);
				}
				else {
					bouton[i][j] = new JButton("");
					final int u=i,v=j;
					bouton[u][v].setBackground(new Color(0,150,250));
					bouton[i][j].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(choixdebombe==0) { //bombe simple
								if(Present(veriteJoueur,u,v)) {
									bouton[u][v].setBackground(Color.red);
									bouton[u][v].setEnabled(false);
									compteur+=1;
								}
								else {
									bouton[u][v].setBackground(Color.green);
									bouton[u][v].setEnabled(false);}
							}
							else if (choixdebombe==1) { //bombe horizontale
								for(int j=1;j<11;++j) {
									if(tabjoueur[u][j]!=9) {
										if(Present(veriteJoueur,u,j)) {
											bouton[u][j].setBackground(Color.red);
											bouton[u][j].setEnabled(false);
											compteur+=1;
											tabjoueur[u][j]=9;
										}
										else {
											bouton[u][j].setBackground(Color.green);
											bouton[u][j].setEnabled(false);
											tabjoueur[u][j]=9;
										}
									}

								}
								choixdebombe=0;
							}
							else if (choixdebombe==2) { //bombe verticale
								for(int i=1;i<11;++i) {
									if(tabjoueur[i][v]!=9) {
										if(Present(veriteJoueur,i,v)) {
											bouton[i][v].setBackground(Color.red);
											bouton[i][v].setEnabled(false);
											compteur+=1;
											tabjoueur[i][v]=9;
										}
										else {
											bouton[i][v].setBackground(Color.green);
											bouton[i][v].setEnabled(false);
											tabjoueur[i][v]=9;}
									}
								}
								choixdebombe=0;
							}
							else if (choixdebombe==3) { //bome en croix
								for(int w=u-1;w<=u+1 && w<11;++w) {
									if(w<=0)w++;
									if(tabjoueur[w][v]!=9) {
										if(Present(veriteJoueur,w,v)) {
											bouton[w][v].setBackground(Color.red);
											bouton[w][v].setEnabled(false);
											compteur+=1;
											tabjoueur[w][v]=9;
										}
										else {
											bouton[w][v].setBackground(Color.green);
											bouton[w][v].setEnabled(false);
											tabjoueur[w][v]=9;}
									}
								}
								for(int w=v-1;w<=v+1 && w<11;w++) {
									if(w<=0)w++;
									if(tabjoueur[u][w]!=9) {
										if(Present(veriteJoueur,u,w)) {
											bouton[u][w].setBackground(Color.red);
											bouton[u][w].setEnabled(false);
											compteur+=1;
											tabjoueur[u][w]=9;
										}
										else {
											bouton[u][w].setBackground(Color.green);
											bouton[u][w].setEnabled(false);
											tabjoueur[u][w]=9;}
									}
								}
								choixdebombe=0;
							}
							try {
								Botjoueur();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							if(compteur>=17 && compteuradv<17) JOptionPane.showMessageDialog(null, "Vous avez gagner");
							else if(compteur<17 && compteuradv>=17) JOptionPane.showMessageDialog(null, "Vous avez perdu");
						}
					});
					panel.add(bouton[i][j]);
				}
			}
		}

		return panel;
	}
	//creation du panel contenant tous les boutons qui composent la zone de jeu du joueur
		private JPanel createPanelC() throws IOException {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(11,11));
			String lettre[]= {"A","B","C","D","E","F","G","H","I","J"};
			String chiffre[]= {"1","2","3","4","5","6","7","8","9","10"};
			veriteJoueur= genereFlote();
			tabjoueur =initPlateau(veriteJoueur);
			bouton = new JButton[11][11];
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 11; j++) {
					if( (i==0)  && (j == 0)) {
						bouton[i][j] = new JButton("");
						panel.add(bouton[i][j]);
						bouton[i][j].setEnabled(false);
					}
					else if ( (i==0)  && (j != 0)) {
						bouton[i][j] = new JButton(lettre[j-1]);
						bouton[i][j].setEnabled(false);
	                    panel.add(bouton[i][j]);
					}
					else if (( i!=0 ) && (j == 0)) {
						bouton[i][j] = new JButton(chiffre[i-1]);
						panel.add(bouton[i][j]);
						bouton[i][j].setEnabled(false);
					}
					else {
						bouton[i][j] = new JButton("");
						final int u=i,v=j;
						bouton[u][v].setBackground(new Color(0,150,250));
						panel.add(bouton[i][j]);
					}
					if(veriteJoueur[i][j]==true) {
						final int u=i,v=j;
						bouton[u][v].setBackground(new Color(0,0,250));
					}

				}
			}

			return panel;
		}

}

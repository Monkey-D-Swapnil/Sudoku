import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Sudoku extends JFrame implements ActionListener 
{
   int x[],y[];
   static JButton bsolve,bclose,bmode,bminimize,bhelp,b0,b1,b2,b3;
   static JFrame frame;static JTextArea results;
   static JTextField graphicalboard[][]= new JTextField[9][9],title;
   static int mode=0,help=0,cus=1,ck=0,lv=3;
   static  long t1,t2;
   public static void dis()
    {
        frame.dispose();
    }
   public void helpdesk(int ch)
   {
       if(help == 1)
      { 
        results.setBackground(Color.white);
       switch (ch)
       {
        case 1:results.setText("σ>Easy mode \nDisplays 45 to 50 numbers");break;
        case 2:results.setText("σ>Medium Mode  \nDisplays 30 to 40 numbers");break;
        case 3:results.setText("σ>Hard Mode \nDisplays 20 to 25 numbers");break;
        case 4:results.setText("σ>Give Your Own Sudoku to solve"); break;
        case 5:results.setText("σ>Your Everday Classical Sudoku ");break;
        case 6:results.setText("σ>Any two squares joined\n by a Knight move \ncannot have same digit");break;
        case 7:results.setText("σ>Left & Right Diagonals\n have number 1 to 9");break;
        case 8:results.setText("σ>Solves or Checks the Sudoku for you");break;
        case 9:results.setText("σ>Help Button(?) \nTo view help");break;
        case 10:results.setText("Exit Button(X) \nExits Sudoku");break;
        case 11:results.setText("Minmize Button([...])-Minimizes the Sudoku");break;
       }
       help = 0;
      }
   }
   public void clearBoard()//# clears the Display for new output
   {
            for(int i=0;i<9;i++)
            {
                 for(int j=0;j<9;j++)
                 {
                     graphicalboard[i][j].setText("");graphicalboard[i][j].setEditable(true);
                 }
            }
            results.setText("");
   }
   public boolean errorinBoard()//# Checks whether the user gave any wrong input or not
   {
       for(int i=0;i<9;i++)
       {
           for(int j=0;j<9;j++)
           {
              if(graphicalboard[i][j].getText().length()>1 || "123456789".indexOf(graphicalboard[i][j].getText())==-1) 
                  return true;
           }
       }
       return false;
   }
     public int SudokuNumbers(int a, int b)//# Used by the Solution() method for Sudoku Generation
   {
        while (true)
        {
           int x = (int)(Math.random()*100);
            if(x>=a && x<=b)
            return x;
        }
   }
   public boolean validity(int board[][])//# Checks validity of the sudoku
   {
       for(int i=0;i<9;i++)
       {
           for(int j=0;j<9;j++)
           {
             if(board[i][j]!=0)
             {
             if(inRowColGrid(board,board[i][j],i,j)||board[i][j]<1||board[i][j]>9)
             return false;
            }
           }
       }
       return true;
   }
   public int[][] inputBoard()//# Takes input for the Sudoku from user
   {
       int board[][]=new int[9][9];
       for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(graphicalboard[i][j].getText().length()==1)
                board[i][j]=Integer.valueOf(graphicalboard[i][j].getText());
            }
        }
       return board;
   }
   public void outputBoard1(int board[][]) //# Displays the Sudoku
   {
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
               graphicalboard[i][j].setText(""+board[i][j]);
   }
   public void outputBoard2(int board[][])//# Displays the Generated Sudoku
   {
     for(int i=0;i<9;i++)
     {
         for(int j=0;j<9;j++)
         {
              int fl=0;
             for(int k=0;k<x.length;k++)
             {
                 if(i==x[k] && j==y[k])
                { fl=1;break;}
             }
             if(fl==1)
              {
                  graphicalboard[i][j].setText(board[i][j]+"");
                  graphicalboard[i][j].setEditable(false);
              }
             else
              {
                  graphicalboard[i][j].setText("");
                  graphicalboard[i][j].setBackground(Color.white);
                  graphicalboard[i][j].setForeground(Color.red);
                  graphicalboard[i][j].setEditable(true);
              }
         }
     }
   }
   public boolean inRowColGrid(int board[][],int n,int row,int column) //# Helps in checking whether a numberis possible to place in the Sudoku                                                            
   {
        for(int i=0;i<9;i++)
         if(board[row][i]==n && i!=column)
            return true;
        for(int i=0;i<9;i++)
         if(board[i][column]==n && i!=row)
            return true;
         int i=row-(row%3),j =column - (column%3);
        for(int k=i;k<i+3;k++)
        {
            for(int q=j;q<j+3;q++)
            if(board[k][q]==n && k!=row && q!=column)
            return true;
        }
        if(mode == 1)
        {
            int x1 = column + 2;int x2 = column - 2;
            int y1 = row - 2;int y2 = row + 2;
            if(row-1>0 && x1<board[row-1].length && board[row-1][x1]==n)
            return true;
            if(row-1>0 && x2 > 0 && board[row-1][x2]==n)
            return true;
            if(row+1<board.length && x1<board[row+1].length && board[row+1][x1]==n)
            return true;
            if(row+1<board.length && x2 > 0 && board[row+1][x2]==n)
            return true;
            if(y1 > 0 && column+1<board[y1].length  && board[y1][column+1]==n)
            return true;
            if(column-1>0 && y1>0 && board[column-1][y1]==n)
            return true;
            if( y2 < board.length && column+1<board[y2].length  && board[y2][column+1]==n)
            return true;
            if(column-1>0 && y2 < board.length && board[y2][column-1]==n)
            return true;
        }
        else if(mode == 2 )
        {
         if(row==column)
         {
             for(int k=0;k<9;k++)
             if(board[k][k]==n && row!=k)
             return true;
         }
         else if (row == 8-column)
         {
             for(int k=0;k<9;k++)
             if(board[k][8-k]==n && (8-k)!=column)
             return true;
         }
        }
        return false;
   }
   public boolean Solution(int board[][]) //# Solves the Sudoku
   {
       if(!validity(board))
        return false;
       int a[]=new int[9];
       for(int i=0;i<9;i++)//# Required for Sudoku generation as well
       {
       a[i]=SudokuNumbers(1,9);
       for(int j=0;j<i;j++)
       if(a[i]==a[j])
       {i--;break;}
       }
       for(int i=0;i<9;i++)
       {
           for(int j=0;j<9;j++)
           {
               if(board[i][j]==0)
               {
                   for(int k=0;k<9;k++)
                 {
                    if(!inRowColGrid(board,a[k],i,j))
                    {
                    board[i][j]=a[k];
                    if(Solution(board))
                    return true;
                    else 
                    board[i][j]=0;
                    }
                 }
                 return false;
                } 
           }
       }
       return true;
   }
   public  void SudokuSolver()//# Calls the Solution() method to solve the Sudoku
   {
      int board[][]=inputBoard();
      int f=0;
      for(int i=0;i<81;i++)
       if(board[i/9][i%9]==0)
       {f=1;break;}
      if(Solution(board))
        {
            outputBoard1(board);
            if(ck==0)
            {results.setText("#Sudoku Solved Succesfully"+((f==0 && cus==0)?" in "+milli_to_minsec(t1,t2):""));
             ck=(cus==1)?0:1;}
        }
      else 
       results.setText("#Invalid Sudoku");
      results.setBackground(Color.white);
   }
   String milli_to_minsec(long t1,long t2)
   {
       long s=(t2-t1)/1000;
       long m=s/60;
       s=s%60;
       return m+":"+s;
   }
   public void SudokuGenerate(int ch) //# Generates new Sudoku for user
   {
       results.setText("");int board[][]=new int[9][9];int p=0;
        switch(ch)
        {
            case 3:p=SudokuNumbers(20,25);break;
            case 2:p=SudokuNumbers(30,40);break;
            case 1:p=SudokuNumbers(45,50);break;
        }   
        x=new int[p]; y=new int[p];
        for(int i=0;i<p;i++)
        {
            x[i]=SudokuNumbers(0,8);y[i]=SudokuNumbers(0,8);
            for(int j=0;j<i;j++)
              if(x[j]==x[i] && y[i]==y[j])
                {i--;break;}
        }   
      Solution(board);outputBoard2(board);
   }
   public static void main(String args[]) //# Creates the frame,grid,button and timer 
    {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 
        catch (Exception e){System.err.println(e.getMessage());}
        JPanel panel = new JPanel();
        bsolve = new JButton("    Solve / Check    ");
        bclose = new JButton("X");bminimize = new JButton("[...]");
        bmode=new JButton("CLASSICAL MODE");
        bhelp=new JButton("?");
        b0 = new JButton("          Easy         ");
        b1 = new JButton("         Medium        ");
        b2 = new JButton("          Hard         ");
        b3 = new JButton("         Custom        ");
        b0.setBackground(Color.GREEN);
        b0.setBorderPainted(false);b0.setFocusable(false);     
        b0.setFont(new Font("Times New Roman",Font.ITALIC,24));
        b1.setBackground(Color.CYAN);b1.setBorderPainted(false);b1.setFocusable(false);
        b1.setFont(new Font("Times New Roman",Font.ITALIC,24));
        b2.setBackground(new Color(250, 110, 75));
        b2.setBorderPainted(false);b2.setFocusable(false);
        b2.setFont(new Font("Times New Roman",Font.ITALIC,24));
        b3.setBackground(new Color(246, 242, 205));
        b3.setBorderPainted(false);b3.setFocusable(false);
        b3.setFont(new Font("Times New Roman",Font.ITALIC,24));
        bsolve.setBackground(new Color(100, 149, 237));  
        bsolve.setFont(new Font("Times New Roman",Font.ITALIC,24));
        bsolve.setFocusable(false);bsolve.setBorderPainted(false);  
        bclose.setBackground(new Color(115,200,149));
        bclose.setBorderPainted(false);bclose.setFocusable(false);
        bclose.setFont(new Font("Verdana",Font.BOLD,20));
        bmode.setBackground(Color.magenta); bmode.setBorderPainted(false);bmode.setFocusable(false);
        bmode.setFont(new Font("Times New Roman",Font.ITALIC,24));
        bhelp.setBackground(Color.gray);bhelp.setBorderPainted(false);
        bhelp.setFocusable(false);bhelp.setFont(new Font("Verdana",Font.BOLD,20));
        bminimize.setFont(new Font("Verdana",Font.BOLD,20)); bminimize.setFocusable(false);
        bminimize.setFocusable(false);bminimize.setBackground(Color.gray.darker().darker());
        title=new JTextField("S-U-D-O-K-U",15);title.setEditable(false);
        title.setFont(new Font("Times New Roman",Font.ITALIC,24));
        title.setHorizontalAlignment(SwingConstants.CENTER);title.setForeground(Color.red);
        bsolve.addActionListener(new Sudoku());bclose.addActionListener(new Sudoku());
        bmode.addActionListener(new Sudoku()); bhelp.addActionListener(new Sudoku());
        b0.addActionListener(new Sudoku());b1.addActionListener(new Sudoku());bminimize.addActionListener(new Sudoku());
        b2.addActionListener(new Sudoku());b3.addActionListener(new Sudoku());
        panel.add(bhelp);panel.add(bminimize);panel.add(bclose);panel.add(title);
        panel.add(b0);panel.add(b1);panel.add(b2);panel.add(b3);
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graphicalboard[i][j] = new JTextField("",2);
                graphicalboard[i][j].setEditable(true);
                graphicalboard[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                graphicalboard[i][j].setFont(new Font("Verdana",Font.PLAIN,20));
                panel.add(graphicalboard[i][j]);
            }
        }
        results = new JTextArea(); results.setEditable(false);
        results.setFont(new Font("Verdana",Font.PLAIN,20));
        frame = new JFrame(); frame.setUndecorated(true);
        panel.add(bsolve);panel.add(bmode);panel.add(results);
        panel.setBackground(Color.black);frame.add(panel);
        frame.setSize(460,600);frame.setResizable(false);frame.show();
        frame.setLocation(90,100);
        frame.getRootPane().setDefaultButton(bsolve);
        frame.setCursor( Cursor.CROSSHAIR_CURSOR);colorBoard();
        results.setBackground(Color.black);results.setForeground(Color.red);fore();
    }
   public static void fore()
   {
       b0.setForeground((lv==0)?Color.red:Color.black);b1.setForeground((lv==1)?Color.red:Color.black);
       b2.setForeground((lv==2)?Color.red:Color.black);b3.setForeground((lv==3)?Color.red:Color.black);
   }
   @Override
   public void actionPerformed(ActionEvent click)//# Takes care of all the clicks on the button 
   {
          String s = click.getActionCommand().trim();  
          if(s.equals("[...]"))
          {    if(help == 0)
              frame.setState(Frame.ICONIFIED);
              else 
              helpdesk(11);
          }
          else
          {results.setBackground(Color.black);
            if(s.equals("?"))
            {   
             if(help == 0)
             {
              results.setText("");results.setBackground(Color.black);
              results.setFont(new Font("Verdana",Font.PLAIN,20));help = 1;
              results.setBackground(Color.white); results.setText("σ>Press another Button to check");
             }
             else 
              helpdesk(9);
          }
        if(s.equals("X"))
        {    
             if(help ==0)
             frame.dispose();
             else 
             helpdesk(10);
        }
         if(s.equals("Solve / Check"))
         
        {   if(help == 0 )
            {if(errorinBoard())
            {
                results.setBackground(Color.white);
                results.setText("#Invalid Sudoku");
            }
            else
             {t2=System.currentTimeMillis();SudokuSolver();}
            }
            else 
            helpdesk(8);
         }
        if(s.equals("Easy"))
           { 
               if(help ==0)
               {colorBoard();SudokuGenerate(1);t1=System.currentTimeMillis();lv=0;cus=0;ck=0;}helpdesk(1);
           }
           
        else if(s.equals("Medium"))
         {
             if(help == 0)
             {colorBoard();SudokuGenerate(2);t1=System.currentTimeMillis();lv=1;cus=0;ck=0;}helpdesk(2);
         }
         
        else if(s.equals("Hard"))
             {
              if(help ==0)
              {colorBoard();SudokuGenerate(3);t1=System.currentTimeMillis();lv=2;cus=0;ck=0;}helpdesk(3);
             }
          
        else if(s.equals("Custom"))
        {
           if(help ==0)
           {clearBoard();colorBoard();cus=1;lv=3;ck=0;}helpdesk(4);
        }
        else if(s.equals("CLASSICAL MODE"))
        {
             cus=1;
             if(help == 0)
            {
                clearBoard();colorBoard();mode = 1;
                bmode.setText("  KNIGHT MODE  ");results.setText("");
            }
            else 
            helpdesk(5);
        }
        else if(s.equals("KNIGHT MODE"))
        {
           cus=1;
           if(help ==0)
           {
            clearBoard();colorBoard();mode = 2;
            bmode.setText(" DIAGONAL MODE ");results.setText("");
           } 
           else 
           helpdesk(6);           
        }
        else if (s.equals("DIAGONAL MODE"))
        {
            cus=1;
            if(help == 0)
            {
            clearBoard();colorBoard();mode = 0;
            bmode.setText(" CLASSICAL MODE ");results.setText("");
            }
            else 
            helpdesk(7);
        }}
        fore();
   }
   public static  void colorBoard()//# Colors the board
   {
        lv=3;
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                graphicalboard[i][j].setForeground(Color.black);
                if(i<3 || i>5)
                {
                    if(j<3 || j>5)
                     graphicalboard[i][j].setBackground(Color.yellow);
                     else
                     graphicalboard[i][j].setBackground(Color.pink);
                }
                else
                {
                    if(j<3 || j>5)
                     graphicalboard[i][j].setBackground(Color.pink);
                    else
                     graphicalboard[i][j].setBackground(Color.yellow);
                }
            }
        }
   }
}

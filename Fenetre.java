import javax.swing.JFrame;
 
public class Fenetre extends JFrame {

  public Fenetre(){
  	Shape sh=new Shape();
    this.setTitle("Ma première fenêtre Java");
    this.setSize(1000, 500);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    this.setContentPane(new Draw(sh));          
    this.setVisible(true);

  }

}
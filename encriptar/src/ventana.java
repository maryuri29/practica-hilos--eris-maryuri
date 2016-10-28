import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.swing.*;


public class ventana implements MouseListener
{
    public JFrame frame;
    public JFileChooser fchooser;
    public JButton abrir;
    public hilo buscarMD5[] = new hilo[50];
    int cant_hilos;
    public String codigo[] = new String[100];
    int cant_codigos;
    int pos_actual=0;
    public JComboBox cantidad;
    public JLabel label;
    public JTextArea area;
   
    public ventana()
    {

    }

   public void iniciar()
   {
      frame = new JFrame("desencriptar MD5");
      frame.setBounds(100,100,800,500);
      frame.setResizable(false);
      frame.setLayout(null);
      
      fchooser = new JFileChooser();
      fchooser.setBounds(50,20,400,400);
      fchooser.setCurrentDirectory(new File("."));
      fchooser.setControlButtonsAreShown(false);
      fchooser.setMultiSelectionEnabled(false);
      
      abrir = new JButton("Abrir Archivo");
      abrir.setBounds(280,420,150,30);
      abrir.setName("abrir");
      abrir.addMouseListener(this);
      
      cantidad = new JComboBox();
      cantidad.setBounds(200,420,50,30);
      cantidad.addItem(1);
      cantidad.addItem(2);
      cantidad.addItem(3);
      cantidad.addItem(4);
      cantidad.addItem(5);
      cantidad.addItem(6);
      
      area = new JTextArea();
      area.setBounds(500,50,250,400);
      area.setEditable(false);
    
      label = new JLabel("cantidad de hilos: ");
      label.setBounds(70,420,150,30);
      
      frame.getContentPane().add(area);
      frame.getContentPane().add(label);
      frame.getContentPane().add(cantidad);
      frame.getContentPane().add(abrir);
      frame.getContentPane().add(fchooser);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }

   
    public void procesar_codigo(int pos)
    {
        String linea, usuario, clave;
        
        linea=codigo[pos];
        
        int parte = (int)(52/cant_hilos);
        usuario=linea.split("::")[0];
        clave=linea.split("::")[1];
                            
        System.out.println(linea+":\n\n");
               
        area.append("Procesando...\n");
        
        int i=0;
        hilo.exito=false;
        for(i=0;i<cant_hilos-1;i++)
        {
            if(buscarMD5[i]!=null)
            {
                buscarMD5[i].interrupt();
                buscarMD5[i]=null;
            }
            buscarMD5[i] = new hilo(this,usuario,clave,i*parte,(i+1)*parte-1);
            buscarMD5[i].start();
        }
        buscarMD5[i] = new hilo(this,usuario,clave,i*parte,51);
        buscarMD5[i].start();
    }
   
    @Override
    public void mouseClicked(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        if(e.getComponent().getName().compareTo("abrir")==0)
        {
            File archivo = fchooser.getSelectedFile();
            
            cant_hilos = (int)cantidad.getSelectedItem();
            
            if(archivo!=null)
            {
                if(archivo.getName().endsWith("txt"))
                {
                    try {
                        
                        BufferedReader buffer = new BufferedReader(new FileReader(archivo));
                        String linea = buffer.readLine();
                        int pos=0;
                        
                        abrir.setEnabled(false);
                        cantidad.setEnabled(false);
                         
                        while(linea!=null)
                        {
                            codigo[pos]=linea;
                            linea = buffer.readLine();
                            pos++;
                        }
                        
                        cant_codigos=pos;
                        
                        buffer.close();
                        
                        procesar_codigo(0);
                        
                    } catch (FileNotFoundException ex) 
                    {
                       
                    } catch (IOException ex) {
                       
                    }
                }
                else
                {
                    System.out.println("otro archivo");
                }
            }
            else
            {
                System.out.println("no hay archivo");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}



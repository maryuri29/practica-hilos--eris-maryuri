public class hilo extends Thread
{
    
    String usuario;
    String cadenaMD5;
    static boolean exito;
    boolean termino=false;
    int ini, fin;
    ventana vent;
    
    char caracateres[] = new char[52];
    int cod[] = {-1,-1,-1,-1,-1,-1};
     
    public hilo(ventana v, String u, String md5, int i, int f)
    {
      usuario=u;
      cadenaMD5=md5;
      ini=i;
      fin=f;
      vent=v;
        for (int c = 0; c < 26; c++) 
        {
           caracateres[c]=(char)('A'+c);
           caracateres[c+26]=(char)('a'+c);
        }
    }
    
    public void nextCodigo()
    {
        if(cod[0]==-1)
        {
            cod[0]=ini;
        }
        else
        {
            cod[0]++;
            if(cod[0]==fin+1)
            {
                cod[0]=ini;
                cod[1]++;
            }
            int c=1;
            while(cod[c]==52 && c<5)
            {
                cod[c]=0;
                c++;
                cod[c]++;
            }
            if(cod[c]==52)
            {
                termino=true;
            }
        }
    }
    
    public String codStr()
    {
       int c=0;
       String str="";
       while(cod[c]!=-1 && c<6)
       {
           str=str+caracateres[cod[c]];
           c++;
       }
       return str;
    }
    
    @Override
    public void run()
    {
        while(!exito && !termino)
        {
           
                String newMD5 = MD5.getMD5(codStr());
                
                if(cadenaMD5.compareTo(newMD5)==0)
                {
                    exito=true;
                    System.out.println(usuario+" "+codStr());
                    vent.area.append(usuario+" "+codStr()+"\n");
                    vent.pos_actual++;
                    if(vent.pos_actual<vent.cant_codigos)
                    {
                        vent.procesar_codigo(vent.pos_actual);
                    }
                    else
                    {
                        vent.abrir.setEnabled(true);
                        vent.cantidad.setEnabled(true);
                    }
                }
                
                nextCodigo();
                
                //Thread.sleep(50);
                
            
        };
    }
}

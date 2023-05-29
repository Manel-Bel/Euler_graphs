package Abstrait;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.*;
import java.awt.Image;



public class Resize {

	private int currentFrameHeight=Abstract.HEIGHT;
	private int currentFrameWidth=Abstract.WIDTH;
	private HashMap<Component,ArrayList<Double>>scales=new HashMap<Component,ArrayList<Double>>();
	private JPanel panel;
	public Resize(JPanel p) {
		panel=p;
		updateAllComponentScales(panel);
		//System.out.println("scales dans Resize");
		//afficheScales();
		
		
	}
	public void ResizeAllComponent() {
		ResizeAllComponentAUX(panel);
	}
	public void ResizeAllComponentAUX(JPanel panel){
		//compter le nombre de component dans le panel
				
		int taille=panel.getComponentCount();
				
		//tester si c'est absolute layout ou GridBagLayout			  
			
		boolean isabsolute=(panel.getLayout()==null)?true:false;					  
		
		for(int i=0;i<taille;i++) {
			Component tmp=panel.getComponent(i);
			if(tmp.getClass() == JPanel.class) {
				JPanel tpanel=(JPanel)tmp;						
				//appel recursif si le Component est de type effectif JPanel
				ResizeAllComponentAUX(tpanel);
			}
			ResizeComponent(tmp,isabsolute);
		}
   
	}
	

	public void  ResizeComponent(Component tmp,boolean absoluteLayout){
	    //tmp.setFont(newFont);
	    ArrayList<Double>l=scales.get(tmp);
	    int width=sizeWidth(l.get(0));
	    int height=sizeHeight(l.get(1));
	    if(absoluteLayout){
	      int x=sizeWidth(l.get(2));
	      int y=sizeHeight(l.get(3));
	      tmp.setBounds(x,y,width,height);

	    }else{
	      tmp.setPreferredSize(new Dimension(width,height));
	    }
	    
	  //resize img size :
	      
	      if(tmp.getClass() == JLabel.class ) {
	    	  JLabel imgtmp=(JLabel)tmp;
	    	  if(imgtmp.getIcon()!= null) {
	    		 // System.out.println("resize a component "+imgtmp);
	    		  ImageIcon newimg=resizeImg(((ImageIcon)imgtmp.getIcon()).getImage(),width,height);
	    		  imgtmp.setIcon(newimg);
	    	  }
	    	  
	      }
	      
	      if(tmp.getClass() == JButton.class ) {
	    	  JButton imgtmp=(JButton)tmp;
	    	  if(imgtmp.getIcon()!= null) {
	    		 // System.out.println("resize a component "+imgtmp);
	    		  ImageIcon newimg=resizeImg(((ImageIcon)imgtmp.getIcon()).getImage(),width,height);
	    		  imgtmp.setIcon(newimg);	  
	    	  }
	      }
	    
	}
	private void updateAllComponentScales(JPanel panel){
		//compter le nombre de component dans le panel
		int taille=panel.getComponentCount();
		
		//tester si c'est absolute layout ou GridBagLayout		
		
		boolean isabsolute=(panel.getLayout()==null)?true:false;
					  
		// on calcul les scales de tous les components 
		
		for(int i=0;i<taille;i++) {
		
			Component tmp=panel.getComponent(i);
			
			//appel recursif si le Component est de type JPanel
			
			if(tmp.getClass() == JPanel.class) {
			
				JPanel tpanel=(JPanel)tmp;
				
				
				
				
				
				updateAllComponentScales(tpanel);
				
			}
			
			updatescales(tmp,isabsolute);
			
		}
	    
	  
	}
	  
	  
	private  void updatescales(Component tmp,boolean absoluteLayout){
	    
		ArrayList<Double>l=new ArrayList<>();
		
		//System.out.println("Dans le resize updatescales");
	    
		//System.out.println(tmp);
	    
	    
		if(absoluteLayout){
	      l.add(0,scaleX((int)tmp.getBounds().getWidth()));
	      l.add(1,scaleY((int)tmp.getBounds().getHeight()));
	      l.add(2,scaleX((int)tmp.getBounds().getX()));
	      l.add(3,scaleY((int)tmp.getBounds().getY()));
	    }else{
	      l.add(0,scaleX((int)tmp.getPreferredSize().getWidth()));
	      l.add(1,scaleY((int)tmp.getPreferredSize().getHeight()));

	    }
	    scales.put(tmp, l);
	  }
	  
	 
	public double scaleX(int x){
	    //System.out.println(x);
	    double scal=((double)x) / ((double)currentFrameWidth);
	   // System.out.println("scaleX:"+scal);
	    return scal;

	  }
	  
	public double scaleY(int y){
	    //System.out.println(y);
	    double scal=((double)y) / ((double)currentFrameHeight);
	  //  System.out.println("scaleY:"+scal);
	    return scal;
	  
	}
	  
	public int sizeWidth(double scaleX){
		//System.out.println("currenW:"+currentFrameWidth+" scaleX"+scaleX);
	    int tmp=(int)Math.round(currentFrameWidth*scaleX);
	    return tmp;

	  
	}
	 
	public int sizeHeight(double scaleY){
	    int tmp=(int)Math.round(currentFrameHeight*scaleY);
	    return tmp;

	}
	public void afficheScales() {
	
		for(Component tmp: scales.keySet()) {
			System.out.print(tmp);
			for(Double arrytmp : scales.get(tmp)) {
				System.out.print(" "+arrytmp);
			}
			System.out.println();
		}
		
	}
	public boolean egal(HashMap<Component,ArrayList<Double>> c) {
		for(Component tmp: scales.keySet()) {
			ArrayList<Double>l1=scales.get(tmp);
			ArrayList<Double>l2=c.get(tmp);
			int taille=l1.size();
			if(l1.size()!=l2.size()) {
				System.out.println("la taille de deux liste est differents l1 : "+l1.size()+" l2:"+l2.size());
				
				return false;
			}
			for(int i=0;i<taille;i++) {
				if(l1.get(i)-l2.get(i)!=0) {
					System.out.println(tmp+" l1:"+l1.get(i)+" l2:"+l2.get(i));
					return false;
				}
			}
		}
		return true;
	}
	
	public void setCurrentW(int width) {
		this.currentFrameWidth=width;
	}
	public void setCurrentH(int height) {
		this.currentFrameHeight=height;
	}

	public ImageIcon resizeImg(Image src,int width,int height){
		Image newImg=src.getScaledInstance(width,height,Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}
	


}
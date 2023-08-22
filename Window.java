package main;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends JFrame implements Runnable /* Implementamos la interfaz Runnable que me exigira que defina el cuerpo del metodo abstracto llamado Runnable*/{
	
	public static final/* final porque son variables constantes*/ int/*Utilizadas para las dimensiones de la ventana*/ WIDTH = 800, /*Ancho*/ HEIGHT = 600;/* Altura*/  
	private Canvas canvas; /* Lo que nos permitira dibujar*/
	private Thread thread; /* Crear El hilo principal del juego  */
	private boolean running = false; /* Utilizamos valor Booleano para mantener controlado el estado del juego*/
	
	/* Objetos para Dibujar: */
	private BufferStrategy bs;
	private Graphics g;
	
	public Window() /*Constructor*/
	{
		setTitle("Intergalactic Battle");
		setSize(WIDTH, HEIGHT); /* Dimensiones*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/* Permitiremos que la ventana se cierre cuando le damos a la x*/
		setResizable(false); /* Para que la ventana no se redireccione */
		setLocationRelativeTo(null); /* Para permitir que la ventana se despliegue en e centro de la pantalla cuando inicie el programa*/
		setVisible(true); /* Para desplegar la ventana y hacera visible*/
		
		canvas = new Canvas();
		
		canvas.setPreferredSize /* Para limitar el tamano*/(new Dimension(WIDTH, HEIGHT /* Parametros para Dimension*/));
		canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		canvas.setFocusable(true); /* Sirve para recibir entradas por parte del teclado*/
		
		add(canvas); /* Para agragar el canvas a la ventana*/
	}

	public static void main(String[] args) {
		new Window().start();; /* Creamos este objeto para que cuando corra, corra la ventana y arrancar el hilo principal*/

	}
    
	int x = 0; /* Crear variable para mover el rectangulo*/
	private void update/*Metodo para actualizar*/() {
		x++; /* Para que el rectangulo se este moviendo en x*/
	}
	
	private void draw()/*Metodo para dibujar*/ {
		/* Inicializar el metodo para dibujar: */
		bs = canvas.getBufferStrategy();
		
		if(bs == null) {
			canvas.createBufferStrategy(3); /* Numero de Bufers adecuado que utiliza un canvas*/
			return;
		}
		
		g = bs.getDrawGraphics(); /*Nuestro objeto g sera igual a bs.getDrawGraphics*/
		/* Empieza el dibujo*/
		
		//-----------------------
		
		g.clearRect(x, x, WIDTH, HEIGHT);
		
		g.drawRect(x, 0, 100, 100); /*Dibujando en la ventana*/
		
		
		//-----------------------
		
		/* Termina el dibujo*/
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() {
		
		while(running)/* Este ciclo se encarga de actualizar la posicion de todos los objetos para mantener el juego actualizado*/{
			
			update(); /*ACtualizar*/
			draw(); /*Dibujar*/
		}
			
		  stop(); /* Cuando acaba el metodo*/
	}
	
	/*Creamos dos metodos para iniciar o detener el hilo principal*/
	private void start()/*Iniciar el hilo*/ {
		
		thread = new Thread(this); /* Esta clase implementa la interfaz Runnable*/
		thread.start(); /* Llama el metodo run que creamos aneriormente*/
		running = true; /* Cuando arrancamos el hilo running es igual a verdadero*/
	}
	private void stop()/*Detener el Hilo*/ {
		try {
			thread.join();
			running = false; /*Cuando cerremos el hilo running sera falso*/
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

}

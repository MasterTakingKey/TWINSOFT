package application;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public abstract class Animaciones {

	public StackPane stackPane;
	
	public Baraja baraja;
	public ImageView imagen1;
	public ImageView imagen2;
	public Carta carta;
	
	public Animaciones() {
	}
	
    public Node FlipAdelanteCard(ImageView card) {
        return card;
    }
    
    public RotateTransition createFirstRotator(Node card) {
   
        RotateTransition firstRotator = new RotateTransition(Duration.millis(200), card);
        
        firstRotator.setAxis(Rotate.Y_AXIS);
        firstRotator.setFromAngle(180);
        firstRotator.setToAngle(91);
        firstRotator.setInterpolator(Interpolator.LINEAR);
        firstRotator.setCycleCount(1);
        
        return firstRotator;
    }
    public RotateTransition createSecondRotator(Node card) {
    	
        RotateTransition secondRotator = new RotateTransition(Duration.millis(200), card);
        
        secondRotator.setAxis(Rotate.Y_AXIS);
        secondRotator.setFromAngle(90);
        secondRotator.setToAngle(0);
        secondRotator.setInterpolator(Interpolator.LINEAR);
        secondRotator.setCycleCount(1);

        return secondRotator;
    }
    
    public TranslateTransition createIncorrectTranslation(Node card) {
    	
    	TranslateTransition translation = new TranslateTransition(Duration.millis(300), card);
    	
    	translation.setByY(50);
    	translation.setByY(-50);
    	translation.setAutoReverse(true);
    	translation.setCycleCount(2);

    	return translation;
    }
    
    public TranslateTransition createCorrectTranslation(Node card) {
    	
    	TranslateTransition translation = new TranslateTransition(Duration.millis(200), card);

    	translation.setByY(50);
    	translation.setByY(-50);
    	translation.setAutoReverse(true);
    	translation.setCycleCount(4);

    	return translation;

    }
    
    public abstract void crearAnimacion();
}
    
